# java compiler
JCC = javac

# compilation flags
JFLAGS = -cp ../json-simple-1.1.1.jar:../btc-ascii-table-1.0.jar:.
RFLAGS = -cp ../json-simple-1.1.1.jar:../btc-ascii-table-1.0.jar:.:../

ScheduleGenerator: 
	$(JCC) $(JFLAGS) *.java* 

.PHONY: run
run:
	java $(RFLAGS) Main

rune:
	java $(RFLAGS) Main < entrada.bat

clean:
	$(RM) *.class*
	$(RM) Domini/*.class*
