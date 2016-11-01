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
    private totalBudget:number;
    private datePickersInicio = [];
    private datePickersFin = [];
    private currentobra: Obra;
    private tiposObra: TipoObra[];
    private selectedSubTipo:SubtipoObra;
    private validHito: string;
    private isNewObra: boolean;

    /*@ngInject*/
    constructor( private $compile: ng.ICompileService, private $scope:ng.IScope, private services:GeneralServices) {
      this.validHito = "";
      services.comunas().then((data) => {
        this.comunas = data;
      });
      services.tiposDeObra().then((data) => {
        this.tiposObra = data;
      });
      if (!this.currentobra) {
        this.isNewObra = true;
        this.createNewObra();
        this.initializeBudget();
        this.initializePickers();
      } else {
        this.isNewObra = false;
      }
      services.comunas().then((data) => {
        this.comunas = data;
      });
      let self = this;
      this.tipoUbicacion = 'direccion';
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
      this.currentobra.hitos.forEach((h) => {
        this.datePickersInicio.push({  "status": false });
        this.datePickersFin.push({ "status": false });
      });
    }

    createNewObra() {
      this.currentobra = <Obra> {
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
        "publicableTableroCiudadano": null,
        "direccionUnidad": null
      };
    }

    saveObra() {
      this.currentobra.subtipoObra = this.selectedSubTipo;
      this.currentobra.presupuestoTotal = this.totalBudget;
      this.validarUbicacion();

      if (!this.isNewObra) {
        for (var y = 0; y < this.currentproject.obras.length; y++) {
          if (this.currentproject.obras[y].idObra === this.currentobra.idObra) {
            this.currentproject.obras[y] = this.currentobra;
          }
        }
      } else {
        this.currentproject.obras.push(this.currentobra);
      }
      (<any>$('#obraModal')).modal('hide');
    }

    validarUbicacion() {
      this.currentobra.tipoUbicacion = this.tipoUbicacion;
      if (this.currentobra.tipoUbicacion === 'tramo') {
        this.currentobra.direccionDesde = this.currentobra.direccion;
        this.currentobra.direccion = '';
      }
    }

    validateHitoName() {
      this.validHito = "";
      for (var y = 0; y < this.currentobra.hitos.length; y++) {
        for (var j = 0; j < this.currentobra.hitos.length; j++) {
          if (this.currentobra.hitos[y].nombre === this.currentobra.hitos[j].nombre && y !== j) {
            this.validHito = "Los nombres de hito no pueden estar repetidos";
          }
        }
      }
    }

    removeObra() {
      for (var y = 0; y < this.currentproject.obras.length; y++) {
        if (this.currentproject.obras[y].idObra === this.currentobra.idObra) {
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
      if (!this.currentobra.presupuestosPorAnio) {
        this.currentobra.presupuestosPorAnio = new Array<PresupuestoPorAnioObra>();
        this.currentproject.presupuestosPorAnio.forEach((p) => {
          this.currentobra.presupuestosPorAnio.push(<PresupuestoPorAnioObra> {
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
      this.currentobra.presupuestosPorAnio.forEach((p) => {
          this.totalBudget += Number(p.presupuesto);
        });
    }

    addExpediente() {
      if (!this.currentobra.expedientes) {
        this.currentobra.expedientes = new Array<Expediente>();
      }
      this.currentobra.expedientes.push(<Expediente>{});
    }

    removeExpediente(index) {
      this.currentobra.expedientes.splice(index, 1);
    }

    addHito() {
      this.currentobra.hitos.push(<HitoObra>  {
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
      this.currentobra.hitos.splice(index, 1);
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
        currentobra: '<'
    },
      templateUrl: template,
      controller: ObraProjectController,
      controllerAs: 'obraCtrl',
  };
}

export = Home;
