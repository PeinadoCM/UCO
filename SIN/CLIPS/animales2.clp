(deftemplate animales
    (slot sonido (type SYMBOL) (allowed-symbols guau miau kikiriki beee))
)

(defrule sonido-perro
    (animales (sonido guau))
    =>
    (printout t "El animal que hace guau es el perro" crlf)
)

(defrule sonido-gato
    (animales (sonido miau))
    =>
    (printout t "El animal que hace miau es el gato" crlf)
)

(defrule sonido-gallo
    (animales (sonido kikiriki))
    =>
    (printout t "El animal que hace kikiriki es el gallo" crlf)
)