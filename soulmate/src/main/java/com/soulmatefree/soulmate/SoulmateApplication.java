package com.soulmatefree.soulmate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.jms.listener.AbstractJmsListeningContainer;
import org.springframework.jms.listener.MessageListenerContainer;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class SoulmateApplication {

    private  static Boolean  flag ;
    @Value("${spring.activemq.consumers}")
    public  void setFlag(Boolean flag) {
        flag = flag;
    }


    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(SoulmateApplication.class, args);
        JmsListenerEndpointRegistry registry = context.getBean(JmsListenerEndpointRegistry.class);
        if (null == flag ||flag){
            for (MessageListenerContainer container : registry.getListenerContainers()) {
                ((AbstractJmsListeningContainer) container).start();
            }
        }else{
            for (MessageListenerContainer container : registry.getListenerContainers()) {
                ((AbstractJmsListeningContainer) container).shutdown();
            }
        }
    }

}

