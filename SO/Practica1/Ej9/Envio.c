#include<stdio.h>
#include<stdlib.h>
#include <sys/wait.h>
#include <unistd.h> 
#include <signal.h>
#include <errno.h>

void sig_alrm(){
    
}

int main(int argc, char** argv){
	if (signal (SIGALRM, sig_alrm) == SIG_ERR){
		perror("Signal error");
   	    printf("errno value= %d\n", errno);
		exit(EXIT_FAILURE);
	}
    if(argc<2){
        printf("Error, tienes que pasar por linea de comandos los nombres de los ficheros que quieres abrir\n");
        exit(1);
    }
    kill(atoi(argv[1]),SIGUSR1);
    alarm(1);
    pause();
    kill(atoi(argv[1]),SIGKILL);
    exit(0);
}