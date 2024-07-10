set serveroutput on
declare
cursor c is select * from votantesantiguos;
cursor caux is select * from votantes;
cont number:=0;
begin
for num_row in c loop
    for aux_row in caux loop
        if (num_row.dni=aux_row.dni) then
            if (aux_row.situacionlaboral='Jubilado') then
                update votantesantiguos set sueldo=sueldo*1.05 where dni=num_row.dni;
            else
                update votantesantiguos set sueldo=sueldo*1.02 where dni=num_row.dni;
            end if;
        end if;
    end loop;
end loop;

end;