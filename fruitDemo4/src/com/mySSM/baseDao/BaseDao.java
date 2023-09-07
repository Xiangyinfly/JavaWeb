package com.mySSM.baseDao;

import com.utils.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

//以后尝试使用common-dbutils
public abstract class BaseDao<T> {

    public int executeUpdate(String sql,Object... params) {
        try {
            Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            if (params != null && params.length != 0) {
                for (int i = 1; i <= params.length; i++) {
                    preparedStatement.setObject(i, params[i - 1]);
                }
            }

            int rows = preparedStatement.executeUpdate();
            preparedStatement.close();
            if (connection.getAutoCommit()) {//如果开启事务，就让业务层去关闭
                JDBCUtils.closeConnection();
            }

            return rows;
        } catch (Exception e) {
            throw new RuntimeException();
        }


    }

    public List<T> executeQuery(Class<T> cls,String sql,Object... params) {
        try {
            Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            if (params != null && params.length != 0) {
                for (int i = 1; i <= params.length; i++) {
                    preparedStatement.setObject(i,params[i - 1]);
                }
            }
            ResultSet resultSet = preparedStatement.executeQuery();

            List<T> list = new ArrayList<>();
            ResultSetMetaData metaData = resultSet.getMetaData();
            while (resultSet.next()) {
                T t = cls.newInstance();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    Object value = resultSet.getObject(i);
                    String columnLabel = metaData.getColumnLabel(i);
                    Field field = cls.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t,value);
                }
                list.add(t);
            }

            resultSet.close();
            preparedStatement.close();
            if (connection.getAutoCommit()) {//如果开启事务，就让业务层去关闭
                JDBCUtils.closeConnection();
            }

            return list;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
