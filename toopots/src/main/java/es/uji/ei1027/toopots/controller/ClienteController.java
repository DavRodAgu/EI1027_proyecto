package es.uji.ei1027.toopots.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.uji.ei1027.toopots.dao.ActividadDao;
import es.uji.ei1027.toopots.dao.ClienteDao;
import es.uji.ei1027.toopots.dao.ComentarioDao;
import es.uji.ei1027.toopots.dao.ImagenPromocionalDao;
import es.uji.ei1027.toopots.dao.LoginDao;
import es.uji.ei1027.toopots.dao.PrefiereDao;
import es.uji.ei1027.toopots.dao.ReservaDao;
import es.uji.ei1027.toopots.dao.TipoActividadDao;
import es.uji.ei1027.toopots.model.Actividad;
import es.uji.ei1027.toopots.model.Cliente;
import es.uji.ei1027.toopots.model.Comentario;
import es.uji.ei1027.toopots.model.Login;
import es.uji.ei1027.toopots.model.Prefiere;
import es.uji.ei1027.toopots.model.Reserva;
import es.uji.ei1027.toopots.services.ClienteService;

class ClienteValidator implements Validator { 
	@Override
	public boolean supports(Class<?> cls) { 
		return Cliente.class.isAssignableFrom(cls);
	}
	@Override 
	public void validate(Object obj, Errors errors) {
		Cliente cliente = (Cliente)obj;
		// TODO: Añadir comprobaciones
	}
}


@Controller
@RequestMapping("/cliente")
public class ClienteController {

	private ClienteDao clienteDao;
	private ActividadDao actividadDao;
	private ClienteService clienteService;
	private LoginDao loginDao;
	private TipoActividadDao tipoActividadDao;
	private PrefiereDao prefiereDao;
	private ImagenPromocionalDao imagenPromocionalDao;
	private ReservaDao reservaDao;
	private ComentarioDao comentarioDao;
	
	@Autowired
	public void setClienteDao(ClienteDao clienteDao) {
		this.clienteDao = clienteDao;
	}

	@Autowired
	public void setActividadDao(ActividadDao actividadDao) {
		this.actividadDao = actividadDao;
	}
	
