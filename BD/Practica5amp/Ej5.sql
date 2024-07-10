set serveroutput on
declare
cursor c is select * from partidos;
prestotal number:=0;
begin
    for num_row in c loop
        prestotal:=prestotal+num_row.presupuesto;
    end loop;
    
    for num_row in c loop
        update partidos set presupuesto=prestotal/6 where idpartido=num_row.idpartido;
    end loop;
    
    insert into partidos
    values(6,'VOX','VOX','c/ Azul 43','Madrid','Santiago Abascal','01/12/04',prestotal/6);
end;