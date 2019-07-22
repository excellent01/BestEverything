package com.xust.everything.core.search.impl;
import com.xust.everything.core.dao.FileIndexDao;
import com.xust.everything.core.model.Condition;
import com.xust.everything.core.model.Thing;
import com.xust.everything.core.search.FileSearch;
import java.util.List;

/**
 * @auther plg
 * @date 2019/7/21 18:10
 */

public class FileSearchimpl implements FileSearch {
    private  final FileIndexDao fileIndexDao;
    public FileSearchimpl(FileIndexDao fileIndexDao) {
        this.fileIndexDao = fileIndexDao;
    }
    @Override
    public List<Thing> search(Condition condition) {
        return fileIndexDao.search(condition);
    }
}
