#include "program.h"

#include <stdlib.h>
#include <stdio.h>
#include <sys/prctl.h>

#define print(text) {\
	fprintf(stdout, text); \
	fflush(stdout);\
}

int main(int argc, char **argv)
{
	print("Hello... ");
	sleep(SLEEP_TIME);
	printf("World! \n");
	
	exit(EXIT_SUCCESS);
}
