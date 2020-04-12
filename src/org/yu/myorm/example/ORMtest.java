package org.yu.myorm.example;// package xxx;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

import org.yaml.snakeyaml.error.YAMLException;

import org.yu.myorm.config.DBConfig;
import org.yu.myorm.config.YMLConfig;
import org.yu.myorm.core.DBConnecter;
import org.yu.myorm.core.Exception.NoSuchDataInDBException;
import org.yu.myorm.core.handleErr;
import org.yu.myorm.core.dynproxy.MapperInvoHander;
import org.yu.myorm.example.mapper.Grade;
import org.yu.myorm.example.service.BaseMapper;

public class ORMtest {
//    static final String YML_PATH = "./myFile/tconf.yml";
      static final String YML_PATH = "./src/main/resources/myconf.yml";
//    static final String YML_PATH = "./tconf.yml";
    public static Connection conn;

    public static void main(String[] args) {

        // 获取一个数据的连接
        Connection conn = null;
        YMLConfig ymlConfig;
        DBConfig dbconf;
        // 获取连接的一个状态
        try {
            System.out.println("YML_FILE_PATH: " + new File(YML_PATH).getCanonicalPath());
            ymlConfig = YMLConfig.loadDataFromYML(YML_PATH);
            dbconf = ymlConfig.getDB();
            // dbconf.setUrl("your can set url here, or set in the yml, or automatic set by Class YMLConfig");
            conn = DBConnecter.getConnection(dbconf);
            if (!conn.isClosed())
                System.out.println("数据库连接成功！");


            DBConnecter.printConnInfo(conn);
            // tableQueryTest.test(conn);


            InvocationHandler invocationHandler = new MapperInvoHander();
            BaseMapper baseMapper = (BaseMapper) Proxy.newProxyInstance(BaseMapper.class.getClassLoader(), new Class[]{BaseMapper.class}, invocationHandler);
            // Object e = new Object();
            Grade entity = new Grade();
            // String r =
            // int i = baseMapper.select(grade);
            // Grade grade = baseMapper.select(entity);
            // Grade grade = baseMapper.select(3);
            // String i = baseMapper.selectNameById(53);
            // List<Grade> grade = baseMapper.select("B", 800);
            // baseMapper.select("C");
            // System.out.print(grade);
            // System.out.println(i);
            Grade grade = baseMapper.select(3333);
            System.out.println("Result :" + grade);
            int updateCount = baseMapper.insert(entity);
            System.out.println(updateCount);

        } catch (NoSuchDataInDBException dbe) {
            handleErr.printErr(dbe, "No Such Data In DB!", false);
        } catch (ClassNotFoundException e) {
            handleErr.printErr(e, "DB Driver Load Failed!", false);
        } catch (SQLException e1) {
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