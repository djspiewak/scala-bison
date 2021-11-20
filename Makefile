scala-bison.jar :
	@if [ -f bin/edu/uwm/cs/scalabison/BisonParser.class ]; \
	then \
	  echo jar cf scala-bison.jar -C bin edu; \
	  jar cf scala-bison.jar -C bin edu; \
	elif [ -f src/edu/uwm/cs/scalabison/BisonParser.scala ]; \
	then \
	  make compile && make scala-bison.jar; \
	else \
	  echo "Need to fetch scala-bison.jar from the distribution site."; \
	fi

.PHONY: compile
compile:
	mkdir -p bin
	(cd src; scalac -d ../bin edu/uwm/cs/util/*.scala edu/uwm/cs/scalabison/*.scala)
	rm -f scala-bison.jar

PDIR = src/edu/uwm/cs/scalabison/

.PHONY: boot
boot : scala-bison.jar
	bison -v ${PDIR}/Bison.y
	rm Bison.tab.c
	scala -cp scala-bison.jar -howtorun:object edu.uwm.cs.scalabison.RunGenerator ${PDIR}/Bison.y
	rm Bison.output
	cp BisonParser.scala BisonTokens.scala ${PDIR}/.
	@echo "Now refresh/rebuild the project"

.PHONY: boot-trace
boot-trace: scala-bison.jar
	bison -v ${PDIR}/Bison.y
	rm Bison.tab.c
	scala -cp scala-bison.jar -howtorun:object edu.uwm.cs.scalabison.RunGenerator -t -T ${PDIR}/Bison.y
	rm Bison.output
	cp BisonParser.scala BisonTokens.scala ${PDIR}/.
	@echo "Now refresh/rebuild the project"

.PHONY: clean
clean : 
	rm -fr Bison* scala-bison.jar bin/edu
	 
.PHONY: all-jar
all-jar: jar2.9 jar2.10 jar2.11
 
.PHONY: jar2.*

jar2.% : clean
	mkdir -p bin
	(cd src; scalac-2.$* -d ../bin edu/uwm/cs/util/*.scala edu/uwm/cs/scalabison/*.scala)
	jar cf lib/scala-bison-2.$*.jar -C bin edu
	    	 
.PHONY: %.lcoutput
%.lcoutput : %.y
	bison -n -v $*.y
	scala edu.uwm.cs.cool.meta.parser.RunGenerator -v $*.y

