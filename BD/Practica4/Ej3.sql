set serveroutput on
declare
nombre votantes.nombrecompleto%type;
correo votantes.email%type;
mydni votantes.dni%type:=30983712;
begin
select substr(v.nombrecompleto,1,instr(v.nombrecompleto,' ')-1), v.email into nombre, correo
from votantes v
where v.dni=mydni;

dbms_output.put_line(nombre||' tiene como correo: '||correo);
end;