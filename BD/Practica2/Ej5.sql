select v.nombrecompleto, p.comunidad
from votantes v, localidades l, provincias p
where v.localidad=l.idlocalidad and l.provincia=p.idprovincia and l.numerohabitantes>300000;