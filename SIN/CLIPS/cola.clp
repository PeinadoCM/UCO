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
	?cola <- (cola $?x)
	?dato <- (dato ?y)
	=>
	(retract ?cola ?dato)
	(assert (cola $?x ?y))
)

(defrule pop
	?cola <- (cola ?x $?y)
    =>
	(retract ?cola)
	(assert (cola $?y) (extraido ?x))
)

; ¿Cómo se haría una cola?