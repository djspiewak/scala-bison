/* LALR(1) Parser generation for Scala
 * John Boyland
 * This file may be used, copied and/or modified for any purpose.
 */
package edu.uwm.cs.cool.meta.parser;

case class VariableCode(i : Int) extends Code { 
  override def toString : String = {
    if (i == -1) "yyresult";
    else "yyarg" + i;
  }
}
