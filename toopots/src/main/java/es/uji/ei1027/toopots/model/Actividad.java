package es.uji.ei1027.toopots.model;

import java.sql.Date;
import java.sql.Time;


public class Actividad implements Comparable<Actividad> {
	private int idActividad;
	private String estado;
	private String nombre;
	private String descripcion;
	private Time duracion;
	private Date fecha;
	private float precio;
	private int minAsistentes;
	private int maxAsistentes;
	private String lugar;
	private String puntoDeEncuentro;
	private Time horaDeEncuentro;
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
	
	
	public Time getDuracion() {
		return duracion;
	}
	public void setDuracion(Time duracion) {
		this.duracion = duracion;
	}
	
	
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
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
	
	
	public Time getHoraDeEncuentro() {
		return horaDeEncuentro;
	}
	public void setHoraDeEncuentro(Time horaDeEncuentro) {
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
