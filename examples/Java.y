%token Identifier

%token IntegerLiteral
%token FloatingPointLiteral
%token BooleanLiteral
%token CharacterLiteral
%token StringLiteral
%token NullLiteral

%token BOOLEAN
%token BYTE SHORT INT LONG CHAR
%token FLOAT DOUBLE

%token PACKAGE IMPORT CLASS INTERFACE
%token 	PUBLIC PROTECTED PRIVATE
%token STATIC
%token 	ABSTRACT FINAL NATIVE SYNCHRONIZED TRANSIENT VOLATILE
%token EXTENDS IMPLEMENTS
%token VOID
%token THROWS

%token THIS SUPER
%token IF ELSE SWITCH CASE DEFAULT WHILE DO FOR 
%token BREAK CONTINUE RETURN THROW 
%token TRY CATCH FINALLY
%token NEW NULL INSTANCEOF

%token PLUSPLUS MINUSMINUS
%token LTLT GTGT GTGTGT LTEQ GTEQ EQEQ BANGEQ
%token ANDAND OROR
%token TIMESEQ DIVEQ MODEQ PLUSEQ MINUSEQ 
%token LTLTEQ GTGTEQ GTGTGTEQ ANDEQ XOREQ OREQ
%%

/* 19.2 Productions from S2.3: The Syntactic Grammar */

Goal:

	CompilationUnit
	;

/* 19.3 Productions from S3: Lexical Structure */

Literal:

	IntegerLiteral|

	FloatingPointLiteral|

	BooleanLiteral|

	CharacterLiteral|

	StringLiteral|

	NullLiteral;

/* 19.4 Productions from S4: Types, Values, and Variables */

Type:

	PrimitiveType|

	ReferenceType;

PrimitiveType:

	NumericType|

	BOOLEAN;

NumericType:

	IntegralType|

	FloatingPointType ;

IntegralType: 

	BYTE | SHORT | INT | LONG | CHAR;

FloatingPointType:

	FLOAT | DOUBLE;

ReferenceType:

	ClassOrInterfaceType|

	ArrayType;

ClassOrInterfaceType:

	Name;

ClassType:

	ClassOrInterfaceType;

InterfaceType:

	ClassOrInterfaceType;

ArrayType:

	PrimitiveType '[' ']'|

	Name '[' ']'|

	ArrayType '[' ']';

/* 19.5 Productions from S6: Names */

Name:

	SimpleName|

	QualifiedName;

SimpleName:

	Identifier;

QualifiedName:

	Name '.' Identifier;

/* 19.6 Productions from S7: Packages */

CompilationUnit:

	PackageDeclaration ImportDeclarations TypeDeclarations|
	PackageDeclaration ImportDeclarations|
	PackageDeclaration TypeDeclarations|
	PackageDeclaration|
	ImportDeclarations TypeDeclarations|
	ImportDeclarations|
        TypeDeclarations|
	;

ImportDeclarations:

	ImportDeclaration|

	ImportDeclarations ImportDeclaration;

TypeDeclarations:

	TypeDeclaration|

	TypeDeclarations TypeDeclaration;

PackageDeclaration:

	PACKAGE Name ';' ;

ImportDeclaration:

	SingleTypeImportDeclaration|

	TypeImportOnDemandDeclaration;

SingleTypeImportDeclaration:

	IMPORT Name ';';

TypeImportOnDemandDeclaration:

	IMPORT Name '.' '*' ';';

TypeDeclaration:

	ClassDeclaration|

	InterfaceDeclaration|

	';';

/* 19.7 Productions Used Only in the LALR(1) Grammar */

Modifiers:

	Modifier|

	Modifiers Modifier;

Modifier:

	PUBLIC| PROTECTED| PRIVATE|

	STATIC|

	ABSTRACT| FINAL| NATIVE| SYNCHRONIZED| TRANSIENT| VOLATILE;

/* 19.8 Productions from S8: Classes */

/* 19.8.1 Productions from S8.1: Class Declaration */

ClassDeclaration:

	Modifiers CLASS Identifier Super Interfaces ClassBody|
	Modifiers CLASS Identifier Super ClassBody|
	Modifiers CLASS Identifier Interfaces ClassBody|
	Modifiers CLASS Identifier ClassBody|
	CLASS Identifier Super Interfaces ClassBody|
	CLASS Identifier Super ClassBody|
	CLASS Identifier Interfaces ClassBody|
	CLASS Identifier ClassBody;

Super:

	EXTENDS ClassType;

Interfaces:

	IMPLEMENTS InterfaceTypeList;

InterfaceTypeList:

	InterfaceType|

	InterfaceTypeList ',' InterfaceType;

ClassBody:

	'{' ClassBodyDeclarations '}'|

	'{' '}';

