(deffacts Datos
    (datos 1 2)
    (datos 3 2)
    (datos 3)
    (dato 3 4)
    (datos 3 4)
)

(defrule mayor
    (datos ?val1 ?val2&:(> ?val2 ?val1))
    =>
    (printout t ?val2 " > " ?val1 crlf)
)