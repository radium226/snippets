#include "snippet.h"

#include <stdio.h>
#include <stdlib.h>
#include <signal.h>

#define SPUTNIK "./sputnik"

void println(const char *text)
{
	fprintf(stdout, "%s\n", text);
	fflush(stdout);
}

void signal_handler(int signal)
{
	if (signal == SIGINT) {
		println("snippet:signal_handler: SIGINT signal catched");
	}
}

int main(int UNUSED(argc), char **argv)
{
	pid_t parent_pid, child_pid, pid;
	
	parent_pid = getpid();
	pid = fork();
	if (pid > 0) {
		// Signal Handling
		struct sigaction sa;
		sa.sa_handler =  signal_handler;
		sigemptyset(&sa.sa_mask);
		sigaction(SIGINT, &sa, NULL);
		
		child_pid = pid;
		sleep(5);
		kill(child_pid, SIGINT);
		
		int status;
		waitpid(child_pid, &status, NULL);
		println("Quitting Snippet. ");
	} else if (pid == 0) {
		child_pid = getpid();
		exec_sputnik(argv[0], "Test");
	}
	
	exit(EXIT_SUCCESS);
}

exec_sputnik(const char* script, const char *parameter)
{
	println("Executing Sputnik... ");
	const char **argv = (const char **)malloc(sizeof(char *) * 2);
	*(argv + 0) = script;
	*(argv + 1) = parameter;
	execve(SPUTNIK, argv, NULL);
}
