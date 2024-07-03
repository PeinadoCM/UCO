#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

int n;
pthread_mutex_t sb;

void* DecrementarStock(void* v){
    int* vaux=(int*) v;
    int pos=rand()%n;
    int cant=rand()%10+1;
    pthread_mutex_lock(&sb);
    if(vaux[pos]<=cant){
        printf("<--Un cliente ha comprado %i unidades de la camiseta %i\n",vaux[pos],pos);
        vaux[pos]=0;
    }
    else{
        printf("<--Un cliente ha comprado %i unidades de la camiseta %i\n",cant,pos);
        vaux[pos]=vaux[pos]-cant;
    }
    pthread_mutex_unlock(&sb);
    pthread_exit(NULL);
}

void* AumentarStock(void* v){
    int* vaux=(int*) v;
    int pos=rand()%n;
    int cant=rand()%10+1;
    pthread_mutex_lock(&sb);
    printf("-->Un proveedor ha suministrado %i unidades de la camiseta %i\n",cant,pos);
    vaux[pos]=vaux[pos]+cant;
    pthread_mutex_unlock(&sb);
    pthread_exit(NULL);

}

int main(int argc, char** argv){
    if(argc<3){
        printf("Error, no se han introducido los parametros por linea de argumentos\n");
        exit(0);
    }
    n=atoi(argv[2]);
    pthread_mutex_init(&sb,NULL);
    srand(time(NULL));
    pthread_t tid_c[atoi(argv[1])];
    pthread_t tid_p[n];
    int v[n];
    printf("El stock de las camisetas es el siguiente:\n");
    for(int i=0;n>i;i++){
        v[i]=rand()%100+1;
        printf("Camiseta %i = %i\n",i,v[i]);
    }
    for(int i=0;atoi(argv[1])>i;i++){
        pthread_create(&tid_c[i],NULL,DecrementarStock,(void*)v);
    }
    for(int i=0;n>i;i++){
        pthread_create(&tid_p[i],NULL,AumentarStock,(void*)v);
    }
    for(int i=0;atoi(argv[1])>i;i++){
        pthread_join(tid_c[i],NULL);
    }
    for(int i=0;n>i;i++){
        pthread_join(tid_p[i],NULL);
    }
    pthread_mutex_destroy(&sb);
    printf("El stock de las camisetas es el siguiente:\n");
    for(int i=0;n>i;i++){
        printf("Camiseta %i = %i\n",i,v[i]);
    }
}