DROP TABLE `proyecto_por_indicador_estrategico`;

CREATE TABLE `proyecto_por_indicador_estrategico` (
  `idProyecto` int(11) NOT NULL,
  `idIndicadorEstrategico` int(11) NOT NULL,
  PRIMARY KEY (`idProyecto`,  `idIndicadorEstrategico`),
  KEY `proyecto_por_indicador_estrategico_indicador_estrategico_FK` (`idIndicadorEstrategico`),
  CONSTRAINT `FK_indicador_estrategico_pxie` FOREIGN KEY (`idIndicadorEstrategico`) REFERENCES `indicador_estrategico` (`idIndicadorEstrategico`),
  CONSTRAINT `FK_proyecto_pxie` FOREIGN KEY (`idProyecto`) REFERENCES `proyecto` (`idProyecto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE `proyecto_por_indicador_estrategico_aud`;

CREATE TABLE `proyecto_por_indicador_estrategico_aud` (
  `rev` int(11) NOT NULL,
  `revtype` int(4) DEFAULT NULL,
  `idProyecto` int(11) NOT NULL,
  `idIndicadorEstrategico` int(11) NOT NULL,
  PRIMARY KEY (`rev`, `idProyecto`, `idIndicadorEstrategico` ),
  KEY `FK_idIndicadorEstrategico` (`rev`),
  CONSTRAINT `FK_idIndicadorEstrategico` FOREIGN KEY (`rev`) REFERENCES `audit_revision_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;