package es.uji.ei1027.toopots.model;

public class Acreditacion {
	private int idAcreditacion;
	private String certificado;
	private String estado;
	private String idInstructor;
	
	
	public int getIdAcreditacion() {
		return idAcreditacion;
	}
	public void setIdAcreditacion(int idAcreditacion) {
		this.idAcreditacion = idAcreditacion;
	}
	public String getCertificado() {
		return certificado;
	}
	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getIdInstructor() {
		return idInstructor;
	}
	public void setIdInstructor(String idInstructor) {
		this.idInstructor = idInstructor;
	}
	
	
	@Override
	public String toString() {
		return "Acreditacion [idAcreditacion=" + idAcreditacion + ", certificado=" + certificado + ", estado=" + estado
				+ ", idInstructor=" + idInstructor + "]";
	}
	
	
}
