import {IndicadorEstrategico} from "../../models/jurisdiccion";
import {ObjetivoJurisdiccional} from "../../models/jurisdiccion";
import {Proyecto} from "../../models/jurisdiccion";
import {Meta} from "../../models/jurisdiccion";
import {Semaforizacion} from "../../models/jurisdiccion";
import {MedicionHistorica} from "../../models/jurisdiccion";
const template = require('./project-indicador.html');

module Home {

  export class IndicadorProjectController {
    private errorCalculoAutomatico: String;
    private cantJustificaciones = 0;
    private currentindicador: IndicadorEstrategico;
    private strategicobjetive: ObjetivoJurisdiccional;
    private currentMeta: Meta;
    private objectiveprojects: Proyecto[];
    private selectedProjects = new Array<any>();
    private medicionesHistoricas = [];
    private currentYear = new Date().getFullYear();
    private fullYear = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];
    private frecuencias = [{key: "Mensual", multi:1}, {key: "Bimestral", multi:2}, {key: "Trimestral", multi:3}, {key: "Cuatrimestral", multi:4},
        {key: "Semestral", multi:6}, {key: "Anual", multi:12}];
    private labelsMonth = [];
    private data = [];
    private semaforizacionesAnual = [];
    private currentSemaforizaciones = [];
        /* tslint:disable:no-unused-variable */
    private series = ['Meta'];
    private datasetOverride = [{ yAxisID: 'y-axis-1' }];
    private options = {
      scales: {
        yAxes: [
          {
            id: 'y-axis-1',
            type: 'linear',
            display: true,
            position: 'left'
          }
        ]
      }
    };

    /*@ngInject*/
    constructor( private $compile: ng.ICompileService, private $scope:ng.IScope, private $timeout:ng.ITimeoutService) {
      this.currentindicador = angular.copy(this.currentindicador);
      if (!this.currentindicador.frecuencia) {
        this.currentindicador.frecuencia = "Mensual";
      }
      this.initializeCurrentMeta();
      this.initializeHistorico();
      this.initializeAvanceMeta();

      var arrayJustificaciones = ['justificacionPresupuesto', 'justificacionParametroNac', 'justificacionInstituciones', 'justificacionResultadoHistorico', 'justificacionParametroInternacional'];

      arrayJustificaciones.forEach((justi) => {
        if (this.currentMeta[justi]) {
          this.cantJustificaciones ++;
        }
      });

    }

    initializeCurrentMeta() {
      var scope = this;
      var metaProxAnio = this.currentindicador.metas.filter(function (element) {
        return element.anio === scope.currentYear + 1;
      })[0];
      if (metaProxAnio) {
        this.currentMeta = metaProxAnio;
      } else {
        this.currentMeta = this.createEmptyMeta(this.currentYear + 1);
      }
    }

    initializeHistorico() {
      if (this.currentindicador.medicionesHistoricas.length > 0) {
        this.createHistorico();
      }
    }

    moveToSelectedList(project, status, index) {
      if (status.target.checked === true) {
        this.selectedProjects.push(project);
        for (let i = 0; i < this.objectiveprojects.length; i++) {
            if (this.objectiveprojects[i] === project) {
                this.objectiveprojects.splice(i, 1);
            }
        }
      }
    }

    removeToSelectedList(project, status, index) {
      if (status.target.checked === false) {
        this.objectiveprojects.push(project);
        this.selectedProjects.splice(index, 1);
      }

    }

    initializeAvanceMeta() {
      for (var i = 0; i <= 11; i++) {
        var sem = this.getSemaforizacionForMonth(i + 1);
        if (!sem) {
          sem = this.createEmptySemaforizacion(i + 1);
        }
        if (i === 11 && this.currentMeta.meta) {
          sem.valor = this.currentMeta.meta;
        }
        this.semaforizacionesAnual.push(sem);
      }

      this.calculateChartAndSemaforizaciones();
    }

    calculateChartAndSemaforizaciones() {
      var scope = this;
      this.labelsMonth = [];
      this.currentSemaforizaciones = [];
      var data = [];
      var frecuencia = this.frecuencias.filter(function(v) {
        return v.key === scope.currentindicador.frecuencia;
      })[0];

      for (var i = 1; i * frecuencia.multi <= this.fullYear.length; i++) {
        data.push(this.semaforizacionesAnual[(i * frecuencia.multi) - 1].valor);
        this.labelsMonth.push(this.fullYear[(i * frecuencia.multi) - 1]);
        this.currentSemaforizaciones.push(this.semaforizacionesAnual[(i * frecuencia.multi) - 1]);
      }
      this.data = [];
      this.data.push(data);
    }

    frecuenciaChange() {
      this.calculateChartAndSemaforizaciones();
    }

    currentMetaChange() {
      this.semaforizacionesAnual[11].valor = this.currentMeta.meta;
      this.calculateChartAndSemaforizaciones();
    }

