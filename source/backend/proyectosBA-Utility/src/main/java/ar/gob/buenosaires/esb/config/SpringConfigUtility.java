package ar.gob.buenosaires.esb.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

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

	@Bean(name = "Consumer")
	public JmsTemplate getJmsTemplateConsumer(PooledConnectionFactory pooledCF) {
		JmsTemplate jmsTemplate = new JmsTemplate(pooledCF);
		jmsTemplate.setExplicitQosEnabled(true);
		jmsTemplate.setPubSubDomain(jmsTemplateTopic); // topic = true, Queue = false.
		jmsTemplate.setTimeToLive(1000);
		jmsTemplate.setDeliveryPersistent(false);
		return jmsTemplate;
	}
	
	@Bean(name = "Producer")
	public JmsTemplate getJmsTemplateProducer(PooledConnectionFactory pooledCF) {
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
	
	@Bean
	public ObjectWriter getObjectWriter(){
		XmlMapper xmlMapper = new XmlMapper();
		ObjectWriter writer = xmlMapper.writer();
		return writer;
	}
}
