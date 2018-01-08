package com.redhat.btison.enmasse.producer.jms.sb;

import javax.jms.ConnectionFactory;

import org.amqphub.spring.boot.jms.autoconfigure.AMQP10JMSConnectionFactoryFactory;
import org.amqphub.spring.boot.jms.autoconfigure.AMQP10JMSProperties;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@Configuration
public class ApplicationConfiguration {

    @Autowired
    private AMQP10JMSProperties properties;

    @Value("${amqpjms.session-cache-size}")
    private int jmsSessionCacheSize;

    @Bean
    public JacksonJsonProvider jsonProvider(ObjectMapper objectMapper) {
        JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
        provider.setMapper(objectMapper);
        return provider;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        JmsConnectionFactory jcf = new AMQP10JMSConnectionFactoryFactory(properties)
                .createConnectionFactory(JmsConnectionFactory.class);
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(jcf);
        connectionFactory.setSessionCacheSize(jmsSessionCacheSize);
        return connectionFactory;
    }
}
