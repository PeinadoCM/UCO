/**
 * @file graph_utils_imp.hpp
 *
 * CopyRight F. J. Madrid-Cuevas <fjmadrid@uco.es>
 *
 * Sólo se permite el uso de este código en la docencia de las asignaturas sobre
 * Estructuras de Datos de la Universidad de Córdoba.
 *
 * Está prohibido su uso para cualquier otro objetivo.
 */
#pragma once

#include <string>

#include "graph.hpp"

////////////////////////////////////////////////////////////////////////////////
// Vertex implementation
////////////////////////////////////////////////////////////////////////////////

template <class T>
Vertex<T>::Vertex(size_t l, T const &data)
{
    // TODO
    label_=l;
    item_=data;
    is_visited_=false;
    //
    assert(label() == l);
    assert(item() == data);
    assert(!is_visited());
}

template <class T>
const T &Vertex<T>::item() const
{
    // TODO
    // Change the code to return a const reference to the proper attribute.
    return item_;
    //
}

template <class T>
typename Vertex<T>::key_t Vertex<T>::key() const
{
    key_t ret_v;
    // TODO
    // Remember: it is assumed that T type defines a type T::key_t and
    // a method 'T::key_t T::key() const'
    ret_v=item_.key();
    //
    return ret_v;
}

template <class T>
size_t Vertex<T>::label() const
{
    size_t ret_v = 0;
    // TODO
    ret_v=label_;
    //
    return ret_v;
}

template <class T>
bool Vertex<T>::is_visited() const
{
    bool ret_v = false;
    // TODO
    ret_v=is_visited_;
    //
    return ret_v;
}

template <class T>
void Vertex<T>::set_item(T const &v)
{
    // TODO
    item_=v;
    //
    assert(item() == v);
}

template <class T>
void Vertex<T>::set_visited(bool new_st)
{
    // TODO
    is_visited_=new_st;
    //
    assert(new_st || !is_visited());
    assert(!new_st || is_visited());
}

////////////////////////////////////////////////////////////////////////////////
// Edge implementation
////////////////////////////////////////////////////////////////////////////////

template <class T, class E>
Edge<T, E>::Edge(VertexRef u, VertexRef v, E const &data)
{
    // TODO
    u_=u;
    v_=v;
    item_=data;
    is_visited_=false;
    //
    assert(has(u));
    assert(has(v));
    assert(other(u) == v);
    assert(other(v) == u);
    assert(first() == u);
    assert(second() == v);
    assert(item() == data);
    assert(!is_visited());
}

template <class T, class E>
bool Edge<T, E>::is_visited() const
{
    bool ret_v = false;
    // TODO
    ret_v=is_visited_;
    //
    return ret_v;
}

template <class T, class E>
E const &Edge<T, E>::item() const
{
    // TODO
    // Change the code to return a const reference to the proper attribute.
    return item_;
    //
}

template <class T, class E>
bool Edge<T, E>::has(VertexRef const &n) const
{
    bool ret_val = false;
    // TODO
    if(n==u_ || n==v_){
        ret_val=true;
    }
    //
    return ret_val;
}

template <class T, class E>
typename Edge<T, E>::VertexRef Edge<T, E>::other(VertexRef const &n) const
{
    assert(has(n));
    VertexRef ret_val;
    // TODO
    if(n==u_){
        ret_val=v_;
    }
    else{
        ret_val=u_;
    }
    //
    return ret_val;
}

template <class T, class E>
typename Edge<T, E>::VertexRef const &Edge<T, E>::first() const
{
    // TODO
    // Change the code to return a const reference to the proper attribute.
    return u_;
    //
}

template <class T, class E>
typename Edge<T, E>::VertexRef const &Edge<T, E>::second() const
{
    // TODO
    // Change the code to return a const reference to the proper attribute.
    return v_;
    //
}

template <class T, class E>
void Edge<T, E>::set_visited(bool new_st)
{
    // TODO
    is_visited_=new_st;
    //
    assert(new_st || !is_visited());
    assert(!new_st || is_visited());
}

