package es.uji.ei1027.toopots.model;

public class TipoActividad {
	private int idTipoActividad;
	private String nombre;
	private String nivel;
	
	
	public int getIdTipoActividad() {
		return idTipoActividad;
	}
	public void setIdTipoActividad(int idTipoActividad) {
		this.idTipoActividad = idTipoActividad;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	
	
	@Override
	public String toString() {
		return "TipoActividad [idTipoActividad=" + idTipoActividad + ", nombre=" + nombre + ", nivel=" + nivel + "]";
	}
}
