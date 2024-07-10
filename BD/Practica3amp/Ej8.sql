select l.nombre
from localidades l, votantes v
where l.idlocalidad=v.localidad
having avg(decode(v.estudiossuperiores,'Ninguno',0,'Basicos',1,'Superiores',2,'Doctorado',3))> (
    select max(cnt)
    from(
        select avg(decode(v.estudiossuperiores,'Ninguno',0,'Basicos',1,'Superiores',2,'Doctorado',3)) as cnt
        from localidades l, votantes v, provincias p
        where l.provincia=p.idprovincia and l.idlocalidad=v.localidad
        group by l.provincia
        )       
    )
group by l.nombre;