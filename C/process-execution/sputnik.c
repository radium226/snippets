#include "sputnik.h"

#include <stdio.h>
#include <stdlib.h>
#include <signal.h>

#define ACTION_CONTINUE_LOOP 0
#define ACTION_STOP_LOOP 1

#define SLEEP_TIME_LOOP 2
#define SLEEP_TIME_SIGNAL 1

volatile sig_atomic_t action = ACTION_CONTINUE_LOOP;

void signal_handler(int signal) {
	if (signal == SIGINT) {
		printf("sputnik:signal_handler: SIGINT signal catched\n");
		sleep(SLEEP_TIME_SIGNAL);
		action = ACTION_STOP_LOOP;
	}
}

int main(int argc, char **argv)
{
	char *parameter = argc > 1 ? argv[1] : "None";
	
	// Signal Handling
	struct sigaction sa;
	sa.sa_handler =  signal_handler;
	sigemptyset(&sa.sa_mask);
	sigaction(SIGINT, &sa, NULL);
	
	fprintf(stdout, "Starting Sputnik <%s>... \n", argv[0]);
	fflush(stdout);
	
	// Loop Control
	int loop = 1;
	do {
		//printf("[TOP] action = %i / loop=%i\n", action, loop);
		switch (action) {
		case ACTION_CONTINUE_LOOP:
			fprintf(stdout, "Bip <%s>... \n", parameter);
			fflush(stdout);
			sleep(SLEEP_TIME_LOOP);
			break;
		case ACTION_STOP_LOOP:
			loop = 0;
			break;
		}
		//printf("[BOTTOM] action = %i / loop=%i\n", action, loop);
	} while(loop);
}
