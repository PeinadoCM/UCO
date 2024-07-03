#include "market.h"
#include<iostream>
int main(){
    Market m("El secreto esta en la masa");
    std::cout<<"El eslogan de nuestra empresa es: "<<m.GetSlogan()<<std::endl;
}