#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

int par=0;
int impar=0;
pthread_mutex_t sb;

void* Sumador(void* orden){
    int* paridad=(int*)orden;
    int* suma=malloc(sizeof(int));
    for(int i=0; 5>i; i++){
        *suma=*suma+(rand()%11);
    }
    pthread_mutex_lock(&sb);
    if((*paridad)%2==0){
        par=par+*suma;
    }
    else{
        impar=impar+*suma;
    }
    pthread_mutex_unlock(&sb);
    pthread_exit((void*)suma);
}

int main(int argc, char** argv){
    if(argc<2){
        printf("Error, no has introducido el numero de hebras que deseas crear\n");
        exit(0);
    }
    pthread_mutex_init(&sb,NULL);
    srand(time(NULL));
    pthread_t tid[atoi(argv[1])];
    int v[atoi(argv[1])];
    int aux[atoi(argv[1])];
    int* x;
    for(int i=0;atoi(argv[1])>i; i++){
        aux[i]=i+1;
    }
    for(int i=0;atoi(argv[1])>i; i++){
        pthread_create(&tid[i], NULL, Sumador, (void*)&aux[i]);
    }
    for(int i=0;atoi(argv[1])>i; i++){
        pthread_join(tid[i], (void**)&x);
        v[i]=*x;
        printf("La hebra de orden de creacion %i ha devuelto el valor de suma: %i\n",aux[i],v[i]);
    }
    printf("El valor de la variable par es: %i\n",par);
    printf("El valor de la variable impar es: %i\n",impar);
    pthread_mutex_destroy(&sb);
    exit(1);
}