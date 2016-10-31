-- se corre el ID de los indicadores estrategicos.
UPDATE indicador_estrategico SET idIndicadorEstrategico = idIndicadorEstrategico+100000;
-- se corre el ID de los compromisos publicos.
UPDATE compromiso_publico SET idCompromisoPublico = idCompromisoPublico+100000;

CREATE TABLE `presupuesto_proyecto_por_mes` (
  `idPresupuestoProyectoPorMes` int(11) NOT NULL AUTO_INCREMENT,
  `idProyecto` int(11) NOT NULL,
  `idProyectoAux` int(11) NOT NULL,
  `mes` tinyint(4) NOT NULL,
  `presupuesto` double NOT NULL,
  `anio` smallint(6) NOT NULL,
  PRIMARY KEY (`idPresupuestoProyectoPorMes`),
  KEY `presupuesto_proyecto_por_mes_proyecto_FK` (`idProyecto`),
  CONSTRAINT `presupuesto_proyecto_por_mes_proyecto_FK` FOREIGN KEY (`idProyecto`) REFERENCES `proyecto` (`idProyecto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `presupuesto_proyecto_por_mes_aud` (
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `idPresupuestoProyectoPorMes` varchar(100) NOT NULL,
  `idProyecto` int(11) NOT NULL,
  `idProyectoAux` int(11) NOT NULL,
  `mes` tinyint(4) NOT NULL,
  `presupuesto` double DEFAULT NULL,
  `anio` smallint(6) NOT NULL,
  PRIMARY KEY (`idPresupuestoProyectoPorMes`,`rev`),
  KEY `FK_presupuesto_proyecto_por_mes_aud` (`rev`),
  CONSTRAINT `FK_presupuesto_proyecto_por_mes_aud` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `medicion_historica_indicador` (
  `idMedicionHistoricaIndicador` int(11) NOT NULL AUTO_INCREMENT,
  `anio` smallint(6) NOT NULL,
  `idIndicadorEstrategico` int(11) NOT NULL,
  `medicion` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idMedicionHistoricaIndicador`),
  KEY `medicion_historica_indicador_indicador_estrategico_FK` (`idIndicadorEstrategico`),
  CONSTRAINT `medicion_historica_indicador_indicador_estrategico_FK` FOREIGN KEY (`idIndicadorEstrategico`) REFERENCES `indicador_estrategico` (`idIndicadorEstrategico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `medicion_historica_indicador_aud` (
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `idMedicionHistoricaIndicador` int(11) NOT NULL,
  `anio` smallint(6) NOT NULL,
  `idIndicadorEstrategico` int(11) NOT NULL,
  `medicion` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idMedicionHistoricaIndicador`,`rev`),
  KEY `FK_medicion_historica_indicador_aud` (`rev`),
  CONSTRAINT `FK_medicion_historica_indicador_aud` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `meta_indicador` (
  `idMetaIndicador` int(11) NOT NULL AUTO_INCREMENT,
  `idIndicadorEstrategico` int(11) NOT NULL,
  `anio` smallint(6) NOT NULL,
  `meta` varchar(100) DEFAULT NULL COMMENT 'Hay que revisar el formato. Debe aceptar: entero, decimal, porcentaje y fracciÃ³n (2/500)',
  `origen` varchar(100) DEFAULT NULL,
  `referente` varchar(512) DEFAULT NULL,
  `metodoRecoleccion` varchar(100) DEFAULT NULL,
  `sistemaRecoleccion` varchar(100) DEFAULT NULL,
  `pesoRelativo` tinyint(4) DEFAULT NULL,
  `justificacionParametroInternacional` smallint(6) DEFAULT NULL,
  `justificacionParametroNacional` smallint(6) DEFAULT NULL,
  `justificacionResultadosHistoricos` smallint(6) DEFAULT NULL,
  `justificacionPresupuesto` smallint(6) DEFAULT NULL,
  `justificacionInstituciones` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`idMetaIndicador`),
  KEY `meta_indicador_indicador_estrategico_FK` (`idIndicadorEstrategico`),
  CONSTRAINT `meta_indicador_indicador_estrategico_FK` FOREIGN KEY (`idIndicadorEstrategico`) REFERENCES `indicador_estrategico` (`idIndicadorEstrategico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `meta_indicador_aud` (
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `idMetaIndicador` int(11) NOT NULL,
  `idIndicadorEstrategico` int(11) NOT NULL,
  `anio` smallint(6) NOT NULL,
  `meta` varchar(100) DEFAULT NULL,
  `origen` varchar(100) DEFAULT NULL,
  `referente` varchar(512) DEFAULT NULL,
  `metodoRecoleccion` varchar(100) DEFAULT NULL,
  `sistemaRecoleccion` varchar(100) DEFAULT NULL,
  `pesoRelativo` tinyint(4) DEFAULT NULL,
  `justificacionParametroInternacional` smallint(6) DEFAULT NULL,
  `justificacionParametroNacional` smallint(6) DEFAULT NULL,
  `justificacionResultadosHistoricos` smallint(6) DEFAULT NULL,
  `justificacionPresupuesto` smallint(6) DEFAULT NULL,
  `justificacionInstituciones` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`idMetaIndicador`,`rev`),
  KEY `FK_meta_indicador_aud` (`rev`),
  CONSTRAINT `FK_meta_indicador_aud` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `tipo_obra` (
  `idTipoObra` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`idTipoObra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `tipo_obra_aud` (
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `idTipoObra` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`idTipoObra`,`rev`),
  KEY `FK_tipo_obra_aud` (`rev`),
  CONSTRAINT `FK_tipo_obra_aud` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `subtipo_obra` (
  `idSubtipoObra` int(11) NOT NULL AUTO_INCREMENT,
  `idTipoObra` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`idSubtipoObra`),
  KEY `subtipo_obra_tipo_obra_FK` (`idTipoObra`),
  CONSTRAINT `subtipo_obra_tipo_obra_FK` FOREIGN KEY (`idTipoObra`) REFERENCES `tipo_obra` (`idTipoObra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `subtipo_obra_aud` (
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `idSubtipoObra` int(11) NOT NULL,
  `idTipoObra` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`idSubtipoObra`,`rev`),
  KEY `FK_subtipo_obra_aud` (`rev`),
  CONSTRAINT `FK_subtipo_obra_aud` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `obra` (
  `idObra` int(11) NOT NULL AUTO_INCREMENT,
  `idProyecto` int(11) NOT NULL,
  `estado` varchar(50) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  `idSubtipoObra` int(11) DEFAULT NULL,
  `idSubtipoObraAux` int(11) DEFAULT NULL,
  `referenteEjecucion` varchar(512) DEFAULT NULL,
  `presupuestoTotal` double DEFAULT NULL,
  `tipoUbicacion` varchar(50) DEFAULT NULL,
  `direccion` varchar(512) DEFAULT NULL COMMENT 'si direccion es Punto',
  `direccionDesde` varchar(512) DEFAULT NULL COMMENT 'si direccion es Tramo',
  `direccionHasta` varchar(512) DEFAULT NULL COMMENT 'si direccion es Tramo',
  `detalleUbicacion` varchar(100) DEFAULT NULL COMMENT 'piso/departamento/otro',
  `usig_seccion` varchar(100) DEFAULT NULL,
  `usig_manzana` varchar(100) DEFAULT NULL,
  `usig_parcela` varchar(100) DEFAULT NULL,
  `usig_barrio` varchar(100) DEFAULT NULL,
  `usig_UTIU` varchar(100) DEFAULT NULL COMMENT 'unidad terrritorial de inclusion urbana',
  `usig_distritoEscolar` varchar(100) DEFAULT NULL,
  `usig_areaHospitalaria` varchar(100) DEFAULT NULL,
  `usig_comisaria` varchar(100) DEFAULT NULL,
  `usig_transporteCercano` varchar(100) DEFAULT NULL,
  `usig_CPU` varchar(100) DEFAULT NULL COMMENT 'codigo de planeamiento urbano',
  `idComuna` int(11) DEFAULT NULL,
  `prioridadJefatura` varchar(100) DEFAULT NULL,
  `informacionRelevamiento` text,
  `publicableTableroCiudadano` tinyint(4) DEFAULT NULL,
  `direccionUnidad` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`idObra`),
  KEY `obra_proyecto_FK` (`idProyecto`),
  KEY `obra_subtipo_obra_FK` (`idSubtipoObra`),
  KEY `obra_comuna_FK` (`idComuna`),
  CONSTRAINT `obra_comuna_FK` FOREIGN KEY (`idComuna`) REFERENCES `comuna` (`idComuna`),
  CONSTRAINT `obra_proyecto_FK` FOREIGN KEY (`idProyecto`) REFERENCES `proyecto` (`idProyecto`),
  CONSTRAINT `obra_subtipo_obra_FK` FOREIGN KEY (`idSubtipoObra`) REFERENCES `subtipo_obra` (`idSubtipoObra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `obra_aud` (
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `idObra` int(11) NOT NULL,
  `idProyecto` int(11) NOT NULL,
  `estado` varchar(50) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  `idSubtipoObra` int(11) DEFAULT NULL,
  `idSubtipoObraAux` int(11) DEFAULT NULL,
  `referenteEjecucion` varchar(512) DEFAULT NULL,
  `presupuestoTotal` double DEFAULT NULL,
  `tipoUbicacion` varchar(50) DEFAULT NULL,
  `direccion` varchar(512) DEFAULT NULL COMMENT 'si direccion es Punto',
  `direccionDesde` varchar(512) DEFAULT NULL COMMENT 'si direccion es Tramo',
  `direccionHasta` varchar(512) DEFAULT NULL COMMENT 'si direccion es Tramo',
  `detalleUbicacion` varchar(100) DEFAULT NULL COMMENT 'piso/departamento/otro',
  `usig_seccion` varchar(100) DEFAULT NULL,
  `usig_manzana` varchar(100) DEFAULT NULL,
  `usig_parcela` varchar(100) DEFAULT NULL,
  `usig_barrio` varchar(100) DEFAULT NULL,
  `usig_UTIU` varchar(100) DEFAULT NULL COMMENT 'unidad terrritorial de inclusion urbana',
  `usig_distritoEscolar` varchar(100) DEFAULT NULL,
  `usig_areaHospitalaria` varchar(100) DEFAULT NULL,
  `usig_comisaria` varchar(100) DEFAULT NULL,
  `usig_transporteCercano` varchar(100) DEFAULT NULL,
  `usig_CPU` varchar(100) DEFAULT NULL COMMENT 'codigo de planeamiento urbano',
  `idComuna` int(11) DEFAULT NULL,
  `prioridadJefatura` varchar(100) DEFAULT NULL,
  `informacionRelevamiento` text,
  `publicableTableroCiudadano` tinyint(4) DEFAULT NULL,
  `direccionUnidad` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`idObra`,`rev`),
  KEY `FK_obra_aud` (`rev`),
  CONSTRAINT `FK_obra_aud` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `presupuesto_obra_por_anio` (
  `idPresupuestoObraPorAnio` int(11) NOT NULL AUTO_INCREMENT,
  `anio` smallint(6) NOT NULL,
  `idObra` int(11) NOT NULL,
  `presupuesto` double DEFAULT NULL,
  PRIMARY KEY (`idPresupuestoObraPorAnio`),
  KEY `presupuesto_obra_por_anio_obra_FK` (`idObra`),
  CONSTRAINT `presupuesto_obra_por_anio_obra_FK` FOREIGN KEY (`idObra`) REFERENCES `obra` (`idObra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `presupuesto_obra_por_anio_aud` (
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `idPresupuestoObraPorAnio` int(11) NOT NULL,
  `anio` smallint(6) NOT NULL,
  `idObra` int(11) NOT NULL,
  `presupuesto` double DEFAULT NULL,
  PRIMARY KEY (`idPresupuestoObraPorAnio`,`rev`),
  KEY `FK_presupuesto_obra_por_anio_aud` (`rev`),
  CONSTRAINT `FK_presupuesto_obra_por_anio_aud` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `archivos_obra` (
  `idArchivoObra` int(11) NOT NULL AUTO_INCREMENT,
  `idObra` int(11) NOT NULL,
  `nombre` varchar(512) NOT NULL,
  `descripcion` varchar(512) DEFAULT NULL,
  `fuente` varchar(512) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  PRIMARY KEY (`idArchivoObra`),
  KEY `archivos_obra_obra_FK` (`idObra`),
  CONSTRAINT `archivos_obra_obra_FK` FOREIGN KEY (`idObra`) REFERENCES `obra` (`idObra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `archivos_obra_aud` (
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `idArchivoObra` int(11) NOT NULL,
  `idObra` int(11) NOT NULL,
  `nombre` varchar(512) NOT NULL,
  `descripcion` varchar(512) DEFAULT NULL,
  `fuente` varchar(512) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  PRIMARY KEY (`idArchivoObra`,`rev`),
  KEY `FK_archivos_obra_aud` (`rev`),
  CONSTRAINT `FK_archivos_obra_aud` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `expediente_obra` (
  `idExpediente`int(11) NOT NULL AUTO_INCREMENT,
  `idObra` int(11) NOT NULL,
  `descripcion` varchar(512) DEFAULT NULL,
  `sigla` varchar(20) DEFAULT NULL,
  `numero` int(11) DEFAULT NULL,
  `anio` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`idExpediente`),
  KEY `expediente_obra_obra_FK` (`idObra`),
  CONSTRAINT `expediente_obra_obra_FK` FOREIGN KEY (`idObra`) REFERENCES `obra` (`idObra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `expediente_obra_aud` (
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `idExpediente` int(11) NOT NULL,
  `idObra` int(11) DEFAULT NULL,
  `descripcion` varchar(512) DEFAULT NULL,
  `sigla` varchar(20) DEFAULT NULL,
  `numero` int(11) DEFAULT NULL,
  `anio` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`idExpediente`,`rev`),
  KEY `FK_expediente_obra_aud` (`rev`),
  CONSTRAINT `FK_expediente_obra_aud` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `hito_obra` (
  `idHito` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(512) NOT NULL,
  `fechaInicio` date DEFAULT NULL,
  `fechaFin` date DEFAULT NULL,
  `estado` varchar(50) NOT NULL,
  `esImportante` tinyint(4) DEFAULT NULL,
  `idObra` int(11) NOT NULL COMMENT 'idObra',
  PRIMARY KEY (`idHito`),
  KEY `hito_obra_obra_FK` (`idObra`),
  CONSTRAINT `hito_obra_obra_FK` FOREIGN KEY (`idObra`) REFERENCES `obra` (`idObra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `hito_obra_aud` (
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `idHito` int(11) NOT NULL,
  `nombre` varchar(512) NOT NULL,
  `fechaInicio` date DEFAULT NULL,
  `fechaFin` date DEFAULT NULL,
  `estado` varchar(50) NOT NULL,
  `esImportante` tinyint(4) DEFAULT NULL,
  `idObra` int(11) NOT NULL,
  PRIMARY KEY (`idHito`,`rev`),
  KEY `FK_hito_obra_aud` (`rev`),
  CONSTRAINT `FK_hito_obra_aud` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `hito_proyecto` (
  `idHito` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(512) NOT NULL,
  `fechaInicio` date DEFAULT NULL,
  `fechaFin` date DEFAULT NULL,
  `idProyecto` int(11) NOT NULL,
  `estado` varchar(50) NOT NULL,
  `esImportante` tinyint(4) DEFAULT NULL,
  `idHitoPredecesor` int(11) DEFAULT NULL COMMENT 'idHito',
  `idPadre` int(11) DEFAULT NULL COMMENT 'idHito',
  `presupuesto` double DEFAULT NULL,
  PRIMARY KEY (`idHito`),
  KEY `hito_proyecto_FK` (`idProyecto`),
  KEY `hito_hito_FK` (`idHitoPredecesor`),
  KEY `hito_proyecto_hito_proyecto_FK` (`idPadre`),
  CONSTRAINT `hito_hito_FK` FOREIGN KEY (`idHitoPredecesor`) REFERENCES `hito_proyecto` (`idHito`),
  CONSTRAINT `hito_proyecto_FK` FOREIGN KEY (`idProyecto`) REFERENCES `proyecto` (`idProyecto`),
  CONSTRAINT `hito_proyecto_hito_proyecto_FK` FOREIGN KEY (`idPadre`) REFERENCES `hito_proyecto` (`idHito`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `hito_proyecto_aud` (
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `idHito` int(11) NOT NULL,
  `nombre` varchar(512) NOT NULL,
  `fechaInicio` date DEFAULT NULL,
  `fechaFin` date DEFAULT NULL,
  `idProyecto` int(11) NOT NULL,
  `estado` varchar(50) NOT NULL,
  `esImportante` tinyint(4) DEFAULT NULL,
  `idHitoPredecesor` int(11) DEFAULT NULL,
  `idPadre` int(11) DEFAULT NULL,
  `presupuesto` double DEFAULT NULL,
  PRIMARY KEY (`idHito`,`rev`),
  KEY `FK_hito_proyecto_aud` (`rev`),
  CONSTRAINT `FK_hito_proyecto_aud` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `proyecto_por_indicador_estrategico` (
  `idProyectoPorIndicadorEstrategico` int(11) NOT NULL AUTO_INCREMENT,
  `idProyecto` int(11) NOT NULL,
  `idIndicadorEstrategico` int(11) NOT NULL,
  PRIMARY KEY (`idProyectoPorIndicadorEstrategico`),
  KEY `proyecto_por_indicador_estrategico_proyecto_FK` (`idProyecto`),
  KEY `proyecto_por_indicador_estrategico_indicador_estrategico_FK` (`idIndicadorEstrategico`),
  CONSTRAINT `proyecto_por_indicador_estrategico_indicador_estrategico_FK` FOREIGN KEY (`idIndicadorEstrategico`) REFERENCES `indicador_estrategico` (`idIndicadorEstrategico`),
  CONSTRAINT `proyecto_por_indicador_estrategico_proyecto_FK` FOREIGN KEY (`idProyecto`) REFERENCES `proyecto` (`idProyecto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `proyecto_por_indicador_estrategico_aud` (
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `idProyectoPorIndicadorEstrategico` int(11) NOT NULL,
  `idProyecto` int(11) NOT NULL,
  `idIndicadorEstrategico` int(11) NOT NULL,
  PRIMARY KEY (`rev`,`idProyectoPorIndicadorEstrategico`,`idProyecto`, `idIndicadorEstrategico` ),
  KEY `FK_idIndicadorEstrategico` (`rev`),
  CONSTRAINT `FK_idIndicadorEstrategico` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `indicador_semaforo` (
  `idIndicadorSemaforo` int(11) NOT NULL AUTO_INCREMENT,
  `idIndicadorEstrategico` int(11) NOT NULL,
  `anio` smallint(6) NOT NULL,
  `mes` tinyint(4) NOT NULL,
  `valor` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idIndicadorSemaforo`),
  KEY `indicador_semaforo_indicador_estrategico_FK` (`idIndicadorEstrategico`),
  CONSTRAINT `indicador_semaforo_indicador_estrategico_FK` FOREIGN KEY (`idIndicadorEstrategico`) REFERENCES `indicador_estrategico` (`idIndicadorEstrategico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `indicador_semaforo_aud` (
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `idIndicadorSemaforo` int(11) NOT NULL,
  `idIndicadorEstrategico` int(11) NOT NULL,
  `anio` smallint(6) NOT NULL,
  `mes` tinyint(4) NOT NULL,
  `valor` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idIndicadorSemaforo`,`rev`),
  KEY `FK_indicador_semaforo_aud` (`rev`),
  CONSTRAINT `FK_indicador_semaforo_aud` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


/*ALTERS!*/
ALTER TABLE indicador_estrategico ADD `metodoCalculo` varchar(512) DEFAULT NULL;
ALTER TABLE indicador_estrategico_aud ADD `metodoCalculo` varchar(512) DEFAULT NULL;
ALTER TABLE indicador_estrategico ADD `tipoIndicador` varchar(100) DEFAULT NULL;
ALTER TABLE indicador_estrategico_aud ADD `tipoIndicador` varchar(100) DEFAULT NULL;
ALTER TABLE indicador_estrategico ADD `sentido` varchar(100) DEFAULT NULL;
ALTER TABLE indicador_estrategico_aud ADD `sentido` varchar(100) DEFAULT NULL;
ALTER TABLE indicador_estrategico ADD `frecuencia` varchar(100) DEFAULT NULL;
ALTER TABLE indicador_estrategico_aud ADD `frecuencia` varchar(100) DEFAULT NULL;
ALTER TABLE indicador_estrategico ADD `formatoNumero` varchar(100) DEFAULT NULL;
ALTER TABLE indicador_estrategico_aud ADD `formatoNumero` varchar(100) DEFAULT NULL;
ALTER TABLE indicador_estrategico ADD `unidadDeMedida` varchar(512) DEFAULT NULL;
ALTER TABLE indicador_estrategico_aud ADD `unidadDeMedida` varchar(512) DEFAULT NULL;
ALTER TABLE indicador_estrategico ADD `estado` VARCHAR(50) NOT NULL DEFAULT 'Presentado';
ALTER TABLE indicador_estrategico_aud ADD `estado` VARCHAR(50) NOT NULL DEFAULT 'Presentado';


ALTER TABLE proyecto ADD `presupuestoGastosCorrientes` double DEFAULT NULL;
ALTER TABLE proyecto_aud ADD `presupuestoGastosCorrientes` double DEFAULT NULL;
ALTER TABLE proyecto ADD `presupuestoPPIObra` double DEFAULT NULL;
ALTER TABLE proyecto_aud ADD `presupuestoPPIObra` double DEFAULT NULL;
ALTER TABLE proyecto ADD `presupuestoPPIMantenimiento` double DEFAULT NULL;
ALTER TABLE proyecto_aud ADD `presupuestoPPIMantenimiento` double DEFAULT NULL;
ALTER TABLE proyecto ADD `presupuestoACUMAR` double DEFAULT NULL;
ALTER TABLE proyecto_aud ADD `presupuestoACUMAR` double DEFAULT NULL;





