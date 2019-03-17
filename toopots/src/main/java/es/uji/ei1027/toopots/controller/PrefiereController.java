package es.uji.ei1027.toopots.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.toopots.dao.PrefiereDao;
import es.uji.ei1027.toopots.model.Prefiere;

@Controller
@RequestMapping("/prefiere")
public class PrefiereController {

	private PrefiereDao prefiereDao;

	@Autowired
	public void setPrefiereDao(PrefiereDao prefiereDao) {
		this.prefiereDao = prefiereDao;
	}

	@RequestMapping("/list")
	public String listPreferencias(Model model) {
		model.addAttribute("preferencias", prefiereDao.getPreferencias());
		return "prefiere/list";
	}

	@RequestMapping(value = "/add")
	public String addPreferencias(Model model) {
		model.addAttribute("preferencia", new Prefiere());
		return "prefiere/add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processAddSubmit(@ModelAttribute("prefiere") Prefiere prefiere, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "prefiere/add";
		prefiereDao.addPrefiere(prefiere);
		return "redirect:list";
	}

	@RequestMapping(value = "/delete/{idTipoActividad}/{idCliente}")
	public String processDelete(@PathVariable int idTipoActividad, @PathVariable String idCliente) {
		prefiereDao.deletePrefiere(idTipoActividad, idCliente);
		return "redirect:../list";
	}
}