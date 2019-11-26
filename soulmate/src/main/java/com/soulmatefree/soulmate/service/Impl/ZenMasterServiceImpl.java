package com.soulmatefree.soulmate.service.Impl;

import com.soulmatefree.soulmate.mapper.ZenMasterMapper;
import com.soulmatefree.soulmate.model.ZenMaster;
import com.soulmatefree.soulmate.model.vo.Dictionary;
import com.soulmatefree.soulmate.mq.JmsProducer;
import com.soulmatefree.soulmate.service.ZenMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;

/**
 * @Author: baishuzi
 * @Date: 2019/3/17
 * @Description:com.soulmatefree.soulmate.service.Impl
 * @version: 1.0
 */
@Service("zenMasterService")
public class ZenMasterServiceImpl implements ZenMasterService {

    @Autowired
    private ZenMasterMapper mapper ;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    private JmsProducer jmsProducer ;

    @Override
    public int deleteZenMaster(Integer id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int save(ZenMaster record) {
        return mapper.insert(record);
    }

    @Override
    public ZenMaster findZenMaster(Integer id) {
        ZenMaster zenMaster = mapper.selectByPrimaryKey(id);
        jmsProducer.sendTestMessageByJms(id,zenMaster.getName(),zenMaster.getNickName(),zenMaster.getDescription());
        return zenMaster;
    }

    @Override
    public int update(ZenMaster record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<ZenMaster> findAll() {
        return mapper.selectZenMasterList();
    }

    @Override
    public Object findAllFromRedis(String groupKey, String hashKey) {
        Object result = redisTemplate.opsForHash().get(groupKey, hashKey);
        if (null == result) {
            List<String> list = new ArrayList<String>();
            List<ZenMaster> zenMasters = findAll();

            for (ZenMaster zenMaster : zenMasters) {
                list.add(zenMaster.getName());
            }
            redisTemplate.opsForHash().put(groupKey, hashKey, list);
            return redisTemplate.opsForHash().get(groupKey, hashKey);
        } else {
            return result;
        }
    }

    @Override
    public Object reloadZenMastersRedis(String groupKey, String hashKey) {
        Instant start = Instant.now();
        List<Dictionary> dictionaries = mapper.selectToolDictionary();
        Optional.ofNullable(dictionaries)
                .ifPresent(e->{
                    Map<String, List<Dictionary>> map = e.stream()
                            .distinct()
                            .filter(t->!StringUtils.isEmpty(t.getCategory()))
                            .collect(groupingBy(Dictionary::getCategory));

                    map.forEach((key,values)->{
                        values.stream()
                                .forEach(t->{
                                    redisTemplate.opsForSet().add(key,t);
                                });
                    });
                });
        System.out.println("Duration.between(start,Instant.now()).getSeconds() = " + Duration.between(start, Instant.now()).getSeconds());
        redisTemplate.delete(groupKey);
        return  findAllFromRedis( groupKey, hashKey);
    }

    @Override
    public Set<ZenMaster> findAllFromRedisByList(String groupKey) {
        Set<ZenMaster> members = (Set)redisTemplate.opsForSet().members(groupKey);
        if (CollectionUtils.isEmpty(members)){
            List<ZenMaster> zenMasters = findAll();
            Optional.ofNullable(zenMasters)
                    .ifPresent(e->{
                        e.stream()
                                .forEach(v->{
                                    redisTemplate.opsForSet().add(groupKey,v);
                                });
                    });
            return (Set)redisTemplate.opsForSet().members(groupKey);
        }
        return members;
    }
}
