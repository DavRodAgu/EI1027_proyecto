package es.uji.ei1027.toopots.controller;

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
		if (session.getAttribute("user") == null) 
	       { 
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
	public String processDelete(@PathVariable String idReserva) {
		reservaDao.deleteReserva(idReserva);
		return "redirect:../../cliente/listarReservas";
	}

	@RequestMapping(value = "/añadirReserva/{idActividad}", method = RequestMethod.GET)
	public String añadirReserva(Model model, @PathVariable int idActividad) {
		System.out.println("ID Actividad: " + idActividad);
		Actividad actividad = actividadDao.getActividad(idActividad + "");
		model.addAttribute("reserva", new Reserva(actividad.getFecha(), actividad.getPrecio(), idActividad));
		return "cliente/actividades";
//		return model;
	}

	@RequestMapping(value = "/añadirReserva", method = RequestMethod.POST)
	public String processAñadirSubmit(HttpSession session, @ModelAttribute("reserva") Reserva reserva, @RequestParam(name = "nPersonas") int nPersonas,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "cliente/actividades";
		reserva.setNumAsistentes(nPersonas);
		Login usuario = (Login) session.getAttribute("user");
		reserva.setIdCliente(usuario.getUsuario());
		reserva.setEstadoPago("pendiente");
		reservaDao.addReserva(reserva);
		return "redirect:../cliente/reservas";
	}
	
}



