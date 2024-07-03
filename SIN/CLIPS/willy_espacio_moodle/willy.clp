(deftemplate casilla
    (slot fila)
    (slot columna)
    (multislot peligros)
)

(deffacts estadoInicial "Estado inicial del sistema"
    (hice nada) ; Hecho para la última acción que realizó Willy
    (nMovimientos 0) ; Número de pasos que ha dado Willy
    (willy 0 0)
    (casilla (fila 0) (columna 0))
)

(defrule moverWillySouth
    (directions $? south $?)
    ?h <- (hice $?)
    (not (percepts $? ? $?)) ; No se detecta ningún peligro
    ?nmov <- (nMovimientos ?mov&:(< ?mov 998)) ; No se ha alcanzado el número máximo de pasos
    ?w <- (willy ?f ?c)
    (not (casilla (fila =(- ?f 1)) (columna ?c)))
    =>
    (retract ?h)
    (assert (hice south)) ; Apuntar el movimiento que se hizo para que se puede volver a él si hay un peligro
    (retract ?nmov)
 	(assert (nMovimientos (+ ?mov 1))) ; Incrementar el número de pasos
    (assert (casilla (fila (- ?f 1)) (columna ?c)))
    (retract ?w)
    (assert (willy (- ?f 1) ?c))
    (moveWilly south)
)

(defrule moverWillySouthVisitado
    (declare (salience -10))
    (directions $? south $?)
    ?h <- (hice $?)
    (not (percepts $? ? $?)) ; No se detecta ningún peligro
    ?nmov <- (nMovimientos ?mov&:(< ?mov 998)) ; No se ha alcanzado el número máximo de pasos
    ?w <- (willy ?f ?c)
    (casilla (fila =(- ?f 1)) (columna ?c))
    =>
    (retract ?h)
    (assert (hice south)) ; Apuntar el movimiento que se hizo para que se puede volver a él si hay un peligro
    (retract ?nmov)
 	(assert (nMovimientos (+ ?mov 1))) ; Incrementar el número de pasos
    (retract ?w)
    (assert (willy (- ?f 1) ?c))
    (moveWilly south)
)

(defrule moverWillyNorth
    (directions $? north $?)
    ?h <- (hice $?)
    (not (percepts $? ? $?)) ; No se detecta ningún peligro
    ?nmov <- (nMovimientos ?mov&:(< ?mov 998)) ; No se ha alcanzado el número máximo de pasos
    ?w <- (willy ?f ?c)
    (not (casilla (fila =(+ ?f 1)) (columna ?c)))
    =>
    (retract ?h)
    (assert (hice north)) ; Apuntar el movimiento que se hizo para que se puede volver a él si hay un peligro
    (retract ?nmov)
 	(assert (nMovimientos (+ ?mov 1))) ; Incrementar el número de pasos
    (assert (casilla (fila (+ ?f 1)) (columna ?c)))
    (retract ?w)
    (assert (willy (+ ?f 1) ?c))
    (moveWilly north)
)

(defrule moverWillyNorthVisitado
    (declare (salience -10))
    (directions $? north $?)
    ?h <- (hice $?)
    (not (percepts $? ? $?)) ; No se detecta ningún peligro
    ?nmov <- (nMovimientos ?mov&:(< ?mov 998)) ; No se ha alcanzado el número máximo de pasos
    ?w <- (willy ?f ?c)
    (casilla (fila =(+ ?f 1)) (columna ?c))
    =>
    (retract ?h)
    (assert (hice north)) ; Apuntar el movimiento que se hizo para que se puede volver a él si hay un peligro
    (retract ?nmov)
 	(assert (nMovimientos (+ ?mov 1))) ; Incrementar el número de pasos
    (retract ?w)
    (assert (willy (+ ?f 1) ?c))
    (moveWilly north)
)

