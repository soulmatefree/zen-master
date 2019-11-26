package com.soulmatefree.soulmate.mapper;

import com.soulmatefree.soulmate.model.Users;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UsersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    Users selectUserByName(@Param("username") String username);

    List<String>  selectRoleNamesByUserName(@Param("username") String username);
}