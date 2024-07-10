select distinct c.votante, '0'||avg(cd.certidumbre) "Certidumbre media"
from consultas c, consultas_datos cd
where c.idconsulta=cd.consulta and cd.respuesta='Si'
group by c.votante;