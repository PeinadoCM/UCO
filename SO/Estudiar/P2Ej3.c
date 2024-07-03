#include<stdio.h>
#include<stdlib.h>
#include<pthread.h>
#include<errno.h>

int n;

void* Suma(void* v){
    int* vint=(int*)v;
    int* suma=malloc(sizeof(int));
    *suma=0;
    for(int i=0; 10/n>i; i++){
        *suma=*suma+vint[i];
        printf("Soy la hebra %ld, estoy sumando el valor %i\n",pthread_self(),vint[i]);
    }
    printf("Soy la hebra %ld, mi suma total es: %i\n",pthread_self(),*suma);
    pthread_exit((void*)suma);
}

int main(int argc, char** argv){
    //Comprobamos que se ha añadido el numero de veces que se desea separar el vector
    if(argc!=2){
        printf("Error, no has indicado en cuantas partes quieres separar el vector\n");
        exit(EXIT_FAILURE);
    }
    n=atoi(argv[1]);
    //Comprobamos que el numero es 2 o 5
    if(n!=2 && n!=5){
        printf("Error, el numero introducido no es valido, tiene que ser 2 o 5\n");
        exit(EXIT_FAILURE);
    }
    int v[10];
    int* pos[n];
    int* suma;
    int total=0, comp=0;
    pthread_t tid[n];
    //Ajustamos la semilla en aleatoria
    srand(time(NULL));
    //Rellenamos el vector
    for(int i=0;10>i;i++){
        v[i]=rand()%9+1;
        comp+=v[i];
        printf("v[%d]=%d--------%p\n",i,v[i],&v[i]);
    }
    //Rellenamos el vector de las direcciones
    for(int i=0;n>i;i++){
        pos[i]=&v[(10/n)*i];
        printf("pos[%d]=%p\n",i,pos[i]);
    }
    //Creamos las hebras
    for(int i=0;n>i;i++){
        if(pthread_create(&tid[i],NULL,Suma,(void*)pos[i])!=0){
            printf("Error al crear las hebras, numero de error= %d\n",errno);
            exit(EXIT_FAILURE);
        }
        
    }
    //Unimos las hebras
    for(int i=0;n>i;i++){
        if(pthread_join(tid[i],(void**)&suma)!=0){
            printf("Error al unir las hebras, numero de error= %d\n",errno);
            exit(EXIT_FAILURE);
        }
        total+=*suma;
        printf("Hebra %ld unida, el valor de la suma es: %d\n",tid[i],total);
    }
    printf("El valor de la suma total es: %d = %d\n",total, comp);
    exit(EXIT_SUCCESS);
}