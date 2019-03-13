package es.uji.ei1027.toopots.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.toopots.dao.TipoActividadDao;
import es.uji.ei1027.toopots.model.TipoActividad;


@Controller
@RequestMapping("/tipoActividad") 
public class TipoActividadController {

   private TipoActividadDao tipoActividadDao;

   @Autowired
   public void setTipoActividadDao(TipoActividadDao tipoActividadDao) { 
       this.tipoActividadDao=tipoActividadDao;
   }

   @RequestMapping("/list")
   public String listTipoActividades(Model model) {
      model.addAttribute("tipoActividades", tipoActividadDao.getTipoActividades());
      return "tipoActividad/list";
   }

   @RequestMapping(value="/add") 
	public String addTipoActividad(Model model) {
		model.addAttribute("tipoActividad", new TipoActividad());
		return "tipoActividad/add";
	}

   @RequestMapping(value="/add", method=RequestMethod.POST) 
   public String processAddSubmit(@ModelAttribute("tipoActividad") TipoActividad tipoActividad,
                                   BindingResult bindingResult) {  
   	 if (bindingResult.hasErrors()) 
   			return "tipoActividad/add";
   	 tipoActividadDao.addTipoActividad(tipoActividad);
   	 return "redirect:list"; 
    }

   @RequestMapping(value="/update/{idTipoActividad}", method = RequestMethod.GET) 
	public String editTipoActividad(Model model, @PathVariable String idTipoActividad) { 
		model.addAttribute("tipoActividad", tipoActividadDao.getTipoActividad(idTipoActividad));
		return "tipoActividad/update"; 
	}

   @RequestMapping(value="/update/{idTipoActividad}", method = RequestMethod.POST) 
	public String processUpdateSubmit(@PathVariable String idTipoActividad, 
                           @ModelAttribute("tipoActividad") TipoActividad tipoActividad, 
                           BindingResult bindingResult) {
		 if (bindingResult.hasErrors()) 
			 return "tipoActividad/update";
		 tipoActividadDao.updateTipoActividad(tipoActividad);
		 return "redirect:../list"; 
	}
   
   @RequestMapping(value="/delete/{idTipoActividad}")
	public String processDelete(@PathVariable String idTipoActividad) {
          tipoActividadDao.deleteTipoActividad(idTipoActividad);
          return "redirect:../list"; 
	}

}
