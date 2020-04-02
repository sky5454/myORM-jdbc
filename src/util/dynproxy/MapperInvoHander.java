package util.dynproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.DBConnecter;
import util.handleErr;


public class MapperInvoHander implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String sqlString = ClassUtil.getValue(method);

        // exec -> sql
        Connection conn = DBConnecter.getConnection();
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlString);
            int paramCount = pStatement.getParameterMetaData().getParameterCount();
            System.out.println("### prepareStatement: " + sqlString);
            System.out.print("### Params: " + '\t');
            
            // set args to pStatement
            for (int i = 0; i < paramCount; i++) {
                // TODO: NOTE may be 90 convert to "B" 
                pStatement.setObject(i+1, args[i]);                     // set:  void com.mysql.cj.AbstractQueryBindings.setObject(int parameterIndex, Object parameterObj)
                System.out.print(args[i].toString() + "\t | \t");
                sqlString = sqlString.replaceFirst("\\?", args[i].toString());
            }
            System.out.println();
            System.out.println("concatSql: " + sqlString);
            pStatement.execute();
            ResultSet rs = pStatement.getResultSet();
            DBConnecter.printfResult(rs, true); // TODO: conf
            rs.beforeFirst();
            if (rs.next()) {
                return ResultWrapper.convert(rs, method.getGenericReturnType());
            } else {
                return new Object();
            }
            // Object returnClass = method.getReturnType();
        
           


        } catch (SQLException e) {
            handleErr.printErr(e, method, sqlString, "SQLException", false);
        }
       
        


        String returnType = method.getGenericReturnType().getTypeName();
        System.out.println(" -[ReturnTYPE]: " + returnType);        // int, java.lang.String  etc..
        switch (returnType) {
            
        }
        
        return sqlString.length();
    }
    
    
}