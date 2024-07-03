#include<stdio.h>
#include<stdlib.h>
#include <sys/types.h> 
#include <sys/wait.h>
#include <unistd.h> 

int main(int argc, char** argv){
    pid_t pid_hijo;
    int status=0;
    //Por si no se mete ningun argumento en linea de comandos
    if(argc<2){
        printf("Error, tienes que pasar por linea de comandos el numero de hijos que quieres crear\n");
        exit(1);
    }
    //Creacion de hijos 
    do{
        pid_hijo=fork();
        if(pid_hijo==0){
            printf("Soy un hijo, mi pid es: %i, el pid de mi padre es: %i\n",getpid(),getppid());
        }
        status++;
    }while((pid_hijo==0)&&(status<atoi(argv[1])));//El (pid_hijo==0) es para que los padres salgan del bucle y los hijos se queden en el bucle
    //Para terminar el ultimo proceso hijo
        if(pid_hijo==0){
            exit(0);
        }
        wait(&status);
        printf("Soy un padre, mi pid es: %i ya ha terminado mi hijo, status= %i\n",getpid(),status);
        exit (0);
}