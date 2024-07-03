#include<stdio.h>
#include<stdlib.h>
#include <unistd.h> 
#include <signal.h>

void sig_alarm(int cont){
    printf("RING\n");
}

int main(){
    //No cerrar programa con las señales de alarma
    signal(SIGALRM,sig_alarm);
    //Bucle infinito
    for(int i=1; i>-1; i++){
        alarm(5);
        pause();
        alarm(3);
        pause();
        alarm(1);
        pause();
        //Terminar proceso a la 4 iteracion
        if(i==4){
            kill(getpid(),9);
        }
    }
}