package es.uji.ei1027.toopots.model;

public class Prefiere {
	private int idTipoActividad;
	private String idCliente;
	
	
	public int getIdTipoActividad() {
		return idTipoActividad;
	}
	public void setIdTipoActividad(int idTipoActividad) {
		this.idTipoActividad = idTipoActividad;
	}
	public String getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	
	
	@Override
	public String toString() {
		return "Prefiere [idTipoActividad=" + idTipoActividad + ", idCliente=" + idCliente + "]";
	}
	
	
}
