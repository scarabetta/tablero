package ar.gob.buenosaires.service;

import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.message.ProyectoRespMsg;

public interface IGenericService {
	public Object executeCustomStatement(ESBEvent event, ProyectoRespMsg response, String statement);
}
