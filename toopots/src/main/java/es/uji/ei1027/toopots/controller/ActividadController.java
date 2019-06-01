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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.uji.ei1027.toopots.dao.ActividadDao;
import es.uji.ei1027.toopots.dao.ReservaDao;
import es.uji.ei1027.toopots.dao.TipoActividadDao;
import es.uji.ei1027.toopots.model.Actividad;
import es.uji.ei1027.toopots.model.Login;
import es.uji.ei1027.toopots.model.Reserva;

@Controller
@RequestMapping("/actividad")
public class ActividadController {

	private ActividadDao actividadDao;
	private TipoActividadDao tipoActividadDao;
	
	@Autowired
	public void setActividadDao(ActividadDao actividadDao) {
		this.actividadDao = actividadDao;
	}

	@Autowired
	public void setTipoActividadDao(TipoActividadDao tipoActividadDao) {
		this.tipoActividadDao = tipoActividadDao;
	}


	@RequestMapping(value = "/cancelar/{idActividad}")
	public String processCancelar(@PathVariable String idActividad, RedirectAttributes redirectAttributes) {
		Actividad actividad = actividadDao.getActividad(idActividad);
		actividad.setEstado("cancelada");
		actividadDao.updateActividad(actividad);
		return "redirect:../../instructor/actividades";
	}
	
	@RequestMapping(value = "/borrar/{idActividad}")
	public String processBorrar(@PathVariable String idActividad, RedirectAttributes redirectAttributes) {
		actividadDao.deleteActividad(idActividad);
		return "redirect:../../instructor/actividades";
	}
}