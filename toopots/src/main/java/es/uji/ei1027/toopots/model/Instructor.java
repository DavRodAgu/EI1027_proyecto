package es.uji.ei1027.toopots.model;

public class Instructor implements Comparable<Instructor> {
	private String idInstructor;
	private String estado;
	private String nombre;
	private String email;
	private String iban;
	private String foto;
	
	
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
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	@Override
	public String toString() {
		return "Instructor [idInstructor=" + idInstructor + ", estado=" + estado + ", nombre=" + nombre + ", email="
				+ email + ", iban=" + iban + ", foto=" + foto + "]";
	}
	
	public int compareTo(Instructor altre) {
		return this.getNombre().compareTo(altre.getNombre());
	}
	
}
