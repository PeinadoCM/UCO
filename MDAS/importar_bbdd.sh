#!/bin/bash

# Configuración de la base de datos
DB_USER="root"               # Usuario de la base de datos
DB_NAME="mdas"          # Nombre de la base de datos a exportar
DB_PWD="1234"
BACKUP_FILE="backup.sql"  # Nombre del archivo de backup

# Mensaje de inicio
echo "📦 Cargando backup de la base de datos '$DB_NAME'"

# Ejecutar el dump dentro del contenedor y guardar el archivo
mysql -u $DB_USER -p$DB_PWD $DB_NAME < $BACKUP_FILE


