package es.uji.ei1027.toopots.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.toopots.dao.ImagenPromocionalDao;
import es.uji.ei1027.toopots.model.ImagenPromocional;

@Controller
@RequestMapping("/ImagenPromocional")
public class ImagenPromocionalController {

	private ImagenPromocionalDao imagenPromocionalDao;

	@Autowired
	public void setImagenPromocionalDao(ImagenPromocionalDao imagenPromocionalDao) {
		this.imagenPromocionalDao = imagenPromocionalDao;
	}

	@RequestMapping("/list")
	public String listImagenes(Model model) {
		model.addAttribute("imagenes", imagenPromocionalDao.getImagenesPromocionales());
		return "imagen/list";
	}

	@RequestMapping(value = "/add")
	public String addImagen(Model model) {
		model.addAttribute("imagen", new ImagenPromocional());
		return "imagen/add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processAddSubmit(@ModelAttribute("imagen") ImagenPromocional imagen,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "imagen/add";
		}
		imagenPromocionalDao.addImagenPromocional(imagen);
		return "redirect:list";
	}

	@RequestMapping(value = "/update/{idImagen}", method = RequestMethod.GET)
	public String editImagen(Model model, @PathVariable String idImagen) {
		model.addAttribute("comentario", imagenPromocionalDao.getImagenPromocional(idImagen));
		return "imagen/update";
	}

	@RequestMapping(value = "/update/{idImagen}", method = RequestMethod.POST)
	public String processUpdateSubmit(@PathVariable String idImagen,
			@ModelAttribute("imagen") ImagenPromocional imagen, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "imagen/update";
		imagenPromocionalDao.updateImagenPromocional(imagen);
		return "redirect:../list";
	}

	@RequestMapping(value = "/delete/{idImagen}")
	public String processDelete(@PathVariable String idImagen) {
		imagenPromocionalDao.deleteImagenPromocional(idImagen);
		return "redirect:../list";
	}
}
