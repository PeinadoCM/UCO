/**
 * CopyRight F. J. Madrid-Cuevas <fjmadrid@uco.es>
 *
 * Sólo se permite el uso de este código en la docencia de las asignaturas sobre
 * Estructuras de Datos de la Universidad de Córdoba.
 *
 * Está prohibido su uso para cualquier otro objetivo.
 */
#pragma once

#include <cassert>
#include <exception>
#include <memory>
#include <iostream>
#include <queue>

#include "btree_utils.hpp"

template<class T>
int compute_height (typename BTree<T>::Ref t)
{
    assert(t != nullptr);
    int height = 0;
    //TODO
    int aux;
    if(t->is_empty() == true){
        height=-1;
    }
    else{
        height=compute_height<T>(t->left());
        aux=compute_height<T>(t->right());
        if(height<aux){
            height=aux;
        }
        height++;
    }
    //
    return height;
}

template<class T>
size_t compute_size (typename BTree<T>::Ref t)
{
    assert(t != nullptr);
    size_t ret_val = 0;
    //TODO
    //Hint: when you call a template into other template maybe you need
    // to specialize the call.
    if(t->is_empty()!=true){
        ret_val=1;
        ret_val+=compute_size<T>(t->left());
        ret_val+=compute_size<T>(t->right());
    }
    //
    return ret_val;
}

template <class T, typename Processor>
bool
prefix_process(typename BTree<T>::Ref tree, Processor& p)
{
    assert(tree != nullptr);
    bool retVal = true;
    //TODO
    //Hint: when you call a template into other template maybe you need
    // to specialize the call.
    if(tree->is_empty() == false && retVal == true){
        if(retVal == true){
            retVal=p(tree->item());
        }
        if(retVal == true){
            retVal=prefix_process<T>(tree->left(),p);
        }
        if(retVal == true){
            retVal=prefix_process<T>(tree->right(),p);
        }
    }
    //
    return retVal;
}

template <class T, class Processor>
bool
infix_process(typename BTree<T>::Ref tree, Processor& p)
{
    assert(tree != nullptr);
    bool retVal = true;
    //TODO
    //Hint: when you call a template into other template maybe you need
    // to specialize the call.
    if(tree->is_empty() == false && retVal == true){
        if(retVal == true){
            retVal=infix_process<T>(tree->left(),p);
        }
        if(retVal == true){
            retVal=p(tree->item());
        }
        if(retVal == true){
            retVal=infix_process<T>(tree->right(),p);
        }
    }
    //
    return retVal;
}

template <class T, class Processor>
bool
postfix_process(typename BTree<T>::Ref tree, Processor& p)
{
    assert(tree != nullptr);
    bool retVal = true;
    //TODO
    //Hint: when you call a template into other template maybe you need
    // to specialize the call.
    if(tree->is_empty() == false && retVal == true){
        if(retVal == true){
            retVal=postfix_process<T>(tree->left(),p);
        }
        if(retVal == true){
            retVal=postfix_process<T>(tree->right(),p);
        }
        if(retVal == true){
            retVal=p(tree->item());
        }
    }
    //
    return retVal;
}


template <class T, class Processor>
bool
breadth_first_process(typename BTree<T>::Ref tree, Processor& p)
{
    assert(tree != nullptr);
    bool go_on = true;
    //TODO
    //Hint: think about which data structure can help you to do this kind 
    //  of traversal.
    std::queue<typename BTree<T>::Ref> q;
    q.push(tree);
    while(q.empty() == false && go_on == true){
        if((q.front())->is_empty() == false){
            go_on=p((q.front())->item());
            q.push((q.front())->left());
            q.push((q.front())->right());
        }
        q.pop();
    }
    //
    return go_on;
}

template <class T>
std::ostream&
print_prefix(std::ostream& out, typename BTree<T>::Ref tree)
{
    //TODO
    //You must create a lambda function with a parameter to be printed and
    //  use a prefix_process to process the tree with this lambda.
    //Remember: the lambda must return true.
    auto f_l=[&out](T it){
        out << it << " ";
        return true;
    };
    prefix_process<T>(tree,f_l);
    //
    return out;
}

template <class T>
std::ostream&
print_infix(std::ostream& out, typename BTree<T>::Ref tree)
{
    //TODO
    //You must create a lambda function with a parameter to be printed and
    //  use an infix_process to process the tree with this lambda.
    //Remember: the lambda must return true.
    auto f_l=[&out](T it){
        out << it << " ";
        return true;
    };
    infix_process<T>(tree,f_l);
    //
    return out;
}

template <class T>
std::ostream&
print_postfix(std::ostream& out, typename BTree<T>::Ref tree)
{
    //TODO
    //You must create a lambda function with a parameter to be printed and
    //  use a postfix_process to process the tree with this lambda.
    //Remember: the lambda must return true.
    auto f_l=[&out](T it){
        out << it << " ";
        return true;
    };
    postfix_process<T>(tree,f_l);
    //
    return out;
}

