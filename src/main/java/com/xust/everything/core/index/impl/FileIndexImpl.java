package com.xust.everything.core.index.impl;
import com.xust.everything.config.Config;
import com.xust.everything.core.dao.DataSourceFactory;
import com.xust.everything.core.dao.impl.FileIndexDaoImpl;
import com.xust.everything.core.index.FileIndex;
import com.xust.everything.core.interceptor.FileInterceptor;
import com.xust.everything.core.interceptor.impl.FileIndexInterceptor;
import com.xust.everything.core.interceptor.impl.FileInterceptorPrintImpl;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * 索引类，将文件信息存入数据库
 * @auther plg
 * @date 2019/7/21 20:08
 */
public class FileIndexImpl implements FileIndex {


    private List<FileInterceptor> interceptors = new LinkedList<>();

    private Config config = Config.getInstance();

    @Override
    public void scan(String path) {
        File file = new File(path);
        if(file.isFile()){
            String parent = file.getParent();
            //D:\a\b\abc.ppt ---> D:\a\b
            if(config.getExcludePath().contains(parent)){
                return;
            }
        }else {
            if (config.getExcludePath().contains(path)) {
                return;
            }
            File[] files = file.listFiles();
            if (files != null) {
                for (File file1 : files) {
                    scan(file1.getAbsolutePath());
                }
            }
        }

        // File DIrectory
        // 将最终获得的文件进行拦截处理
        for(FileInterceptor interceptor : this.interceptors){
            interceptor.apply(file);
        }
    }

    @Override
    public void interceptor(FileInterceptor fileInterceptor) {
        this.interceptors.add(fileInterceptor);
    }

}
