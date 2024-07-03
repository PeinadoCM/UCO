#ifndef CONTADOR_H
    #define CONTADOR_H
    
    #include<iostream>

    class Contador{
        private:
            int valor_;
            int min_;
            int max_;
        public:
            Contador(int valor=0, int min=0, int max=1000);
            int get(){return valor_;}
            Contador operator=(int valor);
            Contador operator=(Contador c);
            Contador operator++();
            Contador operator++(int);
            Contador operator--();
            Contador operator--(int);
            Contador operator+(int valor);
            Contador operator-(int valor);
            friend std::ostream &operator<<(std::ostream &stream, Contador &c);
            friend std::istream &operator>>(std::istream &stream, Contador &c);
    };

    Contador operator+(int valor,Contador c);
    Contador operator-(int valor,Contador c);

    
#endif
