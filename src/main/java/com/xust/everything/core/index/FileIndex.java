package com.xust.everything.core.index;

import com.xust.everything.core.interceptor.FileInterceptor;
import com.xust.everything.core.model.Thing;

public interface FileIndex {
    // 遍历path
    void scan(String path);

    // 遍历的拦截器
    void interceptor(FileInterceptor fileInterceptor);
}
