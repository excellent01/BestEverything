package com.xust.everything.core.interceptor.impl;

import com.xust.everything.core.common.FileConvertThing;
import com.xust.everything.core.dao.FileIndexDao;
import com.xust.everything.core.interceptor.FileInterceptor;
import com.xust.everything.core.model.FileType;
import com.xust.everything.core.model.Thing;

import java.io.File;

/**
 * 将结果存入数据库
 * @auther plg
 * @date 2019/7/22 8:28
 */
public class FileIndexInterceptor implements FileInterceptor {
    private final FileIndexDao fileIndexDao;

    public FileIndexInterceptor(FileIndexDao fileIndexDao) {
        this.fileIndexDao = fileIndexDao;
    }

    @Override
    public void apply(File file) {

        // File---> Thing
        Thing thing = FileConvertThing.convert(file);
        System.out.println("Thing ==>" + thing);
        fileIndexDao.insert(thing);
    }
}
