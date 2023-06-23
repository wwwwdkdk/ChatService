package com.aiit.pojo;

import com.aiit.utils.FileUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;                 //用户id
    private String  username;           //用户名
    private String  nickname;           //昵称
    private Integer gender;             //性别
    private String  city;               //城市
    private String  age;                //年龄
    private String  headPortrait;       //头像
    private String  background;         //背景

    public String getHeadPortrait() {
        return FileUtil.localResToHttpRes(headPortrait);
    }

    public String getBackground() {
        return FileUtil.localResToHttpRes(background);
    }
}