declare class MapaInteractivo {
    api : any;

    addMarker(place: any, goTo?: boolean, onClick?: any, options?: any) : number;
    removeMarker(idMarker: number ) : void;
    goTo(point: any, zoomIn: boolean) : void;
    getMarkers() : any;
}

declare interface MapaInteractivoFactory {
    new(idDiv: string, options: any): MapaInteractivo;
}

declare class Punto {
    x: number;
    y: number;
}

declare interface ReverseGeoCodingData {
    altura_impar: string;
    altura_par: string;
    calle_alturas: string;
    esquina: string;
    metros_a_esquina: string;
    parcela: string;
    puerta: string;
    puerta_x: number;
    puerta_y: number;
}

declare interface PuntoFactory {
    new(coordX: number, coordY: number): Punto;
}

declare class GeoCoder {
    reverseGeoCoding(coordX: number, coordY: number,onReverseGeoCoding: any, onReverseGeoCodingError: any): any;
    getSMP( cod_calle: number, altura: number, onSuccess: any, onError: any ) : any
}

declare interface GeoCoderFactory {
    new() : GeoCoder;
}

declare class FotosParcela {
    cargarFoto(any) : void;
    fotoAnterior() : number;
    fotoSiguiente(): number;
    getCurrentFoto(): number;
    getDatosFoto(id: number): any;
    getDatosFotos(): any;
}

declare interface FotosParcelaFactory {
    new(smp: string, options?: any): FotosParcela;
}

declare interface OpenLayers {

}

declare interface CalleFactory {
    new(cod: number, nombre: string, alturas?: number[], cruces?: string[]): Calle;
}

declare interface Calle {
    toString(): string;
}

declare interface Ubicacion {
    calle: string;
    altura: number;
    calleCruce: string;
    coordenadas: Punto;
    smp: string;
    nombre: string;
}

declare interface Direccion {
    getCalle() : Calle;
    getAltura(): number;
    getCalleCruce(): Calle;
    getCoordenadas(): Punto;
    setCoordenadas(punto: Punto): void;
    getSmp();
    setSmp(codigoParcela: string): void;
    toString(): string;
}

declare interface DireccionFactory {
    new(calle1: Calle, calle2OAltura: Calle | number): Direccion;
    fromObj(object: any): Direccion;
}

declare interface AutoCompleterFactory {
    new(idField: string, options: any, viewCtrl?: any): AutoCompleter;
}

declare interface AutoCompleter {

}

declare interface NormalizadorDirecciones {
    normalizar(calleAltura: string, cantCoincidencias: number): Direccion[];
}

declare interface NormalizadorDireccionesFactory {
    init(options?: any): any;
    new(): NormalizadorDirecciones;
}

declare interface MapaEstatico {

}

declare interface OptionsMapaEstatico {
    desc: string,
    dir: string,
    height: number,
    marcarPunto: boolean,
    punto: Punto,
    radio : number,
    smp: string,
    width: number,
    x: number,
    y: number
}

declare interface MapaEstaticoFactory {
    new(options: any): string;
}

declare interface Usig {

    MapaInteractivo: MapaInteractivoFactory;
    MapaEstatico: MapaEstaticoFactory;
    AutoCompleter: AutoCompleterFactory;
    Punto: PuntoFactory;
    GeoCoder: GeoCoderFactory;
    FotosParcela: FotosParcelaFactory;
    Direccion: DireccionFactory;
    Calle: CalleFactory;
    NormalizadorDirecciones: NormalizadorDireccionesFactory;

    debug(message:string);
}

declare let usig: Usig;

