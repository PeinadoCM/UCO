#include "basket.h"

void Basket::AddProduct(Product p1){
    if(product_quantity_.count(p1.GetId())==1){
        product_quantity_[p1.GetId()]++;
    }
    else{
        product_list_.push_back(p1);
        product_quantity_[p1.GetId()]=1;
    }
    total_=total_+p1.GetPrice();
}

bool Basket::DeleteProduct(Product p1){
    if(product_quantity_.count(p1.GetId())==1){
        product_quantity_[p1.GetId()]--;
        total_=total_-p1.GetPrice();
        if(product_quantity_[p1.GetId()]==0){
            product_quantity_.erase(p1.GetId());
            for(std::list<Product>::iterator ipl=product_list_.begin(); ipl!=product_list_.end(); ipl++){
                if((*ipl).GetId()==p1.GetId()){
                    ipl=product_list_.erase(ipl);
                }
            }
        }
        return true;
    }
    else{
        return false;
    }
}

bool Basket::DeleteProduct(std::string id){
    if(product_quantity_.count(id)==1){
        product_quantity_[id]--;
        for(std::list<Product>::iterator ipl=product_list_.begin(); ipl!=product_list_.end(); ipl++){
            if((*ipl).GetId()==id){
                total_=total_-((*ipl).GetPrice());
            }
        }
        if(product_quantity_[id]==0){
            product_quantity_.erase(id);
            for(std::list<Product>::iterator ipl=product_list_.begin(); ipl!=product_list_.end(); ipl++){
                if((*ipl).GetId()==id){
                    ipl=product_list_.erase(ipl);
                }
            }
        }
        return true;
    }
    else{
        return false;
    }
}

std::vector<std::string> Basket::GetIds(){
    std::vector<std::string> v;
    for(std::list<Product>::iterator ipl=product_list_.begin(); ipl!=product_list_.end(); ipl++){
        v.push_back((*ipl).GetId());
    }
    return v;
}

std::vector<int> Basket::GetQs(){
    std::vector<int> v;
    std::vector<std::string> vids=GetIds();
    for(std::map<std::string,int>::iterator ipq=product_quantity_.begin(); ipq!=product_quantity_.end(); ipq++){
        v.push_back(ipq->second);
    }
    return v;
}

