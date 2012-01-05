/* A Bison parser, made by GNU Bison 1.875.  */

/* Skeleton parser for Yacc-like parsing with Bison,
   Copyright (C) 1984, 1989, 1990, 2000, 2001, 2002 Free Software Foundation, Inc.

   This program is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2, or (at your option)
   any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program; if not, write to the Free Software
   Foundation, Inc., 59 Temple Place - Suite 330,
   Boston, MA 02111-1307, USA.  */

/* As a special exception, when this file is copied by Bison into a
   Bison output file, you may use that output file without restriction.
   This special exception was added by the Free Software Foundation
   in version 1.24 of Bison.  */

/* Written by Richard Stallman by simplifying the original so called
   ``semantic'' parser.  */

/* All symbols defined below should begin with yy or YY, to avoid
   infringing on user name space.  This should be done even for local
   variables, as they might otherwise be expanded by user macros.
   There are some unavoidable exceptions within include files to
   define necessary library symbols; they are noted "INFRINGES ON
   USER NAME SPACE" below.  */

/* Identify Bison output.  */
#define YYBISON 1

/* Skeleton name.  */
#define YYSKELETON_NAME "yacc.c"

/* Pure parsers.  */
#define YYPURE 0

/* Using locations.  */
#define YYLSP_NEEDED 0



/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     CASE = 258,
     CLASS = 259,
     DEF = 260,
     ELSE = 261,
     EQ = 262,
     EXTENDS = 263,
     IF = 264,
     MATCH = 265,
     NATIVE = 266,
     NEW = 267,
     NULL = 268,
     NOT = 269,
     OVERRIDE = 270,
     SUPER = 271,
     VAR = 272,
     WHILE = 273,
     STR_LIT = 274,
     INT_LIT = 275,
     BOOL_LIT = 276,
     TYPEID = 277,
     OBJECTID = 278,
     EQEQ = 279,
     LE = 280,
     ARROW = 281,
     ERROR = 282,
     UNARY = 283
   };
#endif
#define CASE 258
#define CLASS 259
#define DEF 260
#define ELSE 261
#define EQ 262
#define EXTENDS 263
#define IF 264
#define MATCH 265
#define NATIVE 266
#define NEW 267
#define NULL 268
#define NOT 269
#define OVERRIDE 270
#define SUPER 271
#define VAR 272
#define WHILE 273
#define STR_LIT 274
#define INT_LIT 275
#define BOOL_LIT 276
#define TYPEID 277
#define OBJECTID 278
#define EQEQ 279
#define LE 280
#define ARROW 281
#define ERROR 282
#define UNARY 283




/* Copy the first part of user declarations.  */
#line 6 "Cool.y"

package edu.uwm.cs.cool.parser;
import edu.uwm.cs.cool.tree._;
import edu.uwm.cs.cool.scanner.CoolScanner;



/* Enabling traces.  */
#ifndef YYDEBUG
# define YYDEBUG 0
#endif

/* Enabling verbose error messages.  */
#ifdef YYERROR_VERBOSE
# undef YYERROR_VERBOSE
# define YYERROR_VERBOSE 1
#else
# define YYERROR_VERBOSE 0
#endif

#if ! defined (YYSTYPE) && ! defined (YYSTYPE_IS_DECLARED)
typedef int YYSTYPE;
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
# define YYSTYPE_IS_TRIVIAL 1
#endif



/* Copy the second part of user declarations.  */


/* Line 214 of yacc.c.  */
#line 149 "Cool.tab.c"

#if ! defined (yyoverflow) || YYERROR_VERBOSE

/* The parser invokes alloca or malloc; define the necessary symbols.  */

# if YYSTACK_USE_ALLOCA
#  define YYSTACK_ALLOC alloca
# else
#  ifndef YYSTACK_USE_ALLOCA
#   if defined (alloca) || defined (_ALLOCA_H)
#    define YYSTACK_ALLOC alloca
#   else
#    ifdef __GNUC__
#     define YYSTACK_ALLOC __builtin_alloca
#    endif
#   endif
#  endif
# endif

# ifdef YYSTACK_ALLOC
   /* Pacify GCC's `empty if-body' warning. */
#  define YYSTACK_FREE(Ptr) do { /* empty */; } while (0)
# else
#  if defined (__STDC__) || defined (__cplusplus)
#   include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
#   define YYSIZE_T size_t
#  endif
#  define YYSTACK_ALLOC malloc
#  define YYSTACK_FREE free
# endif
#endif /* ! defined (yyoverflow) || YYERROR_VERBOSE */


#if (! defined (yyoverflow) \
     && (! defined (__cplusplus) \
	 || (YYSTYPE_IS_TRIVIAL)))

/* A type that is properly aligned for any stack member.  */
union yyalloc
{
  short yyss;
  YYSTYPE yyvs;
  };

/* The size of the maximum gap between one aligned stack and the next.  */
# define YYSTACK_GAP_MAXIMUM (sizeof (union yyalloc) - 1)

/* The size of an array large to enough to hold all stacks, each with
   N elements.  */
# define YYSTACK_BYTES(N) \
     ((N) * (sizeof (short) + sizeof (YYSTYPE))				\
      + YYSTACK_GAP_MAXIMUM)

/* Copy COUNT objects from FROM to TO.  The source and destination do
   not overlap.  */
# ifndef YYCOPY
#  if 1 < __GNUC__
#   define YYCOPY(To, From, Count) \
      __builtin_memcpy (To, From, (Count) * sizeof (*(From)))
#  else
#   define YYCOPY(To, From, Count)		\
      do					\
	{					\
	  register YYSIZE_T yyi;		\
	  for (yyi = 0; yyi < (Count); yyi++)	\
	    (To)[yyi] = (From)[yyi];		\
	}					\
      while (0)
#  endif
# endif

/* Relocate STACK from its old location to the new one.  The
   local variables YYSIZE and YYSTACKSIZE give the old and new number of
   elements in the stack, and YYPTR gives the new location of the
   stack.  Advance YYPTR to a properly aligned location for the next
   stack.  */
# define YYSTACK_RELOCATE(Stack)					\
    do									\
      {									\
	YYSIZE_T yynewbytes;						\
	YYCOPY (&yyptr->Stack, Stack, yysize);				\
	Stack = &yyptr->Stack;						\
	yynewbytes = yystacksize * sizeof (*Stack) + YYSTACK_GAP_MAXIMUM; \
	yyptr += yynewbytes / sizeof (*yyptr);				\
      }									\
    while (0)

#endif

#if defined (__STDC__) || defined (__cplusplus)
   typedef signed char yysigned_char;
#else
   typedef short yysigned_char;
#endif

/* YYFINAL -- State number of the termination state. */
#define YYFINAL  8
/* YYLAST -- Last index in YYTABLE.  */
#define YYLAST   346

/* YYNTOKENS -- Number of terminals. */
#define YYNTOKENS  44
/* YYNNTS -- Number of nonterminals. */
#define YYNNTS  19
/* YYNRULES -- Number of rules. */
#define YYNRULES  68
/* YYNRULES -- Number of states. */
#define YYNSTATES  150

/* YYTRANSLATE(YYLEX) -- Bison symbol number corresponding to YYLEX.  */
#define YYUNDEFTOK  2
#define YYMAXUTOK   283

