#include <iostream>

template <typename T> class MiClase{
    private:
        T x_;
        T y_;
    public:
        MiClase (T a, T b){ x_=a; y_=b;};
        T Div(){return x_/y_;};
};

int main(){
    MiClase <int> iobj(10,3);
    MiClase <float> fobj(3.3, 5.5);
    std::cout<<"El resultado de la división de enteros es: "<<iobj.Div()<<std::endl;
    std::cout<<"El resultado de la división de floats es: "<<fobj.Div()<<std::endl;
}