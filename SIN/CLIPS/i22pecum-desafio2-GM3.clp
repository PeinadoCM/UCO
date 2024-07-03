; Partiendo de la siguiente definición de plantillas y hechos, defina un sistema para el correcto funcionamiento 
; de la gestión académica de un centro educativo utilizando reglas y módulos en CLIPS.
; En el programa de CLIPS se contemplan dos módulos: ALUMNADO y EVALUACION:
; Módulo ALUMNADO: maneja la información relacionada con los estudiantes, permitiendo consultar y actualizar 
; datos según diferentes criterios.
; Módulo EVALUACION: gestiona las calificaciones de los estudiantes y evalúa su rendimiento académico.
; Como puede verse en el fichero esqueleto adjunto, el sistema utiliza dos plantillas para hechos no ordenados 
; definidos en el módulo MAIN. La primera tarea será añadir las definiciones de los módulos previamente 
; mencionados y hacer que importen la plantilla “estudiante” en el caso del módulo ALUMNADO y todas las 
; plantillas definidas en MAIN en el caso de EVALUACION. Además, añada los módulos ALUMNADO y EVALUACION 
; (en este orden) al focus stack.
;
; En el módulo ALUMNADO se deberán definir 3 reglas:
; mostrar-cursos-superiores: se activa para todos los estudiantes que estén en un curso igual o superior a 4,
; mostrando por pantalla el mensaje "Estudiante: <nombre-estudiante>, Curso: <curso-estudiante>".
; Esta regla debe activarse la primera del módulo.
; estudiantes-mate-fisica: se activa para todos los estudiantes que estén matriculados tanto en Física como
; en Matemáticas (independientemente del orden en el que aparezcan estas asignaturas). Debe imprimir por pantalla 
; el mensaje "El estudiante con DNI <dni> está matriculado en Física y Matemáticas"
; al-menos-tres-suspensos: se activa si al menos tres estudiantes tienen una nota media suspensa, mostrando 
; por pantalla el mensaje "Al menos 3 estudiantes tienen la media suspensa". 
; El mensaje solamente debe mostrarse una vez. Esta regla debe activarse la última del módulo.
;
; En el módulo EVALUACION se definirán dos reglas:
; evaluar-aprobados: toma las notas en estado Indefinida y las modifica a Aprobada si la calificación es 5 o superior,
; mostrando por pantalla el mensaje "El estudiante <dni-estudiante> ha aprobado <asignatura>".
; evaluar-suspensos: toma las notas en estado Indefinida y las modifica a Suspensa si la calificación es inferior a 5,
; mostrando por pantalla el mensaje "El estudiante <dni-estudiante> ha suspendido <asignatura>".
; pasar-curso: encargada de registrar los usuarios que no tienen ninguna asignatura suspensa y pasan de curso. 
; Para ello, genera un nuevo hecho (pasa <dni> <curso+1>) y elimina el hecho actual del estudiante con el curso antiguo. 
; Recordad que para pasar de curso este debe ser menor de 6 (curso máximo) Tiene menor prioridad que la regla anterior, 
; de forma que se activará una vez todas las calificaciones pendientes hayan sido procesadas.

(defmodule MAIN (export ?ALL))

(deftemplate estudiante
    (slot dni (type SYMBOL))  
    (slot nombre (type STRING))
    (slot curso (type INTEGER) (range 1 6))
    (slot nota-media (type FLOAT) (range 0.0 10.0) (default 0.0))
    (multislot asignaturas (type SYMBOL) (allowed-symbols Matematicas Lengua Fisica Informatica))
) 

(deftemplate nota
    (slot dni-estudiante (type SYMBOL)) 
    (slot asignatura (type SYMBOL) (allowed-symbols Matematicas Lengua Fisica Informatica))
    (slot calificacion (type FLOAT) (range 0.0 10.0)) 
    (slot estado (type SYMBOL) (allowed-values Aprobada Suspensa Indefinida) (default Indefinida))
)

(defrule MAIN::inicia
    =>
    (printout t "Iniciando el programa" crlf)
    ;TODO
    (focus ALUMNADO EVALUACION)
)

