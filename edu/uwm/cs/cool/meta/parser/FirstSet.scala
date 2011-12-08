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
