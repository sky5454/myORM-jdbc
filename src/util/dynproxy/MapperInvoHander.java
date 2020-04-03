package util.dynproxy;

import java.lang.reflect.Field;
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
        Object[] values = args.clone();

        // TODO: add handler for Entity
        if (sqlString.contains("?E")) {
            Field[] fields = args[0].getClass().getDeclaredFields();
            int entitySize = fields.length;
            String parmString = "";
            values = new Object[entitySize];
            for (int i = 0; i < entitySize; i++) {
                parmString = (i == entitySize-1)? parmString.concat("\\?") : parmString.concat("\\?\\, ");
                boolean accessFlag = fields[i].isAccessible();
                fields[i].setAccessible(true);
                values[i] = fields[i].get(args[0]);
                fields[i].setAccessible(accessFlag);
            }
            sqlString = sqlString.replaceAll("\\?E", parmString);
            System.out.println("Found Entity and has concated!");
        }

        // exec -> sql
        Connection conn = DBConnecter.getConnection();
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlString);
            int paramCount = pStatement.getParameterMetaData().getParameterCount();
            System.out.println("### prepareStatement: " + sqlString);
            System.out.print("### Params: " + '\t');
            
            // set Entity args to pStatement
            for (int i = 0; i < paramCount; i++) {
                // TODO: NOTE may be 90 convert to "B"?
                pStatement.setObject(i+1, values[i]);                                                                          // set:  void com.mysql.cj.AbstractQueryBindings.setObject(int parameterIndex, Object parameterObj)
                String valueString = (values[i] == null)? "null" : values[i].toString();
                System.out.print(valueString + "\t | \t");
                sqlString = sqlString.replaceFirst("\\?", valueString);
            }
            System.out.println();
            System.out.println("guessSql: " + sqlString);
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