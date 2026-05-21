package com.wy.shop.common.util;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class ImageUtil {

    /**
     * 生成商品图片多尺寸
     * 原图：保留原始尺寸
     * 电脑版（_pc）：宽度 1080px，高度自适应
     * 手机版（_mobile）：宽度 720px，高度自适应
     */
    public void generateProductSizes(String sourcePath) throws IOException {
        File sourceFile = new File(sourcePath);
        String parentPath = sourceFile.getParent();
        String fileName   = sourceFile.getName();
        String baseName   = fileName.substring(0, fileName.lastIndexOf("."));
        String extension  = fileName.substring(fileName.lastIndexOf("."));

        // 电脑版
        Thumbnails.of(sourceFile)
                .width(1080)
                .keepAspectRatio(true)
                .outputQuality(0.85f)
                .toFile(new File(parentPath, baseName + "_pc" + extension));

        // 手机版
        Thumbnails.of(sourceFile)
                .width(720)
                .keepAspectRatio(true)
                .outputQuality(0.8f)
                .toFile(new File(parentPath, baseName + "_mobile" + extension));
    }
}