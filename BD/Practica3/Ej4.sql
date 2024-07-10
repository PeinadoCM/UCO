select dni
from votantes
where fechanacimiento=(
    select min(fechanacimiento)
    from votantes
    where fechanacimiento!=(
        select min(fechanacimiento)
        from votantes
        )
    );
