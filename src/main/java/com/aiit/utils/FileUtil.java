package com.aiit.utils;

import com.aiit.config.APPConfig;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.UUID;

public class FileUtil {

    //将文件路径转换为http地址
    static public String filePathToHttpAddress(String path) {
        if (path == null) {
            return null;
        }
        return path.replace(APPConfig.resPath, APPConfig.resHttpPath);
    }

    // 保存图片到本地
    static public String saveImage(MultipartFile file) {
        try {
            File fileMkdir = new File(APPConfig.imagePath);
            if (!fileMkdir.exists()) {
                fileMkdir.mkdir();
            }
            String filePath = fileMkdir.getPath() + "/" + UUID.randomUUID() + file.getOriginalFilename();
            FileOutputStream os = new FileOutputStream(filePath);
            final BufferedOutputStream bufOs = new BufferedOutputStream(os);
            InputStream in = file.getInputStream();
            int data;
            while ((data = in.read()) != -1) { //读取文件
                bufOs.write(data);
            }
            bufOs.flush(); //关闭流
            in.close();
            bufOs.close();
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //保存文件到本地
    static public String saveFile(byte[] file, String type) {
        File fileMkdir = new File(APPConfig.messageFilePath);
        if (!fileMkdir.exists()) {
            fileMkdir.mkdir();
        }
        String filePath = fileMkdir.getPath() + "/" + UUID.randomUUID() + "." + type;
        try {
            FileOutputStream os = new FileOutputStream(filePath);
            final BufferedOutputStream bufOs = new BufferedOutputStream(os);
            for (Byte aByte : file) {
                bufOs.write(aByte);
            }
            bufOs.flush();
            bufOs.close();

            return filePath;
        } catch (Exception e) {
            return null;
        }
    }

    static public String localResToHttpRes(String res) {
        if (res == null) {
            return "";
        }
        return res.replace(APPConfig.resLocalHttpPath, APPConfig.resHttpPath);
    }
}
