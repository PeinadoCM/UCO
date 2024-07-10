select l.nombre ||' va antes que '|| l2.nombre
from localidades l, localidades l2
where l.idlocalidad=(l2.idlocalidad-1);