;Se quiere modelar la información de una carrera de caballos, en las que participan 5 caballos en diferentes tipos de pista. Para ello, será necesario modelar las plantillas: caballo y carrera.
;
;La plantilla caballo debe incluir:
;
;jinete: campo de tipo cadena con el nombre del jinete. Por defecto toma el valor cadena vacía (“”).
;dorsal: campo entero que identifica el caballo. Puede tomar valores entre 0 y 99, y es un parámetro obligatorio.
;racha: campo de tipo símbolo que contiene los resultados de las últimas 5 carreras (como máximo). Solamente puede tomar los valores victoria o derrota.
;tiempo: campo de tipo real que contiene el tiempo medio en segundos que tarda un caballo en realizar la carrera. Por defecto toma el valor 0.0.
;Por otro lado, la plantilla carrera estará formada por los atributos:
;
;lugar: campo de tipo cadena que indica el lugar donde se realiza la carrera. Es un parámetro obligatorio.
;tipo: campo de tipo símbolo que indica el tipo de la pista donde tiene lugar. Puede tomar los valores: llana, campo y vallas. Por defecto toma el valor llana.
;meta: campo de enteros que contiene el orden de llegada a la meta, indicado por el dorsal. Las carreras son siempre de 5 caballos. Recordemos que los dorsales sólo pueden tomar valores entre 0 y 99.
;Además, define mediante el constructor de hechos:
;
;El caballo con el dorsal 92, cuya corredora es Marina y lleva una racha de 5 victorias. Su tiempo promedio es de 27.04.
;El caballo con el dorsal 66, cuyo corredor es Gonzalo. No ha participado aún en ninguna carrera por lo que no hay racha ni tiempos registrados.
;Una carrera en pista llana que tuvo lugar en París. En la meta se registraron las posiciones en el orden: 92, 19, 72, 66 y 31
;El ejercicio completo debe completarse en un fichero .clp con el nombre <nombre de usuario>-desafio1.clp. Por ejemplo: i72alllj-desafio1.clp

(deftemplate caballo
    (slot jinete (type STRING) (default ""))
    (slot dorsal (type INTEGER) (range 0 99) (default ?NONE))
    (multislot racha (type SYMBOL) (cardinality 0 5) (allowed-symbols victoria derrota))
    (slot tiempo (type FLOAT) (default 0.0))
)

(deftemplate carrera
    (slot lugar (type STRING) (default ?NONE))
    (slot tipo (type SYMBOL) (allowed-symbols llana campo vallas) (default llana))
    (multislot meta (type INTEGER) (cardinality 5 5) (range 0 99))
)

(deffacts caballo1
    (caballo
        (jinete "Marina")
        (dorsal 92)
        (racha victoria victoria victoria victoria victoria)
        (tiempo 27.04)
    )
)

(deffacts caballo2
    (caballo
    (jinete "Gonzalo")
    (dorsal 66)
    )
)

(deffacts carrera1
    (carrera
    (lugar "Paris")
    (tipo llana)
    (meta 92 19 72 66 31)
    )
)