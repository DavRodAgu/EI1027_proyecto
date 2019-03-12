package es.uji.ei1027.toopots.model;

public class Acredita {
	private int idTipoActividad;
	private int idAcreditacion;
	
	
	public int getIdTipoActividad() {
		return idTipoActividad;
	}
	public void setIdTipoActividad(int idTipoActividad) {
		this.idTipoActividad = idTipoActividad;
	}
	public int getIdAcreditacion() {
		return idAcreditacion;
	}
	public void setIdAcreditacion(int idAcreditacion) {
		this.idAcreditacion = idAcreditacion;
	}
	
	
	@Override
	public String toString() {
		return "Acredita [idTipoActividad=" + idTipoActividad + ", idAcreditacion=" + idAcreditacion + "]";
	}
	
	
}
