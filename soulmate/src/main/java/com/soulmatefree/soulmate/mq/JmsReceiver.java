package com.soulmatefree.soulmate.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soulmatefree.soulmate.model.ZenMaster;
import com.soulmatefree.soulmate.model.ext.ZenMasterQueue;
import com.soulmatefree.soulmate.service.ZenMasterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: baishuzi
 * @Date: 2019/3/18
 * @Description:com.soulmatefree.soulmate.mq
 * @version: 1.0
 */
@Component
public class JmsReceiver {

    @Autowired
    private ZenMasterService zenMasterService;

    private Logger log = LoggerFactory.getLogger(JmsReceiver.class);

    private ObjectMapper om = new ObjectMapper();

    // 发送评价问卷email给founder
    @JmsListener(destination = "sync.message.queue")
    public void sendEvaluateEmail(String msg) {
        log.debug("[s]onMessageIn:sync.message.queue:{}", msg);
        ZenMasterQueue queue;
        try {
            queue = om.readValue(msg, ZenMasterQueue.class);
        } catch (IOException e) {
            log.error("Failed to parse sync.message.queue:{}", msg);
            return;
        }
        if (null == queue) {
            log.debug("[e]onMessageIn: with null msg");
            return;
        }
        ZenMaster zenMaster = new ZenMaster();
        zenMaster.setId(queue.getId());
        zenMaster.setName(queue.getName()+"(3)");
        zenMaster.setNickName(queue.getNickName());
        zenMaster.setDescription(queue.getDescription());
        zenMasterService.update(zenMaster);

    }
}
