select v.nombrecompleto, count(c.votante) "Numero de consultas"
from consultas c, votantes v
where v.dni=c.votante
having count(c.votante)>3
group by v.nombrecompleto
order by count(c.votante) asc;