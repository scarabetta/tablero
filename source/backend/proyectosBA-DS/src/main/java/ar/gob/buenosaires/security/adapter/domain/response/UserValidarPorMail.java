package ar.gob.buenosaires.security.adapter.domain.response;

public class UserValidarPorMail {

	private String nombre;
	private String apellido;
	private String numero_cui;
	private String tipo_cui;
	private String rlaboral;
	private String tipo_cuenta;

	
	public UserValidarPorMail() {
		super();
	}

	public UserValidarPorMail(String nombre, String apellido,
			String numero_cui, String tipo_cui, String rlaboral,
			String tipo_cuenta) {

		this.nombre = nombre;
		this.apellido = apellido;
		this.numero_cui = numero_cui;
		this.tipo_cui = tipo_cui;
		this.rlaboral = rlaboral;
		this.tipo_cuenta = tipo_cuenta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNumero_cui() {
		return numero_cui;
	}

	public void setNumero_cui(String numero_cui) {
		this.numero_cui = numero_cui;
	}

	public String getTipo_cui() {
		return tipo_cui;
	}

	public void setTipo_cui(String tipo_cui) {
		this.tipo_cui = tipo_cui;
	}

	public String getRlaboral() {
		return rlaboral;
	}

	public void setRlaboral(String rlaboral) {
		this.rlaboral = rlaboral;
	}

	public String getTipo_cuenta() {
		return tipo_cuenta;
	}

	public void setTipo_cuenta(String tipo_cuenta) {
		this.tipo_cuenta = tipo_cuenta;
	}

}
