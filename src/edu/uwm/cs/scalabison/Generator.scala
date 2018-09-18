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

import scala.io.Source
import java.io._
import scala.collection.Set
import scala.collection.immutable.ListSet
import scala.collection.mutable._
import edu.uwm.cs.util.CharUtil

/** Generate recursive-ascent-descent parser for the given tables. 
 * This technique is inspired by Nigel Horspool's paper
 * on the same topic with a number of sloppy shortcuts.
 * Any errors are strictly my own (John Boyland).
 */
class Generator(prefix : String, table : BisonTable)
{
  val lctable : LeftCornerTable = new LeftCornerTable(table);

  def writeFiles() = {
    if (Options.verbose > 0) writeOutput();
    writeTokens();
    writeParser();
  }

  def writeOutput() : Unit = {
    var fw : Writer = new FileWriter(prefix + ".lcoutput")
    val pw : PrintWriter = new PrintWriter(fw);
    pw.println("Recognition points");
    pw.println();
    pw.println(table.grammar.toString(true));
    pw.println();
    pw.println(lctable.toString(true)); // was false
    pw.close();
  }

  def writeTokens() : Unit = {
    var fw : Writer = new FileWriter(prefix + "Tokens.scala")
    val pw : PrintWriter = new PrintWriter(fw);

    pw.println(table.bison.prologue);
    pw.println("object " + prefix + "Tokens {");
    pw.println("  class YYSymbol;")
    pw.println();
    writeTerminals(pw);
    pw.println("}");
    pw.close();
  }

