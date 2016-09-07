-- --------------------------------------------------------
-- Host:                         10.140.150.154
-- Server version:               10.1.14-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping structure for view proyectos_ba_generated.exportacion_proyectos_view
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `exportacion_proyectos_view` (
  `idProyecto` INT(10) NOT NULL,
  `nombreJurisidiccion` VARCHAR(512) NOT NULL COLLATE 'latin1_swedish_ci',
  `nombreProyecto` VARCHAR(512) NOT NULL COLLATE 'latin1_swedish_ci',
  `estado` VARCHAR(50) NULL COLLATE 'latin1_swedish_ci',
  `objetivoEstrategico` VARCHAR(512) NOT NULL COLLATE 'latin1_swedish_ci',
  `objetivoOperativo` VARCHAR(512) NOT NULL COLLATE 'latin1_swedish_ci',
  `descripcionProyecto` TEXT NULL COLLATE 'latin1_swedish_ci',
  `responsable` VARCHAR(512) NULL COLLATE 'latin1_swedish_ci',
  `area` VARCHAR(256) NULL COLLATE 'latin1_swedish_ci',
  `organismosCorresponsables` VARCHAR(512) NULL COLLATE 'latin1_swedish_ci',
  `fechaInicio` DATE NOT NULL,
  `fechaFin` DATE NULL,
  `tipoProyecto` VARCHAR(11) NULL COLLATE 'latin1_swedish_ci',
  `implicaCambioLegislativo` TINYINT(4) NULL,
  `prioridadJurisdiccional` VARCHAR(50) NULL COLLATE 'latin1_swedish_ci',
  `meta` DECIMAL(22,2) NULL,
  `unidadMeta` VARCHAR(512) NULL COLLATE 'latin1_swedish_ci',
  `cantidadPoblacionImpactada` BIGINT(20) NULL,
  `prioridadJefatura` BINARY(0) NULL,
  `estadoAprobacion` BINARY(0) NULL
) ENGINE=MyISAM;


-- Dumping structure for view proyectos_ba_generated.exportacion_proyectos_view
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `exportacion_proyectos_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`admin`@`localhost` VIEW `exportacion_proyectos_view` AS SELECT DISTINCT p.idProyecto "idProyecto", j.nombre "nombreJurisidiccion", p.nombre "nombreProyecto", 
        p.estado, oj.nombre "objetivoEstrategico", oop.nombre "objetivoOperativo", p.descripcion "descripcionProyecto", p.liderProyecto "responsable",
         a.nombre "area", p.organismosCorresponsables "organismosCorresponsables", p.fechaInicio "fechaInicio",
        p.fechaFin "fechaFin", p.tipoProyecto "tipoProyecto", p.cambioLegislativo "implicaCambioLegislativo",
         p.prioridadJurisdiccional "prioridadJurisdiccional", p.meta "meta", p.unidadMeta "unidadMeta", p.poblacionAfectada "cantidadPoblacionImpactada",
         NULL "prioridadJefatura",  NULL "estadoAprobacion"
FROM jurisdiccion j
INNER JOIN objetivo_jurisdiccional oj ON oj.idJurisdiccion = j.idJurisdiccion
INNER JOIN objetivo_operativo oop ON oop.idObjetivoOperativo = oj.idObjetivoJurisdiccional
INNER JOIN proyecto p ON p.idObjetivoOperativo = oop.idObjetivoOperativo
LEFT JOIN area a on a.idArea = p.idArea
where p.estado in ("verificado", "presentado")
order by 1,2,3,4,5,6 ;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;