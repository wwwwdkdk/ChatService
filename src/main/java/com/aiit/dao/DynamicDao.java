package com.aiit.dao;

import com.aiit.pojo.Dynamic;
import com.aiit.pojo.Picture;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface DynamicDao {

    //获取指定用户的一页动态
    @Select("select * from `dynamic` where `userId` = #{userId} and `state` = 1  order by time desc limit #{limitBegin},#{limitCount}")
    List<Dynamic> getDynamic(int userId, int limitBegin, int limitCount);

    //获取指定用户的动态数量
    @Select("select count(*) from `dynamic` where `userId` = #{userId} and `state` = 1 limit 1")
    int getDynamicCount(int userId);

    //获取好友动态
    @Select("select * from `dynamic` where `userId` in (select `friendId` from `friend` where `userId` = #{userId} and `state` = 1)" +
            " and `state` = 1  order by time desc limit #{limitBegin},#{limitCount}")
    List<Dynamic> getFriendDynamic(int userId, int limitBegin, int limitCount);

    //获取好友的动态数量
    @Select("select count(*) from `dynamic` where `userId` in (select `friendId` from `friend` where `userId` = #{userId} and `state` = 1) and `state` = 1  limit 1")
    int getFriendDynamicCount(int userId);

    //获取一条动态
    @Select("select * from `dynamic` where `id` = #{id} limit 1")
    Dynamic getOneDynamic(int id);

    //获取一条动态的点赞数量
    @Select("select `agreeCount` from `dynamic` where `id` = #{id} limit 1")
    Dynamic getOneDynamicAgreeCount(int id);

    //获取一条动态的收藏数量
    @Select("select `collectionCount` from `dynamic` where `id` = #{id} limit 1")
    Dynamic getOneDynamicCollectionCount(int id);

    //获取一页点赞过的动态
    @Select("select `dynamicId` as `id`,userId from agree where `userId` = #{userId} and `state` = 1 order by `id` desc limit #{limitBegin},#{limitCount}")
    List<Dynamic> getAgreeDynamic(int userId, int limitBegin, int limitCount);

    //获取一页收藏过的动态
    @Select("select `dynamicId` as `id`,userId from collection where `userId` = #{userId} and `state` = 1 order by `id` desc limit #{limitBegin},#{limitCount}")
    List<Dynamic> getCollectionDynamic(int userId, int limitBegin, int limitCount);

    //判断某条动态是否属于当前用户
    @Select("select 1 from dynamic where userId = #{userId} and id = #{dynamicId} limit 1")
    String isUserDynamic(int userId, int dynamicId);

    //判断某条动态是否被当前用户收藏
    @Select("select 1 from `collection` where `userId` = #{userId} and`dynamicId` = #{dynamicId} and `state` = 1 limit 1")
    String isUserCollectionDynamic(int userId, int dynamicId);

    //判断某条动态是否被当前用户点赞
    @Select("select 1 from `agree` where `userId` = #{userId} and `dynamicId` = #{dynamicId} and `state` = 1 limit 1")
    String isUserAgreeDynamic(int userId, int dynamicId);

    //获取某条收藏的状态
    @Select("select `state` from `collection` where `dynamicId` = #{dynamicId}  limit 1")
    String getCollectionState(int dynamicId);

    //获取某条点赞的状态
    @Select("select `state` from `agree` where `dynamicId` = #{dynamicId} limit 1")
    String getAgreeState(int dynamicId);

    //添加一条动态
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into dynamic(userId,content,time)values(#{userId},#{content},now())")
    int insertOneDynamic(Dynamic dynamic);

    //添加一条动态的图片
    @Insert("insert into picture(dynamicId,picture)values(#{dynamicId},#{picture})")
    int insertDynamicPicture(int dynamicId, String picture);

    //删除一条动态
    @Update("update dynamic set state = 0 where id = #{dynamicId} and userId = #{userId}")
    int deleteOneDynamic(int userId, int dynamicId);

    //获取一条动态的图片
    @Select("select `picture`,`id`,`width`,`height` from `picture` where `dynamicId` = #{dynamicId}")
    List<Picture> getDynamicPicture(int dynamicId);

    //给一条动态的点赞数加一
    @Update("update `dynamic` set `agreeCount` = `agreeCount` + 1 where `id` = #{id} limit 1 ")
    int addDynamicAgree(int id);

    //给一条动态的点赞数减一
    @Update("update `dynamic` set `agreeCount` = `agreeCount` - 1 where `id` = #{id} limit 1")
    int subDynamicAgree(int id);

    //给一条动态的收藏数加一
    @Update("update `dynamic` set `collectionCount` = `collectionCount` + 1 where `id` = #{id} limit 1 ")
    int addDynamicCollection(int id);

    //给一条动态的收藏数减一
    @Update("update `dynamic` set `collectionCount` = `collectionCount` - 1 where `id` = #{id} limit 1 ")
    int subDynamicCollection(int id);

    //将取消点赞的帖子设置为点赞
    @Update("update `agree` set `state` = 1 where `dynamicId` = #{dynamicId} and userId = #{userId} ")
    int updateDynamicAgree(int userId, int dynamicId);

    //新增一条点赞信息
    @Insert("insert into `agree`(`dynamicId`,`userId`) values (#{dynamicId},#{userId}) ")
    int insertDynamicAgree(int userId, int dynamicId);

    //取消点赞
    @Update("update `agree` set `state` = 0 where `dynamicId` = #{dynamicId} and `userId` = #{userId} ")
    int deleteDynamicAgree(int userId, int dynamicId);

    //新增一条收藏信息
    @Insert("insert into `collection`(`dynamicId`,`userId`) values (#{dynamicId},#{userId}) ")
    int insertDynamicCollection(int userId, int dynamicId);

    //将取消收藏的帖子设置为点赞
    @Update("update `collection` set `state` = 1 where `dynamicId` = #{dynamicId} and userId = #{userId} ")
    int updateDynamicCollection(int userId, int dynamicId);

    //删除一条收藏信息
    @Update("update `collection` set `state` = 0 where `dynamicId` = #{dynamicId} and `userId` = #{userId}")
    int deleteDynamicCollection(int userId, int dynamicId);

}
