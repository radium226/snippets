#include "snippet.h"

#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/prctl.h>

int main(int argc, char **argv)
{
	char **env = (char **)malloc(sizeof(char *) * 1);
	*env = NULL;
	
	char **args = (char **)malloc(sizeof(char *) * 2);
	args[0] = PROGRAM;
	args[1] = NULL;
	
	fprintf(stdout, "Let's run the program... ");
	fflush(stdout);
	sleep(SLEEP_TIME_BEFORE_FEXEVE);
	fprintf(stdout, "Go! \n");
	fflush(stdout);
	
	int fd = open(PROGRAM, O_RDONLY);
	fexecve(fd, args, env);
	
	exit(EXIT_SUCCESS);
}
