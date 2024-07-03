/**
 * @file hash_table_imp.hpp
 *
 * CopyRight F. J. Madrid-Cuevas <fjmadrid@uco.es>
 *
 * Sólo se permite el uso de este código en la docencia de las asignaturas sobre
 * Estructuras de Datos de la Universidad de Córdoba.
 *
 * Está prohibido su uso para cualquier otro objetivo.
 */

#pragma once

#ifdef __DEBUG__
#include <iomanip>
#include <iostream>
#endif

#include <cassert>

#include "hash_table.hpp"

template <class K, class V>
HashTable<K, V>::HashTable(size_t m, const Key_to_uint64_t &key_to_int, OACollisionResolution::Ref hash)
{
    assert(m == hash->m());
    // TODO
    key_to_int_=key_to_int;
    hash_=hash;
    table_.resize(m);
    used_entries_=0;
    //
    assert(size() == m);
}

template <class K, class V>
size_t
HashTable<K, V>::size() const
{
    size_t ret_v = 0;
    // TODO
    ret_v=table_.size();
    //
    return ret_v;
}

template <class K, class V>
float HashTable<K, V>::load_factor() const
{
    float ret_v = 0.0f;
    // TODO
    ret_v=static_cast<float>(used_entries_)/size();
    //
    return ret_v;
}

template <class K, class V>
bool HashTable<K, V>::has(K const &k) const
{
    bool ret_v = false;
    // TODO
    //  Hint: use the find method.
    size_t idx=find(k);
    if(table_[idx].is_valid()){
        if(table_[idx].key() == k){
            ret_v=true;
        }
    }
    //
    return ret_v;
}

template <class K, class V>
size_t HashTable<K, V>::find(K const &k) const
{
    assert(load_factor() < 1.0f);
    size_t idx = 0;
    // TODO
    size_t i=0;
    idx=(*hash_)(key_to_int_(k),i);
    while(table_[idx].is_empty() == false && key_to_int_(table_[idx].key()) != key_to_int_(k)){
        i++;
        idx=(*hash_)(key_to_int_(k),i);
    }
    //
    assert(0 <= idx && idx < size());
    return idx;
}

template <class K, class V>
size_t HashTable<K, V>::insert(K const &k, V const &v)
{
    assert(load_factor() <= 0.5f);
    size_t idx = 0;
    // TODO
    // Remember: increment the number of used entries only when an
    // empty entry was used.
    idx=find(k);
    if((table_[idx]).is_empty()){
        used_entries_++;
    }
    table_[idx].set(k,v);
    rehash();
    idx=find(k);
    //
    assert(idx < size());
    assert(entry(idx).is_valid());
    assert(entry(idx).key() == k);
    assert(entry(idx).value() == v);
    assert(load_factor() <= 0.5f);
    return idx;
}

template <class K, class V>
void HashTable<K, V>::remove(size_t idx)
{
#ifndef NDEBUG
    assert(entry(idx).is_valid());
    K old_k = entry(idx).key();
#endif
    // TODO
    // Remember: we are using a mark to sign "deleted".
    table_[idx].set_deleted();
    //
    assert(!entry(idx).is_valid());
    assert(!has(old_k));
}

template <class K, class V>
size_t HashTable<K, V>::rehash()
{
    // TODO
    // Remember: rehash when load_factor > 0.5
    // Remember: we use a 2 factor to grown the current size.
    // Remember: a new hash function will be picked at random from the Universal Family.
    // Remember: reset the number of used entries to zero before inserting the old valid
    // entries in the new table.
    if(load_factor() > 0.5){
        auto aux=table_;
        table_.clear();
        table_.resize(2*aux.size());
        hash_=hash_->pick_at_new(table_.size());
        used_entries_=0;
        for(size_t i=0; aux.size()>i; i++){
            if(aux[i].is_valid()){
                insert(aux[i].key(), aux[i].value());
            }
        }
    }
    //
    assert(load_factor() <= 0.5);
    return size();
}

template <class K, class V>
HashTableEntry<K, V> const &HashTable<K, V>::entry(size_t idx) const
{
    assert(idx < size());
    // TODO: recode according to your representation.
    return table_[idx];
    //
}

template <class K, class V>
HashTableEntry<K, V> &HashTable<K, V>::entry(size_t idx)
{
    assert(idx < size());
    // TODO: recode according to your representation.
    return table_[idx];
    //
}

template <class K, class V>
V const &HashTable<K, V>::operator[](K const &k) const
{
    assert(has(k));
    // TODO: recode according to your representation.
    return (table_[find(k)]).value();
    //
}

template <class K, class V>
V &HashTable<K, V>::operator[](K const &k)
{
    // TODO: recode according to your representation.
    // Remember: if an entry exits for the key, return a reference to its value,
    // else insert a new entry pair <k, _> and return a reference to its value.
    if(has(k) == false){
        insert(k,0);
    }
    return (table_[find(k)]).value();
    //
}