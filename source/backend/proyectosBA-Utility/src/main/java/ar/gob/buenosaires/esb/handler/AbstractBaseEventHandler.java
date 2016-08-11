package ar.gob.buenosaires.esb.handler;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.exception.ESBException;

@Component
public abstract class AbstractBaseEventHandler implements ESBProcess {

    @Override
    public boolean onMessage(final ESBEvent event) throws ESBException {
        Boolean retval = Boolean.TRUE;

        try {
            if (validateEvent(event)) {
                processLog(event.getOrigin());
                process(event);
                event.setRequestStatus(ESBEvent.STATUS_SUCCESSFUL);
                event.setStatusDescription(ESBEvent.STATUS_SUCCESSFUL_DESC);
                retval = Boolean.FALSE;
                completeLog();
            }
        } catch (final Exception e) {
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
}
