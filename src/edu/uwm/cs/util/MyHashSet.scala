package edu.uwm.cs.util

import scala.collection.mutable.HashSet

class MyHashSet[T] extends HashSet[T] {

  def getEntry(x : T) : Option[T] = super.findEntry(x);
}