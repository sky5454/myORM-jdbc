// package mysql;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.yaml.snakeyaml.error.YAMLException;

import config.DBConfig;
import config.YMLConfig;
import designing.BaseMapper;
import tmp.Grade;
import tmp.tableQueryTest;
import util.DBConnecter;
import util.handleErr;
import util.dynproxy.MapperInvoHander;

public class JDBCtest {
    static final String YML_PATH = "./tconf.yml";
    public static Connection conn;

    public static void main(String[] args) {

        // 获取一个数据的连接
        Connection conn = null;
        YMLConfig ymlConfig;
        DBConfig dbconf;
        // 获取连接的一个状态
        try {
            new DBConfig().setUrl("fg");
            ymlConfig = YMLConfig.loadDataFromYML(YML_PATH);
            dbconf = ymlConfig.getDB();
            conn = DBConnecter.getConnection(dbconf);
            if (!conn.isClosed())
                System.out.println("数据库连接成功！");

               
                DBConnecter.printConnInfo(conn);
                tableQueryTest.test(conn);
            
            

                InvocationHandler invocationHandler = new MapperInvoHander();
                BaseMapper baseMapper = (BaseMapper)Proxy.newProxyInstance(BaseMapper.class.getClassLoader(), new Class[]{ BaseMapper.class}, invocationHandler);
                Object e = new Object();
                // String r = 
                // int i = baseMapper.select(e);
                // Grade grade = baseMapper.select(3);
                // String i = baseMapper.selectNameById(53);
                List<Grade> grade = baseMapper.select("B", 800);
                // baseMapper.select("C");
                System.out.print(grade);
                // System.out.println(i);

        } catch (ClassNotFoundException e) {
            // 数据库驱动类异常处理
            handleErr.printErr(e, "DB Driver Load Failed!", false);
        } catch (SQLException e1) {
            // 数据库连接失败异常处理
            handleErr.printErr(e1, "DB CONNECT/COMMAND FAILED!  state: " + e1.getSQLState() +'\n'+ "VendorErrCode: "+e1.getErrorCode(), false);
        } catch (YAMLException e2) {
            handleErr.printErr(e2, "LOAD OBJECT FROM YAML FAILED!", false);
        } catch (Exception e3) {
            handleErr.printErr(e3, "EXCEPTION!!!", true);
        } finally {
            // try-with-resources
            DBConnecter.close(conn);
            System.out.println("----------------EOF---------------");
        }
    }
}

// ————————————————
// 版权声明：本文为CSDN博主「杨丹的博客」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
// 原文链接：https://blog.csdn.net/yangdan1025/article/details/80591012