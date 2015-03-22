build: libapi.so

api.o:
	gcc -Wall -fPIC -c api.c -o api.o

libapi.so: api.o
	gcc -shared -Wl,-soname,libapi.so.1 -o libapi.so.1.0 api.o
	ln -s ./libapi.so.1.0 ./libapi.so.1
	ln -s ./libapi.so.1 ./libapi.so
