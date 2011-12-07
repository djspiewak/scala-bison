/* LALR(1) Parser generation for Scala
 * John Boyland
 * This file may be used, copied and/or modified for any purpose.
 */
package edu.uwm.cs.cool.meta.parser;

import scala.collection.mutable.ArrayBuffer;
import scala.collection.Set;
import scala.collection.mutable.HashSet;

/**
 * LALR(1) Parse tables
 */
class Table(val grammar : Grammar) {
  val states : ArrayBuffer[State] = new ArrayBuffer;

  override def toString : String = toString(true);

  def toString(showAll : Boolean) : String = {
    val sb : StringBuffer = new StringBuffer();
    for (state <- states) {
      sb append (state.toString(showAll))
    }
    sb toString
  }

  private var nonfree : HashSet[Item] = null;

  def getNonFree() : Set[Item] = {
    if (nonfree != null) return nonfree;
    nonfree = new HashSet;
    for (state <- states) {
      // println("Getting nonfree for " + state);
      state.addNonFree(nonfree);
    }
    nonfree
  }

  def setRecognitionPoints() : Unit = {
    val nonfree : Set[Item] = getNonFree();
    for (rule <- grammar.rules) {
      rule.setRecognitionPoint(nonfree);
    }
  }

  
}
