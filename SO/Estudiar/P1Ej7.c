#include<signal.h>
#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<errno.h>

void* alarma(int numero){
    printf("RING\n");
}

int main(){
    int cont=0;
    if(signal(SIGALRM,(void*)alarma)==SIG_ERR){
        printf("Error al asiganr el callback a la señal, numero de error %d",errno);
        exit(EXIT_FAILURE);
    }
    while(1){
        alarm(5);
        pause();
        alarm(3);
        pause();
        alarm(1);
        pause();
        cont++;
        if(cont==4){
            if(kill(getpid(),SIGTERM)==-1){
                printf("Error al enviar señal, numero de error= %d",errno);
                exit(EXIT_FAILURE);
            }
        }
    }
}