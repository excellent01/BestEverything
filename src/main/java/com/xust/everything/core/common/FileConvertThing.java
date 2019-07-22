package com.xust.everything.core.common;

import com.xust.everything.core.model.FileType;
import com.xust.everything.core.model.Thing;

import java.io.File;

/**
 * 工具类
 * 将File对象转换为Thing
 * @auther plg
 * @date 2019/7/22 7:11
 */
public final class FileConvertThing {
    private FileConvertThing(){}

    public static Thing convert (File file){
        Thing thing = new Thing();
        thing.setName(file.getName());
        thing.setPath(file.getPath());
        thing.setDepth(getFileDepth(file));
        thing.setFileType(getFileType(file));
        return thing;
    }

    /**
     * 获取文件的深度
     * @param file
     * @return 文件深度
     */
    private static int getFileDepth(File file){
        String[] arr = file.getAbsolutePath().split("\\\\");
        if(arr != null){
            return arr.length;
        }
        return 0;
    }

    /**
     * abc.txt----> txt
     * a.b.c.ppt--->ppt
     * 获取文件的扩展名
     * @param file
     * @return
     */
    private static FileType getFileType(File file){
        if(file.isDirectory()){
            return FileType.OTHER;
        }
        String[] arr = file.getName().split(".");
        String extendName = null;
        if(arr != null && arr.length > 0) {
             extendName = arr[arr.length - 1];
             return FileType.lookUp(extendName);
        }
        return FileType.OTHER;
    }
}
