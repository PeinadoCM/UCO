select l.nombre, p.nombre
from localidades l, provincias p
where l.provincia=p.idprovincia and substr(l.numerohabitantes,-1,1)=l.provincia;
