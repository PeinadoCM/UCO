#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>

sem_t sA;
sem_t sB;

void* EscribirA(){
    for(int i=0; 5>i; i++){
        sem_wait(&sA);
        printf("A");
        sem_post(&sB);
    }
    pthread_exit(NULL);
}
void* EscribirB(){
    for(int i=0; 5>i; i++){
        sem_wait(&sB);
        printf("B");
        sem_post(&sA);
    }
    pthread_exit(NULL);
}

int main(){
    pthread_t tidA;
    pthread_t tidB;
    sem_init(&sA,0,0);
    sem_init(&sB,0,1);
    pthread_create(&tidA,NULL,EscribirA,NULL);
    pthread_create(&tidB,NULL,EscribirB,NULL);
    pthread_join(tidA,NULL);
    pthread_join(tidB,NULL);
    sem_destroy(&sA);
    sem_destroy(&sB);
    printf("\n");
    exit(1);
}