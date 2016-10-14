package ar.gob.buenosaires.service.impl;

import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.message.ProyectoRespMsg;
import ar.gob.buenosaires.service.IGenericService;

public abstract class AbstractSeriviceImpl implements IGenericService {

	@Override
	public Object executeCustomStatement(ESBEvent event, ProyectoRespMsg response, String statement) {
		return null;
	}

}