ClassBodyDeclarations:

	ClassBodyDeclaration|

	ClassBodyDeclarations ClassBodyDeclaration;

ClassBodyDeclaration:

	ClassMemberDeclaration|

	StaticInitializer|

	ConstructorDeclaration;

ClassMemberDeclaration:

	FieldDeclaration|

	MethodDeclaration;

/* 19.8.2 Productions from S8.3: Field Declarations */

FieldDeclaration:

	Modifiers Type VariableDeclarators ';'|

	Type VariableDeclarators ';';

VariableDeclarators:

	VariableDeclarator|

	VariableDeclarators ',' VariableDeclarator;

VariableDeclarator:

	VariableDeclaratorId|

	VariableDeclaratorId '=' VariableInitializer;

VariableDeclaratorId:

	Identifier|

	VariableDeclaratorId '[' ']';

VariableInitializer:

	Expression|

	ArrayInitializer;

/* 19.8.3 Productions from S8.4: Method Declarations */

MethodDeclaration:

	MethodHeader MethodBody;

MethodHeader:

	Modifiers Type MethodDeclarator Throws |
	Modifiers Type MethodDeclarator |
	Type MethodDeclarator Throws |
	Type MethodDeclarator |

	Modifiers VOID MethodDeclarator Throws|
	Modifiers VOID MethodDeclarator |
	VOID MethodDeclarator Throws|
	VOID MethodDeclarator ;

MethodDeclarator:

	Identifier '(' FormalParameterList ')' |
	Identifier '(' ')' |

	MethodDeclarator '[' ']';

FormalParameterList:

	FormalParameter|

	FormalParameterList ',' FormalParameter;

FormalParameter:

	Type VariableDeclaratorId;

Throws:

	THROWS ClassTypeList;

ClassTypeList:

	ClassType|

	ClassTypeList ',' ClassType;

MethodBody:

	Block| 

	';';

/* 19.8.4 Productions from S8.5: Static Initializers */

StaticInitializer:

	STATIC Block;

/* 19.8.5 Productions from S8.6: Constructor Declarations */

ConstructorDeclaration:

	Modifiers ConstructorDeclarator Throws ConstructorBody|
	Modifiers ConstructorDeclarator ConstructorBody|
	ConstructorDeclarator Throws ConstructorBody|
	ConstructorDeclarator ConstructorBody;

ConstructorDeclarator:

	SimpleName '(' FormalParameterList ')'|
	SimpleName '(' ')';

ConstructorBody:

	'{' ExplicitConstructorInvocation BlockStatements '}'|
	'{' ExplicitConstructorInvocation '}'|
	'{' BlockStatements '}'|
	'{' '}';

ExplicitConstructorInvocation:

	THIS '(' ArgumentList ')' ';'|
	THIS '(' ')' ';'|

	SUPER '(' ArgumentList ')' ';'|
	SUPER '(' ')' ';';


/* 19.9 Productions from S9: Interfaces */

/* 19.9.1 Productions from S9.1: Interface Declarations */

InterfaceDeclaration:

	Modifiers INTERFACE Identifier ExtendsInterfaces InterfaceBody|
	Modifiers INTERFACE Identifier InterfaceBody|
	INTERFACE Identifier ExtendsInterfaces InterfaceBody|
	INTERFACE Identifier InterfaceBody;

ExtendsInterfaces:

	EXTENDS InterfaceType|

	ExtendsInterfaces ',' InterfaceType;

InterfaceBody:

	'{' InterfaceMemberDeclarations '}'|
	'{' '}';

InterfaceMemberDeclarations:

	InterfaceMemberDeclaration|

	InterfaceMemberDeclarations InterfaceMemberDeclaration;

InterfaceMemberDeclaration:

	ConstantDeclaration|

	AbstractMethodDeclaration;

ConstantDeclaration:

	FieldDeclaration;

AbstractMethodDeclaration:

	MethodHeader ';';

/* 19.10 Productions from S10: Arrays */

ArrayInitializer:

	'{' VariableInitializers ',' '}'|

	'{' VariableInitializers '}'|

	'{' ',' '}'|

	'{' '}';

VariableInitializers:

	VariableInitializer|

	VariableInitializers ',' VariableInitializer;

/* 19.11 Productions from S14: Blocks and Statements */

Block:

	'{' BlockStatements '}'|
	'{' '}';

BlockStatements:

	BlockStatement|

	BlockStatements BlockStatement;

BlockStatement:

	LocalVariableDeclarationStatement|

	Statement;

LocalVariableDeclarationStatement:

	LocalVariableDeclaration ';';

LocalVariableDeclaration:

	Type VariableDeclarators;

Statement:

	StatementWithoutTrailingSubstatement|

	LabeledStatement|

	IfThenStatement|

	IfThenElseStatement|

	WhileStatement|

	ForStatement;

