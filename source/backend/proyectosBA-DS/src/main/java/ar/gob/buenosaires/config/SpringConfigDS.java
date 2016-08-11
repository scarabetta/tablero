package ar.gob.buenosaires.config;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import ar.gob.buenosaires.esb.producer.EsbProducer;
import ar.gob.buenosaires.esb.producer.EsbSyncProducer;
import ar.gob.buenosaires.esb.service.impl.EsbServiceImpl;

@Configuration
@ComponentScan("ar.gob.buenosaires")
@PropertySources({
    @PropertySource(value = "file:/etc/pba/ds/application.properties")
    , @PropertySource("classpath:importador.properties")
})
public class SpringConfigDS {

	@Bean
	public EsbServiceImpl getEsbService(@Qualifier("EsbSyncProducer") EsbProducer producer){
		return new EsbServiceImpl(producer);
	}
	
	@Bean(name = "EsbSyncProducer")
	public EsbSyncProducer getEsbSyncProducer(){
		return new EsbSyncProducer();
	}
	
//	@Bean
//	public Jackson2ObjectMapperBuilder jacksonBuilder() {
//	    Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
//	    SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD'T'HH:mm:ss.SSSZ");
//	    builder.indentOutput(true).dateFormat(dateFormat);
//	    return builder;
//	}		
}
