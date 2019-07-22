package com.xust.everything.core;

import com.xust.everything.core.index.FileIndex;
import com.xust.everything.core.model.Condition;
import com.xust.everything.core.model.Thing;
import com.xust.everything.core.search.FileSearch;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 调度器
 * @auther plg
 * @date 2019/7/22 6:55
 */
public class Manager {
    private final FileSearch fileSearch;
    private final FileIndex fileIndex;

    private ExecutorService service;

    public Manager(FileSearch fileSearch, FileIndex fileIndex) {
        this.fileSearch = fileSearch;
        this.fileIndex = fileIndex;
    }

    public List<Thing> search(Condition condition){
        //NOTICE 扩展
        return this.fileSearch.search((condition));
    }

    public void buildIndex(Thing thing){
        if(service == null){
            service = Executors.newFixedThreadPool(10);
        }
       // this.fileIndex.index(thing);
    }
}
