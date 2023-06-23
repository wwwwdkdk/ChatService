package com.aiit.service.impl;

import com.aiit.config.APPConfig;
import com.aiit.dao.FriendDao;
import com.aiit.dao.UserDao;
import com.aiit.pojo.FriendRequest;
import com.aiit.pojo.User;
import com.aiit.service.IFriendService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService implements IFriendService {

    private static class FriendType {
        private static final Integer friend = 1;        //已经是好友
        private static final Integer isNotFriend = 2;   //不是好友，也没有好友请求
        private static final Integer refuse = 3;        //好友请求被拒绝
        private static final Integer wait = 4;          //等待验证
        private static final Integer isRefused = 5;     //拒绝了对方的好友申请
        private static final Integer waitDeal = 6;      //未处理对方的好友申请
    }

    private FriendDao friendDao;
    private UserDao userDao;

    @Autowired
    public void setService(FriendDao friendDao, UserDao userDao) {
        this.friendDao = friendDao;
        this.userDao = userDao;
    }

    //查询好友列表中的用户信息
    private void setFriendUserInfo(List<User> friendList) {
        for (User user : friendList) {
            final User userInfo = userDao.getUserInfo(user.getId());
            BeanUtils.copyProperties(userInfo, user);
        }
    }

    @Override
    public List<User> getFriendInfo(int userId, int page) {
        final int limitBegin = (page - 1) * APPConfig.limitCount;
        System.out.println(userId);
        System.out.println(limitBegin);
        final List<User> friendList = friendDao.getFriendId(userId, limitBegin, APPConfig.limitCount);
        setFriendUserInfo(friendList);
        System.out.println(friendList);
        return friendList;
    }

    @Override
    public List<FriendRequest> getSendFriendRequest(int userId, int page) {
        final int limitBegin = (page - 1) * APPConfig.limitCount;
        final List<FriendRequest> friendList = friendDao.getSendFriendRequest(userId, limitBegin, APPConfig.limitCount);
        for (FriendRequest request : friendList) {
            final User userInfo = userDao.getUserInfo(request.getReceivedId());
            request.setUser(userInfo);
        }
        return friendList;
    }

    @Override
    public List<FriendRequest> getReceivedFriendRequest(int userId, int page) {
        final int limitBegin = (page - 1) * APPConfig.limitCount;
        final List<FriendRequest> friendList = friendDao.getReceivedFriendRequest(userId, limitBegin, APPConfig.limitCount);
        for (FriendRequest request : friendList) {
            final User userInfo = userDao.getUserInfo(request.getSendId());
            request.setUser(userInfo);
        }
        return friendList;
    }

    @Override
    public boolean agreeFriendRequest(int userId, int sendId) {
        if (friendDao.agreeFriendRequest(userId, sendId) > 0) {
            return friendDao.insertFriend(userId, sendId) > 0;
        }
        return false;
    }

    @Override
    public boolean refuseFriendRequest(int userId, int sendId) {
        return friendDao.refuseFriendRequest(userId, sendId) > 0;
    }

    @Override
    public boolean sendFriendRequest(int userId, int sendId) {
        return friendDao.insertFriendRequest(userId, sendId) > 0;
    }

    @Override
    public int isFriend(int userId, int friendId) {
        if (friendDao.isFriend(userId, friendId) != null) {
            return FriendType.friend;
        }

        Integer state = friendDao.receivedFriendState(userId, friendId);
        state = state != null ? state : -1;
        if (state == 0) {
            return FriendType.isRefused;
        } else if (state == 1) {
            return FriendType.waitDeal;
        }

        state = friendDao.requestFriendState(userId, friendId);
        state = state != null ? state : -1;
        if (state == 0) {
            return FriendType.refuse;
        } else if (state == 1) {
            return FriendType.wait;
        }
        return FriendType.isNotFriend;
    }
}
