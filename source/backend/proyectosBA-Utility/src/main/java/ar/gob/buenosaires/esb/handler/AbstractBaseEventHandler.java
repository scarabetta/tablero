package ar.gob.buenosaires.esb.handler;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import ar.gob.buenosaires.audit.storage.AuditRevisionStorage;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.exception.CodigoError;
import ar.gob.buenosaires.esb.exception.ESBException;

@Component
public abstract class AbstractBaseEventHandler implements ESBProcess {
	
	private ObjectReader reader;
	
	@Autowired
	private AuditRevisionStorage revisionStorage;
	
    @Override
    public boolean onMessage(final ESBEvent event) throws ESBException {
        Boolean retval = Boolean.TRUE;

        try {
            if (validateEvent(event)) {
                processLog(event.getOrigin());
                saveAuditUser(event);
                process(event);
                removeAuditUser();
                event.setRequestStatus(ESBEvent.STATUS_SUCCESSFUL);
                event.setStatusDescription(ESBEvent.STATUS_SUCCESSFUL_DESC);
                retval = Boolean.FALSE;
                completeLog();
            }
        } catch (final Exception e) {
        	removeAuditUser();
            updateEventException(event, e);
            throw e;
        }
        return retval;
    }

	/**
     * Update the status and the description of the Exception in the event.
     * 
     * @param event
     * @param exception
     *            .
     */
    private void updateEventException(final ESBEvent event,
            final Exception e) {
        event.setRequestStatus(ESBEvent.STATUS_FAIL);
        event.setStatusDescription(e.getLocalizedMessage());
        if(e instanceof ESBException) {
        	event.setErrorCode(((ESBException)e).getCodigoError());
        } else {
        	event.setErrorCode("500");
        }
    }

    private void completeLog() {
        getLogger()
                .debug(" completed processing event message.");
    }

    private void processLog(final String origin) {
        getLogger().debug(": processing {} message.\"", origin);
    }

    /**
     * Log method to use inside the {@code AbstractBaseEventHandler.process()}
     * implementation
     * 
     * @param event
     * @param class1
     */
    protected void logRequestMessage(final ESBEvent event,
            final Class<?> class1) {
        getLogger().info("Request message: {} from {} to be process by the class: {}",
                new Object[]{event.getObj().toString(), event.getOrigin(), class1});
    }

    /**
     * Log method to use inside the {@code AbstractBaseEventHandler.process()}
     * implementation
     * 
     * @param event
     * @param class1
     */
    protected void logResponseMessage(final ESBEvent event,
            final Class<?> class1) {
        getLogger().info(
                "Response message: {}  has been processed by the class: {}",
                event.getObj().toString(), class1);
    }

    /**
     * @param event
     * @return true if is the right handler for the given message
     */
    protected abstract boolean validateEvent(final ESBEvent event);

    /**
     * Process the given message calling the corresponding services.
     * 
     * @param event
     * @throws PolarException
     * @throws RepositoryException
     */
    protected abstract void process(final ESBEvent event) throws ESBException;
    
    protected abstract Logger getLogger();
    
    protected ObjectReader getReader(Class<?> clase) {
		if(reader == null){
			XmlMapper xmlMapper = new XmlMapper();
			reader = xmlMapper.readerFor(clase);
		}
		return reader;
	}
	
    /**
     * se guarda el usuario, para luego obetenerlo desde el listener para la auditoria.
     * @param email
     * @throws ESBException 
     */
    protected void saveAuditUser(ESBEvent event) throws ESBException{
    	String action = event.getAction();
    	String email = event.getUserEmail();
    	
    	// Siempre asegurarnos de borrar el ususario anterior por si hubo una exception.
    	removeAuditUser();
    	
    	if(!(action.startsWith(ESBEvent.ACTION_RETRIEVE))) {
			if(email == null || StringUtils.isBlank(email)){
				throw new ESBException(CodigoError.FALTA_USUARIO.getCodigo(), "Falta el usuario que esta realizando la accion en el mensaje enviado.");
			}
			
			revisionStorage.setUserEmail(email);
    	}
    }
    

    /**
     * Se limpia el usuario.
     */
    private void removeAuditUser() {
    	revisionStorage.setUserEmail("");
	}
}
