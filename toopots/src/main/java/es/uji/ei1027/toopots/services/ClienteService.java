package es.uji.ei1027.toopots.services;

import java.util.Map;

import es.uji.ei1027.toopots.model.Actividad;
import es.uji.ei1027.toopots.model.Reserva;

import java.util.List;

public interface ClienteService {
	public Map<Reserva, List<Object>> getReservaByClient(String idCliente);
	public Map<Actividad, List<Object>> getActividadByPreference(String idCliente, boolean pref);
	Map<Integer, Actividad> getActividadByClient(String idCliente);
}
