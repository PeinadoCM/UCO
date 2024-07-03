#include <stdio.h>
#include <stdlib.h>

int main(int argc, char** argv){
    int res=1;
    if(argc<2){
        printf("Error, tienes que añadir el numero al que quieres calcularle el factorial\n");
        exit(-1);
    }
    for(int i=1;i<=atoi(argv[1]);i++){
        res=i*res;
    }
    printf("El factorial de %i es %i\n",atoi(argv[1]),res);
}