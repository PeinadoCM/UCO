(defmodule MAIN (export ?ALL))

(deftemplate MAIN::libro
    "Los datos de un libro de la biblioteca"
    (slot id (type SYMBOL) (default ?NONE)) ; Identificador del libro
    (slot anio (type INTEGER)) ; Año de publicación
    (slot nombre (type STRING)) ; Nombre del libro
    (multislot usuarios (type SYMBOL)) ; Registra los usuarios que tienen actualmente un ejemplar del libro
    (slot cantidad (type INTEGER) (default 1) (range 0 9999)) ; Cantidad de ejemplares disponibles del libro
)

(deftemplate MAIN::prestamo
    "Registro de un préstamo de un libro a un usuario, desde la solicitud hasta finalizar el préstamo"
    (slot id-usuario (type SYMBOL)) ; Identificador del solicitante del préstamo
    (slot id-libro (type SYMBOL)) ; ID del libro que quiere tomar prestado
    (slot estado (type SYMBOL) (allowed-symbols Solicitado Aprobado Prestado Finalizado) (default Solicitado)) ; Estado en el que se encuentra el préstamo.
)


(deffacts MAIN::hechos_iniciales
	(libro (id l1) (anio 1605) (nombre "Don Quijote de la Mancha") (cantidad 2) (usuarios u2 u18))
	(libro (id l2) (anio 1943) (nombre "El principito") (cantidad 5) (usuarios u13 u19 u22 u66 u32))
	(libro (id l3) (anio 2001) (nombre "Bases de datos: Desde Chen hasta Codd") (cantidad 0) (usuarios u7 u6 u89 u20))
	(libro (id l4) (anio 1865) (nombre "Alicia en el país de las maravillas") (cantidad 3))
	
    (prestamo (id-usuario u72) (id-libro l1))
    (prestamo (id-usuario u23) (id-libro l3))
    (prestamo (id-usuario u19) (id-libro l4)) ; no se podrá alquilar l4 porque u19 tiene otro libro
    (prestamo (id-usuario u54) (id-libro l2))
)

(defrule MAIN::inicia
    =>
    (printout t "Iniciando el programa" crlf)
    (focus BIBLIOTECA PRESTAMO)
)


;------- Módulo BIBLIOTECA
;COMPLETAR --> definir modulo
(defmodule BIBLIOTECA
    (import MAIN deftemplate libro)
)

(defrule BIBLIOTECA::obtener-disponibles
    ;COMPLETAR
    (declare (salience 100))
    (libro (nombre ?nom) (cantidad ?num&:(> ?num 0)))
    =>
    (printout t "El libro " ?nom " se encuentra disponible" crlf)
)

(defrule BIBLIOTECA::obtener-siglo-XX
    ;COMPLETAR
    (libro (nombre ?nom) (anio ?year))
    (test (>= ?year 1900))
    (test (< ?year 2000))
    =>
    (printout t "El libro " ?nom " pertenece al siglo XX" crlf)
)

(defrule BIBLIOTECA::obtener-populares
    ;COMPLETAR
    (libro (nombre ?nom) (usuarios $?usus&:(>= (length$ $?usus) 4)))
    =>
    (printout t "El libro " ?nom " es popular" crlf)
)

;------- Módulo PRESTAMO
;COMPLETAR --> definir modulo

(defmodule PRESTAMO 
    ;COMPLETAR
    (import MAIN ?ALL)
)

(defrule PRESTAMO::prestar-libro
    ;COMPLETAR
    (declare (salience 10))
    ?solicitud <- (prestamo (id-usuario ?usu) (id-libro ?idl) (estado Solicitado))
    ?libro <- (libro (id ?idl) (nombre ?nom) (usuarios $?usus) (anio ?year) (cantidad ?cant&:(> ?cant 0)))
    (not (libro (usuarios $? ?usu $?)))
    =>
    (modify ?solicitud (estado Aprobado))
    (modify ?libro (cantidad (- ?cant 1)))
    (printout t "Aprobada solicitud de " ?usu " para el libro " ?nom crlf)
)

(defrule PRESTAMO::confirmar-prestamo
    ;COMPLETAR
    ?solicitud <- (prestamo (id-usuario ?usu) (id-libro ?idl) (estado Aprobado))
    ?libro <- (libro (id ?idl) (nombre ?nom) (usuarios $?usus) (anio ?year) (cantidad ?cant ))
    =>
    (modify ?libro (usuarios $?usus ?usu))
    (modify ?solicitud (estado Prestado))
    (printout t "Prestamo confirmado del usuario " ?usu " para el libro " ?nom crlf)
)