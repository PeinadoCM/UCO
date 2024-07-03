(deftemplate pacientes
    (slot nombre (type STRING))
    (slot apellidos (type STRING))
    (slot dni (type SYMBOL))
    (slot seguro-medico)
)

(deftemplate visitas
    (slot fecha)
    (slot sintomas (type STRING))
    (slot pruebas (type STRING))
    (slot medicacion)
)