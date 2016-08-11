package ar.gob.buenosaires.esb.domain;

public class ESBSyncEvent extends ESBAsyncEvent {

    public ESBSyncEvent(Object object) {
        super(object);
    }
    
    public static ESBEvent createEvent(Object object) {
        return new ESBSyncEvent(object);
    }
}
