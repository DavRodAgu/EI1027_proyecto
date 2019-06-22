package es.uji.ei1027.toopots.services;

import java.util.List;
import java.util.Map;

import es.uji.ei1027.toopots.model.Acreditacion;
import es.uji.ei1027.toopots.model.Actividad;
import es.uji.ei1027.toopots.model.Instructor;
import es.uji.ei1027.toopots.model.Reserva;
import es.uji.ei1027.toopots.model.TipoActividad;

public interface AdminService {
	public Map<Acreditacion, List<Object>> getSolicitudesPendientes(String tipoSolicitud);
	public Map<Acreditacion, TipoActividad> getSolicitudesByInstructor(String idInstructor);
	public Map<Actividad, List<Object>> getActividades();
	public List<Object> getActividad(String idActividad);
	public Map<Reserva, String> getReservasActividad(String idActividad);
	public Map<Actividad, List<Object>> getActividadesInstructor(String idInstructor);
 }