(defrule moverWillyEast
    (directions $? east $?)
    ?h <- (hice $?)
    (not (percepts $? ? $?)) ; No se detecta ningún peligro
    ?nmov <- (nMovimientos ?mov&:(< ?mov 998)) ; No se ha alcanzado el número máximo de pasos
    ?w <- (willy ?f ?c)
    (not (casilla (fila ?f) (columna =(+ ?c 1))))
    =>
    (retract ?h)
    (assert (hice east)) ; Apuntar el movimiento que se hizo para que se puede volver a él si hay un peligro
    (retract ?nmov)
 	(assert (nMovimientos (+ ?mov 1))) ; Incrementar el número de pasos
    (assert (casilla (fila ?f) (columna (+ ?c 1))))
    (retract ?w)
    (assert (willy ?f (+ ?c 1)))
    (moveWilly east)
)

(defrule moverWillyEastVisitado
    (declare (salience -10))
    (directions $? east $?)
    ?h <- (hice $?)
    (not (percepts $? ? $?)) ; No se detecta ningún peligro
    ?nmov <- (nMovimientos ?mov&:(< ?mov 998)) ; No se ha alcanzado el número máximo de pasos
    ?w <- (willy ?f ?c)
    (casilla (fila ?f) (columna =(+ ?c 1)))
    =>
    (retract ?h)
    (assert (hice east)) ; Apuntar el movimiento que se hizo para que se puede volver a él si hay un peligro
    (retract ?nmov)
 	(assert (nMovimientos (+ ?mov 1))) ; Incrementar el número de pasos
    (retract ?w)
    (assert (willy ?f (+ ?c 1)))
    (moveWilly east)
)

(defrule moverWillyWest
    (directions $? west $?)
    ?h <- (hice $?)
    (not (percepts $? ? $?)) ; No se detecta ningún peligro
    ?nmov <- (nMovimientos ?mov&:(< ?mov 998)) ; No se ha alcanzado el número máximo de pasos
    ?w <- (willy ?f ?c)
    (not (casilla (fila ?f) (columna =(- ?c 1))))
    =>
    (retract ?h)
    (assert (hice west)) ; Apuntar el movimiento que se hizo para que se puede volver a él si hay un peligro
    (retract ?nmov)
 	(assert (nMovimientos (+ ?mov 1))) ; Incrementar el número de pasos
    (assert (casilla (fila ?f) (columna (- ?c 1))))
    (retract ?w)
    (assert (willy ?f (- ?c 1)))
    (moveWilly west)
)

(defrule moverWillyWestVisitado
    (declare (salience -10))
    (directions $? west $?)
    ?h <- (hice $?)
    (not (percepts $? ? $?)) ; No se detecta ningún peligro
    ?nmov <- (nMovimientos ?mov&:(< ?mov 998)) ; No se ha alcanzado el número máximo de pasos
    ?w <- (willy ?f ?c)
    (casilla (fila ?f) (columna =(- ?c 1)))
    =>
    (retract ?h)
    (assert (hice west)) ; Apuntar el movimiento que se hizo para que se puede volver a él si hay un peligro
    (retract ?nmov)
 	(assert (nMovimientos (+ ?mov 1))) ; Incrementar el número de pasos
    (retract ?w)
    (assert (willy ?f (- ?c 1)))
    (moveWilly west)
)

(defrule moverYRezarWilly "Mover a willy en una dirección aleatoria, en caso de detectar peligro y no tener apuntado el último movimiento"
    (directions $? ?direction $?)
    ?h <- (hice nada)
    (percepts $? ? $?) ; Se detecta algún peligro
    ?nmov <- (nMovimientos ?mov&:(< ?mov 998)) ; No se ha alcanzado el número máximo de pasos
        =>
    (retract ?h)
    (assert (hice ?direction)) ; Apuntar el movimiento que se hizo para que se puede volver a él si hay un peligro
    (retract ?nmov)
	(assert (nMovimientos (+ ?mov 1))) ; Incrementar el número de pasos
    (moveWilly ?direction)
)

