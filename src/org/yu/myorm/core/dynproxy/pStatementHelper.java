package org.yu.myorm.core.dynproxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.yu.myorm.core.DBConnecter;
import org.yu.myorm.core.handleErr;

public class pStatementHelper {
    public static PreparedStatement parseParamer(String sqlString, Object[] args, Method method)
            throws IllegalArgumentException, IllegalAccessException {
        
        Object[] values = args.clone();

        // TODO: add handler for Entity
        if (!sqlString.equalsIgnoreCase(ClassUtil.getValue(method))) {  //TODO: may need optimize...
            Field[] fields = args[0].getClass().getDeclaredFields();
            int entitySize = fields.length;
            values = new Object[entitySize];
            for (int i = 0; i < entitySize; i++) {
                boolean accessFlag = fields[i].isAccessible();
                fields[i].setAccessible(true);
                values[i] = fields[i].get(args[0]);
                fields[i].setAccessible(accessFlag);
            }
        }

        // exec -> sql
        Connection conn = DBConnecter.getConnection();
        try {
            PreparedStatement pStatement = conn.prepareStatement(sqlString);
            int paramCount = pStatement.getParameterMetaData().getParameterCount();
            System.out.println("### prepareStatement: " + sqlString);
            System.out.print("### Params: " + '\t');


            // set Entity args to pStatement
            String guessSQL = sqlString;
            for (int i = 0; i < paramCount; i++) {
                // TODO: NOTE may be 90 convert to "B"?
                pStatement.setObject(i+1, values[i]);                                                                          // set:  void com.mysql.cj.AbstractQueryBindings.setObject(int parameterIndex, Object parameterObj)
                String valueString = (values[i] == null)? "null" : values[i].toString();
                System.out.print(valueString + "\t | \t");
                guessSQL = guessSQL.replaceFirst("\\?", valueString);
            }
            System.out.println();
            System.out.println("guessSql: " + guessSQL);
            return pStatement;
        } catch (SQLException e) {
                handleErr.printErr(e, method, sqlString, "SQLException", false);
        }
        return null;
    }



    public static String concatPreSqlString(String sqlString, Object args) {
        if (sqlString.contains("?E")) {
            Field[] fields = args.getClass().getDeclaredFields();
            int entitySize = fields.length;
            
            String parmString = "";
            for (int i = 0; i < entitySize; i++) {
                parmString = (i == entitySize-1)? parmString.concat("\\?") : parmString.concat("\\?\\, ");
                
            }
            sqlString = sqlString.replaceAll("\\?E", parmString);
            System.out.println("Found Entity and has concated!");
        }
        return sqlString;
    }
}