/**
 * @file trie_node.cpp
 *
 * CopyRight F. J. Madrid-Cuevas <fjmadrid@uco.es>
 *
 * Sólo se permite el uso de este código en la docencia de las asignaturas sobre
 * Estructuras de Datos de la Universidad de Córdoba.
 *
 * Está prohibido su uso para cualquier otro objetivo.
 */

#include <cassert>
#include <sstream>
#include <cstdint>

#include "trie_node.hpp"

TrieNode::TrieNode (bool is_key_state)
{
    //TODO
    is_key_=is_key_state;
    current_=childs_.end();
    //
    assert(is_key()==is_key_state);
    assert(!current_exists());
};

TrieNode::Ref TrieNode::create(bool is_key_state)
{
    return std::make_shared<TrieNode>(is_key_state);
}

TrieNode::~TrieNode()
{}

bool TrieNode::is_key() const
{
    bool ret_val = true;
    //TODO
    ret_val=is_key_;
    //
    return ret_val;
}

bool
TrieNode::has(char k) const
{
    bool ret_v = false;
    //TODO
    if(childs_.find(k)!=childs_.end()){
        ret_v=true;
    }
    //
    return ret_v;
}

TrieNode::Ref
TrieNode::child(char k) const
{
    assert(has(k));
    TrieNode::Ref node = nullptr;
    //TODO
    node=childs_.at(k);
    //
    return node;
}

bool
TrieNode::current_exists() const
{
    bool ret_val = true;
    //TODO
    if(current_==childs_.end()){
        ret_val=false;
    }
    //
    return ret_val;
}

TrieNode::Ref
TrieNode::current_node() const
{
    assert(current_exists());
    TrieNode::Ref node = nullptr;
    //TODO
    node=current_->second;
    //
    return node;
}

char
TrieNode::current_symbol() const
{
    assert(current_exists());
    char symbol = 0;
    //TODO
    symbol=current_->first;
    //
    return symbol;
}

void
TrieNode::set_is_key_state(bool new_state)
{
    //TODO
    is_key_=new_state;
    //
    assert(is_key()==new_state);
}

bool
TrieNode::find_child(char s)
{
    bool found = false;
    //TODO
    current_=childs_.find(s);
    if(current_!=childs_.end()){
        found=true;
    }
    //
    assert(found || !current_exists());
    assert(!found || current_symbol()==s);
    return found;
}

void
TrieNode::goto_first_child()
{
    //TODO
    current_=childs_.begin();
    //
}

void
TrieNode::goto_next_child()
{
    assert(current_exists());
    //TODO
    current_++;
    //
}

void
TrieNode::set_child(char k, Ref node)
{
    assert(node != nullptr);
    //TODO
    childs_[k]=node;
    current_=childs_.find(k);
    //
    assert(current_symbol()==k);
    assert(current_node()==node);
}

std::ostream&
TrieNode::fold(std::ostream& out) const
{
    //TODO
    //Hint: review c++ input/output manipulators at
    //      https://en.cppreference.com/w/cpp/io/manip
    out << "[ ";
    if(is_key_==true){
        out << "T";
    }
    else{
        out << "F";
    }
    for(auto aux=childs_.begin(); aux!=childs_.end(); aux++){
        out << " " << std::hex << static_cast<uint16_t>(aux->first) << " ";
        (aux->second)->fold(out);
    }
    out << " ]";
    //
    return out;
}

TrieNode::Ref TrieNode::create(std::istream& in) noexcept(false)
{
    TrieNode::Ref node = nullptr;
    //TODO
    std::string token;
    in >> token;
    if(token == "["){
        in >> token;
        if(token == "T"){
            node=node->create(true);
        }
        else if(token == "F"){
            node=node->create(false);
        }
        else{
            throw std::runtime_error("Wrong input format.");
        }
        in >> token;
        while(token != "]" && in.eof()==false){
            try{
                node->set_child(static_cast<char>(std::stoi(token,0,16)),create(in));
            }
            catch(std::exception &e){
                throw std::runtime_error("Wrong input format.");
            }
            in >> token;
        }
        if(token != "]"){
            throw std::runtime_error("Wrong input format.");
        }
    }
    else{
        throw std::runtime_error("Wrong input format.");
    }
    //
    return node;
}
