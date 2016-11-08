ALTER TABLE obra MODIFY descripcion VARCHAR(512);
ALTER TABLE obra_aud MODIFY descripcion VARCHAR(512);

ALTER TABLE obra MODIFY nombre VARCHAR(512);
ALTER TABLE obra_aud MODIFY nombre VARCHAR(512);

ALTER TABLE obra MODIFY descripcion text;
ALTER TABLE obra_aud MODIFY descripcion text;

ALTER TABLE obra MODIFY nombre VARCHAR(512);
ALTER TABLE obra_aud MODIFY nombre VARCHAR(512);

ALTER TABLE obra MODIFY detalleUbicacion VARCHAR(512);
ALTER TABLE obra_aud MODIFY detalleUbicacion VARCHAR(512);

ALTER TABLE obra ADD usig_RegionSanitaria VARCHAR(50);
ALTER TABLE obra_aud ADD usig_RegionSanitaria VARCHAR(50);

ALTER TABLE obra ADD usig_CodigoPostal VARCHAR(5);
ALTER TABLE obra_aud ADD usig_CodigoPostal VARCHAR(5);

ALTER TABLE obra ADD usig_CodigoPostalArgentino VARCHAR(10);
ALTER TABLE obra_aud ADD usig_CodigoPostalArgentino VARCHAR(10);

ALTER TABLE obra ADD usig_Latitud VARCHAR(20);
ALTER TABLE obra_aud ADD usig_Latitud VARCHAR(20);

ALTER TABLE obra ADD usig_Longitud VARCHAR(20);
ALTER TABLE obra_aud ADD usig_Longitud VARCHAR(20);

ALTER TABLE obra ADD fechaInicio date DEFAULT NULL;
ALTER TABLE obra_aud ADD fechaInicio date DEFAULT NULL;

ALTER TABLE obra ADD fechaFin date DEFAULT NULL;
ALTER TABLE obra_aud ADD fechaFin date DEFAULT NULL;