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

import es.uji.ei1027.toopots.dao.ActividadDao;
import es.uji.ei1027.toopots.dao.ClienteDao;
import es.uji.ei1027.toopots.dao.ReservaDao;
import es.uji.ei1027.toopots.model.Cliente;
import es.uji.ei1027.toopots.model.Login;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

	private ClienteDao clienteDao;
	private ActividadDao actividadDao;
	private ReservaDao reservaDao;

	@Autowired
	public void setClienteDao(ClienteDao clienteDao) {
		this.clienteDao = clienteDao;
	}

	@Autowired
	public void setActividadDao(ActividadDao actividadDao) {
		this.actividadDao = actividadDao;
	}
	
	@Autowired
	public void setReservaDao(ReservaDao reservaDao) {
		this.reservaDao = reservaDao;
	}

	@RequestMapping("/home")
	public String homeClientes(Model model) {
		return "cliente/home";
	}

	@RequestMapping("/list")
	public String listClientes(Model model) {
		model.addAttribute("clientes", clienteDao.getClientes());
		return "cliente/list";
	}

	@RequestMapping(value = "/add")
	public String addCliente(Model model) {
		model.addAttribute("cliente", new Cliente());
		return "cliente/add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processAddSubmit(@ModelAttribute("cliente") Cliente cliente, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "cliente/add";
		clienteDao.addCliente(cliente);
		return "redirect:list";
	}

	@RequestMapping(value = "/update/{idCliente}", method = RequestMethod.GET)
	public String editCliente(Model model, @PathVariable String idCliente) {
		model.addAttribute("cliente", clienteDao.getCliente(idCliente));
		return "cliente/update";
	}

	@RequestMapping(value = "/update/{idCliente}", method = RequestMethod.POST)
	public String processUpdateSubmit(@PathVariable String idCliente, @ModelAttribute("cliente") Cliente cliente,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "cliente/update";
		clienteDao.updateCliente(cliente);
		return "redirect:../list";
	}

	@RequestMapping(value = "/delete/{idCliente}")
	public String processDelete(@PathVariable String idCliente) {
		clienteDao.deleteCliente(idCliente);
		return "redirect:../list";
	}

	@RequestMapping("/actividades")
	public String listActividades(HttpSession session, Model model) {
		if (session.getAttribute("user") == null) {
			model.addAttribute("user", new Login());
			session.setAttribute("nextUrl", "cliente/actividades");
			return "login";
		}
		model.addAttribute("actividades", actividadDao.getActividades());
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
		model.addAttribute("reservas", reservaDao.getReservasUsuario(usuario.getUsuario()));
		return "cliente/reservas";
	}
}
