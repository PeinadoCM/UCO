select nombrecompleto
from votantes
where nombrecompleto like '%s%' and fechanacimiento not like '%9_' and fechanacimiento not like '%00';