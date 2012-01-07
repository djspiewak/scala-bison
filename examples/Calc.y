/*
 * A Parser for bison's "calc" example language.
 */
 
%token EOF
%token<Double> NUM

%type <Double> exp

%left '-' '+'
%left '*' '/'
%left UMINUS
%right '^'

%%
program :
        | program action '\n'
        ;

action  :       { }
        | exp   { println($1); } 
        ;

exp:      NUM                   { $$ = $1;         }
        | exp '+' exp           { $$ = $1 + $3;    }
        | exp '-' exp           { $$ = $1 - $3;    }
        | exp '*' exp           { $$ = $1 * $3;    }
        | exp '/' exp           { $$ = $1 / $3;    }
        | '-' exp  %prec UMINUS { $$ = -$2;        }
        | exp '^' exp           { $$ = math.pow ($1, $3); }
        | '(' exp ')'           { $$ = $2;         }
        ;
%%
   
def yyerror(s:String) = println(s);
     