  def writeParser() : Unit = {
    var fw : Writer = new FileWriter(prefix + "Parser.scala")
    val pw : PrintWriter = new PrintWriter(fw);

    pw.println(table.bison.prologue);
    pw.println("/** Generated LALR(1) recursive-ascent-descent parser */");
    pw.println("class " + prefix + "Parser extends " + prefix + "ParserBase {");
    writeNonterminals(pw);
    pw.println();
    /*
    pw.println("  private class YYGoto;");
    pw.println("  private case class YYBase(yy : YYNonterminal) extends YYGoto");
    pw.println("  private case class YYNested(yy : YYGoto) extends YYGoto");
    */
    pw.println("  private var yynt : YYNonterminal = null;");
    pw.println();
    pw.println("  case class YYError(s:String) extends Exception(s);");
    pw.println();
    pw.println("  // boilerplate");
    pw.println();
    pw.println("  var yydebug : Boolean = false;");
    pw.println("  private var yyinput : Iterator["+prefix+"Tokens.YYToken] = null;");
    pw.println("  private var yycur : "+prefix+"Tokens.YYToken = null;");
    pw.println();
    pw.println("  private def yynext() = {");
    pw.println("    yycur = {");
    pw.println("      if (yyinput.hasNext) {");
    pw.println("        yyinput.next");
    pw.println("      } else {");
    pw.println("        "+prefix+"Tokens.YYEOF();");
    pw.println("      }");
    pw.println("    }");
    if (Options.debug)
      pw.println("    if (yydebug) println(\"Current token now is \" + yycur);");
    pw.println("  }");
    pw.println("");
    pw.println("  private def yypanic(test : ("+prefix+"Tokens.YYToken) => Boolean) = {");
    pw.println("    while (!test(yycur)) {");
    if (Options.debug)
      pw.println("      if (yydebug) println(\"Discarding current token\");");
    pw.println("      yynext;");
    pw.println("      if (yycur == "+prefix+"Tokens.YYEOF()) throw new YYError(\"Giving up\")");
    pw.println("    }");
    pw.println("  }");
    pw.println("");
    pw.println("  def yyreset(input : Iterator["+prefix+"Tokens.YYToken]) = {");
    pw.println("    yyinput = input;");
    pw.println("    yynt = null;"); // No YYGoto
    pw.println("    yynext");
    pw.println("  }");
    pw.println("");
    pw.println("  def yyparse() : Boolean = {");
    pw.println("    try {");
    pw.println("      parse_"+table.grammar.start.name+"()");
    pw.println("      parse_YYEOF()");
    pw.println("      true");
    pw.println("    } catch {");
    pw.println("      case YYError(s) => yyerror(s); false");
    pw.println("    }");
    pw.println("  }");
    pw.println("");
    /*
    pw.println("  private def yynest(nx:Int,g: YYNonterminal) : YYGoto = {");
    //pw.println("    try {");
    pw.println("      var n : Int = nx;");
    pw.println("      var yygoto : YYGoto = YYBase(g)");
    pw.println("      while (n > 0) {");
    pw.println("        yygoto = YYNested(yygoto)");
    pw.println("        n -= 1");
    pw.println("      }");
    pw.println("      yygoto");
    //pw.println("    } catch {");
    //pw.println("      case YYError(s) => YYBase(YYNTerror(s))");
    //pw.println("    }");
    pw.println("  }");
    pw.println("");
     */
    pw.println("  def parse_YYCHAR(yy:Char) : Unit = {");
    pw.println("    yycur match {");
    pw.println("      case " + prefix +"Tokens.YYCHAR(`yy`) => yynext; ()");
    pw.println("      case _ => throw new YYError(\"Expected '\"+yy+\"'\");");
    pw.println("    }");
    pw.println("  }");
    pw.println("");
    pw.println("  // generated parser");
    pw.println();
    for (sym <- table.grammar.all_symbols) {
      sym match {
      case _:CharLitTerminal => ()
      case t:Terminal => {
        pw.println("  def parse_" + t.name + "() : " + t.getType() + " = {")
        pw.println("    yycur match {");
        pw.print("      case " + prefix + "Tokens." + t.name);
        if (t.typed) {
          pw.println("(yy) => yynext; yy");
        } else {
          pw.println("() => yynext; ()");
        }
        pw.println("      case _ => throw new YYError(\"Expected '" + t.name + "'\");");
        pw.println("    }");
        pw.println("  }\n");
      }
      case _ => ()
      }
    }

    for ((nt,state) <- lctable.NTstate) {
      pw.print("  def parse_" + code(nt) + "()")
      pw.print(" : " + nt.getType)
      pw.println(" = {");
      if (Options.debug)
        pw.println("    if (yydebug) println(\"Parsing "+nt.name+"\");");
      /* YYGoto:
      pw.println("    yystate" + state.number + "() match {");
      pw.println("      case YYBase(YYNT" + (code(nt)) + "(yy)) => yy");
      pw.println("      case YYBase(YYNTerror(s)) => throw new YYError(s)");
      if (Options.debug)
	pw.println("      case _ => throw new YYError(\"internal parser error\")");
      pw.println("    }");
       */
      if (Options.debug) {
        pw.println("    if (yystate" + state.number + "() != 0)");
        pw.println("      throw new YYError(\"internal parse error\");");
      } else {
        pw.println("    yystate" + state.number + "();");
      }
      pw.println("    yynt match {");
      if (nt.typed) {
        pw.println("      case YYNT" + (code(nt)) + "(yy) => yy");
      } else {
        pw.println("      case YYNT" + (code(nt)) + "() => ()");
      }
      pw.println("      case YYNTerror(s) => throw new YYError(s)");
      if (Options.debug)
        pw.println("      case _ => throw new YYError(\"internal parser error\")");
      pw.println("    }");
      /* end removal of YYGoto */
      pw.println("  }\n");
    }

    for (rule <- table.grammar.rules) {
      writeRule(pw,rule);
    }

    for ((_,state) <- lctable.NTstate) {
      writeState(pw,state);
    }
    pw.println(table.bison.extra);
    
    if (Options.gen_lalr_table) {
      writeLALRTable(pw);
    }
    pw.println("}");
    pw.close();
  }

