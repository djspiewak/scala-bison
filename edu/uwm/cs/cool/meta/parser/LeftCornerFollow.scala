/* LALR(1) Parser generation for Scala
 * John Boyland
 * This file may be used, copied and/or modified for any purpose.
 */
package edu.uwm.cs.cool.meta.parser;

import scala.collection.Set;
import scala.collection.mutable.{HashMap};
import scala.collection.immutable.ListSet;

import scala.io.Source;

/**
 * A restriction of the Follow Set that
 * applies to left corner parsing: 
 * this is the set of tokens that can follow a NT
 * during town-down parsing.
 *
 * This code is copied almost verbatim from Follow.scala.
 */
class LeftCornerFollow(val grammar : Grammar,
		       val first : First, val follow : Follow)
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

  {
    for (rule <- grammar.rules) {
      val rp : Int = rule.getRecognitionPoint();
      setFollow(rule.rhs.drop(rp),follow(rule.lhs));
    }

    /*for ((nt,fs) <- ntfol) {
      println("FFollow(" + nt + ") = " + fs);
    }*/
  }

  private def setFollow(l : List[Symbol], fol : Set[Terminal]) : Unit = {
    /*println("Calling setFollow(" + l + "," + fol + ")");*/
    l match {
      case (nt:Nonterminal)::t => {
	val oldFol : scala.collection.immutable.Set[Terminal] = ntfol(nt);
	val newFol : Set[Terminal] = computeFollow(t,fol);
	if (!(newFol subsetOf oldFol)) {
	  ntfol.put(nt,oldFol ++ newFol);
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
    computeFollow(item.rule.rhs.drop(item.index),follow(item.rule.lhs))
  }

  private def computeFollow(t : List[Symbol], fol : Set[Terminal]) : Set[Terminal] = {
    /*println("Calling computeFollow(" + t + "," + fol + ")");*/
    (first(t) dot new FirstSet(scala.collection.immutable.Set.empty[Terminal]++fol,false)).set
  }

  def apply(nt : Nonterminal) : Set[Terminal] = ntfol(nt);

}
