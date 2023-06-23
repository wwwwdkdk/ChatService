package com.aiit.service.impl;

import com.aiit.dao.UserDao;
import com.aiit.pojo.User;
import com.aiit.service.IJedisService;
import com.aiit.service.IUserService;
import com.aiit.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    private UserDao userDao;
    private IJedisService jedisService;

    @Autowired
    public void setService(UserDao userDao, JedisService jedisService) {
        this.userDao = userDao;
        this.jedisService = jedisService;
    }

    @Override
    public String checkLogin(String username, String password) {
        String id = userDao.getUserId(username, password);
        if (id != null) {
            String token = String.format("%s%d", UUID.randomUUID(), new Date().getTime());
            jedisService.setToken(token, id);
            return token;
        } else {
            return null;
        }
    }

    @Override
    public Boolean userRegister(String username, String password) {
        return userDao.insertUserInfo(username,password) > 0;
    }

    @Override
    public User getUserInfo(String id) {
        return userDao.getUserInfo(Integer.parseInt(id));
    }

    @Override
    public String changeHeadPortrait(int userId, MultipartFile file) {
        final String filepath = FileUtil.saveImage(file);
        final String httpAddress = FileUtil.filePathToHttpAddress(filepath);
        if (filepath != null) {
            if (userDao.changUserHeadPortrait(userId, httpAddress) > 0) {
                return userDao.getUserInfo(userId).getHeadPortrait();
            }
        }
        return null;
    }

    @Override
    public String changeBackground(int userId, MultipartFile file) {
        final String filepath = FileUtil.saveImage(file);
        final String httpAddress = FileUtil.filePathToHttpAddress(filepath);
        if (filepath != null) {
            if (userDao.changUserBackground(userId, httpAddress) > 0) {
                return userDao.getUserInfo(userId).getBackground();
            }
        }
        return null;
    }

    @Override
    public List<User> searchUser(String search) {
        if (search.isEmpty()) {
            return null;
        }
        return userDao.searchUser(search);
    }
}