  private def writeTerminals(pw : PrintWriter) = {
    pw.println("  class YYToken extends YYSymbol;\n");
    pw.println("  case class YYCHAR(yy: Char) extends YYToken {");
    pw.println("    override def toString() : String = \"'\" + yy + \"'\";");
    pw.println("  }");
    for (symbol <- table.grammar.all_symbols) {
      symbol match {
      case _:CharLitTerminal => ()
      case t:Terminal => {
        pw.print("  case class " + t.name + "(");
        if (t.typed) {
          pw.print("yy: " + t.getType())
        }
        pw.println(") extends YYToken;");
      }
      case _ => ()
      }
    }
  }

  private def writeNonterminals(pw : PrintWriter) = {
    pw.println("  class YYNonterminal extends " + prefix + "Tokens.YYSymbol;\n");
    pw.println("  case class YYNTerror(yy : String) extends YYNonterminal;");
    for (symbol <- table.grammar.all_symbols) {
      symbol match {
      case _:ArtificialNonterminal => ()
      case nt:Nonterminal => {
        pw.print("  case class YYNT" + code(nt) + "(");
        if (nt.typed) {
          pw.print("yy: " + nt.getType())
        }
        pw.println(") extends YYNonterminal;");
      }
      case _ => ()
      }
    }
    if (Options.gen_lalr_table) {
      pw.println("  case class YYNT(num : Int) extends YYNonterminal;");
    }
  }

  private val usedAfterRecognition : Map[Symbol,Item] = new HashMap();

  private def writeRule(pw: PrintWriter, rule : Rule) : Unit = {
      if (rule.lhs.isInstanceOf[ArtificialNonterminal]) return;
      // write the rescurive descent part after the recognition point
      pw.println();
      pw.println("  /** Recursive descent parser after recognition point");
      pw.println("   * " + new Item(rule,rule.recognitionPoint))
      pw.println("   */");
      pw.print("  private def yyrule" + rule.number + "(");
      var noargs : Boolean = true;
      var i : Int = 0;
      var recognized : Boolean = false;
      for (symbol <- rule.rhs) {
        if (i == rule.recognitionPoint) {
          finishRuleHeader(pw,rule);
          recognized = true;
        }
        i += 1;
        if (recognized) {
          pw.print("    ");
          symbol match {
          case CharLitTerminal(ch) => {
            pw.println("parse_YYCHAR("+ toLit(ch) +");");
          }
          case nt:ArtificialNonterminal => {
            if (nt.name.charAt(0) == '@') {
              pw.println(nt.rules(0).action);
            } else {
              sys.error("Unknown artificial nonterminal " + nt);
            }
          }
          case _ => {
            usedAfterRecognition.put(symbol,new Item(rule,i-1));
            if (symbol.typed) {
              pw.print("val yyarg" + i + " : " + symbol.getType() + " = ");
            }
            pw.println("parse_" + code(symbol) + "();");
          }
          }
        } else {
          // not yet recognized
          if (symbol.typed) {
            if (noargs) {
              noargs = false;
            } else {
              pw.print(", ");
            }
            pw.print("yyarg" + i + " : " + symbol.getType());
          }
        }
      }
      if (!recognized) finishRuleHeader(pw,rule);
      rule.action match {
      case NoCode() => {
        if (rule.lhs.typed) {
          pw.println("    yyresult = yyarg1;");
        }
      }
      case _ => {
        pw.println("    " + rule.action);
      }
      }
      if (rule.lhs.typed) {
        pw.println("    yyresult");
      }
      pw.println("  }");
  }

  private def finishRuleHeader(pw : PrintWriter, rule : Rule) = {
    pw.print(") ");
    if (rule.lhs.typed) {
      pw.println(": " + rule.lhs.getType() + " = {");
      pw.println("    var yyresult : " + rule.lhs.getType() + 
          " = " + defaultInitial(rule.lhs.getType()) + ";");
    } else {
      pw.println(": Unit = {");
    }
    if (Options.debug)
      pw.println("    if (yydebug) println(\"Announcing rule "+rule.number+"\");");
  }

