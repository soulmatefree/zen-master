package com.soulmatefree.soulmate.service;

import com.soulmatefree.soulmate.model.Users;

import java.util.List;

/**
 * @Author: baishuzi
 * @Date: 2019/3/17
 * @Description:com.soulmatefree.soulmate.service
 * @version: 1.0
 */
public interface UsersService {

    Users findUserByUsername(String username);

    List<String> findRolesByUsername(String username);

}
