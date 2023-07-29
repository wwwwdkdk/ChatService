package com.aiit.config;

public class APPConfig {
    //资源目录地址(windows平台下需要写成"C:\\路径\\包名"这种形式)
    public static final String resPath = "C:\\Users\\wwwww\\Documents\\res\\";      //需要修改为正确的路径，会影响图片保存功能
    //资源地址头
    public static final String resHttpPath = "http://192.168.31.248:8088/res/";     //需要修改为正确的服务器地址，会影响图片显示功能
    //图片地址
    public static final String imagePath = resPath + "img/";
    //消息图片地址
    public static final String messageFilePath = resPath + "/cacheMessage";
    //本地地址头
    public static final String resLocalHttpPath = "http://127.0.0.1:8088/res/";

    //一次在数据库获取多少条数据
    public static final int limitCount = 5;
}



