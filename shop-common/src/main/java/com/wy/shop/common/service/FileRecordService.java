package com.wy.shop.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wy.shop.common.entity.FileRecord;

public interface FileRecordService extends IService<FileRecord> {
    FileRecord getByHash(String fileHash);
}