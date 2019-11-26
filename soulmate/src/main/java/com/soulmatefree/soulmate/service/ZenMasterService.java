package com.soulmatefree.soulmate.service;

import com.soulmatefree.soulmate.model.ZenMaster;

import java.util.List;
import java.util.Set;

/**
 * @Author: baishuzi
 * @Date: 2019/3/17
 * @Description:com.soulmatefree.soulmate.service
 * @version: 1.0
 */
public interface ZenMasterService {

    int deleteZenMaster(Integer id);

    int save(ZenMaster record);

    ZenMaster findZenMaster(Integer id);

    int update(ZenMaster record);

    List<ZenMaster> findAll();

    Object findAllFromRedis(String groupKey, String hashKey);

    Object reloadZenMastersRedis(String groupKey, String hashKey);


    Set<ZenMaster> findAllFromRedisByList(String groupKey);
}