(defrule volverNorthWilly "Retroceder cuando el movimiento realizado previamente fue al norte"
    (directions $? south $?)
    ?h <- (hice north) ; Condición para que esta regla se ejecute sólo para volver de un movimiento hacia arriba
    (percepts $? ? $?) ; Se detecta algún peligro
    ?nmov <- (nMovimientos ?mov&:(< ?mov 998)) ; No se ha alcanzado el número máximo de pasos
    ?w <- (willy ?f ?c)
    =>
    (retract ?h) 
    (assert (hice south)); No hace falta apuntar lo que se hizo
    (retract ?nmov)
	(assert (nMovimientos (+ ?mov 1))) ; Incrementar el número de pasos
    (moveWilly south); Mover a willy en la dirección contraria
    (retract ?w)
    (assert (willy (- ?f 1) ?c))
)

(defrule volverSouthWilly "Retroceder cuando el movimiento realizado previamente fue al sur"
    (directions $? north $?)
    ?h <- (hice south) ; Condición para que esta regla se ejecute sólo para volver de un movimiento hacia abajo
    (percepts $? ? $?) ; Se detecta algún peligro
    ?nmov <- (nMovimientos ?mov&:(< ?mov 998)) ; No se ha alcanzado el número máximo de pasos
    ?w <- (willy ?f ?c)
    =>
    (retract ?h)
    (assert (hice north)); No hace falta apuntar lo que se hizo
    (moveWilly north) ; Mover a willy en la dirección contraria
    (retract ?nmov)
	(assert (nMovimientos (+ ?mov 1))) ; Incrementar el número de pasos
    (retract ?w)
    (assert (willy (+ ?f 1) ?c))
)

(defrule volverWestWilly "Retroceder cuando el movimiento realizado previamente fue al oeste"
    (directions $? east $?) 
    ?h<-(hice west) ; Condición para que esta regla se ejecute sólo para volver de un movimiento hacia la izquierda
    (percepts $? ? $?) ; Se detecta algún peligro
    ?nmov <- (nMovimientos ?mov&:(< ?mov 998)) ; No se ha alcanzado el número máximo de pasos
    ?w <- (willy ?f ?c)
    =>
    (retract ?h)
    (assert (hice east)); No hace falta apuntar lo que se hizo
    (moveWilly east) ; Mover a willy en la dirección contraria
    (retract ?nmov)
	(assert (nMovimientos (+ ?mov 1))) ; Incrementar el número de pasos
    (retract ?w)
    (assert (willy ?f (+ ?c 1)))
)

(defrule volverEastWilly "Retroceder cuando el movimiento realizado previamente fue al este"
    (directions $? west $?)
    ?h <- (hice east) ; Condición para que esta regla se ejecute sólo para volver de un movimiento hacia la derecha
    (percepts $? ? $?) ; Se detecta algún peligro
    ?nmov <- (nMovimientos ?mov&:(< ?mov 998)) ; No se ha alcanzado el número máximo de pasos
    ?w <- (willy ?f ?c)
    =>
    (retract ?h)
    (assert (hice west)); No hace falta apuntar lo que se hizo
    (moveWilly west) ; Mover a willy en la dirección contraria
    (retract ?nmov)
	(assert (nMovimientos (+ ?mov 1))) ; Incrementar el número de pasos
    (retract ?w)
    (assert (willy ?f (- ?c 1)))
)

(defrule AgujeroNegroCerca
    (declare (salience 10))
    (percepts $? Pull $?)
    (willy ?f ?c)
    ?cas <- (casilla (fila ?f) (columna ?c) (peligros $?pel))
    (not (casilla (fila ?f) (columna ?c) (peligros $? Pull $?)))
    =>
    (modify ?cas (peligros $?pel Pull))
)

