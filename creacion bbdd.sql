DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS mensaje;
DROP TABLE IF EXISTS problema;
DROP TABLE IF EXISTS partida;

CREATE TABLE usuario (
    correo VARCHAR(100) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    puntuacion FLOAT DEFAULT 0,
    tema VARCHAR(20) DEFAULT 'claro'
);

CREATE TABLE mensaje (
    id INT AUTO_INCREMENT PRIMARY KEY,
    autor VARCHAR(100) NOT NULL,
    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    mensaje TEXT NOT NULL,
    FOREIGN KEY (autor) REFERENCES usuario(nombre)
);

CREATE TABLE problema (
    id INT AUTO_INCREMENT PRIMARY KEY,
    operacion VARCHAR(15) NOT NULL,
    fecha DATE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    dificultad INT NOT NULL,
    modoJuego ENUM('normal', 'ninos')
    CHECK (dificultad IN (6, 8, 10, 12))
);

CREATE TABLE partida (
    idProblema INT NOT NULL,
    nombreUsuario VARCHAR(100) NOT NULL,
    duracion INT NOT NULL,
    intentos INT NOT NULL,
    puntuacion FLOAT NOT NULL,
    PRIMARY KEY (idProblema, nombreUsuario),
    FOREIGN KEY (nombreUsuario) REFERENCES usuario(nombre),
    FOREIGN KEY (idProblema) REFERENCES problema(id)
);

