package com.xust.everything.core.dao.impl;
import com.xust.everything.core.dao.FileIndexDao;
import com.xust.everything.core.model.Condition;
import com.xust.everything.core.model.FileType;
import com.xust.everything.core.model.Thing;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther plg
 * @date 2019/7/21 18:25
 */
public class FileIndexDaoImpl implements FileIndexDao {
    private final DataSource ds;
    public FileIndexDaoImpl(DataSource ds) {
        this.ds = ds;
    }
    @Override
    public void insert(Thing thing) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            // 获取连接
            connection = ds.getConnection();
            // sql语句
            String sql = "insert into file_index (name, path, depth, file_type) values (?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            // 设置参数
            preparedStatement.setString(1,thing.getName());
            preparedStatement.setString(2,thing.getPath());
            preparedStatement.setInt(3,thing.getDepth());
            preparedStatement.setString(4,thing.getFileType().name());

            // 执行
            preparedStatement.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            release(null,preparedStatement,connection);
        }
    }
    @Override
    public List<Thing> search(Condition condition) {


        //名称模糊匹配  like
        String name = condition.getName();
        // 类型精确匹配 =
        String fileTyoe = condition.getFileType();
        // 限制行数
        String limit = condition.getLimit() + "";
        // 排序显示
        String sort = condition.getOrderByASC() ? "Asc" : "Desc";
        StringBuilder sql = new StringBuilder();
        sql.append(" select name,path,depth,file_type from file_index ");
        sql.append(" where ")
        .append(" name like '%").append(name).append("%' ");


        if(fileTyoe != null){
            sql.append(" and file_type = '").append(fileTyoe.toUpperCase()).append("'");
        }

        // limit order by
        sql.append(" order by depth ").append(sort).append(" limit ").append(limit).append(" offset 0 ");

        List<Thing> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            // 获取连接
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(sql.toString());
            // 执行DQL
             resultSet = preparedStatement.executeQuery();
            // 处理结果
            while(resultSet.next()){
                Thing thing = new Thing();
                thing.setName(resultSet.getString("name"));
                thing.setPath(resultSet.getString("path"));
                thing.setDepth(resultSet.getInt("depth"));
                thing.setFileType(FileType.lookUpByName(resultSet.getString("file_type")));
                list.add(thing);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            release(resultSet,preparedStatement,connection);
        }
        return list;
    }

    /**
     * 删除
     * @param thing
     */
    @Override
    public void delete(Thing thing) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            // 获取连接
            connection = ds.getConnection();
            // sql语句
            String sql = "delete from file_index where path like '" + thing.getPath() + "%'";
            preparedStatement = connection.prepareStatement(sql);
            // 执行
            preparedStatement.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            release(null,preparedStatement,connection);
        }
    }

    private void release(ResultSet resultSet,PreparedStatement preparedStatement,Connection connection){

        if(resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(preparedStatement != null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
