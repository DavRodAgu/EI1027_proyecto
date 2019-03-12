package es.uji.ei1027.toopots.model;

public class Instructor {
	private String idInstructor;
	private String estado;
	private String nombre;
	private String email;
	private String iban;
	
	
	public String getIdInstructor() {
		return idInstructor;
	}
	public void setIdInstructor(String idInstructor) {
		this.idInstructor = idInstructor;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	
	
	@Override
	public String toString() {
		return "Instructor [idInstructor=" + idInstructor + ", estado=" + estado + ", nombre=" + nombre + ", email="
				+ email + ", iban=" + iban + "]";
	}
	
}
