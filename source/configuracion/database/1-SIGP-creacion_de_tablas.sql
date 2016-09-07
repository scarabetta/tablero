-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.1.14-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             9.1.0.4867
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for proyectos_ba_generated
-- CREATE DATABASE IF NOT EXISTS `proyectos_ba_generated` /*!40100 DEFAULT CHARACTER SET latin1 */;
-- USE `proyectos_ba_generated`;

-- Dumping structure for table proyectos_ba_generated.jurisdiccion
CREATE TABLE IF NOT EXISTS `jurisdiccion` (
  `idJurisdiccion` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(512) NOT NULL,
  `abreviatura` varchar(50) NOT NULL,
  `mision` text NOT NULL,
  `codigo` varchar(50) NOT NULL,
  PRIMARY KEY (`idJurisdiccion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table proyectos_ba_generated.objetivo_jurisdiccional
CREATE TABLE IF NOT EXISTS `objetivo_jurisdiccional` (
  `idObjetivoJurisdiccional` int(11) NOT NULL AUTO_INCREMENT,
  `idJurisdiccion` int(11) DEFAULT NULL,
  `codigo` varchar(50) DEFAULT NULL,
  `nombre` varchar(512) NOT NULL,
  `idJurisdiccionAux` int(11) DEFAULT NULL,
  PRIMARY KEY (`idObjetivoJurisdiccional`),
  KEY `FK_jurisdiccion_objetoJurisdiccional` (`idJurisdiccion`),
  CONSTRAINT `FK_jurisdiccion_objetoJurisdiccional` FOREIGN KEY (`idJurisdiccion`) REFERENCES `jurisdiccion` (`idJurisdiccion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table proyectos_ba_generated.objetivo_operativo
CREATE TABLE IF NOT EXISTS `objetivo_operativo` (
  `idObjetivoOperativo` int(11) NOT NULL AUTO_INCREMENT,
  `idObjetivoJurisdiccional` int(11) DEFAULT NULL,
  `codigo` varchar(50) DEFAULT NULL,
  `nombre` varchar(512) NOT NULL,
  `idObjetivoJurisdiccionalAux` int(11) DEFAULT NULL,
  PRIMARY KEY (`idObjetivoOperativo`),
  KEY `FK_objetivoJurisdiccional_objetivoOperativo` (`idObjetivoJurisdiccional`),
  CONSTRAINT `FK_objetivoJurisdiccional_objetivoOperativo` FOREIGN KEY (`idObjetivoJurisdiccional`) REFERENCES `objetivo_jurisdiccional` (`idObjetivoJurisdiccional`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table proyectos_ba_generated.proyecto
CREATE TABLE IF NOT EXISTS `proyecto` (
  `idProyecto` int(10) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(50) DEFAULT NULL,
  `nombre` varchar(512) NOT NULL,
  `descripcion` text,
  `idObjetivoOperativo` int(11) DEFAULT NULL,
  `tipoProyecto` varchar(11) DEFAULT NULL,
  `idArea` int(11) DEFAULT NULL,
  `meta` decimal(22,2) DEFAULT NULL,
  `unidadMeta` varchar(512) DEFAULT NULL,
  `poblacionAfectada` bigint(20) DEFAULT NULL,
  `liderProyecto` varchar(512) DEFAULT NULL,
  `area` varchar(512) DEFAULT NULL,
  `tipoUbicacionGeografica` varchar(50) DEFAULT NULL,
  `direccion` varchar(512) DEFAULT NULL,
  `cambioLegislativo` tinyint(4) DEFAULT NULL,
  `fechaInicio` date NOT NULL,
  `fechaFin` date DEFAULT NULL,
  `prioridadJurisdiccional` varchar(50) DEFAULT NULL,
  `estado` varchar(50) DEFAULT NULL,
  `verificado` tinyint(4) NOT NULL DEFAULT '0',
  `idObjetivoOperativo2` int(11) NOT NULL,
  `idJurisdiccion2` int(11) DEFAULT NULL,
  `idObjetivoJurisdiccional2` int(11) DEFAULT NULL,
  `organismosCorresponsables` varchar(512) DEFAULT NULL,
  `coordenadaX` varchar(50) DEFAULT NULL,
  `coordenadaY` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idProyecto`),
  KEY `FK_idObjetivoOperativo_proyecto` (`idObjetivoOperativo`),
  CONSTRAINT `FK_idObjetivoOperativo_proyecto` FOREIGN KEY (`idObjetivoOperativo`) REFERENCES `objetivo_operativo` (`idObjetivoOperativo`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table proyectos_ba_generated.archivos_proyecto
CREATE TABLE IF NOT EXISTS `archivos_proyecto` (
  `idArchivoProyecto` int(10) NOT NULL AUTO_INCREMENT,
  `proyecto_idproyecto` int(10) DEFAULT NULL COMMENT 'El id al proyecto al que pertence el archivo',
  `nombre` varchar(512) NOT NULL COMMENT 'El nombre del archivo',
  PRIMARY KEY (`idArchivoProyecto`),
  KEY `FK_archivos_proyecto_proyecto` (`proyecto_idproyecto`),
  CONSTRAINT `FK_archivos_proyecto_proyecto` FOREIGN KEY (`proyecto_idproyecto`) REFERENCES `proyecto` (`idProyecto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table proyectos_ba_generated.area
CREATE TABLE IF NOT EXISTS `area` (
  `idArea` int(11) NOT NULL AUTO_INCREMENT,
  `idJurisdiccion` int(11) NOT NULL,
  `nombre` varchar(256) NOT NULL,
  PRIMARY KEY (`idArea`),
  KEY `FK__jurisdiccion` (`idJurisdiccion`),
  CONSTRAINT `FK__jurisdiccion` FOREIGN KEY (`idJurisdiccion`) REFERENCES `jurisdiccion` (`idJurisdiccion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Contiene las areas de cada jurisdiccion.';

-- Data exporting was unselected.


-- Dumping structure for table proyectos_ba_generated.comuna
CREATE TABLE IF NOT EXISTS `comuna` (
  `idComuna` int(11) NOT NULL AUTO_INCREMENT,
  `abreviatura` varchar(50) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`idComuna`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table proyectos_ba_generated.comuna_por_proyecto
CREATE TABLE IF NOT EXISTS `comuna_por_proyecto` (
  `idProyecto` int(11) NOT NULL,
  `idComuna` int(11) NOT NULL,
  PRIMARY KEY (`idProyecto`,`idComuna`),
  KEY `FK_idUbicacionGeografica` (`idComuna`),
  CONSTRAINT `FK_idComuna` FOREIGN KEY (`idComuna`) REFERENCES `comuna` (`idComuna`),
  CONSTRAINT `FK_idProyecto_ubicacionGeografica` FOREIGN KEY (`idProyecto`) REFERENCES `proyecto` (`idProyecto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table proyectos_ba_generated.eje_de_gobierno
CREATE TABLE IF NOT EXISTS `eje_de_gobierno` (
  `idEjeDeGobierno` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(512) NOT NULL,
  `descripcion` varchar(512) NOT NULL,
  `ejemplos` varchar(512) NOT NULL,
  PRIMARY KEY (`idEjeDeGobierno`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table proyectos_ba_generated.eje_de_gobierno_por_proyecto
CREATE TABLE IF NOT EXISTS `eje_de_gobierno_por_proyecto` (
  `id_Proyecto` int(11) NOT NULL,
  `id_EjeDeGobierno` int(11) NOT NULL,
  PRIMARY KEY (`id_Proyecto`,`id_EjeDeGobierno`),
  KEY `FK_idEjeDeGobierno` (`id_EjeDeGobierno`),
  CONSTRAINT `FK_idEjeDeGobierno` FOREIGN KEY (`id_EjeDeGobierno`) REFERENCES `eje_de_gobierno` (`idEjeDeGobierno`),
  CONSTRAINT `FK_idProyecto_ejeDeGobierno` FOREIGN KEY (`id_Proyecto`) REFERENCES `proyecto` (`idProyecto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table proyectos_ba_generated.indicador_estrategico
CREATE TABLE IF NOT EXISTS `indicador_estrategico` (
  `idIndicadorEstrategico` int(11) NOT NULL AUTO_INCREMENT,
  `idObjetivoJurisdiccional` int(11) DEFAULT NULL,
  `indicador` varchar(256) NOT NULL,
  `descripcion` varchar(512) NOT NULL,
  PRIMARY KEY (`idIndicadorEstrategico`),
  KEY `FK_objetivoJurisdiccional_indicardorEstrategico` (`idObjetivoJurisdiccional`),
  CONSTRAINT `FK_objetivoJurisdiccional_indicardorEstrategico` FOREIGN KEY (`idObjetivoJurisdiccional`) REFERENCES `objetivo_jurisdiccional` (`idObjetivoJurisdiccional`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table proyectos_ba_generated.poblacion_meta
CREATE TABLE IF NOT EXISTS `poblacion_meta` (
  `idPoblacionMeta` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(512) NOT NULL,
  PRIMARY KEY (`idPoblacionMeta`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table proyectos_ba_generated.poblacion_meta_por_proyecto
CREATE TABLE IF NOT EXISTS `poblacion_meta_por_proyecto` (
  `idProyecto` int(11) NOT NULL,
  `idPoblacionMeta` int(11) NOT NULL,
  PRIMARY KEY (`idProyecto`,`idPoblacionMeta`),
  KEY `FK_idPoblacionMeta` (`idPoblacionMeta`),
  CONSTRAINT `FK_idPoblacionMeta` FOREIGN KEY (`idPoblacionMeta`) REFERENCES `poblacion_meta` (`idPoblacionMeta`),
  CONSTRAINT `FK_idProyecto_poblacionMeta` FOREIGN KEY (`idProyecto`) REFERENCES `proyecto` (`idProyecto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table proyectos_ba_generated.presupuesto_por_anio
CREATE TABLE IF NOT EXISTS `presupuesto_por_anio` (
  `idPresupuestoPoranio` int(11) NOT NULL AUTO_INCREMENT,
  `idProyecto` int(11) DEFAULT NULL,
  `anio` smallint(6) NOT NULL,
  `presupuesto` double DEFAULT NULL,
  `otrasFuentes` double DEFAULT NULL,
  PRIMARY KEY (`idPresupuestoPoranio`),
  KEY `FK_idProyecto_presupuestoPorAnio` (`idProyecto`),
  CONSTRAINT `FK_idProyecto_presupuestoPorAnio` FOREIGN KEY (`idProyecto`) REFERENCES `proyecto` (`idProyecto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table proyectos_ba_generated.rol
CREATE TABLE IF NOT EXISTS `rol` (
  `idRol` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `descripcion` varchar(100) NOT NULL,
  PRIMARY KEY (`idRol`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table proyectos_ba_generated.rol_por_usuario
CREATE TABLE IF NOT EXISTS `rol_por_usuario` (
  `rol_idRol` int(11) NOT NULL,
  `usuario_idUsuario` int(11) NOT NULL,
  PRIMARY KEY (`rol_idRol`,`usuario_idUsuario`),
  KEY `usuario_fk` (`usuario_idUsuario`),
  CONSTRAINT `rol_fk` FOREIGN KEY (`rol_idRol`) REFERENCES `rol` (`idRol`),
  CONSTRAINT `usuario_fk` FOREIGN KEY (`usuario_idUsuario`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table proyectos_ba_generated.tema_transversal
CREATE TABLE IF NOT EXISTS `tema_transversal` (
  `idTemaTransversal` int(11) NOT NULL AUTO_INCREMENT,
  `temaTransversal` varchar(512) NOT NULL,
  `activo` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`idTemaTransversal`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table proyectos_ba_generated.usuario
CREATE TABLE IF NOT EXISTS `usuario` (
  `idUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `apellido` varchar(50) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  `activo` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`idUsuario`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table proyectos_ba_generated.usuario_por_jurisdiccion
CREATE TABLE IF NOT EXISTS `usuario_por_jurisdiccion` (
  `jurisdiccion_idJurisdiccion` int(11) NOT NULL,
  `usuario_idUsuario` int(11) NOT NULL,
  PRIMARY KEY (`jurisdiccion_idJurisdiccion`,`usuario_idUsuario`),
  KEY `FK_idJurisdiccion` (`jurisdiccion_idJurisdiccion`),
  KEY `FK_idUsuario_jurisdiccion` (`usuario_idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Dumping structure for table proyectos_ba_generated.permiso_entidad
CREATE TABLE IF NOT EXISTS `permiso_entidad` (
  `idPermisoEntidad` int(11) NOT NULL AUTO_INCREMENT,
  `alta` tinyint(1) DEFAULT NULL,
  `idRol` int(11) DEFAULT NULL,
  `baja` tinyint(1) DEFAULT NULL,
  `modificacion` tinyint(1) DEFAULT NULL,
  `nombre` varchar(50) NOT NULL,
  `gestion` tinyint(4) NOT NULL,
  PRIMARY KEY (`idPermisoEntidad`),
  KEY `FK_idRol_pe` (`idRol`),
  CONSTRAINT `FK_idRol_pe` FOREIGN KEY (`idRol`) REFERENCES `rol` (`idRol`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
