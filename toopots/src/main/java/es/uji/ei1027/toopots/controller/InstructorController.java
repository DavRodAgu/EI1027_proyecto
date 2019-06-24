package es.uji.ei1027.toopots.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import es.uji.ei1027.toopots.dao.AcreditaDao;
import es.uji.ei1027.toopots.dao.AcreditacionDao;
import es.uji.ei1027.toopots.dao.ActividadDao;
import es.uji.ei1027.toopots.dao.ClienteDao;
import es.uji.ei1027.toopots.dao.ImagenPromocionalDao;
import es.uji.ei1027.toopots.dao.LoginDao;
import es.uji.ei1027.toopots.dao.ReservaDao;
import es.uji.ei1027.toopots.dao.TipoActividadDao;
import es.uji.ei1027.toopots.model.Acredita;
import es.uji.ei1027.toopots.model.Acreditacion;
import es.uji.ei1027.toopots.model.Actividad;
import es.uji.ei1027.toopots.model.Cliente;
import es.uji.ei1027.toopots.model.ImagenPromocional;
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
		Instructor instructor = (Instructor) obj;
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
	private AcreditacionDao acreditacionDao;
	private AcreditaDao acreditaDao;
	private ClienteDao clienteDao;

	@Value("${upload.certificate.directory}")
	private String uploadDirectory;

	@Value("${upload.imagen.directory}")
	private String imageDirectory;

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

	@RequestMapping("/perfil")
	public String perfilInstructor(HttpSession session, Model model) {
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "instructor/perfil");
			return "login";
		}
		Login usuario = (Login) session.getAttribute("user");
		Instructor instructor = instructorDao.getInstructor(usuario.getUsuario());

		if (!usuario.getRol().equals("instructor")) {
			return "error/error";
		}

		if (instructor.getEstado().equals("pendiente")) {
			return "redirect:/instructor/actividades";
		}

		model.addAttribute("instructor", instructor);
		model.addAttribute("solicitudes", instructorService.getSolicitudesByInstructor(usuario.getUsuario()));
		return "instructor/perfil";
	}

	@RequestMapping(value = "/{idAcreditacion}/certificado")
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

	@RequestMapping(value = "/perfil/{idInstructor}", method = RequestMethod.POST)
	public String updatePerfilInstructor(HttpSession session, Model model, @RequestParam("afoto") MultipartFile foto,
			@PathVariable String idInstructor, @RequestParam("nombre") String nombre,
			@RequestParam("estado") String estado, @RequestParam("email") String email,
			@RequestParam("iban") String iban, @ModelAttribute("instructor") Instructor instructor,
			/* BindingResult bindingResult, */ RedirectAttributes redirectAttributes) throws IOException {
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

		// Foto
		byte[] bytes2 = foto.getBytes();
		String nombreFoto = instructor.getIdInstructor() + "-" + foto.getOriginalFilename();
		Path path2 = Paths.get(imageDirectory + "/" + nombreFoto);

		Files.write(path2, bytes2);

		// TODO: Modificar este código en un futuro
		Instructor ins = instructorDao.getInstructor(idInstructor);
		ins.setNombre(nombre);
		ins.setEstado(estado);
		ins.setEmail(email);
		ins.setIban(iban);
		ins.setFoto(nombreFoto);
		instructorDao.updateInstructor(ins);
		redirectAttributes.addFlashAttribute("message", "Cambios guardados");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
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
		Instructor instructor = instructorDao.getInstructor(usuario.getUsuario());

		if (!usuario.getRol().equals("instructor")) {
			return "error/error";
		}

		if (instructor.getEstado().equals("pendiente")) {
			return "redirect:/instructor/actividades";
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

	@RequestMapping(value = "/solicitar")
	public String solicitarAcreditacion(HttpSession session, Model model) {
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			model.addAttribute("nextUrl", "instructor/solicitar");
			return "login";
		}
		Login usuario = (Login) session.getAttribute("user");
		Instructor instructor = instructorDao.getInstructor(usuario.getUsuario());

		if (!usuario.getRol().equals("instructor")) {
			return "error/error";
		}

		if (instructor.getEstado().equals("pendiente")) {
			return "redirect:/instructor/actividades";
		}

		model.addAttribute("tipos", tipoActividadDao.getTipoActividades());
		model.addAttribute("tiposacreditados", instructorService.getTipoActividadInstructor(usuario.getUsuario()));
		return "instructor/perfil/solicitar";
	}

	@RequestMapping(value = "/solicitar", method = RequestMethod.POST)
	public String processSolicitarAcreditacion(HttpSession session, Model model,
			@RequestParam("tipo-actividad") int idTipoActividad,
			@RequestParam("certificado-instructor") MultipartFile certificado, RedirectAttributes redirectAttributes) {
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			model.addAttribute("nextUrl", "instructor/solicitar");
			return "login";
		}
		Login usuario = (Login) session.getAttribute("user");

		if (!usuario.getRol().equals("instructor")) {
			return "error/error";
		}

		// Obtener datos del pdf
		try {
			byte[] bytes = certificado.getBytes();
			Path path = Paths.get(uploadDirectory + "certificates/" + usuario.getUsuario() + "-" + idTipoActividad + "-"
					+ certificado.getOriginalFilename());

			Files.write(path, bytes);

			// Crear objeto Acreditación y Acredita
			Acreditacion acreditacion = new Acreditacion();
			acreditacion.setCertificado(path.toString());
			acreditacion.setEstado("pendiente");
			acreditacion.setIdInstructor(usuario.getUsuario());
			System.out.println(acreditacion);

			// Añadir la información en la base de datos
			acreditacionDao.addAcreditacion(acreditacion);

			int idAcreditacion = acreditacionDao.getAcreditacion(usuario.getUsuario(), acreditacion.getCertificado())
					.getIdAcreditacion();

			// Objeto Acredita
			Acredita acredita = new Acredita();
			acredita.setIdTipoActividad(idTipoActividad);
			acredita.setIdAcreditacion(idAcreditacion);
			System.out.println(acredita);
			acreditaDao.addAcredita(acredita);

			redirectAttributes.addFlashAttribute("register",
					"Solicitud enviada, en breves será revisada por el administrador del sitio.");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			return "redirect:/instructor/solicitar";
		} catch (IOException e) {
			e.printStackTrace();
			return "/instructor/solicitar";
		}
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

		model.addAttribute("instructor", instructorDao.getInstructor(usuario.getUsuario()));
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
		TipoActividad tipoActividad = tipoActividadDao
				.getTipoActividad(Integer.toString(actividad.getIdTipoActividad()));
		model.addAttribute("tipoactividad", tipoActividad.getNombre());
		model.addAttribute("nivel", tipoActividad.getNivel());

		// Obtener las reservas de una actividad
		model.addAttribute("reservas", instructorService.getReservasByActividad(idActividad));

		// Obtener imagen de la actividad
		model.addAttribute("imagen", imagenPromocionalDao.getImagen(idActividad).getImagen());
		return "instructor/actividad";
	}

	@RequestMapping(value = "/actividad/{idActividad}/reserva/{idReserva}/confirmar")
	public String confirmReserva(@PathVariable String idActividad, @PathVariable String idReserva,
			RedirectAttributes redirectAttributes) {
		Reserva reserva = reservaDao.getReserva(idReserva);
		reserva.setEstadoPago("pagado");
		reservaDao.updateReserva(reserva);
		String nombre = clienteDao.getCliente(reserva.getIdCliente()).getNombre();

		redirectAttributes.addFlashAttribute("message",
				"El pago de la reserva del usuario " + nombre + " ha sido confirmado");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");

		return "redirect:/instructor/actividad/" + idActividad;
	}

	@RequestMapping(value = "/actividad/{idActividad}/update")
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
		// Obtener imagen de la actividad
		model.addAttribute("imagen", imagenPromocionalDao.getImagen(idActividad).getImagen());

		model.addAttribute("actividad", actividadDao.getActividad(idActividad));
		model.addAttribute("tipos", tipoActividadDao.getTipoActividades());
		return "instructor/modificar";
	}

	@RequestMapping(value = "/actividad/{idActividad}/update", method = RequestMethod.POST)
	public String processUpdateSubmit(HttpSession session, @PathVariable String idActividad,
			@RequestParam("tipo") int tipo, @ModelAttribute("actividad") Actividad actividad,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors())
			return "actividad/" + idActividad + "/update";
		
		if (actividad.getFecha().isBefore(LocalDate.now())) {
			redirectAttributes.addFlashAttribute("errorFecha", "La fecha de la actividad no puede pertenecer al pasado");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:/instructor/actividad/" + idActividad + "/update";	
		}
		
		if (actividad.getMinAsistentes() > actividad.getMaxAsistentes()) {
			redirectAttributes.addFlashAttribute("errorAsistentes", "El número mínimo de asistentes no puede ser mayor que el máximo");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:/instructor/actividad/" + idActividad + "/update";	
		}
		
		if (actividad.getMinAsistentes() < 1) {
			redirectAttributes.addFlashAttribute("errorAsistentes", "El número mínimo de asistentes debe ser al menos 1");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:/instructor/actividad/" + idActividad + "/update";	
		}

		if (actividad.getDuracion().toString().equals("00:00")) {
			redirectAttributes.addFlashAttribute("errorDuracion", "La duración no puede ser 0");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:/instructor/actividad/" + idActividad + "/update";	
		}
		
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
			model.addAttribute("nextUrl", "instructor/actividad/add");
			return "login";
		}

		Login usuario = (Login) session.getAttribute("user");
		Instructor instructor = instructorDao.getInstructor(usuario.getUsuario());

		if (!(usuario.getRol().equals("instructor"))) {
			return "error/error";
		}

		if (instructor.getEstado().equals("pendiente")) {
			return "redirect:/instructor/actividades";
		}

		model.addAttribute("actividad", new Actividad());
		model.addAttribute("tipos", instructorService.getTiposAcreditados(usuario.getUsuario()));
		return "instructor/add";
	}

	@RequestMapping(value = "/actividad/add", method = RequestMethod.POST)
	public String processAddSubmit(HttpSession session, @ModelAttribute("actividad") Actividad actividad,
			BindingResult bindingResult, @RequestParam("imagen") MultipartFile imagen,
			RedirectAttributes redirectAttributes) throws IOException {
		if (bindingResult.hasErrors()) {
			return "instructor/add";
		}
		
		if (actividad.getFecha().isBefore(LocalDate.now())) {
			redirectAttributes.addFlashAttribute("errorFecha", "La fecha de la actividad no puede pertenecer al pasado");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:/instructor/actividad/add";	
		}
		
		if (actividad.getMinAsistentes() > actividad.getMaxAsistentes()) {
			redirectAttributes.addFlashAttribute("errorAsistentes", "El número mínimo de asistentes no puede ser mayor que el máximo");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:/instructor/actividad/add";			
		}
		
		if (actividad.getMinAsistentes() < 1) {
			redirectAttributes.addFlashAttribute("errorAsistentes", "El número mínimo de asistentes debe ser al menos 1");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:/instructor/actividad/add";			
		}

		if (actividad.getDuracion().toString().equals("00:00")) {
			redirectAttributes.addFlashAttribute("errorDuracion", "La duración no puede ser 0");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:/instructor/actividad/add";
		}
		
		actividad.setEstado("abierta");
		Login usuario = (Login) session.getAttribute("user");
		actividad.setIdInstructor(usuario.getUsuario());
		actividadDao.addActividad(actividad);

		int idActividad = actividadDao.getActividad(actividad).getIdActividad();
		
		// Crear objeto ImagenPromocional
		byte[] bytes2 = imagen.getBytes();
		String nombreImagen = imagen.getOriginalFilename();
		Path path2 = Paths.get(imageDirectory + "/" + nombreImagen);

		System.out.println("Nombre: " + nombreImagen);

		Files.write(path2, bytes2);
		ImagenPromocional imagenPromocional = new ImagenPromocional();
		imagenPromocional.setIdActividad(idActividad);
		imagenPromocional.setImagen(nombreImagen);
		System.out.println(imagenPromocional);
		imagenPromocionalDao.addImagenPromocional(imagenPromocional);

		redirectAttributes.addFlashAttribute("message",
				"Actividad " + actividad.getNombre() + "ha sido creada satisfactoriamente");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:../actividades";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processAddSubmit(@ModelAttribute("instructor") Instructor instructor, BindingResult bindingResult,
			@RequestParam("contraseña-instructor") String passwd, @RequestParam("foto-instructor") MultipartFile foto,
			@RequestParam("contraseña-instructor-rep") String passwdRep,
			@RequestParam("certificado-instructor") MultipartFile certificado,
			@RequestParam("tipo-actividad-acreditado") int idTipoActividad, RedirectAttributes redirectAttributes) {

		// Crear objeto Login
		Login login = new Login();
		login.setUsuario(instructor.getIdInstructor());
		login.setContraseña(passwd);
		login.setRol("instructor");

		// Crear el objeto validator
		InstructorValidator instructorValidator = new InstructorValidator();
		instructorValidator.validate(instructor, bindingResult);
		
		if (instructor.getIban().length() != 29) {
			redirectAttributes.addFlashAttribute("errorIBAN", "Formato de IBAN incorrecto, recuerda que debe ser del estilo: ES60 0049 1500 0512 3456 7892");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:/register";
		}
		
		List<String> DNIs = instructorDao.getInstructores().stream().map(Instructor::getIdInstructor).collect(Collectors.toList());
		
		if (DNIs.contains(instructor.getIdInstructor())) {
			redirectAttributes.addFlashAttribute("errorDNI", "El DNI introducido ya se encuentra registrado");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:/register";
		}

		if (instructor.getIdInstructor().length() != 9 || Character.isLetter(instructor.getIdInstructor().charAt(8)) == false) {
			redirectAttributes.addFlashAttribute("errorDNI", "Formato de DNI incorrecto, recuerda que debes introducir 8 dígitos y 1 letra");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:/register";
		}
		
		if (!(passwd.equals(passwdRep))) {
			redirectAttributes.addFlashAttribute("errorPasswd", "Las contraseñas no coinciden");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:/register";
			//return "registro";
		}

		if (certificado.isEmpty()) {
			// Enviar mensaje de error porque no hay fichero seleccionado
			redirectAttributes.addFlashAttribute("message", "Selecciona el certificado a subir");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
		}

		if (bindingResult.hasErrors()) {
			return "redirect:/register";
			//return "registro";
		}

		try {
			// Certificado
			byte[] bytes = certificado.getBytes();
			Path path = Paths.get(uploadDirectory + "certificates/" + instructor.getIdInstructor() + "-"
					+ idTipoActividad + "-" + certificado.getOriginalFilename());

			Files.write(path, bytes);

			// Foto
			byte[] bytes2 = foto.getBytes();
			String nombreFoto = instructor.getIdInstructor() + "-" + foto.getOriginalFilename();
			Path path2 = Paths.get(imageDirectory + "/" + nombreFoto);

			Files.write(path2, bytes2);

			// Añadir ruta de la foto al objeto instructor
			instructor.setFoto(nombreFoto);

			// Crear objeto Acreditación y Acredita
			Acreditacion acreditacion = new Acreditacion();
			acreditacion.setCertificado(path.toString());
			acreditacion.setEstado("pendiente");
			acreditacion.setIdInstructor(instructor.getIdInstructor());

			// Añadir la información en la base de datos
			instructorDao.addInstructor(instructor);
			loginDao.addLogin(login);
			acreditacionDao.addAcreditacion(acreditacion);

			int idAcreditacion = acreditacionDao.getAcreditacionByIdInstructor(instructor.getIdInstructor()).get(0)
					.getIdAcreditacion();

			// Objeto Acredita
			Acredita acredita = new Acredita();
			acredita.setIdTipoActividad(idTipoActividad);
			acredita.setIdAcreditacion(idAcreditacion);
			acreditaDao.addAcredita(acredita);

			redirectAttributes.addFlashAttribute("register",
					"Su cuenta ha sido creada. Puede iniciar sesión. Sin embargo, no podrá realizar ninguna acción hasta que su solicitud sea aceptada");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			return "redirect:/login";
		} catch (IOException e) {
			e.printStackTrace();
			return "registro";
		}
	}
}