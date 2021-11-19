/* Determining which nodes are in cycles in a graph.
 * John Boyland
 * This file may be used, copied and/or modified for any purpose.
 */
package edu.uwm.cs.util;

import scala.collection.Set;
import scala.collection.immutable.ListSet;
import scala.collection.mutable.BitSet;

/**
 * Reaching computation of graph represented by vertices 1..max
 * (Not 0 .. (max-1) !).  The result returns all nodes reachable
 * from the given node.
 */
class Reach(max : Int, val succ : PartialFunction[Int,Set[Int]])
  extends PartialFunction[Int,Set[Int]]
{
  private val reachable : Array[BitSet] = new Array(max+1);

  {
    for (i <- 1 to max) {
      reachable(i) = new BitSet(max+1);
      for (j <- succ(i)) {
	reachable(i) += j
	if (j < i) reachable(i) ++= reachable(j);
      }
    }
    var done : Boolean = false;
    while (!done) {
      done = true;
      for (i <- 1 to max) {
	val is : BitSet = reachable(i);
	for (j <- is.clone) {
	  val js : BitSet = reachable(j);
	  if (!(js subsetOf is)) {
	    is ++= js;
	    done = false;
	  }
	}
      }
    }
  }

  override def isDefinedAt(n : Int) = n >= 1 && n <= max;

  override def apply(n : Int) = reachable(n);
}

object TestReach extends App {
  val e : ListSet[Int] = ListSet.empty;
  val c : Reach = 
    new Reach(13, { i => i match {
      case 13 => e + 1 + 2 + 3 + 13 // The +13 is new to this test
      case 1 => e + 4
      case 2 => e + 4 + 1 + 5
      case 3 => e + 7+6
      case 4 => e + 12
      case 5 => e + 8
      case 6 => e + 9
      case 7 => e + 9+10
      case 8 => e + 5+11
      case 9 => e + 11
      case 10 => e + 9
      case 11 => e + 9 // formerly + 13
      case 12 => e + 8
    }});

  def name(i:Int) : String = {
    if (i == 0) "<none>";
    else ((i - 1 + 'A').toChar) + "";
  }

  {
    for (i <- 1 to 13) {
      print(name(i) + " reaches");
      for (j <- c(i)) {
	print(" "+name(j));
      }
      println();
    }
  }
}
				    
