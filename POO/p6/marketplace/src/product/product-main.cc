#include "product.h"

int main(){
    Product p1= Product("XX1","Mesa");
    std::cout<<p1.GetId()<<"\n"<<p1.GetName()<<"\n"<<p1.GetPrice()<<"\n"<<p1.GetMaker()<<"\n"<<p1.GetSeller();
}