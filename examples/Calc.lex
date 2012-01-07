%%

%class CalcScanner
%type CalcTokens.YYToken
%implements Iterator[CalcTokens.YYToken]

%line

%{
  // These features are added to the Scanner class
  var lookahead : CalcTokens.YYToken = null;
   
  override def hasNext() : Boolean = { 
    if (lookahead == null) lookahead = yylex();
    lookahead match {
      case x:CalcTokens.YYEOF => false;
      case x:CalcTokens.YYToken => true;
    }
  };
  
  override def next() : CalcTokens.YYToken = {
    if (lookahead == null) lookahead = yylex();
    var result : CalcTokens.YYToken = lookahead;
    lookahead = null;
    result
  };
  
  def getLineNumber() : Int = yyline+1;

%}

NEWLINE = [\n]
WHITESPACE = [ \t\r]
SINGLE  = [+\-*/\^()]
NUM = [0-9]+
ERROR = [^\n \t\r+\-*/\^()0-9]

%%

{NEWLINE}		{ return CalcTokens.YYCHAR('\n'); }
{WHITESPACE}+	{}
{SINGLE}		{ return CalcTokens.YYCHAR(yytext.charAt(0)); }
{NUM}			{ return CalcTokens.NUM(Integer.parseInt(yytext)); }

{ERROR}+		{ println("Ignored characters: " + yytext); }

<<EOF>>			{ return CalcTokens.YYEOF(); }
