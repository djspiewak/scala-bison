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
import scala.collection.mutable.{HashMap,HashSet,ArrayBuffer};
import scala.io.Source;

/**
 * LeftCorner parser tables built from LALR(1) Parse tables
 */
class LeftCornerTable(val table : Table) {
  protected val _states : HashSet[LeftCornerState] = new HashSet;
  def states : Set[LeftCornerState] = _states;
  private var _startState : LeftCornerState = null;
  def startState : LeftCornerState = _startState;

  val NTstate : HashMap[Nonterminal,LeftCornerState] = new HashMap;

  def grammar : Grammar = table.grammar;

  override def toString : String = toString(true);

  def toString(showAll : Boolean) : String = {
    val sb : StringBuilder = new StringBuilder();
    val a : Array[LeftCornerState] = new Array(states.size);
    for (state <- states) {
      a(state.number) = state;
    }
    for (state <- a) {
      sb append (state.toString(showAll))
    }
    sb toString
  }

  val first : First = new First(grammar);
  val follow : Follow = new Follow(grammar,first);

  {
    table.setRecognitionPoints();
  }

  val lcfollow : LeftCornerFollow = new LeftCornerFollow(grammar,first,follow);
  // compute the states:
  {
    // get the start state
    _startState = new LeftCornerState(0,grammar.start,table.states(0));
    _states += startState;
    startState.init(first,lcfollow,_states);
    NTstate.put(grammar.start,startState);
    //  for all rules, make sure we have NT states:
    for (rule <- grammar.rules) {
      var n : Int = rule.recognitionPoint; // NB: n is changed in loop
      for (symbol <- rule.rhs.drop(n)) {
        symbol match {
        case _:ArtificialNonterminal => ()
        case nt:Nonterminal => {
          if (!(NTstate isDefinedAt nt)) {
            NTstate.put(nt,makeNTstate(nt,new Item(rule,n)));
          }
        }
        case _ => ()
        }
        n += 1;
      }
    }
    for (symbol <- grammar.all_symbols) {
      symbol match {
      case _:ArtificialNonterminal => ()
      case nt:Nonterminal => {
        if (Options.verbose > 1 && !(NTstate isDefinedAt nt)) {
          println("Warning: Nonterminal " + nt.name + 
          " never used in unambiguous context.");
        }
      }
      case _ => ()
      }
    }
  }

  protected def makeNTstate(nt : Nonterminal, item : Item) : LeftCornerState = {
    // println("Building NT state for " + nt.name);
    for (state <- table.states) {
      if (state.completion contains item) {
        // println("Found state " + state.number + " to include " + item);
        val newState : LeftCornerState = 
          new LeftCornerState(_states.size,nt,state);
        _states += (newState);
        newState.init(first,lcfollow,_states);
        return newState;
      }
    }
    throw new GrammarSpecificationError("No state found for item " + item);
  }
}

object LeftCornerTable {
  def main(args : Array[String]) = {
    for (s <- args) {
      val scanner : BisonScanner = new BisonScanner(Source.fromFile(s+".y"))
      val parser : BisonParser = new BisonParser();
      parser.yydebug = true;
      parser.reset(s+".y",scanner);
      if (parser.yyparse()) {
        println(parser.result);
        val table : BisonTable = new BisonTable(parser.result);
        table.fromFile(s+".output");
        val table2 : LeftCornerTable = new LeftCornerTable(table);
        println(table2);
      }
    }
  }
}
