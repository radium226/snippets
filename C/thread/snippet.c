#include "snippet.h"

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>

void say_hello_thread_routine_cleanup(void *argument)
{
	int index = *((int *)argument);
	printf("say_hello_thread_routine_cleanup: Cleanup (index=%d)\n", index);
}

void *say_hello_thread_routine(void *argument)
{
	int index = 1;

	int sleep_seconds = *((int *)argument);
	
	pthread_cleanup_push(say_hello_thread_routine_cleanup, &index);
	
	printf("say_hello_thread_routine: Starting to loop with sleep(seconds=%d)\n", sleep_seconds);
	while(1) {
		printf("say_hello_thread_routine: Looping (index=%d)... \n", index);
		sleep(sleep_seconds);
		index++;
	}
	
	pthread_cleanup_pop(1);
	pthread_exit(NULL);
}

int main(int UNUSED(argc), char** UNUSED(argv)) {
	int thread_routine_sleep_seconds = THREAD_ROUTINE_SLEEP_SECONDS;
	pthread_t say_hello_thread;
	pthread_create(&say_hello_thread, NULL, say_hello_thread_routine, &thread_routine_sleep_seconds);
	sleep(MAIN_SLEEP_SECONDS);
	pthread_cancel(say_hello_thread);
	pthread_join(say_hello_thread, NULL);
	
	exit(EXIT_SUCCESS);
}


