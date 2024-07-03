#include "person.h"
#include <iostream>

namespace ns1{
int a; // Esta es la variable ns1::a
int b; // Esta es la variable ns1::b
}
namespace ns2{
float a; // Esta es la variable ns2::a
float c; // Esta es la variable ns2::c
}

int main(){
    Person p1= Person("Juan", 32, 4.568);
    Person p2= Person("Ana", 41, 7.371);
    std::cout<<p1.GetName()<<std::endl;
    std::cout<<p1.GetAge()<<std::endl;
    std::cout<<p1.GetRank()<<std::endl;
    std::cout<<p2.GetName()<<std::endl;
    std::cout<<p2.GetAge()<<std::endl;
    std::cout<<p2.GetRank()<<std::endl;

    int a=55;
    ns1::a=0;
    ns2::a=2.3;
    std::cout<< "ns1::a= " << ns1::a << "\n";
    std::cout<< "ns2::a= " << ns2::a << "\n";
    std::cout<< "a= " << a << "\n";

    return 0;
}