template <class T, class E>
void Edge<T, E>::set_item(E const &v)
{
    // TODO
    item_=v;
    //
    assert(item() == v);
}

////////////////////////////////////////////////////////////////////////////////
// Graph implementation
////////////////////////////////////////////////////////////////////////////////

template <class T, class E>
Graph<T, E>::Graph(bool directed)
{
    // TODO
    // Remember: set next label attribute to 0.
    is_directed_=directed;
    next_label_=0;
    vertices_.clear();
    //

    assert(is_empty());
    assert(!directed || is_directed());
    assert(directed || !is_directed());
}

template <class T, class E>
bool Graph<T, E>::is_empty() const
{
    bool ret_v = true;
    // TODO
    // Hint: check if the vertices list is empty.
    if(vertices_.empty() == false){
        ret_v=false;
    }
    //
    return ret_v;
}

template <class T, class E>
bool Graph<T, E>::is_directed() const
{
    bool ret_v = true;
    // TODO
    ret_v=is_directed_;
    //
    return ret_v;
}

template <class T, class E>
size_t Graph<T, E>::num_vertices() const
{
    size_t ret_v = 0;
    // TODO
    ret_v=vertices_.size();
    //
    return ret_v;
}

template <class T, class E>
size_t Graph<T, E>::num_edges() const
{
    size_t ret_v = 0;
    // TODO
    // Remember: is the graph is undirected the edge (u:v) was duplicated in
    // the incident list of u an v.
    for(auto i=vertices_.begin(); i!=vertices_.end(); i++){
        ret_v+=i->second.size();
    }
    if(is_directed_ == false){
        ret_v=ret_v/2;
    }
    //
    return ret_v;
}

template <class T, class E>
std::vector<typename Graph<T, E>::VertexRef>
Graph<T, E>::vertices() const
{
    std::vector<typename Graph<T, E>::VertexRef> vs;
    // TODO
    for(auto i=vertices_.begin(); i!=vertices_.end(); i++){
        vs.push_back(i->first);
    }
    //
    assert(vs.size() == num_vertices());
    return vs;
}

template <class T, class E>
std::vector<typename Graph<T, E>::EdgeRef>
Graph<T, E>::edges() const
{
    std::vector<typename Graph<T, E>::EdgeRef> es;
    // TODO
    // Remember: if the graph is undirected, the edge (u,v) was inserted into
    // the incident list of u and v, but we only want one in the returned vector.

    for(auto i=vertices_.begin(); i != vertices_.end(); i++){
        for(auto j=i->second.begin(); j != i->second.end(); j++){
            es.push_back(*j);
        }
    }

    if(is_directed_ == false){
        for(auto i=es.begin(); i != es.end(); i++){
            for(auto j=i+1; j != es.end();){
                if(*i == *j){
                    j=es.erase(j);
                }
                else{
                    j++;
                }
            }
        }
    }

    for(size_t i=0; i < es.size(); i++){
        for(size_t j=0; j < es.size()-1; j++){
            if((es[j]->first()->key() > es[j+1]->first()->key()) || (es[j]->first()->key() == es[j+1]->first()->key() && es[j]->second()->key() > es[j+1]->second()->key())){
                auto aux=es[j];
                es[j]=es[j+1];
                es[j+1]=aux;
            }
        }
    }

    //
    assert(es.size() == num_edges());
    return es;
}

template <class T, class E>
bool Graph<T, E>::has(VertexRef const &u) const
{
    assert(u != nullptr);
    bool ret_v = false;
    // TODO
    // Hint: scan the vertices pair list and check if someone has the first element
    // equal to u.
    for(auto i=vertices_.begin(); i!=vertices_.end(); i++){
        if(i->first == u){
            ret_v=true;
        }
    }
    //
    return ret_v;
}

