package com.xust.everything.core.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 文件类型
 */
public enum FileType {
    IMG("png","jpeg","jpg","gif","img"),
    DOC("ppt","pptx","doc","docx","pdf"),
    BIN("exe","sh","jar","msi"),
    ARCHIVE("zip","rar"),
    OTHER("*");
    private Set<String> set = new HashSet<>();
    FileType(String...extend){
        this.set.addAll(Arrays.asList(extend));
    }

    /**
     * 根据文件扩展名获取文件类型
     * @param extend
     * @return
     */
    public static FileType lookUp(String extend){
        for(FileType fileType : FileType.values()){
            if(fileType.set.contains(extend)){
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
