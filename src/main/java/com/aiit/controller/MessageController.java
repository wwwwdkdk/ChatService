package com.aiit.controller;

import com.aiit.pojo.Message;
import com.aiit.pojo.UnreadMessage;
import com.aiit.service.IMessageService;
import com.aiit.service.impl.MessageService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/message")
public class MessageController {

    private IMessageService messageService;

    @Autowired
    public void setService(MessageService messageService) {
        this.messageService = messageService;
    }

    //获取两个用户间的未读消息
    @GetMapping("/unread")
    public String getUnreadMessage(@RequestHeader("userId") String userId, int friendId, int page) {
        Map<String, List<Message>> map = new HashMap<>();
        map.put("data", messageService.getUnreadMessage(Integer.parseInt(userId), friendId, page));
        return JSON.toJSONString(map);
    }

    //获取未读的消息列表（两个用户间未读的最新一条消息组成的列表）
    @GetMapping("/unreadList")
    public String getUnreadMessageList(@RequestHeader("userId") String userId) {
        System.out.println("unreadList调用");
        Map<String, List<UnreadMessage>> map = new HashMap<>();
        map.put("data", messageService.getUnreadMessageList(Integer.parseInt(userId)));
        return JSON.toJSONString(map);
    }

    //获取所有未读的消息
    @GetMapping("/allUnread")
    public String getAllUnreadMessage(@RequestHeader("userId") String userId) {
        Map<String, List<Message>> map = new HashMap<>();
        map.put("data", messageService.getAllUnreadMessage(Integer.parseInt(userId)));
        return JSON.toJSONString(map);
    }

    //将未发送消息标记为已发送
    @GetMapping("/delete")
    public String deleteMessage(@RequestHeader("userId") String userId) {
        Map<String, String> map = new HashMap<>();
        map.put("code", messageService.deleteMessage(Integer.parseInt(userId)) ? "200" : "-200");
        return JSON.toJSONString(map);
    }

    /* 以下是tcp连接 */
    //向指定用户发送信息
    public String sendMessage(JSONObject jsonObject, Boolean isOnLine) {
        Message message = JSONObject.toJavaObject(jsonObject, Message.class);
        final Message result = messageService.sendMessage(message, isOnLine);
        return JSON.toJSONString(result);
    }
}
