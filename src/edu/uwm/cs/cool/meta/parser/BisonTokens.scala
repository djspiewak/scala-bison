/* This file is intended to be machine generated
 */
package edu.uwm.cs.cool.meta.parser;

object BisonTokens {
  trait YYSymbol;
  trait YYToken extends YYSymbol;

  case class YYEOF() extends BisonTokens.YYToken;
  case class YYCHAR(yy : Char) extends YYToken;
  case class CHARLIT(yy : Char) extends YYToken;
  case class STRINGLIT(yy : String) extends YYToken;
  case class ID(yy : String) extends YYToken;
  case class BEGIN() extends YYToken;
  case class PROLOGUE_BEGIN() extends YYToken;
  case class PROLOGUE_END() extends YYToken;
  case class LEFT() extends YYToken;
  case class RIGHT() extends YYToken;
  case class NONASSOC() extends YYToken;
  case class TOKEN() extends YYToken;
  case class TYPE() extends YYToken;
  case class START() extends YYToken;
  case class UNION() extends YYToken;
  case class PREC() extends YYToken;
  case class VAR(yy : Int) extends YYToken;
  case class TYPELIT(yy : String) extends YYToken;
  case class CODE(yy : String) extends YYToken;
  case class END(yy : String) extends YYToken;
}
