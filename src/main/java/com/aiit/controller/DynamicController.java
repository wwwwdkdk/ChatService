package com.aiit.controller;

import com.aiit.pojo.Dynamic;
import com.aiit.pojo.resp.BaseResp;
import com.aiit.config.CodeConfig;
import com.aiit.pojo.resp.DynamicResp;
import com.aiit.service.IDynamicService;
import com.aiit.service.impl.DynamicService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dynamic")
public class DynamicController {

    private IDynamicService dynamicService;

    @Autowired
    public void setDynamicService(DynamicService dynamicService) {
        this.dynamicService = dynamicService;
    }

    //获取一页动态
    @GetMapping("")
    public String getMyDynamic(int userId, String page) {
        int aPage = page == null ? 1 : Integer.parseInt(page);
        DynamicResp dynamicResp = new DynamicResp(dynamicService.hasMoreDynamic(userId,aPage), dynamicService.getDynamic(userId,aPage));
        BaseResp<DynamicResp> resp = new BaseResp<>(dynamicResp, CodeConfig.SUCCESS);
        return resp.toJson();
    }

    @GetMapping("/friends")
    public String getFriendsDynamic(int userId, String page) {
        int aPage = page == null ? 1 : Integer.parseInt(page);
        DynamicResp dynamicResp = new DynamicResp(dynamicService.hasMoreDynamic(userId,aPage), dynamicService.getFriendDynamic(userId,aPage));
        BaseResp<DynamicResp> resp = new BaseResp<>(dynamicResp, CodeConfig.SUCCESS);
        return resp.toJson();
    }

    //获取一页点赞过的动态
    @GetMapping("/myAgree")
    public String getMyAgreeDynamic(@RequestHeader("userId") int userId, String page) {
        int aPage = page == null ? 1 : Integer.parseInt(page);
        DynamicResp dynamicResp = new DynamicResp(dynamicService.hasMoreDynamic(userId,aPage), dynamicService.getAgreeDynamic(userId,aPage));
        BaseResp<DynamicResp> resp = new BaseResp<>(dynamicResp, CodeConfig.SUCCESS);
        return resp.toJson();
    }

    //获取一页收藏过的动态
    @GetMapping("/myCollection")
    public String getMyCollectionDynamic(@RequestHeader("userId") int userId, String page) {
        int aPage = page == null ? 1 : Integer.parseInt(page);
        DynamicResp dynamicResp = new DynamicResp(dynamicService.hasMoreDynamic(userId,aPage), dynamicService.getCollectionDynamic(userId,aPage));
        BaseResp<DynamicResp> resp = new BaseResp<>(dynamicResp, CodeConfig.SUCCESS);
        return resp.toJson();
    }

    //点赞或取消点赞
    @GetMapping("/agree")
    public String agreeDynamic(@RequestHeader("userId") int userId, int dynamicId) {
        Map<String, String> map = new HashMap<>();
        String agreeCount = dynamicService.agreeDynamic(userId, dynamicId);
        map.put("code", CodeConfig.getCode(agreeCount));
        map.put("agreeCount", agreeCount);
        return JSON.toJSONString(map);
    }

    //收藏或取消收藏
    @GetMapping("/collection")
    public String collectionDynamic(@RequestHeader("userId") int userId, int dynamicId) {
        Map<String, String> map = new HashMap<>();
        String collectionCount = dynamicService.collectionDynamic(userId, dynamicId);
        map.put("code", CodeConfig.getCode(collectionCount));
        map.put("collectionCount", collectionCount);
        return JSON.toJSONString(map);
    }

    //删除动态
    @GetMapping("/delete")
    public String deleteDynamic(@RequestHeader("userId") String userId, int dynamicId) {
        Map<String, String> map = new HashMap<>();
        map.put("code", dynamicService.deleteDynamic(userId, dynamicId) ? "200" : "-200");
        return JSON.toJSONString(map);
    }

    //发表动态
    @PostMapping("/post")
    public String postDynamic(@RequestHeader("userId") int userId,
                              @RequestParam(value = "content") String content,
                              @RequestParam(value = "imageFile") MultipartFile[] file) {
        Map<String, String> map = new HashMap<>();
        map.put("code", dynamicService.postDynamic(userId, content, file) ? "200" : "-200");
        return JSON.toJSONString(map);
    }

}
