(deftemplate casilla
    (slot fil)
    (slot col)
    (multislot peligros)
)

(deffacts Inicio
    (directions north east south west)
    (hice east)
    (percepts)
    (hasLaser)
    (willy 3 2)
    (myNumMoves 19)
    (casilla (fil 2) (col 4) (peligros Noise))
    (casilla (fil 3) (col 1) (peligros))
    (casilla (fil 3) (col 2) (peligros))
    (casilla (fil 3) (col 3) (peligros))
    (casilla (fil 3) (col 4) (peligros))
    (casilla (fil 4) (col 1) (peligros))
    (casilla (fil 4) (col 2) (peligros))
    (casilla (fil 4) (col 3) (peligros))
)

(defrule MoverSouthNoVisitado "Mueve a Willy al sur en caso de que la casilla no este visitada"
    (directions $? south $?)
    ?h <- (hice ?) 
    ?w <- (willy ?f ?c)
    ?p <- (percepts)
    (not (percepts $? ? $?))
    (not (casilla (fil =(- ?f 1)) (col ?c)))
    ?nmov <- (myNumMoves ?mov&:(< ?mov 998))
    =>
    (retract ?h)
    (assert (hice south))
    (retract ?w)
    (assert (willy (- ?f 1) ?c))
    (retract ?nmov)
    (assert (myNumMoves (+ ?mov 1)))
    (assert (casilla (fil (- ?f 1)) (col ?c)))
    (retract ?p)
    (assert (percepts Noise))
)

(defrule AlienCerca "Añade a la casilla en la que se encuentra Willy el peligro Noise"
    (declare (salience 50))
    (willy ?f ?c)
    (percepts $? Noise $?)
    ?cas <- (casilla (fil ?f) (col ?c) (peligros $?pel))
    (not (casilla (fil ?f) (col ?c) (peligros $? Noise $?)))
    =>
    (modify ?cas (peligros $?pel Noise))
)

(defrule UbicacionAlien "Crea la ubicacion del alien"
    (declare (salience 10))
    (casilla (fil ?fil) (col ?col) (peligros $? Noise $?))
    (casilla (fil ?fil) (col =(+ ?col 2)) (peligros $? Noise $?))
    =>
    (assert (alien ?fil (+ ?col 1)))
)