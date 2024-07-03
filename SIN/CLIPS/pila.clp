(deffacts init-estructuras-datos
	(pila)
    (cola)
    (dato 4) ; Los datos los declaro en el orden inverso al que quiero que los lea CLIPS porque siempre toma los más recientes primero.
    (dato 3)
    (dato 2)
    (dato 1)
)

(defrule push
    (declare (salience 10))
	?pila <- (pila $?x)
	?dato <- (dato ?y)
	=>
	(retract ?pila ?dato)
	(assert (pila ?y $?x))
)

(defrule pop
	?pila <- (pila ?x $?y)
    =>
	(retract ?pila)
	(assert (pila $?y) (extraido ?x))
)

; ¿Cómo se haría una cola?