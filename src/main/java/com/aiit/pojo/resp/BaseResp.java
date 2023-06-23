package com.aiit.pojo.resp;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BaseResp<T> {
    private T data;         //数据
    private String code;    //状态码
    private String msg;     //错误信息

    public BaseResp(T data, String code) {
        this.data = data;
        this.code = code;
    }

    public String toJson(){
       return JSONObject.toJSONString(this);
    }
}


