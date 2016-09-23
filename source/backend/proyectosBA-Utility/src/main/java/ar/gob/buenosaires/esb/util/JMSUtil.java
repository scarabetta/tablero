package ar.gob.buenosaires.esb.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.xml.sax.SAXParseException;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import ar.gob.buenosaires.esb.domain.ESBAsyncEvent;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.ESBSyncEvent;
import ar.gob.buenosaires.esb.exception.CodigoError;
import ar.gob.buenosaires.esb.exception.ESBException;

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
        final String errorCode = event.getErrorCode() == null ? "" : event.getErrorCode();
        m.setStringProperty(ESBEvent.ERROR_CODE_TAG, errorCode);
        m.setJMSExpiration(event.getExpiration());
        m.setJMSReplyTo(event.getReplyToDestination());
        event.setOutputMsg(m);
    }

    public static ESBEvent constructEvent(final Message jmsMsg) throws SAXParseException, JMSException {
        final ESBEvent event = new ESBAsyncEvent(jmsMsg);
        event.setInputMsg(jmsMsg);
        event.setType(jmsMsg.getStringProperty(ESBEvent.TYPE_TAG));
        event.setAction(jmsMsg.getStringProperty(ESBEvent.ACTION_TAG));
        event.setOrigin(jmsMsg.getStringProperty(ESBEvent.ORIGIN_TAG));
        event.setRequestStatus(jmsMsg.getStringProperty(ESBEvent.STATUS_TAG));
        event.setStatusDescription(jmsMsg.getStringProperty(ESBEvent.STATUS_DESC_TAG));
        event.setExpiration(jmsMsg.getJMSExpiration());
        event.setReplyToDestination(jmsMsg.getJMSReplyTo());
        event.setXml(((TextMessage) jmsMsg).getText());
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
	
	/**
	 * Metodo para parsear el XML que viene por el BUS a un objeto.
	 * @param xml
	 * @param clase
	 * @return EL objeto creado desde el XML.
	 * @throws ESBException
	 */
	public static Object crearObjeto(String xml, Class<?> clase) throws ESBException {
		String Xmlrecortado = recortarXml(xml);
		
		XmlMapper xmlMapper = new XmlMapper();
		Object result = null;
		try {
			result = xmlMapper.readValue(Xmlrecortado, clase);
		} catch (IOException e) {
			LoggerFactory.getLogger(clase).error(CodigoError.ERROR_PARSEO.getCodigo(), "Se ha producido un error al querer parsear el XML a Objecto", e);
			throw new ESBException(e);
		}
		
		return result;
	}

	/**
	 * Se debe recortar el XML para sacar la propiedad heredada desde EsbBaseMsg.
	 * @param xml
	 * @return El XML sin la propiedad EvenType.
	 */
	private static String recortarXml(String xml) {
		String cutXml = xml;
		
		if(xml.contains("<eventType>")){
			cutXml = xml.substring(0, xml.indexOf("<eventType>"));
			cutXml = cutXml.concat(xml.substring(xml.indexOf("</eventType>") + 12));
		}
			
		return cutXml;
	}
}
