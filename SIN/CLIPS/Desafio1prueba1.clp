;En un escenario deportivo, se desea gestionar información sobre jugadores y equipos de baloncesto 3x3. Define las plantillas necesarias para representar la siguiente información:
;
;    Nombre del jugador: De tipo STRING, obligatorio.
;    Dorsal del jugador: De tipo INTEGER, con valores en el rango de 0 a 15, por defecto el 0.
;    Posición principal del jugador: De tipo SYMBOL, permitiendo valores como base, escolta, alero, alapivot y pivot.
;    Posiciones alternativas del jugador: De tipo SYMBOL, permitiendo múltiples valores de posición alternativa y siendo los posibles, los valores principales.
;
;Además, para los equipos:
;
;    Jugadores titulares del equipo: De tipo STRING, con un total de 3 jugadores, obligatorio los 3.
;    Jugadores suplentes del equipo: De tipo STRING, con un máximo de 2 jugadores sin ser necesario los 2.
;    Color del equipo: De tipo STRING, permitiendo valores como rojo, azul, amarillo, blanco y negro, por defecto blanco.
;    Puntuación del equipo: De tipo INTEGER, registrando la puntuación obtenida, por defecto 0.
;
;Define las plantillas y restricciones correspondientes para este contexto deportivo.
;
;Después crea 2 jugadores y un equipo.

(deftemplate jugador
    (slot nombre (type STRING) (default ?NONE))
    (slot dorsal (type INTEGER) (range 0 15) (default 0))
    (slot pos_principal (type SYMBOL) (allowed-symbols base escolta alero alapivot pivot))
    (multislot pos_alternativa (type SYMBOL) (allowed-symbols base escolta alero alapivot pivot))
)

(deftemplate equipo
    (multislot jug_titulares (type STRING) (cardinality 3 3) (default ?NONE))
    (multislot jug_suplentes (type STRING) (cardinality 0 2))
    (slot color (type STRING) (allowed-strings "rojo" "azul" "amarillo" "blanco" "negro") (default "blanco"))
    (slot puntuación (type INTEGER) (default 0))
)

(deffacts Equipo "Un equipo con dos jugadores"
    (equipo
        (jug_titulares "Manuel" "Guillermo" "Pepe")
        (jug_suplentes "Matas")    
    )

    (jugador
        (nombre "Manuel")
        (dorsal 2)
        (pos_principal base)
        (pos_alternativa alero pivot)
    )

    (jugador
        (nombre "Pepe")
        (pos_principal pivot)
    )
)