package ar.gob.buenosaires.esb.util;

import java.io.StringReader;
import java.io.StringWriter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.xml.sax.SAXParseException;

import ar.gob.buenosaires.esb.domain.ESBAsyncEvent;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.ESBSyncEvent;

public class JMSUtil {

	public static String marshal(final Object message, Jaxb2Marshaller marshaller) {
        StringWriter out = new StringWriter();
        marshaller.marshal(message, new StreamResult(out));
        return out.toString();
    }
    
    public static Object unmarshal(final String string, Jaxb2Marshaller marshaller) {
        return marshaller.unmarshal(new StreamSource(new StringReader(string)));
    }
    
    public static void constructJmsMsg(final Message m, final ESBEvent event) throws JMSException {
        final String eventType = event.getType() == null ? event.getObj().getClass().getSimpleName() : event.getType();
        m.setStringProperty(ESBEvent.TYPE_TAG, eventType);
        final String action = event.getAction() == null ? ESBEvent.ACTION_DEFAULT : event.getAction();
        m.setStringProperty(ESBEvent.ACTION_TAG, action);
        final String origin = event.getOrigin() == null ? ESBEvent.DEFAULT_ORIGIN : event.getOrigin();
        m.setStringProperty(ESBEvent.ORIGIN_TAG, origin);
        final String requestStatus = event.getRequestStatus() == null ? ESBEvent.STATUS_SUCCESSFUL : event.getRequestStatus();
        m.setStringProperty(ESBEvent.STATUS_TAG, requestStatus);
        final String statusDescription = event.getStatusDescription() == null ? "" : event.getStatusDescription();
        m.setStringProperty(ESBEvent.STATUS_DESC_TAG, statusDescription);
        m.setJMSExpiration(event.getExpiration());
        m.setJMSReplyTo(event.getReplyToDestination());
        event.setOutputMsg(m);
    }

    public static ESBEvent constructEvent(final Message jmsMsg, final String s, Object message) throws SAXParseException, JMSException {
//        final Object message = (StringUtils.isBlank(s)) ? "" : unmarshal(s, marshaller);
        final ESBEvent event = new ESBAsyncEvent(message);
        event.setInputMsg(jmsMsg);
//        if (message instanceof EsbMessage) {
//            event.setObj(((EsbMessage)message).getMessage());
//        }
        event.setType(jmsMsg.getStringProperty(ESBEvent.TYPE_TAG));
        event.setAction(jmsMsg.getStringProperty(ESBEvent.ACTION_TAG));
        event.setOrigin(jmsMsg.getStringProperty(ESBEvent.ORIGIN_TAG));
        event.setRequestStatus(jmsMsg.getStringProperty(ESBEvent.STATUS_TAG));
        event.setStatusDescription(jmsMsg.getStringProperty(ESBEvent.STATUS_DESC_TAG));
        event.setExpiration(jmsMsg.getJMSExpiration());
        event.setReplyToDestination(jmsMsg.getJMSReplyTo());
        return event;
    }
    
    public static ESBEvent createAsyncEvent(final Object request,
            final String origin) {
        final ESBEvent event = new ESBAsyncEvent(request);
        event.setAction(ESBEvent.ACTION_CREATE);
        event.setOrigin(origin);
        event.setRequestStatus(ESBEvent.STATUS_SUCCESSFUL);
        event.setStatusDescription("Processed Successfully");
        return event;
    }

    public static ESBEvent createSyncEvent(final Object request,
            final String origin, final String action) {
        final ESBEvent event = new ESBSyncEvent(request);
        event.setAction(resolveAction(action));
        event.setOrigin(origin);
        event.setRequestStatus(ESBEvent.STATUS_SUCCESSFUL);
        event.setStatusDescription("Processed Successfully");
        return event;
    }

	/**
	 * valida que la accion no sea null o vacio, sino por default setea que sea un busqueda.
	 * @param action
	 * @return
	 */
	private static String resolveAction(String action) {
		return StringUtils.isNotBlank(action)?action:ESBEvent.ACTION_RETRIEVE;
	}
}
