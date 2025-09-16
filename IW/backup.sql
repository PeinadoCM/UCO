/*M!999999\- enable the sandbox mode */ 
-- MariaDB dump 10.19  Distrib 10.11.11-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: mathle
-- ------------------------------------------------------
-- Server version	10.11.11-MariaDB-0ubuntu0.24.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `mensaje`
--

DROP TABLE IF EXISTS `mensaje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `mensaje` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `autor` varchar(100) NOT NULL,
  `fecha` datetime NOT NULL DEFAULT current_timestamp(),
  `mensaje` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `autor` (`autor`),
  CONSTRAINT `mensaje_ibfk_1` FOREIGN KEY (`autor`) REFERENCES `usuario` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mensaje`
--

LOCK TABLES `mensaje` WRITE;
/*!40000 ALTER TABLE `mensaje` DISABLE KEYS */;
INSERT INTO `mensaje` VALUES
(1,'jose','2025-05-21 12:28:17','Buen juego\r\n'),
(2,'SrGuille','2025-05-21 12:31:04','Llevo desde los 0,5 años jugando a este juego, y la verdad que es la mejor version que he visto del mismo, un saludo!'),
(5,'Manuel','2025-05-22 17:13:12','Que juego más divertido!!'),
(6,'Manuel','2025-05-23 11:03:36','Me encanta este juego'),
(7,'matas','2025-05-24 12:21:51','¡SOY UN MAQUINAA!!!!!');
/*!40000 ALTER TABLE `mensaje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `partida`
--

DROP TABLE IF EXISTS `partida`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `partida` (
  `idProblema` int(11) NOT NULL,
  `nombreUsuario` varchar(100) NOT NULL,
  `duracion` int(11) NOT NULL,
  `intentos` int(11) NOT NULL,
  `puntuacion` float NOT NULL,
  PRIMARY KEY (`idProblema`,`nombreUsuario`),
  KEY `nombreUsuario` (`nombreUsuario`),
  CONSTRAINT `partida_ibfk_1` FOREIGN KEY (`nombreUsuario`) REFERENCES `usuario` (`nombre`),
  CONSTRAINT `partida_ibfk_2` FOREIGN KEY (`idProblema`) REFERENCES `problema` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partida`
--

LOCK TABLES `partida` WRITE;
/*!40000 ALTER TABLE `partida` DISABLE KEYS */;
INSERT INTO `partida` VALUES
(4,'Manuel',4,1,10),
(13,'Manuel',70,3,4),
(14,'jose',48,3,5),
(14,'Manuel',5,1,10),
(14,'SrGuille',3,1,10),
(15,'ANTONIO',25,2,9.6),
(15,'SrGuille',29,1,10.8),
(18,'Manuel',15,1,12),
(19,'Manuel',28,1,14.4),
(22,'Manuel',5,7,0),
(23,'ANTONIO',110,2,5),
(25,'ANTONIO',11,1,12),
(26,'matas',14,1,16),
(27,'matas',15,1,14);
/*!40000 ALTER TABLE `partida` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `problema`
--

DROP TABLE IF EXISTS `problema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `problema` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `operacion` varchar(15) NOT NULL,
  `fecha` date NOT NULL DEFAULT current_timestamp(),
  `dificultad` int(11) NOT NULL,
  `modoJuego` enum('normal','ninos') DEFAULT NULL CHECK (`dificultad` in (6,8,10,12)),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problema`
--

LOCK TABLES `problema` WRITE;
/*!40000 ALTER TABLE `problema` DISABLE KEYS */;
INSERT INTO `problema` VALUES
(1,'3+76*5=395','2025-05-21',10,'normal'),
(2,'17+51+69=137','2025-05-21',12,'normal'),
(3,'21+64=85','2025-05-21',8,'normal'),
(4,'0*90=0','2025-05-21',6,'ninos'),
(5,'47-19=28','2025-05-18',8,'ninos'),
(6,'18/6=3','2025-05-21',6,'normal'),
(7,'7*90=630','2025-05-21',8,'ninos'),
(8,'56*85=4760','2025-05-16',10,'normal'),
(9,'16-9=7','2025-05-14',6,'normal'),
(10,'56+39+38=133','2025-05-16',12,'normal'),
(11,'19*30+86=656','2025-05-19',12,'ninos'),
(12,'75+12+3=90','2025-05-21',10,'ninos'),
(13,'0/14=0','2025-05-22',6,'normal'),
(14,'0*28=0','2025-05-22',6,'ninos'),
(15,'12+18=30','2025-05-22',8,'ninos'),
(16,'89+78+29=196','2025-05-22',12,'ninos'),
(17,'58*57=3306','2025-05-22',10,'ninos'),
(18,'88-64=24','2025-05-22',8,'normal'),
(19,'31-2+23+6=58','2025-05-22',12,'normal'),
(20,'13-7=6','2025-05-20',6,'ninos'),
(21,'0*49=0','2025-05-23',6,'ninos'),
(22,'3*4=12','2025-05-23',6,'normal'),
(23,'7*3=21','2025-05-24',6,'normal'),
(24,'88+41-25=104','2025-05-24',12,'ninos'),
(25,'49-10=39','2025-05-24',8,'normal'),
(26,'11*34-81=293','2025-05-24',12,'normal'),
(27,'41*60=2460','2025-05-24',10,'normal');
/*!40000 ALTER TABLE `problema` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `correo` varchar(100) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `contrasena` varchar(255) NOT NULL,
  `puntuacion` float DEFAULT 0,
  `tema` varchar(20) DEFAULT 'claro',
  PRIMARY KEY (`correo`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES
('ANTONIO@gmail.com','ANTONIO','$2a$10$z2MfRgb2ZcMeDaZSAZTfCenNRrpWfaR3DKDu0CMVectfjgWHdG9nO',26,'azul'),
('guillermito@gmail.com','SrGuille','$2a$10$djQZHGqS8nYxhqg6eM4BROkPpz.SmXrqpVpir2Q78ZxjkeDQUiCbG',20.8,'claro'),
('i22arpep@uco.es','pabloaz','$2a$10$pCX7zNpNItCi19bUZ72NVe/9Drqiun8mAuYXHdhydAAJ5AbT8Q176',0,'claro'),
('i22pecum@uco.es','Manuel','$2a$10$bhD0caxKBUfiThNgqDeAMuYp5Pqkj2MZfhgcpBSgzxUxRpoouOi.W',50,'oscuro'),
('jose@gmail.com','jose','$2a$10$FXlLqKiDr4nSXt6SJgxDueyhBKimpb7wMTDW/CFBBeZMV4LbBkBuG',5,'verde'),
('matas@gmail.com','matas','$2a$10$TrnY3iGrKZD1HMOcQsysPus0C9S9erKHQ6.Vu1VxY07cocUk4FMsi',30,'oscuro'),
('pepe@gmail.com','pepe','$2a$10$phzoqTMrA4lHoWDMtqe7HuYybp/9n6MaR0NrCyKQ31Ak8MY4BHKLa',0,'azul');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-24 12:38:37
