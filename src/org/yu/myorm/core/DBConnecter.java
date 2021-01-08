package org.yu.myorm.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.yu.myorm.config.DBConfig;

public class DBConnecter {
    // 单例模式
    private static Connection conn = getSingleton();
    private static Statement statement;
    private static PreparedStatement preparedStatement;

    private DBConnecter() {
    }

    private static Connection getSingleton() {
        return conn;
    }

    public static Connection getConnection() {
        return conn;
    }

    public static Connection getConnection(final DBConfig dbconf) throws SQLException, ClassNotFoundException {
        if (conn != null  && !conn.isClosed())
            return conn;
        Class.forName(dbconf.getDriver());
        // getConnection()方法，连接MySQL数据库！
        System.out.println("Connecting to " + dbconf.getUrl());
        conn = DriverManager.getConnection(dbconf.getUrl(), dbconf.getUser(), dbconf.getPasswd());
        return conn;
    }

    public static void close(final Connection conn) {
        System.out.println("\nClosing DB Connection...");
        if (conn == null) return;
        try {
            conn.close();
            System.out.println("Closed DB Connection!");
        } catch (final SQLException e) {
            handleErr.printErr(e, "Connection Close Failed!!!", true);
        }
    }

    // TODO: 日志功能
    public static void printConnInfo(final Connection conn) throws SQLException {
        final DatabaseMetaData metaData = conn.getMetaData();
        final ResultSet metaRS = metaData.getCatalogs();
        System.out.println(metaData.getDatabaseProductName() + "ver" + metaData.getDatabaseProductVersion() + "\n"
                + metaData.getDriverName() + metaData.getDriverVersion() + "\n" + metaData.getCatalogSeparator() + "\n"
                + ((metaRS.next()) ? metaRS.getString(1) : "RS_null"));
    }

    public PreparedStatement getPreStatement() {
        return preparedStatement;
    }

    public Statement getStatement() {
        return statement;
    }

    public static void printfResult(final ResultSet rs, boolean isPrintData) throws SQLException {
        final ResultSetMetaData rsMetaData = rs.getMetaData();
        final int count = rsMetaData.getColumnCount();
        System.out.println("=====================" + rsMetaData.getTableName(1) + "============================");
        // PRINT table's Column
        for (int i = 1; i <= count; i++) {
            System.out.print(rsMetaData.getColumnName(i) + "\t|\t");
        }
        System.out.println();

        // PRINT the type of table's Column
        for (int i = 1; i <= count; i++) {
            System.out.print('[' + rsMetaData.getColumnTypeName(i) + "]\t=\t");
        }
        System.out.println();

        if (!isPrintData) return;
        // PRINT table's data
        while (rs.next()) {
            for (int i = 1; i <= count; i++) {
                System.out.print(rs.getObject(i) + "\t|\t");
            }
            System.out.println("\n");
        }
    }

    public static <E> Object convertEntity(final ResultSet rs, Class clazz) throws Exception {
        if (rs == null)
            return new Object();
        // TODO: 在多行rs时可优化
        ResultSetMetaData rsMetaData = rs.getMetaData();
        int count = rsMetaData.getColumnCount();
        Field[] fields = clazz.getFields();
        Object entity;

        try {
            // TODO: when clazz is not a JavaBeans but a int, Integer, String,    then how to do ?
            // entity = clazz.getDeclaredConstructor().newInstance(); //JDK9+
            entity = clazz.newInstance();
            for (int i = 1; i <= count; i++) {
                // if some error happen here, table's column name maybe no fit the format:   (Lowercase letters and underline)
                // or columnName can't fit with the javabeans
                String columnName = rsMetaData.getColumnName(i);

                // (db)stu_name_code -> (javaField)stuNameCode
                String fieledName = commonUtil.lineToHump(columnName);
                Field f = clazz.getDeclaredField(fieledName);


                // Upper the first letter
                String substring = fieledName.substring(0, 1);
                String UfieldName = fieledName.replaceFirst(substring, substring.toUpperCase());

                // invoke field's setter func
                Method method = clazz.getMethod("set" + UfieldName, f.getType());
                method.invoke(entity, rs.getObject(i));
            }
            return entity;
        } catch (Exception e) {
            handleErr.printErr(e, "The javabean may not fit with the Table's columnName", true);
        }
        

        return new Object();
    }

}
