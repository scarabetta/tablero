package ar.gob.buenosaires;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ar.gob.buenosaires.config.SpringConfigBETest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BEApplication.class)
//@WebAppConfiguration
@ContextConfiguration(classes = SpringConfigBETest.class)
public class BEApplicationTests {

	@Test
	public void contextLoads() {
	}

}
