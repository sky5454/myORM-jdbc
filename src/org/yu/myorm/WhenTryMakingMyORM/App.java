package org.yu.myorm.WhenTryMakingMyORM;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.yu.myorm.core.dynproxy.ResultWrapper;

public class App {
    private static String Name =  "stu_name_for";
    public static void main(String[] args) throws Exception {
        System.out.println("Hello Java");
        // System.out.print("stu_name_for".replaceAll("_([a-z])", "0$1"));

        // String k = commonUtil.lineToHump(Name);
        // System.out.println(k);
        Method method = App.class.getMethod("testM");
        Type returnType = method.getGenericReturnType();
        Class returnClass = method.getReturnType();
        AnnotatedType annotatedType =  method.getAnnotatedReturnType();
    
        //System.out.println(List.class.isInstance(returnType));
        //System.out.println(List.class == method.getReturnType());
        method.getDeclaredAnnotations();
        method.getGenericReturnType();
        k(App.class);

        ResultSet rs = null;
        // returnType.
        // ResultWrapper.convert(rs, returnType);
        ResultWrapper.convert(rs,  App.class.getMethod("testM", App.class).getGenericReturnType());
    }

    public List<Map> testM() {
        Map a;
        return new LinkedList<Map>();
    }

    public int testM(App app) {
        List k = new ArrayList();
        return int.class.cast(3);

    }

    static void k(Object k) {
        System.out.println("Object K");
    }

    static void k(String k) {
        System.out.println("String K");
    }

    static void k(App app) {
        System.out.println("APP");
    }
    static void k(Iapp app) {
        System.out.println("I_APP");
    }
    static void k(Class<String> app) {
        System.out.println("CLASS_APP");
    }
}