import {Proyecto} from "../../models/jurisdiccion";
import {GeneralServices} from "../../services/services.ts";
import {Comuna} from "../../models/jurisdiccion.ts";
import {SubtipoObra} from "../../models/jurisdiccion";
import {TipoObra} from "../../models/jurisdiccion";
import {HitoObra} from "../../models/jurisdiccion";
import {Obra} from "../../models/jurisdiccion";
import {Expediente} from "../../models/jurisdiccion";
import {PresupuestoPorAnioObra} from "../../models/jurisdiccion";
const template = require('./project-obra.html');

module Home {

  export class ObraProjectController {

    private currentproject:Proyecto;
    private comunas:Comuna[];
    private origen:any;
    private destino:any;
    private recorrido:any;
    private mapa:any;
    private tipoUbicacion:string;
    private currentobraid: number;
    private totalBudget:number;
    private datePickersInicio = [];
    private datePickersFin = [];
    private currentObra: Obra;
    private tiposObra: TipoObra[];
    private selectedSubTipo:SubtipoObra;

    /*@ngInject*/
    constructor( private $compile: ng.ICompileService, private $scope:ng.IScope, private services:GeneralServices) {

      services.comunas().then((data) => {
        this.comunas = data;
      });
      services.tiposDeObra().then((data) => {
        this.tiposObra = data;
      });
      if (!this.currentobraid) {
        this.createNewObra();
        this.initializeBudget();
        this.initializePickers();
      } else {
        this.fetchObra();
      }
      services.comunas().then((data) => {
        this.comunas = data;
      });
      let self = this;
      let optsRecorridos = {
        tipo: 'pie',
        gml: true
      };
      (<any>usig).Recorridos.init(optsRecorridos);
      let buscarRecorridos = function(){
        if (!self.origen || !self.destino) {
          return;
        }
        (<any>usig).Recorridos.buscarRecorridos(self.origen, self.destino, function(opciones){
          if (self.recorrido) {
            self.mapa.borrarRecorrido(self.recorrido);
          }
          self.recorrido = opciones[0];
          self.mapa.mostrarRecorrido(self.recorrido);
        });
      };
      new usig.AutoCompleter('direccionOrigen', {
        debug: false,
        rootUrl: '../',
        onReady: function() {
          (<any>$('#direccionOrigen')).val('').removeAttr('disabled').focus();
        },
        afterSelection: function(option) {
          if (option instanceof usig.Direccion || option instanceof (<any>usig).inventario.Objeto) {
            self.origen = option;
          }
        },
        afterGeoCoding: function(pt) {
          if (pt instanceof usig.Punto) {
            if (self.origen instanceof usig.Direccion) {
              self.origen.setCoordenadas(pt);
            }
            if (self.tipoUbicacion === 'direccion') {
              if (self.recorrido) {
                self.mapa.borrarRecorrido(self.recorrido);
              }
              self.mapa.addMarker(self.origen, true, function(ev, place, popup) {
                popup.show();
              });
            } else if (self.tipoUbicacion === 'tramo') {
              buscarRecorridos();
            }
          }
        }
      });
      new usig.AutoCompleter('direccionDestino', {
        debug: false,
        rootUrl: '../',
        onReady: function() {
          (<any>$('#direccionDestino')).val('').removeAttr('disabled');
        },
        afterSelection: function(option) {
          if (option instanceof usig.Direccion || option instanceof (<any>usig).inventario.Objeto) {
            self.destino = option;
          }
        },
        afterGeoCoding: function(pt) {
          if (pt instanceof usig.Punto) {
            if (self.destino instanceof usig.Direccion) {
              self.destino.setCoordenadas(pt);
            }
            if (self.tipoUbicacion === 'tramo') {
              buscarRecorridos();
            }
          }
        }
      });

      (<any>$('#mapa')).css('width', 700).css('height', 450);
      this.mapa = new usig.MapaInteractivo('mapa', { rootUrl: '../'	});
    }

    initializePickers() {
      this.currentObra.hitos.forEach((h) => {
        this.datePickersInicio.push({  "status": false });
        this.datePickersFin.push({ "status": false });
      });
    }

