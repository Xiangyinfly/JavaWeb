package com.mySSM.baseDao;

import com.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
public interface BasicDao<T> {
    QueryRunner qr =  new QueryRunner();

    default int update(String sql, Object... parameters) {
        Connection connection = null;

        try {
            connection = JDBCUtils.getConnection();
            int update = qr.update(connection, sql, parameters);

            if (connection.getAutoCommit()) {//如果开启事务，就让业务层去关闭
                JDBCUtils.closeConnection();
            }
            return update;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DaoException("update error!");
        }



    }


    default List<T> multiQuery(String sql, Class<T> clazz, Object... parameters) {
        Connection connection = null;

        try {
            connection = JDBCUtils.getConnection();
            List<T> query = qr.query(connection, sql, new BeanListHandler<T>(clazz), parameters);
            if (connection.getAutoCommit()) {//如果开启事务，就让业务层去关闭
                JDBCUtils.closeConnection();
            }
            return query;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DaoException("multiQuery error!");
        }


    }

    default T singleQuery(String sql, Class<T> clazz, Object... parameters) {
        Connection connection = null;

        try {
            connection = JDBCUtils.getConnection();
            T query = qr.query(connection, sql, new BeanHandler<T>(clazz), parameters);
            if (connection.getAutoCommit()) {//如果开启事务，就让业务层去关闭
                JDBCUtils.closeConnection();
            }
            return query;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DaoException("singleQuery error!");
        }

    }


    default Object scalarQuery(String sql, Object... parameters) {
        Connection connection = null;

        try {
            connection = JDBCUtils.getConnection();
            Object query = qr.query(connection, sql, new ScalarHandler(), parameters);
            if (connection.getAutoCommit()) {//如果开启事务，就让业务层去关闭
                JDBCUtils.closeConnection();
            }
            return query;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DaoException("scalarQuery error!");
        }


    }
}
