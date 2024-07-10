set serveroutput on
declare
mynombre votantes.nombrecompleto%type;
mycorreo votantes.email%type;
begin
select v.nombrecompleto, v.email into mynombre, mycorreo
from votantes v
where v.fechanacimiento=(
    select min(fechanacimiento)
    from votantes
    );
    
dbms_output.put_line(mynombre||' -- '||mycorreo);

select v.nombrecompleto, v.email into mynombre, mycorreo
from votantes v
where v.fechanacimiento=(
    select max(fechanacimiento)
    from votantes
    );
    
dbms_output.put_line(mynombre||' -- '||mycorreo);
end;