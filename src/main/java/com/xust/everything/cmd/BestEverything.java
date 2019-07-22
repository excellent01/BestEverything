package com.xust.everything.cmd;

/**
 * @auther plg
 * @date 2019/7/21 14:41
 */

import com.xust.everything.core.dao.DataSourceFactory;
import com.xust.everything.core.dao.FileIndexDao;
import com.xust.everything.core.dao.impl.FileIndexDaoImpl;
import com.xust.everything.core.index.FileIndex;
import com.xust.everything.core.index.impl.FileIndexImpl;
import com.xust.everything.core.model.Condition;
import com.xust.everything.core.model.FileType;
import com.xust.everything.core.model.Thing;
import com.xust.everything.core.search.FileSearch;
import com.xust.everything.core.search.impl.FileSearchimpl;

import java.util.List;

/**
 * 项目的入口
 */
public class BestEverything {
    public static void main(String[] args) {
        System.out.println("这是BestEverything应用程序的命令行交互程序的入口");

    }
}
