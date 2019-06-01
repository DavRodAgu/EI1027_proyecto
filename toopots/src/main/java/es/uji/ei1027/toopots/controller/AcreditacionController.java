package es.uji.ei1027.toopots.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.toopots.dao.AcreditacionDao;
import es.uji.ei1027.toopots.model.Acreditacion;


@Controller
@RequestMapping("/acreditacion") 
public class AcreditacionController {

   private AcreditacionDao acreditacionDao;

   @Autowired
   public void setAcreditacionDao(AcreditacionDao acreditacionDao) { 
       this.acreditacionDao=acreditacionDao;
   }

   @RequestMapping("/list")
   public String listAcreditaciones(Model model) {
      model.addAttribute("acreditaciones", acreditacionDao.getAcreditaciones());
      return "acreditacion/list";
   }

   @RequestMapping(value="/add") 
	public String addAcreditacion(Model model) {
		model.addAttribute("acreditacion", new Acreditacion());
		return "acreditacion/add";
	}

   @RequestMapping(value="/add", method=RequestMethod.POST) 
   public String processAddSubmit(@ModelAttribute("acreditacion") Acreditacion acreditacion,
                                   BindingResult bindingResult) {  
   	 if (bindingResult.hasErrors()) 
   			return "acreditacion/add";
   	 acreditacionDao.addAcreditacion(acreditacion);
   	 return "redirect:list"; 
    }

   @RequestMapping(value="/update/{idAcreditacion}", method = RequestMethod.GET) 
	public String editAcreditacion(Model model, @PathVariable int idAcreditacion) { 
		model.addAttribute("acreditacion", acreditacionDao.getAcreditacion(idAcreditacion));
		return "acreditacion/update"; 
	}

   @RequestMapping(value="/update/{idAcreditacion}", method = RequestMethod.POST) 
	public String processUpdateSubmit(@PathVariable String idAcreditacion, 
                           @ModelAttribute("acreditacion") Acreditacion acreditacion, 
                           BindingResult bindingResult) {
		 if (bindingResult.hasErrors()) 
			 return "acreditacion/update";
		 acreditacionDao.updateAcreditacion(acreditacion);
		 return "redirect:../list"; 
	}
   
   @RequestMapping(value="/delete/{idAcreditacion}")
	public String processDelete(@PathVariable String idAcreditacion) {
          acreditacionDao.deleteAcreditacion(idAcreditacion);
          return "redirect:../list"; 
	}
   
   @RequestMapping(value="/rechaza/{idAcreditacion}")
	public String processRechaza(@PathVariable int idAcreditacion) {
          acreditacionDao.rechazaAcreditacion(idAcreditacion);
          return "redirect:../../admin/solicitudes"; 
	}
   
   @RequestMapping(value="/acepta/{idAcreditacion}")
	public String processAcepta(@PathVariable int idAcreditacion) {
          acreditacionDao.aceptaAcreditacion(idAcreditacion);
          return "redirect:../../admin/solicitudes"; 
	}
}