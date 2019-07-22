package com.xust.everything.core.dao;
import com.alibaba.druid.pool.DruidDataSource;
import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *  生产DataSource的工厂
 *  工厂 + 单例
 * @auther plg
 * @date 2019/7/21 17:06
 */
public final class DataSourceFactory {

    private static volatile DruidDataSource dataSource;
    private DataSourceFactory(){
    }
    public static DataSource dataSource(){
        if(dataSource == null){
            synchronized (DataSourceFactory.class){
                if(dataSource == null){
                    //实例化
                    dataSource = new DruidDataSource();
                    //  初始化操作
                    dataSource.setDriverClassName("org.h2.Driver");
                    // 获取当前工程路径 System.getProperty("user.dir")
                    String dir = System.getProperty("user.dir");
                    // 采用H2嵌入式的数据库，数据库以本地文件存储
                    dataSource.setUrl("jdbc:h2:" + dir + File.separator + "best_Everything");
                }
            }
        }
        return dataSource;
    }

    /**
     * 数据库的初始化工作
     */
    public  static void initDatabase() {
        // 1、获取数据源
        DataSource ds = DataSourceFactory.dataSource();
        // 2、读取文件，编程sql语句
        StringBuilder sb = new StringBuilder();
        try(InputStream in = DataSourceFactory.class.getClassLoader().getResourceAsStream("bestEverything.sql");){
            if(in == null){
                throw new RuntimeException("not read init database");
            }
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(in));){

                String line = null;
                while((line = reader.readLine()) != null){
                    if(!line.startsWith("--")){
                        sb.append(line);
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
       String sql = sb.toString();
        Connection connection = null;
        PreparedStatement psatement = null;
        try {
            // 3、获取连接对象
             connection = ds.getConnection();
            // 4、执行sql语句
             psatement = connection.prepareCall(sql);
            psatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            // 关闭数据库资源
        }finally {
            try {
                psatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}
