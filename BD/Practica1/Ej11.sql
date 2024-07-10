select votantes.nombrecompleto ||' vive en '|| localidades.nombre "Votante y localidad"
from votantes, localidades
where votantes.localidad=localidades.idlocalidad;
select eventos.nombre ||' cuyo resultado es: 0'||eventos_resultados.resultado "Evento y resultado"
from eventos, eventos_resultados
where eventos_resultados.evento=eventos.idevento;