#define YYTRANSLATE(YYX) 						\
  ((unsigned int) (YYX) <= YYMAXUTOK ? yytranslate[YYX] : YYUNDEFTOK)

/* YYTRANSLATE[YYLEX] -- Bison symbol number corresponding to YYLEX.  */
static const unsigned char yytranslate[] =
{
       0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,    43,     2,     2,     2,     2,     2,     2,
      40,    41,    32,    31,    42,    30,    35,    33,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,    39,    36,
      29,    28,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,    37,     2,    38,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22,    23,    24,
      25,    26,    27,    34
};

#if YYDEBUG
/* YYPRHS[YYN] -- Index of the first RHS symbol of rule number YYN in
   YYRHS.  */
static const unsigned char yyprhs[] =
{
       0,     0,     3,     5,     7,     9,    12,    15,    19,    27,
      28,    32,    35,    36,    40,    44,    48,    53,    62,    70,
      77,    82,    84,    85,    88,    92,    96,    98,   102,   106,
     110,   115,   120,   128,   134,   138,   144,   148,   152,   156,
     160,   164,   167,   171,   175,   179,   183,   186,   190,   192,
     194,   196,   198,   200,   203,   205,   208,   210,   212,   216,
     225,   229,   232,   236,   238,   242,   244,   246,   249
};

/* YYRHS -- A `-1'-separated list of the rules' RHS. */
static const yysigned_char yyrhs[] =
{
      45,     0,    -1,    46,    -1,     1,    -1,    47,    -1,     1,
      36,    -1,    46,    47,    -1,    46,     1,    36,    -1,     4,
      22,    52,    48,    37,    49,    38,    -1,    -1,     8,    22,
      58,    -1,     8,    11,    -1,    -1,    49,    50,    36,    -1,
      49,     1,    36,    -1,    49,    11,    36,    -1,    49,    37,
      56,    38,    -1,    51,     5,    23,    52,    39,    22,    28,
      55,    -1,    51,     5,    23,    52,    39,    22,    11,    -1,
      17,    23,    39,    22,    28,    55,    -1,    17,    23,    39,
      11,    -1,    15,    -1,    -1,    40,    41,    -1,    40,    53,
      41,    -1,    40,     1,    41,    -1,    54,    -1,    53,    42,
      54,    -1,    23,    39,    22,    -1,    23,    28,    55,    -1,
      16,    35,    23,    58,    -1,    55,    35,    23,    58,    -1,
       9,    40,    55,    41,    55,     6,    55,    -1,    18,    40,
      55,    41,    55,    -1,    37,    56,    38,    -1,    55,    10,
      37,    60,    38,    -1,    12,    22,    58,    -1,    55,    31,
      55,    -1,    55,    30,    55,    -1,    55,    32,    55,    -1,
      55,    33,    55,    -1,    30,    55,    -1,    55,    29,    55,
      -1,    55,     7,    55,    -1,    55,    24,    55,    -1,    55,
      25,    55,    -1,    43,    55,    -1,    40,    55,    41,    -1,
      13,    -1,    20,    -1,    19,    -1,    21,    -1,    23,    -1,
      23,    58,    -1,    57,    -1,    55,    36,    -1,    55,    -1,
       1,    -1,    55,    36,    57,    -1,    17,    23,    39,    22,
      28,    55,    36,    57,    -1,     1,    36,    57,    -1,    40,
      41,    -1,    40,    59,    41,    -1,    55,    -1,    59,    42,
      55,    -1,    61,    -1,    62,    -1,    61,    62,    -1,     3,
      23,    39,    22,    26,    55,    -1
};

/* YYRLINE[YYN] -- source line where rule number YYN was defined.  */
static const unsigned short yyrline[] =
{
       0,    70,    70,    71,    75,    77,    79,    81,    86,    94,
      97,   101,   110,   111,   113,   115,   117,   122,   124,   126,
     129,   139,   140,   143,   145,   147,   152,   154,   158,   162,
     164,   167,   169,   171,   173,   175,   177,   179,   181,   183,
     185,   187,   189,   191,   193,   195,   197,   199,   201,   203,
     205,   207,   209,   211,   217,   221,   224,   226,   228,   230,
     232,   236,   238,   243,   245,   250,   261,   263,   268
};
#endif

#if YYDEBUG || YYERROR_VERBOSE
/* YYTNME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
   First, the terminals, then, starting at YYNTOKENS, nonterminals. */
static const char *const yytname[] =
{
  "$end", "error", "$undefined", "CASE", "CLASS", "DEF", "ELSE", "EQ", 
  "EXTENDS", "IF", "MATCH", "NATIVE", "NEW", "NULL", "NOT", "OVERRIDE", 
  "SUPER", "VAR", "WHILE", "STR_LIT", "INT_LIT", "BOOL_LIT", "TYPEID", 
  "OBJECTID", "EQEQ", "LE", "ARROW", "ERROR", "'='", "'<'", "'-'", "'+'", 
  "'*'", "'/'", "UNARY", "'.'", "';'", "'{'", "'}'", "':'", "'('", "')'", 
  "','", "'!'", "$accept", "program", "class_list", "class_decl", 
  "superclass", "feature_list", "feature", "opt_override", "formals", 
  "formal_list", "formal", "expr", "block", "stmt_list", "actuals", 
  "exp_list", "case_list", "simple_cases", "simple_case", 0
};
#endif

# ifdef YYPRINT
/* YYTOKNUM[YYLEX-NUM] -- Internal token number corresponding to
   token YYLEX-NUM.  */
static const unsigned short yytoknum[] =
{
       0,   256,   257,   258,   259,   260,   261,   262,   263,   264,
     265,   266,   267,   268,   269,   270,   271,   272,   273,   274,
     275,   276,   277,   278,   279,   280,   281,   282,    61,    60,
      45,    43,    42,    47,   283,    46,    59,   123,   125,    58,
      40,    41,    44,    33
};
# endif

/* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */
static const unsigned char yyr1[] =
{
       0,    44,    45,    45,    46,    46,    46,    46,    47,    48,
      48,    48,    49,    49,    49,    49,    49,    50,    50,    50,
      50,    51,    51,    52,    52,    52,    53,    53,    54,    55,
      55,    55,    55,    55,    55,    55,    55,    55,    55,    55,
      55,    55,    55,    55,    55,    55,    55,    55,    55,    55,
      55,    55,    55,    55,    56,    57,    57,    57,    57,    57,
      57,    58,    58,    59,    59,    60,    61,    61,    62
};

/* YYR2[YYN] -- Number of symbols composing right hand side of rule YYN.  */
static const unsigned char yyr2[] =
{
       0,     2,     1,     1,     1,     2,     2,     3,     7,     0,
       3,     2,     0,     3,     3,     3,     4,     8,     7,     6,
       4,     1,     0,     2,     3,     3,     1,     3,     3,     3,
       4,     4,     7,     5,     3,     5,     3,     3,     3,     3,
       3,     2,     3,     3,     3,     3,     2,     3,     1,     1,
       1,     1,     1,     2,     1,     2,     1,     1,     3,     8,
       3,     2,     3,     1,     3,     1,     1,     2,     6
};

/* YYDEFACT[STATE-NAME] -- Default rule to reduce with in state
   STATE-NUM when YYTABLE doesn't specify something else to do.  Zero
   means the default is an error.  */
