package org.yu.myorm.example;

import java.sql.*;

public class JDBCTest {
    public static void test(Connection conn) throws SQLException {
        // 创建statement类对象，用来执行SQL语句！
        Statement Statement = conn.createStatement();
        // 要执行的SQL语句


        String creatSQL =   "" +
                            "create table grade (" +
                            "   id int  AUTO_INCREMENT," +
                            "   name char(20)," +
                            "   s_math int," +
                            "   PRIMARY KEY(id)" +
                            ");"
                            ;
                            // System.out.println(Statement.execute(creatSQL));
        
        String insertSQL =  "" +
                            "INSERT INTO grade (id, name, s_math) " +
                            "  VALUES" +
                            "  (null, \"A\", 87)," +
                            "  (null, \"B\", RAND()*10000)," +
                            "  (null, \"C\", 90)" +
                            ";"
                            ;
        String prepareQuerySQL = "SELECT * FROM grade where s_math > ?";
        PreparedStatement pStatement = conn.prepareStatement(prepareQuerySQL, Statement.RETURN_GENERATED_KEYS);
        pStatement.setInt(1, 93);
        ResultSet rsPS= pStatement.executeQuery();
        // System.out.println(rsPS.next() + rsPS.getString(2));



        System.out.println(Statement.execute(insertSQL, Statement.RETURN_GENERATED_KEYS));
        String sql = "select * from grade";
        // ResultSet类，用来存放获取的结果集！
        ResultSet rs = Statement.executeQuery(sql);

        System.out.println("-------------------------------");
        System.out.println("学号" + "\t" + "姓名" + "\t" + "数学成绩");
        System.out.println("-------------------------------");
        String id = null;
        String name = null;
        String math = null;
        while (rs.next()) {
            // 获取‘学号’这列数据
            id = rs.getString("id");
            name = rs.getString("name");
            math = rs.getString("s_math");
            // 输出结果
            System.out.println(id + "\t" + name + "\t" + math);
        }



        //TODO: remember
        rs.close();
        Statement.close();
        // conn.close();
    }
}