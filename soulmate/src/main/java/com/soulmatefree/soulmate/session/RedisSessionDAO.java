package com.soulmatefree.soulmate.session;


import com.soulmatefree.soulmate.utils.SerializableUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class RedisSessionDAO extends AbstractSessionDAO {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    private static final   String SESSION_PREFIX = "session:";

    private static final Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);


    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session,sessionId);
        redisTemplate.opsForHash().put(SESSION_PREFIX,sessionId, SerializableUtils.serialize(session));
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        logger.info("read sessionId" + sessionId );
        return Optional.ofNullable(sessionId)
                .flatMap(e->Optional.ofNullable(SerializableUtils.deserialize((String) redisTemplate.opsForHash().get(SESSION_PREFIX, e))))
                .orElse(null);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        Optional.ofNullable(session)
                .ifPresent(e->{
                    redisTemplate.opsForHash().put(SESSION_PREFIX,e.getId(), SerializableUtils.serialize(e));
                });
    }

    @Override
    public void delete(Session session) {
        Optional.ofNullable(session)
                .flatMap(e->Optional.ofNullable(e.getId()))
                .ifPresent(e->{
                    redisTemplate.opsForHash().delete(SESSION_PREFIX,e);
                });
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<String> sessionSet = (Set)redisTemplate.opsForHash().values(SESSION_PREFIX);
        if(!CollectionUtils.isEmpty(sessionSet)){
            return sessionSet.stream()
                    .map(e->SerializableUtils.deserialize(e))
                    .collect(Collectors.toSet());
        }
        return null;
    }
}
