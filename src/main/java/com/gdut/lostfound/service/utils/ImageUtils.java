package com.gdut.lostfound.service.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Service
public class ImageUtils {
    /**
     * 文件上传的路径
     */
    @Value("${web.upload-path}")
    private String path;
    //去掉base64编码的头部 如："data:image/jpeg;base64," 如果不去，转换的图片不可以查看
    //data:image/x-icon;base64,
    //data:image/png;base64,
    private static final String flag = ";base64,";
    private static final String imageSuffix = ".jpg";

    /**
     * 去掉base64头部
     */
    public String getBase64Image(String file) throws UnsupportedEncodingException {
        int index = file.indexOf(flag);//对于json传的base64图片可直接截取

        if (index == -1) {
            file = java.net.URLDecoder.decode(file, "UTF-8");//对于使用url编码过的base64图片需要解码
            index = file.indexOf(flag);
        }
        if (index == -1) {
            return "";
        } else {
            return file.substring(index + flag.length());
        }
    }

    /**
     * 保存文件
     */
    public Resource copyFileToResource(byte[] bytes) throws IOException {
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        File tempFile = File.createTempFile("upload_", imageSuffix, f);

        FileCopyUtils.copy(bytes, tempFile);

        return new FileSystemResource(tempFile);
    }


}
