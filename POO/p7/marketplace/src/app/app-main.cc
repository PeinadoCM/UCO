#include "market.h"
#include <iostream>
int main(){
    int opc;
    Market m=Market("Yo no soy tonto");
    Client auxC=Client("C1");
    Seller auxS=Seller("S1");
    Product auxP=Product("P1");
    std::string cad, cad1,cad2;
    int n=1;
    for(int i=1;6>i;i++){
        cad="C"+std::to_string(i);
        cad1="S"+std::to_string(i);
        auxC=Client(cad);
        auxS=Seller(cad1);
        for(int j=0; 5>j; j++){
            cad2="P"+std::to_string(n);
            auxP=Product(cad2);
            auxC.AddProduct(auxP);
            auxS.AddProduct(auxP);
            n++;
        }
        m.AddClient(auxC);
        m.AddSeller(auxS);
    }
    do{
        std::cout<<std::endl<<"Seleccione una de las opciones siguientes"<<std::endl<<
        "1. Añadir cliente."<<std::endl<<
        "2. Añadir vendedor."<<std::endl<<
        "3. Añadir producto en la cesta de un cliente pidiendo su id de cliente y el id del producto."<<std::endl<<
        "4. Borrar producto de la cesta de un cliente pidiendo su id de cliente y el id del producto."<<std::endl<<
        "5. Volcar datos al fichero de salida ventas.txt."<<std::endl<<
        "6. Volcar datos a pantalla."<<std::endl<<
        "7. Salir del programa."<<std::endl;
        std::cin>>opc;
        std::cout<<std::endl;
        switch(opc){
            case 1:
                std::cout<<"Introduzca el ID del cliente"<<std::endl;
                std::cin>>cad;
                auxC=Client(cad);
                if(m.AddClient(auxC)==false){
                    std::cout<<"Error al añadir el cliente"<<std::endl;
                }
            break;

            case 2:
                std::cout<<"Introduzca el ID del vendedor"<<std::endl;
                std::cin>>cad;
                auxS=Seller(cad);
                if(m.AddSeller(auxS)==false){
                    std::cout<<"Error al añadir el vendedor"<<std::endl;
                }
            break;

            case 3:
                std::cout<<"Introduzca el ID del cliente"<<std::endl;
                std::cin>>cad;
                std::cout<<"Introduzca el ID del producto"<<std::endl;
                std::cin>>cad1;
                auxP=Product(cad1);
                if(m.AddProductClient(cad1,cad)==false){
                    std::cout<<"Error al añadir el producto en la cesta del cliente"<<std::endl;
                }
            break;

            case 4:
                std::cout<<"Introduzca el ID del cliente"<<std::endl;
                std::cin>>cad;
                std::cout<<"Introduzca el ID del producto"<<std::endl;
                std::cin>>cad1;
                auxP=Product(cad1);
                if(m.DeleteProductClient(cad1,cad)==false){
                    std::cout<<"Error al borrar el producto en la cesta del cliente"<<std::endl;
                }
            break;

            case 5:
                if(m.DumpMarket(1)==false){
                    std::cout<<"Error al volcar los datos al fichero de salida ventas.txt"<<std::endl;
                }
            break;

            case 6:
                if(m.DumpMarket(0)==false){
                    std::cout<<"Error al volcar los datos a pantalla"<<std::endl;
                }
            break;

            case 7:
                std::cout<<"Cerrando la aplicacion"<<std::endl;
            break;

            default:
                std::cout<<"El numero introducido no es un numero valido"<<std::endl;
            break;
        }
    }while(opc!=7);
}