  private def defaultInitial(t : String) : String = {
    t match {
    case "Boolean" => "false";
    case "Int" => "0";
    case "Double" => "0";
    case "Float" => "0";
    case "Char" => "0";
    case "Byte" => "0";
    case "Short" => "0";
    case _ => "null";
    }
  }

  private def toLit(ch : Char) : String = CharUtil.lit(ch);

  private def code(s : Symbol) : String = s.name;

  private val stateWritten : HashSet[Int] = new HashSet;

  private def writeState(pw : PrintWriter, state : LeftCornerState) : Unit = {
      if (stateWritten contains (state.number)) return;
      stateWritten += state.number;
      // println("Writing state " + state.number);

      // we cache everything into a string builder first,
      // to avoid intermixing states
      val sb : StringBuilder = new StringBuilder();

      // the "longest" item
      val longest : Item = state.longestItem;
      var nextIndex : Int = 1;
      if (longest != null) nextIndex = longest.index+1;
      val nextarg : String = "yyarg" + nextIndex;

      sb.append("\n  private def yystate" + state.number + "(");
      var noargs : Boolean = true;
      var i : Int = 0;
      if (longest != null) {
        for (symbol <- (longest.rule.rhs.slice(0,longest.index))) {
          i += 1;
          if (symbol.typed) {
            if (noargs) {
              noargs = false;
            } else {
              sb.append(", ");
            }
            sb.append("yyarg" + i + ": " + symbol.getType());
          }
        }
      } else if (state.nonterminal != null && !state.atStart) {
        // for fake item:
        if (state.nonterminal.typed) {
          sb.append("yyarg1: " + state.nonterminal.getType())
        }
      }
      /*YYGoto
    sb.append(") : YYGoto = {\n")
       */
      sb.append(") : Int = {\n") // No YYGoto
      if (Options.debug)
        sb append "    if (yydebug) println(\"Entering state "+ state.number+"\")\n";
      /* YYGoto
    sb append "    var yygoto : YYGoto = null;\n";
       */
      sb append "    var yygoto : Int = 0;\n"; // No YYGoto

      if (state.gotos contains (ErrorNonterminal())) {
        sb append "    try {\n";
      }

      sb append "    yycur match {\n";
      for ((t,action) <- state.actions) {
        sb append "      case ";
        sb append prefix
        sb append "Tokens."
        t match {
        case CharLitTerminal(ch) => {
          sb append "YYCHAR(" + toLit(ch) + ")";
        }
        case _ => {
          sb append (t.name);
          sb append '(';
          if (t.typed) {
            action match {
            case ShiftAction(_) => sb append nextarg 
            case _ => sb append "_"
            }
          }
          sb append ')'
        }
        }
        sb append " => ";
        action match {
        case AnnounceAction(r) => {
          appendRuleCall(sb,longest,r);
        }
        case AcceptNTAction(nt) => {
          appendAcceptNT(sb,nt);
        }
        case ShiftAction(s) => {
          sb append "yynext; ";
          writeState(pw,s.asInstanceOf[LeftCornerState]);
          appendGoto(sb,longest,s);
        }
        case ErrorAction(s) => {
          /* YYGoto
	  sb append "yygoto = YYBase(YYNTerror(\"";
	  sb append s
	  sb append "\"));\n"
           */
          sb append "yynt = YYNTerror(\"";
          sb append s
          sb append "\");\n"
        }
        }
      }
      sb append "      case _ => "
      state.default match {
      case null =>
      /* YYGoto
	sb append "yygoto = YYBase(YYNTerror(\"syntax error\"));\n";
       */
      sb append "yynt = YYNTerror(\"syntax error\");\n";
      case AnnounceAction(r) =>
      appendRuleCall(sb,longest,r)
      case AcceptNTAction(nt) =>
      appendAcceptNT(sb,nt);
      case AcceptAction() =>
      appendRuleCall(sb,longest,table.grammar.rules(0));
      }
      sb append "    }\n";

      if (state.gotos contains ErrorNonterminal()) {
        sb append "    } catch {\n";
        /* YYGoto
      sb append "      case YYError(s) => yygoto = YYBase(YYNTerror(s));\n";
         */
        sb append "      case YYError(s) => yynt = YYNTerror(s);\n";
        sb append "    }\n";
      }

      /* YYGoto:
    sb append "    while (true) {\n";
    sb append "      yygoto match {\n";
    sb append "        case YYNested(g) => return g;\n";
       */
      sb append "    while (yygoto == 0) {\n";
      if (state.gotos contains (ErrorNonterminal())) {
        sb append "      try {\n";
    }

    sb append "      yynt match {\n";

    for ((nt,next) <- state.gotos) {
      writeState(pw,next.asInstanceOf[LeftCornerState]);
      if (nt == ErrorNonterminal()) {
	/* YYGoto
	sb append "        case YYBase(YYNTerror(s)) => \n"
	*/
	sb append "        case YYNTerror(s) => \n"
	sb append "          if (yycur == "+prefix+"Tokens.YYEOF()) return 0;"
        sb append "          yyerror(s)\n"
	if (Options.debug)
	  sb append "          if (yydebug) println(\"Recovering in state "+ state.number+"\")\n";
	var panic : scala.collection.immutable.Set[Terminal] = 
	            scala.collection.immutable.Set.empty;
	for (item <- next.core) {
	  panic ++= lctable.follow(item);
	}	
	sb append "          yypanic({ t:" 
	sb append prefix
	sb append "Tokens.YYToken => t match {\n";
	for (t <- panic) {
	  sb append "            case ";
	  sb append prefix
	  sb append "Tokens.";
	  t match {
	    case CharLitTerminal(ch) => {
	      sb append "YYCHAR("
	      sb append toLit(ch)
	      sb append ")"
	    }
	    case _ => {
	      sb append (t.name)
	      sb append "("
	      if (t.typed) sb append "_"
	      sb append ")"
	    }
	  }
	  sb append " => true\n"
	}
	sb append "            case _ => false\n";
	sb append "          }})\n";
	sb append "          ";
      } else {
	/* YYGoto:
	sb append ("        case YYBase(YYNT" + code(nt) + "(");
	sb append nextarg;
	sb append ")) => ";
	*/
	sb append ("        case YYNT" + code(nt) + "(");
	if (nt.typed) {
	  sb append nextarg;
	}
	sb append ") => ";
      }
      appendGoto(sb,longest,next);
    }
    // The following won't happen any more
    if (state.nonterminal != null && 
	!(state.gotos isDefinedAt(state.nonterminal))) {
      /* YYGoto:
      sb append "        case x@YYBase(_:YYNT" + code(state.nonterminal) + 
	  ") => return x;\n";      
      */
      sb append "        case _:YYNT" + code(state.nonterminal) + 
	  " => return 0;\n";
    }
    if (!(state.gotos isDefinedAt ErrorNonterminal())) {
      /* YYYGoto:
      sb append "        case e@YYBase(_:YYNTerror) => return e\n";
      */
      sb append "        case _:YYNTerror => return 0;\n";
    }

    if (Options.debug) {
      /* YYGoto:
      sb append "        case _ => return YYBase(YYNTerror(\"internal parser error\"));\n";
      */
      sb append "        case _ => yynt = YYNTerror(\"internal parser error\"); return 0;\n";
    }
    sb append "      }\n";
    if (state.gotos contains ErrorNonterminal()) {
      sb append "      } catch {\n";
      /* YYGoto
      sb append "      case YYError(s) => yygoto = YYBase(YYNTerror(s));\n";
      */
      sb append "        case YYError(s) => yynt = YYNTerror(s);\n";
      sb append "      }\n";
    }
    sb append "    }\n";
    sb append "    yygoto-1\n"; // YYGoto omits "-1"
    sb append "  }\n";
    pw.append(sb.toString);
  }

