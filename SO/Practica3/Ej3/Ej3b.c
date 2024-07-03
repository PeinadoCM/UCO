#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>

pthread_mutex_t sB;
sem_t sH;
sem_t sO;
int posC=0;
int posP=0;

void* Productor(void* v){
    int* buffer=(int*)v;
        sem_wait(&sH);
        pthread_mutex_lock(&sB);
        buffer[posP]=rand()%10+1;
        printf("-->Produciendo un %i en la posicion %i\n",buffer[posP],posP);
        posP=(posP+1)%5;
        pthread_mutex_unlock(&sB);
        sem_post(&sO);
}

void* Consumidor(void* v){
    int* buffer=(int*)v;
        sem_wait(&sO);
        pthread_mutex_lock(&sB);
        printf("<--Consumiendo un %i en la posicion %i\n",buffer[posC],posC);
        buffer[posC]=0;
        posC=(posC+1)%5;
        pthread_mutex_unlock(&sB);
        sem_post(&sH);
}


int main(){
    pthread_t tid_p[3];
    pthread_t tid_c[3];
    int v[5];
    srand(time(NULL));
    pthread_mutex_init(&sB,NULL);
    sem_init(&sH,0,5);
    sem_init(&sO,0,0);
    for(int i=0; 3>i;i++){
        pthread_create(&tid_p[i],NULL,Productor,(void*)v);
        pthread_create(&tid_c[i],NULL,Consumidor,(void*)v);
    }
    for(int i=0; 3>i; i++){
        pthread_join(tid_p[i],NULL);
        pthread_join(tid_c[i],NULL);
    }
    pthread_mutex_destroy(&sB);
    sem_destroy(&sH);
    sem_destroy(&sO);
    exit(1);
}