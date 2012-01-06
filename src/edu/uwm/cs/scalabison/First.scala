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

import scala.collection.Set;
import scala.collection.mutable.{HashMap};
import scala.collection.immutable.ListSet;

class First(grammar : Grammar) extends Function[Any,FirstSet] {

  private val ntmap : HashMap[Nonterminal,FirstSet] = new HashMap;

  // initialize and run to a fixed point
  {
    for (sym <- grammar.all_symbols) {
      sym match {
	case nt:Nonterminal => ntmap.put(nt,FirstSet.empty);
	case _ => ()
      }
    }
    var done : Boolean = false;
    while (!done) {
      done = true;
      for (sym <- grammar.all_symbols) {
	sym match {
	  case nt:Nonterminal => {
	    var fs : FirstSet = FirstSet.empty;
	    for (rule <- nt.rules) {
	      fs = fs ++ first(rule) 
	    }
	    if (!(fs.equals(ntmap(nt)))) {
	      ntmap.put(nt,fs);
	      // println("getting bigger: FIRST(" + nt.name + ") = " + fs);
	      done = false;
	    }
	  }
	  case _ => ()
	}
      }
    }
    /*
    for ((nt,fs) <- ntmap) {
      println("FIRST(" + nt.name + ") = " + fs);
    }*/
  }

  def first(sym : Symbol) : FirstSet = {
    sym match {
      case nt : Nonterminal => ntmap(nt);
      case t : Terminal => new FirstSet(ListSet.empty + t,false)
    }
  }

  def first(rule : Rule) : FirstSet = first(new Item(rule,0));

  def first(item : Item) : FirstSet = first(item.rule.rhs.drop(item.index));

  def first(l : List[Symbol]) : FirstSet = {
    l match {
      case Nil => FirstSet.epsilon
      case h::t => first(h) dot first(t)
    }
  }

  override def apply(x : Any) : FirstSet = {
    x match {
      case x:Item => first(x)
      case x:Rule => first(x)
      case x:List[Symbol] => first(x)
      case x:Symbol => first(x)
      case _ => FirstSet.empty
    }
  }
}
