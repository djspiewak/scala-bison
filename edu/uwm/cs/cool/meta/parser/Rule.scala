/* LALR(1) Parser generation for Scala
 * John Boyland
 * This file may be used, copied and/or modified for any purpose.
 */
package edu.uwm.cs.cool.meta.parser;

import scala.collection.Set;

class Rule(val number : Int, val lhs : Nonterminal, val rhs : List[Symbol], 
	   val precedence : Precedence, val action : Code)
{
  {
    lhs.addRule(this);
  }

  def this(number : Int, lhs:Nonterminal, rhs:List[Symbol], 
	   precedence : Precedence, action:Code, 
	   ignored : Boolean) = this(number,lhs,rhs reverse,precedence,action);

  def length : Int = rhs.length;

  var recognitionPoint : Int = rhs.length;

  def getRecognitionPoint() = recognitionPoint;

  def setRecognitionPoint(nonfree : Set[Item]) : Unit = {
    recognitionPoint = 0; // optimistic
    for (i <- 0 until length) {
      if (precedence != null || // precedence forces using LALR parsing
	  (nonfree contains new Item(this,i)) || 
	  (rhs(i).isInstanceOf[ErrorNonterminal])) {
	recognitionPoint = i+1; // oops! must be later than we hoped!
      }
    }
    // println("Recognition Point is " + new Item(this,recognitionPoint));
  }

  override def toString = {
    val sb : StringBuffer = new StringBuffer(lhs.name);
    sb.append(':');
    if (rhs.isEmpty) sb.append(" /* empty */");
    else {
      for (sym <- rhs) {
	sb.append(' ');
	sb.append(sym.name);
      }
    }
    sb toString
  }
}
