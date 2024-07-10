select nombrecompleto
from votantes 
where (substr(dni,-1,1)-1)=localidad;