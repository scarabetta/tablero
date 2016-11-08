package ar.gob.buenosaires.geocoder.adapter.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DatoUtil {
	private String comuna;
	private String barrio;
	private String comisaria;
	private String areaHospitalaria;
	private String regionSanitaria;
	private String distritoEscolar;
	private String seccionCatastral;
	private String distritoEconomico;
	private String codigoDePlaneamientoUrbano;
	private String partidoAmba;
	private String localidadAmba;
	private String codigoPostal;
	private String codigoPostalArgentino;

	public DatoUtil() {
	}

	public DatoUtil(String comuna, String barrio, String comisaria, String areaHospitalaria, String regionSanitaria,
			String distritoEscolar, String seccionCatastral, String distritoEconomico,
			String codigoDePlaneamientoUrbano, String partidoAmba, String localidadAmba, String codigoPostal,
			String codigoPostalArgentino) {
		super();
		this.comuna = comuna;
		this.barrio = barrio;
		this.comisaria = comisaria;
		this.areaHospitalaria = areaHospitalaria;
		this.regionSanitaria = regionSanitaria;
		this.distritoEscolar = distritoEscolar;
		this.seccionCatastral = seccionCatastral;
		this.distritoEconomico = distritoEconomico;
		this.codigoDePlaneamientoUrbano = codigoDePlaneamientoUrbano;
		this.partidoAmba = partidoAmba;
		this.localidadAmba = localidadAmba;
		this.codigoPostal = codigoPostal;
		this.codigoPostalArgentino = codigoPostalArgentino;
	}

	public String getComuna() {
		return comuna;
	}

	public void setComuna(String comuna) {
		this.comuna = comuna;
	}

	public String getBarrio() {
		return barrio;
	}

	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}

	public String getComisaria() {
		return comisaria;
	}

	public void setComisaria(String comisaria) {
		this.comisaria = comisaria;
	}

	public String getAreaHospitalaria() {
		return areaHospitalaria;
	}

	public void setAreaHospitalaria(String areaHospitalaria) {
		this.areaHospitalaria = areaHospitalaria;
	}

	public void setArea_hospitalaria(String areaHospitalaria) {
		this.areaHospitalaria = areaHospitalaria;
	}

	public String getRegionSanitaria() {
		return regionSanitaria;
	}

	public void setRegionSanitaria(String regionSanitaria) {
		this.regionSanitaria = regionSanitaria;
	}

	public void setRegion_sanitaria(String regionSanitaria) {
		this.regionSanitaria = regionSanitaria;
	}

	public String getDistritoEscolar() {
		return distritoEscolar;
	}

	public void setDistritoEscolar(String distritoEscolar) {
		this.distritoEscolar = distritoEscolar;
	}

	public void setDistrito_escolar(String distritoEscolar) {
		this.distritoEscolar = distritoEscolar;
	}

	public String getSeccionCatastral() {
		return seccionCatastral;
	}

	public void setSeccionCatastral(String seccionCatastral) {
		this.seccionCatastral = seccionCatastral;
	}

	public void setSeccion_catastral(String seccionCatastral) {
		this.seccionCatastral = seccionCatastral;
	}

	public String getDistritoEconomico() {
		return distritoEconomico;
	}

	public void setDistritoEconomico(String distritoEconomico) {
		this.distritoEconomico = distritoEconomico;
	}

	public void setDistrito_economico(String distritoEconomico) {
		this.distritoEconomico = distritoEconomico;
	}

	public String getCodigoDePlaneamientoUrbano() {
		return codigoDePlaneamientoUrbano;
	}

	public void setCodigoDePlaneamientoUrbano(String codigoDePlaneamientoUrbano) {
		this.codigoDePlaneamientoUrbano = codigoDePlaneamientoUrbano;
	}

	public void setCodigo_de_planeamiento_urbano(String codigoDePlaneamientoUrbano) {
		this.codigoDePlaneamientoUrbano = codigoDePlaneamientoUrbano;
	}

	public String getPartidoAmba() {
		return partidoAmba;
	}

	public void setPartidoAmba(String partidoAmba) {
		this.partidoAmba = partidoAmba;
	}

	public void setPartido_amba(String partidoAmba) {
		this.partidoAmba = partidoAmba;
	}

	public String getLocalidadAmba() {
		return localidadAmba;
	}

	public void setLocalidadAmba(String localidadAmba) {
		this.localidadAmba = localidadAmba;
	}

	public void setLocalidad_amba(String localidadAmba) {
		this.localidadAmba = localidadAmba;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public void setCodigo_postal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getCodigoPostalArgentino() {
		return codigoPostalArgentino;
	}

	public void setCodigoPostalArgentino(String codigoPostalArgentino) {
		this.codigoPostalArgentino = codigoPostalArgentino;
	}

	public void setCodigo_postal_argentino(String codigoPostalArgentino) {
		this.codigoPostalArgentino = codigoPostalArgentino;
	}
}
