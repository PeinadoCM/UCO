DROP TABLE IF EXISTS transacciones;
DROP TABLE IF EXISTS entradasVendidas;
DROP TABLE IF EXISTS entradas;
DROP TABLE IF EXISTS eventos;
DROP TABLE IF EXISTS organizadores;
DROP TABLE IF EXISTS usuarios;

CREATE TABLE usuarios (
    	correo VARCHAR(150) PRIMARY KEY,
    	nombreCompleto VARCHAR(100) NOT NULL,
    	contrasena VARCHAR(255) NOT NULL,
    	telefono INT NOT NULL,
    	dni VARCHAR(255) NOT NULL UNIQUE,
    	monedero FLOAT DEFAULT 0
);

CREATE TABLE organizadores (
    	correo VARCHAR(150) PRIMARY KEY,
	contrasena VARCHAR(255) NOT NULL,
	descripcion VARCHAR(1000) NOT NULL,
	monedero FLOAT DEFAULT 0
);

CREATE TABLE eventos (
	nombre VARCHAR(255) PRIMARY KEY,
	descripcion VARCHAR(1000) NOT NULL,
	ubicacion VARCHAR(255) NOT NULL,
	fecha DATE NOT NULL,
	limiteReventa FLOAT NOT NULL,
	correoOrganizador VARCHAR(150) NOT NULL,
	FOREIGN KEY (correoOrganizador) REFERENCES organizadores(correo)
);

CREATE TABLE entradas (
	id INT PRIMARY KEY AUTO_INCREMENT,
	tipo ENUM('GENERAL', 'VIP', 'NUMERADA') NOT NULL,
	tipoVenta ENUM('VENTAPRIMARIA', 'VENTASECUNDARIA') NOT NULL,
	disponibles INT NOT NULL,
	precio FLOAT NOT NULL,
	evento VARCHAR(255) NOT NULL,
	correoVendedor VARCHAR(150) DEFAULT NULL, 
	FOREIGN KEY (evento) REFERENCES eventos(nombre)
);

CREATE TABLE entradasVendidas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    idEntrada INT NOT NULL,
    correoUsuario VARCHAR(150) NOT NULL,
    FOREIGN KEY (idEntrada) REFERENCES entradas(id),
    FOREIGN KEY (correoUsuario) REFERENCES usuarios(correo)
);

CREATE TABLE transacciones (
	id INT PRIMARY KEY AUTO_INCREMENT,
	tipo ENUM('VENTAPRIMARIA', 'VENTASECUNDARIA') NOT NULL,
	importe FLOAT NOT NULL,
	fecha DATE NOT NULL,
	comprador VARCHAR(150) NOT NULL,
	vendedor VARCHAR(150) NOT NULL,
	evento VARCHAR(255) NOT NULL,
	FOREIGN KEY (evento) REFERENCES eventos(nombre)
);

INSERT INTO usuarios (correo, nombreCompleto, contrasena, telefono, dni, monedero) VALUES 
('i22pecum@uco.es', 'Manuel Peinado', '$2a$10$Tdzno1MvLb5wCtI/wem1VuuoNvZxLloe38dFOVuIZdyKKsA.AiBh6', 626394400, '31880419G', 25.0),
('ana.garcia@gmail.com', 'Ana García López', '$2a$10$a7JkldmkePq9KM2FjsldOeIrXZjkQwzGJks1', 654789321, '12345678A', 50.0),
('luis.martinez@gmail.com', 'Luis Martínez', '$2a$10$sjdhfjKH8sjkdjsLJFlkslfJKslf9', 698123456, '87654321B', 10.0);

INSERT INTO organizadores (correo, contrasena, descripcion, monedero) VALUES 
('organizador@gmail.com', '$2a$10$Tdzno1MvLb5wCtI/wem1VuuoNvZxLloe38dFOVuIZdyKKsA.AiBh6', 'Organizador de eventos musicales y deportivos.', 125.0),
('eventosplus@gmail.com', '$2a$10$sakdks23Jskddlsjdjkdjdd2ls93jdsl', 'Empresa dedicada a organización de eventos culturales.', 0);


INSERT INTO eventos (nombre, descripcion, ubicacion, fecha, limiteReventa, correoOrganizador) VALUES
('Concierto Rock 2025', 'Gran concierto de bandas nacionales.', 'Madrid Arena', '2025-06-15', 0.5, 'organizador@gmail.com'),
('Maratón Ciudad 2025', 'Carrera popular anual.', 'Parque del Retiro, Madrid', '2025-09-10', 0.2, 'organizador@gmail.com'),
('Festival Jazz 2025', 'Festival internacional de jazz.', 'Teatro Real, Madrid', '2025-07-20', 0.4, 'eventosplus@gmail.com');

INSERT INTO entradas (tipo, tipoVenta, disponibles, precio, evento, correoVendedor) VALUES
('GENERAL', 'VENTAPRIMARIA', 99, 30.0, 'Concierto Rock 2025', 'organizador@gmail.com'),
('VIP', 'VENTAPRIMARIA', 40, 60.0, 'Concierto Rock 2025', 'organizador@gmail.com'),
('NUMERADA', 'VENTAPRIMARIA', 149, 40.0, 'Concierto Rock 2025', 'organizador@gmail.com'),
('GENERAL', 'VENTAPRIMARIA', 299, 20.0, 'Maratón Ciudad 2025', 'organizador@gmail.com'),
('VIP', 'VENTAPRIMARIA', 50, 40.0, 'Maratón Ciudad 2025', 'organizador@gmail.com'),
('NUMERADA', 'VENTAPRIMARIA', 100, 25.0, 'Maratón Ciudad 2025', 'organizador@gmail.com'),
('GENERAL', 'VENTAPRIMARIA', 80, 25.0, 'Festival Jazz 2025', 'eventosplus@gmail.com'),
('VIP', 'VENTAPRIMARIA', 30, 50.0, 'Festival Jazz 2025', 'eventosplus@gmail.com'),
('NUMERADA', 'VENTAPRIMARIA', 70, 35.0, 'Festival Jazz 2025', 'eventosplus@gmail.com'),
('GENERAL', 'VENTASECUNDARIA', 1, 22.0, 'Maraton Ciudad 2025', 'luis.martinez@gmail.com');

INSERT INTO entradasVendidas (idEntrada, correoUsuario) VALUES
(1, 'i22pecum@uco.es'),
(2, 'i22pecum@uco.es'),
(3, 'ana.garcia@gmail.com'),
(10, 'luis.martinez@gmail.com');

INSERT INTO transacciones (tipo, importe, fecha, comprador, vendedor, evento) VALUES
('VENTAPRIMARIA', 30.0, '2025-05-10', 'i22pecum@uco.es', 'organizador@gmail.com', 'Concierto Rock 2025'),
('VENTAPRIMARIA', 60.0, '2025-05-11', 'i22pecum@uco.es', 'organizador@gmail.com', 'Concierto Rock 2025'),
('VENTAPRIMARIA', 15.0, '2025-05-12', 'ana.garcia@gmail.com', 'organizador@gmail.com', 'Concierto Rock 2025'),
('VENTAPRIMARIA', 20.0, '2025-05-13', 'luis.martinez@gmail.com', 'organizador@gmail.com', 'Maratón Ciudad 2025');



