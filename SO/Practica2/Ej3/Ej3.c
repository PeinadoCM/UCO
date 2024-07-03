#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

void* Suma2(void* v){
    float* vf= (float*)v;
    float* res=(float*)malloc(sizeof(float));
    for(int i=0;5>i;i++){
        *res=*res+vf[i];
    }
    pthread_exit((void*)res);
}

void* Suma5(void* v){
    float* vf= (float*)v;
    float* res=(float*)malloc(sizeof(float));
    for(int i=0;2>i;i++){
        *res=*res+vf[i];
    }
    pthread_exit((void*)res);
}

int main(int argc, char** argv){
    if(argc<2){
        printf("Error no se ha introducido ningun elemento por linea de comandos\n");
        exit(1);
    }
    srand48(time(NULL));
    float v[10];
    float* res;
    float total=0;
    for(int i=0; 10>i; i++){
        v[i]=drand48()*10.0;
        printf("v[%i]= %f\n",i,v[i]);
    }
    switch(atoi(argv[1])){
        case 2:
            pthread_t tid2[2];
            for(int i=0;2>i;i++){
                if(pthread_create(&tid2[i],NULL,Suma2,(void*)(v+(5*i)))){
                    printf("Error creando la hebra\n");
                    exit(1); 
                }            
            }
            for(int i=0;2>i;i++){
                if(pthread_join(tid2[i],(void**)&res)){
                    printf("Error uniendo la hebra\n");
                    exit(1);
                }
                total=total+*res;
                printf("La suma %i tiene como resultado: %f\n",i,total);
            }
        break;
        case 5:
            pthread_t tid5[5];
            for(int i=0;5>i;i++){
                if(pthread_create(&tid5[i],NULL,Suma5,(void*)(v+(2*i)))){
                    printf("Error creando la hebra\n");
                    exit(1); 
                }
            }
            for(int i=0;5>i;i++){
                if(pthread_join(tid5[i],(void**)&res)){
                    printf("Error uniendo la hebra\n");
                    exit(1);
                }
                total=total+*res;
                printf("La suma %i tiene como resultado: %f\n",i,total);
            }
        break;
        default:
            printf("Error tienes que ejecutar el programa con un 2 o un 5\n");
            exit(1);
        break;
    }
    printf("El resultado de la suma es: %f\n",total);
    free(res);
    exit(0);
}