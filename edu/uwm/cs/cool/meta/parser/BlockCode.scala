/* LALR(1) Parser generation for Scala
 * John Boyland
 * This file may be used, copied and/or modified for any purpose.
 */
package edu.uwm.cs.cool.meta.parser;

case class BlockCode(cl : List[Code]) extends Code { 
  override def toString : String = {
    "{" + (cl foldRight "}")((c:Code,s:String) => (c toString) + s);
  }
}
