select v.nombrecompleto, l.nombre "Localidad"
from votantes v, localidades l
where v.localidad=l.idlocalidad and l.numerohabitantes>300000;
