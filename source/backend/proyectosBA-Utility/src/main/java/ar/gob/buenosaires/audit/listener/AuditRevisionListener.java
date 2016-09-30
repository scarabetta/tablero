package ar.gob.buenosaires.audit.listener;

import org.hibernate.envers.RevisionListener;

import ar.gob.buenosaires.audit.domain.AuditRevisionEntity;
import ar.gob.buenosaires.audit.storage.AuditRevisionStorage;
import ar.gob.buenosaires.context.ApplicationContextProvider;

public class AuditRevisionListener implements RevisionListener {
	
	@Override
	public void newRevision(Object revisionEntity) {
		AuditRevisionEntity entity = (AuditRevisionEntity) revisionEntity;
		
		AuditRevisionStorage revisionStorage = ApplicationContextProvider.getApplicationContext().getBean(AuditRevisionStorage.class);
		
		if(revisionStorage != null && revisionStorage.getUserEmail() != null){
			entity.setUsuario(revisionStorage.getUserEmail());
		}
	}
}