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
