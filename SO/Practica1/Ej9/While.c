#include<stdio.h>
#include<stdlib.h>
#include <sys/wait.h>
#include <unistd.h> 
#include <signal.h>
#include <errno.h>

void sig_usr(){
    printf("El PID de este programa es: %i\n",getpid());
}

int main(){
	if (signal (SIGUSR1, sig_usr) == SIG_ERR)
	{
		perror("Signal error");
   	    printf("errno value= %d\n", errno);
		exit(EXIT_FAILURE);
	}
    do{
        pause();
    }while(1);
}