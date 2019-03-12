package es.uji.ei1027.toopots.model;

import java.sql.Date;

public class Reserva {
	private int idReserva;
	private String estadoPago;
	private int numTransaccion;
	private Date fecha;
	private int numAsistentes;
	private float precioPorPersona;
	private int idActividad;
	private String idCliente;
	
	
	public int getIdReserva() {
		return idReserva;
	}
	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
	}
	public String getEstadoPago() {
		return estadoPago;
	}
	public void setEstadoPago(String estadoPago) {
		this.estadoPago = estadoPago;
	}
	public int getNumTransaccion() {
		return numTransaccion;
	}
	public void setNumTransaccion(int numTransaccion) {
		this.numTransaccion = numTransaccion;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public int getNumAsistentes() {
		return numAsistentes;
	}
	public void setNumAsistentes(int numAsistentes) {
		this.numAsistentes = numAsistentes;
	}
	public float getPrecioPorPersona() {
		return precioPorPersona;
	}
	public void setPrecioPorPersona(float precioPorPersona) {
		this.precioPorPersona = precioPorPersona;
	}
	public int getIdActividad() {
		return idActividad;
	}
	public void setIdActividad(int idActividad) {
		this.idActividad = idActividad;
	}
	public String getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	
	
	@Override
	public String toString() {
		return "Reserva [idReserva=" + idReserva + ", estadoPago=" + estadoPago + ", numTransaccion=" + numTransaccion
				+ ", fecha=" + fecha + ", numAsistentes=" + numAsistentes + ", precioPorPersona=" + precioPorPersona
				+ ", idActividad=" + idActividad + ", idCliente=" + idCliente + "]";
	}
	
	
}
