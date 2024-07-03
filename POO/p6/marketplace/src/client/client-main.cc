#include "client.h"

int main(){
    Client c("C1");
    c.SetPremium(1);
    std::cout<<"El nivel de premium del cliente "<<c.GetId()<<" es: "<<c.GetPremium()<<std::endl;
}