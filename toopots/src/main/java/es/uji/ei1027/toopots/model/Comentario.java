package es.uji.ei1027.toopots.model;

public class Comentario {
	private int idComentario;
	private String comentario;
	private int valoracion;
	private String idCliente;
	private int idActividad;
	private String idInstructor;
	
	
	public int getIdComentario() {
		return idComentario;
	}
	public void setIdComentario(int idComentario) {
		this.idComentario = idComentario;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public int getValoracion() {
		return valoracion;
	}
	public void setValoracion(int valoracion) {
		this.valoracion = valoracion;
	}
	public String getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	public int getIdActividad() {
		return idActividad;
	}
	public void setIdActividad(int idActividad) {
		this.idActividad = idActividad;
	}
	public String getIdInstructor() {
		return idInstructor;
	}
	public void setIdInstructor(String idInstructor) {
		this.idInstructor = idInstructor;
	}
	
	
	@Override
	public String toString() {
		return "Comentario [idComentario=" + idComentario + ", comentario=" + comentario + ", valoracion=" + valoracion
				+ ", idCliente=" + idCliente + ", idActividad=" + idActividad + ", idInstructor=" + idInstructor + "]";
	}
	
	
}
