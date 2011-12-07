/* LALR(1) Parser generation for Scala
 * John Boyland
 * This file may be used, copied and/or modified for any purpose.
 */
package edu.uwm.cs.cool.meta.parser;

import scala.collection.Set;
import scala.collection.immutable.ListSet;
import scala.collection.mutable.{ArrayBuffer,HashSet};

class Nonterminal(s : String, t : String) extends Symbol(s,t) {
  val rules : ArrayBuffer[Rule] = new ArrayBuffer();

  def addRule(r : Rule) : Unit = rules += r;
}
