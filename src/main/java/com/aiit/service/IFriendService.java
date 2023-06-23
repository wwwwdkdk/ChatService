package com.aiit.service;

import com.aiit.pojo.FriendRequest;
import com.aiit.pojo.User;

import java.util.List;

public interface IFriendService {
    //获取一页好友信息
    List<User> getFriendInfo(int userId, int page);

    //获取一页发出的好友申请信息
    List<FriendRequest> getSendFriendRequest(int userId, int page);

    //获取一页收到的好友申请信息
    List<FriendRequest> getReceivedFriendRequest(int userId, int page);

    //同意好友申请
    boolean agreeFriendRequest(int userId, int sendId);

    //拒绝好友申请
    boolean refuseFriendRequest(int userId, int sendId);

    boolean sendFriendRequest(int userId, int sendId);

    //判断两个用户是否是好友
    int isFriend(int userId, int friendID);


}
