#include "market.h"

bool Market::AddClient(Client c1){
    for(auto itc=client_list_.begin(); itc!=client_list_.end(); itc++){
        if(itc->GetId()==c1.GetId()){
            return false;
        }
    }
    client_list_.push_back(c1);
    return true;
}

bool Market::AddSeller(Seller s1){
    for(auto its=seller_list_.begin(); its!=seller_list_.end(); its++){
        if(its->GetId()==s1.GetId()){
            return false;
        }
    }
    seller_list_.push_back(s1);
    return true;
}

bool Market::DeleteClient(Client c1){
    for(auto itc=client_list_.begin(); itc!=client_list_.end(); itc++){
        if(itc->GetId()==c1.GetId()){
            client_list_.erase(itc);
            return true;
        }
    }
    return false;
}

bool Market::DeleteSeller(Seller s1){
    for(auto its=seller_list_.begin(); its!=seller_list_.end(); its++){
        if(its->GetId()==s1.GetId()){
            seller_list_.erase(its);
            return true;
        }
    }
    return false;
}

bool Market::AddProductSeller(Product p, std::string id_seller){
    for(auto its=seller_list_.begin(); its!=seller_list_.end(); its++){
        if(its->GetId()==id_seller){
            its->AddProduct(p);
            return true;
        }
    }
    return false;
}

bool Market::AddProductClient(Product p, std::string id_client){
    for(auto itc=client_list_.begin(); itc!=client_list_.end(); itc++){
        if(itc->GetId()==id_client){
            for(auto its=seller_list_.begin(); its!=seller_list_.end(); its++){
                if(its->DeleteProduct(p)==true){
                    itc->AddProduct(p);
                    return true;
                }
            }
            return false;
        }
    }
    return false;
}

bool Market::DeleteProductSeller(Product p, std::string id_seller){
    for(auto its=seller_list_.begin(); its!=seller_list_.end(); its++){
        if(its->GetId()==id_seller){
            return its->DeleteProduct(p);
        }
    }
    return false;
}

bool Market::DeleteProductClient(Product p, std::string id_client){
    for(auto itc=client_list_.begin(); itc!=client_list_.end(); itc++){
        if(itc->GetId()==id_client){
            return itc->DeleteProduct(p);
            
        }
    }
    return false;
}

float Market::GetMoneyInBasket(){
    float total=0;
    for(auto itc=client_list_.begin(); itc!=client_list_.end(); itc++){
        total=total+itc->GetTotal();
    }
    return total;
}

bool Market::DumpMarket(int outmode){            
    std::vector<std::string> vids;
    std::vector<int> vqs;
    std::fstream fs;
    switch(outmode){
        case 0:
            std::cout<<"|-------------------------------------|"<<std::endl<<"|CLIENT ID |PRODUCT ID |PRODUCT QTY   |"<<std::endl;
            for(auto itc=client_list_.begin(); itc!=client_list_.end(); itc++){
                vids=itc->GetIds();
                vqs=itc->GetQs();
                for(int i=0;vids.size()>i;i++){
                    std::cout<<"|"<<itc->GetId()<<"        |"<<vids[i]<<"         |"<<vqs[i]<<"             |"<<std::endl;
                }
            }
            std::cout<<"|-------------------------------------|"<<std::endl<<"|TOTAL: "<<GetMoneyInBasket()<<"€                          |"<<std::endl<<"|-------------------------------------|"<<std::endl;
            return true;
        break;

        case 1:
            fs.open("ventas.txt",std::fstream::out);
            fs<<"|-------------------------------------|"<<std::endl<<"|CLIENT ID |PRODUCT ID |PRODUCT QTY   |"<<std::endl;
            for(auto itc=client_list_.begin(); itc!=client_list_.end(); itc++){
                vids=itc->GetIds();
                vqs=itc->GetQs();
                for(int i=0;vids.size()>i;i++){
                    fs<<"|"<<itc->GetId()<<"        |"<<vids[i]<<"         |"<<vqs[i]<<"             |"<<std::endl;
                }
            }
            fs<<"|-------------------------------------|"<<std::endl<<"|TOTAL: "<<GetMoneyInBasket()<<"€                          |"<<std::endl<<"|-------------------------------------|"<<std::endl;
            fs.close();
            return true;
        break;

        default:
            return false;
        break;
    }
}
