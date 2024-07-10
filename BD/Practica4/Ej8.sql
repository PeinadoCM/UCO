set serveroutput on
declare
menorp localidades.numerohabitantes%type;
menorp2 localidades.numerohabitantes%type;
minid localidades.idlocalidad%type;
cont number;
begin
select min(idlocalidad) into minid
from localidades;

select numerohabitantes into menorp
from localidades
where idlocalidad=minid;

select min(idlocalidad) into minid
from localidades
where idlocalidad!=minid;

select numerohabitantes into menorp2
from localidades
where idlocalidad=minid;

menorp:=menorp + menorp2;

select count(idlocalidad) into cont
from localidades
where numerohabitantes>menorp;

dbms_output.put_line('Hay '||cont||' ciudades con mas habitantes de '||menorp||' habitanets, que es la suma de las dos localidades con IDs mas pequeños');
end;