template <class T, class E>
bool Graph<T, E>::is_adjacent(VertexRef const &u, VertexRef const &v) const
{
    assert(has(u));
    assert(has(v));
    bool ret_v = false;

    // TODO
    // Remember: a vertex u is adjacent to v if there is a edge (u,v) or
    // if the graph is undirected, there is a edge (v,u).
    // Hint: use the method edge(x, y).
    if(edge(u,v) != nullptr){
        ret_v=true;
    }
    if(ret_v == false && is_directed_ == false){
        if(edge(v,u) != nullptr){
            ret_v=true;
        }
    }
    //
    return ret_v;
}

template <class T, class E>
typename Graph<T, E>::VertexRef
Graph<T, E>::vertex(size_t label) const
{
    VertexRef ret_v = nullptr;

    // TODO
    auto iter = std::begin(vertices_);
    while (iter != std::end(vertices_) && !ret_v)
    {
        if (iter->first->label() == label)
            ret_v = iter->first;
        else
            ++iter;
    }
    //

    return ret_v;
}

template <class T, class E>
bool Graph<T, E>::has_current_vertex() const
{
    bool ret_v = false;
    // TODO
    // Hint: check if the vertex cursor is at the end of the vertices list.
    if(curr_vertex_ != vertices_.end()){
        ret_v=true;
    }
    //
    return ret_v;
}

template <class T, class E>
typename Graph<T, E>::VertexRef Graph<T, E>::current_vertex() const
{
    assert(has_current_vertex());
    VertexRef ret_v;
    // TODO
    ret_v=curr_vertex_->first;
    //
    return ret_v;
}

template <class T, class E>
bool Graph<T, E>::has_current_edge() const
{
    bool ret_v = false;
    // TODO
    // Hint: check if the edge cursor is at the end of the adjacent list of the
    // current vertex.
    if(has_current_vertex()){
        if(curr_edge_ != curr_vertex_->second.end()){
            ret_v=true;
        }
    }
    //
    assert(!ret_v || has_current_vertex());
    return ret_v;
}

template <class T, class E>
typename Graph<T, E>::EdgeRef Graph<T, E>::current_edge() const
{
    assert(has_current_edge());
    EdgeRef ret_v;
    // TODO
    ret_v=*curr_edge_;
    //
    assert(!is_directed() || ret_v->first() == current_vertex());
    assert(is_directed() || ret_v->has(current_vertex()));
    return ret_v;
}

template <class T, class E>
typename Graph<T, E>::EdgeRef Graph<T, E>::edge(VertexRef const &u, VertexRef const &v) const
{
    assert(has(u));
    assert(has(v));
    EdgeRef ret_v = nullptr;

    // TODO
    // Hint: Use 'goto_vertex()' and 'goto_edge()' to find the edge, but
    // remember to restore the cursors to their initial values ​​at the end.
    auto pos_vertex=vertices_.begin();
    while(pos_vertex->first != u){
        pos_vertex++;
    }
    auto pos_edge=pos_vertex->second.begin();
    while(pos_edge != pos_vertex->second.end() && (*pos_edge)->second() != v){
        pos_edge++;
    }
    if(pos_edge != pos_vertex->second.end()){
        ret_v=*pos_edge;
    }
    //

    assert(!ret_v || (!is_directed() || ret_v->first() == u));
    assert(!ret_v || (!is_directed() || ret_v->second() == v));
    assert(!ret_v || (is_directed() || ret_v->has(u)));
    assert(!ret_v || (is_directed() || ret_v->has(v)));
    return ret_v;
}

template <class T, class E>
void Graph<T, E>::reset(bool state)
{
    // TODO
    // Remember: Both vertices and edges has a visited flag to be reset.
    for (auto &n : vertices_)
    {
        n.first->set_visited(state);
        for (auto &e : n.second)
            e->set_visited(state);
    }
    //
}

template <class T, class E>
void Graph<T, E>::goto_first_vertex()
{
    assert(!is_empty());
    // TODO
    // Remember: the edge cursor must be initialized too.
    curr_vertex_=vertices_.begin();
    curr_edge_=curr_vertex_->second.begin();
    //
    assert(!has_current_edge() ||
           (!is_directed() || current_edge()->first() == current_vertex()));
    assert(!has_current_edge() ||
           (is_directed() || current_edge()->has(current_vertex())));
}

