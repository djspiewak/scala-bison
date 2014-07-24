/*
 * Copyright 2011 University of Wisconsin, Milwaukee
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.uwm.cs.scalabison;

import scala.collection.immutable.Set;

class Item(val rule : Rule, val index : Int)  {

  def symbol : Symbol = {
    if (index == rule.length) null;
    else rule.rhs(index);
  }

  def next() : Item = new Item(rule,index+1);

  /** Ready for recognition */
  def ready : Boolean = index == rule.getRecognitionPoint();

  override def equals(o : Any) : Boolean = {
    o match {
      case i:Item => rule == i.rule && index == i.index;
      case _ => false
    }
  }

  override def hashCode() : Int = rule.hashCode() + index;

  override def toString : String = {
    val sb : StringBuilder = new StringBuilder( rule.lhs.name );
    sb append ":";
    var i : Int = 0;
    for (symbol <- rule.rhs) {
      if (index == i) {
	sb append " .";
      }
      sb append ' '
      sb append symbol.name
      i += 1
    }
    if (index == i) {
      sb append " .";
    }
    sb.toString
  }

}

object Item {
  def fromLine(grammar : Grammar, line : String) : Item = {
    val rulenum : Int = Integer.parseInt(line.substring(0,5).trim);
    val rule : Rule = grammar.rules(rulenum);
    val itemtext : String = line.substring(6).trim;
    val parts : Array[String] = itemtext.split(" ");
    if (!parts(0).equals("|") && !parts(0).equals(rule.lhs.name + ":")) {
      throw new GrammarSpecificationError("Expected rule " + rulenum + 
					  " to have lhs = " + parts(0) + 
					  " not " + rule.lhs.name);
    }
    for (i <- 1 until (parts.length)) {
      if (parts(i).equals(".")) {
	val item : Item = new Item(rule,i-1);
	/*
	if (!itemtext.equals(item.toString())) {
	  throw new GrammarSpecificationError("sanity check failed: " + 
					      item + " != " + itemtext);
	}
	*/
	return item
      }
    }
    throw new GrammarSpecificationError("Couldn't find . in " + itemtext);
  }
}
