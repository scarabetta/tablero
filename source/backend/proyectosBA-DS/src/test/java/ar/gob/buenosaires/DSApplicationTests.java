package ar.gob.buenosaires;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import ar.gob.buenosaires.config.SpringConfigDSTest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DSApplication.class)
@WebAppConfiguration
@ContextConfiguration(classes = SpringConfigDSTest.class)
public class DSApplicationTests {

	@Test
	public void contextLoads() {
	}

}
