package es.uji.ei1027.toopots.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.ei1027.toopots.dao.ActividadDao;
import es.uji.ei1027.toopots.dao.ClienteDao;
import es.uji.ei1027.toopots.dao.ReservaDao;
import es.uji.ei1027.toopots.model.Actividad;
import es.uji.ei1027.toopots.model.Reserva;

@Service
public class InstructorSvc implements InstructorService {

	@Autowired
	private ActividadDao actividadDao;
	
	@Autowired
	private ReservaDao reservaDao;
	
	@Autowired
	private ClienteDao clienteDao;
	
	
	@Override
	public Map<Actividad, Integer> getNumReservas(String idInstructor) {
		// Se obtienen las actividades de un instructor
		List<Actividad> actividades = actividadDao.getActividadesByInstructor(idInstructor);
		HashMap<Actividad, Integer> asistentesActividad = new HashMap<Actividad, Integer>();
		
		// Se obtienen los asistentes de cada actividad
		int numAsistentes;
		for (Actividad actv : actividades) {
			List<Reserva> reservasActividad = reservaDao.getNumReservasActividad(actv.getIdActividad());
			numAsistentes = 0;
			for (Reserva rsrv: reservasActividad) {
				numAsistentes += rsrv.getNumAsistentes();
			}
			asistentesActividad.put(actv,  numAsistentes);
		}
		return asistentesActividad;
	}

	@Override
	public Map<Reserva, String> getReservasByActividad(String idActividad) {
		List<Reserva> reservas = reservaDao.getNumReservasActividad(Integer.valueOf(idActividad));
		
		HashMap<Reserva, String> reservasClientes = new HashMap<Reserva, String>();
		for (Reserva rsrv : reservas) {
			String nombre = clienteDao.getCliente(rsrv.getIdCliente()).getNombre();
			reservasClientes.put(rsrv, nombre);
		}
		return reservasClientes;
	}

}
