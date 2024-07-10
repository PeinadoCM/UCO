set serveroutput on
declare
cursor c is select * from votantesantiguos;
cursor caux is select * from votantesantiguos;
cont number:=0;
maxedad votantesantiguos.fechanacimiento%type;
minedad votantesantiguos.fechanacimiento%type;
begin
    select min(fechanacimiento) into maxedad
    from votantesantiguos;
    
    select max(fechanacimiento) into minedad
    from votantesantiguos;

    loop
        for num_row in c loop
            if(num_row.fechanacimiento=maxedad)then
                dbms_output.put_line(num_row.nombrecompleto||' tiene mas letras que los siguientes mas jovenes:');
                cont:=0;
                for aux_row in caux loop
                    if (length(substr(num_row.nombrecompleto,1,instr(num_row.nombrecompleto,' ')-1))>length(substr(aux_row.nombrecompleto,1,instr(aux_row.nombrecompleto,' ')-1)) and (maxedad<aux_row.fechanacimiento)) then
                        dbms_output.put_line(aux_row.nombrecompleto);
                        cont:=cont+1;
                    end if;
                end loop;
                if (cont=0) then
                    dbms_output.put_line('Nadie cumple esta condicion');
                end if;
                dbms_output.put_line('');
            end if;
        end loop;
        exit when maxedad=minedad;
        select min(fechanacimiento) into maxedad
        from votantesantiguos
        where fechanacimiento>maxedad;
    end loop;
end;