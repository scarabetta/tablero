package ar.gob.buenosaires;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;

@SpringBootApplication(exclude = JmsAutoConfiguration.class)
public class BEApplication {

	public static void main(String[] args) {
		SpringApplication.run(BEApplication.class, args);
	}
}
