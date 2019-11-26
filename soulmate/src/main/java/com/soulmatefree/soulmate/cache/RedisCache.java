package com.soulmatefree.soulmate.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

/**
 * Created by Administrator on 2018/7/2 0002.
 */
@Component
public class RedisCache<K, V> implements Cache<K, V>{

    private final String CACHE_PREFIX = "cache:柏树子-cache";

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;


    @Override
    public V get(K k) throws CacheException {
        // 这里扩展可以加入echache二级缓存机制
        System.out.println("从redis中获取用户角色数据");
        return (V) redisTemplate.opsForHash().get(CACHE_PREFIX, k);
    }

    @Override
    public V put(K k, V v) throws CacheException {
        System.out.println("将获取用户角色数据存入到redis中");
        redisTemplate.opsForHash().put(CACHE_PREFIX,k,v);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {

        Object o = redisTemplate.opsForHash().get(CACHE_PREFIX, k);
        redisTemplate.opsForHash().delete(CACHE_PREFIX, k);
        if(o != null) {
            return (V) o;
        }
        return null;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }
}