  private def appendAcceptNT(sb : StringBuilder, nt : Nonterminal) : Unit = {
    /* YYGoto
     sb append "yygoto = YYNested(YYNested(YYBase(YYNT"+code(nt)+"(";
     if (nt.typed) sb.append("yyarg1");
     else sb.append("()");
     sb append "))));\n";
     */
    sb append "yynt = YYNT"+code(nt)+"(";
    if (nt.typed) {
      sb.append("yyarg1");
    }
    sb append "); yygoto = 2;\n"
  }

  private def appendRuleCall(sb : StringBuilder, 
			     longest : Item, 
			     rule : Rule) = {
    // YYGOTO: This routine must be rewritten to use yynest.
    val rp = rule.getRecognitionPoint;
    var li : Int = 0;
    if (longest != null) li = longest.index;
    if (rule.lhs.typed) {
      sb append "yynt = " // No YYGoto
      sb append "YYNT"
      sb append code(rule.lhs)			      
      sb append "(yyrule";
      sb append rule.number;
      sb append "(";
      appendActuals(sb,li-rp,rule.rhs.slice(0,rp))
      sb append ")); ";
    } else {
      sb append "yyrule";
      sb append rule.number;
      sb append "(";
      appendActuals(sb,li-rp,rule.rhs.slice(0,rp))
      sb append "); yynt = YYNT";
      sb append code(rule.lhs);
      sb append "(); ";
    }
    sb append "yygoto = " 
    sb append rp;
    sb append "\n";
  }

