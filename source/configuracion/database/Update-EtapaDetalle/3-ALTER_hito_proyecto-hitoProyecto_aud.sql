ALTER TABLE hito_proyecto ADD idHitoPadreAux INT(11);
ALTER TABLE hito_proyecto_aud ADD idHitoPadreAux INT(11);

ALTER TABLE hito_proyecto ADD idProyectoAux INT(11);
ALTER TABLE hito_proyecto_aud ADD idProyectoAux INT(11);

ALTER TABLE hito_proyecto MODIFY idProyecto INT(11) DEFAULT NULL;
ALTER TABLE hito_proyecto_aud MODIFY idProyecto INT(11) DEFAULT NULL;

ALTER TABLE hito_proyecto MODIFY idPadre INT(11) DEFAULT NULL;
ALTER TABLE hito_proyecto_aud MODIFY idPadre INT(11) DEFAULT NULL;


