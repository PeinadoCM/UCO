#include "seller.h"

int main(){
    Seller s("S1");
    s.SetSector("Informatica");
    std::cout<<"El sector del proveedor "<<s.GetId()<<" es: "<<s.GetSector()<<std::endl;
}