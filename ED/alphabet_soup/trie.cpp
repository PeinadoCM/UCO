/**
 * @file trie.cpp
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
#include "trie.hpp"

Trie::Trie()
{
    // TODO
    root_=nullptr;
    prefix_="";
    //
    assert(is_empty());
}

Trie::Trie(TrieNode::Ref root_node, std::string const &pref)
{
    // TODO
    root_=root_node;
    prefix_=pref;
    //
    assert(prefix() == pref);
}

Trie::Trie(std::istream &in) noexcept(false)
{
    // TODO
    std::string token;
    prefix_.clear();
    in >> token;
    if(token != "["){
        throw std::runtime_error("Wrong input format.");
    }
    in >> token;
    if(token != "\""){
        throw std::runtime_error("Wrong input format.");
    }
    in >> token;
    while(token != "\"" && in.eof()==false){
        try{
            prefix_.push_back(static_cast<char>(std::stoi(token,0,16)));
        }
        catch(std::exception &e){
            throw std::runtime_error("Wrong input format.");
        }
        in >> token;
    }
    if(token != "\""){
        throw std::runtime_error("Wrong input format.");
    }
    root_=root_->create(in);
    in >> token;
    if(token != "]"){
        throw std::runtime_error("Wrong input format.");
    }
    //
}

bool Trie::is_empty() const
{
    bool ret_v = true;
    // TODO
    if(root_!=nullptr){
        ret_v=false;
    }
    //
    return ret_v;
}

std::string
Trie::prefix() const
{
    std::string ret_val = "";
    // TODO
    ret_val=prefix_;
    //
    return ret_val;
}

void Trie::set_prefix(const std::string &new_p)
{
    // TODO
    prefix_=new_p;
    //
    assert(prefix() == new_p);
}

bool Trie::is_key() const
{
    assert(!is_empty());
    bool ret_val = true;
    // TODO
    ret_val=root_->is_key();
    //
    return ret_val;
}

TrieNode::Ref
Trie::root() const
{
    TrieNode::Ref node;
    // TODO
    node=root_;
    //
    return node;
}

void Trie::set_root(TrieNode::Ref const &new_r)
{
    // TODO
    root_=new_r;
    //
    assert(root() == new_r);
}

bool Trie::has(std::string const &k) const
{
    assert(!is_empty());
    bool found = false;
    // TODO
    // Hint: use find_node() to do this.
    // Remember: The Trie can have a prefix==k but does not store the key k.
    auto aux=find_node(k);
    if(aux!=nullptr && aux->is_key()){
        found=true;
    }
    //
    return found;
}

/**
 * @brief Helper function to retrieve the keys.
 *
 * This function does a recursive preorder traversal of the trie's nodes
 * keeping the current prefix and the retrieved keys as functions parameters.
 *
 * @param[in] node is the current node.
 * @param[in] prefix is the current prefix.
 * @param[in,out] keys are the retrieved keys.
 */
static void
preorder_traversal(TrieNode::Ref node, std::string prefix, std::vector<std::string> &keys)
{
    // TODO
    // Remember: node->is_key() means the prefix is a key too.
    if(node->is_key()){
        keys.push_back(prefix);
    }
    node->goto_first_child();
    while(node->current_exists()){
        preorder_traversal(node->current_node(),prefix+node->current_symbol(),keys);
        node->goto_next_child();
    }
    //
}

void Trie::retrieve(std::vector<std::string> &keys) const
{
    assert(!is_empty());
    // TODO
    // Remember add the subtrie's prefix to the retrieve keys.
    preorder_traversal(root_,prefix_,keys);
    //
}

Trie Trie::child(std::string const &postfix) const
{
    assert(!is_empty());
    Trie ret_v;
    // TODO
    // Hint: use find_node() to follow the chain of nodes whose represent postfix.
    TrieNode::Ref node=find_node(postfix);
    if(node != nullptr){
        ret_v.set_root(node);
        ret_v.prefix_=prefix()+postfix;
    }
    //
    assert(ret_v.is_empty() || ret_v.prefix() == (prefix() + postfix));
    return ret_v;
}

bool Trie::current_exists() const
{
    assert(!is_empty());
    bool ret_val = false;
    // TODO
    ret_val=root_->current_exists();
    //
    return ret_val;
}

Trie Trie::current() const
{
    assert(current_exists());
    Trie ret_v;
    // TODO
    ret_v.set_root(root_->current_node());
    ret_v.set_prefix(prefix_+root_->current_symbol());
    //
    return ret_v;
}

char Trie::current_symbol() const
{
    assert(current_exists());
    char symbol = 0;
    // TODO
    symbol=root_->current_symbol();
    //
    return symbol;
}

void Trie::insert(std::string const &k)
{
    assert(k != "");
    // TODO

    if(root_==nullptr){
        root_=root_->create(false);
    }
    auto node=root_;
    for(int i=0; k.size()>i; i++){
        if(node->has(k[i])){
            node=node->child(k[i]);
        }
        else{
            auto new_node=node->create(false);
            node->set_child(k[i], new_node);
            node=new_node;
        }
    }
    node->set_is_key_state(true);
    //
    assert(!is_empty());
    assert(has(k));
}

TrieNode::Ref
Trie::find_node(std::string const &pref) const
{
    assert(!is_empty());
    TrieNode::Ref node;
    // TODO
    // Remember: the prefix "" must return the trie's root node.
    node=root_;
    int i=0;
    while(pref.size()>i && node!=nullptr){
        if(node->has(pref[i])){
            node=node->child(pref[i]);
            i++;
        }
        else{
            node=nullptr;
        }
    }
    //
    return node;
}

std::ostream &
Trie::fold(std::ostream &out) const
{
    // TODO
    out << "[ \" ";
    for(int i=0; prefix_.size()>i; i++){
        out << std::hex << static_cast<uint16_t>(prefix_[i]) << " ";
    }
    out << "\" ";
    root_->fold(out);
    out << " ]";
    //
    return out;
}

bool Trie::find_symbol(char symbol)
{
    assert(!is_empty());
    bool found = false;
    // TODO
    found=root_->find_child(symbol);
    //
    assert(!found || current_exists());
    assert(found || !current_exists());
    assert(!current_exists() || current_symbol() == symbol);
    return found;
}

void Trie::goto_first_symbol()
{
    assert(!is_empty());
    // TODO
    root_->goto_first_child();
    //
    assert(!current_exists() ||
           current().prefix() == (prefix() + current_symbol()));
}

void Trie::goto_next_symbol()
{
    assert(current_exists());
    // TODO
    root_->goto_next_child();
    //
}
