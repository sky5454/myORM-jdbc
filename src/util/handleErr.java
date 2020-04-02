package util;

import java.lang.reflect.Method;

public class handleErr {
    private static int i;

    public static void printErr(Exception e, String msg, boolean enablePrintStack) {
        System.err.println("=====================================================[E"+  i++ +"]=====================================================");
        System.err.println("[ERR]:  \t\t" + msg);
        // e.getCause()
        System.err.println("[ERR_MSG] " + e.getClass().getName() + ":  " + e.getMessage());
        if (enablePrintStack) {
            System.err.println("[ERR_TRACE]: ");
            e.printStackTrace();
        }
        System.err.println("=============================================================================================================================================");
    }



    public static void printErr(Exception e, Method method, String sqlString, String msg, boolean enablePrintStack) {
        // TODO: 放到ErrHandler
        System.err.println("=====================================================[E"+  i++ +"]=====================================================");
        System.out.println( "\n\t" +
                            "[ERR]: \t\t" + msg +
                            "[ERR_MSG]:" + e.getMessage()  + "\n\t" +
                            "[In Method]:\t " +
                            method.toGenericString() + "\n\t" +
                            "[SQL]:\t" + sqlString
        );

        if (enablePrintStack) {
            System.err.println("[ERR_TRACE]: ");
            e.printStackTrace();
        }
        System.err.println("=============================================================================================================================================");
    }
}