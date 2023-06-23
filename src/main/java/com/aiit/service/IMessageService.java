package com.aiit.service;


import com.aiit.pojo.Message;
import com.aiit.pojo.UnreadMessage;

import java.util.List;

public interface IMessageService {
    // 将消息转发给用户
    Message sendMessage(Message message, boolean isOnline);

    //获取与某个用户的未读消息
    List<Message> getUnreadMessage(int userId, int friendId, int page);

    //获取未读消息列表
    List<UnreadMessage> getUnreadMessageList(int userId);

    //获取所有未读消息
    List<Message> getAllUnreadMessage(int userId);

    //删除数据库中消息（将未读消息发送给用户后，服务器不再保存用户消息）
    Boolean deleteMessage(int userId);
}
