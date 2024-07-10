set serveroutput on
declare
cursor c is select * from votantes;
nombrel localidades.nombre%type;
cont number:=0;
begin
for num_row in c loop
    select nombre into nombrel
    from localidades
    where idlocalidad=num_row.localidad;
    
    if num_row.localidad<=3 or num_row.localidad=9 then
    dbms_output.put_line(num_row.nombrecompleto||' es de Madrid');
    cont:=cont+1;
    else 
    dbms_output.put_line(num_row.nombrecompleto||' es de '||nombrel);
    end if;
end loop;
dbms_output.put_line('Hay un total de '||cont||' votantes de Madrid');
end;