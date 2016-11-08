package ar.gob.buenosaires.geocoder.adapter.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeccionManzanaParcela {

	private String smp;
	private Centroide centroide;
	private String pdaMatriz;

	public SeccionManzanaParcela() {
		super();
	}

	public SeccionManzanaParcela(String smp, Centroide centroide, String pdaMatriz) {
		super();
		this.smp = smp;
		this.centroide = centroide;
		this.pdaMatriz = pdaMatriz;
	}

	public String getSeccion() {
		return smp.substring(0, smp.indexOf("-"));
	}

	public String getManzana() {
		return smp.substring(smp.indexOf("-") + 1, smp.indexOf("-", smp.indexOf("-") + 1));
	}

	public String getParcela() {
		return smp.substring(smp.indexOf("-", smp.indexOf("-") + 1) + 1, smp.length());
	}

	public String getSmp() {
		return smp;
	}

	public void setSmp(String smp) {
		this.smp = smp;
	}

	public Centroide getCentroide() {
		return centroide;
	}

	public void setCentroide(Centroide centroide) {
		this.centroide = centroide;
	}

	public String getPdaMatriz() {
		return pdaMatriz;
	}

	public void setPdaMatriz(String pdaMatriz) {
		this.pdaMatriz = pdaMatriz;
	}

	public void setPdamatriz(String pdaMatriz) {
		this.pdaMatriz = pdaMatriz;
	}

}
