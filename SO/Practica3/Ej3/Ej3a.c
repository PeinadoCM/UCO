#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>

pthread_mutex_t sB;
sem_t sH;
sem_t sO;

void* Productor(void* v){
    int pos=0;
    int* buffer=(int*)v;
    for(int i=0; 10>i; i++){
        if(sem_wait(&sH)==-1){
            printf("Error al llamar al semaforo\n");
            exit(0);
        }
        if(pthread_mutex_lock(&sB)!=0){
            printf("Error al llamar al semaforo\n");
            exit(0);
        }
        buffer[pos]=rand()%10+1;
        printf("-->Produciendo un %i en la posicion %i\n",buffer[pos],pos);
        pos=(pos+1)%5;
        if(pthread_mutex_unlock(&sB)!=0){
            printf("Error al llamar al semaforo\n");
            exit(0);
        }
        if(sem_post(&sO)==-1){
            printf("Error al llamar al semaforo\n");
            exit(0);
        }
   }
}

void* Consumidor(void* v){
    int pos=0;
    int* buffer=(int*)v;
    for(int i=0; 10>i; i++){
        if(sem_wait(&sO)==-1){
            printf("Error al llamar al semaforo\n");
            exit(0);
        }        
        if(pthread_mutex_lock(&sB)!=0){
            printf("Error al llamar al semaforo\n");
            exit(0);
        }
        printf("<--Consumiendo un %i en la posicion %i\n",buffer[pos],pos);
        buffer[pos]=0;
        pos=(pos+1)%5;
        if(pthread_mutex_unlock(&sB)!=0){
            printf("Error al llamar al semaforo\n");
            exit(0);
        }
        if(sem_post(&sH)==-1){
            printf("Error al llamar al semaforo\n");
            exit(0);
        }
    }
}


int main(){
    pthread_t tid_p;
    pthread_t tid_c;
    int v[5];
    srand(time(NULL));
    if(pthread_mutex_init(&sB,NULL)!=0){
        printf("Error al inicializar el semaforo\n");
        exit(0);        
    }
    if(sem_init(&sH,0,5)==-1){
        printf("Error al inicializar el semaforo\n");
        exit(0);
    }
    if(sem_init(&sO,0,0)==-1){
        printf("Error al inicializar el semaforo\n");
        exit(0);        
    }
    pthread_create(&tid_p,NULL,Productor,(void*)v);
    pthread_create(&tid_c,NULL,Consumidor,(void*)v);
    pthread_join(tid_p,NULL);
    pthread_join(tid_c,NULL);
    if(pthread_mutex_destroy(&sB)!=0){
        printf("Error al destruir el semaforo\n");
        exit(0);
    }
    if(sem_destroy(&sH)==-1){
        printf("Error al destruir el semaforo\n");
        exit(0);
    }
    if(sem_destroy(&sO)==-1){
        printf("Error al destruir el semaforo\n");
        exit(0);
    }
    exit(1);
}