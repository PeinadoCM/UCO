(defmodule MAIN (export ?ALL))

(deftemplate MAIN::pelicula
    "Los datos de un libro de la CATALOGO"
    (slot id (type SYMBOL) (default ?NONE)) ; Identificador del libro
    (slot anio (type INTEGER)) ; Año de estreno
    (slot titulo (type STRING)) ; Titulo de la pelicula
    (multislot usuarios (type SYMBOL)) ; Registra los usuarios que tienen actualmente un ejemplar de la pelicula
    (slot cantidad (type INTEGER) (default 1) (range 0 9999)) ; Cantidad de ejemplares disponibles de la pelicula
)

(deftemplate MAIN::solicitud_alquiler
    "Registro de un préstamo de una pelicula a un usuario, desde la solicitud hasta finalizar el préstamo"
    (slot id-usuario (type SYMBOL)) ; Identificador del solicitante del préstamo
    (slot id-pelicula (type SYMBOL)) ; ID del pelicula que quiere tomar prestado
    (slot estado (type SYMBOL) (allowed-symbols Solicitado Aprobado Alquilado Finalizado) (default Solicitado)) ; Estado en el que se encuentra el préstamo.
)


(deffacts inicio
    ; Películas en el catálogo
    (pelicula (id p1) (titulo "Regreso al Futuro") (anio 1985) (cantidad 3) (usuarios u1 u3 u4 u5 u7 u10))
    (pelicula (id p2) (titulo "Interstellar") (anio 2014) (cantidad 5) (usuarios u4))
    (pelicula (id p3) (titulo "El Rey León") (anio 1994) (cantidad 0) (usuarios u3 u2 u8))
    (pelicula (id p4) (titulo "Joker") (anio 2019) (cantidad 2))
    (pelicula (id p5) (titulo "Dune 2") (anio 2024) (cantidad 8) (usuarios u7 u9 u2))

    ; Solicitudes de alquiler
    (solicitud_alquiler (id-usuario u1) (id-pelicula p1))
    (solicitud_alquiler (id-usuario u2) (id-pelicula p3))
    (solicitud_alquiler (id-usuario u3) (id-pelicula p2))
    (solicitud_alquiler (id-usuario u4) (id-pelicula p4))
    (solicitud_alquiler (id-usuario u5) (id-pelicula p4))
    (solicitud_alquiler (id-usuario u2) (id-pelicula p4))
)

(defrule MAIN::inicia
    =>
    (printout t "Iniciando el programa" crlf)
    (focus CATALOGO ALQUILER)
    ;COMPLETAR
)


;------- Módulo CATALOGO
;COMPLETAR --> Definir modulo
(defmodule CATALOGO
    (import MAIN deftemplate pelicula)
)

(defrule CATALOGO::mostrar-disponibles
   ;COMPLETAR
   (declare (salience 100))
   (pelicula (titulo ?tit) (cantidad ?cant&:(> ?cant 0)))
   =>
   (printout t "La pelicula " ?tit " está disponible." crlf)
)


(defrule CATALOGO::mostrar-clasicos
   ;COMPLETAR
   (pelicula (titulo ?tit) (anio ?year))
   (test (>= ?year 0))
   (test (<= ?year 1990))
   =>
   (printout t "La pelicula " ?tit " es un clásico." crlf)
)

(defrule CATALOGO::promocionar-peliculas
    ;COMPLETAR
    (or 
        (pelicula (titulo ?tit) (anio ?year&:(= ?year 2024)) (usuarios $?usus&:(>= (length$ $?usus) 3)) )
        (pelicula (titulo ?tit) (anio ?year&:(<> ?year 2024)) (usuarios $?usus&:(> (length$ $?usus) 5)) )
    )
    =>
    (printout t "Promoción especial:La película " ?tit " es altamente recomendada!" crlf)
)



;------- Módulo ALQUILER
;COMPLETAR --> Definir modulo
(defmodule ALQUILER
    (import MAIN deftemplate ?ALL)
)

(defrule ALQUILER::alquilar-pelicula
   ;COMPLETAR
   ?estado <- (solicitud_alquiler (id-usuario ?usu) (id-pelicula ?pel) (estado Solicitado))
   ?pelicula <- (pelicula (id ?pel) (titulo ?tit) (cantidad ?cant&:(> ?cant 0)))
   (not (solicitud_alquiler (id-usuario ?usu) (estado ?est&:(or 
                                                    (eq ?est Aprobado) 
                                                    (eq ?est Alquilado)
                                                ))
   ))
   =>
   (modify ?estado (estado Aprobado))
   (modify ?pelicula (cantidad (- ?cant 1)))
   (printout t "Aprobada solicutud de " ?usu " para la pelicula " ?tit crlf)
)

(defrule ALQUILER::confirmar-alquiler
   ;COMPLETAR
   (declare (salience -100))
    ?estado <- (solicitud_alquiler (id-usuario ?usu) (id-pelicula ?pel) (estado Aprobado))
    ?pelicula <- (pelicula (id ?pel) (titulo ?tit) (usuarios $?usus))
   =>
   (modify ?estado (estado Alquilado))
   (modify ?pelicula (usuarios $?usus ?usu))
   (printout t "Alquiler confirmado del usuario " ?usu " para la pelicula " ?tit crlf)
)
