/*
 *  cool.y
 *              Parser definition for the COOL language.
 *              This requires ScalaBison
 */
%{
package edu.uwm.cs.cool.parser;
import edu.uwm.cs.cool.tree._;
import edu.uwm.cs.cool.scanner.CoolScanner;

%}

/* 
   Declare the terminals; a few have types for associated lexemes.
   The token ERROR is never used in the parser; thus, it is a parse
   error when the lexer returns it.
*/
%token CASE CLASS DEF ELSE EQ EXTENDS IF
%token MATCH NATIVE NEW NULL NOT OVERRIDE SUPER VAR WHILE
%token <Symbol>  STR_LIT INT_LIT 
%token <Boolean> BOOL_LIT
%token <Symbol>  TYPEID OBJECTID
%token EQEQ LE ARROW 
%token <String> ERROR

/*## The following messages are for the .SKEL file for students: */
/*  DON'T CHANGE ANYTHING ABOVE THIS LINE, OR YOUR PARSER WONT WORK       */
/**************************************************************************/

  /* Complete the nonterminal list below, giving a type for the semantic
      value of each non terminal. (See the bison documentation for details. */
/*## End of messages for the .SKEL file */

/* Declare types for the grammar's non-terminals. */
%type <Classes> class_list
%type <Class_> class_decl
%type <Symbol> superclass
/* The students will have to do the rest themselves #( */
%type <Features> feature_list
%type <Feature> feature
%type <Formals> formals formal_list
%type <Formal> formal
%type <Expression> expr block
%type <Expressions> actuals exp_list stmt_list 
%type <Cases> case_list simple_cases
%type <Case> simple_case
%type <Boolean> opt_override
/* end of section omitted from cool.y.SKEL #) */

/*## End of lines for students .SKEL  file */

/* Precedence declarations. */
/* #( omitted from SKEL file */
%left IF
%left MATCH
%left '='
%nonassoc EQ EQEQ LE '<'
%left '-' '+'
%left '*' '/'
%left UNARY
%left '.'
/* end of omissions from .SKEL file #) */

%%
/* 
   Save the parsed classes in a global variable.
   We don't create a "program" node because that must include all
   classes on the command line, not just the current file.
*/
program	: class_list	{ result = program($1); }
	| error         /* ## */
        ;

class_list
	: class_decl			/* single class */
		{ $$ = List($1); }
        | error ';'             /* error in the first class ## */
                { $$ = Nil; } /* ## */
	| class_list class_decl	/* several classes */
		{ $$ = $1 + $2; }
	| class_list error ';'  /* error message ## */
		{ $$ = $1; } /* ## */
	;

class_decl
	: CLASS TYPEID formals superclass '{' feature_list '}'
		{ $$ = class_($2,$4,
			      make_constructor($2,$3) :: $6,
			      Symbol(filename)); }
	;

/* #( If no parent is specified, the class inherits from the Object class. */
superclass : /* empty */
		{ superclass_name = Symbol("Object"); 
                  $$ = superclass_name;
                  add_supercall(superclass_name,Nil); }
	| EXTENDS TYPEID actuals
		{ superclass_name = $2; 
		  $$ = superclass_name;
		  add_supercall($2,$3); }
	| EXTENDS NATIVE
		{ superclass_name = null; 
		  $$ = null;
		  native_constructor(); }
	;

/* The rest of the grammar is omitted from .SKEL file #( */
feature_list
	: /* EMPTY */
		{  $$ = Nil; }
	| feature_list feature ';'  	/* Several features */
		{  $$ = $1 + $2; }
	| feature_list error ';' 	/* error message */
		{  $$ = $1; }
        | feature_list NATIVE ';'
                { $$ = $1; native_constructor(); }
	| feature_list '{' block '}'
		{ add_to_constructor($3);
		  $$ = $1; }
	;

feature : opt_override DEF OBJECTID formals ':' TYPEID '=' expr
		{ $$ = method($1,$3,$4,$6,$8); }
	| opt_override DEF OBJECTID formals ':' TYPEID NATIVE
		{ $$ = method($1,$3,$4,$6,no_expr()); }
	| VAR OBJECTID ':' TYPEID '=' expr
		{ $$ = attr($2,$4);
		  add_to_constructor(assign($2,$6)); }
	| VAR OBJECTID ':' NATIVE
		{ $$ = attr($2,Symbol("native"));
		  /* if you have a native field, you cannot be inherited */
		  current_inherit_status = false; 
		  /* and your constructor is native */
		  native_constructor();
		}
	;

opt_override
	: OVERRIDE { $$ = true; }
	| /* nothing */ { $$ = false; }
	;

formals	: '(' ')' 		/* allow an empty formal list */
		{ $$ = Nil; }
	| '(' formal_list ')'
		{ $$ = $2; }
	| '(' error ')'
		{ $$ = Nil;  }
	;

formal_list
	: formal 		 /* One formal */
		{  $$ = List($1); }            
	| formal_list ',' formal /* Several declarations */
		{ $$ = $1 + $3; }
	;

formal	: OBJECTID ':' TYPEID
		{  $$ = formal($1,$3); }
	;

