package es.uji.ei1027.toopots.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.toopots.dao.OfertaDao;
import es.uji.ei1027.toopots.model.Oferta;

@Controller
@RequestMapping("/oferta")
public class OfertaController {

	private OfertaDao ofertaDao;

	@Autowired
	public void setOfertaDao(OfertaDao ofertaDao) {
		this.ofertaDao = ofertaDao;
	}

	@RequestMapping("/list")
	public String listOfertas(Model model) {
		model.addAttribute("ofertas", ofertaDao.getOfertas());
		return "oferta/list";
	}

	@RequestMapping(value = "/add")
	public String addOferta(Model model) {
		model.addAttribute("oferta", new Oferta());
		return "oferta/add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processAddSubmit(@ModelAttribute("oferta") Oferta oferta, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "oferta/add";
		ofertaDao.addOferta(oferta);
		return "redirect:list";
	}

	@RequestMapping(value = "/delete/{idInstructor}/{idActividad}")
	public String processDelete(@PathVariable String idInstructor, @PathVariable int idActividad) {
		ofertaDao.deleteOferta(idInstructor, idActividad);
		return "redirect:../list";
	}
}