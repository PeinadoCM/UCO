;Escribe las plantillas necesarias para representar la información de una plataforma de películas online.
;Para ello, se debe definir una plantilla que permita definir las películas disponibles en la plataforma y otra para los usuarios de la misma.
;
;La plantilla pelicula debe incluir los campos:
;
;   Id: deberá generar un símbolo por defecto dinámico.
;   Nombre-director: campo que debe incluir dos cadenas de texto (exactamente 2). La primera hará referencia al nombre de la película. La segunda, al director. Además, este campo debe de ser obligatorio.
;   Anio: Debe ser entero. Por defecto, debe valer 2010. Además, como es lógico, debemos garantizar que es un año entre el 0 y el 2024.
;   Opiniones: campo de tipo cadena. Incluye las opiniones de múltiples páginas webs para evaluar películas. Además, las opiniones deben de ser una de entre las siguientes: muy mala, mala, regular, buena, muy buena.
;
;Por otra parte, la plantilla usuario debe incluir los campos:
;
;   DNI: campo obligatorio de tipo símbolo. 
;   Nombre: campo de tipo cadena que por defecto debe tomar el valor cadena vacía (“”).
;   Historial: campo de tipo símbolo que incluye el historial con los identificadores de las últimas películas (máximo 6) que haya visto el usuario.
;   Puntuacion: campo de tipo entero que incluye la puntuación que el usuario le da a la plataforma. La nota puede tener valores entre 0 y 10 y por defecto tomará el valor 7.
;
;Además, define mediante el constructor de hechos:
;
;La película “Dune” del director “Denis Villeneuve”, estrenada en 2021. Las opiniones recogidas han sido (buena, muy buena, muy buena)
;La película “Kung Fu Panda 4” del director “Mike Mitchell”, estrenada en 2024. Aún no hay opiniones sobre esta película.
;La usuario “María Jiménez”, con DNI “34287654Y”. Es una nueva usuario por lo que solo ha visto las películas con identificador (gensym165 gensym19) y todavía no ha valorado la plataforma.
;
;El ejercicio completo debe completarse en un fichero .clp con el nombre <nombre de usuario>-desafio1.clp. Por ejemplo: i72alllj-desafio1.clp

(deftemplate pelicula
    (slot id (type SYMBOL) (default-dynamic (gensym)))
    (multislot nombre_director (type STRING) (cardinality 2 2) (default ?NONE))
    (slot anio (type INTEGER) (range 0 2024) (default 2010))
    (multislot opiniones (type STRING) (allowed-strings "muy mala" "mala" "regular" "buena" "muy buena"))
)

(deftemplate usuario
    (slot dni (type SYMBOL) (default ?NONE))
    (slot nombre (type STRING) (default ""))
    (multislot historial (type SYMBOL) (cardinality 0 6))
    (slot puntuacion (type INTEGER) (range 0 10) (default 7))
)

(deffacts Dune "Se va a crear la pelicula Dune"
    (pelicula
        (nombre_director "Dune" "Denis Villeneuve")
        (anio 2021)
        (opiniones "buena" "muy buena" "muy buena")
    )
)

(deffacts KungFuPanda4 "Se va a crear la pelicula Kung Fu Panda 4"
    (pelicula
        (nombre_director "Kung Fu Panda 4" "Mike Mitchell")
        (anio 2024)
    )
)

(deffacts usu1 "Se va a crear al usuario Maria Jimenez"
    (usuario
        (dni 34287654Y)
        (nombre "Maria Jimenez")
        (historial gensym165 gensym19)
    )
)