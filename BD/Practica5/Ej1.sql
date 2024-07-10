set serveroutput on
declare
cursor c is select * from votantes;
cont number:=0;
begin
for num_row in c loop
    if num_row.localidad+1=substr(num_row.dni,-1,1)then
        dbms_output.put_line(num_row.nombrecompleto);
        cont:=cont+1;
    end if;
end loop;
dbms_output.put_line('Hay un total de '||cont||' votantes');
end;