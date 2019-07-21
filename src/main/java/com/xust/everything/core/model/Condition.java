package com.xust.everything.core.model;

import lombok.Data;

/**
 * 搜索条件
 * @auther plg
 * @date 2019/7/21 16:17
 */
@Data
public class Condition {

    private Integer limit;

    private String name;


    private String fileType;

    /**
     * 排序规则
     */
    private Boolean orderByASC;
}
