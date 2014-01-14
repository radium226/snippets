#include "snippet.h"

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <time.h>
#include <sys/wait.h>

volatile sig_atomic_t terminate = 0;

timer_t print_timer_id;
timer_t terminate_timer_id;

void timer_handler(int UNUSED(signal), siginfo_t *si, void *UNUSED(uc))
{
	timer_t timer_id = *((timer_t *)si -> si_value.sival_ptr);
	if (timer_id == print_timer_id) {
		printf("Print\n");
	} else if (timer_id == terminate_timer_id) {
		printf("Terminate\n");
		terminate = 1;
	}
}

void make_timer(timer_t *timer_id, int interval)
{
	struct sigevent te;
	struct itimerspec its;
	struct sigaction sa;
	int sig = SIGRTMIN;

	/* Set up signal handler. */
	sa.sa_flags = SA_SIGINFO;
	sa.sa_sigaction = timer_handler;
	sigemptyset(&sa.sa_mask);
	
	sigaction(sig, &sa, NULL);

	/* Set and enable alarm */
	te.sigev_notify = SIGEV_SIGNAL;
	te.sigev_signo = sig;
	te.sigev_value.sival_ptr = timer_id;
	timer_create(CLOCK_REALTIME, &te, timer_id);

	its.it_interval.tv_sec = 0; //interval; //interval;
	its.it_interval.tv_nsec = 0;//interval * 1000000;
	its.it_value.tv_sec = interval;
	its.it_value.tv_nsec = 0;//interval * 1000000;
	timer_settime(*timer_id, 0, &its, NULL);
}

void wait_for_terminate()
{
	sigset_t mask, oldmask;
	sigemptyset (&mask);
	sigaddset (&mask, SIGEV_SIGNAL);
	sigprocmask(SIG_BLOCK, &mask, &oldmask);
	while (!terminate) {
		sigsuspend (&oldmask);
	}
	sigprocmask (SIG_UNBLOCK, &mask, NULL);

}

int main(int UNUSED(argc), char **UNUSED(argv))
{
	make_timer(&print_timer_id, 1);
	make_timer(&terminate_timer_id, 5);
	
	wait_for_terminate();
	exit(EXIT_SUCCESS);
}
