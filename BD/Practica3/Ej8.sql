select v.dni, count(c.votante) "Numero de consultas"
from votantes v, consultas c
where v.dni=c.votante and fechanacimiento!=(
    select min(fechanacimiento)
    from votantes
    where fechanacimiento!=(
        select min(fechanacimiento)
        from votantes
        )
    )
group by v.dni
order by count(c.votante)desc;
