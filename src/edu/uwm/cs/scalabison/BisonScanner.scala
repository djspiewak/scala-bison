/* Scanner for Scala-Bison files
 * John Boyland
 * This file may be used, copied and/or modified for any purpose.
 * Eventually it might be nice to have this file generated.
 */
package edu.uwm.cs.scalabison;

import scala.collection.Set;
import scala.io.Source;

class BisonScanner(input : Iterator[Char]) 
	extends Iterator[BisonTokens.YYToken]
{ 
  private val NOCURRENT : Int = -1;
  private var current : Int = NOCURRENT;
  private var linenumber : Int = 1;

  def getLineNumber : Int = linenumber;

  private def headInput : Char = {
    if (current == NOCURRENT) {
      if (!input.hasNext) {
	throw new GrammarSpecificationError("unexpected EOF");
      }
      current = (input.next).toInt
    }
    current.toChar
  }
  private def nextInput : Unit = {
    if (current == '\n') linenumber += 1;
    (current = NOCURRENT);
  }
  private def pushInput(chx : Char) : Unit = {
    var ch : Char = chx
    if (ch == '\n') ch = ' ';
    if (current == NOCURRENT) {
      current = ch;
    } else {
      throw new GrammarSpecificationError("meta error in pushInput");
    }
  }
  private def restInput : String = {
    val sb:StringBuilder = new StringBuilder;
    if (current != NOCURRENT) sb += (current.toChar);
    current = NOCURRENT;
    while (input.hasNext) {
      sb += (input.next)
    }
    sb.toString
  }

  val whitespace : Set[Char] = 
    Set.empty + ' ' + '\t' + '\r' + '\n' + '\f' + '\b';

  private def skipWhitespace : Unit = {
    if (whitespace contains headInput) {
      nextInput
      skipWhitespace
    }
  }

  private def readComment() : String = {
    val sb : StringBuilder = new StringBuilder("/");
    if (headInput == '/') {
      do {
	sb += headInput
	nextInput
      } while (headInput != '\n');
    } else if (headInput == '*') {
      do {
	   do {
	     sb += headInput;
	     //print("+" + headInput);
	     nextInput;
	   } while (headInput != '*');
	   do {
	     sb += headInput;
	     //print("-" + headInput);
	     nextInput;
	   } while (headInput == '*');
      } while (headInput != '/');
      sb += headInput;
      nextInput
    } else {
      throw new GrammarSpecificationError("Meta error in readComment");
    }
    sb.toString
  }

  def isKeyChar(ch : Char) : Boolean = {
    ch == '-' || ch == '_' || ch >= 'a' && ch <= 'z' || 
    ch >= 'A' && ch <= 'Z' || ch >= '0' && ch <= '9'
  }

  object ID {
    def unapply(ch : Char) : Boolean = isKeyChar(ch);
  }

  def readID() : String = {
    val sb : StringBuilder = new StringBuilder();
    do {
      sb += headInput;
      nextInput
    } while (isKeyChar(headInput));
    sb.toString
  }

  def readQuoted(quote : Char) : String = {
    val sb : StringBuilder = new StringBuilder();
    sb += quote;
    nextInput
    while (headInput != quote) {
      if (headInput == '\\') {
	sb += headInput;
	nextInput
      }
      sb += headInput;
      nextInput
    }
    sb += headInput;
    nextInput;
    
    return sb.toString
  }

  def readSingleQuoted : String = {
    val sb : StringBuilder = new StringBuilder();
    sb += '\'';
    nextInput
    if (headInput == '\\') {
      sb += headInput;
      nextInput
    }
    sb += headInput;
    nextInput
    if (headInput == '\'') {
      sb += headInput;
      nextInput;
    }
    return sb.toString
  }

  def convert(quoted : String) : String = {
    // println("Converting " + quoted);
    val sb : StringBuilder = new StringBuilder();
    val n : Int = quoted.length() - 1;
    var i : Int = 1;
    while (i < n) {
      val ch : Char = quoted.charAt(i);
      sb += {
	if (ch == '\\') {
	  i += 1
	  quoted.charAt(i) match {
	    case 'b' => '\b'
	    case 't' => '\t'
	    case 'f' => '\f'
	    case 'n' => '\n'
	    case 'r' => '\r'
	    case '0' => '\u0000'
	    case c => c
	  }
	} else {
	  ch
	}
      };
      i += 1
    }
    // println("Result is '" + sb + "'");
    sb.toString
  }

  abstract class State {
    def next : BisonTokens.YYToken;
    def hasNext : Boolean;
  }

  private var state : State = new InitialState;
  private var savedState : State = null;

  /*override*/ def hasNext : Boolean = state != null && state.hasNext;
  /*override*/ def next : BisonTokens.YYToken = state.next;

  class InitialState extends State {
    override def hasNext : Boolean = {
      skipWhitespace
      if (headInput == '/') {
	nextInput
	if (headInput == '/' || headInput == '*') {
	  readComment
	  hasNext
	} else {
	  pushInput('/');
	  true
	}
      } else true
    }

    override def next : BisonTokens.YYToken = {
      headInput match {
	case '%' => {
	  nextInput
	  headInput match {
	    case '%' => {
	      nextInput
	      state = new RuleState;
	      BisonTokens.BEGIN()
	    }
	    case ID() => {
	      readID match {
		case "left" => BisonTokens.LEFT()
		case "right" => BisonTokens.RIGHT()
		case "nonassoc" => BisonTokens.NONASSOC()
		case "token" => BisonTokens.TOKEN()
		case "type" => BisonTokens.TYPE()
		case "start" => BisonTokens.START()
		case "union" => BisonTokens.UNION()
		case s:String =>
		  throw new GrammarSpecificationError("Unknown key %" + s)
	      }
	    }
	    case '{' => {
	      nextInput
	      savedState = this
	      state = new CodeState;
	      BisonTokens.PROLOGUE_BEGIN()
	    }
	    case ch:Char => {
	      throw new GrammarSpecificationError("Unknown directive %" + ch)
	    }
	  }
	}

	case ID() => BisonTokens.ID(readID())
	case '{' => {
	  nextInput
	  savedState = this
	  state = new CodeState;
	  BisonTokens.YYCHAR('{')
	}
	case '<' => {
	  val sb : StringBuilder = new StringBuilder;
	  nextInput
	  while (headInput != '>' && headInput != '\n') {
	    sb += headInput
	    nextInput
	  }
	  if (headInput != '>') {
	    throw new GrammarSpecificationError("<TYPE unterminated");
	  }
	  nextInput
	  BisonTokens.TYPELIT(sb.toString)
	}
	case '\'' => BisonTokens.CHARLIT(convert(readQuoted('\'')).charAt(0))
	case '"' => BisonTokens.STRINGLIT(convert(readQuoted('"')))
	case ch:Char => nextInput; BisonTokens.YYCHAR(ch)
      }
    }
  }

  class CodeState extends State {
    var level : Int = 1;
    override def hasNext : Boolean = true;
    override def next : BisonTokens.YYToken = {
      headInput match {
	case '{' => {
	  nextInput
	  level += 1; 
	  BisonTokens.YYCHAR('{')
	}
	case '}' => {
	  nextInput
	  level -= 1
	  if (level == 0) state = savedState; 
	  BisonTokens.YYCHAR('}')
	}
	case '/' => {
	  nextInput;
	  if (headInput == '*' || headInput == '/') {
	    BisonTokens.CODE(readComment())
	  } else {
	    BisonTokens.CODE("/")
	  }
	}
	case '$' => {
	  nextInput;
	  if (headInput == '$') {
	    nextInput
	    BisonTokens.VAR(-1)
	  } else if (headInput >= '0' && headInput <= '9') {
	    var result : Int = 0
	    do {
	      result *= 10;
	      result += (headInput - '0');
	      nextInput
	    } while (headInput >= '0' && headInput <= '9');
	    BisonTokens.VAR(result)
	  } else {
	    throw new GrammarSpecificationError("$ must be followed by int");
	  }
	}
	case '%' => {
	  nextInput
	  if (headInput == '}') {
	    nextInput
	    state = savedState;
	    BisonTokens.PROLOGUE_END()
	  } else {
	    BisonTokens.CODE("%");
	  }
	}
	case '\'' => BisonTokens.CODE(readSingleQuoted)
	case '"' => BisonTokens.CODE(readQuoted('"'))
	case ch:Char => {
	  var sb : StringBuilder = new StringBuilder
	  do {
	    sb += headInput
	    nextInput
	  } while (headInput != '$' && 
		   headInput != '}' &&
		   headInput != '{' &&
		   headInput != '%' &&
		   headInput != '/' && 
		   headInput != '\'' && headInput != '"')
	  return BisonTokens.CODE(sb.toString)
	}
      }
    }
  }

  class RuleState extends InitialState {
    override def next : BisonTokens.YYToken = {
      headInput match {
	case ID() => BisonTokens.ID(readID())
	case '{' => super.next
	case '\'' => BisonTokens.CHARLIT(convert(readQuoted('\'')).charAt(0))
	case '"' => BisonTokens.STRINGLIT(convert(readQuoted('"')))
	case '%' => {
	  nextInput
	  headInput match {
	    case '%' => {
	      nextInput
	      state = null
	      BisonTokens.END(restInput)
	    }
	    case ID() => {
	      readID() match {
		case "prec" => BisonTokens.PREC()
		case s:String =>
		  throw new GrammarSpecificationError("Illegal keyword %"+s)
	      }
	    }
	    case ch:Char => {
	      throw new GrammarSpecificationError("Illegal directive %"+ch)
	    }
	  }
	}
	case ch:Char => nextInput; BisonTokens.YYCHAR(ch);
      }
    }
  }
}

object BisonScanner {
  def main(args : Array[String]) = {
    for (s <- args) {
      val scanner : BisonScanner = new BisonScanner(Source.fromFile(s))
      while (scanner.hasNext) {
	println(scanner.next)
      }
    }
  }
}