StatementNoShortIf:

	StatementWithoutTrailingSubstatement|

	LabeledStatementNoShortIf|

	IfThenElseStatementNoShortIf|

	WhileStatementNoShortIf|

	ForStatementNoShortIf;

StatementWithoutTrailingSubstatement:

	Block|

	EmptyStatement|

	ExpressionStatement|

	SwitchStatement|

	DoStatement|

	BreakStatement|

	ContinueStatement|

	ReturnStatement|

	SynchronizedStatement|

	ThrowStatement|

	TryStatement;

EmptyStatement:

	';';

LabeledStatement:

	Identifier ':' Statement;

LabeledStatementNoShortIf:

	Identifier ':' StatementNoShortIf;

ExpressionStatement:

	StatementExpression ';';

StatementExpression:

	Assignment|

	PreIncrementExpression|

	PreDecrementExpression|

	PostIncrementExpression|

	PostDecrementExpression|

	MethodInvocation|

	ClassInstanceCreationExpression;

IfThenStatement:

	IF '(' Expression ')' Statement;

IfThenElseStatement:

	IF '(' Expression ')' StatementNoShortIf ELSE Statement;

IfThenElseStatementNoShortIf:

	IF '(' Expression ')' StatementNoShortIf ELSE StatementNoShortIf;

SwitchStatement:

	SWITCH '(' Expression ')' SwitchBlock;

SwitchBlock:

	'{' SwitchBlockStatementGroups SwitchLabels '}'|
	'{' SwitchLabels '}'|
	'{' SwitchBlockStatementGroups '}'|
	'{'  '}';


SwitchBlockStatementGroups:

	SwitchBlockStatementGroup|

	SwitchBlockStatementGroups SwitchBlockStatementGroup;

SwitchBlockStatementGroup:

	SwitchLabels BlockStatements;

SwitchLabels:

	SwitchLabel|

	SwitchLabels SwitchLabel;

SwitchLabel:

	CASE ConstantExpression ':'|

	DEFAULT ':';

WhileStatement:

	WHILE '(' Expression ')' Statement;

WhileStatementNoShortIf:

	WHILE '(' Expression ')' StatementNoShortIf;

DoStatement:

	DO Statement WHILE '(' Expression ')' ';';

ForStatement:

	FOR '(' ForInit ';' Expression ';' ForUpdate ')'
		Statement|
	FOR '(' ForInit ';' Expression ';' ')'
		Statement|
	FOR '(' ForInit ';' ';' ForUpdate ')'
		Statement|
	FOR '(' ForInit ';' ';' ')'
		Statement|
	FOR '(' ';' Expression ';' ForUpdate ')'
		Statement|
	FOR '(' ';' Expression ';' ')'
		Statement|
	FOR '(' ';' ';' ForUpdate ')'
		Statement|
	FOR '(' ';' ';' ')'
		Statement;

ForStatementNoShortIf:

	FOR '(' ForInit ';' Expression ';' ForUpdate ')'
		StatementNoShortIf|
	FOR '(' ForInit ';' Expression ';' ')'
		StatementNoShortIf|
	FOR '(' ForInit ';' ';' ForUpdate ')'
		StatementNoShortIf|
	FOR '(' ForInit ';' ';' ')'
		StatementNoShortIf|
	FOR '(' ';' Expression ';' ForUpdate ')'
		StatementNoShortIf|
	FOR '(' ';' Expression ';' ')'
		StatementNoShortIf|
	FOR '(' ';' ';' ForUpdate ')'
		StatementNoShortIf|
	FOR '(' ';' ';' ')'
		StatementNoShortIf;

ForInit:

	StatementExpressionList|

	LocalVariableDeclaration;

ForUpdate:

	StatementExpressionList;

StatementExpressionList:

	StatementExpression|

	StatementExpressionList ',' StatementExpression;

BreakStatement:

	BREAK Identifier ';'|
	BREAK ';';

ContinueStatement:

	CONTINUE Identifier ';'|
	CONTINUE ';';

ReturnStatement:

	RETURN Expression ';'|
	RETURN ';';

ThrowStatement:

	THROW Expression ';';

SynchronizedStatement:

	SYNCHRONIZED '(' Expression ')' Block;

TryStatement:

	TRY Block Catches|

	TRY Block Catches Finally|
	TRY Block Finally;

Catches:

	CatchClause|

	Catches CatchClause;

CatchClause:

	CATCH '(' FormalParameter ')' Block;

Finally:

	FINALLY Block;

/* 19.12 Productions from S15: Expressions */

Primary:

	PrimaryNoNewArray|

	ArrayCreationExpression;

