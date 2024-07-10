select distinct c.votante, '0'||avg(cd.certidumbre) "Certidumbre media"
from consultas c, consultas_datos cd
where c.idconsulta=cd.consulta and cd.respuesta='Si'
having avg(cd.certidumbre) between 0.5 and 0.8
group by c.votante;