package es.uji.ei1027.toopots.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.uji.ei1027.toopots.dao.InstructorDao;
import es.uji.ei1027.toopots.dao.ActividadDao;
import es.uji.ei1027.toopots.dao.ImagenPromocionalDao;
import es.uji.ei1027.toopots.dao.LoginDao;
import es.uji.ei1027.toopots.dao.ReservaDao;
import es.uji.ei1027.toopots.dao.TipoActividadDao;
import es.uji.ei1027.toopots.model.Actividad;
import es.uji.ei1027.toopots.model.Cliente;
import es.uji.ei1027.toopots.model.Instructor;
import es.uji.ei1027.toopots.model.Login;
import es.uji.ei1027.toopots.model.Reserva;
import es.uji.ei1027.toopots.model.TipoActividad;
import es.uji.ei1027.toopots.services.InstructorService;

class InstructorValidator implements Validator { 
	@Override
	public boolean supports(Class<?> cls) { 
		return Instructor.class.isAssignableFrom(cls);
	}
	@Override 
	public void validate(Object obj, Errors errors) {
		Instructor instructor = (Instructor)obj;
		// TODO: Añadir comprobaciones
	}
}

@Controller
@RequestMapping("/instructor")
public class InstructorController {

	private InstructorDao instructorDao;
	private LoginDao loginDao;
	private ActividadDao actividadDao;
	private InstructorService instructorService;
	private ReservaDao reservaDao;
	private TipoActividadDao tipoActividadDao;
	private ImagenPromocionalDao imagenPromocionalDao;
	
	
	@Autowired
	public void setImagenPromocionalDao(ImagenPromocionalDao imagenPromocionalDao) {
		this.imagenPromocionalDao = imagenPromocionalDao;
	}

	@Autowired
	public void setInsructorDao(InstructorDao instructorDao) {
		this.instructorDao = instructorDao;
	}

