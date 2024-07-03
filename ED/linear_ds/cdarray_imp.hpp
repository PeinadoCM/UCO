/**
 * CopyRight F. J. Madrid-Cuevas <fjmadrid@uco.es>
 *
 * Sólo se permite el uso de este código en la docencia de las asignaturas sobre
 * Estructuras de Datos de la Universidad de Córdoba.
 *
 * Está prohibido su uso para cualquier otro objetivo.
 */
#pragma once
#include <sstream>
#include <cassert>
#include "cdarray.hpp"

template <class T>
CDArray<T>::CDArray(size_t cap)
{
    assert(cap > 0);
    // TODO
    std::shared_ptr<T[]> aux(new T[cap]);
    data_=aux;
    capacity_=cap;
    size_=0;
    //
    assert(capacity() == cap);
    assert(is_empty());
    assert(size() == 0);
}

template <class T>
CDArray<T>::~CDArray()
{
    // TODO
    // Remember: depending on your representation, it is possible that nothing
    // must be done.
    //
}

template <class T>
typename CDArray<T>::Ref
CDArray<T>::create(size_t cap)
{
    return std::make_shared<CDArray<T>>(cap);
}

template <class T>
typename CDArray<T>::Ref
CDArray<T>::create(std::istream &in) noexcept(false)
{
    auto ret_v = std::make_shared<CDArray<T>>(1);
    // TODO
    // Hint: use std::istringstream to convert from "string" to template
    //  parameter T type.
    // Remember: throw std::runtime_error("Wrong input format.") exception when an input
    //  format error was found.
    std::string token;
    T aux;
    in >> token;
    if(token=="["){
        in >> token;
        while(in.eof()==false && token != "]"){
            std::istringstream iss(token);
            iss >> aux;
            if(iss.fail()){
                throw std::runtime_error("Wrong input format.");
            }
            ret_v->push_back(aux);
            in >> token;
        }
        if(token != "]"){
            throw std::runtime_error("Wrong input format.");
        }
    }
    else if(token != "[]"){
        throw std::runtime_error("Wrong input format.");
    }
    //
    return ret_v;
}

template <class T>
bool CDArray<T>::is_empty() const
{
    bool ret_v = false;
    // TODO
    if(size_ == 0){
        ret_v=true;
    }
    //
    return ret_v;
}

template <class T>
bool CDArray<T>::is_full() const
{
    bool ret_v = false;
    // TODO
    if(size_ == capacity_){
        ret_v=true;
    }
    //
    assert(!ret_v || size() == capacity());
    return ret_v;
}

template <class T>
size_t
CDArray<T>::capacity() const
{
    size_t ret_v = 0;
    // TODO
    ret_v=capacity_;
    //
    return ret_v;
}

template <class T>
size_t
CDArray<T>::size() const
{
    size_t ret_v = 0;
    // TODO
    ret_v=size_;
    //
    return ret_v;
}

template <class T>
std::ostream &
CDArray<T>::fold(std::ostream &out) const
{
    // TODO
    out<<"[";
    for(int i=0;size_>i;i++){
        out << " ";
        out << data_.get()[i];
    }
    out<<" ]";

    //
    return out;
}

template <class T>
T CDArray<T>::get(size_t pos) const
{
    T ret_v;
    // TODO
    ret_v=data_.get()[((pos+front_)%capacity_)];
    //
    return ret_v;
}

template <class T>
void CDArray<T>::set(size_t pos, T const &new_v)
{
    // TODO
    data_.get()[(pos+front_)%capacity_]=new_v;
    //
    assert(new_v == get(pos));
}

size_t cInc(size_t v, size_t size)
{
    size_t ret_v;
    // TODO
    ret_v=(v+1)%size;
    //
    return ret_v;
}

size_t cDec(size_t v, size_t size)
{
#ifndef NDEBUG
    size_t old_v = v;
#endif
    size_t ret_v;
    // TODO
    // Remember: v is a unsigned value, so you must cast to signed before decrementing
    ret_v=((int)v-1)%size;
    //
    assert(old_v == cInc(ret_v, size));
    return ret_v;
}

