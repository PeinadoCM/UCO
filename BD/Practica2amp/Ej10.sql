select p.nombrecompleto, '0'||max(cd.certidumbre) "Certidumbre maxima"
from partidos p, consultas_datos cd
where p.idpartido=cd.partido
group by p.nombrecompleto;