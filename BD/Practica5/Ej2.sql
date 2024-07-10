set serveroutput on
declare
cursor c is select * from votantes;
votantesdata votantes%rowtype;
cont number:=0;
begin
open c;
loop
    fetch c into votantesdata; 
    if votantesdata.localidad+1=substr(votantesdata.dni,-1,1)then
        dbms_output.put_line(votantesdata.nombrecompleto);
        cont:=cont+1;
    end if;
exit when c%notfound;
end loop;
dbms_output.put_line('Hay un total de '||cont||' votantes');
end;