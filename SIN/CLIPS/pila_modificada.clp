(deffacts init-estructuras-datos
    (elementos 5)
    (dato 1)
)

(defrule no-pila
    (not (pila))
    =>
    (assert (pila))
)

(defrule push
    (declare (salience 10))
    ?pila <- (pila $?x)
    ?dato <- (dato ?y&:(numberp ?y)) ;Comprobamos que dato sea un numero
    ?ele <- (elementos ?n&:(> ?n 0)) ;Comprobamos que elementos es mayor que 0
    ;(test (numberp ?y)) Se puede poner en vez de &:(numberp ?y) en dato
    =>
    (retract ?pila ?dato ?ele)
    (assert (pila ?y $?x))
    (assert (dato (+ ?y 1))) ;Incrementamos dato
    (assert (elementos (- ?n 1))) ;Decrementamos elementos
)

(defrule pop
    ?pila <- (pila ?x $?y)
    =>
    (retract ?pila)
    (assert (pila $?y) (extraido ?x))
)
