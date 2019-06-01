package es.uji.ei1027.toopots.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import es.uji.ei1027.toopots.dao.ActividadDao;
import es.uji.ei1027.toopots.dao.ClienteDao;
import es.uji.ei1027.toopots.dao.InstructorDao;
import es.uji.ei1027.toopots.model.Login;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private ClienteDao clienteDao;
	private InstructorDao instructorDao;
	private ActividadDao actividadDao;
	
	@Autowired
	public void setClienteDao(ClienteDao clienteDao) {
		this.clienteDao = clienteDao;
	}
	
	@Autowired
	public void setInstructorDao(InstructorDao instructorDao) {
		this.instructorDao = instructorDao;
	}
	
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
		
		return "admin/solicitudes";
	}
	
	@RequestMapping(value = "/clientes")
	public String listaClientes(HttpSession session, Model model) {
		if (session.getAttribute("user") == null) {
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
		model.addAttribute("actividades", actividadDao.getActividades());
		return "admin/actividades";
	}
}
