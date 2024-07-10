set serveroutput on
declare
nombre votantes.nombrecompleto%type:='Juan';
apellido1 votantes.nombrecompleto%type;
apellido2 votantes.nombrecompleto%type;
fechamayor votantes.fechanacimiento%type;
begin
select max(fechanacimiento) into fechamayor
from votantes;

select substr(nombrecompleto,instr(nombrecompleto,' ')+1) into apellido2
from votantes
where fechanacimiento=fechamayor;

apellido2:=substr(apellido2,1,instr(apellido2,' ')-1);

select max(fechanacimiento) into fechamayor
from votantes
where fechanacimiento!=fechamayor;

select substr(nombrecompleto,instr(nombrecompleto,' ')+1) into apellido1
from votantes
where fechanacimiento=fechamayor;

apellido1:=substr(apellido1,1,instr(apellido1,' ')-1);

dbms_output.put_line('El hijo se llama '||nombre||' '||apellido1||' '||apellido2);
end;