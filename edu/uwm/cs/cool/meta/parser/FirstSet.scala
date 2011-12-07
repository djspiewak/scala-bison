/* LALR(1) Parser generation for Scala
 * John Boyland
 * This file may be used, copied and/or modified for any purpose.
 */
package edu.uwm.cs.cool.meta.parser;

import scala.collection.immutable.Set;
import scala.collection.immutable.ListSet;

class FirstSet(val set : Set[Terminal], val incEpsilon : Boolean) {
  def dot(other : => FirstSet) : FirstSet = {
    if (incEpsilon) new FirstSet(set ++ other.set, other.incEpsilon);
    else this;
  }
  def ++(other : FirstSet) : FirstSet = {
    if (incEpsilon) new FirstSet(set ++ other.set, true);
    else new FirstSet(set ++ other.set, other.incEpsilon);
  }
  override def equals(x : Any) = {
    x match {
      case o:FirstSet => set.equals(o.set) && incEpsilon == o.incEpsilon
      case _ => false
    }
  }
  override def hashCode() : Int = set.hashCode() + incEpsilon.hashCode();

  override def toString : String = {
    if (incEpsilon) set.toString + " + e"
    else set.toString
  }
}

object FirstSet {
  val empty : FirstSet = new FirstSet(ListSet.empty,false);
  val epsilon : FirstSet = new FirstSet(ListSet.empty,true);
}
