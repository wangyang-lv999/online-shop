package com.wy.shop.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wy.shop.common.entity.FileRecord;
import com.wy.shop.common.mapper.FileRecordMapper;
import com.wy.shop.common.service.FileRecordService;
import org.springframework.stereotype.Service;

@Service
public class FileRecordServiceImpl
        extends ServiceImpl<FileRecordMapper, FileRecord>
        implements FileRecordService {

    @Override
    public FileRecord getByHash(String fileHash) {
        return getOne(new QueryWrapper<FileRecord>().eq("file_hash", fileHash));
    }
}