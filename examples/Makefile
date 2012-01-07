.PHONY: all

all: Calc.class JavaParser.scala

Calc.class : Calc.scala CalcScanner.scala CalcParser.scala CalcTokens.scala CalcParserbase.scala
	scalac Calc.scala CalcScanner.scala CalcParser.scala CalcTokens.scala CalcParserBase.scala

CalcScanner.scala : Calc.lex
	jflex --scala Calc.lex

CalcParser.scala CalcTokens.scala : Calc.y
	../cmd/scala-bison Calc

JavaParser.scala : Java.y
	../cmd/scala-bison Java

clean :
	rm -f *.class *Parser.scala *Tokens.scala *~ *.output *.lcoutput *.tab.c
