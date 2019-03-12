package es.uji.ei1027.toopots.model;

public class Oferta {
	private String idInstructor;
	private int idActividad;
	
	
	public String getIdInstructor() {
		return idInstructor;
	}
	public void setIdInstructor(String idInstructor) {
		this.idInstructor = idInstructor;
	}
	public int getIdActividad() {
		return idActividad;
	}
	public void setIdActividad(int idActividad) {
		this.idActividad = idActividad;
	}
	
	
	@Override
	public String toString() {
		return "Oferta [idInstructor=" + idInstructor + ", idActividad=" + idActividad + "]";
	}
	
}
