# ChatService
这是一个使用Springboot+mysql+netty+redis实现的Java后端程序（聊天APP后端），

实现了消息列表，好友聊天，好友动态，好友列表等功能  

大学时编写的学习项目
## 聊天APP iOS端地址：https://github.com/wwwwdkdk/Sparrow 
 
## 如何运行：
1.需要安装redis, 端口6379

2.需要Java环境（版本不能太低）

3.需要安装mysql（开发环境是8版本），并运行chat.sql文件

4.需要修改application.yaml中的配置

5.需要修改config/APPConfig.java中的配置
``` java
    //资源目录地址(windows平台下需要写成"C:\\路径\\包名"这种形式)
    public static final String resPath = "C:\\Users\\wwwww\\Documents\\res\\";      //res前的需要修改为正确的路径，会影响图片保存功能(请保存在res文件夹中)
    //资源地址头
    public static final String resHttpPath = "http://192.168.31.248:8088/res/";     //res前的需要修改为正确的服务器地址，会影响图片显示功能
```

6.需要安装pom.xml中的依赖项


