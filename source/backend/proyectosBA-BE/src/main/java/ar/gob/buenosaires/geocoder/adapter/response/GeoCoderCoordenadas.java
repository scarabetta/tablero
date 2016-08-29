package ar.gob.buenosaires.geocoder.adapter.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoCoderCoordenadas {
	
	private int srid;
	private String x;
	private String y;
	
	
	public GeoCoderCoordenadas() {}

	public GeoCoderCoordenadas(int srid, String x, String y) {
		this.srid = srid;
		this.x = x;
		this.y = y;
	}

	public int getSrid() {
		return srid;
	}

	public void setSrid(int srid) {
		this.srid = srid;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}		
}
