package com.aiit.dao;

import com.aiit.pojo.FriendRequest;
import com.aiit.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface FriendDao {

    //获取一页好友id
    @Select("select `friendId` as `id` from `friend` where `userId` = #{userId} limit #{limitBegin},#{limitCount}")
    List<User> getFriendId(int userId, int limitBegin, int limitCount);

    //获取一页发出的好友请求
    @Select("select `sendId`,`state`,`receivedId` from `friendRequest` where `sendId` = #{userId}")
    List<FriendRequest> getSendFriendRequest(int userId, int limitBegin, int limitCount);

    //获取一页收到的好友请求
    @Select("select `sendId`,`state`,`receivedId` from `friendRequest` where `receivedId` = #{userId}")
    List<FriendRequest> getReceivedFriendRequest(int userId, int limitBegin, int limitCount);

    //判断两人是否是好友关系
    @Select("select 1 from `friend` where `userId` = #{userId} and `friendId` = #{friendId} limit 1")
    Integer isFriend(int userId, int friendId);

    //发送的好友申请状态
    @Select("select `state` from `friendRequest` where `receivedId` = #{friendId} and sendId = #{userId} limit 1")
    Integer requestFriendState(int userId, int friendId);

    //收到的好友请求状态
    @Select(" select `state` from `friendRequest` where `receivedId` = #{userId} and sendId = #{friendId} limit 1")
    Integer receivedFriendState(int userId, int friendId);

    //删除好友将好友表中的好友信息state设置为0
    @Update("update `friend` set `state` = 0 where `userId` = #{userId} and `friendId` = #{friendId} limit 1")
    Integer deleteFriend(int userId, int friendId);

    //向好友表中添加好友信息
    @Insert("insert into `friend` (`userId`,`friendId`) values (#{userId},#{friendId})")
    Integer insertFriend(int userId, int friendId);

    //向好友请求表中添加好友请求信息
    @Insert("insert into `friendRequest` (`sendId`,`receivedId`) values (#{userId},#{friendId})")
    Integer insertFriendRequest(int userId, int friendId);

    //拒绝好友请求，将好友请求表中的state设置为0
    @Update("update `friendRequest` set `state` = 0 where sendId = #{sendId} and receivedId = #{userId} limit 1")
    Integer refuseFriendRequest(int userId, int sendId);

    //同意好友请求,将好友请求表中的state设置为2
    @Update("update `friendRequest` set `state` = 2 where sendId = #{sendId} and receivedId = #{userId} limit 1")
    Integer agreeFriendRequest(int userId, int sendId);


}
