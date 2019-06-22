package es.uji.ei1027.toopots.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.ei1027.toopots.dao.AcreditaDao;
import es.uji.ei1027.toopots.dao.AcreditacionDao;
import es.uji.ei1027.toopots.dao.ActividadDao;
import es.uji.ei1027.toopots.dao.ClienteDao;
import es.uji.ei1027.toopots.dao.ReservaDao;
import es.uji.ei1027.toopots.dao.TipoActividadDao;
import es.uji.ei1027.toopots.model.Acredita;
import es.uji.ei1027.toopots.model.Acreditacion;
import es.uji.ei1027.toopots.model.Actividad;
import es.uji.ei1027.toopots.model.Reserva;
import es.uji.ei1027.toopots.model.TipoActividad;

@Service
public class InstructorSvc implements InstructorService {

	@Autowired
	private ActividadDao actividadDao;
	
	@Autowired
	private ReservaDao reservaDao;
	
	@Autowired
	private ClienteDao clienteDao;
	
	@Autowired
	private AcreditacionDao acreditacionDao;
	
	@Autowired
	private AcreditaDao acreditaDao;
	
	@Autowired
	private TipoActividadDao tipoActividadDao;
	
	@Override
	public Map<Actividad, Integer> getNumReservas(String idInstructor) {
		// Se obtienen las actividades de un instructor
		List<Actividad> actividades = actividadDao.getActividadesByInstructor(idInstructor);
		HashMap<Actividad, Integer> asistentesActividad = new HashMap<Actividad, Integer>();
		
		// Se obtienen los asistentes de cada actividad
		int numAsistentes;
		for (Actividad actv : actividades) {
			if (!(actv.getEstado().equals("cancelada"))) {
				List<Reserva> reservasActividad = reservaDao.getNumReservasActividad(actv.getIdActividad());
				numAsistentes = 0;
				for (Reserva rsrv: reservasActividad) {
					numAsistentes += rsrv.getNumAsistentes();
				}
				asistentesActividad.put(actv,  numAsistentes);
			}
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

	@Override
	public Map<Acreditacion, TipoActividad> getSolicitudesByInstructor(String idInstructor) {
		List<Acreditacion> acreditaciones = acreditacionDao.getAcreditacionByIdInstructor(idInstructor);
		Map<Acreditacion, TipoActividad> solicitudes = new HashMap<Acreditacion, TipoActividad>();
		
		for (Acreditacion acr: acreditaciones) {
			Acredita acredita = acreditaDao.getAcredita(acr.getIdAcreditacion());
			solicitudes.put(acr, tipoActividadDao.getTipoActividad(Integer.toString(acredita.getIdTipoActividad())));
		}
		return solicitudes;
	}

	@Override
	public List<Integer> getTipoActividadInstructor(String idInstructor) {
		List<Acreditacion> acreditaciones = acreditacionDao.getAcreditacionByIdInstructor(idInstructor);
		List<Integer> tiposAcreditados = new ArrayList<Integer>();
		
		for (Acreditacion acr: acreditaciones) {
			Acredita acredita = acreditaDao.getAcredita(acr.getIdAcreditacion());
			tiposAcreditados.add(tipoActividadDao.getTipoActividad(Integer.toString(acredita.getIdTipoActividad())).getIdTipoActividad());
		}
		return tiposAcreditados;
	}

	@Override
	public List<TipoActividad> getTiposAcreditados(String idInstructor) {
		List<Acreditacion> acreditaciones = acreditacionDao.getAcreditacionByIdInstructor(idInstructor);
		List<TipoActividad> tiposAcreditados = new ArrayList<TipoActividad>();
		
		for (Acreditacion acr: acreditaciones) {
			if (acr.getEstado().equals("aceptada")) {
				Acredita acredita = acreditaDao.getAcredita(acr.getIdAcreditacion());
				tiposAcreditados.add(tipoActividadDao.getTipoActividad(Integer.toString(acredita.getIdTipoActividad())));
			}
		}
		return tiposAcreditados;
	}

}
