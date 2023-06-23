package com.aiit.controller;

import com.aiit.config.CodeConfig;
import com.aiit.pojo.FriendRequest;
import com.aiit.pojo.User;
import com.aiit.pojo.resp.BaseResp;
import com.aiit.pojo.resp.DynamicResp;
import com.aiit.service.IFriendService;
import com.aiit.service.impl.FriendService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/friend")
public class FriendController {

    private IFriendService friendService;

    @Autowired
    public void setFriendService(FriendService friendService) {
        this.friendService = friendService;
    }

    //获取一页好友信息
    @GetMapping("")
    public String getFriendInfo(@RequestHeader("userId") String userId, String page) {
        int aPage = page == null ? 1 : Integer.parseInt(page);
        Map<String, List<User>> map = new HashMap<>();
        map.put("data", friendService.getFriendInfo(Integer.parseInt(userId), aPage));
        return JSON.toJSONString(map);
    }

    //获取一页发出的好友申请信息
    @GetMapping("/send")
    public String getSendFriendRequest(@RequestHeader("userId") String userId, String page) {
        int aPage = page == null ? 1 : Integer.parseInt(page);
        Map<String, List<FriendRequest>> map = new HashMap<>();
        map.put("data", friendService.getSendFriendRequest(Integer.parseInt(userId), aPage));
        return JSON.toJSONString(map);
    }

    //获取一页收到的好友申请信息
    @GetMapping("/receive")
    public String getReceivedFriendRequest(@RequestHeader("userId") String userId, String page) {
        int aPage = page == null ? 1 : Integer.parseInt(page);
        Map<String, List<FriendRequest>> map = new HashMap<>();
        map.put("data", friendService.getReceivedFriendRequest(Integer.parseInt(userId), aPage));
        return JSON.toJSONString(map);
    }

    //发出好友申请
    @GetMapping("/request")
    public String sendFriendRequest(@RequestHeader("userId") String userId, int friendId) {
        Map<String, Boolean> map = new HashMap<>();
        map.put("ok", friendService.sendFriendRequest(Integer.parseInt(userId),friendId));
        return JSON.toJSONString(map);

    }

    //拒绝好友申请
    @GetMapping("/refuse")
    public String refuseFriendRequest(@RequestHeader("userId") String userId, int sendId) {
        Map<String, Boolean> map = new HashMap<>();
        map.put("ok", friendService.refuseFriendRequest(Integer.parseInt(userId), sendId));
        return JSON.toJSONString(map);
    }

    //同意好友申请
    @GetMapping("/agree")
    public String agreeFriendRequest(@RequestHeader("userId") String userId, int sendId) {
        Map<String, Boolean> map = new HashMap<>();
        map.put("ok", friendService.agreeFriendRequest(Integer.parseInt(userId), sendId));
        return JSON.toJSONString(map);
    }

    //判断用户间的状态
    @GetMapping("/friendState")
    public String friendState(@RequestHeader("userId") String userId, int friendId) {

        Map<String, Integer> map = new HashMap<>();
        map.put("ok", friendService.isFriend(Integer.parseInt(userId), friendId));
        return JSON.toJSONString(map);
    }
}
