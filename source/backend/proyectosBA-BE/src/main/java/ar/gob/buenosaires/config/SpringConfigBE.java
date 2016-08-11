package ar.gob.buenosaires.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@ComponentScan("ar.gob.buenosaires")
@PropertySources({
    @PropertySource("file:/etc/pba/be/application.properties")
})
public class SpringConfigBE {

}
