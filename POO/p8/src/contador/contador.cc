#include "contador.h"

Contador::Contador(int valor, int min, int max){
    if(max>=valor && valor>=min){
        valor_=valor;
    }
    else{
        valor_=0;
    }
    if(max>min){
        min_=min;
        max_=max;
    }
    else{
        valor_=0;
        min_=0;
        max_=1000;
    }
}

Contador Contador::operator=(int valor){
    if(valor<min_){
        valor_=min_;
    }
    else { 
        if(valor>max_){
            valor_=max_;
        }
        else{
        valor_=valor;
        }
    }
    return *this;
}

Contador Contador::operator=(Contador c){
    if(c.get()<min_){
        valor_=min_;
    }
    else { 
        if(c.get()>max_){
            valor_=max_;
        }
        else{
        valor_=c.get();
        }
    }
    return *this;
}

Contador Contador::operator++(){
    if(valor_<max_){
        valor_++;
    }
    return *this;
}

Contador Contador::operator++(int){
    Contador aux=*this;
    if(valor_<max_){
        valor_++;
    }
    return aux;
}

Contador Contador::operator--(){
    if(valor_>min_){
        valor_--;
    }
    return *this;
}

Contador Contador::operator--(int){
    Contador aux=*this;
    if(valor_>min_){
        valor_--;
    }
    return aux;
}

Contador Contador::operator+(int valor){
    valor_=valor_+valor;
    if(valor_<min_){
        valor_=min_;
    }
    else if(valor_>max_){
        valor_=max_;
    }
    return *this;
}

Contador Contador::operator-(int valor){
    valor_=valor_-valor;
    if(valor_<min_){
        valor_=min_;
    }
    else if(valor_>max_){
        valor_=max_;
    }
    return *this;
}

Contador operator+(int valor,Contador c){
    c=valor+c.get();
    return c;
}

Contador operator-(int valor,Contador c){
    c=valor-c.get();
    return c;
}

std::ostream &operator<<(std::ostream &stream, Contador &c){
    stream << c.valor_;
    return stream;
}

std::istream &operator>>(std::istream &stream, Contador &c){
    int aux;
    stream >> aux;
    c=aux;
    return stream;
}