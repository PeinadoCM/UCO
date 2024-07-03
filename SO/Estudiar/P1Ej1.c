#include<unistd.h>
#include<sys/types.h>
#include<sys/wait.h>
#include<stdio.h>
#include<stdlib.h>
#include<errno.h>


int main(int argc, char** argv){
    //Comprobamos que se añade por parametros el numero de hijos
    if(argc!=2){
        printf("Error, tienes que introducir el numero de hijos que quieres crear\n");
        exit(EXIT_FAILURE);
    }
    int status, j=0;
    pid_t pid;
    //Creamos los hijos
    do{
        pid=fork();
        j++;
    }while(atoi(argv[1])>j && pid!=0);
    //Definimos el comportamiento de los siguientes procesos
    switch(pid){
        //Error
        case -1:
            printf("Error al crear los hijos, numero de error= %d\n",errno);
            exit(EXIT_FAILURE);
        break;
        //Procesos hijos
        case 0:
            printf("Soy un proceso hijo, mi pid es %d, el pid de mi padre es %d\n",getpid(),getppid());
            exit(EXIT_SUCCESS);
        break;
        //Proceso padre
        default:
            //Esperamos a todos los hijos
            for(int i=0;atoi(argv[1])>i;i++){
                //Comprobamos que no hay ningun error a la hora de recoger a los hijos
                if(wait(&status)==-1){
                    printf("Error al recoger el proceso hijo, numero de error= %d\n", errno);
                    exit(EXIT_FAILURE);
                }
                else{
                    printf("Hijo recogido, status= %d\n",status);
                }
            }
        break;
    }
    exit(EXIT_SUCCESS);

}