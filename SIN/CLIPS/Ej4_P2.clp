(deffacts Datos
    (datos 2 2)
    (datos 2 3 4 5 6)
    (datos 1)
    (datos 4)
)

(defrule mostrar
    (datos ?prim&:(= 0 (mod ?prim 2)) $?med ?ult&:(<= ?prim ?ult))
    =>
    (printout t "Primer valor: "?prim " Segundo valor: " ?ult crlf)
)