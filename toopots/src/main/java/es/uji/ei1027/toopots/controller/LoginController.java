package es.uji.ei1027.toopots.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; 
import org.springframework.web.bind.annotation.ModelAttribute; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestMethod; 
import org.springframework.validation.Errors; 
import org.springframework.validation.Validator;

import es.uji.ei1027.toopots.dao.LoginDao;
import es.uji.ei1027.toopots.model.Login;

class UserValidator implements Validator { 
	@Override
	public boolean supports(Class<?> cls) { 
		return Login.class.isAssignableFrom(cls);
	}
	@Override 
	public void validate(Object obj, Errors errors) {
		Login user = (Login)obj;
		if (user.getUsuario().trim().equals(""))
		       errors.rejectValue("usuario", "obligatorio",
		                          "Debes introducir el usuario");
		
		if (user.getContraseña().trim().equals(""))
		       errors.rejectValue("contraseña", "obligatorio",
		                          "Debes introducir la contraseña");
	}
}

@Controller
public class LoginController {
	@Autowired
	private LoginDao loginDao;

	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new Login());
		return "login";
	}

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String checkLogin(@ModelAttribute("user") Login user,  		
				BindingResult bindingResult, HttpSession session) {
		UserValidator userValidator = new UserValidator(); 
		userValidator.validate(user, bindingResult); 
		if (bindingResult.hasErrors()) {
			return "login";
		}
	       // Comprova que el login siga correcte 
		// intentant carregar les dades de l'usuari 
		user = loginDao.getLogin(user.getUsuario(), user.getContraseña()); 		
		if (user == null) {
			bindingResult.rejectValue("contraseña", "badpw", "Usuario y/o contraseña incorrectos"); 
			return "login";
		}
		System.out.println("-------------" + user.toString());
		// Autenticats correctament. 
		// Guardem les dades de l'usuari autenticat a la sessió
		session.setAttribute("user", user);
		Object url = session.getAttribute("nextUrl");
		if(url != null){
        	session.removeAttribute("nextUrl");
        	return "redirect:/" + url;
        }
		
		switch (user.getRol()) {
		case "cliente":
			return "redirect:/cliente/actividades";
		case "administrador":
			return "redirect:/administrador/home.html";
		case "instructor":
			return "redirect:/instructor/home.html";
		}
			
		// Torna a la pàgina principal
		return "redirect:/";
	}

	@RequestMapping("/logout") 
	public String logout(HttpSession session) {
		session.invalidate(); 
		return "redirect:/";
	}
}