    pesoRelativoChanged() {
        if (this.currentindicador.pesoRelativo > 100) {
          this.currentindicador.pesoRelativo = 100;
        }

        if (this.currentindicador.pesoRelativo < 0 ) {
          this.currentindicador.pesoRelativo = 0;
        }
    }
    changedJustificacion(justificacion) {
      if (justificacion) {
        this.cantJustificaciones ++;
      } else {
        if (this.cantJustificaciones > 0) {
          this.cantJustificaciones --;
        }
      }
    }

    changeValorSemaforizacion(valor, mes) {
      var scope = this;
      this.semaforizacionesAnual.filter(function(v) {
        return v.mes === mes;
      })[0].valor = valor;
      this.calculateChartAndSemaforizaciones();
    }

    calcularAvance() {
      this.errorCalculoAutomatico = "";
      var ultimaMedicion = 0;
      var meta = this.getNumberFromMeta(this.currentMeta.meta);
        this.medicionesHistoricas.forEach((m) => {
          if (this.getNumberFromMeta(m.medicion) > 0) {
            ultimaMedicion = this.getNumberFromMeta(m.medicion);
          }
      });
      if (this.currentMeta.meta && this.currentindicador.formatoNumero && this.currentindicador.frecuencia) {
         if (this.currentindicador.formatoNumero !== "Fraccion") {
          this.semaforizacionesAnual.forEach((s) => {
            s.valor = +(ultimaMedicion + ((meta - ultimaMedicion) / 12) * s.mes).toFixed(2);
          });
          this.calculateChartAndSemaforizaciones();
        } else {
          this.errorCalculoAutomatico = "No es posible calcular automáticamente la meta para formatos de fracción.";
        }
      } else {
        this.errorCalculoAutomatico = "Es necesario que la Meta, Formato numérico de la meta y Frecuencia de carga de datos se encuentren completos.";
      }

    }

    getNumberFromMeta(meta) {
        return parseFloat(meta);
    }

    createHistorico() {
      for (var i = 0; i <= 3; i++) {
        var year = this.currentYear - i;
        this.medicionesHistoricas.push(this.getMedicionForYear(year));
      }
      this.medicionesHistoricas.sort(function(a, b) {return (a.anio > b.anio) ? 1 : ((b.anio > a.anio) ? -1 : 0); } );
    }

    getMedicionForYear(year) {
      var medicion = this.currentindicador.medicionesHistoricas.filter(function ( obj ) {
        return obj.anio === year;
      })[0];
      if (!medicion) {
        medicion = this.createEmptyMedicion(year);
      }

      return medicion;
    }

    getSemaforizacionForMonth(month) {
      return this.currentindicador.semaforizaciones.filter(function (element) {
        return element.mes === month;
      })[0];
    }

    save() {
        this.currentindicador.pesoRelativo = Number(this.currentindicador.pesoRelativo);
        this.currentindicador.semaforizaciones = this.currentSemaforizaciones;
        this.currentindicador.medicionesHistoricas = this.medicionesHistoricas;
        if (!this.currentMeta.idMeta) {
          this.currentindicador.metas.push(this.currentMeta);
        }
        this.currentindicador.estado = "Presentado"; //TODO change this when we have multiple estados
        for (var y = 0; y < this.strategicobjetive.indicadoresEstrategicos.length; y++) {
          if (this.strategicobjetive.indicadoresEstrategicos[y].idIndicadorEstrategico === this.currentindicador.idIndicadorEstrategico) {
            this.strategicobjetive.indicadoresEstrategicos[y] = this.currentindicador;
          }
        }
        this.currentindicador.proyectos = this.selectedProjects;
        (<any>$('#indicadorModal')).modal('hide');
    }

    cancel() {
      (<any>$('#indicadorModal')).modal('hide');
    }

    createEmptyMeta(anio) {
      return <Meta> {
        "idMeta": null,
        "indicadorEstrategico": null,
        "anio": anio,
        "meta": null,
        "origen": null,
        "referente": null,
        "metodoRecoleccion": null,
        "sistemaRecoleccion": null,
        "pesoRelativo": null,
        "justificacionParametroInternacional": false,
        "justificacionParametroNac": false,
        "justificacionResultadoHistorico": false,
        "justificacionPresupuesto": false,
        "justificacionInstituciones": false
      };
    }

    createEmptySemaforizacion(mes) {
      return <Semaforizacion> {
        "idIndicadorSemaforo": null,
        "indicadorEstrategico": null,
        "anio": this.currentYear + 1,
        "mes": mes,
        "valor": "0"
      };
    }

    createEmptyMedicion(anio) {
      return <MedicionHistorica> {
        "idMedicionHistoricaIndicador": null,
        "indicadorEstrategico": null,
        anio: anio,
        medicion: "0"
      };
    }
  }

  export let indicadorProjectComponent = {
    bindings: {
        currentindicador: '<',
        objectiveprojects: '<',
        strategicobjetive: '<'
    },
    templateUrl: template,
    controller: IndicadorProjectController,
    controllerAs: 'indicadorCtrl',
  };
}

export = Home;
