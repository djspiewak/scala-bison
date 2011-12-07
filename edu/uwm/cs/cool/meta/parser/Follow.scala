/* LALR(1) Parser generation for Scala
 * John Boyland
 * This file may be used, copied and/or modified for any purpose.
 */
package edu.uwm.cs.cool.meta.parser;

import scala.collection.Set;
import scala.collection.mutable.{HashMap};
import scala.collection.immutable.ListSet;


class Follow(grammar : Grammar, first : First) 
extends Function[Nonterminal,Set[Terminal]]
{
  private val ntfol : HashMap[Nonterminal,scala.collection.immutable.Set[Terminal]] = new HashMap;
  
  { // initialize the follow set
    for (sym <- grammar.all_symbols) {
      sym match {
	case nt:Nonterminal =>
	  ntfol.put(nt,scala.collection.immutable.Set.empty);
	case _ => ()
      }
    }
  }

  private var done : Boolean = false;

  {
    // standard "code until done" loop:
    while (!done) {
      done = true;
      for (rule <- grammar.rules) {
	setFollow(rule.rhs,ntfol(rule.lhs));
      }
    }
  }

  private def setFollow(l : List[Symbol], fol : scala.collection.immutable.Set[Terminal]) : Unit = {
    l match {
      case (nt:Nonterminal)::t => {
	val oldFol : scala.collection.immutable.Set[Terminal] = ntfol(nt);
	val newFol : Set[Terminal] = follow(t,fol);
	if (!(newFol subsetOf oldFol)) {
	  ntfol.put(nt,oldFol ++ newFol);
	  done = false;
	}
      }
      case _ => ()
    }
    l match {
      case Nil => ();
      case _::t => setFollow(t,fol);
    }
  }

  def apply(item : Item) : Set[Terminal] = {
    follow(item.rule.rhs.drop(item.index),ntfol(item.rule.lhs))
  }

  private def follow(t : List[Symbol], fol : scala.collection.immutable.Set[Terminal]) : Set[Terminal] = {
    (first(t) dot new FirstSet(fol,false)).set
  }

  def apply(nt : Nonterminal) : Set[Terminal] = ntfol(nt);

}
