#include "person.h"

int main(){
    Person p1= Person("XX1");
    Person p2= Person("XX2", "Manuel", "Bujalance", "Cordoba");
    std::cout<<p1.GetDataStr();
    std::cout<<p2.GetDataStr();
    
    return 0;
}