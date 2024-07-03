#include<stdio.h>
#include<stdlib.h>
#include <sys/types.h> 
#include <sys/wait.h>
#include <unistd.h> 
#include <signal.h>

void sig_alarm(){

}

void tratarsennal(){
    printf("Señal recibida de mi padre\n");
}

int main(){
    pid_t pid_hijo;
    pid_hijo=fork();
    int status;
    signal(SIGALRM,sig_alarm);
    if(pid_hijo==0){
        signal(SIGUSR1,tratarsennal);
        for(int i=0; 5>i; i++){
            pause();
        }
    }
    else{
        for(int j=0; 5>=j; j++){
            alarm(1);
            pause();
            kill(pid_hijo,SIGUSR1);
        }
        wait(&status);
    }
    exit(0);
}