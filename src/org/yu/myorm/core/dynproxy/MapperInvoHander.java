package org.yu.myorm.core.dynproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.yu.myorm.core.DBConnecter;


public class MapperInvoHander implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String sqlString = ClassUtil.getValue(method);
        PreparedStatement pStatement;
        if (args == null) {
            pStatement = DBConnecter.getConnection().prepareStatement(sqlString);
        } else {
            sqlString = pStatementHelper.concatPreSqlString(sqlString, args[0]);
            pStatement = pStatementHelper.parseParamer(sqlString, args, method);
        }

            if (sqlString.toLowerCase().startsWith("select")) {       // SELECT
                pStatement.execute();
                ResultSet rs = pStatement.getResultSet();
                DBConnecter.printfResult(rs, true); // TODO: conf
                rs.beforeFirst();
                if (rs.next()) {
                    return ResultWrapper.convert(rs, method.getGenericReturnType());
                } else {
                    return method.getReturnType().newInstance();
                }
            } else { // update, delete, etc...
                if (method.getReturnType() == Boolean.class || method.getReturnType() == boolean.class)
                    return (pStatement.executeUpdate() > 0);
                else
                    return pStatement.executeUpdate();
            }
       
        // System.out.println(" -[ReturnTYPE]: " + method.getGenericReturnType().getTypeName());        // int, java.lang.String  etc..
 
    }
    
    
}