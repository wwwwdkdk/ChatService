package com.aiit.service.impl;

import com.aiit.dao.FriendDao;
import com.aiit.dao.MessageDao;
import com.aiit.pojo.Message;
import com.aiit.pojo.UnreadMessage;
import com.aiit.pojo.User;
import com.aiit.service.IMessageService;
import com.aiit.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class MessageService implements IMessageService {
    private MessageDao messageDao;
    private FriendDao friendDao;

    @Autowired
    public void setService(MessageDao messageDao, FriendDao friendDao) {
        this.messageDao = messageDao;
        this.friendDao = friendDao;
    }

    @Override
    public Message sendMessage(Message message, boolean isOnline) {
        System.out.println(message);
        if (message.getType() != 0) { //对文本之外的消息进行处理
            message.setContent(saveFile(message.getContent(), message.getSuffix()));
        }
        if (isOnline) { //判断转发的用户tcp连接是否成功
            return message;
        } else { //不在线的用户就将消息保存到数据库中
            messageDao.insertMessage(message);
            return null;
        }
    }

    @Override
    public List<Message> getUnreadMessage(int userId, int friendId, int page) {
        return messageDao.getUnreadMessage(userId, friendId);
    }

    @Override
    public List<UnreadMessage> getUnreadMessageList(int userId) {
        final List<User> friendList = friendDao.getFriendId(userId, 0, 100);
        List<UnreadMessage> messageList = new ArrayList<>();
        for (User user : friendList) {
            final Message message = messageDao.getOneUnreadMessage(userId, user.getId());
            if (message != null) {
                UnreadMessage unreadMessage = new UnreadMessage();
                unreadMessage.setMessage(message);
                unreadMessage.setUnreadCount(messageDao.getUnreadMessageCount(userId, user.getId()));
                messageList.add(unreadMessage);
            }
        }
        return messageList;
    }

    @Override
    public List<Message> getAllUnreadMessage(int userId) {
        return messageDao.getAllUnreadMessage(userId);
    }

    @Override
    public Boolean deleteMessage(int userId) {
        System.out.println(userId+" ajsdfkhaskd");
        return messageDao.deleteMessage(userId) > 0;
    }

    private String saveFile(String file, String suffix) {
        final Base64.Decoder decoder = Base64.getMimeDecoder();
        final byte[] content = decoder.decode(file);
        final String path = FileUtil.saveFile(content, suffix);
        return FileUtil.filePathToHttpAddress(path);
    }


}
