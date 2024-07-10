select p.nombrecompleto, count(cd.partido) "Conteo"
from partidos p, consultas_datos cd
where p.idpartido=cd.partido
having count(cd.partido)>10
group by p.nombrecompleto;

