package ar.gob.buenosaires.mvc.cors.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ar.gob.buenosaires.mvc.cors.impl.CorsConfigurer;

@Configuration
@ComponentScan(basePackages = "ar.gob.buenosaires.mvc.cors")
public class SpringConfigurationCors {

	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new CorsConfigurer();
    }
	

}
