package com.aiit.dao;

import com.aiit.pojo.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MessageDao {
    //获取和某个用户的未读消息
    @Select("select * from `message` where `state` = 1 and `sendId` = #{sendId} and `receivedId` = #{receivedId} order by `time` desc ")
    List<Message> getUnreadMessage(int receivedId, int sendId);

    //获取所有未读消息
    @Select("select * from `message` where `state` = 1 and `receivedId` = #{receivedId} ")
    List<Message> getAllUnreadMessage(int receivedId);

    //获取和某个用户最新的一条未读消息
    @Select("select * from `message` where `state` = 1 and `sendId` = #{sendId} and `receivedId` = #{receivedId} order by `time` desc limit 1")
    Message getOneUnreadMessage(int receivedId, int sendId);

    //获取未读消息数量
    @Select("select count(*) from message where `state` = 1 and  `sendId` = #{sendId} and `receivedId` = #{receivedId}")
    String getUnreadMessageCount(int receivedId, int sendId);

    //添加一条未读消息
    @Insert("insert into `message`(`content`,`type`,`time`,`sendId`,`receivedId`)values(#{content},#{type},now(),#{sendId},#{receivedId})")
    int insertMessage(Message message);

    //删除未读消息
    @Update("update message set `state` = 0 where `receivedId` = #{receivedId} ")
    int deleteMessage(int receivedId);
}
