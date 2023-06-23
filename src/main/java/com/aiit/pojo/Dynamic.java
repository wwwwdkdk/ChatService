package com.aiit.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.util.List;

//动态
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dynamic {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer       id;                 //帖子id
    private Integer       userId;             //发表者id
    private String        time;               //发表时间
    private String        agreeCount;         //点赞数
    private String        collectionCount;    //收藏数
    private Boolean       agree;              //当前用户是否点赞
    private Boolean       collection;         //当前用户是否收藏
    private List<Picture> picture;            //图片
    private String        content;            //文本内容
    private User          user;               //用户信息

    public String getAgreeCount() {
        String maxAgreeCount = "999+";
        if (agreeCount.equals(maxAgreeCount)) {
            return agreeCount;
        } else if (Integer.parseInt(agreeCount) > 999) {
            return maxAgreeCount;
        }
        return agreeCount;
    }

    public String getCollectionCount() {
        String maxCollectionCount = "999+";
        if (collectionCount.equals(maxCollectionCount)) {
            return agreeCount;
        } else if (Integer.parseInt(collectionCount) > 999) {
            return maxCollectionCount;
        }
        return collectionCount;
    }
}
