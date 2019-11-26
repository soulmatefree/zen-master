package com.soulmatefree.soulmate.mapper;

import com.soulmatefree.soulmate.model.ZenMaster;
import com.soulmatefree.soulmate.model.vo.Dictionary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZenMasterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZenMaster record);

    int insertSelective(ZenMaster record);

    ZenMaster selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZenMaster record);

    int updateByPrimaryKey(ZenMaster record);

    List<ZenMaster> selectZenMasterList();

    List<Dictionary> selectToolDictionary();
}