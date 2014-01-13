#include "snippet.h"

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <errno.h>
#include <sys/wait.h>
#include <signal.h>
#include <sys/shm.h>
#include <sys/ipc.h>
#include <string.h>
#include <sys/stat.h>

#define NOTHING_ACTION 0
#define PRINT_ACTION 1

#define SHM_KEY 666
#define SHM_SIZE 1024
#define SHM_PERM (S_IRUSR|S_IWUSR |S_IRGRP|S_IWGRP|S_IROTH|S_IWOTH)

struct SignalData {
	
	char *text;
	
};

typedef struct SignalData SignalData;

sig_atomic_t action = 0;

void usr1_signal_handler(int signal) {
	if (signal == SIGUSR1) {
		printf("usr1_signal_handler: SIGUSR1 signal catched\n");
		action = PRINT_ACTION;
	}
}

int main_child(int UNUSED(argc), char** UNUSED(argv), pid_t parent_pid, pid_t child_pid, int shm_segment_id) {
	int remaining_seconds=0;
	char *shm_addr = NULL; 
	SignalData *signal_data = NULL;
	printf("main_child: parent_pid=%d / child_pid=%d\n", parent_pid, child_pid);

	signal(SIGUSR1, usr1_signal_handler);


	shm_addr = shmat(shm_segment_id, NULL, 0);


	printf("main_child: sleep(seconds=%d)\n", SLEEP_SECONDS);
	remaining_seconds = sleep(SLEEP_SECONDS);
	printf("main_child: sleep(remaining_seconds=%d)\n", remaining_seconds);
	sleep(remaining_seconds);
	if (action == PRINT_ACTION) {
		signal_data = (SignalData *)malloc(sizeof(SignalData));
		memcpy(signal_data, shm_addr, sizeof(SignalData));
		printf("main_child: main_parent's text is \"%s\"\n", signal_data -> text);
		free(signal_data);
	}
	sleep(1);
	shmdt(shm_addr);

	return EXIT_SUCCESS;
}

int main_parent(int UNUSED(argc), char** UNUSED(argv), pid_t parent_pid, pid_t child_pid, int shm_segment_id) {
	char *shm_addr = NULL; 
	SignalData *signal_data = (SignalData *)malloc(sizeof(SignalData));
	
	printf("main_parent: parent_pid=%d / child_pid=%d\n", parent_pid, child_pid);
	sleep(1);
	
	signal_data -> text = "This text was written by main_parent";
	shm_addr = shmat(shm_segment_id, NULL, 0);
	memcpy(shm_addr, signal_data, sizeof(SignalData));
	free(signal_data);
	kill(child_pid, SIGUSR1);
	while(wait(NULL) != child_pid);
	printf("main_parent: Child ended\n");
	shmdt(shm_addr);
	return EXIT_SUCCESS;
}

int main(int argc, char** argv) {
	pid_t parent_pid, child_pid, pid;
	
	int shm_segment_id = shmget(SHM_KEY, sizeof(SignalData), IPC_CREAT | SHM_PERM);
	int code = EXIT_SUCCESS;
	
	parent_pid = getpid();
	pid = fork();
	if (pid > 0) {
		child_pid = pid;
		code = main_parent(argc, argv, parent_pid, child_pid, shm_segment_id);
	} else if (pid == 0) {
		child_pid = getpid();
		code = main_child(argc, argv, parent_pid, child_pid, shm_segment_id);
	}
	
   exit(code); 
}