(deffacts hechos_iniciales

; Estudiantes con diferentes cursos y asignaturas
    (estudiante (dni DNI001) (nombre "Juan Perez") (curso 5) (nota-media 3.5) (asignaturas Matematicas Fisica Informatica))
    (estudiante (dni DNI002) (nombre "Ana Gomez") (curso 4) (nota-media 3.0) (asignaturas Lengua Matematicas Fisica))
    (estudiante (dni DNI003) (nombre "Carlos Jimenez") (curso 2) (nota-media 7.0) (asignaturas Informatica Lengua))
    (estudiante (dni DNI004) (nombre "Luisa Fernandez") (curso 3) (nota-media 3.2) (asignaturas Fisica Matematicas))
    (estudiante (dni DNI005) (nombre "Sofia Morales") (curso 6) (nota-media 8.5) (asignaturas Lengua Fisica Informatica))
    (estudiante (dni DNI006) (nombre "Marta Morales") (curso 2) (nota-media 4.0) (asignaturas Informatica))

    ; Notas para los estudiantes
    (nota (dni-estudiante DNI001) (asignatura Matematicas) (calificacion 3.5))
    (nota (dni-estudiante DNI001) (asignatura Fisica) (calificacion 0.0))
    (nota (dni-estudiante DNI001) (asignatura Informatica) (calificacion 7.0))
    (nota (dni-estudiante DNI003) (asignatura Lengua) (calificacion 6.0))
    (nota (dni-estudiante DNI003) (asignatura Fisica) (calificacion 7.0))
    (nota (dni-estudiante DNI003) (asignatura Informatica) (calificacion 8.0))
    (nota (dni-estudiante DNI005) (asignatura Matematicas) (calificacion 10.0))
    (nota (dni-estudiante DNI005) (asignatura Lengua) (calificacion 8.5))
    (nota (dni-estudiante DNI005) (asignatura Fisica) (calificacion 7.0))

)

;MODULO ALUMNADO
;COMPLETAR --> definir módulo
(defmodule ALUMNADO
    (import MAIN deftemplate estudiante)
)

(defrule ALUMNADO::mostrar-cursos-superiores
    ;COMPLETAR
    (declare (salience 50))
    (estudiante (nombre ?nom) (curso ?cur&:(>= ?cur 4)))
    =>
    (printout t "Estudiante: " ?nom " , Curso: " ?cur crlf)
)

(defrule ALUMNADO::estudiantes-mate-fisica
    ;COMPLETAR
    (or
        (estudiante (dni ?d) (asignaturas $? Fisica $? Matematicas $?))
        (estudiante (dni ?d) (asignaturas $? Matematicas $? Fisica $?))
    )
    =>
    (printout t "El estudiante con DNI " ?d " esta matriculado en Fisica y Matematicas" crlf)
)


(defrule ALUMNADO::al-menos-tres-suspensos
    ;COMPLETAR
    (declare (salience -50))
    (forall
        (estudiante (dni ?d) (nota-media ?nota&:(< ?nota 5)))
        (estudiante (dni ?d2&:(neq ?d ?d2)) (nota-media ?nota2&:(< ?nota2 5)))
        (estudiante (dni ?d3&:(neq ?d2 ?d3)) (nota-media ?nota3&:(< ?nota3 5)))
    )
    =>
    (printout t "Al menos 3 estudiantes tienen la media suspensa" crlf)
)


;MODULO EVALUACION
;COMPLETAR --> definir módulo
(defmodule EVALUACION
    (import MAIN deftemplate ?ALL)
)

(defrule EVALUACION::evaluar-aprobados
    ;COMPLETAR
    ?nota <- (nota (dni-estudiante ?d) (asignatura ?asig) (estado Indefinida) (calificacion ?cal&:(>= ?cal 5)))
    =>
    (modify ?nota (estado Aprobada))
    (printout t "El estudiante " ?d " ha aprobado " ?asig crlf)
)

(defrule EVALUACION::evaluar-suspensos
    ;COMPLETAR
    ?nota <- (nota (dni-estudiante ?d) (asignatura ?asig) (estado Indefinida) (calificacion ?cal&:(< ?cal 5)))
    =>
    (modify ?nota (estado Suspensa))
    (printout t "El estudiante " ?d " ha suspendido " ?asig crlf)
)

(defrule EVALUACION::pasar-curso
    ;COMPLETAR
    (declare (salience -50))
    ?est <- (estudiante (dni ?d) (curso ?cur&:(< ?cur 6)))
    (not (nota (dni-estudiante ?d) (estado Suspensa)))
    =>
    (retract ?est)
    (assert (pasa ?d (+ ?cur 1)))
)
