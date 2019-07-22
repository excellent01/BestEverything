package com.xust.everything.core.model;

import lombok.Data;

/**
 * 对搜素条件的封装
 * @auther plg
 * @date 2019/7/21 16:17
 */
@Data
public class Condition {

    /**
     * 显示的条数
     */
    private Integer limit;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 排序规则
     */
    private Boolean orderByASC;
}
