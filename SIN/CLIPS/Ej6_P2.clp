(deffacts hechos
    (vector v1 2 3 6 1 4)
    (vector v2 2 6 1 4)
    (vector v3 3 2 6 1 4)
)

(defrule r-3-4-impar
    (vector ?nombre $? 3 $?valores 4 $?)
    (test (oddp (length$ $?valores)))
    =>
    (printout t "El vector " ?nombre " cumple las condiciones"  crlf)
)