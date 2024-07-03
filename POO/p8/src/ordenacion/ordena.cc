#include <vector>
#include <iostream>
#include <algorithm>

int main(){
    std::vector<int> v;
    int n, val;
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
    std::sort(v.begin(),v.end());
    std::cout<<"El vector ordenado es:"<<std::endl;
    for(int i=0; n>i; i++){
        std::cout<<"v["<<i<<"]= "<<v[i]<<std::endl;
    }
}