select nombrecompleto
from votantes
where substr(telefono,-1,1)=substr(dni,-1,1);