set serveroutput on
declare
mynombre votantes.nombrecompleto%type;
mydni votantes.dni%type;
begin
select v.nombrecompleto, v.dni into mynombre, mydni
from votantes v
where v.fechanacimiento=(
    select min(fechanacimiento)
    from votantes
    );
    
dbms_output.put_line('El señor '||mynombre||' con dni '||mydni||' es el votante mas longevo');
end;