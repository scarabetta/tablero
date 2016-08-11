package ar.gob.buenosaires.mvc.cors.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public class CorsConfigurer extends WebMvcConfigurerAdapter {

	@Value("${security.cors.mappings}")
	private String mappings;
	
	@Value("${security.cors.origins}")
	private String origins;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping(mappings).allowedOrigins(origins).allowedMethods(HttpMethod.GET.name(),
				HttpMethod.HEAD.name(), HttpMethod.POST.name(), HttpMethod.DELETE.name(),HttpMethod.PUT.name(),
				HttpMethod.OPTIONS.name());
	}
}
