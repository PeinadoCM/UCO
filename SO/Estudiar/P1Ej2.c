#include<unistd.h>
#include<sys/types.h>
#include<sys/wait.h>
#include<errno.h>
#include<stdlib.h>
#include<stdio.h>

int main(int argc, char** argv){
    //Comprobamos que se añade el numero de hijos que se han de crear
    if(argc!=2){
        printf("Error, no has introducido el numero de hijos que se han de crear\n");
        exit(EXIT_FAILURE);
    }
    pid_t pid, pid_padre=getpid();
    int status, i=0;
    //Creamos a los procesos hijos
    do{
        pid=fork();
        i++;
    }while(atoi(argv[1])>i && pid==0);
    //Definimos el comportamiento de cada proceso
    switch(pid){
        //Error
        case -1:
            printf("Error al crear el proceso hijo, numero de error= %d\n",errno);
            exit(EXIT_FAILURE);
        break;
        //Proceso hijo
        case 0:
            printf("Soy un proceso hijo con pid %d, el pid de mi padre es %d\n",getpid(),getppid());
            exit(EXIT_SUCCESS);
        break;
        //Procesos padre
        default:
            //Procesos que no son el padre principal
            if(getpid()!=pid_padre){
                printf("Soy un proceso hijo con pid %d, el pid de mi padre es %d\n",getpid(),getppid());
            }
            //Recogemos a los hijos y comprobamos que no haya errores
            if(wait(&status)==-1){
                printf("Error al recoger el proceso hijo, numero de error= %d\n",errno);
                exit(EXIT_FAILURE);
            }
            else{
                printf("Soy un proceso padre con pid %d, hijo recogido con status= %d\n",getpid(),status);
            }
        break;
    }
    exit(EXIT_SUCCESS);
}