    createNewObra() {
      this.currentObra = <Obra> {
        "idObra": null,
        "proyecto": null,
        "subtipoObra": null,
        "expedientes": null,
        "presupuestosPorAnio": null,
        "idSubtipoObraAux": null,
        "archivosAdjuntos": null,
        "hitos": <HitoObra[]> [
          {
            "idHito": null,
            "obra": null,
            "nombre": "AnteProyecto",
            "fechaInicio":  null,
            "fechaFin": null,
            "estado": "incompleto",
            "esImportante": true
          },
        {
          "idHito": null,
          "obra": null,
          "nombre": "Licitacion",
          "fechaInicio":  null,
          "fechaFin": null,
          "estado": "incompleto",
          "esImportante": true
        },
        {
          "idHito": null,
          "obra": null,
          "nombre": "Ejecucion",
          "fechaInicio":  null,
          "fechaFin": null,
          "estado": "incompleto",
          "esImportante": true
        }],
        "estado": "incompleto",
        "nombre": null,
        "descripcion": null,
        "referenteEjecucion": null,
        "presupuestototal": null,
        "tipoUbicacion": null,
        "presupuestoTotal": null,
        "direccion": null,
        "direccionDesde": null,
        "direccionHasta": null,
        "detalleUbicacion": null,
        "usigSeccion": null,
        "usigManzana": null,
        "usigParcela": null,
        "usigBarrio": null,
        "usigUtiu": null,
        "usigDistritoEscolar": null,
        "usigAreaHospitalaria": null,
        "usigComisaria": null,
        "usigTransporteCercano": null,
        "usigCPU": null,
        "comuna": null,
        "prioridadJefatura": null,
        "informacionRelevamiento": null,
        "publicableTableroElectronico": null,
        "direccionUnidad": null
      };
    }

    fetchObra() {
      this.currentproject.obras.forEach((obra) => {
          if (obra.idObra === this.currentobraid) {
            this.currentObra = obra;
            this.initializeBudget();
            this.initializePickers();
          }
      });
    }

    saveObra() {
      this.currentObra.subtipoObra = this.selectedSubTipo;
      this.currentObra.presupuestoTotal = this.totalBudget;
      this.validarUbicacion();

      if (this.currentobraid) {
        for (var y = 0; y < this.currentproject.obras.length; y++) {
          if (this.currentproject.obras[y].idObra === this.currentobraid) {
            this.currentproject.obras[y] = this.currentObra;
          }
        }
      } else {
        this.currentproject.obras.push(this.currentObra);
      }
      this.closeModal();
    }

    validarUbicacion() {
      this.currentObra.tipoUbicacion = this.tipoUbicacion;
      if (this.currentObra.tipoUbicacion === 'tramo') {
        this.currentObra.direccionDesde = this.currentObra.direccion;
        this.currentObra.direccion = '';
      }
    }

    removeObra() {
      for (var y = 0; y < this.currentproject.obras.length; y++) {
        if (this.currentproject.obras[y].idObra === this.currentobraid) {
          this.currentproject.obras.splice(y, 1);
        }
      }
    }

    closeModal() {
      var modal = document.getElementsByTagName('obraproject');
      angular.element(modal).remove();
    }

    loadComunas($query) {
      return this.comunas.filter(function(tag) {
        return tag.nombre.toLowerCase().indexOf($query.toLowerCase()) !== -1;
      });
    }

    initializeBudget() {
      if (!this.currentObra.presupuestosPorAnio) {
        this.currentObra.presupuestosPorAnio = new Array<PresupuestoPorAnioObra>();
        this.currentproject.presupuestosPorAnio.forEach((p) => {
          this.currentObra.presupuestosPorAnio.push(<PresupuestoPorAnioObra> {
            "idPresupuestoPorAnio": null,
            "anio": p.anio,
            "presupuesto": 0,
            "obra": null
          });
        });
      }
      this.getTotalBudget();
    }

    getTotalBudget() {
      this.totalBudget = 0;
      this.currentObra.presupuestosPorAnio.forEach((p) => {
          this.totalBudget += Number(p.presupuesto);
        });
    }

    addExpediente() {
      if (!this.currentObra.expedientes) {
        this.currentObra.expedientes = new Array<Expediente>();
      }
      this.currentObra.expedientes.push(<Expediente>{});
    }

    removeExpediente(index) {
      this.currentObra.expedientes.splice(index, 1);
    }

    addHito() {
      this.currentObra.hitos.push(<HitoObra>  {
          "idHito": null,
          "obra": null,
          "nombre": null,
          "fechaInicio":  null,
          "fechaFin": null,
          "estado": "incompleto",
          "esImportante": false
        });
      this.datePickersInicio.push({  "status": false });
      this.datePickersFin.push({ "status": false });
    }

    removeHito(index) {
      this.currentObra.hitos.splice(index, 1);
      this.datePickersInicio.splice(index, 1);
      this.datePickersFin.splice(index, 1);
    }

    handlePickerInicio(index) {
      this.datePickersInicio[index].status = !this.datePickersInicio[index].status;
    }

    handlePickerFin(index) {
      this.datePickersFin[index].status = !this.datePickersFin[index].status;
    }

  }

  export let obraProjectComponent = {
    bindings: {
        currentproject: '<',
        currentobraid: '='
    },
      templateUrl: template,
      controller: ObraProjectController,
      controllerAs: 'obraCtrl',
  };
}

export = Home;