	@Autowired
	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;
	}
	
	@Autowired
	public void setActividadDao(ActividadDao actividadDao) {
		this.actividadDao = actividadDao;
	}
	
	@Autowired
	public void setInstructorService(InstructorService instructorService) {
		this.instructorService = instructorService;
	}
	
	@Autowired
	public void setReservaDao(ReservaDao reservaDao) {
		this.reservaDao = reservaDao;
	}
	
	@Autowired
	public void setTipoActividadDao(TipoActividadDao tipoActividadDao) {
		this.tipoActividadDao = tipoActividadDao;
	}
	
	
	@RequestMapping("/perfil")
	public String perfilInstructor(HttpSession session, Model model) {
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "instructor/perfil");
			return "login";
		}
		Login usuario = (Login) session.getAttribute("user");
		
		if (!usuario.getRol().equals("instructor")) {
			return "error/error";
		}
		
		model.addAttribute("instructor", instructorDao.getInstructor(usuario.getUsuario()));
		return "instructor/perfil";
	}

	public static File convert(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	@RequestMapping(value = "/perfil/{idInstructor}", method = RequestMethod.POST)
	public String updatePerfilInstructor(HttpSession session, Model model, @RequestParam("afoto") MultipartFile foto,
			@PathVariable String idInstructor, @RequestParam("nombre") String nombre,
			@RequestParam("estado") String estado, @RequestParam("email") String email,
			@RequestParam("iban") String iban, @ModelAttribute("instructor") Instructor instructor
	/* BindingResult bindingResult, RedirectAttributes redirectAttributes */) throws IOException {
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "instructor/perfil");
			return "login";
		}

		Login usuario = (Login) session.getAttribute("user");
		if (!usuario.getRol().equals("instructor")) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "instructor/perfil");
			return "login";
		}

		/*
		 * if (bindingResult.hasErrors()) {
		 * redirectAttributes.addFlashAttribute("message",
		 * "Error al actualizar su perfil");
		 * redirectAttributes.addFlashAttribute("alertClass", "alert-danger"); return
		 * "instructor/perfil"; }
		 */

		/*
		 * Se convierte el MultipartFile a File, se escribe el archivo en su ruta
		 * automática, se toma la ruta absoluta del archivo, se corta el nombre del
		 * archivo para conseguir la ruta absoluta del directorio, se crea un File en el
		 * directorio media
		 */
		File archivo = convert(foto);
		archivo.createNewFile();
		String directorio = archivo.getAbsolutePath();
		directorio = directorio.substring(0, directorio.length() - archivo.getPath().length() - 1);
		String nombreFoto = archivo.getName();

		File destination = new File(directorio + "/src/main/resources/static/media/" + nombreFoto);

		/* Copiar fichero a directorio destino */
		try {
			FileUtils.copyFile(archivo, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		 * Se escribe el fichero en el directorio media y se borra el archivo del
		 * directorio por defecto
		 */
		destination.createNewFile();
		archivo.delete();

		// TODO: Modificar este código en un futuro
		Instructor ins = instructorDao.getInstructor(idInstructor);
		ins.setNombre(nombre);
		ins.setEstado(estado);
		ins.setEmail(email);
		ins.setIban(iban);
		ins.setFoto(destination.getName());
		instructorDao.updateInstructor(ins);
		//redirectAttributes.addFlashAttribute("message", "Cambios guardados");
		//redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:../perfil";
	}

	@RequestMapping(value = "/perfil/password")
	public String processChangePassword(HttpSession session, Model model) {
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "instructor/perfil");
			return "login";
		}
		Login usuario = (Login) session.getAttribute("user");
		if (!usuario.getRol().equals("instructor")) {
			return "error/error";
		}
		return "instructor/perfil/password";
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

	@RequestMapping("/actividades")
	public String processActividades(HttpSession session, Model model) {
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			model.addAttribute("nextUrl", "instructor/actividades");
			return "login";
		}
		Login usuario = (Login) session.getAttribute("user");
		
		if (!usuario.getRol().equals("instructor")) {
			return "error/error";
		}
		
		model.addAttribute("actividades", instructorService.getNumReservas(usuario.getUsuario()));
		return "instructor/actividades";
	}

	@RequestMapping("/actividad/{idActividad}")
	public String processReservas(HttpSession session, Model model, @PathVariable String idActividad) {
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			model.addAttribute("nextUrl", "instructor/actividades");
			return "login";
		}
		
		Login usuario = (Login) session.getAttribute("user");
		if (!usuario.getRol().equals("instructor")) {
			return "error/error";
		}
		
		model.addAttribute("actividad", actividadDao.getActividad(idActividad));
		model.addAttribute("asistentes", reservaDao.getNumReservasActividad(Integer.valueOf(idActividad)).size());
		Actividad actividad = actividadDao.getActividad(idActividad);
		TipoActividad tipoActividad = tipoActividadDao.getTipoActividad(Integer.toString(actividad.getIdTipoActividad()));
		model.addAttribute("tipoactividad", tipoActividad.getNombre());
		model.addAttribute("nivel", tipoActividad.getNivel());
		
		// Obtener las reservas de una actividad
		model.addAttribute("reservas", instructorService.getReservasByActividad(idActividad));
		
		// Obtener imagen de la actividad
		model.addAttribute("imagen", imagenPromocionalDao.getImagen(idActividad).getImagen());
		return "instructor/actividad";
	}
	
	@RequestMapping("/actividad/{idActividad}/update")
	public String updateActividad(HttpSession session, Model model, @PathVariable String idActividad) {
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			model.addAttribute("nextUrl", "instructor/modificar");
			return "login";
		}
		
		Login usuario = (Login) session.getAttribute("user");
		if (!usuario.getRol().equals("instructor")) {
			return "error/error";
		}
		
		model.addAttribute("actividad", actividadDao.getActividad(idActividad));
		model.addAttribute("tipos", tipoActividadDao.getTipoActividades());
		return "instructor/modificar";
	}
	
	@RequestMapping(value = "/actividad/{idActividad}/update", method = RequestMethod.POST)
	public String processUpdateSubmit(HttpSession session, @PathVariable String idActividad, @RequestParam("tipo") int tipo,
			@ModelAttribute("actividad") Actividad actividad, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors())
			return "actividad/" + idActividad + "update";
		actividad.setIdTipoActividad(tipo);
		actividad.setTextoCliente("");
		Login usuario = (Login) session.getAttribute("user");
		actividad.setIdInstructor(usuario.getUsuario());
		actividadDao.updateActividad(actividad);
		redirectAttributes.addFlashAttribute("message", "Datos de la actividad modificados correctamente");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:../" + idActividad;
	}
	
	@RequestMapping(value = "/actividad/add")
	public String addActividad(HttpSession session, Model model) {
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			model.addAttribute("nextUrl", "actividad/add");
			return "login";
		}
		
		Login usuario = (Login)session.getAttribute("user");
		if (!(usuario.getRol().equals("instructor"))) {
			return "error/error";
		}
		model.addAttribute("actividad", new Actividad());
		model.addAttribute("tipos", tipoActividadDao.getTipoActividadesInstructor(usuario.getUsuario()));
		return "instructor/add";
	}
	
	@RequestMapping(value = "/actividad/add", method = RequestMethod.POST)
	public String processAddSubmit(HttpSession session, @ModelAttribute("actividad") Actividad actividad, BindingResult bindingResult,
			@RequestParam("imagenPromocional") String imagen, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "instructor/add";
		}
		
		// TODO: Crear objeto ImagenPromocional
		// System.out.println(imagen);
		actividad.setEstado("abierta");
		Login usuario = (Login) session.getAttribute("user");
		actividad.setIdInstructor(usuario.getUsuario());
		actividadDao.addActividad(actividad);
		
		redirectAttributes.addFlashAttribute("message", "Actividad " + actividad.getNombre() + " creada satisfactoriamente");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:../actividades";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processAddSubmit(@ModelAttribute("instructor") Instructor instructor, BindingResult bindingResult,
			@RequestParam("contraseña-instructor") String passwd,
			@RequestParam("contraseña-instructor-rep") String passwdRep, RedirectAttributes redirectAttributes) {
		
		// Establecer el estado del instructor a 'pendiante'
		instructor.setEstado("pendiente");
		
		// Crear objeto Login
		Login login = new Login();
		login.setUsuario(instructor.getIdInstructor());
		login.setContraseña(passwd);
		login.setRol("instructor");

		// Crear el objeto validator
		InstructorValidator instructorValidator = new InstructorValidator();
		instructorValidator.validate(instructor, bindingResult);

		if (!(passwd.equals(passwdRep))) {
			redirectAttributes.addFlashAttribute("errorPasswd", "Las contraseñas no coinciden");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			// return "redirect:/register";
			return "registro";
		}

		if (bindingResult.hasErrors()) {
			return "registro";
		}

		// Añadir la información en la base de datos
		// clienteDao.addCliente(cliente);
		// loginDao.addLogin(login);
		System.out.println(instructor);
		System.out.println(login);

		// TODO: Modificar este codigo
		redirectAttributes.addFlashAttribute("register",
				"Su cuenta ha sido creada. Inicie sesión para empezar a reservar sus actividades.");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/login";
	}
}