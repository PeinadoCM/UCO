set serveroutput on
declare
cursor c is select * from votantes;
mindni votantes.dni%type;
maxdni votantes.dni%type;
dniaux votantes.dni%type;
begin
select min(dni) into mindni
from votantes;

select max(dni) into maxdni 
from votantes;
    
for num_row in c loop
    if maxdni!=mindni then
        select max(dni) into dniaux
        from votantes
        where dni<maxdni;
        
        dbms_output.put_line(maxdni||' va antes que '||dniaux);
        
        maxdni:=dniaux;
    else
        dbms_output.put_line(mindni||' es el menor');
    end if;
end loop;
end;