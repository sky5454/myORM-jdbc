package org.yu.myorm.core.dynproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.yu.myorm.core.DBConnecter;
import org.yu.myorm.core.Exception.NoSuchDataInDBException;


public class MapperInvoHander implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //long startTime = System.currentTimeMillis();
        PreparedStatement pStatement = null;
        try {

            String sqlString = ClassUtil.getValue(method);
            if (args == null) {
                pStatement = DBConnecter.getConnection().prepareStatement(sqlString);
            } else {
                sqlString = pStatementHelper.concatPreSqlString(sqlString, args[0]);
                pStatement = pStatementHelper.parseParamer(sqlString, args, method);
            }

            System.out.println("realSQL: " + pStatement); // MySQL supported, such as com.mysql.cj.jdbc.ClientPreparedStatement: SELECT * FROM grade where id = 3333
            if (sqlString.toLowerCase().startsWith("select")) {       // SELECT
                pStatement.execute();
                ResultSet rs = pStatement.getResultSet();
                DBConnecter.printfResult(rs, true); // TODO: conf
                rs.beforeFirst();
                if (rs.next()) {
                    return ResultWrapper.convert(rs, method.getGenericReturnType());
                } else {
//                    return method.getReturnType().newInstance();
                    throw new NoSuchDataInDBException(sqlString, args);
                }
            } else { // update, delete, etc...
                if (method.getReturnType() == Boolean.class || method.getReturnType() == boolean.class)
                    return (pStatement.executeUpdate() > 0);
                else
                    return pStatement.executeUpdate();
            }

            // System.out.println(" -[ReturnTYPE]: " + method.getGenericReturnType().getTypeName());        // int, java.lang.String  etc..
        } catch (NoSuchDataInDBException e) {
            throw e;
        } finally {
            //System.out.println("TIME: " + (System.currentTimeMillis()-startTime));
            if (null != pStatement)
                pStatement.close();
        }
    }
    
    
}