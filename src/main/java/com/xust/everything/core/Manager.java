package com.xust.everything.core;
import com.xust.everything.config.Config;
import com.xust.everything.core.dao.DataSourceFactory;
import com.xust.everything.core.dao.FileIndexDao;
import com.xust.everything.core.dao.impl.FileIndexDaoImpl;
import com.xust.everything.core.index.FileIndex;
import com.xust.everything.core.index.impl.FileIndexImpl;
import com.xust.everything.core.interceptor.ThingInterceptor;
import com.xust.everything.core.interceptor.impl.FileIndexInterceptor;
import com.xust.everything.core.interceptor.impl.FileInterceptorPrintImpl;
import com.xust.everything.core.interceptor.impl.ThingInterceptorImpl;
import com.xust.everything.core.model.Condition;
import com.xust.everything.core.model.Thing;
import com.xust.everything.core.search.FileSearch;
import com.xust.everything.core.search.impl.FileSearchimpl;
import javax.sql.DataSource;
import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 调度器，客户端与业务层解耦
 * @auther plg
 * @date 2019/7/22 6:55
 */
public class Manager {
    private static Manager manager = new Manager();
    private  FileSearch fileSearch;
    private  FileIndex fileIndex;
    private ExecutorService service;
    private ThingInterceptorImpl thingInterceptorimpl;
    private AtomicBoolean status = new AtomicBoolean(false);

    /**
     * 处理删除
     */

    private Thread threadClean;
    private Manager(){
        init();
    }

    public static Manager getManager(){
        return manager;
    }
    public void init () {
        // 数据源
        DataSource dataSource = DataSourceFactory.dataSource();
        checkDatabase();

        FileIndexDao fileIndexDao = new FileIndexDaoImpl(dataSource);
        this.fileSearch = new FileSearchimpl(fileIndexDao);
        this.fileIndex = new FileIndexImpl();
        this.fileIndex.interceptor(new FileInterceptorPrintImpl());
        this.fileIndex.interceptor(new FileIndexInterceptor(fileIndexDao));
        this.thingInterceptorimpl = new ThingInterceptorImpl(fileIndexDao);
        this.threadClean = new Thread(thingInterceptorimpl);
        threadClean.setName("Thread-Thing-clean");
        threadClean.setDaemon(true);
    }

    /**
     * 检查数据库
     */
    private void checkDatabase() {
        String workDir = System.getProperty("user.dir");
        String fileName = workDir + File.separator + "mv.db";
        File dbFile = new File(fileName);
        if(dbFile.isFile() && !dbFile.exists()){
            DataSourceFactory.initDatabase();
        }
    }

    /**
     * 检索
     * @param condition
     * @return
     */
    public List<Thing> search(Condition condition) {
        //TODO 扩展
        //Stream
        return this.fileSearch.search(condition).stream().filter(thing -> {
            String path = thing.getPath();
            File file = new File(path);
            boolean flag = file.exists();
            if(!flag){
                // 删除，需要阻塞，生产者消费者模型
                thingInterceptorimpl.apply(thing);
            }
            return flag;
        }).collect(Collectors.toList());
    }
    /**
     * 多线程扫描文件构建索引
     */
    public void buildIndex() {
        Set<String> includePath = Config.getInstance().getIncludePath();
        if (service == null) {
            service = Executors.newFixedThreadPool(includePath.size(), new ThreadFactory() {
                private final AtomicInteger id = new AtomicInteger(0);

                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setName("Thread-Scan-" + id.getAndIncrement());
                    return thread;
                }
            });

            final CountDownLatch countDownLatch = new CountDownLatch(includePath.size());
            System.out.println("buildIndex start...");
            for (String str : includePath) {
                service.execute(new Runnable() {
                    @Override
                    public void run() {
                        // 扫描文件
                        Manager.this.fileIndex.scan(str);
                        countDownLatch.countDown();
                    }
                });
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("buildIndex end");
        }
    }
    /**
     * 启动清理线程
     */
    public void start(){
        if(this.status.compareAndSet(false,true)){
            threadClean.start();
        }
    }
}
