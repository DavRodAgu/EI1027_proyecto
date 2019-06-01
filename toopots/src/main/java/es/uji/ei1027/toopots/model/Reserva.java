package es.uji.ei1027.toopots.model;

import java.sql.Timestamp;

public class Reserva implements Comparable<Reserva> {
	private int idReserva;
	private String estadoPago;
	private int numTransaccion;
	private Timestamp fecha;
	private int numAsistentes;
	private float precioPorPersona;
	private int idActividad;
	private String idCliente;
	

	public Reserva() {
		super();
	}
	public Reserva(Timestamp fecha, float precioPorPersona, int idActividad) {
		super();
		this.fecha = fecha;
		this.precioPorPersona = precioPorPersona;
		this.idActividad = idActividad;
	}
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
	public Timestamp getFecha() {
		return fecha;
	}
	public void setFecha(Timestamp fecha) {
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
	
	@Override
	public int compareTo(Reserva altre) {
		// Cambiamos el orden
		int x = this.getFecha().compareTo(altre.getFecha());
		return -x;
	}
}
