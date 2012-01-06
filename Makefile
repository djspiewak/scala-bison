scala-bison.jar :
	jar cf scala-bison.jar `cd src; find edu -name "*.class" -print`

.PHONY: boot
boot : scala-bison.jar
        cp edu/uwm/cs/scalabison/parser/Bison.y .
         Bison
        scalac BisonParser.scala
        cp BisonParser.scala edu/uwm/cs/cool/meta/parser/.
        rm -f Bison*
        make all

.PHONY: reboot
reboot :
        rm -f scala-bison.jar
        make all scala-bison.jar

.PHONY: %.lcoutput
%.lcoutput : %.y
	bison -n -v $*.y
	scala edu.uwm.cs.cool.meta.parser.RunGenerator -v $*.y

.PHONY: CoolParser
CoolParser : scala-bison.jar
        (HERE=`pwd`; cd edu/uwm/cs/cool/parser; $$HERE/scala-bison Cool)
        scalac edu/uwm/cs/cool/parser/CoolParser.scala edu/uwm/cs/cool/parser/Co
olTokens.scala

.PHONY: %.parse
%.parse : %.cl
        scala edu.uwm.cs.cool.parser.coolParser $*.cl

scala-bison.jar :
        jar cf scala-bison.jar `find edu -name "*.class" -print`
