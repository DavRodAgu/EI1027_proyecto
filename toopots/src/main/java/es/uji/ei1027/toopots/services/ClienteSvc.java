package es.uji.ei1027.toopots.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.ei1027.toopots.dao.ActividadDao;
import es.uji.ei1027.toopots.dao.InstructorDao;
import es.uji.ei1027.toopots.dao.PrefiereDao;
import es.uji.ei1027.toopots.dao.ReservaDao;
import es.uji.ei1027.toopots.dao.TipoActividadDao;
import es.uji.ei1027.toopots.model.Actividad;
import es.uji.ei1027.toopots.model.Prefiere;
import es.uji.ei1027.toopots.model.Reserva;

@Service
public class ClienteSvc implements ClienteService {
	
	@Autowired
	private ActividadDao actividadDao;
	
	@Autowired
	private ReservaDao reservaDao;
	
	@Autowired
	private PrefiereDao prefiereDao;
	
	@Autowired
	private InstructorDao instructorDao;
	
	@Autowired
	private TipoActividadDao tipoActividadDao;
	
	@Override
	public Map<Reserva, List<Object>> getReservaByClient(String idCliente) {
		List<Reserva> reservas = reservaDao.getReservasUsuario(idCliente);
		HashMap<Reserva, List<Object>> nombreActividadesReservadas = new HashMap<Reserva, List<Object>>();
		List<Object> objetos = new ArrayList<Object>();
		for (Reserva rsrv : reservas) {
			String idActividad = Integer.toString(rsrv.getIdActividad());
			Actividad actividad = actividadDao.getActividad(idActividad);
			String nombreInstructor = instructorDao.getInstructor(actividad.getIdInstructor()).getNombre();
			objetos.add(actividad);
			objetos.add(nombreInstructor);
			nombreActividadesReservadas.put(rsrv, new ArrayList<Object>(objetos));
			objetos.clear();
		}
		return nombreActividadesReservadas;
	}

	@Override
	public Map<Actividad, List<Object>> getActividadByPreference(String idCliente, boolean pref) {
		List<Actividad> actividades = new ArrayList<Actividad>();
		if (pref) {			
			List<Prefiere> preferencias = prefiereDao.getPreferenciasCliente(idCliente);
			// Se obtienen las actividades según la preferencia
			for (Prefiere prf : preferencias) {
				actividades.addAll(actividadDao.getActividadesByType(prf.getIdTipoActividad()));
			}
		} else {
			actividades.addAll(actividadDao.getActividades());
		}
		HashMap<Actividad, List<Object>> actividadByReserva = new HashMap<Actividad, List<Object>>();
		List<Object> objetos = new ArrayList<Object>();
		int numReservas;
		// Se comprueba que actividades están ya reservadas
		for (Actividad actv : actividades) {
			if(reservaDao.getIfReservada(idCliente, actv.getIdActividad()) == null) {
				objetos.add(false);
			} else {
				objetos.add(true);
			}
			objetos.add(instructorDao.getInstructor(actv.getIdInstructor()).getNombre());
			// Obtener número de reservas
			List<Reserva> reservasActividad = reservaDao.getNumReservasActividad(actv.getIdActividad());
			numReservas = 0;
			for (Reserva rsrv: reservasActividad) {
				numReservas += rsrv.getNumAsistentes();
			}
			objetos.add(numReservas);
			// Obtener nombre tipo de actividad
			objetos.add(tipoActividadDao.getTipoActividad(Integer.toString(actv.getIdTipoActividad())).getNombre());
			// Obtener nivel del tipo de actividad
			objetos.add(tipoActividadDao.getTipoActividad(Integer.toString(actv.getIdTipoActividad())).getNivel());
			actividadByReserva.put(actv, new ArrayList<Object>(objetos));
			objetos.clear();
		}
		return actividadByReserva;
	}
	
	
	@Override
	public Map<Integer, Actividad> getActividadByClient(String idCliente) {
		List<Reserva> reservas = reservaDao.getReservasUsuario(idCliente);
		HashMap<Integer, Actividad> idActividad_Actividad = new HashMap<Integer, Actividad>();
		for (Reserva rsrv : reservas) {
			Actividad actividad = actividadDao.getActividad(Integer.toString(rsrv.getIdActividad()));
			idActividad_Actividad.put(rsrv.getIdActividad(), actividad);
		}
		return idActividad_Actividad;
	}

}
