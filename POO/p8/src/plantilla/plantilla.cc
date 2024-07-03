#include<iostream>

template<typename T>

void print_vector(T v[], const int n){
    for(int i=0; n>i; i++){
        std::cout<<"v["<<i<<"]= "<<v[i]<<std::endl;
    }
}

int main(){
    int vint[5]={1,3,5,7,9};
    float vfloat[4]={5.6, 7.8, 3.9, 1.2};
    char vchar[7]="Prueba";
    std::cout << "Mostrando vector de enteros"<<std::endl;
    print_vector(vint,5);
    std::cout << "vMostrando vector de floats"<<std::endl;
    print_vector(vfloat,4);
    std::cout << "Mostrando vector de chars"<<std::endl;
    print_vector(vchar,6);
}