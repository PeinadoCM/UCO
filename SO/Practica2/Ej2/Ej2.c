#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

void* Contar(void* n){
    FILE* f;
    int* l=malloc(sizeof(int));
    char aux;
    if((f=fopen((char*)n,"r"))==NULL){
        printf("Error no existe el fichero que se intenta abrir\n");
        exit(1);
    }
    while(!feof(f)){
        do{
            aux=(char)getc(f);
        }while((aux!='\n') && (!feof(f)));
        if(!feof(f)){
            (*l)++;
        }
    }
    fclose(f);
    pthread_exit((void*)l);
}

int main(int argc, char** argv){
    if(argc<2){
        printf("Error no se ha introducido ningun fichero de texto\n");
        exit(1);
    }
    pthread_t tid[argc-1];
    int* l;
    int total=0;
    for(int i=0; (argc-1)>i; i++){
        if(pthread_create(&tid[i],NULL,Contar,(void*)argv[i+1])){
            fprintf(stderr, "Error creating thread\n");
            exit(1); 
        }
    }
    for(int i=0; (argc-1)>i; i++){
        if(pthread_join(tid[i],(void**)&l)){
            fprintf(stderr, "Error joining thread\n");
            exit(1);
        }
        printf("Numero de lineas del archivo %s: %i\n",argv[i+1],*l);
        total=total+*l;
    }
    printf("El numero total de lineas es: %i\n",total);
    exit(0);
}