package es.uji.ei1027.toopots.model;

public class ImagenPromocional {
	private int idImagen;
	private String imagen;
	private int idActividad;
	
	
	public int getIdImagen() {
		return idImagen;
	}
	public void setIdImagen(int idImagen) {
		this.idImagen = idImagen;
	}
	
	
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	
	public int getIdActividad() {
		return idActividad;
	}
	public void setIdActividad(int idActividad) {
		this.idActividad = idActividad;
	}
	
	
	@Override
	public String toString() {
		return "ImagenPromocional [idImagen=" + idImagen + ", imagen=" + imagen + ", idActividad=" + idActividad + "]";
	}
	
	
}
