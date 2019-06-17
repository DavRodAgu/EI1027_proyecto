package es.uji.ei1027.toopots.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class Cliente implements Comparable<Cliente> {
	private String idCliente;
	private String nombre;
	private String email;
	private String sexo;
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
	private LocalDate fechaNacimiento;
	
	
	public String getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
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
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	
	@Override
	public String toString() {
		return "Cliente [idCliente=" + idCliente + ", nombre=" + nombre + ", email=" + email + ", sexo=" + sexo
				+ ", fechaNacimiento=" + fechaNacimiento + "]";
	}
	
	public int compareTo(Cliente altre) {
		return this.getNombre().compareTo(altre.getNombre());
	}
}
