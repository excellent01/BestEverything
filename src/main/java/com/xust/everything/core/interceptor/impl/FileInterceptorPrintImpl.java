package com.xust.everything.core.interceptor.impl;

import com.xust.everything.core.interceptor.FileInterceptor;

import java.io.File;

/**
 * 打印结果
 * @auther plg
 * @date 2019/7/22 8:11
 */
public class FileInterceptorPrintImpl implements FileInterceptor {
    @Override
    public void apply(File file) {
        System.out.println(file.getAbsolutePath());
    }
}
