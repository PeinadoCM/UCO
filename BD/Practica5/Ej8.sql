set serveroutput on
declare
cursor c is select * from votantesantiguos;
cont number:=0;
total number:=0;
begin
    for num_row in c loop
        num_row.nombrecompleto:=substr(num_row.nombrecompleto,1,instr(num_row.nombrecompleto,' ')-1);
        cont:=length(num_row.nombrecompleto);
        dbms_output.put_line(num_row.nombrecompleto||' tiene '||cont||' letras');
        total:=total + cont;
    end loop;
    dbms_output.put_line('En total hay '||total||' letras');
end;