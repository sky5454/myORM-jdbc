package org.yu.myorm.core.Exception;

import java.lang.reflect.Type;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;

public class NoSuchDataInDBException extends UndeclaredThrowableException {
    private Object[] DBfield = null;

    public NoSuchDataInDBException(Type returnType) {
        this("(UnHandle ERR)" + returnType.getTypeName());
    }

    public NoSuchDataInDBException(String message) {
        super(null, message);
    }

    public NoSuchDataInDBException(String s, Object[] args) {
        this("(problemSQL)" + s);
        DBfield = args;
    }


    @Override
    public String getMessage() {
        return super.getMessage() + "\t args: " + Arrays.deepToString(DBfield);
    }
}
