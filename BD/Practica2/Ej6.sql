select p.idpartido "Partido", count(cd.partido) "Conteo"
from partidos p, consultas_datos cd
where p.idpartido=cd.partido
group by p.idpartido;

