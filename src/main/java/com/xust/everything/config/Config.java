package com.xust.everything.config;

import lombok.Getter;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

/**
 * 配置类
 * @auther plg
 * @date 2019/7/22 7:27
 */
@Getter
public class Config {
    private static Config config = new Config();
    /**
     * 建立索引的路径
     */
    private Set<String> includePath = new HashSet<>();

    /**
     * 排除索引文件的路径
     */
    private Set<String> excludePath = new HashSet<>();

    private Config(){}
    public static Config getInstance(){
        // 获取文件系统
        FileSystem fileSystem = FileSystems.getDefault();

        Iterable<Path> iterable = fileSystem.getRootDirectories();

        // 包含路径
        iterable.forEach(path -> {config.getIncludePath().add(path.toString());});
        //在Windows下：  C:\Windows,   C:\Program Files,   C:\Program Files (x86), C:\ProgramData
        //在linux下： /tmp  /etc

        String osNamm = System.getProperty("os.name");

        //排除文件
        if(osNamm.startsWith("Windows")){
            config.getExcludePath().add("C:\\Windows");
            config.getExcludePath().add("C:\\Program Files");
            config.getExcludePath().add("C:\\Program Files (x86)");
            config.getExcludePath().add("C:\\ProgramData");
        }else{
            config.getExcludePath().add("/tmp");
            config.getExcludePath().add("/etc");
        }
        return config;
    }

}
