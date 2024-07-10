select v.dni
from consultas c, votantes v
where v.dni=c.votante
having count(c.votante)>3
group by v.dni;