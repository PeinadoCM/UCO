select v.nombrecompleto, decode(l.nombre,'Cordoba','Madrid','Montilla','Madrid','Baena','Madrid', l.nombre) "Localidad"
from votantes v, localidades l
where v.localidad=l.idlocalidad;