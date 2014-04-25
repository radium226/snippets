CC=gcc
CC_FLAG=-Wall
ECHO=echo


export PATH := .:$(PATH)

.PHONY: clean compile

snippet.o:
	$(CC) $(GCC_FLAG) -c snippet.c -o snippet.o

snippet: snippet.o
	$(CC) $(CC_FLAG) snippet.o -o snippet

sputnik.o:
	$(CC) $(GCC_FLAG) -c sputnik.c -o sputnik.o

sputnik: sputnik.o
	$(CC) $(GCC_FLAG) sputnik.o -o sputnik

clean: 
	$(RM) -f snippet sputnik *.o

compile: snippet sputnik
