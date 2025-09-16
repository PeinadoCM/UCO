# MDAS


# Componentes desarrollados
En esta práctica se han implementado los siguientes componentes del sistema: el Gestor de Entradas y el Gestor de Eventos, definidos previamente en la Práctica 1. Asimismo, se ha desarrollado en su totalidad el componente de negocio TransaccionMgr. Sin embargo, debido a la gran cantidad de funcionalidades que este componente debía abarcar, decidimos dividirlo en dos partes: EventoMgr, responsable de todas las operaciones CRUD relacionadas con los eventos, y TransaccionMgr, encargado de gestionar las operaciones asociadas a las entradas y sus respectivas ventas.

Adicionalmente, se ha implementado parcialmente el componente UsuarioMgr, con el objetivo de permitir el registro y el inicio de sesión tanto de usuarios como de organizadores.

# Patrones de diseño implementados
En esta práctica se han implementado dos patrones de diseño. En primer lugar, el patrón **Factory** ha sido utilizado para la creación de los distintos tipos de entradas, permitiendo una mayor flexibilidad y escalabilidad en la instanciación de objetos. En segundo lugar, se ha aplicado el patrón **Singleton** con el fin de garantizar que los componentes de gestión (EventoMgr, UsuarioMgr y TransaccionMgr) no tengan más de una instancia activa simultáneamente, asegurando así un control centralizado y consistente del estado de dichos componentes.

# Estructura del proyecto
El proyecto esta estructurado de la siguiente forma:
```text
mdas/
├── target/
└── src/
    └── main/
        ├── java/
        │   ├── aux/
        │   ├── dto/
        │   ├── dao/
        │   ├── mgr/
        │   └── main/
        └── resources/
```

Y dentro de cada carpeta encontramos los siguientes archivos:

- **resources** --> Los ficheros *.properties* donde se definen las consultas SQL y la informació para acceder a la base de datos
- **java** --> Los archivos *.java* de la aplicación
- **aux** --> Las clases con funcionalidades auxiliares al programa como son una clase para ayudar a solicitar parametros por la terminal, una clase para ayudar a hacer la conexion a la base de datos y una clase para ayudar a leer las consultas SQL del fichero *.properties*
- **dto** (Data Transfer Objects) --> Las clases donde se almacena la informacion traida de la base de datos
- **dao** (Data Access Objects) --> Las clases que se encargan de acceder a la base de datos
- **mgr** --> Las clases que se encargan de comunicar el main con los DAOs y ademas de hacer comprobaciones adicionales
- **main** --> La clase donde se define el main de la aplicacion

# Claves de Acceso
Para acceder a la aplicacion hay varios organizadores y usuarios creados, entre ellos aqui estan las credenciales de un usuario y un organizador:
Usuario --> Correo: i22pecum@uco.es Contraseña: 1234

Organizador --> Correo: organizador@gmail.com Contraseña: 1234

# Base de datos
Para utilizar una base de datos de MariaDB comun entre todos los miembros del grupo, se han creado varios scripts que se emplean de la siguiente forma:

Para instalar la base de datos y todos tener un mismo usuario dentro de esta para que funcionen los script de importar y exportar hacer:
```bash
sudo ./instalar_bbdd.sh
```
Si este script diera error a la hora de crear el usuario tendriamos que entrar a la BBDD y ejecutar el siguiente comando:
```sql
ALTER USER 'root'@'localhost' IDENTIFIED BY '1234';
```
Este comando nos cambiaria la contraseña del usuario *root* dentro de la base de datos a *1234*


Para cargar los datos dentro de la base de datos habra que tener en el directorio actual el fichero backup.sql y hacer:
```bash
sudo ./importar_bbdd.sh
```

Para exportar los datos de la base de datos habra que hacer:
```bash
sudo ./exportar_bbdd.sh
```
Este comando generara un archivo backup.sql con toda la informacion sobre la BBDD
