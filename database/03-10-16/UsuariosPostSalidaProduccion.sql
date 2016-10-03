INSERT INTO usuario
(email, activo) VALUES
('LEsquerro@buenosaires.gob.ar',1),
('cmealla@buenosaires.gob.ar',1),
('federicobrunetti@buenosaires.gob.ar',1),
('scisco@buenosaires.gob.ar',1),
('emartinezroyano@buenosaires.gob.ar',1);

INSERT INTO usuario_por_jurisdiccion (jurisdiccion_idJurisdiccion, usuario_idUsuario) VALUES ((SELECT J.idJurisdiccion FROM jurisdiccion J WHERE J.codigo = 'BCBA') , (SELECT U.idUsuario FROM usuario U WHERE U.email = 'LEsquerro@buenosaires.gob.ar'));
INSERT INTO usuario_por_jurisdiccion (jurisdiccion_idJurisdiccion, usuario_idUsuario) VALUES ((SELECT J.idJurisdiccion FROM jurisdiccion J WHERE J.codigo = 'EATC') , (SELECT U.idUsuario FROM usuario U WHERE U.email = 'cmealla@buenosaires.gob.ar'));
INSERT INTO usuario_por_jurisdiccion (jurisdiccion_idJurisdiccion, usuario_idUsuario) VALUES ((SELECT J.idJurisdiccion FROM jurisdiccion J WHERE J.codigo = 'MCGC') , (SELECT U.idUsuario FROM usuario U WHERE U.email = 'federicobrunetti@buenosaires.gob.ar'));
INSERT INTO usuario_por_jurisdiccion (jurisdiccion_idJurisdiccion, usuario_idUsuario) VALUES ((SELECT J.idJurisdiccion FROM jurisdiccion J WHERE J.codigo = 'SECISYU') , (SELECT U.idUsuario FROM usuario U WHERE U.email = 'scisco@buenosaires.gob.ar'));
INSERT INTO usuario_por_jurisdiccion (jurisdiccion_idJurisdiccion, usuario_idUsuario) VALUES ((SELECT J.idJurisdiccion FROM jurisdiccion J WHERE J.codigo = 'SGYRI') , (SELECT U.idUsuario FROM usuario U WHERE U.email = 'emartinezroyano@buenosaires.gob.ar'));

INSERT INTO rol_por_usuario (rol_idRol, usuario_idUsuario) VALUES ((SELECT R.idRol FROM rol R WHERE R.nombre = 'Operador de jurisdicción') , (SELECT U.idUsuario FROM usuario U WHERE U.email = 'LEsquerro@buenosaires.gob.ar'));
INSERT INTO rol_por_usuario (rol_idRol, usuario_idUsuario) VALUES ((SELECT R.idRol FROM rol R WHERE R.nombre = 'Operador de jurisdicción') , (SELECT U.idUsuario FROM usuario U WHERE U.email = 'cmealla@buenosaires.gob.ar'));
INSERT INTO rol_por_usuario (rol_idRol, usuario_idUsuario) VALUES ((SELECT R.idRol FROM rol R WHERE R.nombre = 'Operador de jurisdicción') , (SELECT U.idUsuario FROM usuario U WHERE U.email = 'federicobrunetti@buenosaires.gob.ar'));
INSERT INTO rol_por_usuario (rol_idRol, usuario_idUsuario) VALUES ((SELECT R.idRol FROM rol R WHERE R.nombre = 'Operador de jurisdicción') , (SELECT U.idUsuario FROM usuario U WHERE U.email = 'scisco@buenosaires.gob.ar'));
INSERT INTO rol_por_usuario (rol_idRol, usuario_idUsuario) VALUES ((SELECT R.idRol FROM rol R WHERE R.nombre = 'Operador de jurisdicción') , (SELECT U.idUsuario FROM usuario U WHERE U.email = 'emartinezroyano@buenosaires.gob.ar'));

/*Nueva jurisdiccion para usuario*/
INSERT INTO usuario_por_jurisdiccion (jurisdiccion_idJurisdiccion, usuario_idUsuario) VALUES ((SELECT J.idJurisdiccion FROM jurisdiccion J WHERE J.codigo = 'MDUYTGCSBASE') , (SELECT U.idUsuario FROM usuario U WHERE U.email = 'jdaura@buenosaires.gob.ar'));
INSERT INTO usuario_por_jurisdiccion (jurisdiccion_idJurisdiccion, usuario_idUsuario) VALUES ((SELECT J.idJurisdiccion FROM jurisdiccion J WHERE J.codigo = 'MDUYTGCSTRANS') , (SELECT U.idUsuario FROM usuario U WHERE U.email = 'jdaura@buenosaires.gob.ar'));