static const unsigned char yydefact[] =
{
       0,     3,     0,     0,     0,     4,     5,     0,     1,     0,
       6,     0,     9,     7,     0,     0,    23,     0,    26,     0,
       0,    25,     0,    24,     0,    11,     0,    12,    28,    27,
       0,    10,     0,     0,     0,    48,     0,     0,    50,    49,
      51,    52,     0,     0,     0,    61,     0,    63,     0,     0,
       0,    21,     0,     0,     8,     0,     0,     0,     0,     0,
       0,     0,    53,    41,    57,     0,    56,     0,    54,     0,
      46,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,    62,     0,    14,    15,     0,     0,    13,     0,     0,
      36,     0,     0,    29,     0,     0,     0,    34,    47,    43,
       0,    44,    45,    42,    38,    37,    39,    40,     0,    64,
       0,    16,     0,     0,    30,     0,    60,     0,    58,     0,
       0,    65,    66,    31,    20,     0,     0,     0,    33,     0,
       0,    35,    67,     0,     0,     0,     0,     0,    19,     0,
      32,     0,     0,    18,     0,     0,     0,    17,    59,    68
};

/* YYDEFGOTO[NTERM-NUM]. */
static const yysigned_char yydefgoto[] =
{
      -1,     3,     4,     5,    20,    32,    55,    56,    12,    17,
      18,    66,    67,    68,    31,    48,   120,   121,   122
};

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
#define YYPACT_NINF -93
static const short yypact[] =
{
       5,   -25,    -1,    29,    24,   -93,   -93,    -7,   -93,     1,
     -93,     9,    34,   -93,    13,    21,   -93,    15,   -93,     4,
      25,   -93,    39,   -93,    41,   -93,    36,   -93,   -93,   -93,
     121,   -93,     2,    38,    43,   -93,    44,    40,   -93,   -93,
     -93,   -20,   147,   106,   147,   -93,   147,   284,    17,    33,
      45,   -93,    59,   106,   -93,    50,    82,   147,    36,    65,
     147,   147,   -93,    55,    53,    70,   231,    57,   -93,   164,
      55,   147,    61,   147,   147,   147,   147,   147,   147,   147,
      73,   -93,   147,   -93,   -93,    60,    62,   -93,    78,   191,
     -93,    36,   204,   298,   106,    63,    54,   -93,   -93,   311,
     101,   311,   311,   311,     3,     3,    55,    55,    36,   284,
      12,   -93,    -7,   147,   -93,   147,   -93,    86,   -93,    87,
      71,   101,   -93,   -93,   -93,    83,    74,   244,   284,    84,
      81,   -93,   -93,   147,    95,   147,   147,    99,   284,    -6,
     284,   271,   102,   -93,   147,   106,   147,   284,   -93,   284
};

/* YYPGOTO[NTERM-NUM].  */
static const yysigned_char yypgoto[] =
{
     -93,   -93,   -93,   127,   -93,   -93,   -93,   -93,    20,   -93,
     111,   -30,    85,   -92,   -40,   -93,   -93,   -93,    26
};

/* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
   positive, shift that token.  If negative, reduce the rule which
   number is the opposite.  If zero, do what YYDEFACT says.
   If YYTABLE_NINF, syntax error.  */
#define YYTABLE_NINF -56
static const short yytable[] =
{
      47,    62,   116,    49,   118,   143,     1,   -22,    61,     2,
      14,     6,    63,    50,    69,    25,    70,    51,    90,    52,
      30,     7,   144,   124,    -2,     9,    26,    89,     2,     8,
      92,    93,    15,    11,   125,    78,    79,    13,    80,    53,
      54,    99,    19,   101,   102,   103,   104,   105,   106,   107,
      16,   114,   109,   148,    21,    64,    23,    24,    81,    82,
      22,    28,    27,    33,    15,    58,    34,    35,   123,    83,
      36,    65,    37,    38,    39,    40,    30,    41,    57,    59,
      60,    84,    85,   127,    42,   128,    87,    88,    91,    94,
      80,    43,   -55,    95,    44,    97,   108,    46,   100,   110,
     111,   112,   117,   138,   119,   140,   141,    64,   129,   131,
     130,   133,   136,   134,   147,    33,   149,   139,    34,    35,
     137,   142,    36,    65,    37,    38,    39,    40,   146,    41,
      33,    10,   126,    34,    35,    29,    42,    36,    86,    37,
      38,    39,    40,    43,    41,     0,    44,   132,     0,    46,
       0,    42,     0,     0,     0,     0,    33,     0,    43,    34,
      35,    44,    45,    36,    46,    37,    38,    39,    40,     0,
      41,    71,     0,     0,    72,     0,     0,    42,     0,     0,
       0,     0,     0,     0,    43,     0,     0,    44,    73,    74,
      46,     0,     0,    75,    76,    77,    78,    79,    71,    80,
       0,    72,     0,     0,     0,    98,     0,     0,     0,     0,
       0,    71,     0,     0,    72,    73,    74,     0,     0,     0,
      75,    76,    77,    78,    79,     0,    80,     0,    73,    74,
       0,     0,   113,    75,    76,    77,    78,    79,    71,    80,
       0,    72,     0,     0,     0,   115,     0,     0,     0,     0,
     135,    71,     0,     0,    72,    73,    74,     0,     0,     0,
      75,    76,    77,    78,    79,     0,    80,    96,    73,    74,
       0,     0,     0,    75,    76,    77,    78,    79,    71,    80,
       0,    72,     0,     0,     0,     0,     0,     0,     0,     0,
       0,    71,     0,     0,    72,    73,    74,     0,     0,     0,
      75,    76,    77,    78,    79,    71,    80,   145,    73,    74,
       0,     0,     0,    75,    76,    77,    78,    79,   -56,    80,
       0,     0,    73,    74,     0,     0,     0,    75,    76,    77,
      78,    79,     0,    80,     0,   -56,   -56,     0,     0,     0,
     -56,    76,    77,    78,    79,     0,    80
};

static const short yycheck[] =
{
      30,    41,    94,     1,    96,    11,     1,     5,    28,     4,
       1,    36,    42,    11,    44,    11,    46,    15,    58,    17,
      40,    22,    28,    11,     0,     1,    22,    57,     4,     0,
      60,    61,    23,    40,    22,    32,    33,    36,    35,    37,
      38,    71,     8,    73,    74,    75,    76,    77,    78,    79,
      41,    91,    82,   145,    41,     1,    41,    42,    41,    42,
      39,    22,    37,     9,    23,    22,    12,    13,   108,    36,
      16,    17,    18,    19,    20,    21,    40,    23,    40,    35,
      40,    36,    23,   113,    30,   115,    36,     5,    23,    36,
      35,    37,    38,    23,    40,    38,    23,    43,    37,    39,
      38,    23,    39,   133,     3,   135,   136,     1,    22,    38,
      23,    28,    28,    39,   144,     9,   146,    22,    12,    13,
      39,    22,    16,    17,    18,    19,    20,    21,    26,    23,
       9,     4,   112,    12,    13,    24,    30,    16,    53,    18,
      19,    20,    21,    37,    23,    -1,    40,   121,    -1,    43,
      -1,    30,    -1,    -1,    -1,    -1,     9,    -1,    37,    12,
      13,    40,    41,    16,    43,    18,    19,    20,    21,    -1,
      23,     7,    -1,    -1,    10,    -1,    -1,    30,    -1,    -1,
      -1,    -1,    -1,    -1,    37,    -1,    -1,    40,    24,    25,
      43,    -1,    -1,    29,    30,    31,    32,    33,     7,    35,
      -1,    10,    -1,    -1,    -1,    41,    -1,    -1,    -1,    -1,
      -1,     7,    -1,    -1,    10,    24,    25,    -1,    -1,    -1,
      29,    30,    31,    32,    33,    -1,    35,    -1,    24,    25,
      -1,    -1,    41,    29,    30,    31,    32,    33,     7,    35,
      -1,    10,    -1,    -1,    -1,    41,    -1,    -1,    -1,    -1,
       6,     7,    -1,    -1,    10,    24,    25,    -1,    -1,    -1,
      29,    30,    31,    32,    33,    -1,    35,    36,    24,    25,
      -1,    -1,    -1,    29,    30,    31,    32,    33,     7,    35,
      -1,    10,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,     7,    -1,    -1,    10,    24,    25,    -1,    -1,    -1,
      29,    30,    31,    32,    33,     7,    35,    36,    24,    25,
      -1,    -1,    -1,    29,    30,    31,    32,    33,     7,    35,
      -1,    -1,    24,    25,    -1,    -1,    -1,    29,    30,    31,
      32,    33,    -1,    35,    -1,    24,    25,    -1,    -1,    -1,
      29,    30,    31,    32,    33,    -1,    35
};

