#include<stdio.h>
#include<stdlib.h>
#include <sys/types.h> 
#include <sys/wait.h>
#include <unistd.h> 

int n=0;

int main(int argc, char** argv){
    pid_t pid_hijo;
    int status=0, cont=0;
    //Por si no se mete ningun argumento en linea de comandos
    if(argc<2){
        printf("Error, tienes que pasar por linea de comandos el numero de hijos que quieres crear\n");
        exit(1);
    }
    //Creacion del numero de hijos
    for(int i=0; atoi(argv[1])>i;i++){
        pid_hijo=fork();
        if(pid_hijo==0){
            printf("Soy un hijo, mi pid es: %i, el pid de mi padre es: %i\n",getpid(),getppid());
            n++;
            exit (0);
        }
    }
    //Para que el proceso padre espere a que terminen todos los hijos
    do{
        wait(&status);
        cont++;
    }while(atoi(argv[1])>cont);
    printf("Soy el padre, ya han terminado todos mis hijos, el valor de n= %i, status= %i\n",n,status);
    exit (0);
}
