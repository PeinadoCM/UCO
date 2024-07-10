select substr(v.nombrecompleto,1,instr(v.nombrecompleto,' ')) "Nombre", l.nombre "Nombre localidad", p.nombre "Nombre provincia"
from votantes v, localidades l, provincias p
where v.localidad=l.idlocalidad and l.provincia=p.idprovincia;