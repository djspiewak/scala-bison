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
    sb.toString
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
