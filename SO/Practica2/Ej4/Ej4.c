#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

int n=0;

void* Incremento(){
    for(int i=0;10000>i;i++){ //A partir de 1000000 no da 2000000
        n++;
    }
    pthread_exit(NULL);
}

int main(){
    pthread_t tid[2];
    for(int i=0; 2>i;i++){
        if(pthread_create(&tid[i],NULL,Incremento,NULL)){       
            fprintf(stderr, "Error creating thread\n");
            exit(1); 
        }
    }
    for(int i=0; 2>i;i++){
        if(pthread_join(tid[i],NULL)){
            fprintf(stderr, "Error joining thread\n");
            exit(1);
        }
    }
    printf("El valor de n es: %i\n",n);
    exit(0);
}
