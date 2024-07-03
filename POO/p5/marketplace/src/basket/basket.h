#ifndef BASKET_H
    #define BASKET_H
    #include <iostream>
    #include <list>
    #include <map>
    #include <vector>
    #include <string>
    #include "product.h"
    
    class Basket{
        private:
            std::list<Product> product_list_;
            std::map<std::string,int> product_quantity_;
            float total_;
        public:
            Basket(float total=0.0){total_=total;}
            void AddProduct(Product p1);
            bool DeleteProduct(Product p1);
            bool DeleteProduct(std::string id);
            void DeleteBasket(){product_list_.clear(); product_quantity_.clear(); total_=0.0;}

            int GetSize(){return product_list_.size();}
            float GetTotal(){return total_;}
            std::vector<std::string> GetIds();
            std::vector<int> GetQs();
    };
#endif
