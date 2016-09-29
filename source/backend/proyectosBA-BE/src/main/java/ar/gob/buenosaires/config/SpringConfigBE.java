package ar.gob.buenosaires.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import ar.gob.buenosaires.audit.storage.AuditRevisionStorage;

@Configuration
@ComponentScan("ar.gob.buenosaires")
@PropertySources({
    @PropertySource("file:/etc/pba/be/application.properties")
})
public class SpringConfigBE {

	@Bean
	public AuditRevisionStorage getAuditRevisionStorage(){
		return new AuditRevisionStorage();
	}
}
