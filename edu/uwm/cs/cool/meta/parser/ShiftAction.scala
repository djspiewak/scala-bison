/* LALR(1) Parser generation for Scala
 * John Boyland
 * This file may be used, copied and/or modified for any purpose.
 */
package edu.uwm.cs.cool.meta.parser;

case class ShiftAction(target : State) extends Action {
  override def toString = "shift, and go to " + (target.number)
}
