/* A set that is a range of integers
 * John Boyland
 * This file may be used, copied and/or modified for any purpose.
 */
package edu.uwm.cs.util;

import scala.collection.Set;

/**
 * A Set defined over a range.
 */
class RangeSet(val range : Range) extends Set[Int]
{
  override def elements = range.elements;
  override def contains(x : Int) = range.contains(x);
  override def size = range.size;
  override def -(elem : Int) : Set[Int] = throw new UnsupportedOperationException("RangeSet#-");
  override def +(elem : Int) : Set[Int] = throw new UnsupportedOperationException("RangeSet#+");
  override def iterator : Iterator[Int] = range.iterator;
}

object TestRangeSet extends Application {
  val x : RangeSet = new RangeSet(1 to 10 by 2);
  println("(1,3,5,7,9) == " + x);
  println("x.size = " + x.size + " (should be 5)");
  println("x.contains(3) = " + x.contains(3) + " (should be true)");
  println("x.contains(4) = " + x.contains(4) + " (should be false)");
}
				    
