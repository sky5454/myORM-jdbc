package org.yu.myorm.core.dynproxy;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;


public class ClassUtil {

    private static final HashMap CLASS_CACHE = new HashMap<String, List<Object>>();
    private static final HashMap METHOD_CACHE = new HashMap<Method, String>();
    private static final HashMap FIELD_CACHE = new HashMap<String, HashMap<String, Method>>();
    


    public static String getValue(Method method) {
        //method.setAccessible(true);// 跳过安全检查，加速反射
        String value = "";
        if (null != METHOD_CACHE.get(method)) {
            return value = METHOD_CACHE.get(method).toString();
        }

    
        if (method.isAnnotationPresent(SQL.class)) {    // need optimize?
            SQL anno = method.getAnnotation(SQL.class);
            System.out.println("[METHOD]: " + method.toGenericString());
            System.out.println("[Anno.value]: " + anno.value());
            // METHOD_CACHE.toString();
            // METHOD_CACHE.put(method, anno.value());
            return anno.value();
        }

        return value;
    }



}