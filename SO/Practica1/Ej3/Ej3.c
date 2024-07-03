#include<stdio.h>
#include<stdlib.h>
#include <sys/types.h> 
#include <sys/wait.h>
#include <unistd.h> 
#include <signal.h>

void sig_alarm(int cont){
    
}

int main(int argc, char** argv){
    pid_t pid_hijo;
    int status=0, cont=0;
    //Por si no se mete ningun argumento en linea de comandos
    if(argc<2){
        printf("Error, tienes que pasar por linea de comandos el numero de hijos que quieres crear\n");
        exit(1);
    }
    //Para que la señal de alarma no cierre el programa dando un error
    signal(SIGALRM,sig_alarm);
    //Creacion de hijos
    for(int i=0; atoi(argv[1])>i;i++){
        pid_hijo=fork();
        if(pid_hijo==0){
            printf("Soy un hijo, mi pid es: %i, el pid de mi padre es: %i\n",getpid(),getppid());
            alarm(10*(i+1));
            pause();
            exit (0);
        }
        else{
            printf("Hijo con pid %i creado correctamente\n",pid_hijo);
        }
    }
    //Para que el proceso padre muestre por pantalla cada vez que recoga un hijo
    do{
        wait(&status);
        cont++;
        printf("Soy el padre, ya ha terminado mi hijo numero %i, status= %i\n",cont,status);
    }while(atoi(argv[1])>cont);
    exit (0);
}