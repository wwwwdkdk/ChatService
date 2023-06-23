package com.aiit.controller;

import com.aiit.pojo.User;
import com.aiit.pojo.resp.BaseResp;
import com.aiit.config.CodeConfig;
import com.aiit.pojo.resp.LoginResp;
import com.aiit.service.IJedisService;
import com.aiit.service.IUserService;
import com.aiit.service.impl.JedisService;
import com.aiit.service.impl.UserService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private IUserService userService;
    private IJedisService jedisService;

    @Autowired
    public void setUserService(UserService userService, JedisService jedisService) {
        this.userService = userService;
        this.jedisService = jedisService;
    }

    //用户登录
    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        String token = userService.checkLogin(username, password);
        String id = jedisService.getUserId(token);
        LoginResp loginResp = new LoginResp("",token,token != null,id);
        BaseResp<LoginResp> baseResp = new BaseResp<>(loginResp, CodeConfig.getCode(token));
        return baseResp.toJson();
    }

    //用户注册
    @PostMapping("/register")
    public String register(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (!userService.userRegister(username, password)){
           return new HashMap<String, String>().put("code","-200");
        }
        return login(username,password);
    }

    //获取用户信息
    @GetMapping("/info")
    public String info(@RequestHeader("userId") String userId) {
        return JSON.toJSONString(userService.getUserInfo(userId));
    }

    //修改用户头像
    @PostMapping("/headPortrait")
    public String changeHeadPortrait(@RequestHeader("userId") String userId,
                                    @RequestParam(value = "file") MultipartFile file) {
        final String headPortrait = userService.changeHeadPortrait(Integer.parseInt(userId), file);
        Map<String, String> map = new HashMap<>();
        map.put("code", headPortrait != null ? "200" : "-200");
        map.put("background", headPortrait);
        return JSON.toJSONString(map);
    }

    //修改用户背景
    @PostMapping("/background")
    public String changeBackground(@RequestHeader("userId") String userId,
                                  @RequestParam(value = "imageFile") MultipartFile file) {
        final String background = userService.changeBackground(Integer.parseInt(userId), file);
        Map<String, String> map = new HashMap<>();
        map.put("code", background != null ? "200" : "-200");
        map.put("background", background);
        return JSON.toJSONString(map);
    }

    //模糊搜索用户
    @GetMapping("/search")
    public String searchUser(String search) {
        Map<String, List<User>> map = new HashMap<>();
        map.put("data", userService.searchUser(search));
        return JSON.toJSONString(map);
    }

    //退出登录(未实现)
    @GetMapping("/logout")
    public String logout(){
        Map<String, List<User>> map = new HashMap<>();
        return JSON.toJSONString(map);
    }
}
