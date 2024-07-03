#include<stdlib.h>
#include<unistd.h>
#include<sys/types.h>
#include<errno.h>
#include<signal.h>
#include<stdio.h>
#include<sys/wait.h>

void* TratarSennal(int señal){
    printf("Soy el proceso hijo, señal recibida por mi proceso padre\n");
}

void* Nada(){
    printf("Soy el proceso padre, voy a enviarle una señal a mi proceso hijo\n");
}

int main(){
    pid_t pid;
    int status;
    //Asignamos los callbacks a las señales
    if(signal(SIGUSR1,(void*)TratarSennal)==SIG_ERR){
        printf("Error al asignar el callback a la señal, numero de error= %d\n",errno);
        exit(EXIT_FAILURE);
    }
    if(signal(SIGALRM,(void*)Nada)==SIG_ERR){
        printf("Error al asignar el callback a la señal, numero de error= %d\n",errno);
        exit(EXIT_FAILURE);
    }
    //Creamos al proceso hijo
    pid=fork();
    switch(pid){
        //Error
        case -1:
            printf("Error al crear al proceso hijo, numero de error= %d\n",errno);
            exit(EXIT_FAILURE);
        break;
        //Proceso hijo
        case 0:
            for(int i=0;5>i;i++){
                pause();
            }
            exit(EXIT_SUCCESS);
        break;
        //Proceso padre
        default:
            for(int i=0; 5>i; i++){
                alarm(1);
                pause();
                if(kill(pid,SIGUSR1)==-1){
                    printf("Error al enviar la señal, numero de error %d\n",errno);
                    exit(EXIT_FAILURE);
                }
            }
            //Recogemos al proceso hijo
            if(wait(&status)==-1){
                printf("Error al recoger al hijo, numero de error %d\n",errno);
                exit(EXIT_FAILURE);
            }
            else{
                printf("Soy el proceso padre, hijo recogido, status= %d\n",status);
            }
        break;
    }
    exit(EXIT_SUCCESS);
}