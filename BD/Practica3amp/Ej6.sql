select v.nombrecompleto
from consultas c, votantes v
where c.votante=v.dni and situacionlaboral='Activo'
having count(c.votante)<(
    select avg(cnt)
    from(
        select count(c.votante) as cnt
        from consultas c, votantes v
        where c.votante=v.dni
        group by v.dni
        )
    )
group by v.nombrecompleto;