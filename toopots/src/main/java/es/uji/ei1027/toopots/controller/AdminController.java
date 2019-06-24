package es.uji.ei1027.toopots.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.jboss.logging.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.uji.ei1027.toopots.dao.AcreditaDao;
import es.uji.ei1027.toopots.dao.AcreditacionDao;
import es.uji.ei1027.toopots.dao.ActividadDao;
import es.uji.ei1027.toopots.dao.ClienteDao;
import es.uji.ei1027.toopots.dao.ImagenPromocionalDao;
import es.uji.ei1027.toopots.dao.InstructorDao;
import es.uji.ei1027.toopots.dao.ReservaDao;
import es.uji.ei1027.toopots.dao.TipoActividadDao;
import es.uji.ei1027.toopots.model.Acredita;
import es.uji.ei1027.toopots.model.Acreditacion;
import es.uji.ei1027.toopots.model.Instructor;
import es.uji.ei1027.toopots.model.Login;
import es.uji.ei1027.toopots.model.TipoActividad;
import es.uji.ei1027.toopots.services.AdminService;

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
	private AdminService adminService;
	private ImagenPromocionalDao imagenPromocionalDao;
	
	@Autowired
	public void setTipoActividadDao(TipoActividadDao tipoActividadDao) {
		this.tipoActividadDao = tipoActividadDao;
	}

	@Autowired
	public void setImagenPromocionalDao(ImagenPromocionalDao imagenPromocionalDao) {
		this.imagenPromocionalDao = imagenPromocionalDao;
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

	@Autowired
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
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
			return "error/error";
		}
		
		model.addAttribute("solicitudes", adminService.getSolicitudesPendientes("nuevas"));
		model.addAttribute("tipo", "nuevas");
		return "admin/solicitudes";
	}
	
	@RequestMapping(value = "/solicitudes/{tipo}")
	public String listaSolicitudesPorTipo(@PathVariable String tipo, HttpSession session, Model model) {
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "admin/solicitudes");
			return "login";
		}
		
		Login usuario = (Login) session.getAttribute("user");
		if (!usuario.getRol().equals("administrador")) {
			return "error/error";
		}
		
		model.addAttribute("solicitudes", adminService.getSolicitudesPendientes(tipo));
		model.addAttribute("tipo", tipo);
		return "admin/solicitudes";
	}
	
	@RequestMapping(value = "/solicitudes/{idAcreditacion}/certificado")
	public ResponseEntity<byte[]> MostrarCertificado(@PathVariable int idAcreditacion) throws FileNotFoundException {
		HttpHeaders headers = new HttpHeaders();

	    headers.setContentType(MediaType.parseMediaType("application/pdf"));
	    String filename = acreditacionDao.getAcreditacion(idAcreditacion).getCertificado();
	    
	    // Para mostrar PDF en el navegador
	    headers.add("content-disposition", "inline;filename=" + filename);
	    // Para descargar el PDF
	    // headers.add("Content-Disposition", "attachment; filename=" + filename);
	    
	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
	    
	    // Convertir File a byte[]
	    File file = new File(filename);
	    byte[] bytesArray = new byte[(int) file.length()];
	    
	    FileInputStream fis = new FileInputStream(file);
	    try {
			fis.read(bytesArray);
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	      
	    ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(bytesArray, headers, HttpStatus.OK);
	    return response;
	}
	
	@RequestMapping(value = "/solicitudes/{idAcreditacion}/{accion}")
	public String aceptarSolicitud(HttpSession session, Model model, @PathVariable int idAcreditacion, @PathVariable String accion,
			RedirectAttributes redirectAttributes) {
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "admin/solicitudes");
			return "login";
		}
		
		Login usuario = (Login) session.getAttribute("user");
		if (!usuario.getRol().equals("administrador")) {
			return "error/error";
		}
		
		Acreditacion acr = acreditacionDao.getAcreditacion(idAcreditacion);
		Instructor instructor = instructorDao.getInstructor(acr.getIdInstructor());
		
		if (accion.equals("aceptar")) {
			if (instructor.getEstado().equals("pendiente")) {
				instructor.setEstado("aceptada");
			}
			acr.setEstado("aceptada");
			redirectAttributes.addFlashAttribute("message", "Solicitud del instructor " + instructor.getNombre() + " aceptada");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		}
		
		if (accion.equals("rechazar")) {
			if (instructor.getEstado().equals("pendiente")) {
				instructor.setEstado("rechazada");
			}
			acr.setEstado("rechazada");
			redirectAttributes.addFlashAttribute("message", "Solicitud del instructor " + instructor.getNombre() + " rechazada");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
		}
		
		instructorDao.updateInstructor(instructor);
		acreditacionDao.updateAcreditacion(acr);
		
		return "redirect:/admin/solicitudes";
	}
	
	@RequestMapping("/tiposActividad")
	public String listTipoActividades(HttpSession session, Model model) {
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "admin/tiposActividad");
			return "login";
		}
		
		Login usuario = (Login) session.getAttribute("user");
		if (!usuario.getRol().equals("administrador")) {
			return "error/error";
		}
		model.addAttribute("tipoActividades", tipoActividadDao.getTipoActividades());
		return "admin/tiposActividad";
	}

	@RequestMapping(value = "/tiposActividad/add")
	public String addTipoActividad(HttpSession session, Model model) {
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "admin/tiposActividad/add");
			return "login";
		}
		
		Login usuario = (Login) session.getAttribute("user");
		if (!usuario.getRol().equals("administrador")) {
			return "error/error";
		}
		model.addAttribute("tipoActividad", new TipoActividad());
		return "admin/anadirTipoActividad";
	}

	@RequestMapping(value = "/tiposActividad/add", method = RequestMethod.POST)
	public String processAddSubmit(@ModelAttribute("tipoActividad") TipoActividad tipoActividad,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "tipoActividad/add";
		tipoActividadDao.addTipoActividad(tipoActividad);
		return "redirect:/tiposActividad";
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
			return "error/error";
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
			return "error/error";
		}
		
		model.addAttribute("instructores", instructorDao.getInstructores());
		return "admin/instructores";
	}
	
	@RequestMapping(value = "/instructor/{idInstructor}")
	public String infoInstructor(HttpSession session, Model model, @PathVariable String idInstructor) {
		
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "admin/instructores");
			return "login";
		}
		
		Login usuario = (Login) session.getAttribute("user");
		if (!usuario.getRol().equals("administrador")) {
			return "error/error";
		}
		
		model.addAttribute("instructor", instructorDao.getInstructor(idInstructor));
		model.addAttribute("solicitudes", adminService.getSolicitudesByInstructor(idInstructor));
		model.addAttribute("actividades", adminService.getActividadesInstructor(idInstructor));
		return "admin/instructor";
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
			return "error/error";
		}
		
		model.addAttribute("actividades", adminService.getActividades());
		return "admin/actividades";
	}
	
	@RequestMapping(value = "/actividad/{idActividad}")
	public String infoActividad(HttpSession session, Model model, @PathVariable String idActividad) {
		
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "admin/actividades");
			return "login";
		}
		
		Login usuario = (Login) session.getAttribute("user");
		if (!usuario.getRol().equals("administrador")) {
			return "error/error";
		}

		model.addAttribute("imagen", imagenPromocionalDao.getImagen(idActividad));
		model.addAttribute("actividad", adminService.getActividad(idActividad));
		model.addAttribute("reservas", adminService.getReservasActividad(idActividad));
		return "admin/actividad";
	}
}
