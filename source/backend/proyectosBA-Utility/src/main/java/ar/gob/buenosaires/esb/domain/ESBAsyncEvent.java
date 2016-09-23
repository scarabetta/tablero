package ar.gob.buenosaires.esb.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import ar.gob.buenosaires.esb.exception.ESBException;

public class ESBAsyncEvent implements ESBEvent {

	protected Object inputObject;
    protected ArrayList<ESBException> exceptions = new ArrayList<>();
    protected Message inputMsg;
    protected Object outputObj;
    protected Message outputMsg;
    protected String type;
    protected String action;
    protected String origin;
    protected String companyCode;
    protected String countryCode;
    protected long expiration = 0L;
    protected String requestStatus;
    protected String errorCode;
    protected String statusDescription;
    protected Destination replyToDestination;
    protected String xml;
    
    protected Map<String, Object> properties;

    public ESBAsyncEvent(final Object inputObject) {
        this.inputObject = inputObject;
        this.requestStatus = ESBEvent.STATUS_SUCCESSFUL;
        this.statusDescription = "";
        this.properties = new HashMap<>();
    }
    
    /**
     * @return the exceptions
     */
    @Override
    public ArrayList<ESBException> getExceptions() {
        return exceptions;
    }
    
    /**
     * @param arrayList the exceptions to set
     */
    @Override
    public void setExceptions(ArrayList arrayList) {
        this.exceptions = arrayList;
    }

    @Override
    public void setObj(final Object t) {
        this.inputObject = t;
    }

    @Override
    public Object getObj() {
        return inputObject;
    }

    @Override
    public String toString() {
        final StringBuilder retval = new StringBuilder();
        try {
            retval.append("[");
            retval.append(String.format("[redelivered=%s, "
                    + "action=%s, type=%s, "
                    + "origin=%s, "
                    + "statusDescription=\"%s\"] ",
                    this.isRedelivered(),
                    this.getAction(),
                    this.getType(),
                    this.getOrigin(),
                    this.getStatusDescription(),
                    this.getErrorCode()));
        } catch (final JMSException ex) {
        }
        return retval.toString();
    }

    /**
     * @return the inputMsg
     */
    @Override
    public Message getInputMsg() {
        return inputMsg;
    }

    /**
     * @param inputMsg the inputMsg to set
     */
    @Override
    public void setInputMsg(final Message inputMsg) {
        this.inputMsg = inputMsg;
    }

    /**
     * @return the outputObj
     */
    public Object getOutputObj() {
        return outputObj;
    }

    /**
     * @param outputObj the outputObj to set
     */
    public void setOutputObj(final Object outputObj) {
        this.outputObj = outputObj;
    }

    /**
     * @return the outputMsg
     */
    @Override
    public Message getOutputMsg() {
        return outputMsg;
    }

    /**
     * @param outputMsg the outputMsg to set
     */
    @Override
    public void setOutputMsg(final Message outputMsg) {
        this.outputMsg = outputMsg;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(final String type) {
        this.type = type;
    }

    @Override
    public String getAction() {
        return action;
    }

    @Override
    public void setAction(final String action) {
        this.action = action;
    }

    @Override
    public void setOrigin(final String origin) {
        this.origin = origin;
    }

    @Override
    public String getOrigin() {
        return origin;
    }

    @Override
    public long getExpiration() {
        return expiration;
    }

    @Override
    public void setExpiration(final long expiration) {
        this.expiration = expiration;
    }

    @Override
    public Long getTimestamp() throws JMSException {
        return (inputMsg == null) ? null : inputMsg.getJMSTimestamp();
    }

    @Override
    public Boolean isRedelivered() throws JMSException {
        return (inputMsg == null) ? null : inputMsg.getJMSRedelivered();
    }

    @Override
    public String getRequestStatus() {
        return requestStatus;
    }

    @Override
    public void setRequestStatus(final String requestStatus) {
        this.requestStatus = requestStatus;
    }

    @Override
    public String getStatusDescription() {
        return statusDescription;
    }

    @Override
    public void setStatusDescription(final String statusDescription) {
        this.statusDescription = statusDescription;
    }

    @Override
    public String getErrorCode() {
    	return errorCode;
    }
    
    @Override
    public void setErrorCode(final String errorCode) {
    	this.errorCode = errorCode;
    }

    @Override
    public Destination getReplyToDestination() {
        return replyToDestination;
    }

    @Override
    public void setReplyToDestination(final Destination replyToDestination) {
        this.replyToDestination = replyToDestination;
    }

    public Object getProperty(String key) {
        return this.properties.get(key);
    }
    
    public void addProperty(String key, String value) {
        properties.put(key, value);
    }

	@Override
	public void setXml(String xml) {
		this.xml = xml;
	}

	@Override
	public String getXml() {
		return xml;
	}
}
