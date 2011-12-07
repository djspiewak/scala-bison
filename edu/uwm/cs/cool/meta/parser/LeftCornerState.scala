/* LALR(1) Parser generation for Scala
 * John Boyland
 * This file may be used, copied and/or modified for any purpose.
 */
package edu.uwm.cs.cool.meta.parser;

import scala.collection.Set;
import scala.collection.immutable.ListSet;
import scala.collection.mutable.HashMap;
import scala.collection.mutable.HashSet;
import scala.collection.mutable.ArrayBuffer;

import scala.io.Source;

/**
 * A state used for left corner parsing.  We build the states
 * from existing LALR(1) states, and these are passed to the constructor.
 * This new state will have a subset of the items and a modified
 * subset of the actions -- some of the shift actions and all of
 * the ReduceActions are converted into AnnounceActions.
 * <p>
 * Since we are building our states from existing LALR(1) states,
 * we distinguish three kinds of states: <ol>
 * <li> A state with the implicit item S -> |- . NT (empty core)
 * <li> A state with the implicit item S -> |- NT .
 * <li> A state with no implicit items.
 */
class LeftCornerState(n : Int, 
		      val nonterminal : Nonterminal, 
		      val atStart : Boolean,
		      val state : State,
		      c : Set[Item]) extends State(n,c) 
{
  def this(n : Int, nt : Nonterminal, st : State) = this(n,nt,true,st,Set.empty);
  def this(n : Int, st : State, c : Set[Item]) = this(n,null,false,st,c);

  override def hashCode() : Int = {
    if (nonterminal == null) c.hashCode();
    else nonterminal.hashCode() + atStart.hashCode() + c.hashCode();
  }
  override def equals(a : Any) : Boolean = {
    a match {
      case o : LeftCornerState => 
	(nonterminal == o.nonterminal && 
         atStart == o.atStart && 
	 core.equals(o.core))
      case _ => false
    }
  }

  override def appendHeader(sb : StringBuilder) : Unit = {
    sb append "state ";
    sb append number
    sb append " ( based on state "
    sb append state.number
    sb append " )\n\n"
    if (nonterminal != null) {
      sb append "    _: \u22a2 ";
      if (atStart) { sb append ". "; }
      sb append nonterminal.name
      if (!atStart) { sb append " ."; }
      sb append "\n";
    }
  }

  override protected def complete() : Set[Item] = {
    /*
    println("Completing LC state " + number + ":");
    if (nonterminal != null) {
      println("    _: |- . " + nonterminal.name);
    }
    for (item <- core) {
      println("    " + item);
    }
    println();
    */
    var completion : HashSet[Item] = new HashSet;
    completion ++= core;
    var addedNT : ListSet[Nonterminal] = ListSet.empty;
    if (nonterminal != null && atStart) {
      // force the fake rule:
      addedNT += nonterminal;
      for (rule <- nonterminal.rules) {
	val nitem : Item = new Item(rule,0);
	// println("Adding implicit item " + nitem);
	completion += nitem;
      }
    }
    var added : List[Item] = Nil ++ completion;
    while (added.length > 0) {
      val temp : List[Item] = added;
      added = Nil
      for (item <- temp) {
	if (!item.ready) { 
	  // not yet reached recognition point
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
    }
    completion
  }

  private var inited : Boolean = false;

  def init(first : First, lcfollow : LeftCornerFollow,
	   states : HashSet[LeftCornerState]) : Unit = {
    if (inited) return;
    inited = true;
    val ignored = completion;
    //println("Initializing " + this.toString(true));

    val gotoItems = new HashMap[Symbol,ListSet[Item]]();
    val recogActions : HashMap[Terminal,Rule] = new HashMap[Terminal,Rule];

    // force the artificial state transition
    if (nonterminal != null && atStart) {
      gotoItems.put(nonterminal,ListSet.empty);
    }
    for (item <- completion) {
      if (item ready) {
	for (t <- first(item).set) {
	  recogActions.put(t,item.rule);
	}
      } else {
	gotoItems.get(item.symbol) match {
	  case None => gotoItems.put(item.symbol,ListSet.empty + item.next());
	  case Some(s) => gotoItems.put(item.symbol,s + item.next());
	}
      }
    }

    for ((token,action) <- state.actions) {
      action match {
	case ReduceAction(r) => {
	  if (completion contains new Item(r,r.length)) {
	    if (r.recognitionPoint != r.length) {
	      throw new GrammarSpecificationError("assertion error: " + r);
	    }
	    putAction(token,AnnounceAction(r));
	  } else {
	    val rule : Rule = getAnnouncedRule(r.lhs);
	    if (rule != null)
	      putAction(token,AnnounceAction(rule));
	  }
	}
	case ShiftAction(state) => {
	  // only if this shift is relevant, do we use it
	  if (gotoItems isDefinedAt token) {
	    val newState = makeState(first,lcfollow,states,state,gotoItems(token));
	    putAction(token,new ShiftAction(newState));
	  } else {
	    val rule : Rule = getAnnouncedRule(token);

	    // println("On token " + token + ", action is " + action + ", rule = " + rule);
	    if (rule != null) {
	      putAction(token,AnnounceAction(rule));
	    } else if (nonterminal != null && !atStart) {
	      putAction(token,AcceptNTAction(nonterminal));
	    }
	  }
	}
	case ErrorAction(s) => {
	  putAction(token,action)
	}
      }
    }
    state.default match {
      case null => { }
      case ReduceAction(r) => {
	if (completion contains new Item(r,r.length)) {
	  if (r.recognitionPoint != r.length) {
	    throw new GrammarSpecificationError("assertion error: " + r);
	  }
	  default = AnnounceAction(r);
	} else {
	  val rule : Rule = getAnnouncedRule(r.lhs);
	  if (rule != null)
	    default = AnnounceAction(rule);
	}
      }	
    }

    if (nonterminal != null && atStart) {
      var special : Action = null;
      for (rule <- nonterminal.rules) {
	if (first(rule).incEpsilon) {
	  if (special == null)
	    special = AnnounceAction(rule);
	  else
	    println("Warning: announce conflict in state " + number);
	}
      }
      if (special != null) {
	//println("Generated announce action " + special);
	val sp = special;
	for (token <- lcfollow(nonterminal)) {
	  actions.get(token) match {
	    case Some(`sp`) =>
	      // everything is fine
	    case Some(_) =>
	      println("Warning: announce conflict in state " + number + " on "
		      + token)
	    case None =>
	      putAction(token,special)
	  }
	}
      }
    }

    if (nonterminal != null && !atStart) {
      val special = AcceptNTAction(nonterminal)
      for (token <- lcfollow(nonterminal)) {
	actions.get(token) match {
	  case Some(AcceptNTAction(`nonterminal`)) =>
	    // everything is fine
	  case Some(x) =>
	    println("Warning: accept conflict in state " + number + " on "
		    + token + ", conflicts with " + x)
	  case None =>
	    putAction(token,special)
	}
      }
    }

    for ((nt,state) <- state.gotos) {
      if (gotoItems isDefinedAt nt) {
	var next : LeftCornerState = null;
	if (nonterminal == nt && atStart) {
	  next = cacheState(first,lcfollow,states,
			    new LeftCornerState(states.size,
						nonterminal,false,state,
						gotoItems(nt)));
	} else {
	  next = makeState(first,lcfollow,states,state,gotoItems(nt));
	}
	putGoto(nt,next);
      }
    }
  }

  private var announcedRule : HashMap[Symbol,Rule] = new HashMap[Symbol,Rule];
  // private var debuglevel : Int = 0;

  protected def getAnnouncedRule(sym : Symbol) : Rule = {
    /*if (debuglevel == 0) {
      print("st. " + debuglevel);
    }
    print("\t");
    for (i <- 0 upto debuglevel) {
      print("  ");
    }
    print("announced(" + sym + ") = ");*/
    announcedRule.get(sym) match {
      case Some(r) => return r;
      case None => announcedRule.put(sym,null);
    }
    for (item <- completion) {
      if (item.symbol == sym) {
	if (item.ready) {
	  //println("inferred ready rule for " + sym + " is " + item.rule);
	  announcedRule.put(sym,item.rule);
	  return item.rule;
	}
      }
    }
    //println("no ready rules");
    var rule : Rule = null;
    for (item <- state.completion) {
      if (item.index == 0 && item.symbol == sym) {
	//println("Found extra rule that can handle " + sym + ": " + item);
	val xrule : Rule = getAnnouncedRule(item.rule.lhs);
	if (rule == null) 
	  rule = xrule;
	else if (xrule != null && xrule != rule) 
	  throw new AssertionError("different: " + rule + " != " + xrule + 
				 " for " + sym + " in " + this.toString(true)); 
      }
    }
    announcedRule.put(sym,rule);
    rule
  }

  protected def makeState(first : First, lcfollow : LeftCornerFollow,
			  states : HashSet[LeftCornerState], 
			  state : State,
			  newcore : Set[Item]) 
    : LeftCornerState = {
      cacheState(first,lcfollow,
		 states,new LeftCornerState(states.size,state,newcore));
  }

  protected def cacheState(first : First, lcfollow : LeftCornerFollow,
			   states : HashSet[LeftCornerState],
                           newState : LeftCornerState) : LeftCornerState = {
    states.findEntry(newState) match {
      case Some(s) => s; 
      case None => {
	states += newState;
	newState.init(first,lcfollow,states);
	newState
      }
    }
  }
}
