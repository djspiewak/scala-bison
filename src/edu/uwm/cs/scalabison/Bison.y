%{
package edu.uwm.cs.scalabison;

import scala.io.Source;

%}

%token <Char> CHARLIT
%token <String> ID
%token BEGIN PROLOGUE_BEGIN PROLOGUE_END
%token LEFT RIGHT NONASSOC TOKEN TYPE START PREC
%token <Int> VAR
%token <String> TYPELIT CODE END
%token <String> STRINGLIT
%token UNION
%type <Symbol> symbol anon
%type <List[Symbol]> rhs
%type <Code> block piece 
%type <(Symbol,Code)> action
%type <List[Code]> rev_pieces
%type <Precedence> precedence
%type <BisonGrammar> grammar

%%

grammar : declarations BEGIN rules END 
		{ result.addExtra($4); $$ = result; }
	;

declarations : /* EMPTY */
	 | declarations { symbol_type = ""; prec = null; } declaration
	 ;

declaration
	: PROLOGUE_BEGIN rev_pieces PROLOGUE_END 
		{ result.addPrologue($2 reverse); }
	| TOKEN token_names
        | precedence { prec = $1; } token_names
	| TYPE TYPELIT { symbol_type = $2; } nonterminal_names
	| START ID { result.setStart(result.getNT($2)); }
	;

token_names 
	: token_names token_name
	| token_name
	;

token_name
	: ID	{ val t : Terminal = result.add(new Terminal($1,symbol_type));
		  if (prec != null) t.setPrecedence(prec); }
	| CHARLIT
		{ val t : Terminal = result.add(new CharLitTerminal($1));
		  if (prec != null) t.setPrecedence(prec); }
	| TYPELIT
		{ symbol_type = $1; }
	;

nonterminal_names
	: nonterminal_names nonterminal_name
	| nonterminal_name
	;

nonterminal_name
	: ID	{ result.add(new Nonterminal($1,symbol_type)); }
	;

precedence
	: LEFT { precLevel += 1; $$ = new LeftPrecedence(precLevel); }
	| RIGHT { precLevel += 1; $$ = new RightPrecedence(precLevel); }
	| NONASSOC { precLevel += 1; $$ = new NonAssocPrecedence(precLevel); }
        ;

rules	: /* EMPTY */
	| rules rule
	;

rule	: ID ':' rhs action 
		{ currNT = result.getNT($1); 
		  result.addRule(new Rule(genRuleNum(),currNT,$3,getPrec($3,$4._1),$4._2)); } 
	  morerules ';'
	;

morerules
	: /* EMPTY */
	| morerules '|' rhs action 
		{ result.addRule(new Rule(genRuleNum(),currNT,$3,getPrec($3,$4._1),$4._2)); }
	;

rhs	: /* EMPTY */ { $$ = Nil; }
	| rhs symbol { $$ = $1 ++ ($2 :: Nil); }
	| rhs anon symbol { $$ = $1 ++ ($2 :: $3 :: Nil); } 
	;

anon	: block
		{ uniq += 1;
		  val nt : Nonterminal = result.add(new ArtificialNonterminal("@" + uniq,"unit"));
		  result.addRule(new Rule(genRuleNum(),nt,Nil,null,$1));
		  $$ = nt; }
	;

symbol	: ID	{ $$ = result.get($1); }
	| CHARLIT 
		{ $$ = result.getCLT($1); }
	;

action	: block	{ $$ = (null,$1); }
	| PREC symbol block 
		{ $$ = ($2,$3); }
	| PREC symbol 
		{ $$ = ($2,NoCode()); }
	| /* EMPTY */
		{ $$ = (null,NoCode()); }
	;

block	: '{' rev_pieces '}' 
		{ $$ = BlockCode($2 reverse); }
	;

rev_pieces
	: /* EMPTY */
		{ $$ = Nil; }
	| rev_pieces piece
		{ $$ = $2 :: $1; }
	;

piece	: block	{ $$ = $1; }
	| CODE	{ $$ = LiteralCode($1); }
	| VAR	{ $$ = VariableCode($1); }
	;
%%

  def yyerror(s : String) = {
    errorsOccured = true;
    println(filename + ":" + scanner.getLineNumber + ":" + s);
  }

  var scanner : BisonScanner = null;
  var filename : String = "<unknown>";
  var errorsOccured : Boolean = false;
  var result : BisonGrammar = new BisonGrammar(); 
  var precLevel : Int = 0;
  var prec : Precedence = null;
  var symbol_type : String = "";
  var uniq : Int = 0;
  var currNT : Nonterminal = null;
  var rulenum : Int = 0;

  def genRuleNum() : Int = {
    rulenum += 1;
    rulenum
  }

  def getPrec(rhs:List[Symbol], sym : Symbol) : Precedence = {
    if (sym != null) return sym.precedence;
    for (sym <- rhs.reverse) {
      if (sym.precedence != null) return sym.precedence;
    }
    null
  }

  def reset(fn : String, sc : BisonScanner) = {
    filename = fn;
    scanner = sc;
    yyreset(sc);
    result = new BisonGrammar();
    precLevel = 0;
    prec = null;
    uniq = 0;
    currNT = null;
    rulenum = 0;
    errorsOccured = false;
  }

