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
                update votantesantiguos set sueldo=1000 where dni=num_row.dni;
            elsif (aux_row.situacionlaboral='Activo') then
                if aux_row.estudiossuperiores='Ninguno' then
                    update votantesantiguos set sueldo=sueldo+100 where dni=num_row.dni;
                    
                elsif aux_row.estudiossuperiores='Basicos' then
                    update votantesantiguos set sueldo=sueldo+200 where dni=num_row.dni;

                elsif aux_row.estudiossuperiores='Superiores' then
                    update votantesantiguos set sueldo=sueldo+300 where dni=num_row.dni;

                elsif aux_row.estudiossuperiores='Doctorado' then
                    update votantesantiguos set sueldo=sueldo+1000 where dni=num_row.dni;
                end if;
            end if;
        end if;
    end loop;
end loop;

end;