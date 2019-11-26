package com.soulmatefree.soulmate.service.Impl;

import com.soulmatefree.soulmate.mapper.UsersMapper;
import com.soulmatefree.soulmate.model.Users;
import com.soulmatefree.soulmate.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: baishuzi
 * @Date: 2019/3/17
 * @Description:com.soulmatefree.soulmate.service.Impl
 * @version: 1.0
 */
@Service("usersService")
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersMapper mapper ;

    @Override
    public Users findUserByUsername(String username) {
        return mapper.selectUserByName(username);
    }

    @Override
    public List<String> findRolesByUsername(String username) {
        return mapper.selectRoleNamesByUserName(username);
    }
}
