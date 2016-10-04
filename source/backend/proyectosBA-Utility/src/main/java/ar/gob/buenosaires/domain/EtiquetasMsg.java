package ar.gob.buenosaires.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EtiquetasMsg {

	List<TemaTransversal> temasTransversales;
	List<CompromisoPublico> compromisosPublicos;
	List<OtraEtiqueta> otrasEtiquetas;
		
	public EtiquetasMsg() {
	}

	public EtiquetasMsg(List<TemaTransversal> temasTransversales, List<CompromisoPublico> compromisosPublicos,
			List<OtraEtiqueta> otrasEtiquetas) {
		
		this.temasTransversales = temasTransversales;
		this.compromisosPublicos = compromisosPublicos;
		this.otrasEtiquetas = otrasEtiquetas;
	}

	public List<TemaTransversal> getTemasTransversales() {
		return temasTransversales;
	}

	public void setTemasTransversales(List<TemaTransversal> temasTransversales) {
		this.temasTransversales = temasTransversales;
	}

	public List<CompromisoPublico> getCompromisosPublicos() {
		return compromisosPublicos;
	}

	public void setCompromisosPublicos(List<CompromisoPublico> compromisosPublicos) {
		this.compromisosPublicos = compromisosPublicos;
	}

	public List<OtraEtiqueta> getOtrasEtiquetas() {
		return otrasEtiquetas;
	}

	public void setOtrasEtiquetas(List<OtraEtiqueta> otraEtiquetas) {
		this.otrasEtiquetas = otraEtiquetas;
	}			
	
}
