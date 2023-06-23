package com.aiit.pojo;

import com.aiit.utils.FileUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;

//图片，写一半想起的功能，客户端未完全实现
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Picture {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private Integer width;
    private Integer height;
    private String  picture;

    public String getPicture() {
        return FileUtil.localResToHttpRes(picture);
    }
}