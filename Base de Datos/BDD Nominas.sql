-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         11.5.2-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para nominas
CREATE DATABASE IF NOT EXISTS `nominas` /*!40100 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci */;
USE `nominas`;

-- Volcando estructura para tabla nominas.empleados
CREATE TABLE IF NOT EXISTS `empleados` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `dni` varchar(20) NOT NULL,
  `sexo` char(1) NOT NULL,
  `categoria` int(11) NOT NULL CHECK (`categoria` between 1 and 10),
  `anyos` int(11) NOT NULL CHECK (`anyos` >= 0),
  PRIMARY KEY (`id`),
  UNIQUE KEY `dni` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla nominas.empleados: ~3 rows (aproximadamente)
REPLACE INTO `empleados` (`id`, `nombre`, `dni`, `sexo`, `categoria`, `anyos`) VALUES
	(100, 'Grace Hopper', '32000034', 'F', 3, 9),
	(101, 'James Cosling', '32000032G', 'M', 4, 7),
	(102, 'Ada Lovelace', '32000031R', 'F', 3, 0);

-- Volcando estructura para tabla nominas.nomina
CREATE TABLE IF NOT EXISTS `nomina` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dni` varchar(20) NOT NULL,
  `sueldo` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_nomina_dni` (`dni`),
  CONSTRAINT `fk_nomina_dni` FOREIGN KEY (`dni`) REFERENCES `empleados` (`dni`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `nomina_ibfk_1` FOREIGN KEY (`dni`) REFERENCES `empleados` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla nominas.nomina: ~3 rows (aproximadamente)
REPLACE INTO `nomina` (`id`, `dni`, `sueldo`) VALUES
	(83, '32000034', 135000.00),
	(84, '32000032G', 145000.00),
	(85, '32000031R', 90000.00);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
