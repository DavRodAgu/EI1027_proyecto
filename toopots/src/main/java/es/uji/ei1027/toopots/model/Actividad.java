package es.uji.ei1027.toopots.model;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;


public class Actividad implements Comparable<Actividad> {
	private int idActividad;
	private String estado;
	private String nombre;
	private String descripcion;
	@DateTimeFormat(pattern="HH:mm")
	private LocalTime duracion;
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
	private LocalDate fecha;
	private float precio;
	private int minAsistentes;
	private int maxAsistentes;
	private String lugar;
	private String puntoDeEncuentro;
	@DateTimeFormat(pattern="HH:mm")
	private LocalTime horaDeEncuentro;
	private String textoCliente;
	private int idTipoActividad;
	private String idInstructor;
	
	
	public int getIdActividad() {
		return idActividad;
	}
	public void setIdActividad(int idActividad) {
		this.idActividad = idActividad;
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
	
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	public LocalTime getDuracion() {
		return duracion;
	}
	public void setDuracion(LocalTime duracion) {
		this.duracion = duracion;
	}
	
	
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	
	public float getPrecio() {
		return precio;
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	
	
	public int getMinAsistentes() {
		return minAsistentes;
	}
	public void setMinAsistentes(int minAsistentes) {
		this.minAsistentes = minAsistentes;
	}
	
	
	public int getMaxAsistentes() {
		return maxAsistentes;
	}
	public void setMaxAsistentes(int maxAsistentes) {
		this.maxAsistentes = maxAsistentes;
	}
	
	
	public String getLugar() {
		return lugar;
	}
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	
	
	public String getPuntoDeEncuentro() {
		return puntoDeEncuentro;
	}
	public void setPuntoDeEncuentro(String puntoDeEncuentro) {
		this.puntoDeEncuentro = puntoDeEncuentro;
	}
	
	
	public LocalTime getHoraDeEncuentro() {
		return horaDeEncuentro;
	}
	public void setHoraDeEncuentro(LocalTime horaDeEncuentro) {
		this.horaDeEncuentro = horaDeEncuentro;
	}
	
	
	public String getTextoCliente() {
		return textoCliente;
	}
	public void setTextoCliente(String textoCliente) {
		this.textoCliente = textoCliente;
	}
	
	
	public int getIdTipoActividad() {
		return idTipoActividad;
	}
	public void setIdTipoActividad(int idTipoActividad) {
		this.idTipoActividad = idTipoActividad;
	}
	
	
	public String getIdInstructor() {
		return idInstructor;
	}
	public void setIdInstructor(String idInstructor) {
		this.idInstructor = idInstructor;
	}
	
	
	@Override
	public String toString() {
		return "Actividad [idActividad=" + idActividad + ", estado=" + estado + ", nombre=" + nombre + ", descripcion="
				+ descripcion + ", duracion=" + duracion + ", fecha=" + fecha + ", precio=" + precio
				+ ", minAsistentes=" + minAsistentes + ", maxAsistentes=" + maxAsistentes + ", lugar=" + lugar
				+ ", puntoDeEncuentro=" + puntoDeEncuentro + ", horaDeEncuentro=" + horaDeEncuentro + ", textoCliente="
				+ textoCliente + ", idTipoActividad=" + idTipoActividad + ", idInstructor=" + idInstructor + "]";
	}
	
	public int compareTo(Actividad altre) {
		return this.getNombre().compareTo(altre.getNombre());
	}
}
