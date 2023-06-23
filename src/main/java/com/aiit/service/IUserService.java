package com.aiit.service;

import com.aiit.pojo.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUserService {

    //检查用户登录信息是否正确
    String checkLogin(String username, String password);

    //用户注册
    Boolean userRegister(String username, String password);

    //获取用户信息
    User getUserInfo(String id);

    //修改用户头像
    String changeHeadPortrait(int userId, MultipartFile file);

    //修改用户背景
    String changeBackground(int userId, MultipartFile file);

    //模糊搜索用户
    List<User> searchUser(String search);

}
