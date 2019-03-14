package es.uji.ei1027.toopots.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.toopots.dao.ComentarioDao;
import es.uji.ei1027.toopots.model.Comentario;

@Controller
@RequestMapping("/Comentario")
public class ComentarioController {
	
	private ComentarioDao comentarioDao;
	
	@Autowired
	public void setComentarioDao(ComentarioDao comentarioDao) {
		this.comentarioDao = comentarioDao;
	}

	@RequestMapping("/list")
	public String listComentarios(Model model) {
		model.addAttribute("comentarios", comentarioDao.getComentarios());
		return "comentario/list";
	}

	@RequestMapping(value = "/add")
	public String addComentario(Model model) {
		model.addAttribute("nadador", new Comentario());
		return "comentario/add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processAddSubmit(@ModelAttribute("comentario") Comentario comentario,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "comentario/add";
		}
		comentarioDao.addComentario(comentario);
		return "redirect:list";
	}

	@RequestMapping(value = "/update/{idComentario}", method = RequestMethod.GET)
	public String editComentario(Model model, @PathVariable String idComentario) {
		model.addAttribute("comentario", comentarioDao.getComentario(idComentario));
		return "comentario/update";
	}

	@RequestMapping(value = "/update/{idComentario}", method = RequestMethod.POST)
	public String processUpdateSubmit(@PathVariable String idComentario, 
			@ModelAttribute("comentario") Comentario comentario,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "comentario/update";
		comentarioDao.updateComentario(comentario);
		return "redirect:../list";
	}

	@RequestMapping(value = "/delete/{idComentario}")
	public String processDelete(@PathVariable String idComentario) {
		comentarioDao.deleteComentario(idComentario);
		return "redirect:../list";
	}
}
