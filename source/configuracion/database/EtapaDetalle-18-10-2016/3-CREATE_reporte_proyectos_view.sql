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

-- Dumping structure for view proyectos_ba_generated.reporte_proyectos_view
-- Removing temporary table and create final VIEW structure
DROP VIEW IF EXISTS `reporte_proyectos_view`;
CREATE VIEW `reporte_proyectos_view` AS SELECT DISTINCT p.idProyecto "idProyecto", j.nombre "nombreJurisidiccion", p.nombre "nombreProyecto", 
			  p.estado, oj.nombre "objetivoEstrategico", oop.nombre "objetivoOperativo", p.descripcion "descripcionProyecto", p.liderProyecto "responsable",
			   a.nombre "area", p.organismosCorresponsables "organismosCorresponsables", p.fechaInicio "fechaInicio",
			  p.fechaFin "fechaFin", p.tipoProyecto "tipoProyecto", p.cambioLegislativo "implicaCambioLegislativo",
			   p.prioridadJurisdiccional "prioridadJurisdiccional", p.meta "meta", p.unidadMeta "unidadMeta", p.poblacionAfectada "cantidadPoblacionImpactada",
			   p.prioridad_jefatura "prioridadJefatura", p.total_presu_aprobado "presupuestoAprobado", oop.idObjetivoOperativo
FROM jurisdiccion j
INNER JOIN objetivo_jurisdiccional oj ON oj.idJurisdiccion = j.idJurisdiccion
INNER JOIN objetivo_operativo oop ON oop.idObjetivoJurisdiccional = oj.idObjetivoJurisdiccional
INNER JOIN proyecto p ON p.idObjetivoOperativo = oop.idObjetivoOperativo
LEFT JOIN area a on a.idArea = p.idArea
ORDER BY 1,2,3,4,5,6 ;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
