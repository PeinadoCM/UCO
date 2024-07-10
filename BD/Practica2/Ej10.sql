select p.nombrecompleto, count(cd.partido)
from partidos p, consultas_datos cd
where p.idpartido=cd.partido and cd.respuesta='Si' and cd.certidumbre>0.8
group by p.nombrecompleto;

