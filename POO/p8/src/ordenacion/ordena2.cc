#include <vector>
#include <iostream>
#include <algorithm>

int main(){
    std::vector<int> v;
    int n, val, ord;
    std::cout<<"Introduzca el numero de elementos que tendra el vector"<<std::endl;
    std::cin>>n;
    for(int i=0; n>i; i++){
        std::cout<<"Introduzca el valor del elemento "<<i<<" del vector"<<std::endl;
        std::cin>>val;
        v.push_back(val);
    }
    std::cout<<"El vector sin ordenar es:"<<std::endl;
    for(int i=0; n>i; i++){
        std::cout<<"v["<<i<<"]= "<<v[i]<<std::endl;
    }
    do{
        std::cout<<"Seleccione como ordenar el vector:"<<std::endl<<
        "0. Ascendente"<<std::endl<<
        "1. Descendente"<<std::endl;
        std::cin>>ord;
        if(ord!=0 && ord!=1){
            std::cout<<"El valor introducido no es valido"<<std::endl;
        }
    }while(ord!=0 && ord!=1);
    switch(ord){
        case 0:
            std::sort(v.begin(),v.end());
        break;

        case 1:
            std::sort(v.begin(),v.end(),std::greater<int>());
        break;
    }
    std::cout<<"El vector ordenado es:"<<std::endl;
    for(int i=0; n>i; i++){
        std::cout<<"v["<<i<<"]= "<<v[i]<<std::endl;
    }
}