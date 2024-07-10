set serveroutput on
declare
nombre votantes.nombrecompleto%type;
mydni votantes.dni%type:=30983712;
begin
select decode(v.nombrecompleto,'Jose Perez Perez','Pepe Perez Perez') into nombre
from votantes v
where v.dni=mydni;

dbms_output.put_line(nombre);
end;