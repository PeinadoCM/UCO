/**
 * @file hash_table_entry_imp.hpp
 *
 * CopyRight F. J. Madrid-Cuevas <fjmadrid@uco.es>
 *
 * Sólo se permite el uso de este código en la docencia de las asignaturas sobre
 * Estructuras de Datos de la Universidad de Córdoba.
 *
 * Está prohibido su uso para cualquier otro objetivo.
 */

#pragma once

#include <cassert>
#include "hash_table_entry.hpp"

template <class K, class V>
HashTableEntry<K, V>::HashTableEntry()
{
    // TODO
    state_=EMPTY;
    //
    assert(is_empty());
}

template <class K, class V>
HashTableEntry<K, V>::HashTableEntry(const K &k, const V &v)
{
    // TODO
    key_=k;
    value_=v;
    state_=VALID;
    //
    assert(is_valid());
    assert(key() == k);
    assert(value() == v);
}

template <class K, class V>
bool HashTableEntry<K, V>::is_valid() const
{
    bool ret_val = false;
    // TODO
    if(state_ == VALID){
        ret_val=true;
    }
    //
    return ret_val;
}

template <class K, class V>
bool HashTableEntry<K, V>::is_empty() const
{
    bool ret_val = false;
    // TODO
    if(state_ == EMPTY){
        ret_val=true;
    }
    //
    return ret_val;
}

template <class K, class V>
bool HashTableEntry<K, V>::is_deleted() const
{
    bool ret_val = false;
    // TODO
    if(state_ == DELETED){
        ret_val=true;
    }
    //
    return ret_val;
}

template <class K, class V>
K const &
HashTableEntry<K, V>::key() const
{
    assert(!is_empty());
    // TODO: recode according to your representation.
    return key_;
    //
}

template <class K, class V>
void HashTableEntry<K, V>::set(const K &k, const V &v)
{
    // TODO
    key_=k;
    value_=v;
    state_=VALID;
    //
    assert(is_valid());
    assert(key() == k);
    assert(value() == v);
}

template <class K, class V>
V const &HashTableEntry<K, V>::value() const
{
    // TODO: recode according to your representation.
    return value_;
    //
}

template <class K, class V>
V &HashTableEntry<K, V>::value()
{
    assert(is_valid());

    // TODO: recode according to your representation.
    return value_;
    //
}

template <class K, class V>
void HashTableEntry<K, V>::set_deleted()
{
    assert(is_valid());
    // TODO
    state_=DELETED;
    //
    assert(is_deleted());
}
