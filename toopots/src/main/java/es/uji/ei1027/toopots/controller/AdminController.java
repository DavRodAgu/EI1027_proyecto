package es.uji.ei1027.toopots.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import es.uji.ei1027.toopots.dao.AcreditaDao;
import es.uji.ei1027.toopots.dao.AcreditacionDao;
import es.uji.ei1027.toopots.dao.ActividadDao;
import es.uji.ei1027.toopots.dao.ClienteDao;
import es.uji.ei1027.toopots.dao.InstructorDao;
import es.uji.ei1027.toopots.dao.ReservaDao;
import es.uji.ei1027.toopots.dao.TipoActividadDao;
import es.uji.ei1027.toopots.model.Acredita;
import es.uji.ei1027.toopots.model.Acreditacion;
import es.uji.ei1027.toopots.model.Login;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private ClienteDao clienteDao;
	private InstructorDao instructorDao;
	private ActividadDao actividadDao;
	private TipoActividadDao tipoActividadDao;
	private AcreditaDao acreditaDao;
	private AcreditacionDao acreditacionDao;
	private ReservaDao reservaDao;
	
	@Autowired
	public void setTipoActividadDao(TipoActividadDao tipoActividadDao) {
		this.tipoActividadDao = tipoActividadDao;
	}
	
	@Autowired
	public void setReservaDao(ReservaDao reservaDao) {
		this.reservaDao = reservaDao;
	}
	
	@Autowired
	public void setAcreditacionDao(AcreditacionDao acreditacionDao) {
		this.acreditacionDao = acreditacionDao;
	}
	
	@Autowired
	public void setAcreditaDao(AcreditaDao acreditaDao) {
		this.acreditaDao = acreditaDao;
	}
	
	@Autowired
	public void setClienteDao(ClienteDao clienteDao) {
		this.clienteDao = clienteDao;
	}
	
	@Autowired
	public void setInstructorDao(InstructorDao instructorDao) {
		this.instructorDao = instructorDao;
	}
	
	@Autowired
	public void setActividadDao(ActividadDao actividadDao) {
		this.actividadDao = actividadDao;
	}

	@RequestMapping(value = "/solicitudes")
	public String listaSolicitudes(HttpSession session, Model model) {
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "admin/solicitudes");
			return "login";
		}
		
		Login usuario = (Login) session.getAttribute("user");
		if (!usuario.getRol().equals("administrador")) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "admin/solicitudes");
			return "login";
		}
		
		List<Acredita> solicitudes = acreditaDao.getAcreditaciones();
		LinkedHashMap<Acredita, Acreditacion> diccionario = new LinkedHashMap<Acredita, Acreditacion>();
		LinkedHashMap<Integer, String> diccionario2 = new LinkedHashMap<Integer, String>();
		for (Acredita sol : solicitudes) {
			diccionario.put(sol, acreditacionDao.getAcreditacion(sol.getIdAcreditacion()));
			diccionario2.put(sol.getIdTipoActividad(), tipoActividadDao.getTipoActividad("" + sol.getIdTipoActividad()).getNombre());
		}
		model.addAttribute("solicitudes", diccionario);
		model.addAttribute("tipos", diccionario2);
		return "admin/solicitudes";
	}
	
	@RequestMapping(value = "/clientes")
	public String listaClientes(HttpSession session, Model model) {
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "admin/clientes");
			return "login";
		}
		
		Login usuario = (Login) session.getAttribute("user");
		if (!usuario.getRol().equals("administrador")) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "admin/clientes");
			return "login";
		}
		
		model.addAttribute("clientes", clienteDao.getClientes());
		return "admin/clientes";
	}
	
	@RequestMapping(value = "/instructores")
	public String listaInstructores(HttpSession session, Model model) {
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "admin/instructores");
			return "login";
		}
		
		Login usuario = (Login) session.getAttribute("user");
		if (!usuario.getRol().equals("administrador")) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "admin/instructores");
			return "login";
		}
		
		model.addAttribute("instructores", instructorDao.getInstructores());
		return "admin/instructores";
	}
	
	@RequestMapping(value = "/actividades")
	public String listaActividades(HttpSession session, Model model) {
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "admin/actividades");
			return "login";
		}
		
		Login usuario = (Login) session.getAttribute("user");
		if (!usuario.getRol().equals("administrador")) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "admin/actividades");
			return "login";
		}
		
		model.addAttribute("actividades", actividadDao.getActividades());
		return "admin/actividades";
	}
	
	@RequestMapping(value = "/reservas/{idActividad}")
	public String listaReservass(HttpSession session, Model model, @PathVariable int idActividad) {
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "admin/reservas");
			return "login";
		}
		
		Login usuario = (Login) session.getAttribute("user");
		if (!usuario.getRol().equals("administrador")) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "admin/reservas");
			return "login";
		}
		
		model.addAttribute("reservas", reservaDao.getReservasActividad(idActividad));
		return "admin/reservas";
	}
}
