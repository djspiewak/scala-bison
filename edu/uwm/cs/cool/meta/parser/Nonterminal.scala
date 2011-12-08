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

package edu.uwm.cs.cool.meta.parser;

import scala.collection.Set;
import scala.collection.immutable.ListSet;
import scala.collection.mutable.{ArrayBuffer,HashSet};

class Nonterminal(s : String, t : String) extends Symbol(s,t) {
  val rules : ArrayBuffer[Rule] = new ArrayBuffer();

  def addRule(r : Rule) : Unit = rules += r;
}
