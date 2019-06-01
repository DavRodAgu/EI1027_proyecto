package es.uji.ei1027.toopots.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import es.uji.ei1027.toopots.dao.LoginDao;
import es.uji.ei1027.toopots.dao.PrefiereDao;
import es.uji.ei1027.toopots.dao.ReservaDao;
import es.uji.ei1027.toopots.dao.TipoActividadDao;
import es.uji.ei1027.toopots.model.Cliente;
import es.uji.ei1027.toopots.model.Login;
import es.uji.ei1027.toopots.model.Prefiere;
import es.uji.ei1027.toopots.services.ClienteService;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

	private ClienteDao clienteDao;
	private ActividadDao actividadDao;
	private ClienteService clienteService;
	private LoginDao loginDao;
	private TipoActividadDao tipoActividadDao;
	private PrefiereDao prefiereDao;
	
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

	
	@RequestMapping("/actividades")
	public String listActividades(HttpSession session, Model model) {
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "cliente/actividades");
			return "login";
		}
		
		Login usuario = (Login) session.getAttribute("user");
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
		model.addAttribute("actividades", clienteService.getActividadByPreference(usuario.getUsuario(), preferencias));
		model.addAttribute("preferencias", preferencias);
		return "cliente/actividades";
	}
	
	@RequestMapping("/reservas")
	public String listReservas(HttpSession session, Model model) {
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "cliente/reservas");
			return "login";
		}
		Login usuario = (Login) session.getAttribute("user");
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
}
