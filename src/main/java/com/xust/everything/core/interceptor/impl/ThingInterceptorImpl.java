package com.xust.everything.core.interceptor.impl;

import com.xust.everything.core.dao.FileIndexDao;
import com.xust.everything.core.interceptor.ThingInterceptor;
import com.xust.everything.core.model.Thing;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @auther plg
 * @date 2019/7/22 12:03
 */
public class ThingInterceptorImpl implements ThingInterceptor , Runnable {
    private Queue<Thing> queue = new ArrayBlockingQueue<>(1024);

    private final FileIndexDao fileIndexDao;

    public ThingInterceptorImpl(FileIndexDao fileIndexDao) {
        this.fileIndexDao = fileIndexDao;
    }

    @Override
    public void apply(Thing thing) {
        this.queue.add(thing);
    }


    @Override
    public void run() {
        while(true){
           if(queue.size() > 0){{
               Thing thing = this.queue.poll();
               fileIndexDao.delete(thing);
           }}
        }
    }
}
