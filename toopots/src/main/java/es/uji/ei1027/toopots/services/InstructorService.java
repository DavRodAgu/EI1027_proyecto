package es.uji.ei1027.toopots.services;

import java.util.List;
import java.util.Map;

import es.uji.ei1027.toopots.model.Acreditacion;
import es.uji.ei1027.toopots.model.Actividad;
import es.uji.ei1027.toopots.model.Reserva;
import es.uji.ei1027.toopots.model.TipoActividad;

public interface InstructorService {
	public Map<Actividad, Integer> getNumReservas(String idInstructor);
	public Map<Reserva, String> getReservasByActividad(String idActividad);
	public Map<Acreditacion, TipoActividad> getSolicitudesByInstructor(String idInstructor);
	public List<Integer> getTipoActividadInstructor(String idInstructor);
	public List<TipoActividad> getTiposAcreditados(String idInstructor);
}
