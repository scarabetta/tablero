ALTER TABLE indicador_estrategico ADD pesoRelativo tinyint(4) DEFAULT NULL;
ALTER TABLE indicador_estrategico_aud ADD pesoRelativo tinyint(4) DEFAULT NULL;

ALTER TABLE indicador_estrategico MODIFY descripcion varchar(512) DEFAULT NULL;
ALTER TABLE indicador_estrategico_aud MODIFY descripcion varchar(512) DEFAULT NULL;

ALTER TABLE indicador_estrategico MODIFY metodoCalculo varchar(512) NOT NULL;
ALTER TABLE indicador_estrategico_aud MODIFY metodoCalculo varchar(512) NOT NULL;

UPDATE indicador_estrategico i
SET i.metodoCalculo=i.descripcion
WHERE i.metodoCalculo = '';

