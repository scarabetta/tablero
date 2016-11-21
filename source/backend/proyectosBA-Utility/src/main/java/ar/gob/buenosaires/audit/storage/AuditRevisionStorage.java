package ar.gob.buenosaires.audit.storage;

import java.util.HashMap;
import java.util.Map;

public class AuditRevisionStorage {
	
	private Map<String, String> usersEmail;

	public Map<String, String> getUsersEmail() {
		if(usersEmail == null){
			usersEmail = new HashMap<String, String>();
		}
		return usersEmail;
	}

}
