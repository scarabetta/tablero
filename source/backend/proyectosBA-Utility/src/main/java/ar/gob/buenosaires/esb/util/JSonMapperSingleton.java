package ar.gob.buenosaires.esb.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSonMapperSingleton {

	private ObjectMapper mapper;
	private static JSonMapperSingleton instance = null;
	
	private JSonMapperSingleton() {
		mapper = new ObjectMapper(); 
	}
	
	public static JSonMapperSingleton getInstance() {
		synchronized (JSonMapperSingleton.class) {
			if (instance == null) {
				instance = new JSonMapperSingleton();
			}
		}
		return instance;
	}

	public ObjectMapper getMapper() {
		return mapper;
	}

	public void setMapper(ObjectMapper mapper) {
		this.mapper = mapper;
	}
}
