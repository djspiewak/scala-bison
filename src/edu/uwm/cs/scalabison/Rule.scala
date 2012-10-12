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
          (rhs(i) == ErrorNonterminal())) {
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