PrimaryNoNewArray:

	Literal|

	THIS|

	'(' Expression ')'|

	ClassInstanceCreationExpression|

	FieldAccess|

	MethodInvocation|

	ArrayAccess;

ClassInstanceCreationExpression:

	NEW ClassType '(' ArgumentList ')'|
	NEW ClassType '(' ')';

ArgumentList:

	Expression|

	ArgumentList ',' Expression;

ArrayCreationExpression:

	NEW PrimitiveType DimExprs Dims|
	NEW PrimitiveType DimExprs |

	NEW ClassOrInterfaceType DimExprs Dims|
	NEW ClassOrInterfaceType DimExprs ;

DimExprs:

	DimExpr|

	DimExprs DimExpr;

DimExpr:

	'[' Expression ']';

Dims:

	'[' ']'|

	Dims '[' ']';

FieldAccess:

	Primary '.' Identifier|

	SUPER '.' Identifier;

MethodInvocation:

	Name '(' ArgumentList ')'|
	Name '(' ')'|

	Primary '.' Identifier '(' ArgumentList ')'|
	Primary '.' Identifier '(' ')'|

	SUPER '.' Identifier '(' ArgumentList ')'|
	SUPER '.' Identifier '(' ')';

ArrayAccess:

	Name '[' Expression ']'|

	PrimaryNoNewArray '[' Expression ']';

PostfixExpression:

	Primary|

	Name|

	PostIncrementExpression|

	PostDecrementExpression;

PostIncrementExpression:

	PostfixExpression PLUSPLUS;

PostDecrementExpression:

	PostfixExpression MINUSMINUS;

UnaryExpression:

	PreIncrementExpression|

	PreDecrementExpression|

	'+' UnaryExpression|

	'-' UnaryExpression|

	UnaryExpressionNotPlusMinus;

PreIncrementExpression:

	PLUSPLUS UnaryExpression;

PreDecrementExpression:

	MINUSMINUS UnaryExpression;

UnaryExpressionNotPlusMinus:

	PostfixExpression|

	'~' UnaryExpression|

	'!' UnaryExpression|

	CastExpression;

CastExpression:

	'(' PrimitiveType Dims ')' UnaryExpression|
	'(' PrimitiveType ')' UnaryExpression|

	'(' Expression ')' UnaryExpressionNotPlusMinus|

	'(' Name Dims ')' UnaryExpressionNotPlusMinus;

MultiplicativeExpression:

	UnaryExpression|

	MultiplicativeExpression '*' UnaryExpression|

	MultiplicativeExpression '/' UnaryExpression|

	MultiplicativeExpression '%' UnaryExpression;

AdditiveExpression:

	MultiplicativeExpression|

	AdditiveExpression '+' MultiplicativeExpression|

	AdditiveExpression '-' MultiplicativeExpression;

ShiftExpression:

	AdditiveExpression|

	ShiftExpression LTLT AdditiveExpression|

	ShiftExpression GTGT AdditiveExpression|

	ShiftExpression GTGTGT AdditiveExpression;

RelationalExpression:

	ShiftExpression|

	RelationalExpression '<' ShiftExpression|

	RelationalExpression '>' ShiftExpression|

	RelationalExpression LTEQ ShiftExpression|

	RelationalExpression GTEQ ShiftExpression|

	RelationalExpression INSTANCEOF ReferenceType;

EqualityExpression:

	RelationalExpression|

	EqualityExpression EQEQ RelationalExpression|

	EqualityExpression BANGEQ RelationalExpression;

AndExpression:

	EqualityExpression|

	AndExpression '&' EqualityExpression;

ExclusiveOrExpression:

	AndExpression|

	ExclusiveOrExpression '^' AndExpression;

InclusiveOrExpression:

	ExclusiveOrExpression|

	InclusiveOrExpression '|' ExclusiveOrExpression;

ConditionalAndExpression:

	InclusiveOrExpression|

	ConditionalAndExpression ANDAND InclusiveOrExpression;

ConditionalOrExpression:

	ConditionalAndExpression|

	ConditionalOrExpression OROR ConditionalAndExpression;

ConditionalExpression:

	ConditionalOrExpression|

	ConditionalOrExpression '?' Expression ':' ConditionalExpression;

AssignmentExpression:

	ConditionalExpression|

	Assignment;

Assignment:

	LeftHandSide AssignmentOperator AssignmentExpression;

LeftHandSide:

	Name|

	FieldAccess|

	ArrayAccess;

AssignmentOperator: 

	'='| TIMESEQ | DIVEQ | MODEQ | PLUSEQ | MINUSEQ |
        LTLTEQ | GTGTEQ | GTGTGTEQ | ANDEQ | XOREQ | OREQ ;

Expression:

	AssignmentExpression;

ConstantExpression:

	Expression;

%%

def yyerror(s:String) = println(s);
