/**
 * Created by enocmontiel on 7/11/16.
 */
export interface Jurisdiccion {
    idJurisdiccion: number;
    nombre: string;
    abreviatura: string;
    mision: string;
    objetivosJurisdiccionales: ObjetivoJurisdiccional[];
}

export interface ObjetivoJurisdiccional {
    idObjetivoJurisdiccional: number;
    codigo: string;
    nombre: string;
    objetivosOperativos: ObjetivoOperativo[];
    idJurisdiccionAux: number;
    indicadoresEstrategicos: IndicadorEstrategico[];
}

export interface ObjetivoOperativo {
    idObjetivoOperativo: number;
    idObjetivoJurisdiccionalAux: number;
    codigo: string;
    nombre: string;
    proyectos: Proyecto[];
}

export interface Proyecto {
  "idProyecto": number;
  "nombre": string;
  "codigo": string;
  "descripcion": string;
  "tipoProyecto": string;
  "meta": number;
  "unidadMeta": string;
  "poblacionAfectada": number;
  "liderProyecto": string;
  "area": string;
  "tipoUbicacionGeografica": string;
  "direccion": string;
  "cambioLegislativo": boolean;
  "organismosCorresponsables": string;
  "fechaInicio": Date;
  "fechaFin": Date;
  "prioridadJurisdiccional": string;
  "estado": string;
  "idObjetivoOperativo2": number;
  "ejesDeGobierno" : EjeDeGobierno[];
  "poblacionesMeta": PoblacionMeta[];
  "comunas": Comuna[];
  "presupuestosPorAnio": Presupuesto[];
}

export interface IndicadorEstrategico {
  idIndicadorEstrategico: number;
  nombre: string;
  descripcion: string;
}

export interface Presupuesto {
  "anio": number;
  "presupuesto": number;
}

export interface EjeDeGobierno {
    "idEjeDeGobierno": number;
    "nombre": string;
    "descripcion": string;
    "ejemplos": string;
}

export interface PoblacionMeta {
    "idPoblacionMeta": number;
    "nombre": string;
}

export interface Comuna {
    "idComuna": number;
    "abreviatura": string;
    "nombre": string;
}

export interface Usuario {
    id: number;
    nombreUsuario: string;
    nombre: string;
    apellido: string;
}