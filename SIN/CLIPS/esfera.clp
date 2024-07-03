(deftemplate esfera (slot nombre) (slot radio))
(deftemplate objeto (slot nombre) (slot menordimension))
(deftemplate circunferencia (slot nombre) (slot radio) (slot perimetro))

(deffacts hechos
	(esfera (nombre esferaProblema) (radio 1000000)) ; He puesto un valor grande (inventado)
	(objeto (nombre folio) (menordimension 0.0001)) ; He puesto 0,1 mm (inventado)
	(objeto (nombre mano) (menordimension 0.03)) ;He puesto 3 cm (inventado)
	(objeto (nombre pelotaBeisbol) (menordimension 0.055)) ; He puesto 5.5 cm (inventado)
	(objeto (nombre pelotaBaloncesto) (menordimension 0.3)) ; He puesto 30 cm (inventado)
)

(defrule crearEcuador
	(esfera (nombre esferaProblema) (radio ?x))
	=>
	(assert (circunferencia (nombre ecuadorEsfera) (radio ?x)))
)

(defrule creaCinta
	(circunferencia (nombre ecuadorEsfera) (perimetro ?x&~nil))
	=>
	(assert (circunferencia (nombre cinta) (perimetro (+ ?x 1))))
)

(defrule imprimeObjetosQuePasanPorOquedad
	(objeto (nombre ?x) (menordimension ?y))
	(oquedad ?z&:(> ?z ?y))
	=>
	(printout t "El objeto " ?x " pasa entre la cinta y la esfera" crlf)
)

(defrule calculaPerimetros
	?h <- (circunferencia (nombre ?nom) (radio ?rad&~nil) (perimetro nil))
	=>
	(modify ?h (perimetro (* 6.28 ?rad))) 
)

(defrule calculaRadio
	?h <- (circunferencia (nombre ?nom) (radio nil) (perimetro ?p&~nil))
	=>
    (modify ?h (radio (/ ?p 6.28))) 
)

(defrule calculaOquedadCircunferenciasConcentricas
	(circunferencia (nombre ?nom1) (radio ?x&~nil))
    (circunferencia (nombre ?nom2) (radio ?y&~nil&:(> ?y ?x)))
	=>
	(assert (oquedad (- ?y ?x)))
)