	@Autowired
	public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
	}
	
	@Autowired
	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;
	}
	
	@Autowired
	public void setTipoActividadDao(TipoActividadDao tipoActividadDao) {
		this.tipoActividadDao = tipoActividadDao;
	}

	@Autowired
	public void setPrefiereDao(PrefiereDao prefiereDao) {
		this.prefiereDao = prefiereDao;
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
	public void setComentarioDao(ComentarioDao comentarioDao) {
		this.comentarioDao = comentarioDao;
	}
	
	
	@RequestMapping("/actividades")
	public String listActividades(HttpSession session, Model model) {
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "cliente/actividades");
			return "login";
		}
		
		Login usuario = (Login) session.getAttribute("user");
		if (!(usuario.getRol().equals("cliente"))) {
			return "error/error";
		}
		// Comprobar que tipo de vista a seleccionado el usuario
		model.addAttribute("actividades", clienteService.getActividadByPreference(usuario.getUsuario(), true));
		model.addAttribute("preferencias", true);
		return "cliente/actividades";
	}
	
	@RequestMapping("/actividades/{preferencias}")
	public String listActividadesPref(@PathVariable boolean preferencias, HttpSession session, Model model) {
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "cliente/actividades");
			return "login";
		}
		
		Login usuario = (Login) session.getAttribute("user");
		if (!usuario.getRol().equals("cliente")) {
			return "error/error";
		}
		model.addAttribute("actividades", clienteService.getActividadByPreference(usuario.getUsuario(), preferencias));
		model.addAttribute("preferencias", preferencias);
		return "cliente/actividades";
	}
	
	@RequestMapping("/actividad/{idActividad}")
	public String infoActividad(@PathVariable String idActividad, HttpSession session, Model model) {
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "cliente/actividad/" + idActividad);
			return "login";
		}
		
		Login usuario = (Login) session.getAttribute("user");
		if (!usuario.getRol().equals("cliente")) {
			return "error/error";
		}
		
		model.addAttribute("actividad", clienteService.getActividad(usuario.getUsuario(), idActividad));
		model.addAttribute("imagen", imagenPromocionalDao.getImagen(idActividad));
		model.addAttribute("comentarios", clienteService.getComentarios(idActividad));
		return "cliente/actividad";
	}
	
	@RequestMapping(value = "/anadirReserva", method = RequestMethod.POST)
	public String processAñadirSubmit(Model model, HttpSession session, @ModelAttribute("reserva") Reserva reserva,
			@RequestParam(name = "idActividad") String idActividad, @RequestParam(name = "nPersonas") int nPersonas,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "cliente/actividades");
			return "login";
		}
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("message", "Error reservando actividad");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "cliente/actividades";
		}

		Actividad actividad = actividadDao.getActividad(idActividad);
		reserva.setNumAsistentes(nPersonas);
		Login usuario = (Login) session.getAttribute("user");
		reserva.setIdCliente(usuario.getUsuario());
		reserva.setEstadoPago("pendiente");
		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());
		reserva.setFecha(ts);
		reserva.setPrecioPorPersona(actividad.getPrecio());
		reservaDao.addReserva(reserva);
		
		redirectAttributes.addFlashAttribute("message", "Se ha reservado la actividad \"" + actividad.getNombre() + "\"");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		
		return "redirect:../cliente/actividad/" + idActividad;
	}
	
	@RequestMapping(value = "/actividad/{idActividad}/comentario", method = RequestMethod.POST)
	public String añadirComentario(HttpSession session, Model model, @PathVariable String idActividad, @RequestParam("valoracion") int valoracion, 
			@RequestParam("comment") String comment, RedirectAttributes redirectAttributes) {
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "cliente/actividad/" + idActividad);
			return "login";
		}
		
		Login usuario = (Login) session.getAttribute("user");
		if (!usuario.getRol().equals("cliente")) {
			return "error/error";
		}
		
		Comentario comentario = new Comentario();
		comentario.setIdActividad(Integer.parseInt(idActividad));
		comentario.setValoracion(valoracion);
		comentario.setComentario(comment);
		comentario.setIdCliente(usuario.getUsuario());
		
		comentarioDao.addComentario(comentario);
		
		redirectAttributes.addFlashAttribute("message", "Comentario añadido");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		
		return "redirect:/cliente/actividad/" + idActividad;
	}
	
	@RequestMapping("/reservas")
	public String listReservas(HttpSession session, Model model) {
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "cliente/reservas");
			return "login";
		}
		Login usuario = (Login) session.getAttribute("user");
		if (!usuario.getRol().equals("cliente")) {
			return "error/error";
		}
		model.addAttribute("reservas", clienteService.getReservaByClient(usuario.getUsuario()));
		return "cliente/reservas";
	}
	
	@RequestMapping("/perfil")
	public String perfilUsuario(HttpSession session, Model model) {
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "cliente/perfil");
			return "login";
		}
		Login usuario = (Login) session.getAttribute("user");
		if (!usuario.getRol().equals("cliente")) {
			return "error/error";
		}
		model.addAttribute("cliente", clienteDao.getCliente(usuario.getUsuario()));
		model.addAttribute("tipos", tipoActividadDao.getTipoActividades());
		List<Prefiere> prefiere = prefiereDao.getPreferenciasCliente(usuario.getUsuario());
		List<Integer> ids = new ArrayList<Integer>();
		for(Prefiere prf : prefiere) {
			ids.add(prf.getIdTipoActividad());
		}
		model.addAttribute("preferencias", ids);
		return "cliente/perfil";
	}
	
	@RequestMapping(value = "/perfil", method = RequestMethod.POST)
	public String prefiereUpdate(@RequestParam("prefiere") List<Integer> new_preferencias, HttpSession session,
			RedirectAttributes redirectAttributes) {
		// TODO: Código de mierda, pero funciona
		Login usuario = (Login) session.getAttribute("user");
		List<Prefiere> old_preferencias = prefiereDao.getPreferenciasCliente(usuario.getUsuario());
		if (old_preferencias.size() < new_preferencias.size()) {
			// Se han añadido preferencias
			Prefiere pref = null;
			for(int i = 0; i < new_preferencias.size(); i++) {
				if(prefiereDao.getPrefiere(new_preferencias.get(i).intValue(), usuario.getUsuario()) == null) {
					pref = new Prefiere();
					pref.setIdTipoActividad(new_preferencias.get(i).intValue());
					pref.setIdCliente(usuario.getUsuario());
					prefiereDao.addPrefiere(pref);
				}
			}			
		} else if (old_preferencias.size() > new_preferencias.size()) {
			// Se han borrado preferencias
			for(int i = 0; i < old_preferencias.size(); i++) {
				if(!(new_preferencias.contains(old_preferencias.get(i).getIdTipoActividad()))) {
					prefiereDao.deletePrefiere(old_preferencias.get(i));
				}
			}
		}
		return "redirect:perfil";
	}
	
	@RequestMapping(value = "/perfil/{idCliente}", method = RequestMethod.POST)
	public String processPerfilUpdate(@PathVariable String idCliente, @ModelAttribute("cliente") Cliente cliente,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "cliente/perfil";
		}
		clienteDao.updateCliente(cliente);
		redirectAttributes.addFlashAttribute("message", "Cambios guardados");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:../perfil";
	}
	
	@RequestMapping(value = "/perfil/password")
	public String processChangePassword(HttpSession session, Model model) {
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "cliente/perfil");
			return "login";
		}
		
		Login usuario = (Login) session.getAttribute("user");
		if (!usuario.getRol().equals("cliente")) {
			return "error/error";
		}
		
		return "cliente/perfil/password";
	}
	
	@RequestMapping(value = "/perfil/password", method = RequestMethod.POST)
	public String processUpdatePassword(HttpSession session, @RequestParam("password") String password,
			@RequestParam("oldpassword") String oldPassword, @RequestParam("reppassword") String repPassword,
			RedirectAttributes redirectAttributes) {
		Login user = (Login) session.getAttribute("user");
		if (!(user.getContraseña().equals(oldPassword))) {
			redirectAttributes.addFlashAttribute("errorOldPasswd", "Contraseña incorrecta");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:../perfil/password";
		}
		if (!(password.equals(repPassword))) {
			redirectAttributes.addFlashAttribute("errorNewPasswd", "La contraseña nueva no coincide");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:../perfil/password";
		}
		// TODO: Añadir alguna comprobación más?
		
		user.setContraseña(password);
		if (loginDao.updatePassword(user)) {
			redirectAttributes.addFlashAttribute("message", "Contraseña cambiada");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		}
		return "redirect:../perfil/password";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processAddSubmit(@ModelAttribute("cliente") Cliente cliente, BindingResult bindingResult,
			@RequestParam("contraseña-cliente") String passwd, @RequestParam("contraseña-cliente-rep") String passwdRep, 
			RedirectAttributes redirectAttributes) {
			
		// Crear objeto Login
		Login login = new Login();
		login.setUsuario(cliente.getIdCliente());
		login.setContraseña(passwd);
		login.setRol("cliente");
		
		// Crear el objeto validator
		ClienteValidator userValidator = new ClienteValidator(); 
		userValidator.validate(cliente, bindingResult);
		
		if (!(passwd.equals(passwdRep))) {
			redirectAttributes.addFlashAttribute("errorPasswd", "Las contraseñas no coinciden");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			//return "redirect:/register";
			return "registro";
		}
		
		if (bindingResult.hasErrors()) {
			return "registro";
		} 
		
		// Añadir la información en la base de datos
		clienteDao.addCliente(cliente);
		loginDao.addLogin(login);
		
		redirectAttributes.addFlashAttribute("register", "Su cuenta ha sido creada. Inicie sesión para empezar a reservar sus actividades.");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/login";
	}

}
