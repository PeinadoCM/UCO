select p.siglas
from partidos p, eventos_resultados er
where p.idpartido=er.partido
having count(er.partido)=(
    select max(cnt)
    from(
        select count(er.partido) as cnt
        from eventos_resultados er, partidos p
        where p.idpartido=er.partido
        group by p.siglas
        )
    )
group by p.siglas;
