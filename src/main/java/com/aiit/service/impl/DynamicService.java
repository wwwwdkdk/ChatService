package com.aiit.service.impl;

import com.aiit.config.APPConfig;
import com.aiit.dao.DynamicDao;
import com.aiit.dao.UserDao;
import com.aiit.pojo.Dynamic;
import com.aiit.pojo.Picture;
import com.aiit.pojo.User;
import com.aiit.service.IDynamicService;
import com.aiit.service.IJedisService;
import com.aiit.utils.FileUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class DynamicService implements IDynamicService {
    private IJedisService jedisService;
    private DynamicDao dynamicDao;
    private UserDao userDao;

    @Autowired
    public void setService(JedisService jedisService, DynamicDao dynamicDao,
                           UserDao userDao) {
        this.dynamicDao = dynamicDao;
        this.jedisService = jedisService;
        this.userDao = userDao;
    }

    //查询动态信息
    private void setDynamicInfo(Dynamic dynamic) {
        final Dynamic aDynamic = dynamicDao.getOneDynamic(dynamic.getId());
        BeanUtils.copyProperties(aDynamic, dynamic);
    }

    //查询动态中的用户信息
    private void setDynamicUserInfo(Dynamic dynamic) {
        final User user = userDao.getUserInfo(dynamic.getUserId());
        dynamic.setUser(user);
    }

    //查询动态中的图片信息
    private void setDynamicPicture(Dynamic dynamic) {
        final List<Picture> pictureList = dynamicDao.getDynamicPicture(dynamic.getId());
        dynamic.setPicture(pictureList);
    }

    //查询动态中的点赞信息
    private void setDynamicAgree(Dynamic dynamic, int userId) {
        final Boolean isAgreeDynamic = dynamicDao.isUserAgreeDynamic(userId, dynamic.getId()) != null;
        dynamic.setAgree(isAgreeDynamic);
    }

    //查询动态中的收藏信息
    private void setDynamicCollection(Dynamic dynamic, int userId) {
        final Boolean isCollectionDynamic = dynamicDao.isUserCollectionDynamic(userId, dynamic.getId()) != null;
        dynamic.setCollection(isCollectionDynamic);
    }


    @Override
    public List<Dynamic> getDynamic(int userId, int page) {
        final int limitBegin = (page - 1) * APPConfig.limitCount;
        final List<Dynamic> dynamicList = dynamicDao.getDynamic(userId, limitBegin, APPConfig.limitCount);
        for (Dynamic dynamic : dynamicList) {
            setDynamicUserInfo(dynamic);
            setDynamicPicture(dynamic);
            setDynamicAgree(dynamic, userId);
            setDynamicCollection(dynamic, userId);
        }

        return dynamicList;
    }

    @Override
    public List<Dynamic> getFriendDynamic(int userId, int page) {
        final int limitBegin = (page - 1) * APPConfig.limitCount;
        final List<Dynamic> dynamicList = dynamicDao.getFriendDynamic(userId, limitBegin, APPConfig.limitCount);
        for (Dynamic dynamic : dynamicList) {
            setDynamicUserInfo(dynamic);
            setDynamicPicture(dynamic);
            setDynamicAgree(dynamic, userId);
            setDynamicCollection(dynamic, userId);
        }
        return dynamicList;
    }

    @Override
    public String agreeDynamic(int userId, int dynamicId) {
        //查询是否点赞
        final String state = dynamicDao.getAgreeState(dynamicId);

        boolean result = false;
        if (state == null) {
            final boolean deleteResult = dynamicDao.insertDynamicAgree(userId, dynamicId) > 0;
            final boolean subResult = dynamicDao.addDynamicAgree(dynamicId) > 0;
            result = subResult && deleteResult;
        } else if (state.equals("1")) {
            final boolean insertResult = dynamicDao.deleteDynamicAgree(userId, dynamicId) > 0;
            final boolean addResult = dynamicDao.subDynamicAgree(dynamicId) > 0;
            result = addResult && insertResult;
        } else if (state.equals("0")) {
            final boolean updateResult = dynamicDao.updateDynamicAgree(userId, dynamicId) > 0;
            final boolean subResult = dynamicDao.addDynamicAgree(dynamicId) > 0;
            result = subResult && updateResult;
        }
        if (result) {
            return dynamicDao.getOneDynamicAgreeCount(dynamicId).getAgreeCount();
        }
        return null;
    }

    @Override
    public String collectionDynamic(int userId, int dynamicId) {
        //查询是否收藏
        boolean result = false;
        final String state = dynamicDao.getCollectionState(dynamicId);
        if (state == null) {
            final boolean insertResult = dynamicDao.insertDynamicCollection(userId, dynamicId) > 0;
            final boolean addResult = dynamicDao.addDynamicCollection(dynamicId) > 0;
            result = addResult && insertResult;
        } else if (state.equals("1")) {
            final boolean updateResult = dynamicDao.deleteDynamicCollection(userId, dynamicId) > 0;
            final boolean subResult = dynamicDao.subDynamicCollection(dynamicId) > 0;
            result = subResult && updateResult;
        } else if (state.equals("0")) {
            final boolean deleteResult = dynamicDao.updateDynamicCollection(userId, dynamicId) > 0;
            final boolean subResult = dynamicDao.addDynamicCollection(dynamicId) > 0;
            result = subResult && deleteResult;
        }
        if (result) {
            return dynamicDao.getOneDynamicCollectionCount(dynamicId).getCollectionCount();
        }
        return null;
    }

    @Override
    public boolean deleteDynamic(String token, int dynamicId) {
        final int userId = Integer.parseInt(jedisService.getUserId(token));
        return dynamicDao.deleteOneDynamic(userId, dynamicId) > 0;
    }

    @Override
    public List<Dynamic> getAgreeDynamic(int userId, int page) {
        final int limitBegin = (page - 1) * APPConfig.limitCount;
        final List<Dynamic> dynamicList = dynamicDao.getAgreeDynamic(userId, limitBegin, APPConfig.limitCount);
        for (Dynamic dynamic : dynamicList) {
            setDynamicInfo(dynamic);
            setDynamicUserInfo(dynamic);
            setDynamicPicture(dynamic);
            setDynamicCollection(dynamic, userId);
            dynamic.setAgree(true);
        }
        return dynamicList;
    }

    @Override
    public List<Dynamic> getCollectionDynamic(int userId, int page) {
        final int limitBegin = (page - 1) * APPConfig.limitCount;
        final List<Dynamic> dynamicList = dynamicDao.getCollectionDynamic(userId, limitBegin, APPConfig.limitCount);
        for (Dynamic dynamic : dynamicList) {
            setDynamicInfo(dynamic);
            setDynamicUserInfo(dynamic);
            setDynamicPicture(dynamic);
            dynamic.setCollection(true);
            setDynamicAgree(dynamic, userId);
        }
        return dynamicList;
    }

    @Override
    public boolean postDynamic(int userId, String content, MultipartFile[] file) {
        Dynamic dynamic = new Dynamic();
        dynamic.setUserId(userId);
        dynamic.setContent(content);
        if (dynamicDao.insertOneDynamic(dynamic) <= 0) {
            return false;
        }
        if (file != null) {
            for (MultipartFile multipartFile : file) {
                final String path = FileUtil.saveImage(multipartFile);
                final String httpAddress = FileUtil.filePathToHttpAddress(path);
                dynamicDao.insertDynamicPicture(dynamic.getId(), httpAddress);
            }
        }
        return true;
    }

    public boolean hasMoreDynamic(int userId, int page) {
        return page * APPConfig.limitCount < dynamicDao.getDynamicCount(userId);
    }
}
