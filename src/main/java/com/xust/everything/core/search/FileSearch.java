package com.xust.everything.core.search;

import com.xust.everything.core.model.Condition;
import com.xust.everything.core.model.Thing;

import java.util.List;

public interface FileSearch {

    /**
     * 根据条件进行检索
     * @param condition
     * @return
     */
    public List<Thing> search(Condition condition);

}
