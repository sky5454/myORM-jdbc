package org.yu.myorm.core.Exception;

import java.lang.reflect.Type;
import java.util.Arrays;

public class NoSuchDataInDBException extends Exception {
    private Object[] DBfield = null;

    public NoSuchDataInDBException(Type returnType) {
        this("(UnHandle ERR)" + returnType.getTypeName());
    }

    public NoSuchDataInDBException(String message) {
        super(message);
    }

    public NoSuchDataInDBException(String s, Object[] args) {
        super("(problemSQL)" + s);
        DBfield = args;
    }


    public String getMsg() {
        return getMessage() + Arrays.deepToString(DBfield);
    }


}
