/* Dominator computation from Langauer and Tarjan (TOPLAS 1:1)
 * John Boyland
 * This file may be used, copied and/or modified for any purpose.
 */
package edu.uwm.cs.util;

import scala.collection.Set;
import scala.collection.immutable.ListSet;

/**
 * Dominator computation of graph represented by vertices 1..max
 * (Not 0 .. (max-1) !).  The result returns the immediate dominator of any 
 * node or 0 for a node without any dominator.
 */
class Dominator(max : Int, val succ : PartialFunction[Int,Set[Int]], root : Int)
  extends PartialFunction[Int,Int]
{
  private val parent : Array[Int] = new Array(max+1);
  private val ancestor : Array[Int] = new Array(max+1);
  private val child : Array[Int] = new Array(max+1);
  private val vertex : Array[Int] = new Array(max+1);
  private val label : Array[Int] = new Array(max+1);
  private val semi : Array[Int] = new Array(max+1);
  private val size : Array[Int] = new Array(max+1);
  private val dom : Array[Int] = new Array(max+1);

  private val pred : Array[ListSet[Int]] = new Array(max+1);
  private val bucket : Array[ListSet[Int]] = new Array(max+1);

  var n : Int = 0; // NB: In paper, n is also used for "max"

  private def dfs(v : Int) : Unit = {
    n += 1;
    semi(v) = n;
    label(v) = v;
    vertex(n) = v;
    ancestor(v) = 0;
    child(v) = 0;
    size(v) = 1;
    for (w <- succ(v)) {
      if (semi(w) == 0) {
	parent(w) = v; dfs(w)
      }
      pred(w) += v;
    }
  }

  private def compress(v : Int) : Unit = {
    if (ancestor(ancestor(v)) != 0) {
      compress(ancestor(v));
      if (semi(label(ancestor(v))) < semi(label(v))) {
	label(v) = label(ancestor(v));
      }
      ancestor(v) = ancestor(ancestor(v));
    }
  }

  private def evalX(v : Int) : Int = {
    if (ancestor(v) == 0) {
      v;
    } else {
      compress(v);
      label(v);
    }
  }

  private def eval(v : Int) : Int = {
    if (ancestor(v) == 0) {
      label(v)
    } else {
      compress(v);
      if (semi(label(ancestor(v))) >= semi(label(v))) {
	label(v)
      } else {
	label(ancestor(v));
      }
    }
  }

  private def linkX(v : Int, w : Int) = {
    ancestor(w) = v;
  }

  private def link(v : Int, w : Int) = {
    var s : Int = w;
    while (semi(label(w)) < semi(label(child(s)))) {
      if (size(s) + size(child(child(s))) >= 2 * size(child(s))) {
	ancestor(child(s)) = s;
	child(s) = child(child(s));
      } else {
	size(child(s)) = size(s);
	ancestor(s) = child(s);
	s = ancestor(s);
      }
    }
    label(s) = label(w);
    size(v) = size(v) + size(w);
    if (size(v) < 2 * size(w)) {
      val tmp = child(v);
      child(v) = s;
      s = tmp;
    }
    while (s != 0) {
      ancestor(s) = v;
      s = child(s)
    }
  }

  // Step 1:
  {
    for (v <- 1 to max) {
      pred(v) = ListSet.empty;
      bucket(v) = ListSet.empty;
      semi(v) = 0;
    }
    n = 0;
    dfs(root);
    if (n != max) {
      throw new Exception("Assertion failed");
    }
    size(0) = 0;
    label(0) = 0;
    semi(0) = 0;
    for (i <- n to 2 by -1) { // NB: vertex(1) = root always
      val w : Int = vertex(i);

      // step 2
      for (v <- pred(w)) {
	val u : Int = eval(v);
	if (semi(u) < semi(w)) {
	  semi(w) = semi(u);
	}
      }
      bucket(vertex(semi(w))) += w;
      link(parent(w),w)

      // step 3
      val bucketcopy : Set[Int] = bucket(parent(w));
      bucket(parent(w)) = ListSet.empty;
      for (v <- bucketcopy) {
	val u : Int = eval(v);
	dom(v) = {
	  if (semi(u) < semi(v)) u else parent(w)
	}
      }
    }

    // step 4
    for (i <- 2 to n) {
      val w : Int = vertex(i);
      if (dom(w) != vertex(semi(w))) {
	dom(w) = dom(dom(w))
      }
    }
    dom(root) = 0;
  }

  override def isDefinedAt(n : Int) = n >= 1 && n <= max;

  override def apply(n : Int) = dom(n);

  def dominates(i : Int, j : Int) : Boolean = {
    j != 0 && (i == j || dominates(i,apply(j)))
  }
}

object TestDominator extends App {
  val e : ListSet[Int] = ListSet.empty;
  val d : Dominator = 
    new Dominator(13, { i => i match {
      case 13 => e + 1 + 2 + 3
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
      case 11 => e + 9+13
      case 12 => e + 8
    }}, 13);

  def name(i:Int) : String = {
    if (i == 0) "<none>";
    else ((i - 1 + 'A') toChar) + "";
  }

  {
    for (i <- 1 to 13) {
      println(name(i) + " is dominated by " + name(d(i)));
    }
  }
}
				    
