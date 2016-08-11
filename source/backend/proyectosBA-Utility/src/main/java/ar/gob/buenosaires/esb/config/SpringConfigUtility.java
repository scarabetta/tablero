package ar.gob.buenosaires.esb.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
@ComponentScan("ar.gob.buenosaires.jms")
public class SpringConfigUtility {

	private static final String[] JAXB_CONTEXT_PATH = { "ar.gob.buenosaires.domain"
															, "ar.gob.buenosaires.esb.domain"
															, "ar.gob.buenosaires.esb.domain.message" };

	@Value("${esb.jmsTemplate.topic}")
	private boolean jmsTemplateTopic;

	@Value("${esb.pooledConnection.clientID}")
	private String pooledConnectionClientID;

	@Value("${esb.pooledConnecttion.brokerURL}")
	public String pooledConnecttionBrokerURL;

	@Bean
	public Jaxb2Marshaller getJaxb2Marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPaths(JAXB_CONTEXT_PATH);
		return marshaller;
	}

	@Bean
	public JmsTemplate getJmsTemplate(PooledConnectionFactory pooledCF) {
		JmsTemplate jmsTemplate = new JmsTemplate(pooledCF);
		jmsTemplate.setExplicitQosEnabled(true);
		jmsTemplate.setPubSubDomain(jmsTemplateTopic); // topic = true, Queue = false.
		return jmsTemplate;
	}

	@Bean
	public PooledConnectionFactory getPooledConnectionFactory() {
		final ActiveMQConnectionFactory AMQCF = new ActiveMQConnectionFactory();
		AMQCF.setBrokerURL(pooledConnecttionBrokerURL);
		AMQCF.setClientID(pooledConnectionClientID);
		PooledConnectionFactory pooledCF = new PooledConnectionFactory(AMQCF);
		return pooledCF;
	}
}
