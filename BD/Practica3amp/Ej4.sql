select dni, decode(b,null,concat('','menor de 25'),b) "Edad"
from (select dni, (
        select concat('','mayor de 25')
        from votantes
        where (sysdate-fechanacimiento)/365 > 25 and dni=v.dni) as b
    from votantes v);