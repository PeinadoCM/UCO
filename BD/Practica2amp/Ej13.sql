select p.nombrecompleto, '0'||avg(cd.certidumbre) "Certidumbre media"
from partidos p, consultas_datos cd
where p.idpartido=cd.partido and cd.respuesta='No' and cd.certidumbre>0.6
group by p.nombrecompleto;