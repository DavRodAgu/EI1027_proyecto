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
import es.uji.ei1027.toopots.dao.InstructorDao;
import es.uji.ei1027.toopots.dao.ReservaDao;
import es.uji.ei1027.toopots.dao.TipoActividadDao;
import es.uji.ei1027.toopots.model.Acredita;
import es.uji.ei1027.toopots.model.Acreditacion;
import es.uji.ei1027.toopots.model.Actividad;
import es.uji.ei1027.toopots.model.Instructor;
import es.uji.ei1027.toopots.model.Reserva;
import es.uji.ei1027.toopots.model.TipoActividad;

@Service
public class AdminSvc implements AdminService {

	@Autowired
	private InstructorDao instructorDao;
	
	@Autowired
	private AcreditacionDao acreditacionDao;
	
	@Autowired
	private AcreditaDao acreditaDao;
	
	@Autowired
	private TipoActividadDao tipoActividadDao;
	
	@Autowired
	private ActividadDao actividadDao;
	
	@Autowired
	private ReservaDao reservaDao;
	
	@Autowired
	private ClienteDao clienteDao;
	
	@Override
	public Map<Acreditacion, List<Object>> getSolicitudesPendientes(String tipoSolicitud) {
		List<Acreditacion> acreditaciones = acreditacionDao.getAcreditaciones();
		Map<Acreditacion, List<Object>> solicitudes = new HashMap<Acreditacion, List<Object>>();
		
		List<Object> objetos = new ArrayList<Object>();
		switch(tipoSolicitud) {
			case "nuevas":
				for (Acreditacion acr: acreditaciones) {
					if (acr.getEstado().equals("pendiente")) {
						Instructor instructor = instructorDao.getInstructor(acr.getIdInstructor());
						if (instructor.getEstado().equals("aceptada") || instructor.getEstado().equals("pendiente")) {
							objetos.add(instructor);
							int idTipoActividad = acreditaDao.getAcredita(acr.getIdAcreditacion()).getIdTipoActividad();
							objetos.add(tipoActividadDao.getTipoActividad(Integer.toString(idTipoActividad)));
							solicitudes.put(acr, new ArrayList<Object>(objetos));
						}
					}
					objetos.clear();
				}
				break;
			case "aceptadas":
				for (Acreditacion acr: acreditaciones) {
					if (acr.getEstado().equals("aceptada")) {
						Instructor instructor = instructorDao.getInstructor(acr.getIdInstructor());
						if (instructor.getEstado().equals("aceptada")) {
							objetos.add(instructor);
							int idTipoActividad = acreditaDao.getAcredita(acr.getIdAcreditacion()).getIdTipoActividad();
							objetos.add(tipoActividadDao.getTipoActividad(Integer.toString(idTipoActividad)));
							solicitudes.put(acr, new ArrayList<Object>(objetos));
						}
					}
					objetos.clear();
				}
				break;
			case "rechazadas":
				for (Acreditacion acr: acreditaciones) {
					if (acr.getEstado().equals("rechazada")) {
						Instructor instructor = instructorDao.getInstructor(acr.getIdInstructor());
						if (instructor.getEstado().equals("aceptada") || instructor.getEstado().equals("rechazada")) {
							objetos.add(instructor);
							int idTipoActividad = acreditaDao.getAcredita(acr.getIdAcreditacion()).getIdTipoActividad();
							objetos.add(tipoActividadDao.getTipoActividad(Integer.toString(idTipoActividad)));
							solicitudes.put(acr, new ArrayList<Object>(objetos));
						}
					}
					objetos.clear();
				}
				break;
		}
		
		return solicitudes;
	}

	@Override
	public Map<Acreditacion, TipoActividad> getSolicitudesByInstructor(String idInstructor) {
		List<Acreditacion> acreditaciones = acreditacionDao.getAcreditacionByIdInstructor(idInstructor);
		Map<Acreditacion, TipoActividad> solicitudes = new HashMap<Acreditacion, TipoActividad>();
		
		Acredita acredita = null;
		for (Acreditacion acr: acreditaciones) {
			acredita = acreditaDao.getAcredita(acr.getIdAcreditacion());
			solicitudes.put(acr, tipoActividadDao.getTipoActividad(Integer.toString(acredita.getIdTipoActividad())));
		}		
		return solicitudes;
	}

	@Override
	public Map<Actividad, List<Object>> getActividades() {
		List<Actividad> actividades = actividadDao.getActividades();
		Map<Actividad, List<Object>> res = new HashMap<Actividad, List<Object>>();
		
		List<Object> objetos = new ArrayList<Object>();
		for (Actividad actividad: actividades) {
			objetos.add(tipoActividadDao.getTipoActividad(Integer.toString(actividad.getIdTipoActividad())));
			objetos.add(instructorDao.getInstructor(actividad.getIdInstructor()));
			objetos.add(reservaDao.getNumReservasActividad(actividad.getIdActividad()).size());
			res.put(actividad, new ArrayList<Object>(objetos));
			objetos.clear();
		}
		return res;
	}

	@Override
	public List<Object> getActividad(String idActividad) {
		List<Object> res = new ArrayList<Object>();
		Actividad actividad = actividadDao.getActividad(idActividad);
		TipoActividad tipo = tipoActividadDao.getTipoActividad(Integer.toString(actividad.getIdTipoActividad()));
		Instructor instructor = instructorDao.getInstructor(actividad.getIdInstructor());
		
		res.add(actividad);
		res.add(tipo);
		res.add(instructor);
		res.add(reservaDao.getNumReservasActividad(actividad.getIdActividad()).size());
		return res;
	}

	@Override
	public Map<Reserva, String> getReservasActividad(String idActividad) {
		List<Reserva> reservas = reservaDao.getNumReservasActividad(Integer.parseInt(idActividad));
		Map<Reserva, String> res = new HashMap<Reserva, String>();
		
		for (Reserva reserva: reservas) {
			res.put(reserva, clienteDao.getCliente(reserva.getIdCliente()).getNombre());
		}
		return res;
	}

	@Override
	public Map<Actividad, List<Object>> getActividadesInstructor(String idInstructor) {
		List<Actividad> actividades = actividadDao.getActividadesByInstructor(idInstructor);
		
		Map<Actividad, List<Object>> res = new HashMap<Actividad, List<Object>>();
		List<Object> obj = new ArrayList<Object>();
		
		for (Actividad actividad: actividades) {
			obj.add(tipoActividadDao.getTipoActividad(Integer.toString(actividad.getIdTipoActividad())));
			obj.add(reservaDao.getNumReservasActividad(actividad.getIdActividad()).size());
			
			res.put(actividad, new ArrayList<Object>(obj));
			obj.clear();
		}
		return res;
	}

}
