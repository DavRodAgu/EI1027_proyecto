package es.uji.ei1027.toopots.controller;


import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.uji.ei1027.toopots.dao.ActividadDao;
import es.uji.ei1027.toopots.dao.ReservaDao;
import es.uji.ei1027.toopots.model.Actividad;
import es.uji.ei1027.toopots.model.Login;
import es.uji.ei1027.toopots.model.Reserva;

@Controller
@RequestMapping("/reserva")
public class ReservaController {

	private ReservaDao reservaDao;
	private ActividadDao actividadDao;

	@Autowired
	public void setReservaDao(ReservaDao reservaDao) {
		this.reservaDao = reservaDao;
	}

	@Autowired
	public void setActividadDao(ActividadDao actividadDao) {
		this.actividadDao = actividadDao;
	}

	@RequestMapping("/list")
	public String listReservas(HttpSession session, Model model) {
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "reservas/list");
			return "login";
		}
		model.addAttribute("reservas", reservaDao.getReservas());
		return "reserva/list";
	}

	@RequestMapping(value = "/add")
	public String addReserva(Model model) {
		model.addAttribute("reserva", new Reserva());
		return "reserva/add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processAddSubmit(@ModelAttribute("user") Login user, @ModelAttribute("reserva") Reserva reserva,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "reserva/add";
		reserva.setIdCliente(user.getUsuario());
		reservaDao.addReserva(reserva);
		return "redirect:list";
	}

	@RequestMapping(value = "/update/{idReserva}", method = RequestMethod.GET)
	public String editReserva(Model model, @PathVariable String idReserva) {
		model.addAttribute("reserva", reservaDao.getReserva(idReserva));
		return "reserva/update";
	}

	@RequestMapping(value = "/update/{idReserva}", method = RequestMethod.POST)
	public String processUpdateSubmit(@PathVariable String idReserva, @ModelAttribute("reserva") Reserva reserva,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "reserva/update";
		reservaDao.updateReserva(reserva);
		return "redirect:../list";
	}

	@RequestMapping(value = "/delete/{idReserva}")
	public String processDelete(@PathVariable String idReserva,
			RedirectAttributes redirectAttributes) {
		Reserva reserva = reservaDao.getReserva(idReserva);
		// Convertir int a string
		String idActividad = Integer.toString(reserva.getIdActividad());
		String nombre = actividadDao.getActividad(idActividad).getNombre();
		reservaDao.deleteReserva(idReserva);
		redirectAttributes.addFlashAttribute("message", "La reserva de la actividad \"" + nombre + "\" ha sido cancelada.");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:../../cliente/reservas";
	}

	@RequestMapping(value = "/anadirReserva", method = RequestMethod.POST)
	public String processAñadirSubmit(Model model, HttpSession session, @ModelAttribute("reserva") Reserva reserva,
			@RequestParam(name = "idActividad") String idActividad, @RequestParam(name = "nPersonas") int nPersonas, @RequestParam(name = "preferencias") boolean preferencias,
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
		
		return "redirect:../cliente/actividades/"+preferencias;
	}
	
	@RequestMapping(value="/pagar/{idReserva}")
	public String processPago(@PathVariable int idReserva) {
          reservaDao.pagarReserva(idReserva);
          return "redirect:../../cliente/reservas"; 
	}
	
}
