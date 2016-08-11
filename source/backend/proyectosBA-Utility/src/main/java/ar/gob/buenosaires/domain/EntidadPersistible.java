package ar.gob.buenosaires.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public abstract class EntidadPersistible implements Serializable {
	
	private static final long serialVersionUID = -2833947824285802044L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Long id;
	
	@Version
	@JsonIgnore
	protected Long version = -1L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}