  private def appendGoto(sb : StringBuilder, longest : Item, target : State) = {
    val nextlongest = target.longestItem;
    var li : Int = 0;
    if (longest != null) li = longest.index;
    sb append "yygoto = yystate";
    sb append target.number;
    sb append "(";
    if (nextlongest != null) {
      appendActuals(sb,li-nextlongest.index+1,
		    nextlongest.rule.rhs.slice(0,nextlongest.index));
    } else if (target.asInstanceOf[LeftCornerState].nonterminal.typed) {
      sb append "yyarg1";
    }
    sb append ");\n";
  }

  private def appendActuals(sb : StringBuilder, 
			    offset : Int, 
			    syms :scala.collection.Seq[Symbol]) : Boolean = {
    var nonempty : Boolean = false;
    var i : Int = offset;
    for (sym <- syms) {
      i += 1;
      if (sym.typed) {
        if (nonempty) {
          sb append ',';
        } else {
          nonempty = true
        }
        sb append "yyarg";
        sb append i;
      }
    }
    nonempty;
  }
  
  private def writeLALRTable(pw : PrintWriter) {
    pw.print("""  abstract class YYAction;
  case class YYAshift(state : Int) extends YYAction;
  case class YYAreduce(rule : Int, nt:YYNonterminal, size : Int) extends YYAction;
  case class YYAerror(reason : String) extends YYAction;
  case class YYAaccept() extends YYAction;

  private var table : Array[Function[CoolTokens.YYSymbol,YYAction]] = Array(""");
      var started : Boolean = false;
      for (state <- table.states) {
        if (started) pw.print(","); else started = true;
        pw.println("\n    (x => x match {");
        for ((t,action) <- state.actions) {
          pw.print("      case " + prefix + "Tokens.");
          t match {
            case CharLitTerminal(ch) => pw.print("YYCHAR(" + toLit(ch) + ")");
            case _ => pw.print(t.name + (if (t.typed) "(_)" else "()"));
          }
          pw.print(" => ");
          writeAction(pw,action);
          pw.println();
        }
        for ((nt,st) <- state.gotos) {
          val nts = 
             if (nt.name.startsWith("@")) "(" + nt.name.substring(1) + ")"
             else if (nt.typed) nt.name + "(_)"
             else if (nt.name == "error") nt.name + "(_)"; 
             else nt.name + "()";
          pw.println("      case YYNT" + nts + " => YYAshift(" + st.number + ")");
        }
        pw.print("      case _ => ");
        var defAction : Action = state.default;
        if (defAction == null) defAction = ErrorAction("parse error");
        writeAction(pw,defAction);
        pw.print("})");
      }
      pw.println(");\n");
      pw.println("  val MAX_STATE = table.length-1;\n");
      pw.println("  def getAction(state : Int, s : " + prefix + "Tokens.YYSymbol) : YYAction = table(state)(s);");
  }
  