/* YYSTOS[STATE-NUM] -- The (internal number of the) accessing
   symbol of state STATE-NUM.  */
static const unsigned char yystos[] =
{
       0,     1,     4,    45,    46,    47,    36,    22,     0,     1,
      47,    40,    52,    36,     1,    23,    41,    53,    54,     8,
      48,    41,    39,    41,    42,    11,    22,    37,    22,    54,
      40,    58,    49,     9,    12,    13,    16,    18,    19,    20,
      21,    23,    30,    37,    40,    41,    43,    55,    59,     1,
      11,    15,    17,    37,    38,    50,    51,    40,    22,    35,
      40,    28,    58,    55,     1,    17,    55,    56,    57,    55,
      55,     7,    10,    24,    25,    29,    30,    31,    32,    33,
      35,    41,    42,    36,    36,    23,    56,    36,     5,    55,
      58,    23,    55,    55,    36,    23,    36,    38,    41,    55,
      37,    55,    55,    55,    55,    55,    55,    55,    23,    55,
      39,    38,    23,    41,    58,    41,    57,    39,    57,     3,
      60,    61,    62,    58,    11,    22,    52,    55,    55,    22,
      23,    38,    62,    28,    39,     6,    28,    39,    55,    22,
      55,    55,    22,    11,    28,    36,    26,    55,    57,    55
};

#if ! defined (YYSIZE_T) && defined (__SIZE_TYPE__)
# define YYSIZE_T __SIZE_TYPE__
#endif
#if ! defined (YYSIZE_T) && defined (size_t)
# define YYSIZE_T size_t
#endif
#if ! defined (YYSIZE_T)
# if defined (__STDC__) || defined (__cplusplus)
#  include <stddef.h> /* INFRINGES ON USER NAME SPACE */
#  define YYSIZE_T size_t
# endif
#endif
#if ! defined (YYSIZE_T)
# define YYSIZE_T unsigned int
#endif

#define yyerrok		(yyerrstatus = 0)
#define yyclearin	(yychar = YYEMPTY)
#define YYEMPTY		(-2)
#define YYEOF		0

#define YYACCEPT	goto yyacceptlab
#define YYABORT		goto yyabortlab
#define YYERROR		goto yyerrlab1

/* Like YYERROR except do call yyerror.  This remains here temporarily
   to ease the transition to the new meaning of YYERROR, for GCC.
   Once GCC version 2 has supplanted version 1, this can go.  */

#define YYFAIL		goto yyerrlab

#define YYRECOVERING()  (!!yyerrstatus)

#define YYBACKUP(Token, Value)					\
do								\
  if (yychar == YYEMPTY && yylen == 1)				\
    {								\
      yychar = (Token);						\
      yylval = (Value);						\
      yytoken = YYTRANSLATE (yychar);				\
      YYPOPSTACK;						\
      goto yybackup;						\
    }								\
  else								\
    { 								\
      yyerror ("syntax error: cannot back up");\
      YYERROR;							\
    }								\
while (0)

#define YYTERROR	1
#define YYERRCODE	256

/* YYLLOC_DEFAULT -- Compute the default location (before the actions
   are run).  */

#ifndef YYLLOC_DEFAULT
# define YYLLOC_DEFAULT(Current, Rhs, N)         \
  Current.first_line   = Rhs[1].first_line;      \
  Current.first_column = Rhs[1].first_column;    \
  Current.last_line    = Rhs[N].last_line;       \
  Current.last_column  = Rhs[N].last_column;
#endif

/* YYLEX -- calling `yylex' with the right arguments.  */

#ifdef YYLEX_PARAM
# define YYLEX yylex (YYLEX_PARAM)
#else
# define YYLEX yylex ()
#endif

/* Enable debugging if requested.  */
#if YYDEBUG

# ifndef YYFPRINTF
#  include <stdio.h> /* INFRINGES ON USER NAME SPACE */
#  define YYFPRINTF fprintf
# endif

# define YYDPRINTF(Args)			\
do {						\
  if (yydebug)					\
    YYFPRINTF Args;				\
} while (0)

# define YYDSYMPRINT(Args)			\
do {						\
  if (yydebug)					\
    yysymprint Args;				\
} while (0)

# define YYDSYMPRINTF(Title, Token, Value, Location)		\
do {								\
  if (yydebug)							\
    {								\
      YYFPRINTF (stderr, "%s ", Title);				\
      yysymprint (stderr, 					\
                  Token, Value);	\
      YYFPRINTF (stderr, "\n");					\
    }								\
} while (0)

/*------------------------------------------------------------------.
| yy_stack_print -- Print the state stack from its BOTTOM up to its |
| TOP (cinluded).                                                   |
`------------------------------------------------------------------*/

#if defined (__STDC__) || defined (__cplusplus)
static void
yy_stack_print (short *bottom, short *top)
#else
static void
yy_stack_print (bottom, top)
    short *bottom;
    short *top;
#endif
{
  YYFPRINTF (stderr, "Stack now");
  for (/* Nothing. */; bottom <= top; ++bottom)
    YYFPRINTF (stderr, " %d", *bottom);
  YYFPRINTF (stderr, "\n");
}

# define YY_STACK_PRINT(Bottom, Top)				\
do {								\
  if (yydebug)							\
    yy_stack_print ((Bottom), (Top));				\
} while (0)


/*------------------------------------------------.
| Report that the YYRULE is going to be reduced.  |
`------------------------------------------------*/

#if defined (__STDC__) || defined (__cplusplus)
static void
yy_reduce_print (int yyrule)
#else
static void
yy_reduce_print (yyrule)
    int yyrule;
#endif
{
  int yyi;
  unsigned int yylineno = yyrline[yyrule];
  YYFPRINTF (stderr, "Reducing stack by rule %d (line %u), ",
             yyrule - 1, yylineno);
  /* Print the symbols being reduced, and their result.  */
  for (yyi = yyprhs[yyrule]; 0 <= yyrhs[yyi]; yyi++)
    YYFPRINTF (stderr, "%s ", yytname [yyrhs[yyi]]);
  YYFPRINTF (stderr, "-> %s\n", yytname [yyr1[yyrule]]);
}

