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

CREATE TABLE `jurisdiccion_aud` (
  `idJurisdiccion` int(11) NOT NULL,
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `abreviatura` varchar(50) NOT NULL,
  `mision` text NOT NULL,
  `codigo` varchar(50) NOT NULL,
  PRIMARY KEY (`idJurisdiccion`,`rev`),
  KEY `FK_m2o8327p3k67ukeke5lq28gax` (`rev`),
  CONSTRAINT `FK_m2o8327p3k67ukeke5lq28gax` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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

CREATE TABLE `objetivo_jurisdiccional_aud` (
  `idObjetivoJurisdiccional` int(11) NOT NULL,
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `idJurisdiccion` int(11) DEFAULT NULL,
  `codigo` varchar(50) DEFAULT NULL,
  `nombre` varchar(512) NOT NULL,
  `idJurisdiccionAux` int(11) DEFAULT NULL,
  PRIMARY KEY (`idObjetivoJurisdiccional`,`rev`),
  KEY `FK_hmcoafo8316ttqefi2qe5m7v7` (`rev`),
  CONSTRAINT `FK_hmcoafo8316ttqefi2qe5m7v7` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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

CREATE TABLE `objetivo_operativo_aud` (
  `idObjetivoOperativo` int(11) NOT NULL,
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `idObjetivoJurisdiccional` int(11) DEFAULT NULL,
  `codigo` varchar(50) DEFAULT NULL,
  `nombre` varchar(512) NOT NULL,
  `idObjetivoJurisdiccionalAux` int(11) DEFAULT NULL,
  PRIMARY KEY (`idObjetivoOperativo`,`rev`),
  KEY `FK_nefms1vc63q3gu3t5yedihpbw` (`rev`),
  CONSTRAINT `FK_nefms1vc63q3gu3t5yedihpbw` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
  `prioridad_jefatura` VARCHAR(5) NULL DEFAULT NULL,
  `total_presu_aprobado` DOUBLE NULL DEFAULT NULL,
  PRIMARY KEY (`idProyecto`),
  KEY `FK_idObjetivoOperativo_proyecto` (`idObjetivoOperativo`),
  CONSTRAINT `FK_idObjetivoOperativo_proyecto` FOREIGN KEY (`idObjetivoOperativo`) REFERENCES `objetivo_operativo` (`idObjetivoOperativo`),
  CONSTRAINT `FK_idArea_proyecto` FOREIGN KEY (`idArea`) REFERENCES `area` (`idArea`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=latin1;

CREATE TABLE `proyecto_aud` (
  `idProyecto` int(10) NOT NULL,
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
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
  `prioridad_jefatura` VARCHAR(5) NULL DEFAULT NULL,
  `total_presu_aprobado` DOUBLE NULL DEFAULT NULL,
  PRIMARY KEY (`idProyecto`,`rev`),
  KEY `FK_ihyp89k3rrxvy56ul53pqy6x3` (`rev`),
  CONSTRAINT `FK_ihyp89k3rrxvy56ul53pqy6x3` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping structure for table proyectos_ba_generated.archivos_proyecto
CREATE TABLE IF NOT EXISTS `archivos_proyecto` (
  `idArchivoProyecto` int(10) NOT NULL AUTO_INCREMENT,
  `proyecto_idproyecto` int(10) DEFAULT NULL COMMENT 'El id al proyecto al que pertence el archivo',
  `nombre` varchar(512) NOT NULL COMMENT 'El nombre del archivo',
  PRIMARY KEY (`idArchivoProyecto`),
  KEY `FK_archivos_proyecto_proyecto` (`proyecto_idproyecto`),
  CONSTRAINT `FK_archivos_proyecto_proyecto` FOREIGN KEY (`proyecto_idproyecto`) REFERENCES `proyecto` (`idProyecto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `archivos_proyecto_aud` (
  `idArchivoProyecto` int(10) NOT NULL,
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `proyecto_idproyecto` int(10) DEFAULT NULL,
  `nombre` varchar(512) NOT NULL,
  PRIMARY KEY (`idArchivoProyecto`,`rev`),
  KEY `FK_mdlsm0m0xyhqokf6r9n9q9l1t` (`rev`),
  CONSTRAINT `FK_mdlsm0m0xyhqokf6r9n9q9l1t` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping structure for table proyectos_ba_generated.area
CREATE TABLE IF NOT EXISTS `area` (
  `idArea` int(11) NOT NULL AUTO_INCREMENT,
  `idJurisdiccion` int(11) NOT NULL,
  `nombre` varchar(256) NOT NULL,
  PRIMARY KEY (`idArea`),
  KEY `FK__jurisdiccion` (`idJurisdiccion`),
  CONSTRAINT `FK__jurisdiccion` FOREIGN KEY (`idJurisdiccion`) REFERENCES `jurisdiccion` (`idJurisdiccion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Contiene las areas de cada jurisdiccion.';

CREATE TABLE `area_aud` (
  `idArea` int(11) NOT NULL,
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `idJurisdiccion` int(11) NOT NULL,
  `nombre` varchar(256) NOT NULL,
  PRIMARY KEY (`idArea`,`rev`),
  KEY `FK_b38jgo364yfdb4027m86to4pt` (`rev`),
  CONSTRAINT `FK_b38jgo364yfdb4027m86to4pt` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping structure for table `audit_revision_entity`
CREATE TABLE `audit_revision_entity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `usuario` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Dumping structure for table proyectos_ba_generated.comuna
CREATE TABLE IF NOT EXISTS `comuna` (
  `idComuna` int(11) NOT NULL AUTO_INCREMENT,
  `abreviatura` varchar(50) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`idComuna`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `comuna_aud` (
  `idComuna` int(11) NOT NULL,
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `abreviatura` varchar(50) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`idComuna`,`rev`),
  KEY `FK_4i8p6o9f2s08hcjkwcsbidhjl` (`rev`),
  CONSTRAINT `FK_4i8p6o9f2s08hcjkwcsbidhjl` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Dumping structure for table proyectos_ba_generated.comuna_por_proyecto
CREATE TABLE IF NOT EXISTS `comuna_por_proyecto` (
  `idProyecto` int(11) NOT NULL,
  `idComuna` int(11) NOT NULL,
  PRIMARY KEY (`idProyecto`,`idComuna`),
  KEY `FK_idUbicacionGeografica` (`idComuna`),
  CONSTRAINT `FK_idComuna` FOREIGN KEY (`idComuna`) REFERENCES `comuna` (`idComuna`),
  CONSTRAINT `FK_idProyecto_ubicacionGeografica` FOREIGN KEY (`idProyecto`) REFERENCES `proyecto` (`idProyecto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `comuna_por_proyecto_aud` (
  `idProyecto` int(11) NOT NULL,
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `idComuna` int(11) NOT NULL,
  PRIMARY KEY (`rev`,`idProyecto`,`idComuna`),
  KEY `FK_e3ec81kgxhgu0osapwnkjfhv9` (`rev`),
  CONSTRAINT `FK_e3ec81kgxhgu0osapwnkjfhv9` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Dumping structure for table proyectos_ba_generated.eje_de_gobierno
CREATE TABLE IF NOT EXISTS `eje_de_gobierno` (
  `idEjeDeGobierno` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(512) NOT NULL,
  `descripcion` varchar(512) NOT NULL,
  `ejemplos` varchar(512) NOT NULL,
  PRIMARY KEY (`idEjeDeGobierno`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `eje_de_gobierno_aud` (
  `idEjeDeGobierno` int(11) NOT NULL,
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `nombre` varchar(512) NOT NULL,
  `descripcion` varchar(512) NOT NULL,
  `ejemplos` varchar(512) NOT NULL,
  PRIMARY KEY (`idEjeDeGobierno`,`rev`),
  KEY `FK_8u6dhxpyxcyjtg38he123c9mh` (`rev`),
  CONSTRAINT `FK_8u6dhxpyxcyjtg38he123c9mh` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Dumping structure for table proyectos_ba_generated.eje_de_gobierno_por_proyecto
CREATE TABLE IF NOT EXISTS `eje_de_gobierno_por_proyecto` (
  `id_Proyecto` int(11) NOT NULL,
  `id_EjeDeGobierno` int(11) NOT NULL,
  PRIMARY KEY (`id_Proyecto`,`id_EjeDeGobierno`),
  KEY `FK_idEjeDeGobierno` (`id_EjeDeGobierno`),
  CONSTRAINT `FK_idEjeDeGobierno` FOREIGN KEY (`id_EjeDeGobierno`) REFERENCES `eje_de_gobierno` (`idEjeDeGobierno`),
  CONSTRAINT `FK_idProyecto_ejeDeGobierno` FOREIGN KEY (`id_Proyecto`) REFERENCES `proyecto` (`idProyecto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `eje_de_gobierno_por_proyecto_aud` (
  `id_Proyecto` int(11) NOT NULL,
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `id_EjeDeGobierno` int(11) NOT NULL,
  PRIMARY KEY (`rev`,`id_Proyecto`,`id_EjeDeGobierno`),
  KEY `FK_g294a4igmhtd8p0f2k1og9ffi` (`rev`),
  CONSTRAINT `FK_g294a4igmhtd8p0f2k1og9ffi` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


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

CREATE TABLE `indicador_estrategico_aud` (
  `idIndicadorEstrategico` int(11) NOT NULL,
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `idObjetivoJurisdiccional` int(11) DEFAULT NULL,
  `indicador` varchar(256) NOT NULL,
  `descripcion` varchar(512) NOT NULL,
  PRIMARY KEY (`idIndicadorEstrategico`,`rev`),
  KEY `FK_ju7phtvkpxw78iwddayc51dfo` (`rev`),
  CONSTRAINT `FK_ju7phtvkpxw78iwddayc51dfo` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Dumping structure for table proyectos_ba_generated.poblacion_meta
CREATE TABLE IF NOT EXISTS `poblacion_meta` (
  `idPoblacionMeta` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(512) NOT NULL,
  PRIMARY KEY (`idPoblacionMeta`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `poblacion_meta_aud` (
  `idPoblacionMeta` int(11) NOT NULL,
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `nombre` varchar(512) NOT NULL,
  PRIMARY KEY (`idPoblacionMeta`,`rev`),
  KEY `FK_2qs17lr5ptswdluw1ufhlo4vc` (`rev`),
  CONSTRAINT `FK_2qs17lr5ptswdluw1ufhlo4vc` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Dumping structure for table proyectos_ba_generated.poblacion_meta_por_proyecto
CREATE TABLE IF NOT EXISTS `poblacion_meta_por_proyecto` (
  `idProyecto` int(11) NOT NULL,
  `idPoblacionMeta` int(11) NOT NULL,
  PRIMARY KEY (`idProyecto`,`idPoblacionMeta`),
  KEY `FK_idPoblacionMeta` (`idPoblacionMeta`),
  CONSTRAINT `FK_idPoblacionMeta` FOREIGN KEY (`idPoblacionMeta`) REFERENCES `poblacion_meta` (`idPoblacionMeta`),
  CONSTRAINT `FK_idProyecto_poblacionMeta` FOREIGN KEY (`idProyecto`) REFERENCES `proyecto` (`idProyecto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `poblacion_meta_por_proyecto_aud` (
  `idProyecto` int(11) NOT NULL,
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `idPoblacionMeta` int(11) NOT NULL,
  PRIMARY KEY (`rev`,`idProyecto`,`idPoblacionMeta`),
  KEY `FK_uq7vkhijajrub08lb97b5712` (`rev`),
  CONSTRAINT `FK_uq7vkhijajrub08lb97b5712` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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

CREATE TABLE `presupuesto_por_anio_aud` (
  `idPresupuestoPoranio` int(11) NOT NULL,
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `idProyecto` int(11) DEFAULT NULL,
  `anio` smallint(6) NOT NULL,
  `presupuesto` double DEFAULT NULL,
  `otrasFuentes` double DEFAULT NULL,
  PRIMARY KEY (`idPresupuestoPoranio`,`rev`),
  KEY `FK_1vemcwr8jyc45dvd38rfhhk3f` (`rev`),
  CONSTRAINT `FK_1vemcwr8jyc45dvd38rfhhk3f` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



-- Dumping structure for table proyectos_ba_generated.rol
CREATE TABLE IF NOT EXISTS `rol` (
  `idRol` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `descripcion` varchar(100) NOT NULL,
  PRIMARY KEY (`idRol`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `rol_aud` (
  `idRol` int(11) NOT NULL,
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `nombre` varchar(50) NOT NULL,
  `descripcion` varchar(100) NOT NULL,
  PRIMARY KEY (`idRol`,`rev`),
  KEY `FK_o5ol35v1ic6s4esg1vooerqql` (`rev`),
  CONSTRAINT `FK_o5ol35v1ic6s4esg1vooerqql` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- Dumping structure for table proyectos_ba_generated.rol_por_usuario
CREATE TABLE IF NOT EXISTS `rol_por_usuario` (
  `rol_idRol` int(11) NOT NULL,
  `usuario_idUsuario` int(11) NOT NULL,
  PRIMARY KEY (`rol_idRol`,`usuario_idUsuario`),
  KEY `usuario_fk` (`usuario_idUsuario`),
  CONSTRAINT `rol_fk` FOREIGN KEY (`rol_idRol`) REFERENCES `rol` (`idRol`),
  CONSTRAINT `usuario_fk` FOREIGN KEY (`usuario_idUsuario`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `rol_por_usuario_aud` (
  `rol_idRol` int(11) NOT NULL,
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `usuario_idUsuario` int(11) NOT NULL,
  PRIMARY KEY (`rev`,`rol_idRol`,`usuario_idUsuario`),
  KEY `FK_j02bipsm5qs2o7ns0ec3n457g` (`rev`),
  CONSTRAINT `FK_j02bipsm5qs2o7ns0ec3n457g` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Dumping structure for table proyectos_ba_generated.tema_transversal
CREATE TABLE IF NOT EXISTS `tema_transversal` (
  `idTemaTransversal` int(11) NOT NULL AUTO_INCREMENT,
  `temaTransversal` varchar(512) NOT NULL,
  `activo` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`idTemaTransversal`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tema_transversal_aud` (
  `idTemaTransversal` int(11) NOT NULL,
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `temaTransversal` varchar(512) NOT NULL,
  `activo` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`idTemaTransversal`,`rev`),
  KEY `FK_2py8o4qg5ip5sk2d9cuuvm1mn` (`rev`),
  CONSTRAINT `FK_2py8o4qg5ip5sk2d9cuuvm1mn` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Dumping structure for table proyectos_ba_generated.tema_transversal_por_proyecto
CREATE TABLE IF NOT EXISTS `tema_transversal_por_proyecto` (
  `idProyecto` int(11) NOT NULL,
  `idTemaTransversal` int(11) NOT NULL,
  PRIMARY KEY (`idProyecto`,`idTemaTransversal`),
  KEY `FK_temaTransversal` (`idTemaTransversal`),
  CONSTRAINT `FK_temaTransversal` FOREIGN KEY (`idTemaTransversal`) REFERENCES `tema_transversal` (`idTemaTransversal`),
  CONSTRAINT `FK_temaTrasnversal_proyecto` FOREIGN KEY (`idProyecto`) REFERENCES `proyecto` (`idProyecto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tema_transversal_por_proyecto_aud` (
  `idProyecto` int(11) NOT NULL,
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `idTemaTransversal` int(11) NOT NULL,
  PRIMARY KEY (`rev`,`idProyecto`,`idTemaTransversal`),
  KEY `FK_494gjfiahjvxavnlnnaqbjnc5` (`rev`),
  CONSTRAINT `FK_494gjfiahjvxavnlnnaqbjnc5` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Dumping structure for table proyectos_ba_generated.compromiso_publico
CREATE TABLE IF NOT EXISTS `compromiso_publico` (
  `idCompromisoPublico` int(11) NOT NULL AUTO_INCREMENT,
  `compromisoPublico` varchar(50) NOT NULL,
  `activo` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`idCompromisoPublico`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `compromiso_publico_aud` (
  `idCompromisoPublico` int(11) NOT NULL,
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `compromisoPublico` varchar(50) NOT NULL,
  `activo` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`idCompromisoPublico`,`rev`),
  KEY `FK_l9eek599y2b1sqd40mndagkv5` (`rev`),
  CONSTRAINT `FK_l9eek599y2b1sqd40mndagkv5` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Dumping structure for table proyectos_ba_generated.compromiso_publico_por_proyecto
CREATE TABLE IF NOT EXISTS `compromiso_publico_por_proyecto` (
  `idProyecto` int(11) NOT NULL,
  `idCompromisoPublico` int(11) NOT NULL,
  PRIMARY KEY (`idProyecto`,`idCompromisoPublico`),
  KEY `FK_compromisoPublico` (`idCompromisoPublico`),
  CONSTRAINT `FK_compromisoPublico` FOREIGN KEY (`idCompromisoPublico`) REFERENCES `compromiso_publico` (`idCompromisoPublico`),
  CONSTRAINT `FK_compromisoPublico_proyecto` FOREIGN KEY (`idProyecto`) REFERENCES `proyecto` (`idProyecto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `compromiso_publico_por_proyecto_aud` (
  `idProyecto` int(11) NOT NULL,
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `idCompromisoPublico` int(11) NOT NULL,
  PRIMARY KEY (`rev`,`idProyecto`,`idCompromisoPublico`),
  KEY `FK_clxn2o0y4glufj9dn0d46np7s` (`rev`),
  CONSTRAINT `FK_clxn2o0y4glufj9dn0d46np7s` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Dumping structure for table proyectos_ba_generated.otras_etiquetas
CREATE TABLE IF NOT EXISTS `otras_etiquetas` (
  `idEtiqueta` int(11) NOT NULL AUTO_INCREMENT,
  `etiqueta` varchar(512) NOT NULL,
  PRIMARY KEY (`idEtiqueta`),
  UNIQUE KEY `etiqueta` (`etiqueta`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `otras_etiquetas_aud` (
  `idEtiqueta` int(11) NOT NULL,
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `etiqueta` varchar(512) NOT NULL,
  PRIMARY KEY (`idEtiqueta`,`rev`),
  KEY `FK_4f3pdrrbanah7sm17cpkmpjl6` (`rev`),
  CONSTRAINT `FK_4f3pdrrbanah7sm17cpkmpjl6` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Dumping structure for table proyectos_ba_generated.otras_etiquetas_por_proyecto
CREATE TABLE IF NOT EXISTS `otras_etiquetas_por_proyecto` (
  `idEtiqueta` int(11) NOT NULL,
  `idProyecto` int(11) NOT NULL,
  PRIMARY KEY (`idEtiqueta`,`idProyecto`),
  KEY `fk_etiqueta_proyecto` (`idProyecto`),
  CONSTRAINT `FK_etiquetas` FOREIGN KEY (`idEtiqueta`) REFERENCES `otras_etiquetas` (`idEtiqueta`),
  CONSTRAINT `fk_etiqueta_proyecto` FOREIGN KEY (`idProyecto`) REFERENCES `proyecto` (`idProyecto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `otras_etiquetas_por_proyecto_aud` (
  `idEtiqueta` int(11) NOT NULL,
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `idProyecto` int(11) NOT NULL,
  PRIMARY KEY (`rev`,`idProyecto`,`idEtiqueta`),
  KEY `FK_qmu3w4rcoy4s6pny1cyypxyac` (`rev`),
  CONSTRAINT `FK_qmu3w4rcoy4s6pny1cyypxyac` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


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

CREATE TABLE `usuario_aud` (
  `idUsuario` int(11) NOT NULL,
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  `apellido` varchar(50) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  `activo` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`idUsuario`,`rev`),
  KEY `FK_fss773c20l201t8v2jlf59dse` (`rev`),
  CONSTRAINT `FK_fss773c20l201t8v2jlf59dse` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Dumping structure for table proyectos_ba_generated.usuario_por_jurisdiccion
CREATE TABLE IF NOT EXISTS `usuario_por_jurisdiccion` (
  `jurisdiccion_idJurisdiccion` int(11) NOT NULL,
  `usuario_idUsuario` int(11) NOT NULL,
  PRIMARY KEY (`jurisdiccion_idJurisdiccion`,`usuario_idUsuario`),
  KEY `FK_usuario` (`usuario_idUsuario`),
  CONSTRAINT `FK_usuario` FOREIGN KEY (`usuario_idUsuario`) REFERENCES `usuario` (`idUsuario`),
  CONSTRAINT `FK_usuario_jurisdiccion` FOREIGN KEY (`jurisdiccion_idJurisdiccion`) REFERENCES `jurisdiccion` (`idJurisdiccion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `usuario_por_jurisdiccion_aud` (
  `jurisdiccion_idJurisdiccion` int(11) NOT NULL,
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `usuario_idUsuario` int(11) NOT NULL,
  PRIMARY KEY (`rev`,`usuario_idUsuario`,`jurisdiccion_idJurisdiccion`),
  KEY `FK_5bytavxpxhid3e4uf38eqnjk6` (`rev`),
  CONSTRAINT `FK_5bytavxpxhid3e4uf38eqnjk6` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
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

CREATE TABLE `permiso_entidad_aud` (
  `idPermisoEntidad` int(11) NOT NULL,
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `alta` tinyint(1) DEFAULT NULL,
  `idRol` int(11) DEFAULT NULL,
  `baja` tinyint(1) DEFAULT NULL,
  `modificacion` tinyint(1) DEFAULT NULL,
  `nombre` varchar(50) NOT NULL,
  `gestion` tinyint(4) NOT NULL,
  PRIMARY KEY (`idPermisoEntidad`,`rev`),
  KEY `FK_cfc9rhy6hjigvauvrmhh9gapp` (`rev`),
  CONSTRAINT `FK_cfc9rhy6hjigvauvrmhh9gapp` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Dumping structure for table proyectos_ba_generated.error_descripcion
CREATE TABLE IF NOT EXISTS `error_descripcion` (
  `codigo_error` int(11) NOT NULL,
  `descripcion_error` varchar(50) NOT NULL,
  PRIMARY KEY (`codigo_error`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
