select l.nombre
from localidades l, votantes v
where l.idlocalidad=v.localidad
group by l.nombre
order by avg(decode(v.estudiossuperiores,'Ninguno',0,'Basicos',1,'Superiores',2,'Doctorado',3)) desc;