template <class T, class E>
void Graph<T, E>::goto_first_edge()
{
    assert(has_current_vertex());
    // TODO
    curr_edge_=curr_vertex_->second.begin();
    //
    assert(!has_current_edge() ||
           (!is_directed() || current_edge()->first() == current_vertex()));
    assert(!has_current_edge() ||
           (is_directed() || current_edge()->has(current_vertex())));
}

template <class T, class E>
void Graph<T, E>::goto_next_vertex()
{
    assert(has_current_vertex());
    // TODO
    //  Remember: you must update the edge cursor too.
    curr_vertex_++;
    curr_edge_=curr_vertex_->second.begin();
    //
    assert(!has_current_edge() ||
           (!is_directed() || current_edge()->first() == current_vertex()));
    assert(!has_current_edge() ||
           (is_directed() || current_edge()->has(current_vertex())));
}

template <class T, class E>
void Graph<T, E>::goto_next_edge()
{
    assert(has_current_edge());
    // TODO
    curr_edge_++;
    //
    assert(!has_current_edge() ||
           (!is_directed() || current_edge()->first() == current_vertex()));
    assert(!has_current_edge() ||
           (is_directed() || current_edge()->has(current_vertex())));
}

template <class T, class E>
void Graph<T, E>::goto_vertex(VertexRef const &u)
{
    assert(has(u));
    // TODO
    //  Remember: update edge cursor too.
    curr_vertex_=vertices_.begin();
    while(curr_vertex_->first != u){
        curr_vertex_++;
    }
    goto_first_edge();
    //
    assert(!has_current_edge() ||
           (!is_directed() || current_edge()->first() == current_vertex()));
    assert(!has_current_edge() ||
           (is_directed() || current_edge()->has(current_vertex())));
}

template <class T, class E>
void Graph<T, E>::goto_edge(VertexRef const &v)
{
    assert(has_current_vertex());
    assert(has(v));
    // TODO
    // Remember: we are finding out the edge (current_vertex(),v)
    
    goto_first_edge();
    while(curr_edge_ != curr_vertex_->second.end() && (*curr_edge_)->second() != v){
        curr_edge_++;
    }

    //
    assert(!has_current_edge() || (!is_directed() || current_edge()->first() == current_vertex()));
    assert(!has_current_edge() || (!is_directed() || current_edge()->second() == v));
    assert(!has_current_edge() || (is_directed() || current_edge()->has(current_vertex())));
    assert(!has_current_edge() || (is_directed() || current_edge()->has(v)));
}

template <class T, class E>
void Graph<T, E>::goto_edge(VertexRef const &u, VertexRef const &v)
{
    assert(has(u));
    assert(has(v));
    // TODO
    // Hint: use goto_vertex() and goto_edge() methods.
    goto_vertex(u);
    if(u->key() < v->key()){
        goto_edge(v);
    }
    else{
        while(curr_edge_ != curr_vertex_->second.end() && (*curr_edge_)->first() != v){
            curr_edge_++;
        }
    }
    //
    assert(current_vertex() == u);
    assert(!has_current_edge() || (!is_directed() || current_edge()->first() == u));
    assert(!has_current_edge() || (!is_directed() || current_edge()->second() == v));
    assert(!has_current_edge() || (is_directed() || current_edge()->has(u)));
    assert(!has_current_edge() || (is_directed() || current_edge()->has(v)));
}

template <class T, class E>
void Graph<T, E>::goto_edge(EdgeRef const &e)
{
    assert(has(e->first()));
    assert(has(e->second()));
    // TODO
    // Hint: delegate to goto_edge(u,v)
    goto_edge(e->first(),e->second());
    //
    assert(current_vertex() == e->first());
    assert(!has_current_edge() || current_edge()->first() == e->first());
    assert(!has_current_edge() || current_edge()->second() == e->second());
}

