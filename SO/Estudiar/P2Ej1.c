#include<stdio.h>
#include<stdlib.h>
#include<pthread.h>
#include<errno.h>

void* Suma(){
    float n1, n2;
    n1=drand48()*10;
    n2=drand48()*10;
    //Creamos la variable que vamos a devolver en el heap
    float* res=malloc(sizeof(float));
    *res=n1+n2;
    printf("Soy la hebra %ld, los dos numeros creados son %f, %f y su suma es: %f\n",pthread_self(),n1,n2,*res);
    //Devolvemos el valor
    pthread_exit((void*)res);
}


int main(int argc, char** argv){
    //Comprobamos que se haya añadido el numero de hebras que se desean crear
    if(argc!=2){
        printf("Error, no has introducido el numero de hebras que deseas crear\n");
        exit(EXIT_FAILURE);
    }
    //Hacemos que los numeros sean aleatorios
    srand48(time(NULL));
    pthread_t tid[atoi(argv[1])];
    float* res;
    float total=0;
    //Creamos las hebras
    for(int i=0;atoi(argv[1])>i;i++){
        if(pthread_create(&tid[i],NULL,Suma,NULL)!=0){
            printf("Error al crear la hebra, numero de error %d\n",errno);
            exit(EXIT_FAILURE);
        }
    }
    //Recogemos las hebras
    for(int i=0;atoi(argv[1])>i;i++){
        if(pthread_join(tid[i],(void**)&res)!=0){
            printf("Error al unir la hebra, numero de error %d\n",errno);
            exit(EXIT_FAILURE);
        }
        total+=*res;
        printf("Tras unir la hebra %ld, la suma total va por %f\n",tid[i],total);
    }
    printf("La suma total es: %f\n",total);
    exit(EXIT_SUCCESS);
}