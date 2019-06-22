package es.uji.ei1027.toopots.services;

import java.util.Map;

import es.uji.ei1027.toopots.model.Actividad;
import es.uji.ei1027.toopots.model.Comentario;
import es.uji.ei1027.toopots.model.Reserva;

import java.util.List;

public interface ClienteService {
	public Map<Reserva, List<Object>> getReservaByClient(String idCliente);
	public Map<Actividad, List<Object>> getActividadByPreference(String idCliente, boolean pref);
	public List<Object> getActividad(String idCliente, String idActividad);
	public Map<Comentario, String> getComentarios(String idActividad);
}
