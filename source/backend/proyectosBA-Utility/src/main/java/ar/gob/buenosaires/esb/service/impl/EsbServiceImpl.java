package ar.gob.buenosaires.esb.service.impl;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.producer.EsbProducer;
import ar.gob.buenosaires.esb.service.EsbService;
import ar.gob.buenosaires.esb.util.JMSUtil;

public class EsbServiceImpl implements EsbService {

	private final static Logger LOGGER = LoggerFactory.getLogger(EsbServiceImpl.class);

	private static final String UNEXPECTED_EVENT_REQUEST_STATUS_S = "El mensaje enviado por el BUS ha vuelto en un estado inseperado: \"%s\" del mensaje \"%s\"";

	private EsbProducer esbProducer;

	public EsbServiceImpl(EsbProducer esbProducer) {
		super();
		this.esbProducer = esbProducer;
	}

	@Override
	public EsbBaseMsg sendToBus(final EsbBaseMsg req, final String origin, final String action, final Class<?> clase) throws ESBException, JMSException {
		EsbBaseMsg retval = null;
		final ESBEvent event = JMSUtil.createSyncEvent(req, origin, action);

		try {
			// Publish a msg to the ESB
			esbProducer.sendMessage(event);
		} catch (final ESBException ex) {
			getLogger().error(ex.getMessage());
			throw ex;
		}

		if (event.getExceptions().isEmpty()) {
			if (event.getRequestStatus().equalsIgnoreCase(ESBEvent.STATUS_SUCCESSFUL)) {
//				retval = (EsbBaseMsg) event.getObj();
				retval = (EsbBaseMsg) JMSUtil.crearObjeto(event.getXml(), clase);
			} else {
				getLogger().error(String.format(UNEXPECTED_EVENT_REQUEST_STATUS_S,

						event.getRequestStatus(), event.toString()));
				throw new ESBException(event.getErrorCode(), event.getStatusDescription());
			}
		} else {
			logExceptions(event);
			raiseEsbException(event);
		}

		return retval;
	}

	/**
	 * Will retrieve the exception from the event and throw it.
	 *
	 * @param event
	 * @throws EsbException
	 */
	private void raiseEsbException(final ESBEvent event) throws ESBException {
		if (!event.getExceptions().isEmpty()) {
			final Exception excp = (Exception) event.getExceptions().get(0);
			if (excp != null) {
				throw new ESBException(excp.getLocalizedMessage());
			}
		}
	}

	private void logExceptions(final ESBEvent event) {
		for (int i = 0; i < event.getExceptions().size(); i++) {
			final Exception exception = (Exception) event.getExceptions().get(i);
			logException(exception, event.getStatusDescription());
		}
	}

	private void logException(final Exception exception, final String exceptionDesc) {
		getLogger().error(exceptionDesc, exception);
	}

	private Logger getLogger() {
		return LOGGER;
	}
}
