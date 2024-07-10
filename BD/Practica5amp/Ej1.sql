set serveroutput on
/*
drop table votantesAntiguos;
create table votantesAntiguos
	(dni number(8) primary key,
	nombreCompleto varchar2(64),
	email varchar2(32) not null,
	fechaNacimiento date not null,
	sueldo number);
*/

declare
cursor c is select * from votantes;
cont number:=0;
begin
    for num_row in c loop
        if(num_row.fechanacimiento<'01/01/80') then
            if(num_row.situacionlaboral='Activo') then
                insert into votantesAntiguos
                values (num_row.dni,num_row.nombrecompleto,num_row.email,num_row.fechanacimiento,1500);
            else
                insert into votantesAntiguos
                values (num_row.dni,num_row.nombrecompleto,num_row.email,num_row.fechanacimiento,0);
            end if;
            dbms_output.put_line('Se ha insertado a '||num_row.nombrecompleto);
            cont:=cont+1;
        end if;
    end loop;
    dbms_output.put_line('Se han insertado un total de '||cont||' personas');
end;