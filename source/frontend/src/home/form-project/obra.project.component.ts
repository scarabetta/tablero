import {Proyecto} from "../../models/jurisdiccion";
import {GeneralServices} from "../../services/services";
import {Comuna} from "../../models/jurisdiccion";
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
    private totalBudget:number;
    private datePickersInicio = [];
    private datePickersFin = [];
    private editableHitosName = [];
    private currentobra: Obra;
    private currentObraNombre: string;
    private tiposObra: TipoObra[];
    private selectedTipo: TipoObra;
    private selectedSubTipo:SubtipoObra;
    private selectedTipoId: number;
    private selectedComunaId:number;
    private validHitoNombre: string;
    private validHitoFecha: string;
    private validHitoFechaProyecto: string;
    private validObraNombre: string;
    private validDireccionNormalizada: string;
    private validDireccionDesdeNormalizada: string;
    private validDireccionHastaNormalizada: string;
    private isNewObra: boolean;

    /*@ngInject*/
    constructor( private $compile: ng.ICompileService, private $scope:ng.IScope, private $timeout:ng.ITimeoutService, private services:GeneralServices) {
      this.validHitoNombre = "";
      this.validHitoFecha = "";
      this.validHitoFechaProyecto = "";
      this.validObraNombre = "";
      this.validDireccionNormalizada = "";
      this.validDireccionDesdeNormalizada = "";
      this.validDireccionHastaNormalizada = "";
      services.comunas().then((data) => {
        this.comunas = data;
      });
      services.tiposDeObra().then((data) => {
        this.tiposObra = data;
      });
      if (!this.currentobra) {
        this.isNewObra = true;
        this.createNewObra();
      } else {
        this.isNewObra = false;
        this.currentobra = angular.copy(this.currentobra);
        this.currentObraNombre = this.currentobra.nombre;
        this.initializeTipoObra();
        if (this.currentobra.comuna) {
          this.selectedComunaId = this.currentobra.comuna.idComuna;
        }
      }

      this.initializeBudget();
      this.initializePickers();
      this.initializeEditables();

      services.comunas().then((data) => {
        this.comunas = data;
      });
      let self = this;
      $timeout(function(){
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
        this.direccionAutocompleter = new usig.AutoCompleter('direccion', {
          debug: false,
          rootUrl: '../',
          onInputChange: function(input) {
            if (self.currentobra.tipoUbicacion === 'direccion') {
              if (input !== '') {
                self.validDireccionNormalizada = "La direccion debe estar normalizada";
              } else {
                self.validDireccionNormalizada = "";
              }
              self.validDireccionDesdeNormalizada = '';
              self.validDireccionHastaNormalizada = '';
            }
          },
          afterSelection: function(option) {
            if (option instanceof usig.Direccion || option instanceof (<any>usig).inventario.Objeto) {
              self.origen = option;
              self.currentobra.direccion = option.toString();
            }
          },
          afterGeoCoding: function(pt) {
            self.validDireccionNormalizada = "";
            self.$scope.$apply();
            if (pt instanceof usig.Punto) {
              if (self.origen instanceof usig.Direccion) {
                self.origen.setCoordenadas(pt);
              }
              if (self.recorrido) {
                self.mapa.borrarRecorrido(self.recorrido);
              }
              self.mapa.addMarker(self.origen, true, function(ev, place, popup) {
                popup.show();
              });
            }
          }
        });
        new usig.AutoCompleter('direccionDesde', {
          debug: false,
          rootUrl: '../',
          onInputChange: function(input) {
            if (self.currentobra.tipoUbicacion === 'tramo') {
              if (input !== '') {
                self.validDireccionDesdeNormalizada = "La direccion debe estar normalizada";
              } else {
                self.validDireccionDesdeNormalizada = "";
              }
              self.validDireccionNormalizada = '';
            }
          },
          afterSelection: function(option) {
            if (option instanceof usig.Direccion || option instanceof (<any>usig).inventario.Objeto) {
              self.origen = option;
              self.currentobra.direccionDesde = option.toString();
            }
          },
          afterGeoCoding: function(pt) {
            self.validDireccionDesdeNormalizada = "";
            self.$scope.$apply();
            if (pt instanceof usig.Punto) {
              if (self.origen instanceof usig.Direccion) {
                self.origen.setCoordenadas(pt);
              }
              buscarRecorridos();
            }
          }
        });
        new usig.AutoCompleter('direccionHasta', {
          debug: false,
          rootUrl: '../',
          onInputChange: function(input) {
            if (self.currentobra.tipoUbicacion === 'tramo') {
              if (input !== '') {
                self.validDireccionHastaNormalizada = "La direccion debe estar normalizada";
              } else {
                self.validDireccionHastaNormalizada = "";
              }
              self.validDireccionNormalizada = '';
            }
          },
          afterSelection: function(option) {
            if (option instanceof usig.Direccion || option instanceof (<any>usig).inventario.Objeto) {
              self.destino = option;
              self.currentobra.direccionHasta = option.toString();
            }
          },
          afterGeoCoding: function(pt) {
            self.validDireccionHastaNormalizada = "";
            self.$scope.$apply();
            if (pt instanceof usig.Punto) {
              if (self.destino instanceof usig.Direccion) {
                self.destino.setCoordenadas(pt);
              }
              buscarRecorridos();
            }
          }
        });
        (<any>$('#mapa')).css('width', 700).css('height', 250);
        self.mapa = new usig.MapaInteractivo('mapa', {
          rootUrl: '../',
          onReady: function(){
            if (self.currentobra.direccion) {
              self.mapa.addMarker(self.currentobra.direccion, true, function(ev, place, popup) {
                    popup.show();
              });
            }
          }});
      });
    }

    initializePickers() {
      this.currentobra.hitos.forEach((h) => {
        this.datePickersInicio.push({  "status": false });
        this.datePickersFin.push({ "status": false });
      });
    }

    initializeEditables() {
      this.currentobra.hitos.forEach((h) => {
        if (h.nombre === 'AnteProyecto' || h.nombre === 'Licitacion' || h.nombre === 'Ejecucion') {
          this.editableHitosName.push({"editable": false});
        } else {
          this.editableHitosName.push({"editable": true});
        }
      });
    }

    initializeTipoObra() {
      if (this.currentobra.idSubtipoObraAux) {
        this.services.getTipoObraFromSubtipo(this.currentobra.idSubtipoObraAux).then((data) => {
          this.selectedTipo = data;
          this.selectedTipo.subtiposObra.forEach((s) => {
            if (s.idSubtipoObra === this.currentobra.idSubtipoObraAux) {
              this.selectedSubTipo = s;
              this.selectedTipoId = this.selectedTipo.idTipoObra;
            }
          });
        });
      }
    }

    changedTipo() {
      this.tiposObra.forEach((t) => {
        if (t.idTipoObra === this.selectedTipoId) {
          this.selectedTipo = t;
        }
      });
    }

    changedComuna() {
      this.comunas.forEach((c) => {
        if (c.idComuna === this.selectedComunaId) {
          this.currentobra.comuna = c;
        }
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
        "direccionUnidad": null,
        "fechaInicio": null,
        "fechaFin": null
      };
    }

    saveObra() {
      this.currentobra.subtipoObra = this.selectedSubTipo;
      this.currentobra.presupuestoTotal = this.totalBudget;
      if (this.selectedSubTipo) {
        this.currentobra.idSubtipoObraAux = this.selectedSubTipo.idSubtipoObra;
      }
      this.identificarFechas();

      if (!this.isNewObra) {
        for (var y = 0; y < this.currentproject.obras.length; y++) {
          if (this.currentproject.obras[y].nombre === this.currentObraNombre) {
            this.currentproject.obras[y] = this.currentobra;
          }
        }
      } else {
        this.currentproject.obras.push(this.currentobra);
      }
      (<any>$('#obraModal')).modal('hide');
    }

    private identificarFechas() : void {
      let fechasInicio = new Array<Date>();
      let fechasFin = new Array<Date>();
      this.currentobra.hitos.forEach(h => {
        if (h.fechaInicio) {
          fechasInicio.push(h.fechaInicio);
        }
        if (h.fechaFin) {
          fechasFin.push(h.fechaFin);
        }
      });
      this.currentobra.fechaInicio = fechasInicio.length > 0 ? fechasInicio.sort(
        (a, b) => a.getTime() - b.getTime()
      )[0] : null;
      this.currentobra.fechaFin = fechasFin.length > 0 ? fechasFin.sort(
        (a, b) => b.getTime() - a.getTime()
      )[0] : null;
    }

    validateHitoNames() {
      this.validHitoNombre = "";
      for (var y = 0; y < this.currentobra.hitos.length; y++) {
        for (var j = 0; j < this.currentobra.hitos.length; j++) {
          if (this.currentobra.hitos[y].nombre === this.currentobra.hitos[j].nombre && y !== j) {
            this.validHitoNombre = "Los nombres de hito no pueden estar repetidos";
          }
        }
      }
    }

    validateObraName() {
      this.validObraNombre = "";
      this.currentproject.obras.forEach((t) => {
        if (t.nombre === this.currentobra.nombre) {
          if (!this.isNewObra) {
            if (this.currentobra.idObra !== t.idObra) {
              this.validObraNombre = "Ya hay otra obra con el mismo nombre";
            }
          } else {
            this.validObraNombre = "Ya hay otra obra con el mismo nombre";
          }
        }
      });
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
      this.editableHitosName.push({  "editable": true });
    }

    removeHito(index) {
      this.currentobra.hitos.splice(index, 1);
      this.datePickersInicio.splice(index, 1);
      this.datePickersFin.splice(index, 1);
      this.validateDates();
      this.validateHitoNames();
    }

    handlePickerInicio(index) {
      this.datePickersInicio[index].status = !this.datePickersInicio[index].status;
    }

    handlePickerFin(index) {
      this.datePickersFin[index].status = !this.datePickersFin[index].status;
    }

    validateDates() {
      this.validHitoFechaProyecto = "";
      this.validHitoFecha = "";
      this.currentobra.hitos.forEach((p) => {
        let fechaIni = p.fechaInicio;
        let fechaFin = p.fechaFin;
        if (fechaIni && fechaFin) {
          if (fechaIni > fechaFin) {
            this.validHitoFecha = "La fecha de fin debe ser posterior a la fecha de inicio";
          }
        }
        if (fechaIni) {
          if (fechaIni < this.currentproject.fechaInicio) {
            this.validHitoFechaProyecto = "Las fechas son incosistentes con las del proyecto (" +  this.currentproject.fechaInicio.toLocaleDateString() + " - "
              + this.currentproject.fechaFin.toLocaleDateString() + ")";
          }
        }
        if (fechaFin) {
          if (fechaFin > this.currentproject.fechaFin) {
            this.validHitoFechaProyecto = "Las fechas son incosistentes con las del proyecto (" + this.currentproject.fechaInicio.toLocaleDateString() + " - "
              + this.currentproject.fechaFin.toLocaleDateString() + ")";
          }
        }
      });
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
