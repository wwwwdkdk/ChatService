package com.aiit.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

//消息
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @javax.persistence.Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String  content;         //收到的消息内容
    private String  time;            //发送时间
    private Integer sendId;          //发送人
    private Integer receivedId;      //接受人
    private Integer type;            //消息类型
    private Boolean state;           //状态
    private String  suffix;          //我忘记了这个字段当时是干啥的了
}
