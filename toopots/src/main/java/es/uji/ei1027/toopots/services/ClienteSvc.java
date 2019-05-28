package es.uji.ei1027.toopots.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.ei1027.toopots.dao.ActividadDao;
import es.uji.ei1027.toopots.dao.ReservaDao;
import es.uji.ei1027.toopots.model.Reserva;

@Service
public class ClienteSvc implements ClienteService {
	
	@Autowired
	private ActividadDao actividadDao;
	
	@Autowired
	private ReservaDao reservaDao;
	
	@Override
	public Map<Reserva, String> getReservaByClient(String idCliente) {
		List<Reserva> reservas = reservaDao.getReservasUsuario(idCliente);
		HashMap<Reserva, String> nombreActividadesReservadas = new HashMap<Reserva, String>();
		for (Reserva rsrv : reservas) {
			String idActividad = Integer.toString(rsrv.getIdActividad());
			String nombre = actividadDao.getActividad(idActividad).getNombre();
			nombreActividadesReservadas.put(rsrv, nombre);
		}
		return nombreActividadesReservadas;
	}

}
