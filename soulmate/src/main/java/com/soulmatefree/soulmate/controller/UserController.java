package com.soulmatefree.soulmate.controller;

import com.soulmatefree.soulmate.utils.BaseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Controller
public class UserController {

    public static String WX_ACCESS_TOKEN =
            "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxa5a1319205dba819&secret=5290145f659ffa5c6cf1b04bd5609131&code=%s&grant_type=authorization_code";



    /**
     * 1.前端页面跳转到"/",判断后台session是否存在 2.true为登录状态，false为登出状态
     */
    @ApiOperation(value = "判断当前的登录状态", notes = "        ")
    @RequestMapping(value = "/validate-login-status", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<Boolean> validateLoginStatus() throws Exception {
        Boolean flag = false;
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            flag = true;
        }
        return BaseResult.successWithData(flag);
    }


    /**
     * 1.微信扫码登录后，获取到的openid 2.通过openid查询出用户的信息 3.根据用户的账户和密码获取调用登录的接口 4.注意调用cas的登录时候，密码为加密后的字符串，故其它app均需作出修改
     */
    @ApiOperation(value = "微信扫码登录", notes = "        ")
    @RequestMapping(value = "/ajax-wxLogin", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<Object> ajaxWxLogin(HttpServletRequest request, String code, String state) throws Exception {
        return BaseResult.success();
    }


    @ApiOperation(value = "退出", notes = "        ")
    @RequestMapping(value = "/ajax-logout", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<Boolean> ajaxLogout(HttpServletResponse response) throws Exception {
        Boolean flag = true;
        Subject subject = SecurityUtils.getSubject();

        subject.logout();
        return BaseResult.successWithData(flag);
    }

    /**
     * 1.登录方法 2.登录前清空当前拥有的登录信息
     */
    @ApiOperation(value = "登录", notes = "        ")
    @ApiImplicitParams({@ApiImplicitParam(dataType = "String", name = "loginId", value = "用户账号", required = true),
            @ApiImplicitParam(dataType = "String", name = "password", value = "用户账号密码", required = true),
            @ApiImplicitParam(dataType = "String", name = "type", value = "登录的类型 wx：微信登录", required = true)})
    @GetMapping(value = {"/ajax-login", "/ajaxLogin"})
    @ResponseBody
    public BaseResult<Object> ajaxZhizhuLogin(String loginId, String password, String type, String openId) {
        return  ajaxLogin(loginId,password,type,openId);
    }

    /**
     * 1.登录方法 2.登录前清空当前拥有的登录信息
     */
    @ApiOperation(value = "登录", notes = "        ")
    @ApiImplicitParams({@ApiImplicitParam(dataType = "String", name = "loginId", value = "用户账号", required = true),
            @ApiImplicitParam(dataType = "String", name = "password", value = "用户账号密码", required = true),
            @ApiImplicitParam(dataType = "String", name = "type", value = "登录的类型 wx：微信登录", required = true)})
    @RequestMapping(value = {"/ajax-login", "/ajaxLogin"}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<Object> ajaxLogin(String loginId, String password, String type, String openId) {
        UsernamePasswordToken token = new UsernamePasswordToken(loginId, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            System.out.println(subject.isAuthenticated());
            System.out.println(subject.getSession().getId());
            return BaseResult.success();
        } catch (UnknownAccountException e) {
            return BaseResult.failWithCodeAndMsg(502,e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return BaseResult.failWithCodeAndMsg(502,e.getMessage());
        } catch (LockedAccountException e) {
            return BaseResult.failWithCodeAndMsg(502,e.getMessage());
        } catch (AuthenticationException e) {
            return BaseResult.failWithCodeAndMsg(502,e.getMessage());
        }
    }

    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<String>() {{
            add("1");
            add("2");
            add("3");
            add("4");
        }};

        System.out.println("strings = " + test(strings));;
    }

    private static String test(ArrayList<String> strings) {
        for (String id:strings){
            if (id.equalsIgnoreCase("2")){
                return id;
            }
            System.out.println("id = " + id);
        }
        return "none";
    }
}
