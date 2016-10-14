package ar.gob.buenosaires.service.impl;

import java.util.List;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.domain.Area;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;
import ar.gob.buenosaires.esb.domain.message.AreaReqMsg;
import ar.gob.buenosaires.esb.domain.message.AreaRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.service.EsbService;
import ar.gob.buenosaires.service.AreaService;

@Service
public class AreaServiceImpl implements AreaService {

	private final static Logger LOGGER = LoggerFactory.getLogger(AreaServiceImpl.class);

	@Autowired
	private EsbService esbService;

	@Override
	public List<Area> getAreas() throws ESBException, JMSException {
		AreaReqMsg reqMsg = new AreaReqMsg();

		return getAreasFromReqMsg(reqMsg);
	}

	@Override
	public Area getAreaPorId(String id) throws ESBException, JMSException {
		AreaReqMsg reqMsg = new AreaReqMsg();
		reqMsg.setId(Long.parseLong(id));

		List<Area> areas = getAreasFromReqMsg(reqMsg);
		return getAreaFromResponse(areas);
	}

	@Override
	public List<Area> getAreasByName(String nombre) throws ESBException, JMSException {
		AreaReqMsg reqMsg = new AreaReqMsg();
		reqMsg.setName(nombre);

		List<Area> areas = getAreasFromReqMsg(reqMsg);
		return areas;
	}

	@Override
	public Area getAreasByNameAndIdJurisdiccion(String nombre, Long idJurisdiccion) throws ESBException, JMSException {
		AreaReqMsg reqMsg = new AreaReqMsg();
		reqMsg.setName(nombre);
		reqMsg.setIdJurisdiccion(idJurisdiccion);

		List<Area> areas = getAreasFromReqMsg(reqMsg);
		return getAreaFromResponse(areas);
	}

	@Override
	public List<Area> getAreasByIdJurisdiccion(Long idJurisdiccion) throws ESBException, JMSException {
		AreaReqMsg reqMsg = new AreaReqMsg();
		reqMsg.setIdJurisdiccion(idJurisdiccion);

		List<Area> areas = getAreasFromReqMsg(reqMsg);
		return areas;
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	private List<Area> getAreasFromReqMsg(AreaReqMsg reqMsg) throws ESBException, JMSException {
		getLogger().info("Mensaje creado para obtener una Area : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_RETRIEVE,
				AreaRespMsg.class);

		List<Area> areas = null;
		if (response.getEventType().equalsIgnoreCase(AreaRespMsg.AREA_TYPE)) {
			areas = ((AreaRespMsg) response).getAreas();
			if(getLogger().isDebugEnabled()){
				getLogger().debug("Obteninendo las Areas de la respues del BUS de servicios: {}", areas.toString());
			} else {
				getLogger().info("Obteninendo las Areas de la respues del BUS de servicios.");
			}
		}
		return areas;
	}

	private Area getAreaFromResponse(List<Area> areas) {
		if (!areas.isEmpty()) {
			return areas.get(0);
		} else {
			return null;
		}
	}

}
