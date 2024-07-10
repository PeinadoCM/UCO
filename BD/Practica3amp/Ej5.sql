select l.nombre, l.numerohabitantes, p.comunidad
from localidades l, provincias p
where l.provincia<4 and p.idprovincia=l.provincia and l.numerohabitantes>(
    select min(numerohabitantes)
    from localidades
    where provincia=4
    );