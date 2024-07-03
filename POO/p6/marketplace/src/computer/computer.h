#ifndef COMPUTER_H
    #define COMPUTER_H
    #include "product.h"

    enum ComputerType{
        Desktop=1,
        Laptop,
        Server,
        Tablet,
        Gaming
    };

    class Computer: public Product{
        private:
            ComputerType type_;
        public:
            Computer(std::string id, ComputerType type, std::string name="empty", float price=0.0, std::string maker="empty", std::string seller="empty");

            enum ComputerType GetType(){return type_;}
            void SetType(ComputerType type){type_=type;}
    };
#endif