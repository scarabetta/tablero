package ar.gob.buenosaires.esb.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class EsbBaseMsg {
	private String userTokenId;
	private String emailUsuario;

	public abstract String getEventType();

	private String customStatement;

	private List customStatementResult;

	@XmlAttribute(name = "userTokenId")
	public String getUserTokenId() {
		return userTokenId;
	}

	public void setUserTokenId(final String userTokenId) {
		this.userTokenId = userTokenId;
	}


	@XmlAttribute(name = "emailUsuario")
	public String getEmailUsuario() {
		if(emailUsuario == null){
			emailUsuario = "";
		}
		return emailUsuario;
	}

	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}
	
	/**
	 * @return the customStatement
	 */
	public String getCustomStatement() {
		return customStatement;
	}

	/**
	 * @param customStatement the customStatement to set
	 */
	public void setCustomStatement(String customStatement) {
		this.customStatement = customStatement;
	}

	/**
	 * @return the customStatementResult
	 */
	public List getCustomStatementResult() {
		if(customStatementResult == null){
			customStatementResult = new ArrayList<>();
		}
		return customStatementResult;
	}

	/**
	 * @param customStatementResult the customStatementResult to set
	 */
	public void setCustomStatementResult(List customStatementResult) {
		this.customStatementResult = customStatementResult;
	}
}
