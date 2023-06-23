package com.aiit.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;

//好友请求
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequest {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private Integer sendId;
    private Integer receivedId;
    private String state;
    private User user;
}

