#include "person.h"

namespace ns1{
int a; // Esta es la variable ns1::a
int b; // Esta es la variable ns1::b
}
namespace ns2{
float a; // Esta es la variable ns2::a
float c; // Esta es la variable ns2::c
}

int main(){
    //Practica 1
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
    
    //Practica 2
    int pos;
    std::string preference1=("Futbol");
    std::string preference2=("Baloncesto");
    std::string preference3=("Tenis");
    p1.AddPreferences(preference1, preference2, preference3);
    p1.ShowPreferences();
    preference1=("Videojuegos");
    preference2=("Volley");
    preference3=("Coches");
    p1.AddPreferences(preference1, preference2, preference3);
    p1.ShowPreferences();
    preference1=("Pop");
    p1.AddPreference(preference1);
    preference1=("Rock");
    p1.AddPreference(preference1);
    p1.ShowPreferences();
    
    std::cout<<"Introduzca la posición de la preferencia que quieres cambiar"<<std::endl;
    std::cin>>pos;
    std::cout<<"Introduzca la preferencia"<<std::endl;
    std::cin>>preference1;
    p1.ChangePreference(pos,preference1);
    std::cout<<"--END--"<<std::endl;
    return 0;
}