.PHONY: clean shared static

libscream.a: scream.o
	ar -cq libscream.a scream.o

scream.o:
	gcc -fPIC -c scream.c -o scream.o

libscream.so: scream.o
	gcc -shared -Wl,-soname,libscream.so.1 -o libscream.so.1.0 scream.o
	ln -s ./libscream.so.1.0 ./libscream.so.1
	ln -s ./libscream.so.1 ./libscream.so

clean:
	rm -f libscream.a libscream.so libscream.so.1 libscream.so.1.0 scream.o

shared: libscream.so

static: libscream.a