template <class T>
void CDArray<T>::push_front(T const &new_it)
{
#ifndef NDEBUG
    size_t old_size = size();
    bool old_is_empty = is_empty();
    T old_front = is_empty() ? T() : get(0);
#endif
    // TODO
    if(is_empty()){
        front_=0;
        back_=0;
    }
    if(is_full()){
        grow();
    }
    front_=(front_-1)%capacity_;
    set(0,new_it);
    size_++;
    //
    assert(size() == old_size + 1);
    assert(get(0) == new_it);
    assert(old_is_empty || (get(1) == old_front));
}

template <class T>
void CDArray<T>::push_back(T const &new_it)
{
#ifndef NDEBUG
    size_t old_size = size();
    bool old_is_empty = is_empty();
    T old_back = is_empty() ? T() : get(size() - 1);
#endif
    // TODO
    if(is_empty()){
        front_=0;
        back_=0;
    }
    if(is_full()){
        grow();
    }
    back_=(back_+1)%capacity_;
    set(size_,new_it);
    size_++;

    //
    assert(size() == old_size + 1);
    assert(get(size() - 1) == new_it);
    assert(old_is_empty || (get(size() - 2) == old_back));
}

template <class T>
void CDArray<T>::pop_front()
{
#ifndef NDEBUG
    size_t old_size = size();
    T old_next_front = size() > 1 ? get(1) : T();
#endif
    // TODO
    front_=(front_+1)%capacity_;
    size_--;
    //
    assert(size() == old_size - 1);
    assert(is_empty() || get(0) == old_next_front);
}

template <class T>
void CDArray<T>::pop_back()
{
#ifndef NDEBUG
    size_t old_size = size();
    T old_prev_back = size() > 1 ? get(size() - 2) : T();
#endif
    // TODO
    back_=(back_-1)%capacity_;
    size_--;
    //
    assert(size() == old_size - 1);
    assert(is_empty() || get(size() - 1) == old_prev_back);
}

template <class T>
void CDArray<T>::insert(size_t pos, T const &v)
{
    assert(pos >= 0 && pos < size());
#ifndef NDEBUG
    size_t old_size = size();
    T old_back = get(size() - 1);
    T old_pos_v = get(pos);
#endif
    // TODO
    // Hint: if pos==0, delegate in push_front.
    // Remember: back_ needs to be updated too.
    if(is_full()){
        grow();
    }
    if(pos==0){
        push_front(v);
    }
    else{
        for(int i=back_; i!=(pos+front_)%capacity_;i=cDec(i,capacity_)){
            set((front_+i),data_.get()[(i-1)+front_]);
        }
        set(pos,v);
        back_=(back_+1)%capacity_;
        size_++;
    }
    //
    assert(size() == old_size + 1);
    assert(get(pos) == v);
    assert(get(pos + 1) == old_pos_v);  
    assert(get(size() - 1) == old_back);
}

template <class T>
void CDArray<T>::remove(size_t pos)
{
    assert(pos >= 0 && pos < size());
#ifndef NDEBUG
    size_t old_size = size();
    T old_next_pos_v = (pos <= (size() - 2)) ? get(pos + 1) : T();
#endif
    // TODO
    // Remember: back_ needs to be updated.
    for(int i=(pos+front_)%capacity_; i!=back_;i=cInc(i,capacity_)){
        set((i+front_),data_[i+1+front_]);
    }
    size_--;
    back_=(back_-1)%capacity_;
    //
    assert(size() == old_size - 1);
    assert(pos == size() || get(pos) == old_next_pos_v);
}

template <class T>
void CDArray<T>::grow()
{
#ifndef NDEBUG
    size_t old_capacity = capacity();
#endif
    // TODO
    size_t cap=capacity();
    capacity_=capacity_*2;
    std::shared_ptr<T[]> aux(new T[(capacity_)]);
    for(int i=0;size_>i;i++){
        aux.get()[i]=data_.get()[(front_+i)%cap];
    }
    data_=aux;
    front_=0;
    back_=size_;
    assert(capacity() == 2 * old_capacity);
}