  private def writeAction(pw: PrintWriter, action: Action): Unit = {
    action match {
      case AcceptAction() => pw.print("YYAaccept()");
      case ErrorAction(reason) => pw.print("YYAerror(\"" + reason + "\")");
      case ShiftAction(target) => {
        val st = target.number;
        pw.print("YYAshift(" + st + ")");
      }
      case ReduceAction(rule) => {
        val nt = rule.lhs;
          val nts = 
             if (nt.name.startsWith("@")) "(" + nt.name.substring(1) + ")"
             else if (nt.getType() == "Boolean") nt.name + "(false)"
             else if (nt.getType() == "Int") nt.name + "(0)"
             else if (nt.typed) nt.name + "(null)"
             else nt.name + "()";
        pw.print("YYAreduce(" + rule.number + ", YYNT" + nts + ", " + rule.rhs.length + ")");
      }
    }
  }
}

object RunGenerator {
  def main(args : Array[String]) : Unit = {
    for (s <- args) {
      if (s == "-v" || s == "--verbose") {
        Options.verbose += 1;
      } else if (s == "-V" || s == "--version") {
        println("ScalaBison version " + Version.version);
        return;
      } else if (s == "-h" || s == "--help") {
        println("ScalaBison version " + Version.version);
        println("Generates LAXLC(1) parsers in Scala from bison input and output files.");
        println();
        println("Usage: scala-bison [OPTION]... GRAMMARFILE...\n");
        println("Options: ");
        println("  -v \n  --verbose");
        println("\tGenerate XXX.lcoutput file");
        println("  -V \n  --version");
        println("\tPrint version and exit");
        println("  -t \n  --debug");
        println("\tGenerate debugging code that can be turned on using yydebug.");
        println("  -h \n  --help");
        println("\tPrint this message, and exit.");
      } else if (s == "-t" || s == "--debug") {
        Options.debug = true;
      } else if (s == "-T" || s == "--meta-debug") {
        Options.meta_debug = true;
      } else if (s == "-d" || s == "--lalr-table") {
        Options.gen_lalr_table = true;
      } else if (s.startsWith("-")) {
        println("Unknown option " + s + "; use --help to get legal options");
        System.exit(-1);
      } else {
        try {
          val scanner : BisonScanner = new BisonScanner(Source.fromFile(s))
          val parser : BisonParser = new BisonParser();
          val sep : Int = s.lastIndexOf(File.separatorChar);
          val filename = if (sep < 0) s; else s.substring(sep+1);
          val dot : Int = filename.lastIndexOf('.');
          val prefix : String = 
            if (dot < 0) filename; else filename.substring(0,dot);
          if (Options.meta_debug) {
            println("Parsing " + filename);
          }
          parser.reset(s,scanner);
          if (Options.meta_debug) {
            parser.yydebug = true;
          }
          if (parser.yyparse()) {
            // println(parser.result);
            val table : BisonTable = new BisonTable(parser.result);
            table.fromFile(prefix+".output");
            // println(table);
            if (table.states.isEmpty) {
              println("Error: didn't find any states in the bison outfile file.\nPerhaps this is a bison version SCalaBison doesn't understand.");
              System.exit(-1);
            }
            val gen : Generator = new Generator(prefix,table);
            gen.writeFiles();
          } else {
            println("Could not parse "+filename);
            System.exit(-1);
          }
        } catch {
        case e:java.io.IOException => {
          println("Problem with input/output files: " + e);
          System.exit(-1);
        }
        }
      }
    }
  }
}
