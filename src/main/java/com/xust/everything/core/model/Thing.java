package com.xust.everything.core.model;

import lombok.Data;

/**
 * 文件属性信息索引之后的记录
 * 存储在数据库中
 * @auther plg
 * @date 2019/7/21 16:14
 */
@Data
// get set toString 自动生成
public class Thing {

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件路径
     */
    private String path;

    /**
     * 文件路径深度
     */
    private Integer depth;

    /**
     * 文件类型
     */
    private FileType fileType;

}
