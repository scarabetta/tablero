/**
 * Created by enocmontiel on 7/11/16.
 */
export interface Jurisdiccion {
    idJurisdiccion: number;
    nombre: string;
    abreviatura: string;
    mision: string;
    codigo: string;
    areas: Area[];
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
  archivos: any;
  idProyecto: number;
  nombre: string;
  codigo: string;
  descripcion: string;
  tipoProyecto: string;
  meta: number;
  unidadMeta: string;
  poblacionAfectada: number;
  liderProyecto: string;
  area: Area;
  tipoUbicacionGeografica: string;
  direccion: string;
  cambioLegislativo: boolean;
  organismosCorresponsables: string;
  fechaInicio: Date;
  fechaFin: Date;
  prioridadJurisdiccional: string;
  prioridadJefatura: string;
  totalPresupuestoAprobado: number;
  estado: string;
  verificado: boolean;
  temasTransversales: TemaTransversal[];
  compromisosPublicos: CompromisoPublico[];
  otrasEtiquetas: OtraEtiqueta[];
  idObjetivoOperativo2: number;
  ejesDeGobierno : EjeDeGobierno[];
  poblacionesMeta: PoblacionMeta[];
  comunas: Comuna[];
  presupuestosPorAnio: Presupuesto[];
}

export interface ProyectoProcesado {
  estado: string;
  fechaFin: Date;
  fechaInicio: Date;
  idProyecto: number;
  nombreProyecto: string;
}

export interface IndicadorEstrategico {
  idIndicadorEstrategico: number;
  nombre: string;
  descripcion: string;
}

export interface Presupuesto {
  anio: number;
  presupuesto: number;
  otrasFuentes: number;
}

export interface EjeDeGobierno {
    idEjeDeGobierno: number;
    nombre: string;
    descripcion: string;
    ejemplos: string;
}

export interface PoblacionMeta {
    idPoblacionMeta: number;
    nombre: string;
}

export interface Comuna {
    idComuna: number;
    abreviatura: string;
    nombre: string;
}

export interface Usuario {
    idUsuario: number;
    activo: boolean;
    apellido: string;
    descripcion: string;
    email: string;
    jurisdicciones: JurisdiccionId[];
    nombreUsuario: string;
    nombre: string;
    roles: Rol[];
}

export interface JurisdiccionId {
  idJurisdiccion: number;
}

export interface Rol {
  idRol: number;
  nombre: string;
  descripcion: string;
  usuarios: Usuario[];
  permisosEntidad: PermisoEntidad[];
}

export interface Area {
  idArea: number;
  nombre: string;
}

export interface PermisoEntidad {
  idPermisoEntidad: number;
  alta: boolean;
  baja: boolean;
  gestion: boolean;
  modificacion: boolean;
  nombre: string;
}

export interface TemaTransversal {
  idTemaTransversal: number;
  temaTransversal: string;
  activo: boolean;
  nombreParaExportacion: string;
}

export interface Etiquetas {
  temasTransversales: TemaTransversal[];
  compromisosPublicos: CompromisoPublico[];
  otrasEtiquetas: OtraEtiqueta[];
}

export interface CompromisoPublico {
  idCompromisoPublico: number;
  compromisoPublico: string;
  activo: boolean;
}

export interface OtraEtiqueta {
  idEtiqueta: number;
  etiqueta: string;
}
