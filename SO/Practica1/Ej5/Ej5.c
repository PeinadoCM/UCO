#include<stdio.h>
#include<stdlib.h>
#include <sys/types.h> 
#include <sys/wait.h>
#include <unistd.h> 

int main(int argc, char** argv){
    pid_t pid_hijo;
    int status=0, cont=0;
    //Por si no se mete ningun argumento en linea de comandos
    if(argc<3){
        printf("Error, tienes que pasar por linea de comandos los dos numeros a los que quieres calcular el factorial\n");
        exit(1);
    }
    //Creacion del numero de hijos
    for(int i=1; 2>=i;i++){
        pid_hijo=fork();
        if(pid_hijo==0){
            execl("./Factorial","Factorial",argv[i],NULL);//Ejecucion programa Factorial
            exit (0);
        }
    }
    //Para que el proceso padre espere a que terminen todos los hijos
    do{
        wait(&status);
        cont++;
    }while(2>cont);
    printf("Soy el padre, ya han terminado todos mis hijos, status= %i\n",status);
    exit (0);
}
