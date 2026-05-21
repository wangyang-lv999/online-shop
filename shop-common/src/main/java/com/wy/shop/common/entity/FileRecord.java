package com.wy.shop.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("sys_file_record")
public class FileRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String fileHash;
    private String fileUrl;
    private Long fileSize;
    private String fileType;
    private LocalDateTime createTime;
}