template <class T>
std::ostream&
print_breadth_first(std::ostream& out, typename BTree<T>::Ref tree)
{
    //TODO
    //You must create a lambda function with a parameter to be printed and
    //  use a breadth_first_process to process the tree with this lambda.
    //Remember: the lambda must return true.
    auto f_l=[&out](T it){
        out << it << " ";
        return true;
    };
    breadth_first_process<T>(tree,f_l);
    //
    return out;
}

template <class T>
bool search_prefix(typename BTree<T>::Ref tree, const T& it, size_t& count)
{
    bool found = false;
    count = 0;
    //TODO
    //You must create a lambda function with a parameter to compare it to the
    // value of the element to be searched for.
    // Use the lambda with the prefix_process.
    //Remember: Also, the lambda must update the count variable and
    //must return True/False.
    auto f_l=[&count, it](T item){
        count++;
        if(item == it){
            return false;
        }
        else{
            return true;
        }
    };
    found=!prefix_process<T>(tree,f_l);
    //
    return found;
}

template <class T>
bool search_infix(typename BTree<T>::Ref tree, const T& it, size_t& count)
{
    bool found = false;
    count = 0;
    //TODO
    //You must create a lambda function with a parameter to compare it to the
    // value of the element to be searched for.
    // Use the lambda with the infix_process.
    //Remember: Also, the lambda must update the count variable and
    //must return True/False.
    auto f_l=[&count, it](T item){
        count++;
        if(item == it){
            return false;
        }
        else{
            return true;
        }
    };
    found=!infix_process<T>(tree,f_l);
    //
    return found;
}

template <class T>
bool search_postfix(typename BTree<T>::Ref tree, const T& it, size_t& count)
{
    bool found = false;
    count = 0;
    //TODO
    //You must create a lambda function with a parameter to compare it to the
    // value of the element to be searched for.
    // Use the lambda with the postfix_process.
    //Remember: Also, the lambda must update the count variable and
    //must return True/False.
    auto f_l=[&count, it](T item){
        count++;
        if(item == it){
            return false;
        }
        else{
            return true;
        }
    };
    found=!postfix_process<T>(tree,f_l);
    //
    return found;
}

template <class T>
bool search_breadth_first(typename BTree<T>::Ref tree, const T& it, size_t& count)
{
    bool found = false;
    count = 0;
    //TODO
    //You must create a lambda function with a parameter to compare it to the
    // value of the element to be searched for.
    // Use the lambda with the breadth_first_process.
    //Remember: Also, the lambda must update the count variable and
    //must return True/False.
    auto f_l=[&count, it](T item){
        count++;
        if(item == it){
            return false;
        }
        else{
            return true;
        }
    };
    found=!breadth_first_process<T>(tree,f_l);
    //
    return found;
}

template<class T>
bool check_btree_in_order(typename BTree<T>::Ref const& tree)
{
    bool ret_val = true;
    //TODO
    //Hint: You can create a lambda function with a parameter to compare it with
    // the last the value seen.
    // Use the lambda with the infix_process.
    T item_prev;
    bool first=true;
    auto f_l=[&item_prev, &first](T it){
        if(first == true){
            first=false;
            item_prev=it;
            return true;
        }
        else{
            if(item_prev < it){
                item_prev=it;
                return true;
            }
            else{
                item_prev=it;
                return false;
            }
        }
    };
    ret_val=infix_process<T>(tree, f_l);
    //
    return ret_val;
}

template<class T>
bool has_in_order(typename BTree<T>::Ref tree, T const& v)
{
    assert(check_btree_in_order<T>(tree));    
    bool ret_val = false;
    //TODO
    if(tree->is_empty() == false){
        if(tree->item() > v){
            ret_val=has_in_order<T>(tree->left(),v);
        }
        else if(tree->item() < v){
            ret_val=has_in_order<T>(tree->right(),v);
        }
        else{
            ret_val=true;
        }
    }
    //
    return ret_val;
}

template <class T>
void insert_in_order(typename BTree<T>::Ref tree, T const& v)
{
    assert(check_btree_in_order<T>(tree));
    //TODO
    if(tree->is_empty() == true){
        tree->create_root(v);
    }
    else if(tree->item() > v){
        if((tree->left())->is_empty() == true){
            tree->set_left(BTree<T>::create(v));
        }
        else{
            insert_in_order<T>(tree->left(),v);
        }
    }
    else if(tree->item() < v){
        if((tree->right())->is_empty() == true){
            tree->set_right(BTree<T>::create(v));
        }
        else{
            insert_in_order<T>(tree->right(),v);
        }
    }
    //
    assert(has_in_order<T>(tree, v));
}
