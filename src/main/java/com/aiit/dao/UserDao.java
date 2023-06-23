package com.aiit.dao;

import com.aiit.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserDao {

    //根据用户名和密码获取id
    @Select("select `id` from `user` where `username` = #{username} and `password` = #{password} limit 1")
    String getUserId(String username, String password);

    //插入用户信息
    @Insert("insert into user (username,password,nickname)values(#{username},#{password},#{username})")
    int insertUserInfo(String username, String password);

    //修改用户信息, 目前还没实现，客户端页面懒得写
    @Update("")
    int updateUserInfo(User user);

    //获取用户信息
    @Select("select `id`,`nickname`,`gender`,`city`,`age`,`headPortrait`,`background` from user where id = #{id} limit 1")
    User getUserInfo(int id);

    //修改用户头像信息
    @Update("update `user` set `headPortrait` = #{headPortrait} where `id` = #{id} limit 1")
    int changUserHeadPortrait(int id, String headPortrait);

    //修改用户背景信息
    @Update("update `user` set `background` = #{background} where `id` = #{id} limit 1")
    int changUserBackground(int id, String background);

    //搜索用户(分页功能未实现)
    @Select("select * from User where username like CONCAT('%',#{search},'%') or nickname like CONCAT('%',#{search},'%') limit 20")
    List<User> searchUser(String search);
}
