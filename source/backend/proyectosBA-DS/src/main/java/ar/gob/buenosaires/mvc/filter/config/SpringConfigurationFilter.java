package ar.gob.buenosaires.mvc.filter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import ar.gob.buenosaires.mvc.filter.impl.ResourceAuthorizationFilter;

@Configuration
@ComponentScan(basePackages = "ar.gob.buenosaires.mvc.filter")
@Profile({ "prod", "dev" })
public class SpringConfigurationFilter {
	
	@Value("${security.jwt.expirationTime.minutes}")
	private int expirationTime;

	@Value("${security.jwt.issuer}")
	private String issuer;

	@Bean()
	public FilterRegistrationBean resourceAuthorizationFilter() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		ResourceAuthorizationFilter filter = new ResourceAuthorizationFilter(expirationTime, issuer);
		registrationBean.setFilter(filter);
		registrationBean.addUrlPatterns("/api/*");
		return registrationBean;
	}
}
