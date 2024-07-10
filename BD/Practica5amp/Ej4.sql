set serveroutput on
declare
    cursor c is select * from votantesantiguos;
    cont number:=0;
    mincont number;
begin
    for num_row in c loop
        if cont=0 then
            select count(votante) into mincont
            from consultas
            where votante=num_row.dni;
        else
            select count(votante) into cont
            from consultas
            where votante=num_row.dni;

            if mincont>cont then
                mincont:=cont;
            end if;
        end if;
    end loop;

    for num_row in c loop
        select count(votante) into cont
        from consultas
        where votante=num_row.dni;
        
        if cont=mincont then
            delete from votantesantiguos where dni=num_row.dni;
        end if;
    end loop;

end;