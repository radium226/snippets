.PHONY: run clean

clean:
	cd scream && make clean
	rm -f *.o snippet_*_libscream

static_libscream:
	cd scream && make clean static

shared_libscream:
	cd scream && make clean shared

snippet.o:
	gcc -Iscream -Lscream -c snippet.c -o snippet.o

snippet_static_libscream: snippet.o static_libscream
	gcc -static -Iscream -Lscream snippet.o -lscream -o snippet_static_libscream

snippet_shared_libscream: snippet.o shared_libscream
	gcc -Iscream -Lscream snippet.o -lscream -o snippet_shared_libscream

snippet_loadable_libscream.o:
	gcc -c snippet_loadable_libscream.c -o snippet_loadable_libscream.o

snippet_loadable_libscream: snippet_loadable_libscream.o shared_libscream
	gcc -rdynamic -ldl snippet_loadable_libscream.o -o snippet_loadable_libscream
	

run: snippet_static_libscream snippet_shared_libscream snippet_loadable_libscream
	./snippet_static_libscream
	LD_LIBRARY_PATH=./scream ./snippet_shared_libscream
	./snippet_loadable_libscream