# define YY_REDUCE_PRINT(Rule)		\
do {					\
  if (yydebug)				\
    yy_reduce_print (Rule);		\
} while (0)

/* Nonzero means print parse trace.  It is left uninitialized so that
   multiple parsers can coexist.  */
int yydebug;
#else /* !YYDEBUG */
# define YYDPRINTF(Args)
# define YYDSYMPRINT(Args)
# define YYDSYMPRINTF(Title, Token, Value, Location)
# define YY_STACK_PRINT(Bottom, Top)
# define YY_REDUCE_PRINT(Rule)
#endif /* !YYDEBUG */


/* YYINITDEPTH -- initial size of the parser's stacks.  */
#ifndef	YYINITDEPTH
# define YYINITDEPTH 200
#endif

/* YYMAXDEPTH -- maximum size the stacks can grow to (effective only
   if the built-in stack extension method is used).

   Do not make this value too large; the results are undefined if
   SIZE_MAX < YYSTACK_BYTES (YYMAXDEPTH)
   evaluated with infinite-precision integer arithmetic.  */

#if YYMAXDEPTH == 0
# undef YYMAXDEPTH
#endif

#ifndef YYMAXDEPTH
# define YYMAXDEPTH 10000
#endif



#if YYERROR_VERBOSE

# ifndef yystrlen
#  if defined (__GLIBC__) && defined (_STRING_H)
#   define yystrlen strlen
#  else
/* Return the length of YYSTR.  */
static YYSIZE_T
#   if defined (__STDC__) || defined (__cplusplus)
yystrlen (const char *yystr)
#   else
yystrlen (yystr)
     const char *yystr;
#   endif
{
  register const char *yys = yystr;

  while (*yys++ != '\0')
    continue;

  return yys - yystr - 1;
}
#  endif
# endif

# ifndef yystpcpy
#  if defined (__GLIBC__) && defined (_STRING_H) && defined (_GNU_SOURCE)
#   define yystpcpy stpcpy
#  else
/* Copy YYSRC to YYDEST, returning the address of the terminating '\0' in
   YYDEST.  */
static char *
#   if defined (__STDC__) || defined (__cplusplus)
yystpcpy (char *yydest, const char *yysrc)
#   else
yystpcpy (yydest, yysrc)
     char *yydest;
     const char *yysrc;
#   endif
{
  register char *yyd = yydest;
  register const char *yys = yysrc;

  while ((*yyd++ = *yys++) != '\0')
    continue;

  return yyd - 1;
}
#  endif
# endif

#endif /* !YYERROR_VERBOSE */



#if YYDEBUG
/*--------------------------------.
| Print this symbol on YYOUTPUT.  |
`--------------------------------*/

#if defined (__STDC__) || defined (__cplusplus)
static void
yysymprint (FILE *yyoutput, int yytype, YYSTYPE *yyvaluep)
#else
static void
yysymprint (yyoutput, yytype, yyvaluep)
    FILE *yyoutput;
    int yytype;
    YYSTYPE *yyvaluep;
#endif
{
  /* Pacify ``unused variable'' warnings.  */
  (void) yyvaluep;

  if (yytype < YYNTOKENS)
    {
      YYFPRINTF (yyoutput, "token %s (", yytname[yytype]);
# ifdef YYPRINT
      YYPRINT (yyoutput, yytoknum[yytype], *yyvaluep);
# endif
    }
  else
    YYFPRINTF (yyoutput, "nterm %s (", yytname[yytype]);

  switch (yytype)
    {
      default:
        break;
    }
  YYFPRINTF (yyoutput, ")");
}

#endif /* ! YYDEBUG */
/*-----------------------------------------------.
| Release the memory associated to this symbol.  |
`-----------------------------------------------*/

#if defined (__STDC__) || defined (__cplusplus)
static void
yydestruct (int yytype, YYSTYPE *yyvaluep)
#else
static void
yydestruct (yytype, yyvaluep)
    int yytype;
    YYSTYPE *yyvaluep;
#endif
{
  /* Pacify ``unused variable'' warnings.  */
  (void) yyvaluep;

  switch (yytype)
    {

      default:
        break;
    }
}


/* Prevent warnings from -Wmissing-prototypes.  */

#ifdef YYPARSE_PARAM
# if defined (__STDC__) || defined (__cplusplus)
int yyparse (void *YYPARSE_PARAM);
# else
int yyparse ();
# endif
#else /* ! YYPARSE_PARAM */
#if defined (__STDC__) || defined (__cplusplus)
int yyparse (void);
#else
int yyparse ();
#endif
#endif /* ! YYPARSE_PARAM */



/* The lookahead symbol.  */
int yychar;

/* The semantic value of the lookahead symbol.  */
YYSTYPE yylval;

/* Number of syntax errors so far.  */
int yynerrs;



/*----------.
| yyparse.  |
`----------*/

#ifdef YYPARSE_PARAM
# if defined (__STDC__) || defined (__cplusplus)
int yyparse (void *YYPARSE_PARAM)
# else
int yyparse (YYPARSE_PARAM)
  void *YYPARSE_PARAM;
# endif
#else /* ! YYPARSE_PARAM */
#if defined (__STDC__) || defined (__cplusplus)
int
yyparse (void)
#else
int
yyparse ()

