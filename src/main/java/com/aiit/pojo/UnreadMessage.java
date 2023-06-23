package com.aiit.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;

//未读消息
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnreadMessage {
    @Id
    @KeySql(useGeneratedKeys = true)
    private int id;
    private Message message;
    private String unreadCount;
}