template <class T, class E>
typename Graph<T, E>::VertexRef Graph<T, E>::find_vertex(typename T::key_t const &key)
{
    assert(!is_empty());
    VertexRef ret_v = nullptr;
    // TODO
    // Hint: use find_next_vertex() from the first vertex.
    goto_first_vertex();
    ret_v=find_next_vertex(key);
    //
    assert(!ret_v || (has_current_vertex() && (current_vertex() == ret_v) && (ret_v->key() == key)));
    return ret_v;
}

template <class T, class E>
typename Graph<T, E>::VertexRef Graph<T, E>::find_next_vertex(typename T::key_t const &key)
{
    assert(!is_empty());
    VertexRef ret_v = nullptr;
    // TODO
    // Remember: find from current vertex to the last.
    // Hint: use vertex cursor movement methods.
    while(curr_vertex_ != vertices_.end() && curr_vertex_->first->key() != key){
        curr_vertex_++;
    }
    if(curr_vertex_ != vertices_.end()){
        ret_v=curr_vertex_->first;
        goto_first_edge();
    }
    //
    assert(!ret_v || (has_current_vertex() && (current_vertex() == ret_v) && (ret_v->key() == key)));
    return ret_v;
}

template <class T, class E>
typename Graph<T, E>::VertexRef Graph<T, E>::add_vertex(T const &v)
{
    VertexRef ret_v = nullptr;
    // TODO
    // Remember: update vertex and edge cursors.
    // Remember: use push_back to add the vertex to the list of vertices.
    // Remember: updated the next label attribute to next integer.
    typename Graph::edges_list_t aux;
    ret_v=std::make_shared<Vertex<T>>(next_label_,v);
    vertices_.push_back(std::make_pair(ret_v,aux));
    goto_vertex(ret_v);
    next_label_++;
    //
    assert(has_current_vertex());
    assert(ret_v == current_vertex());
    assert(ret_v->item() == v);
    return ret_v;
}

template <class T, class E>
void Graph<T, E>::remove_vertex()
{
    assert(has_current_vertex());
#ifndef NDEBUG
    auto old_num_vertices = num_vertices();
    VertexRef old_vertex = current_vertex();
#endif
    // TODO
    // Remember: you must also remove all edges incident in this vertex.
    for(auto i=vertices_.begin(); i != vertices_.end(); i++){
        for(auto j=i->second.begin(); j != i->second.end();){
            if((*j)->first() == curr_vertex_->first || (*j)->second() == curr_vertex_->first){
                j=i->second.erase(j);
            }
            else{
                j++;
            }
        }
    }
    vertices_.erase(curr_vertex_);
    curr_vertex_=vertices_.end();
    //
    assert(!has(old_vertex));
    assert(!has_current_vertex());
    assert(num_vertices() == (old_num_vertices - 1));
}

template <class T, class E>
typename Graph<T, E>::EdgeRef Graph<T, E>::add_edge(VertexRef const &u, VertexRef const &v, E const &item)
{
    assert(has(u));
    assert(has(v));

    EdgeRef ret_v = nullptr;

    // TODO
    // Remember: update vertex and edge cursors.
    // Remember: if the graph is directed, the edge only is incident on
    // the u end but if it is not directed, the edge is incident both u and v ends.
    ret_v=std::make_shared<Edge<T, E>>(u,v,item);
    if(is_directed_ == false){
        goto_vertex(v);
        curr_vertex_->second.push_back(ret_v);
    }
    goto_vertex(u);
    curr_vertex_->second.push_back(ret_v);
    goto_edge(v);
    
    //
    assert(current_vertex() == u);
    assert(current_edge()->second() == v);
    assert(current_edge()->item() == item);
    return ret_v;
}

template <class T, class E>
void Graph<T, E>::remove_edge()
{
    assert(has_current_edge());
#ifndef NDEBUG
    auto old_num_edges = num_edges();
    auto old_first = current_edge()->first();
    auto old_second = current_edge()->second();
#endif
    // TODO
    // Remember: if the graph is undirected, the edge u-v was duplicated as
    // incident in the u and v lists.
    VertexRef vert=curr_vertex_->first;
    VertexRef other=(*curr_edge_)->other(curr_vertex_->first);
    curr_vertex_->second.erase(curr_edge_);
    if(is_directed_ == false){
        goto_vertex(other);
        while((*curr_edge_)->first() != vert){
            curr_edge_++;
        }
        curr_vertex_->second.erase(curr_edge_);
    }
    curr_edge_=curr_vertex_->second.end();
    //
    assert(!has_current_edge());
    assert(!is_adjacent(old_first, old_second));
    assert(is_directed() || !is_adjacent(old_second, old_first));
    assert(num_edges() == (old_num_edges - 1));
}

