/* LALR(1) Parser generation for Scala
 * John Boyland
 * This file may be used, copied and/or modified for any purpose.
 */
package edu.uwm.cs.cool.meta.parser;

import scala.collection.Set;

abstract class Symbol(val name : String, val resultType : String) {
  def getType() : String = if (typed) resultType else "Unit";
  def typed : Boolean = (!resultType.equals(""))
  var precedence : Precedence = null;
  def setPrecedence(p : Precedence) = {
    if (precedence == null) {
      precedence = p;
    } else {
      throw new GrammarSpecificationError("precedence for " + name + " already specified");
    }
  }

  override def toString = name;
}
