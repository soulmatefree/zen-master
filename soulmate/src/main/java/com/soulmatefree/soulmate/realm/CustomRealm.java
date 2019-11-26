package com.soulmatefree.soulmate.realm;

import com.soulmatefree.soulmate.model.Users;
import com.soulmatefree.soulmate.service.UsersService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UsersService usersService;

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 1.从主体传过来的认证信息中，获取用户名
        String username = (String) authenticationToken.getPrincipal();

        // 2.通过用户名去到数据库中获取凭证
        String password = getPasswordByUsername(username);
        if(password == null) {
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, password, "customRealm");
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("baishuzi"));//设置盐salt

        return authenticationInfo;
    }


    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();

        // 从数据库中根据用户名获取角色数据
        Set<String> roles = getRolesByUsername(username);
        // 从数据库中根据用户名获取权限数据
        Set<String> permissions = getPermissionbyUsername(username);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissions);
        simpleAuthorizationInfo.setRoles(roles);
        return simpleAuthorizationInfo;
    }

    /**
     * 模拟用户权限信息
     * @param username
     * @return
     */
    private Set<String> getPermissionbyUsername(String username) {
        Set<String> sets = new HashSet<String>();
        if (StringUtils.isEmpty(username)){
            return sets;
        }
        List<String> roles = usersService.findRolesByUsername(username);
        Optional.ofNullable(roles)
                .ifPresent(e->{
                    sets.addAll(e);
                });
        return sets;
    }

    /**
     * 模拟根据用户名获取数据库中的角色数据
     * @param username
     * @return
     */
    private Set<String> getRolesByUsername(String username) {
        Set<String> sets = new HashSet<String>();
        sets.add("admin");
        sets.add("user");
        return sets;
    }


    /**
     * 模拟数据库查询凭证
     * @param username
     * @return
     */
    private String getPasswordByUsername(String username) {

        if (StringUtils.isEmpty(username)){
            return null;
        }

        Users user = usersService.findUserByUsername(username);
        return Optional.ofNullable(user)
                .map(Users::getPassword)
                .orElse(null);
    }
}
