package es.uji.ei1027.toopots.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.toopots.dao.AcreditaDao;
import es.uji.ei1027.toopots.model.Acredita;


@Controller
@RequestMapping("/acredita") 
public class AcreditaController {

   private AcreditaDao acreditaDao;

   @Autowired
   public void setAcreditaDao(AcreditaDao acreditaDao) { 
       this.acreditaDao=acreditaDao;
   }

   @RequestMapping("/list")
   public String listAcreditaciones(Model model) {
      model.addAttribute("acreditas", acreditaDao.getAcreditaciones());
      return "acredita/list";
   }

   @RequestMapping(value="/add") 
	public String addAcreditacion(Model model) {
		model.addAttribute("acredita", new Acredita());
		return "acredita/add";
	}

   @RequestMapping(value="/add", method=RequestMethod.POST) 
   public String processAddSubmit(@ModelAttribute("acredita") Acredita acredita,
                                   BindingResult bindingResult) {  
   	 if (bindingResult.hasErrors()) 
   			return "acreditacion/add";
   	 acreditaDao.addAcredita(acredita);
   	 return "redirect:list"; 
    }
   
   @RequestMapping(value="/delete/{idAcreditacion}")
	public String processDelete(@PathVariable int idAcreditacion) {
          acreditaDao.deleteAcredita(idAcreditacion);
          return "redirect:../list"; 
	}
}