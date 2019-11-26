package com.soulmatefree.soulmate.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soulmatefree.soulmate.model.ext.ZenMasterQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @Author: baishuzi
 * @Date: 2019/3/18
 * @Description:com.soulmatefree.soulmate.mq
 * @version: 1.0
 */
@Component
public class JmsProducer {
    @Autowired
    private JmsCreater jmsCreater;

    private Logger log = LoggerFactory.getLogger(JmsProducer.class);

    private ObjectMapper om = new ObjectMapper();


    // tc-app 的相关操作给指数通发送相关的消息通知
    public void sendTestMessageByJms(Integer id, String name, String nickName, String description) {
        ZenMasterQueue queue = new ZenMasterQueue();
        queue.setId(id);
        queue.setName(name);
        queue.setNickName(nickName);
        queue.setDescription(description);
        try {
            final String msg = om.writeValueAsString(queue);
             jmsCreater.send("sync.message.queue", msg);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
