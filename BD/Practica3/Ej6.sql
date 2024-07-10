select v.dni, count(c.votante) "Numero de consultas"
from votantes v, consultas c
where v.dni=c.votante
having count(c.votante)>(
    select avg(cnt)
    from(
        select count(c.votante) as cnt
        from votantes v, consultas c
        where v.dni=c.votante
        group by v.dni
        )
    )
group by v.dni
order by count(c.votante)desc;
