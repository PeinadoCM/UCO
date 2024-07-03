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
#include "queue.hpp"

template <class T>
Queue<T>::Queue()
{
    // TODO
    data_=data_->create();
    //
    assert(is_empty());
}

template <class T>
bool Queue<T>::is_empty() const
{
    bool ret_val = true;
    // TODO
    if(data_->size()!=0){
        ret_val=false;
    }
    //
    return ret_val;
}

template <class T>
size_t
Queue<T>::size() const
{
    size_t ret_val = 0;
    // TODO
    ret_val=data_->size();
    //
    return ret_val;
}

template <class T>
T Queue<T>::front() const
{
    assert(!is_empty());
    T ret_val;
    // TODO
    ret_val=data_->get(0);
    //
    return ret_val;
}

template <class T>
T Queue<T>::back() const
{
    assert(!is_empty());
    T ret_val;
    // TODO
    ret_val=data_->get(data_->size()-1);
    //
    return ret_val;
}

template <class T>
void Queue<T>::enque(const T &new_it)
{
#ifndef NDEBUG
    size_t old_size = size();
#endif
    // TODO
    data_->push_back(new_it);
    //
    assert(back() == new_it);
    assert(size() == (old_size + 1));
}

template <class T>
void Queue<T>::deque()
{
    assert(!is_empty());
#ifndef NDEBUG
    size_t old_size = size();
#endif
    // TODO
    data_->pop_front();
    //
    assert(size() == (old_size - 1));
}