(defrule AlienCerca
    (declare (salience 10))
    (percepts $? Noise $?)
    (willy ?f ?c)
    ?cas <- (casilla (fila ?f) (columna ?c) (peligros $?pel))
    (not (casilla (fila ?f) (columna ?c) (peligros $? Noise $?)))
    =>
    (modify ?cas (peligros $?pel Noise))
)

(defrule fireWillyEast
    (declare (salience 100))
    (hasLaser)
    (percepts $? Noise $?)
    (willy ?f ?c)
    (or
        (casilla (fila =(+ ?f 1)) (columna =(+ ?c 1)) (peligros $? Noise $?))    
        (casilla (fila ?f) (columna =(+ ?c 2)) (peligros $? Noise $?))
        (casilla (fila =(- ?f 1)) (columna =(+ ?c 1)) (peligros $? Noise $?)) 
    )
    (not (casilla (fila ?f) (columna =(+ ?c 1))))
    =>
    (fireLaser east)
)

(defrule fireWillyWest
    (declare (salience 100))
    (hasLaser)
    (percepts $? Noise $?)
    (willy ?f ?c)
    (or
        (casilla (fila =(+ ?f 1)) (columna =(- ?c 1)) (peligros $? Noise $?))    
        (casilla (fila ?f) (columna =(- ?c 2)) (peligros $? Noise $?))
        (casilla (fila =(- ?f 1)) (columna =(- ?c 1)) (peligros $? Noise $?)) 
    )
    (not (casilla (fila ?f) (columna =(- ?c 1))))
    =>
    (fireLaser west)
)

(defrule fireWillyNorth
    (declare (salience 100))
    (hasLaser)
    (percepts $? Noise $?)
    (willy ?f ?c)
    (or
        (casilla (fila =(+ ?f 1)) (columna =(+ ?c 1)) (peligros $? Noise $?))    
        (casilla (fila =(+ ?f 2)) (columna ?c) (peligros $? Noise $?))
        (casilla (fila =(+ ?f 1)) (columna =(- ?c 1)) (peligros $? Noise $?)) 
    )
    (not (casilla (fila =(+ ?f 1)) (columna ?c)))
    (not (casilla (fila ?f) (columna =(- ?c 2)) (peligros $? Noise $?)))
    (not (casilla (fila ?f) (columna =(+ ?c 2)) (peligros $? Noise $?)))
    (not (casilla (fila =(- ?f 1)) (columna =(+ ?c 1)) (peligros $? Noise $?)))
    (not (casilla (fila =(- ?f 1)) (columna =(- ?c 1)) (peligros $? Noise $?)))
    =>
    (fireLaser north)
)

(defrule fireWillySouth
    (declare (salience 100))
    (hasLaser)
    (percepts $? Noise $?)
    (willy ?f ?c)
    (or
        (casilla (fila =(- ?f 1)) (columna =(+ ?c 1)) (peligros $? Noise $?))    
        (casilla (fila =(- ?f 2)) (columna ?c) (peligros $? Noise $?))
        (casilla (fila =(- ?f 1)) (columna =(- ?c 1)) (peligros $? Noise $?)) 
    )
    (not (casilla (fila =(- ?f 1)) (columna ?c)))
    (not (casilla (fila ?f) (columna =(- ?c 2)) (peligros $? Noise $?)))
    (not (casilla (fila ?f) (columna =(+ ?c 2)) (peligros $? Noise $?)))
    (not (casilla (fila =(+ ?f 1)) (columna =(+ ?c 1)) (peligros $? Noise $?)))
    (not (casilla (fila =(+ ?f 1)) (columna =(- ?c 1)) (peligros $? Noise $?)))
    =>
    (fireLaser south)
)

; (defrule fireWilly "En caso de percibir algún sonido, dispara en una dirección aleatoria"
;     (hasLaser)
;     (percepts $? Noise $?)
;     (directions $? ?direction $?)
;     =>
;     (fireLaser ?direction)
; )