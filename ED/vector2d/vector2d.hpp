/**
 * CopyRight F. J. Madrid-Cuevas <fjmadrid@uco.es>
 *
 * Sólo se permite el uso de este código en la docencia de las asignaturas sobre
 * Estructuras de Datos de la Universidad de Córdoba.
 *
 * Está prohibido su uso para cualquier otro objetivo.
 */
#pragma once

#include <iostream>
#include <memory>

/**
 * @brief Models a vector in the plane.
 *
 * A tiny model to practice several c++ concepts:
 * - C++ templates.
 * - typename keyword.
 * - Shared references.
 * - Static class method.
 * - Implement the "Design by Contract" paradigm in c++
 *
 * The specification to implement is:
 *
 * Makers:
 *
 * create() //Default constructor. Return a reference to a zero vector.
 *   - post-c: x()==0 and y()==0
 * create(x:T, y:T) //return a reference to a vector given its ordinates.
 *   - post-c: x()==x and y()==y
 *
 * Observers:
 *
 * x():T // the x ordinate.
 * y():T // the y ordinate.
 *
 * Modifiers:
 *
 * set_x(T x) //set the x ordinate.
 *   - post-c: x==x()
 * set_y(T y) //set the y ordinate.
 *   - post-c: y==y()
 * add(Vector2D o) // add the vector o to this.
 *   - post-c: x()==old.x()+o.x()
 *   - post-c: y()==old.y()+o.y()
 *
 *
 * @tparam T represent the number type for the ordinates.
 */

// TODO: define the interface for the proposed ADT.
// Remember: here we want interfaces not implementations.

template <typename T> class Vector2D{
        public:
            typedef std::shared_ptr<Vector2D<T>> Ref;
            T x_;
            T y_;
            static  Ref create(){
                auto p=std::make_shared<Vector2D<T>>();
                p->set_x(0);
                p->set_y(0);
                return p;
            }
            static  Ref create(T x, T y){
                auto p=std::make_shared<Vector2D<T>>();
                p->set_x(x);
                p->set_y(y);
                return p;
            }
            T x(){return x_;}
            T y(){return y_;}
            void set_x(T x){x_=x;}  
            void set_y(T y){y_=y;} 
            void add(Ref o){
                x_=x_+o->x();
                y_=y_+o->y();
            }
            
};

//

#include "vector2d_imp.hpp"