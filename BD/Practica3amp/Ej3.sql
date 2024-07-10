select l.nombre
from localidades l
where l.numerohabitantes>(
    select l.numerohabitantes
    from localidades l
    where l.idlocalidad=(
        select localidad
        from votantes
        where fechanacimiento=(
            select min(fechanacimiento)
            from votantes
            where fechanacimiento!=(
                select min(fechanacimiento)
                from votantes
                )
            )
        )
    );