#include<stdio.h>
#include<stdlib.h>
#include <sys/types.h> 
#include <sys/wait.h>
#include <unistd.h> 

int main(int argc, char** argv){
    pid_t pid_hijo;
    int status=0, cont=0;
    //Por si no se mete ningun argumento en linea de comandos
    if(argc<4){
        printf("Error, tienes que pasar por linea de comandos los nombres de los ficheros que quieres abrir\n");
        printf("Por ejemplo haz la llamada asi: ./Ej4 gnome-calculator gedit fichero1.txt fichero2.txt ficheroN.txt\n");
        exit(1);
    }
    //Creacion del numero de hijos
    for(int i=0; 2>i;i++){
        pid_hijo=fork();
        cont++;
        if((pid_hijo==0)&&(cont==1)){//Hijo que abre la calculadora
            execlp(argv[1],argv[1],NULL);
            exit (0);
        }
        if((pid_hijo==0)&&(cont==2)){//Hijo que abre los ficheros de texto
            execvp(argv[2],&argv[2]);
            exit (0);
        }
    }
    cont=0;
    //Para que el proceso padre espere a que terminen todos los hijos
    do{
        wait(&status);
        cont++;
    }while(2>cont);
    printf("Soy el padre, ya han terminado todos mis hijos, status= %i\n",status);
    exit (0);
}