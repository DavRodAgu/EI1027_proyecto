package es.uji.ei1027.toopots.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.toopots.dao.InstructorDao;
import es.uji.ei1027.toopots.model.Instructor;


@Controller
@RequestMapping("/instructor") 
public class InstructorController {

   private InstructorDao instructorDao;

   @Autowired
<<<<<<< HEAD
   public void setInstructorDao(InstructorDao instructorDao) { 
=======
   public void setInsructorDao(InstructorDao instructorDao) { 
>>>>>>> 1139f95944f94cda77751406b2360b9e6b5474b8
       this.instructorDao=instructorDao;
   }

   @RequestMapping("/list")
   public String listInstructores(Model model) {
      model.addAttribute("instructores", instructorDao.getInstructores());
      return "instructor/list";
   }

   @RequestMapping(value="/add") 
	public String addInstructor(Model model) {
		model.addAttribute("instructor", new Instructor());
		return "instructor/add";
	}

   @RequestMapping(value="/add", method=RequestMethod.POST) 
   public String processAddSubmit(@ModelAttribute("instructor") Instructor instructor,
                                   BindingResult bindingResult) {  
   	 if (bindingResult.hasErrors()) 
<<<<<<< HEAD
   			return "instructor/add";
=======
   			return "isntructor/add";
>>>>>>> 1139f95944f94cda77751406b2360b9e6b5474b8
   	 instructorDao.addInstructor(instructor);
   	 return "redirect:list"; 
    }

   @RequestMapping(value="/update/{idInstructor}", method = RequestMethod.GET) 
	public String editInstructor(Model model, @PathVariable String idInstructor) { 
		model.addAttribute("instructor", instructorDao.getInstructor(idInstructor));
		return "instructor/update"; 
	}

   @RequestMapping(value="/update/{idInstructor}", method = RequestMethod.POST) 
	public String processUpdateSubmit(@PathVariable String idInstructor, 
                           @ModelAttribute("instructor") Instructor instructor, 
                           BindingResult bindingResult) {
		 if (bindingResult.hasErrors()) 
			 return "instructor/update";
		 instructorDao.updateInstructor(instructor);
		 return "redirect:../list"; 
	}
   
   @RequestMapping(value="/delete/{idInstructor}")
	public String processDelete(@PathVariable String idInstructor) {
          instructorDao.deleteInstructor(idInstructor);
          return "redirect:../list"; 
	}
}