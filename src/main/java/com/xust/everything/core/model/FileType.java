package com.xust.everything.core.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 文件类型
 */
public enum FileType {
    /**
     * 文本文件
     */
    IMG("png","jpeg","jpg","gif","img"),
    /**
     * 图片文件
     */
    DOC("ppt","pptx","doc","docx","pdf"),
    /**
     * 二进制文件
     */
    BIN("exe","sh","jar","msi"),
    /**
     * 压缩文件
     */
    ARCHIVE("zip","rar"),
    OTHER("*");
    private Set<String> set = new HashSet<>();
    FileType(String...extend){
        this.set.addAll(Arrays.asList(extend));
    }

    /**
     * 根据文件扩展名获取文件类型
     * @param extendName
     * @return
     */
    public static FileType lookUp(String extendName){
        for(FileType fileType : FileType.values()){
            if(fileType.set.contains(extendName)){
                return fileType;
            }
        }
       return FileType.OTHER;
    }


    /**
     * 根据文件类型名称获取文件类型对象
     * 本质：String fileType ---> enum  fileType
     * @param name
     * @return
     */
    public static FileType lookUpByName(String name){
        for(FileType fileType : FileType.values()){
            if(fileType.name().equals(name)){
                return fileType;
            }
        }
        return FileType.OTHER;
    }



}
