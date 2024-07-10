/*Enunciado: Se desea obtener informacion sobre los estudios que tienen los votantes segun localidades
para ello se solicita mostrar para cada localidad dada por su nombre el % de personas que cada localidad tiene 
segun el tipo de estudios, el nombre de las columnas que se desean mostrar son nombre (nombre localidad) y los distintos
tipos de Estudios Superiores Hay que comprobar que el porcentaje de cada localidad sume 100%*/

select l.nombre,
100*(select count(dni) from votantes where estudiossuperiores='Doctorado' and localidad=v.localidad)/count(dni) as Doctorado,
100*(select count(dni) from votantes where estudiossuperiores='Superiores' and localidad=v.localidad)/count(dni) as Superiores,
100*(select count(dni) from votantes where estudiossuperiores='Basicos' and localidad=v.localidad)/count(dni) as Basicos,
100*(select count(dni) from votantes where estudiossuperiores='Ninguno' and localidad=v.localidad)/count(dni) as Ninguno
from localidades l, votantes v
where v.localidad=l.idlocalidad
group by l.nombre, v.localidad
order by l.nombre asc;