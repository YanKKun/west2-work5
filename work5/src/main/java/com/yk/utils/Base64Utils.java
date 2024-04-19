package com.yk.utils;

import sun.misc.BASE64Decoder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

/**
 * @author 12080
 */
public class Base64Utils {
    public static String generateImage(String base64String) throws IOException {
        String imagePath = "D:\\"+ UUID.randomUUID()+".png";
        // 解码Base64字符串并保存为图片
        try {
            // 解码Base64字符串
            byte[] imageBytes = Base64.getDecoder().decode(base64String);
            // 创建输出流，将解码后的数据写入图片文件
            try (FileOutputStream fos = new FileOutputStream(imagePath)) {
                fos.write(imageBytes);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fos);
                bufferedOutputStream.write(imageBytes);
                System.out.println("图片保存成功");
            } catch (IOException e) {
                System.out.println("图片保存失败：" + e.getMessage());
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Base64字符串解码失败：" + e.getMessage());
        }
        return imagePath;
    }

}
