-- phpMyAdmin SQL Dump
-- version 2.7.0-pl2
-- http://www.phpmyadmin.net
-- 
-- Servidor: oraclepr.uco.es
-- Version del servidor: 5.1.73
-- Version de PHP: 5.3.3
-- 
-- Base de datos: `i22pecum`
-- 

-- --------------------------------------------------------

-- 
-- Estructura de tabla para la tabla `Jugadores`
-- 

DROP TABLE IF EXISTS `Jugadores`;
CREATE TABLE IF NOT EXISTS `Jugadores` (
  `Correo` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Nombre_y_apellidos` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Fecha_de_nacimiento` date DEFAULT NULL,
  `Fecha_de_inscripcion` date DEFAULT NULL,
  `Contraseña` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Administrador` TINYINT(1) DEFAULT 0,
  PRIMARY KEY (`Correo`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 
-- Volcar la base de datos para la tabla `Jugadores`
-- 

INSERT INTO `Jugadores` VALUES ('i22pecum@uco.es', 'Manuel Peinado Cuenca', '2004-12-01', '2024-10-29', 'a8JkL2', 1);
INSERT INTO `Jugadores` VALUES ('l30jogar@uco.es', 'Laura Jofré García', '2005-05-15', '2023-11-08', 'Pq7RmT', 0);
INSERT INTO `Jugadores` VALUES ('p13sucre@uco.es', 'Pablo Suárez Crespo', '2003-07-22', '2021-03-20', 'x9KvW3', 0);
INSERT INTO `Jugadores` VALUES ('v19pemar@uco.es', 'Víctor Pérez Martín', '1995-09-09', '2020-12-13', 'Zt5LpQ', 0);
INSERT INTO `Jugadores` VALUES ('a45calon@uco.es', 'Ana Castillo Londoño', '1999-11-18', '2023-09-28', 'N8xJk2', 0);
INSERT INTO `Jugadores` VALUES ('j32doagu@uco.es', 'Javier Domínguez Aguilar', '2005-03-27', '2022-06-04', 'R3yTnL', 0);

-- --------------------------------------------------------

-- 
-- Estructura de tabla para la tabla `Pistas`
-- 

-- Estado -> 1 (TRUE) para disponible, 0 (FALSE) para no disponible
-- Tipo -> 1 (TRUE) para exterior, 0 (FALSE) para interior

DROP TABLE IF EXISTS `Pistas`;
CREATE TABLE IF NOT EXISTS `Pistas` (
  `Nombre` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Estado` TINYINT(1) NOT NULL,          
  `Tipo` TINYINT(1) NOT NULL,            
  `Tamaño` ENUM('MINIBASKET', 'ADULTOS', 'TRESVSTRES') NOT NULL,
  `Jugadores_maximos` INT NOT NULL,
  PRIMARY KEY (`Nombre`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 
-- Volcar la base de datos para la tabla `Pistas`
-- 

INSERT INTO `Pistas` VALUES ('Basket1', 1, 0, 'MINIBASKET', 10);
INSERT INTO `Pistas` VALUES ('Adultos1', 1, 1, 'ADULTOS', 12);
INSERT INTO `Pistas` VALUES ('3VS31', 0, 0, 'TRESVSTRES', 6);
INSERT INTO `Pistas` VALUES ('Basket2', 1, 1, 'MINIBASKET', 8);
INSERT INTO `Pistas` VALUES ('Adultos2', 0, 1, 'ADULTOS', 10);
INSERT INTO `Pistas` VALUES ('3VS32', 1, 0, 'TRESVSTRES', 4);

-- --------------------------------------------------------

-- 
-- Estructura de tabla para la tabla `Materiales`
-- 

-- Uso -> 1 (TRUE) para exterior, 0 (FALSE) para interior

DROP TABLE IF EXISTS `Materiales`;
CREATE TABLE IF NOT EXISTS `Materiales` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Uso` TINYINT(1) NOT NULL,          
  `Tipo` ENUM('PELOTAS', 'CANASTAS', 'CONOS') NOT NULL,
  `Estado` ENUM('DISPONIBLE', 'RESERVADO', 'MAL_ESTADO') NOT NULL,
  `Pista` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`Id`),
  CONSTRAINT `fk_pista`
  FOREIGN KEY (`Pista`) REFERENCES `Pistas`(`Nombre`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 
-- Volcar la base de datos para la tabla `Materiales`
-- 

INSERT INTO `Materiales` VALUES (1, 1, 'PELOTAS', 'DISPONIBLE', null);
INSERT INTO `Materiales` VALUES (2, 0, 'CONOS', 'RESERVADO', 'Basket1');
INSERT INTO `Materiales` VALUES (3, 0, 'CANASTAS', 'DISPONIBLE', null);
INSERT INTO `Materiales` VALUES (4, 0, 'CANASTAS', 'MAL_ESTADO', null);
INSERT INTO `Materiales` VALUES (5, 1, 'PELOTAS', 'RESERVADO', 'Basket1');
INSERT INTO `Materiales` VALUES (6, 1, 'PELOTAS', 'DISPONIBLE', null);

-- --------------------------------------------------------

-- 
-- Estructura de tabla para la tabla `Reservas`
-- 

-- Si numero_adultos = 0 -> ReservaInfantil
-- Si numero_niños = 0 -> ReservaAdultos
-- Si numero_adultos y numero_niños != 0 -> ReservaFamiliar

DROP TABLE IF EXISTS `Reservas`;
CREATE TABLE IF NOT EXISTS `Reservas` (
  `Nombre_pista` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Fecha` date NOT NULL,
  `Hora` time NOT NULL,
  `Correo_usuario` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Duracion` int NOT NULL,
  `Precio` float NOT NULL,
  `Descuento` float DEFAULT NULL,
  `Numero_de_adultos` int DEFAULT NULL,
  `Numero_de_niños` int DEFAULT NULL,
  PRIMARY KEY (`Nombre_pista`, `Fecha`, `Hora`),
  CONSTRAINT `fk_reservas_usuarios`
  FOREIGN KEY (`Correo_usuario`) REFERENCES `Jugadores`(`Correo`),
  CONSTRAINT `fk_reservas_pistas`
  FOREIGN KEY (`Nombre_pista`) REFERENCES `Pistas`(`Nombre`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 
-- Volcar la base de datos para la tabla `Reservas`
-- 

INSERT INTO `Reservas` VALUES ('3VS31', '2024-11-18', '12:00', 'l30jogar@uco.es', 60, 20, 0.0, 2, 4);
INSERT INTO `Reservas` VALUES ('Adultos2', '2025-01-10', '13:30', 'v19pemar@uco.es', 120, 40, 0.1, 8, 0);
INSERT INTO `Reservas` VALUES ('3VS31', '2025-01-15', '10:00', 'i22pecum@uco.es',90, 30, 0.0, 3, 3);
INSERT INTO `Reservas` VALUES ('Adultos2', '2024-12-10', '9:00', 'a45calon@uco.es', 120, 40, 0.0, 5, 0);
INSERT INTO `Reservas` VALUES ('3VS32', '2024-12-26', '17:30', 'j32doagu@uco.es', 60, 20, 0.1, 2, 2);
INSERT INTO `Reservas` VALUES ('Adultos1', '2024-12-10', '16:00', 'a45calon@uco.es', 90, 30, 0.0, 4, 0);
INSERT INTO `Reservas` VALUES ('Basket1', '2024-11-29', '10:30', 'i22pecum@uco.es', 60, 20, 0.05, 0, 6);
INSERT INTO `Reservas` VALUES ('Basket1', '2024-12-02', '12:00', 'i22pecum@uco.es', 90, 30, 0.05, 2, 4);
INSERT INTO `Reservas` VALUES ('Basket2', '2024-12-02', '12:00', 'j32doagu@uco.es', 60, 20, 0.05, 0, 8);
INSERT INTO `Reservas` VALUES ('Basket1', '2024-12-05', '16:00', 'v19pemar@uco.es', 120, 40, 0.05, 0, 9);
INSERT INTO `Reservas` VALUES ('Basket1', '2024-12-05', '19:00', 'j32doagu@uco.es', 60, 20, 0.05, 1, 7);
INSERT INTO `Reservas` VALUES ('Adultos1', '2024-11-20', '18:30', 'i22pecum@uco.es', 120, 40, 0.05, 10, 0);

-- --------------------------------------------------------

-- 
-- Estructura de tabla para la tabla `Bonos`
-- 

DROP TABLE IF EXISTS `Bonos`;
CREATE TABLE IF NOT EXISTS `Bonos` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Correo_usuario` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Tipo` ENUM('MINIBASKET', 'ADULTOS', 'TRESVSTRES') NOT NULL,
  `Fecha_caducidad` date DEFAULT NULL,
  `Sesiones_disponibles` int DEFAULT 5,
  PRIMARY KEY (`Id`),
  CONSTRAINT `fk_bonos_usuarios`
  FOREIGN KEY (`Correo_usuario`) REFERENCES `Jugadores`(`Correo`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 
-- Volcar la base de datos para la tabla `Bonos`
-- 

INSERT INTO `Bonos` VALUES (1, 'i22pecum@uco.es', 'MINIBASKET', '2025-11-29', 3);
INSERT INTO `Bonos` VALUES (2, 'l30jogar@uco.es', 'TRESVSTRES', null, 5);
INSERT INTO `Bonos` VALUES (3, 'j32doagu@uco.es', 'MINIBASKET', '2025-12-02', 3);
INSERT INTO `Bonos` VALUES (4, 'v19pemar@uco.es', 'MINIBASKET', '2025-12-05', 4);
INSERT INTO `Bonos` VALUES (5, 'i22pecum@uco.es', 'ADULTOS', '2025-11-20', 4);
INSERT INTO `Bonos` VALUES (6, 'v19pemar@uco.es', 'TRESVSTRES', null, 5);

-- --------------------------------------------------------

-- 
-- Estructura de tabla para la tabla `Reserva_Bonos`
-- 

DROP TABLE IF EXISTS `Reserva_Bonos`;
CREATE TABLE IF NOT EXISTS `Reserva_Bonos` (
  `Id_bono` int NOT NULL,
  `Nombre_pista` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Fecha` date NOT NULL,
  `Hora` time NOT NULL,
  PRIMARY KEY (`Id_bono`, `Nombre_pista`, `Fecha`, `Hora`),
  CONSTRAINT `fk_bonos`
  FOREIGN KEY (`Id_bono`) REFERENCES `Bonos`(`Id`),
  CONSTRAINT `fk_nombre_pista`
  FOREIGN KEY (`Nombre_pista`) REFERENCES `Bonos`(`Nombre_pista`),
  CONSTRAINT `fk_fecha`
  FOREIGN KEY (`Fecha`) REFERENCES `Bonos`(`Fecha`),
  CONSTRAINT `fk_hora`
  FOREIGN KEY (`Hora`) REFERENCES `Bonos`(`Hora`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 
-- Volcar la base de datos para la tabla `Reserva_Bonos`
-- 

INSERT INTO `Reserva_Bonos` VALUES (1, 'Basket1', '2024-11-29', '10:30');
INSERT INTO `Reserva_Bonos` VALUES (1, 'Basket1', '2024-12-02', '12:00');
INSERT INTO `Reserva_Bonos` VALUES (3, 'Basket2', '2024-12-02', '12:00');
INSERT INTO `Reserva_Bonos` VALUES (4, 'Basket1', '2024-12-05', '16:00');
INSERT INTO `Reserva_Bonos` VALUES (3, 'Basket1', '2024-12-05', '19:00');
INSERT INTO `Reserva_Bonos` VALUES (5, 'Adultos1', '2024-11-20', '18:30');

