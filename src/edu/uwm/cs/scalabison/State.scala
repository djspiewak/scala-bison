/*
 * Copyright 2011 University of Wisconsin, Milwaukee
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.uwm.cs.scalabison;

import scala.collection.mutable.Map
import scala.collection.Set
import scala.collection.immutable.ListSet
import scala.collection.mutable.HashMap
import scala.collection.mutable.HashSet
import scala.collection.mutable.ArrayBuffer
import edu.uwm.cs.util.Dominator
import edu.uwm.cs.util.Reach


class State(val number : Int, val core : Set[Item]) {
  // Scala 2.7 dosn't permit super.x any more for some unspecified reason,
  // so I have to fake the getter as a def.
  private val _actions : Map[Terminal,Action] = new HashMap();
  def actions : scala.collection.Map[Terminal,Action] = _actions;
  def putAction(t : Terminal, a : Action) = _actions.put(t,a);

  private val _gotos : Map[Nonterminal,State] = new HashMap();
  def gotos : scala.collection.Map[Nonterminal,State] = _gotos;
  def putGoto(n : Nonterminal, s : State) = _gotos.put(n,s);

  private var _default : Action = null;
  def default : Action = _default;
  def default_=(a : Action) : Unit = _default = a;

  override def equals(a : Any) : Boolean = {
    a match {
      case o:State => core.equals(o.core);
      case _ => false
    }
  }

  override def hashCode() : Int = core.hashCode();

  private var cachedCompletion : Set[Item] = null;
  def completion : Set[Item] = {
    if (cachedCompletion == null) {
      cachedCompletion = complete();
    }
    cachedCompletion
  }
    
  protected def complete() : Set[Item] = {
    /*
    println("Completing state " + number + ":");
    for (item <- core) {
      println("    " + item);
    }
    println();
    */
    var completion : HashSet[Item] = new HashSet;
    completion ++= core;
    var addedNT : ListSet[Nonterminal] = ListSet.empty;
    var added : List[Item] = Nil ++ completion;
    while (added.length > 0) {
      val temp : List[Item] = added;
      added = Nil
      for (item <- temp) {
	item.symbol match {
	  case nt:Nonterminal =>
	    if (!(addedNT contains nt)) {
	      addedNT += nt;
	      for (rule <- nt.rules) {
		val nitem : Item = new Item(rule,0);
		// println("Adding item " + nitem);
		completion += nitem;
		added ::= nitem;
	      }
	    }
	  case _ => ()
	}
      }
    }
    completion
  }

  def addNonFree(nonfree : scala.collection.mutable.Set[Item]) : Unit = {
    // create a graph
    // 1. first the nodes
    val actionset : HashSet[Action] = new HashSet();
    val nodes : ArrayBuffer[Any] = new ArrayBuffer();
    val index : Map[Any,Int] = new HashMap();
    nodes += (null);
    nodes += (null);
    for (item <- completion) {
      val n : Int = nodes.size;
      nodes += (item);
      index.put(item,n);
    }
    val firstAction : Int = nodes.size;
    for ((_,action) <- actions) {
      actionset += action;
    }
    if (default != null) actionset += default;
    for (action <- actionset) {
      val n : Int = nodes.size;
      nodes += (action);
      index.put(action,n);
    }
    // 2. then the successor relation
    val succ : PartialFunction[Int,scala.collection.Set[Int]] = {
      i:Int => i match {
	case 1 => {
	  var s : ListSet[Int] = ListSet.empty;
	  for (item <- core) {
	    s = s + index(item)
	  }
	  s
	}
	case _ =>
	  nodes(i) match {
	    case _:Action => ListSet.empty
	    case item:Item => {
	      item.symbol match {
		case t:Terminal => {
		  actions.get(t) match {
		    case Some(a:ShiftAction) => ListSet.empty + index(a);
		    case Some(a:ErrorAction) => ListSet.empty + index(a);
		    case _ => ListSet.empty
		  }
		}
		case nt:Nonterminal => {
		  var s : ListSet[Int] = ListSet.empty;
		  for (rule <- nt.rules) {
		    s = s + index(new Item(rule,0));
		  }
		  s
		}
		case null => {
		  val red : ReduceAction = ReduceAction(item.rule);
		  if (actionset contains red)
		    ListSet.empty + index(red)
		  else if (default == AcceptAction())
		    ListSet.empty + index(AcceptAction())
		  else
		    ListSet.empty
		}
	      }
	    }
	  }
      }
    };
    if (Options.verbose > 2) {
      println("Graph of state (for computing free positions)");
      for (i <- 1 until nodes.size) {
	print("  " + i + ": ");
	nodes(i) match {
	  case null => println("[INIT]") 
	  case x => println(x);
	  }
	print("    ->");
	for (j <- succ(i)) {
	  print(" " + j);
	}
	println();
      }
    }
    // 3. now compute reachability and domination:
    val reach : Reach = new Reach(nodes.size-1,succ);
    val dominator : Dominator = new Dominator(nodes.size-1,succ,1);
    // 4. now for every item, check that it dosn't reach itself:
    for (item <- completion) {
      val i : Int = index(item);
      if (reach(i) contains i) {
	if (Options.verbose > 1) {
	  println("Item is forbidden: " + item);
	}
	nonfree += item;
      }
    }
    // 5. check that every item dominates every action it can reach:
    for (item <- completion) {      
      val i : Int = index(item);
      for (action <- actionset) {
	val a : Int = index(action);
	if ((reach(i) contains a) && !dominator.dominates(i,a)) {
	  if (Options.verbose > 1) {
	    println("Item is dependent: " + item);
	  }
	  nonfree += item;
	}
      }
    }
  }

  def longestItem : Item = {
    var longest : Item = null;
    for (item <- core) {
      if (longest == null || item.index > longest.index) {
	longest = item;
      }
    }
    longest
  }

  /** A rough-and-ready way to say the state doesn't need
   * to be implemented in a Left-corner parser.  Even
   * if it is not past recognition, it may still be useless,
   * but it's harder to detect.
   */
  def pastRecognition : Boolean = {
    for (item <- core) {
      if (item.index > item.rule.recognitionPoint) return true;
    }
    return false;
  }

  protected def appendHeader(sb : StringBuilder) : Unit = {
    sb append "state ";
    sb append number
    sb append '\n'
    sb append '\n'
  }

  override def toString : String = toString(true);

  def toString(showAll : Boolean) : String = {
    val sb : StringBuilder = new StringBuilder();
    appendHeader(sb)
    for (item <- (showAll match { case true => completion; 
				  case false => core})) {
      sb append "    "
      sb append item
      sb append '\n'
    }
    sb append '\n'
    for ((token,action) <- actions) {
      sb append "    "
      sb append (token.name)
      sb append '\t'
      sb append action
      sb append '\n'
    }
    if (default != null) {
      sb append "    $default\t"
      sb append default
      sb append '\n'
    }
    sb append '\n'
    for ((nt,state) <- gotos) {
      sb append "    "
      sb append (nt.name)
      sb append "\tgo to state "
      sb append (state.number)
      sb append '\n'
    }
    if (gotos.size > 0) {
      sb append '\n'
    }
    sb append '\n'
    sb toString
  }
}
