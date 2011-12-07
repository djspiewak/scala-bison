/* Scanner for Cool files
 * John Boyland
 * This file may be used, copied and/or modified for any purpose.
 * Eventually it might be nice to have this file generated.
 */
package edu.uwm.cs.cool.scanner;

import scala.collection.Set;
import scala.io.Source;
import edu.uwm.cs.cool.parser.CoolTokens;

class CoolScanner(input : Iterator[Char]) 
	extends Iterator[CoolTokens.YYToken]
{ 
  private val NOCHAR : Int = -1;
  private var current : Int = NOCHAR;
  private var saved : Int = NOCHAR; // one push back
  private var linenumber : Int = 1;

  def getLineNumber : Int = linenumber;

  private def hasInput : Boolean = {
    current != NOCHAR || saved != NOCHAR || input.hasNext
  }
  private def headInput : Char = {
    if (current == NOCHAR) {
      if (saved == NOCHAR) {
	if (!input.hasNext) {
	  throw new AssertionError("unexpected EOF");
	} else {
	  current = input.next() toInt
	} 
      } else {
	current = saved;
	saved = NOCHAR;
      }
    }
    current toChar
  }
  private def nextInput : Unit = {
    if (current == '\n') linenumber += 1;
    (current = NOCHAR);
  }
  private def pushInput(chx : Char) : Unit = {
    var ch : Char = chx
    if (ch == '\n') ch = ' ';
    if (current == NOCHAR) {
      current = ch;
    } else if (saved == NOCHAR) {
      saved = current;
      current = ch;
    } else {
      throw new AssertionError("meta error in pushInput");
    }
  }

  val whitespace : Set[Char] = 
    Set.empty + ' ' + '\t' + '\r' + '\n' + '\f' + '\b';

  private def skipWhitespace : Unit = {
    if (hasInput && (whitespace contains headInput)) {
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
	     nextInput;
	   } while (headInput != '*');
	   do {
	     sb += headInput;
	     nextInput;
	   } while (headInput == '*');
      } while (headInput != '/');
      sb += headInput;
      nextInput
    } else {
      throw new AssertionError("Meta error in readComment");
    }
    sb toString
  }

  def isKeyChar(ch : Char) : Boolean = {
    ch == '_' || ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' || 
    ch >= '0' && ch <= '9'
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
    sb toString
  }

  def readINT() : String = {
     val sb : StringBuilder = new StringBuilder();
    do {
      sb += headInput;
      nextInput
    } while (headInput >= '0' && headInput <= '9');
    sb toString
  }

  // A token can start with this character
  val legal : Set[Char] = Set.empty ++ whitespace ++
    ('a' to 'z') ++ ('A' to 'Z') ++ ('0' to '9') +
    '+' + '-' + '*' + '/' + '<' + '=' + '!' +
    '.' + ',' + ':' + ';' + '(' + ')' + '{' + '}' + '"';
   
  def readIllegal() : String = {
    val sb : StringBuilder = new StringBuilder();
    do {
      sb += headInput;
      nextInput
    } while (hasInput && !legal.contains(headInput));
    sb toString
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
    
    return sb toString
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
	    case '0' => '\0'
	    case c => c
	  }
	} else {
	  ch
	}
      };
      i += 1
    }
    // println("Result is '" + sb + "'");
    sb toString
  }

  abstract class State {
    def next : CoolTokens.YYToken;
    def hasNext : Boolean;
  }

  private var state : State = new InitialState;
  private var savedState : State = null;

  /*override*/ def hasNext : Boolean = state != null && state.hasNext;
  /*override*/ def next : CoolTokens.YYToken = state.next;

  class InitialState extends State {
    override def hasNext : Boolean = {
      skipWhitespace
      hasInput && {
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
    }

    def checkKeyword(s : String, ifnotkey : CoolTokens.YYToken) 
    : CoolTokens.YYToken = {
      // TODO: need to require first letter is lowercase for TODO lines:
      s match {
	case "case" => CoolTokens.CASE()
	case "class" => CoolTokens.CLASS()
	case "def" => CoolTokens.DEF()
	case "else" => CoolTokens.ELSE()
	case "eq" => CoolTokens.EQ()
	case "extends" => CoolTokens.EXTENDS()
	case "false" => CoolTokens.BOOL_LIT(false)
	case "if" => CoolTokens.IF()
	case "match" => CoolTokens.MATCH()
	case "native" => CoolTokens.NATIVE()
	case "new" => CoolTokens.NEW()
	case "null" => CoolTokens.NULL()
	case "override" => CoolTokens.OVERRIDE()
	case "super" => CoolTokens.SUPER()
	case "true" => CoolTokens.BOOL_LIT(true)
	case "var" => CoolTokens.VAR()
	case "while" => CoolTokens.WHILE()
	case _ => ifnotkey
      }
    }

    override def next : CoolTokens.YYToken = {
      headInput match {
	case 'A'|'B'|'C'|'D'|'E'|'F'|'G'|'H'|'I'|'J'|'K'|'L'|'M'|
	     'N'|'O'|'P'|'Q'|'R'|'S'|'T'|'U'|'V'|'W'|'X'|'Y'|'Z' => {
	  val str : String = readID
	  checkKeyword(str,CoolTokens.TYPEID(Symbol(str)));
	}
	case 'a'|'b'|'c'|'d'|'e'|'f'|'g'|'h'|'i'|'j'|'k'|'l'|'m'|
	     'n'|'o'|'p'|'q'|'r'|'s'|'t'|'u'|'v'|'w'|'x'|'y'|'z' => {
	  val str : String = readID
	  checkKeyword(str,CoolTokens.OBJECTID(Symbol(str)));
	}
	case '0'|'1'|'2'|'3'|'4'|'5'|'6'|'7'|'8'|'9' => {
	  val str : String = readINT
	  return CoolTokens.INT_LIT(Symbol(str));
	}
	case '+'|'-'|'*'|'/'|'.'|','|':'|';'|'('|')'|'!'|'{'|'}' => {
	  val ch = headInput;
	  nextInput;
	  return CoolTokens.YYCHAR(ch);
	}
	case '=' => {
	  nextInput;
	  if (headInput == '>') {
	    nextInput;
	    CoolTokens.ARROW();
	  } else if (headInput == '=') {
	    nextInput;
	    CoolTokens.EQEQ();
	  } else {
	    CoolTokens.YYCHAR('=');
	  }
	}
	case '<' => {
	  nextInput;
	  if (headInput == '=') {
	    nextInput;
	    CoolTokens.LE();
	  } else {
	    CoolTokens.YYCHAR('<');
	  }
	}
	case '"' => CoolTokens.STR_LIT(Symbol(convert(readQuoted('"'))))
	case ch:Char => {
	  CoolTokens.ERROR(readIllegal());
	}
      }
    }
  }

}
