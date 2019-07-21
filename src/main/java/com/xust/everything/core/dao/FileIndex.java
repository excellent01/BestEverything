package com.xust.everything.core.dao;

import com.xust.everything.core.model.Condition;
import com.xust.everything.core.model.Thing;
import java.util.List;


/**
 * 业务层访问数据的CRUD
 */
public interface FileIndex {
    void insert(Thing thing);
    List<Thing> search(Condition condition);
}
