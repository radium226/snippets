#include "snippet.h"

#include <stdlib.h>
#include <stdio.h>

extern char hello_start;
extern char hello_end;

extern char world_start;
extern char world_end;

int main(int UNUSED(argc), char **UNUSED(argv))
{
	char *f = NULL;
	
	for (f = &hello_start; f < &hello_end; f++) {
		putchar(*f);
	}
	printf(", ");
	for (f = &world_start; f < &world_end; f++) {
		putchar(*f);
	}
	printf("! \n");
	
	
	exit(EXIT_SUCCESS);
}
