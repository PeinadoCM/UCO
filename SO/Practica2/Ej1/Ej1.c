#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

void* Aleatorios(){
    float f1,f2;
    f1=drand48()*10.0;
    f2=drand48()*10.0;
    printf("Numero aleatorio 1: %f\n",f1);
    printf("Numero aleatorio 2: %f\n",f2);
    float* suma=malloc(sizeof(float));
    *suma=f1+f2;
    pthread_exit((void*)suma);
}

int main(int argc, char** argv){
    if(argc<2){
        printf("Error no se ha introducido el numero de hebras que se desean ejecutar\n");
        exit(1);
    }
    srand48(time(NULL));
    pthread_t tid[atoi(argv[1])];
    float* res;
    float total=0;
    for(int i=0; i<atoi(argv[1]); i++){
        if(pthread_create(&tid[i], NULL, (void*)Aleatorios, NULL)){
            fprintf(stderr, "Error creating thread\n");
            exit(1); 
        }
    }
    for(int i=0; i<atoi(argv[1]); i++){
        if(pthread_join(tid[i],(void**)&res)){
            fprintf(stderr, "Error joining thread\n");
            exit(1);
        }
        total=total+*res;
        printf("La suma va por: %f\n",total);
    }
    printf("El numero total es: %f\n",total);
    exit(0);
}
