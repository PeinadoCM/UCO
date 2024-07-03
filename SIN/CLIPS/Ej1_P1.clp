(deftemplate persona
    (slot nombre (type STRING))
    (slot apellidos (type STRING))
    (slot color-ojos (type SYMBOL) (default marron))
    (slot altura (type FLOAT) (default 1.70))
    (slot edad (type INTEGER) (default 18))
)

(deffacts Personas "Creacion de personas"
    (persona 
        (nombre "Manuel")
        (apellidos "Peinado")
    )

    (persona
        (nombre "Pepe")
        (apellidos "Trujillo")
        (color-ojos azul)
        (edad 17)
    )

    (persona
        (nombre "Matias")
        (apellidos "Matas Munoz")
        (edad 19)
        (altura 1.91)
    )
)