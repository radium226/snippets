#include "snippet.h"

#include <stdlib.h>
#include <dlfcn.h>
#include <stdio.h>

int main(int UNUSED(argc), char **UNUSED(argv))
{
	void (*scream_println)(const char *text);
	void *handle;
	char *error;

	handle = dlopen("./scream/libscream.so", RTLD_LAZY);
	if (!handle) 
	{
		fprintf(stderr, "%s\n", dlerror());
		exit(EXIT_FAILURE);
	}

	scream_println = dlsym(handle, "scream_println");
	error = dlerror();
	if (error != NULL) 
	{
		fprintf(stderr, "%s\n", error);
		exit(EXIT_FAILURE);
	}
	
	scream_println("Hello, World!");

	dlclose(handle);
	exit(EXIT_SUCCESS);
}

