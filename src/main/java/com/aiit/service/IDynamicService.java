package com.aiit.service;

import com.aiit.pojo.Dynamic;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IDynamicService {
    //获取一页当前用户的动态
    List<Dynamic> getDynamic(int userId, int page);

    //获取一页好友的动态
    List<Dynamic> getFriendDynamic(int userId, int page);

    //获取一页点赞过的动态
    List<Dynamic> getAgreeDynamic(int userId, int page);

    //获取一页收藏过的动态
    List<Dynamic> getCollectionDynamic(int userId, int page);

    //点赞动态或取消点赞动态
    String agreeDynamic(int userId, int dynamicId);

    //收藏动态或取消收藏动态
    String collectionDynamic(int userId, int dynamicId);

    //删除动态
    boolean deleteDynamic(String token, int dynamicId);

    //发表动态
    boolean postDynamic(int userId, String content, MultipartFile[] file);

   //判断是否还有动态
    boolean hasMoreDynamic(int userId, int page);
}
