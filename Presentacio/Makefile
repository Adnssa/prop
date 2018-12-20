# java compiler
JCC = javac

# compilation flags
JFLAGS = -cp ../../json-simple-1.1.1.jar:.
RFLAGS = -cp ../../json-simple-1.1.1.jar:../


main: 
	$(JCC) $(JFLAGS) *.java* 

.PHONY: run
run:
	java main

clean:
	$(RM) *.class*
