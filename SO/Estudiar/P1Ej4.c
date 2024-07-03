#include<unistd.h>
#include<sys/types.h>
#include<sys/wait.h>
#include<stdio.h>
#include<stdlib.h>
#include<errno.h>

int main(int argc, char** argv){
    //Comprobamos que se han pasado un minimo de valores
    if(argc<4){
        printf("Error, no has ejecutado el prograna correctamente\n");
        exit(EXIT_FAILURE);
    }
    pid_t pid;
    int status, i=0;
    //Creamos a los hijos
    do{
        pid=fork();
        i++;
    }while(2>i && pid!=0);
    //Asignamos la accion de cada proceso
    switch(pid){
        //Error
        case -1:
            printf("Error al crear el proceso hijo, numero de error %d\n",errno);
            exit(EXIT_FAILURE);
        break;
        //Procesos hijos
        case 0:
            switch(i){
                //Primer hijo
                case 1:
                    if(execlp(argv[1],argv[1],NULL)==-1){
                        printf("Error al ejecutar la calculadora, numero de error %d\n",errno);
                        exit(EXIT_FAILURE);
                    }
                    exit(EXIT_SUCCESS);
                break;
                //Segundo hijo
                case 2:
                    if(execvp(argv[2],&argv[2])==-1){
                        printf("Error al abrir los archivos, numero de error %d\n",errno);
                        exit(EXIT_FAILURE);
                    }
                    exit(EXIT_SUCCESS);
                break;
            }
        break;
        //Proceso padre
        default:
            //Recogemos a los hijos
            for(i=0;2>i;i++){
                //Comprobamos que no haya errores
                if(wait(&status)==-1){
                    printf("Error al recoger al hijo, numero de error %d\n",errno);
                    exit(EXIT_FAILURE);
                }
                else{
                    printf("Soy el proceso padre, hijo recogido, status= %d\n",status);
                }
            }
        break;
    }
    exit(EXIT_SUCCESS);
}