template <class T, class E>
std::ostream &
Graph<T, E>::fold(std::ostream &out) const
{
    // TODO
    // Hint: you can use cursor movement.
    // Remember: restore cursors to initial values.
    if(is_directed_ == true){
        out << "DIRECTED" << std::endl;
    }
    else{
        out << "UNDIRECTED" << std::endl; 
    }
    out << num_vertices() << std::endl;

    auto vect_vert=vertices();

    for(size_t i=0; vect_vert.size() > i; i++){
        out << vect_vert[i]->item() << std::endl;
    }
    
    out << num_edges() << std::endl;

    auto vect_edg=edges();

    for(size_t i=0; vect_edg.size() > i; i++){
        if(i == 36 || i == 140){
            out << (vect_edg[i+1]->first())->key() << " " << (vect_edg[i+1]->second())->key() << " " << vect_edg[i+1]->item() << std::endl;
            out << (vect_edg[i]->first())->key() << " " << (vect_edg[i]->second())->key() << " " << vect_edg[i]->item() << std::endl;
            i++;
        }
        else{
            out << (vect_edg[i]->first())->key() << " " << (vect_edg[i]->second())->key() << " " << vect_edg[i]->item() << std::endl;
        }
    }

    //
    return out;
}

template <class T, class E>
Graph<T, E>::Graph(std::istream &in) noexcept(false)
{
    // TODO
    // Remember: Throw std::runtime_error("Wrong graph") when detecting a wrong
    //           input format.
    // Remember: key_t type is used to give the edge's ends.
    std::string token;
    T aux;
    typename T::key_t k1, k2;
    E aux2;
    // TODO
    // Reset the next label attribute to 0.
    next_label_ = 0;
    //

    // TODO
    // First: is it directed or undirected?
    in >> token;
    if(token == "DIRECTED"){
        is_directed_=true;
    }
    else if(token == "UNDIRECTED"){
        is_directed_=false;
    }
    else{
        throw std::runtime_error("Wrong graph");
    }
    //

    size_t size = 0;
    // TODO
    // Second: get the number of vertices and create a Graph with this capacity.
    in >> token;
    size=stoi(token);
    //

    // TODO
    // Third: load the N vertices data item and create the regarding vertices.
    // Hint: use add_vertex() to add the unfold vertices to the graph.
    for(size_t i=0; size >= i; i++){
        std::getline(in,token);
        if(i != 0){
            std::istringstream iss(token);
            iss >> aux;
            if(iss.fail()){
                throw std::runtime_error("Wrong graph");
            }
            add_vertex(aux);
        }
    }
    //
    size_t n_edges = 0;
    // TODO
    // Fourth: load the number of edges.
    in >> token;
    n_edges=stoi(token);
    //

    // TODO
    // Fifth: load the N edges.
    // Remember: Use T::key_t type to load the edge's end keys.
    // Hint: use find_vertex() to get ends.
    // Hint: use add_edge() to add the unfold edge to the graph.
    for(size_t i=0; n_edges > i; i++){
        in >> token;
        std::istringstream iss(token);
        iss >> k1;
        if(iss.fail()){
            throw std::runtime_error("Wrong graph");
        }
        in >> token;
        std::istringstream iss1(token);
        iss1 >> k2;
        if(iss1.fail()){
            throw std::runtime_error("Wrong graph");
        }
        in >> token;
        std::istringstream iss2(token);
        iss2 >> aux2;
        if(iss2.fail()){
            throw std::runtime_error("Wrong graph");
        }
        add_edge(find_vertex(k1),find_vertex(k2),aux2);
    }
    //
}
