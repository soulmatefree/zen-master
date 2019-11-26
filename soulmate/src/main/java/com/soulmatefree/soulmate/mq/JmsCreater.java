package com.soulmatefree.soulmate.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @Author: baishuzi
 * @Date: 2019/3/18
 * @Description:com.soulmatefree.soulmate.mq
 * @version: 1.0
 */
@Component
public class JmsCreater {

    @Autowired
    private JmsTemplate jmsQueueTemplate;
    /**
     * 发送原始消息 Message
     */
    public void send(String destinationName, final String msg){
        jmsQueueTemplate.send(destinationName, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        });

    }
}
