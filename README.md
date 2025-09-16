# Mathle
Este proyecto está basado en Nerdle, un desafío lógico en el que los jugadores deben descubrir una expresión matemática correcta en un número limitado de intentos. Inspirado en Wordle, este juego añade una mayor complejidad al requerir que los usuarios formen ecuaciones matemáticas en lugar de palabras.

# Base de datos
Para utilizar una base de datos de MariaDB comun entre todos los miembros del grupo, se han creado varios scripts que se emplean de la siguiente forma:

Para instalar la base de datos y todos tener un mismo usuario dentro de esta para que funcionen los script de importar y exportar hacer:
```bash
sudo ./instalar_bbdd.sh
```

Para cargar los datos dentro de la base de datos habra que tener en el directorio actual el fichero backup.sql y hacer:
```bash
sudo ./importar_bbdd.sh
```

Para exportar los datos de la base de datos habra que hacer:
```bash
sudo ./exportar_bbdd.sh
```
Este comando generara un archivo backup.sql el cual habra que subir a GitHub posteriormente
