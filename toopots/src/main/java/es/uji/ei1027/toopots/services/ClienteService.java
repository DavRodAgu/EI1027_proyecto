package es.uji.ei1027.toopots.services;

import java.util.Map;

import es.uji.ei1027.toopots.model.Reserva;

import java.util.List;

public interface ClienteService {
	public Map<Reserva, String> getReservaByClient(String idCliente);
}
