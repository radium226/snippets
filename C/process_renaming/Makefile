clean:
	rm program.o snippet.o snippet program

program: program.o
	gcc program.o -o program

program.o:
	gcc -c program.c -o program.o

snippet: snippet.o
	gcc snippet.o -o snippet

snippet.o:
	gcc -c snippet.c -o snippet.o

compile: snippet program

run: compile
	./snippet