expr	: OBJECTID '=' expr
          { $$ = assign($1,$3); }
        | SUPER '.' OBJECTID actuals 
	  { val this_obj : Expression = variable(Symbol("this"));
	    $$ = static_dispatch(this_obj,superclass_name,$3,$4); }
        | expr '.' OBJECTID actuals 
          { $$ = dispatch($1,$3,$4); }
        | IF '(' expr ')' expr ELSE expr %prec IF
          { $$ = cond($3,$5,$7); }
        | WHILE '(' expr ')' expr %prec IF
          { $$ = loop($3,$5); }
        | '{' block '}'
	  { $$ = $2; }
        | expr MATCH '{' case_list '}'
          { $$ = typcase($1,$4); }
        | NEW TYPEID actuals
          { $$ = dispatch(new_($2),$2,$3); }
        | expr '+' expr 
          { $$ = add($1,$3); }
        | expr '-' expr
          { $$ = sub($1,$3); }
        | expr '*' expr
          { $$ = mul($1,$3); }
        | expr '/' expr
          { $$ = div($1,$3); }
        | '-' expr %prec UNARY
          { $$ = neg($2); }
        | expr '<' expr
          { $$ = lt($1,$3); }
        | expr EQ expr
          { $$ = eql($1,$3); }
        | expr EQEQ expr
	  { $$ = dispatch($1,Symbol("equals"),List($3)); }
        | expr LE expr
          { $$ = leq($1,$3); }
	| '!' expr %prec UNARY
          { $$ = comp($2); }
        | '(' expr ')'
          { $$ = $2; }
	| NULL
	  { $$ = unit(); }
        | INT_LIT
          { $$ = int_const($1); }
        | STR_LIT
          { $$ = string_const($1); }
        | BOOL_LIT
          { $$ = bool_const($1); }
        | OBJECTID
          { $$ = variable($1); }
        | OBJECTID actuals
          { 
	    $$ = dispatch(variable(Symbol("this")),$1,$2); 
	  }
        ;

block	: stmt_list
		{ $$ = block($1); }
	;

stmt_list: expr ';'
          { yyerror("deleted semicolon");
	    $$ = List($1); }
	| expr
	  { $$ = List($1); }
        | error
          { $$ = Nil; }   // error in the last expression
        | expr ';' stmt_list           //  several expressions
	  { $$ = $1 :: $3; }
	| VAR OBJECTID ':' TYPEID '=' expr ';' stmt_list
          { $$ = List(let($2,$4,$6,block($8))); }
        | error ';' stmt_list  // error before last expression
          { $$ = $3; }
        ;

actuals	: '(' ')'
		{  $$ = Nil; }
	| '(' exp_list ')'	// List of argument values 
		{  $$ = $2; }
	;

exp_list
	: expr			/* One expression */
		{ $$ = List($1); }
	| exp_list ',' expr	/* Several expressions */
		{ $$ = $1 + $3; }
	;

case_list
	: simple_cases
		{ $$ = $1; }
/*	| simple_cases ELSE expr
 *		{ Symbol ignored = idtable.add_string("_ignore");
 *		  Symbol object = idtable.add_string("Object");
 *		  Case def = branch(ignored,object,$3);
 *		  $$ = append_Cases($1,single_Cases(def)); }
 */
	;

simple_cases
        : simple_case   	/* One branch */
                { $$ = List($1); }
        | simple_cases simple_case 
                { $$ = $1 + $2; }
        ;

simple_case
        : CASE OBJECTID ':' TYPEID ARROW expr
                { $$ = branch($2,$4,$6); }
/*
 *	| CASE NULL ARROW block
 *		{ $$ = branch(Symbol("_ignore"),Symbol("Unit"),$4); }
 */
        ;
/* end of omissions from .SKEL file #) */

/* end of grammar */
%%
/* The following two lines are for the .SKEL file ## */
/************************************************************************/
/*                DONT CHANGE ANYTHING IN THIS SECTION                  */

type Classes = List[Class_]
type Features = List[Feature];
type Expressions = List[Expression];
type Formals = List[Formal];
type Cases = List[Case];

var scanner : CoolScanner = null;
var filename : String = "<unknown>";
var num_errors : Int = 0;
var result : Program = null;
var superclass_name : Symbol = null;
var current_inherit_status : Boolean = true;

def reset(fn : String, sc : CoolScanner) = {
  filename = fn;
  scanner = sc;
  num_errors = 0;
  result = null;
  superclass_name = null;
  current_inherit_status = true;

  yyreset(sc)
}

// Code to help build constructors:
var constr_is_native : Boolean = false;
var constr_body : List[Expression] = Nil;
def add_to_constructor(e : Expression) : Unit = { constr_body += e }
def add_supercall(supername : Symbol, actuals : Expressions) : Unit = {
  add_to_constructor(static_dispatch(variable(Symbol("this")),supername,
				     supername,actuals))
}
def native_constructor() = {
  constr_is_native = true
}
def make_constructor(name : Symbol, formals : List[Formal]) : Feature = {
  val result : Feature = 
  method(false,name,formals,name,
	 if (constr_is_native) no_expr() 
	 else block(constr_body + variable(Symbol("this"))));
  constr_is_native = false;
  constr_body = Nil;
  current_inherit_status = true;
  result
}

/* This function is called automatically when Bison detects a parse error. */
def yyerror(message : String) = {
  val curr_lineno : Int = scanner.getLineNumber;
  
  println(filename + ":" + curr_lineno + ": " + message);
  num_errors += 1;
  if (num_errors>50) { println("More than 50 errors"); System.exit(1); }
}

