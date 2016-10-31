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
  presupuestoGastosCorrientes: number;
  presupuestoPPIObra: number;
  presupuestoPPIMantenimiento: number;
  presupuestoACUMAR: number;
  presupuestosPorMes: PresupuestoPorMes[];
  hitos: HitoProyecto[];
  obras: Obra[];
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
  ObjetivoJurisdiccional: ObjetivoJurisdiccional;
  metas: Meta[];
  medicionesHistoricas: MedicionHistorica[];
  semaforizaciones: Semaforizacion[];
  nombre: string;
  descripcion: string;
  metodoCalculo: string;
  tipoIndicador: string;
  sentido: string;
  frecuencia: string;
  formatoNumero: string;
  unidadDeMedida: string;
  estado: string;
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

export interface ArchivoAdjuntoObra {
  idArchivoObra: number;
  obra: Obra;
  nombre: string;
  descripcion: string;
  fuente: string;
  fecha: Date;
}

export interface Expediente {
  idExpediente: number;
  obra: Obra;
  descripcion: string;
  sigla: string;
  numero: number;
  anio: number;
}

export interface HitoObra {
  idHito: number;
  obra: Obra;
  nombre: string;
  fechaInicio: Date;
  fechaFin: Date;
  estado: string;
  esImportante: boolean;
}

export interface HitoProyecto {
  idHito: number;
  proyecto: Proyecto;
  hitoPadre: HitoProyecto;
  hitosHijos: HitoProyecto[];
  hitoPredecesor: HitoProyecto;
  fechaInicio: Date;
  fechaFin: Date;
  estado: string;
  presupuesto: number;
  nombre: string;
}

export interface MedicionHistorica {
  idMedicionHistoricaIndicador: number;
  indicadorEstrategico: IndicadorEstrategico;
  anio: number;
  medicion: string;
}

export interface Meta {
  idMeta: number;
  indicadorEstrategico: IndicadorEstrategico;
  anio: number;
  meta: string;
  origen: string;
  referente: string;
  metodoRecoleccion: string;
  sistemaRecoleccion: string;
  pesoRelativo: number;
  justificacionparametrointernacional: number;
  justificacionParametroNac: number;
  justificacionResultadoHistorico: number;
  justificacionPresupuesto: number;
  justificacionInstituciones: number;
}

export interface Obra {
  idObra: number;
  idSubtipoObraAux:number;
  proyecto: Proyecto;
  subtipoObra: SubtipoObra;
  expedientes: Expediente[];
  presupuestosPorAnio: PresupuestoPorAnioObra[];
  archivosAdjuntos: ArchivoAdjuntoObra[];
  hitos: HitoObra[];
  estado: string;
  nombre: string;
  descripcion: string;
  referenteEjecucion: string;
  presupuestoTotal: number;
  tipoUbicacion: string;
  direccion: string;
  direccionDesde: string;
  direccionHasta: string;
  detalleUbicacion: string;
  usigSeccion: string;
  usigManzana: string;
  usigParcela: string;
  usigBarrio: string;
  usigUtiu: string;
  usigDistritoEscolar: string;
  usigAreaHospitalaria: string;
  usigComisaria: string;
  usigTransporteCercano: string;
  usigCPU: string;
  comuna: Comuna;
  prioridadJefatura: string;
  informacionRelevamiento: string;
  publicableTableroElectronico: number;
  direccionUnidad: string;
}

export interface PresupuestoPorAnioObra {
  idPresupuestoPorAnio: number;
  obra: Obra;
  anio: number;
  presupuesto: number;
}

export interface PresupuestoPorMes {
  idPresupuestoPorMes: number;
  proyecto: Proyecto;
  anio: number;
  mes: number;
  presupuesto: number;
}

export interface Semaforizacion {
  idIndicadorSemaforo: number;
  indicadorEstrategico: IndicadorEstrategico;
  anio: number;
  mes: number;
  valor: string;
}

export interface SubtipoObra {
  idSubtipoObra: number;
  tipoObra: TipoObra;
  nombre: string;
  obras: Obra[];
}

export interface TipoObra {
  idTipoObra: number;
  nombre: string;
  subtiposObra: SubtipoObra[];
}
