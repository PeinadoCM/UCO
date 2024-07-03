#include "computer.h"

int main(){
    Computer p1= Computer("XX1",Desktop);
    std::cout<<p1.GetId()<<"\n"<<p1.GetType()<<"\n"<<p1.GetName()<<"\n"<<p1.GetPrice()<<"\n"<<p1.GetMaker()<<"\n"<<p1.GetSeller();
}