#endif
#endif
{
  
  register int yystate;
  register int yyn;
  int yyresult;
  /* Number of tokens to shift before error messages enabled.  */
  int yyerrstatus;
  /* Lookahead token as an internal (translated) token number.  */
  int yytoken = 0;

  /* Three stacks and their tools:
     `yyss': related to states,
     `yyvs': related to semantic values,
     `yyls': related to locations.

     Refer to the stacks thru separate pointers, to allow yyoverflow
     to reallocate them elsewhere.  */

  /* The state stack.  */
  short	yyssa[YYINITDEPTH];
  short *yyss = yyssa;
  register short *yyssp;

  /* The semantic value stack.  */
  YYSTYPE yyvsa[YYINITDEPTH];
  YYSTYPE *yyvs = yyvsa;
  register YYSTYPE *yyvsp;



#define YYPOPSTACK   (yyvsp--, yyssp--)

  YYSIZE_T yystacksize = YYINITDEPTH;

  /* The variables used to return semantic value and location from the
     action routines.  */
  YYSTYPE yyval;


  /* When reducing, the number of symbols on the RHS of the reduced
     rule.  */
  int yylen;

  YYDPRINTF ((stderr, "Starting parse\n"));

  yystate = 0;
  yyerrstatus = 0;
  yynerrs = 0;
  yychar = YYEMPTY;		/* Cause a token to be read.  */

  /* Initialize stack pointers.
     Waste one element of value and location stack
     so that they stay on the same level as the state stack.
     The wasted elements are never initialized.  */

  yyssp = yyss;
  yyvsp = yyvs;

  goto yysetstate;

/*------------------------------------------------------------.
| yynewstate -- Push a new state, which is found in yystate.  |
`------------------------------------------------------------*/
 yynewstate:
  /* In all cases, when you get here, the value and location stacks
     have just been pushed. so pushing a state here evens the stacks.
     */
  yyssp++;

 yysetstate:
  *yyssp = yystate;

  if (yyss + yystacksize - 1 <= yyssp)
    {
      /* Get the current used size of the three stacks, in elements.  */
      YYSIZE_T yysize = yyssp - yyss + 1;

#ifdef yyoverflow
      {
	/* Give user a chance to reallocate the stack. Use copies of
	   these so that the &'s don't force the real ones into
	   memory.  */
	YYSTYPE *yyvs1 = yyvs;
	short *yyss1 = yyss;


	/* Each stack pointer address is followed by the size of the
	   data in use in that stack, in bytes.  This used to be a
	   conditional around just the two extra args, but that might
	   be undefined if yyoverflow is a macro.  */
	yyoverflow ("parser stack overflow",
		    &yyss1, yysize * sizeof (*yyssp),
		    &yyvs1, yysize * sizeof (*yyvsp),

		    &yystacksize);

	yyss = yyss1;
	yyvs = yyvs1;
      }
#else /* no yyoverflow */
# ifndef YYSTACK_RELOCATE
      goto yyoverflowlab;
# else
      /* Extend the stack our own way.  */
      if (YYMAXDEPTH <= yystacksize)
	goto yyoverflowlab;
      yystacksize *= 2;
      if (YYMAXDEPTH < yystacksize)
	yystacksize = YYMAXDEPTH;

      {
	short *yyss1 = yyss;
	union yyalloc *yyptr =
	  (union yyalloc *) YYSTACK_ALLOC (YYSTACK_BYTES (yystacksize));
	if (! yyptr)
	  goto yyoverflowlab;
	YYSTACK_RELOCATE (yyss);
	YYSTACK_RELOCATE (yyvs);

#  undef YYSTACK_RELOCATE
	if (yyss1 != yyssa)
	  YYSTACK_FREE (yyss1);
      }
# endif
#endif /* no yyoverflow */

      yyssp = yyss + yysize - 1;
      yyvsp = yyvs + yysize - 1;


      YYDPRINTF ((stderr, "Stack size increased to %lu\n",
		  (unsigned long int) yystacksize));

      if (yyss + yystacksize - 1 <= yyssp)
	YYABORT;
    }

  YYDPRINTF ((stderr, "Entering state %d\n", yystate));

  goto yybackup;

/*-----------.
| yybackup.  |
`-----------*/
yybackup:

/* Do appropriate processing given the current state.  */
/* Read a lookahead token if we need one and don't already have one.  */
/* yyresume: */

  /* First try to decide what to do without reference to lookahead token.  */

  yyn = yypact[yystate];
  if (yyn == YYPACT_NINF)
    goto yydefault;

  /* Not known => get a lookahead token if don't already have one.  */

  /* YYCHAR is either YYEMPTY or YYEOF or a valid lookahead symbol.  */
  if (yychar == YYEMPTY)
    {
      YYDPRINTF ((stderr, "Reading a token: "));
      yychar = YYLEX;
    }

  if (yychar <= YYEOF)
    {
      yychar = yytoken = YYEOF;
      YYDPRINTF ((stderr, "Now at end of input.\n"));
    }
  else
    {
      yytoken = YYTRANSLATE (yychar);
      YYDSYMPRINTF ("Next token is", yytoken, &yylval, &yylloc);
    }

  /* If the proper action on seeing token YYTOKEN is to reduce or to
     detect an error, take that action.  */
  yyn += yytoken;
  if (yyn < 0 || YYLAST < yyn || yycheck[yyn] != yytoken)
    goto yydefault;
  yyn = yytable[yyn];
  if (yyn <= 0)
    {
      if (yyn == 0 || yyn == YYTABLE_NINF)
	goto yyerrlab;
      yyn = -yyn;
      goto yyreduce;
    }

  if (yyn == YYFINAL)
    YYACCEPT;

  /* Shift the lookahead token.  */
  YYDPRINTF ((stderr, "Shifting token %s, ", yytname[yytoken]));

  /* Discard the token being shifted unless it is eof.  */
  if (yychar != YYEOF)
    yychar = YYEMPTY;

  *++yyvsp = yylval;


  /* Count tokens shifted since error; after three, turn off error
     status.  */
  if (yyerrstatus)
    yyerrstatus--;

  yystate = yyn;
  goto yynewstate;


/*-----------------------------------------------------------.
| yydefault -- do the default action for the current state.  |
`-----------------------------------------------------------*/
yydefault:
  yyn = yydefact[yystate];
  if (yyn == 0)
    goto yyerrlab;
  goto yyreduce;


/*-----------------------------.
| yyreduce -- Do a reduction.  |
`-----------------------------*/
yyreduce:
  /* yyn is the number of a rule to reduce with.  */
  yylen = yyr2[yyn];

  /* If YYLEN is nonzero, implement the default value of the action:
     `$$ = $1'.

     Otherwise, the following line sets YYVAL to garbage.
     This behavior is undocumented and Bison
     users should not rely upon it.  Assigning to YYVAL
     unconditionally makes the parser a bit smaller, and it avoids a
     GCC warning that YYVAL may be used uninitialized.  */
  yyval = yyvsp[1-yylen];


  YY_REDUCE_PRINT (yyn);
  switch (yyn)
    {
        case 2:
#line 70 "Cool.y"
    { result = program(yyvsp[0].Classes); ;}
    break;

  case 4:
#line 76 "Cool.y"
    { yyval.Classes = List(yyvsp[0].Class_); ;}
    break;

  case 5:
#line 78 "Cool.y"
    { yyval.Classes = Nil; ;}
    break;

  case 6:
#line 80 "Cool.y"
    { yyval.Classes = yyvsp[-1].Classes + yyvsp[0].Class_; ;}
    break;

  case 7:
#line 82 "Cool.y"
    { yyval.Classes = yyvsp[-2].Classes; ;}
    break;

  case 8:
#line 87 "Cool.y"
    { yyval.Class_ = class_(yyvsp[-5].Symbol,yyvsp[-3].Symbol,
			      make_constructor(yyvsp[-5].Symbol,yyvsp[-4].Formals) :: yyvsp[-1].Features,
			      Symbol(filename)); ;}
    break;

  case 9:
#line 94 "Cool.y"
    { superclass_name = Symbol("Object"); 
                  yyval.Symbol = superclass_name;
                  add_supercall(superclass_name,Nil); ;}
    break;

  case 10:
#line 98 "Cool.y"
    { superclass_name = yyvsp[-1].Symbol; 
		  yyval.Symbol = superclass_name;
		  add_supercall(yyvsp[-1].Symbol,yyvsp[0].Expressions); ;}
    break;

  case 11:
#line 102 "Cool.y"
    { superclass_name = null; 
		  yyval.Symbol = null;
		  native_constructor(); ;}
    break;

  case 12:
#line 110 "Cool.y"
    {  yyval.Features = Nil; ;}
    break;

  case 13:
#line 112 "Cool.y"
    {  yyval.Features = yyvsp[-2].Features + yyvsp[-1].Feature; ;}
    break;

  case 14:
#line 114 "Cool.y"
    {  yyval.Features = yyvsp[-2].Features; ;}
    break;

  case 15:
#line 116 "Cool.y"
    { yyval.Features = yyvsp[-2].Features; native_constructor(); ;}
    break;

  case 16:
#line 118 "Cool.y"
    { add_to_constructor(yyvsp[-1].Expression);
		  yyval.Features = yyvsp[-3].Features; ;}
    break;

  case 17:
#line 123 "Cool.y"
    { yyval.Feature = method(yyvsp[-7].Boolean,yyvsp[-5].Symbol,yyvsp[-4].Formals,yyvsp[-2].Symbol,yyvsp[0].Expression); ;}
    break;

  case 18:
#line 125 "Cool.y"
    { yyval.Feature = method(yyvsp[-6].Boolean,yyvsp[-4].Symbol,yyvsp[-3].Formals,yyvsp[-1].Symbol,no_expr()); ;}
    break;

  case 19:
#line 127 "Cool.y"
    { yyval.Feature = attr(yyvsp[-4].Symbol,yyvsp[-2].Symbol);
		  add_to_constructor(assign(yyvsp[-4].Symbol,yyvsp[0].Expression)); ;}
    break;

  case 20:
#line 130 "Cool.y"
    { yyval.Feature = attr(yyvsp[-2].Symbol,Symbol("native"));
		  /* if you have a native field, you cannot be inherited */
		  current_inherit_status = false; 
		  /* and your constructor is native */
		  native_constructor();
		;}
    break;

  case 21:
#line 139 "Cool.y"
    { yyval.Boolean = true; ;}
    break;

  case 22:
#line 140 "Cool.y"
    { yyval.Boolean = false; ;}
    break;

  case 23:
#line 144 "Cool.y"
    { yyval.Formals = Nil; ;}
    break;

  case 24:
#line 146 "Cool.y"
    { yyval.Formals = yyvsp[-1].Formals; ;}
    break;

  case 25:
#line 148 "Cool.y"
    { yyval.Formals = Nil;  ;}
    break;

  case 26:
#line 153 "Cool.y"
    {  yyval.Formals = List(yyvsp[0].Formal); ;}
    break;

  case 27:
#line 155 "Cool.y"
    { yyval.Formals = yyvsp[-2].Formals + yyvsp[0].Formal; ;}
    break;

  case 28:
#line 159 "Cool.y"
    {  yyval.Formal = formal(yyvsp[-2].Symbol,yyvsp[0].Symbol); ;}
    break;

  case 29:
#line 163 "Cool.y"
    { yyval.Expression = assign(yyvsp[-2].Symbol,yyvsp[0].Expression); ;}
    break;

  case 30:
#line 165 "Cool.y"
    { val this_obj : Expression = variable(Symbol("this"));
	    yyval.Expression = static_dispatch(this_obj,superclass_name,yyvsp[-1].Symbol,yyvsp[0].Expressions); ;}
    break;

  case 31:
#line 168 "Cool.y"
    { yyval.Expression = dispatch(yyvsp[-3].Expression,yyvsp[-1].Symbol,yyvsp[0].Expressions); ;}
    break;

  case 32:
#line 170 "Cool.y"
    { yyval.Expression = cond(yyvsp[-4].Expression,yyvsp[-2].Expression,yyvsp[0].Expression); ;}
    break;

  case 33:
#line 172 "Cool.y"
    { yyval.Expression = loop(yyvsp[-2].Expression,yyvsp[0].Expression); ;}
    break;

  case 34:
#line 174 "Cool.y"
    { yyval.Expression = yyvsp[-1].Expression; ;}
    break;

  case 35:
#line 176 "Cool.y"
    { yyval.Expression = typcase(yyvsp[-4].Expression,yyvsp[-1].Cases); ;}
    break;

  case 36:
#line 178 "Cool.y"
    { yyval.Expression = dispatch(new_(yyvsp[-1].Symbol),yyvsp[-1].Symbol,yyvsp[0].Expressions); ;}
    break;

  case 37:
#line 180 "Cool.y"
    { yyval.Expression = add(yyvsp[-2].Expression,yyvsp[0].Expression); ;}
    break;

  case 38:
#line 182 "Cool.y"
    { yyval.Expression = sub(yyvsp[-2].Expression,yyvsp[0].Expression); ;}
    break;

  case 39:
#line 184 "Cool.y"
    { yyval.Expression = mul(yyvsp[-2].Expression,yyvsp[0].Expression); ;}
    break;

  case 40:
#line 186 "Cool.y"
    { yyval.Expression = div(yyvsp[-2].Expression,yyvsp[0].Expression); ;}
    break;

  case 41:
#line 188 "Cool.y"
    { yyval.Expression = neg(yyvsp[0].Expression); ;}
    break;

  case 42:
#line 190 "Cool.y"
    { yyval.Expression = lt(yyvsp[-2].Expression,yyvsp[0].Expression); ;}
    break;

  case 43:
#line 192 "Cool.y"
    { yyval.Expression = eql(yyvsp[-2].Expression,yyvsp[0].Expression); ;}
    break;

  case 44:
#line 194 "Cool.y"
    { yyval.Expression = dispatch(yyvsp[-2].Expression,Symbol("equals"),List(yyvsp[0].Expression)); ;}
    break;

  case 45:
#line 196 "Cool.y"
    { yyval.Expression = leq(yyvsp[-2].Expression,yyvsp[0].Expression); ;}
    break;

  case 46:
#line 198 "Cool.y"
    { yyval.Expression = comp(yyvsp[0].Expression); ;}
    break;

  case 47:
#line 200 "Cool.y"
    { yyval.Expression = yyvsp[-1].Expression; ;}
    break;

  case 48:
#line 202 "Cool.y"
    { yyval.Expression = unit(); ;}
    break;

  case 49:
#line 204 "Cool.y"
    { yyval.Expression = int_const(yyvsp[0].Symbol); ;}
    break;

  case 50:
#line 206 "Cool.y"
    { yyval.Expression = string_const(yyvsp[0].Symbol); ;}
    break;

  case 51:
#line 208 "Cool.y"
    { yyval.Expression = bool_const(yyvsp[0].Boolean); ;}
    break;

  case 52:
#line 210 "Cool.y"
    { yyval.Expression = variable(yyvsp[0].Symbol); ;}
    break;

  case 53:
#line 212 "Cool.y"
    { 
	    yyval.Expression = dispatch(variable(Symbol("this")),yyvsp[-1].Symbol,yyvsp[0].Expressions); 
	  ;}
    break;

  case 54:
#line 218 "Cool.y"
    { yyval.Expression = block(yyvsp[0].Expressions); ;}
    break;

  case 55:
#line 222 "Cool.y"
    { yyerror("deleted semicolon");
	    yyval.Expressions = List(yyvsp[-1].Expression); ;}
    break;

  case 56:
#line 225 "Cool.y"
    { yyval.Expressions = List(yyvsp[0].Expression); ;}
    break;

  case 57:
#line 227 "Cool.y"
    { yyval.Expressions = Nil; ;}
    break;

  case 58:
#line 229 "Cool.y"
    { yyval.Expressions = yyvsp[-2].Expression :: yyvsp[0].Expressions; ;}
    break;

  case 59:
#line 231 "Cool.y"
    { yyval.Expressions = List(let(yyvsp[-6].Symbol,yyvsp[-4].Symbol,yyvsp[-2].Expression,block(yyvsp[0].Expressions))); ;}
    break;

  case 60:
#line 233 "Cool.y"
    { yyval.Expressions = yyvsp[0].Expressions; ;}
    break;

  case 61:
#line 237 "Cool.y"
    {  yyval.Expressions = Nil; ;}
    break;

  case 62:
#line 239 "Cool.y"
    {  yyval.Expressions = yyvsp[-1].Expressions; ;}
    break;

  case 63:
#line 244 "Cool.y"
    { yyval.Expressions = List(yyvsp[0].Expression); ;}
    break;

  case 64:
#line 246 "Cool.y"
    { yyval.Expressions = yyvsp[-2].Expressions + yyvsp[0].Expression; ;}
    break;

  case 65:
#line 251 "Cool.y"
    { yyval.Cases = yyvsp[0].Cases; ;}
    break;

  case 66:
#line 262 "Cool.y"
    { yyval.Cases = List(yyvsp[0].Case); ;}
    break;

  case 67:
#line 264 "Cool.y"
    { yyval.Cases = yyvsp[-1].Cases + yyvsp[0].Case; ;}
    break;

  case 68:
#line 269 "Cool.y"
    { yyval.Case = branch(yyvsp[-4].Symbol,yyvsp[-2].Symbol,yyvsp[0].Expression); ;}
    break;


    }

/* Line 991 of yacc.c.  */
#line 1533 "Cool.tab.c"

  yyvsp -= yylen;
  yyssp -= yylen;


  YY_STACK_PRINT (yyss, yyssp);

  *++yyvsp = yyval;


  /* Now `shift' the result of the reduction.  Determine what state
     that goes to, based on the state we popped back to and the rule
     number reduced by.  */

  yyn = yyr1[yyn];

  yystate = yypgoto[yyn - YYNTOKENS] + *yyssp;
  if (0 <= yystate && yystate <= YYLAST && yycheck[yystate] == *yyssp)
    yystate = yytable[yystate];
  else
    yystate = yydefgoto[yyn - YYNTOKENS];

  goto yynewstate;


/*------------------------------------.
| yyerrlab -- here on detecting error |
`------------------------------------*/
yyerrlab:
  /* If not already recovering from an error, report this error.  */
  if (!yyerrstatus)
    {
      ++yynerrs;
#if YYERROR_VERBOSE
      yyn = yypact[yystate];

      if (YYPACT_NINF < yyn && yyn < YYLAST)
	{
	  YYSIZE_T yysize = 0;
	  int yytype = YYTRANSLATE (yychar);
	  char *yymsg;
	  int yyx, yycount;

	  yycount = 0;
	  /* Start YYX at -YYN if negative to avoid negative indexes in
	     YYCHECK.  */
	  for (yyx = yyn < 0 ? -yyn : 0;
	       yyx < (int) (sizeof (yytname) / sizeof (char *)); yyx++)
	    if (yycheck[yyx + yyn] == yyx && yyx != YYTERROR)
	      yysize += yystrlen (yytname[yyx]) + 15, yycount++;
	  yysize += yystrlen ("syntax error, unexpected ") + 1;
	  yysize += yystrlen (yytname[yytype]);
	  yymsg = (char *) YYSTACK_ALLOC (yysize);
	  if (yymsg != 0)
	    {
	      char *yyp = yystpcpy (yymsg, "syntax error, unexpected ");
	      yyp = yystpcpy (yyp, yytname[yytype]);

	      if (yycount < 5)
		{
		  yycount = 0;
		  for (yyx = yyn < 0 ? -yyn : 0;
		       yyx < (int) (sizeof (yytname) / sizeof (char *));
		       yyx++)
		    if (yycheck[yyx + yyn] == yyx && yyx != YYTERROR)
		      {
			const char *yyq = ! yycount ? ", expecting " : " or ";
			yyp = yystpcpy (yyp, yyq);
			yyp = yystpcpy (yyp, yytname[yyx]);
			yycount++;
		      }
		}
	      yyerror (yymsg);
	      YYSTACK_FREE (yymsg);
	    }
	  else
	    yyerror ("syntax error; also virtual memory exhausted");
	}
      else
#endif /* YYERROR_VERBOSE */
	yyerror ("syntax error");
    }



  if (yyerrstatus == 3)
    {
      /* If just tried and failed to reuse lookahead token after an
	 error, discard it.  */

      /* Return failure if at end of input.  */
      if (yychar == YYEOF)
        {
	  /* Pop the error token.  */
          YYPOPSTACK;
	  /* Pop the rest of the stack.  */
	  while (yyss < yyssp)
	    {
	      YYDSYMPRINTF ("Error: popping", yystos[*yyssp], yyvsp, yylsp);
	      yydestruct (yystos[*yyssp], yyvsp);
	      YYPOPSTACK;
	    }
	  YYABORT;
        }

      YYDSYMPRINTF ("Error: discarding", yytoken, &yylval, &yylloc);
      yydestruct (yytoken, &yylval);
      yychar = YYEMPTY;

    }

  /* Else will try to reuse lookahead token after shifting the error
     token.  */
  goto yyerrlab2;


/*----------------------------------------------------.
| yyerrlab1 -- error raised explicitly by an action.  |
`----------------------------------------------------*/
yyerrlab1:

  /* Suppress GCC warning that yyerrlab1 is unused when no action
     invokes YYERROR.  */
#if defined (__GNUC_MINOR__) && 2093 <= (__GNUC__ * 1000 + __GNUC_MINOR__)
#ifndef __cplusplus
  __attribute__ ((__unused__))
#endif
#endif


  goto yyerrlab2;


/*---------------------------------------------------------------.
| yyerrlab2 -- pop states until the error token can be shifted.  |
`---------------------------------------------------------------*/
yyerrlab2:
  yyerrstatus = 3;	/* Each real token shifted decrements this.  */

  for (;;)
    {
      yyn = yypact[yystate];
      if (yyn != YYPACT_NINF)
	{
	  yyn += YYTERROR;
	  if (0 <= yyn && yyn <= YYLAST && yycheck[yyn] == YYTERROR)
	    {
	      yyn = yytable[yyn];
	      if (0 < yyn)
		break;
	    }
	}

      /* Pop the current state because it cannot handle the error token.  */
      if (yyssp == yyss)
	YYABORT;

      YYDSYMPRINTF ("Error: popping", yystos[*yyssp], yyvsp, yylsp);
      yydestruct (yystos[yystate], yyvsp);
      yyvsp--;
      yystate = *--yyssp;

      YY_STACK_PRINT (yyss, yyssp);
    }

  if (yyn == YYFINAL)
    YYACCEPT;

  YYDPRINTF ((stderr, "Shifting error token, "));

  *++yyvsp = yylval;


  yystate = yyn;
  goto yynewstate;


/*-------------------------------------.
| yyacceptlab -- YYACCEPT comes here.  |
`-------------------------------------*/
yyacceptlab:
  yyresult = 0;
  goto yyreturn;

/*-----------------------------------.
| yyabortlab -- YYABORT comes here.  |
`-----------------------------------*/
yyabortlab:
  yyresult = 1;
  goto yyreturn;

#ifndef yyoverflow
/*----------------------------------------------.
| yyoverflowlab -- parser overflow comes here.  |
`----------------------------------------------*/
yyoverflowlab:
  yyerror ("parser stack overflow");
  yyresult = 2;
  /* Fall through.  */
#endif

yyreturn:
#ifndef yyoverflow
  if (yyss != yyssa)
    YYSTACK_FREE (yyss);
#endif
  return yyresult;
}


#line 278 "Cool.y"

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



