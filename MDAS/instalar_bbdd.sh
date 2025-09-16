#!/bin/bash

DB_ROOT_PASSWORD="1234"
DB_NAME="mdas"

echo "🔧 Instalando MariaDB Server..."

# Actualiza paquetes e instala MariaDB sin confirmación
sudo apt update
sudo apt install -y mariadb-server

echo "✅ MariaDB instalado."

# Habilita y arranca el servicio
sudo systemctl enable mariadb
sudo systemctl start mariadb

echo "🔐 Configurando contraseña para root..."

# Cambiar el plugin de autenticación y establecer la contraseña
sudo mysql <<EOF
-- Usa el plugin mysql_native_password y asigna contraseña a root
ALTER USER 'root'@'localhost' IDENTIFIED VIA mysql_native_password USING PASSWORD('$DB_ROOT_PASSWORD');
FLUSH PRIVILEGES;
EOF

echo "✅ Contraseña del usuario root configurada como '$DB_ROOT_PASSWORD'"

echo "📦 Creando base de datos '$DB_NAME'..."

# Crear la base de datos
mysql -u root -p$DB_ROOT_PASSWORD <<EOF
CREATE DATABASE IF NOT EXISTS \`$DB_NAME\` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
EOF

echo "✅ Base de datos '$DB_NAME' creada exitosamente."

