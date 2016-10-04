package ar.gob.buenosaires.esb.domain;

import java.util.ArrayList;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import ar.gob.buenosaires.esb.exception.ESBException;

public interface ESBEvent {
	
    public static final String TYPE_TAG = "ObjectType";
    public static final String STATUS_TAG = "RequestStatus";
    public static final String STATUS_SUCCESSFUL = "0000";
    public static final String STATUS_SUCCESSFUL_DESC = "Processed Successfully";
    public static final String STATUS_FAIL = "9999";
    public static final String STATUS_FAIL_DESC = "Fail While Processing";
    public static final String STATUS_DESC_TAG = "RequestStatusDesc";
    public static final String ERROR_CODE_TAG = "RequestErrorCode";
    public static final String ACTION_TAG = "RequestType";
    public static final String ACTION_CREATE = "CREATE";
    public static final String ACTION_DEFAULT = ACTION_CREATE;
    public static final String ACTION_RETRIEVE = "RETRIEVE";
    public static final String ACTION_RETRIEVE_RESUMEN = "RETRIEVE_RESUMEN";
    public static final String ACTION_RETRIEVE_ACTIONS = "RETRIEVE_ACTIONS";
    public static final String ACTION_REQUEST = "REQUEST";
    public static final String ACTION_UPDATE = "UPDATE";
    public static final String ACTION_DELETE = "DELETE";
    public static final String ACTION_CANCEL = "CANCEL";
    public static final String ACTION_ETIQUETAR = "ETIQUETAR";
    public static final String ACTION_DESHACER_CANCELACION = "DESHACER_CANCELACION";
    public static final String ACTION_VERIFICAR = "Verificar";
    public static final String ACTION_PRESENTAR = "PRESENTAR";
    public static final String ACTION_PRESENTAR_TODOS = "PRESENTAR_TODOS";
    public static final String ACTION_CANCELAR_PRIORIZACION = "CANCELAR_PRIORIZACION";
    public static final String ACTION_INICIAR_PRIORIZACION = "INICIAR_PRIORIZACION";
    public static final String ORIGIN_TAG = "Origin";
    public static final String DEFAULT_ORIGIN = "UNKNOWN";
    public static final String COMPANYCODE_TAG = "CompanyCode";
    public static final String COUNTRYCODE_TAG = "CountryCode";
    public static final String USER_EMAIL_TAG = "UserEmail";
    public static final String MANIFEST_TAG = "Manifest";
    public static final String PATH = "Path";
    public static final String TIME_STAMP = "timestamp";
    public static final String CORRELATION_ID = "correlationId";
    public static final String DESTINATION_CONNECTION_KEY = "destination_connection_key";
    public static final String ORIGIN_CONNECTION_KEY = "origin_connection_key";

	public void setObj(Object obj);

    public Object getObj();

    @Override
    public String toString();

    public ArrayList<ESBException> getExceptions();

    public void setExceptions(ArrayList<ESBException> arrayList);

    public void setOutputMsg(Message outputMsg);

    public Message getOutputMsg();

    public void setInputMsg(Message inputMsg);

    public Message getInputMsg();

    public void setType(String string);

    public String getType();

    public void setAction(String action);

    public String getAction();

    public void setOrigin(String origin);

    public String getOrigin();

    public long getExpiration();

    public void setExpiration(long jMSExpiration);

    public Long getTimestamp() throws JMSException;

    public Boolean isRedelivered() throws JMSException;

    public String getRequestStatus();

    public void setRequestStatus(String requestStatus);
    
    public String getErrorCode();

    public void setErrorCode(String errorCode);

    public String getStatusDescription();

    public void setStatusDescription(String statusDescription);
    
    public Destination getReplyToDestination();

    public void setReplyToDestination(Destination replyToDestination);
    
    public Object getProperty(String key);
    
    public void setXml(String xml);
    
    public String getXml();

	public void setUserEmail(String emailUsuario);
	public String getUserEmail();
}
