set serveroutput on
declare
cursor c is select * from votantes;
cont number;
maxcont number:=0;
media number:=0;
aux number:=0;
begin
    for num_row in c loop
        select count(votante) into cont
        from consultas
        where votante=num_row.dni;
        media:=media+cont;   
        aux:=aux+1;
        if cont>maxcont then
            maxcont:=cont;
        end if;
    end loop;
    
    media:=media/aux;
    
    while maxcont>media loop
        for num_row in c loop
            select count(votante) into cont
            from consultas
            where votante=num_row.dni;
            if cont=maxcont then
                dbms_output.put_line(num_row.dni||' ha participado '||cont||' veces');
            end if;
        end loop;
        maxcont:=maxcont-1;
    end loop;
end;