package org.yu.myorm.WhenTryMakingMyORM;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.yu.myorm.example.service.BaseMapper;
import org.yu.myorm.core.dynproxy.ClassUtil;
import org.yu.myorm.core.dynproxy.SQL;

import java.lang.Class;

public class test {

    public static void main(String[] args) {

        long now0, now1, now2, start;
        long n1, n2, k2, k1;
        Method method;

        start = System.currentTimeMillis();

     
           
       
        
        // 仿照MybatisPlus
        try {
            k1 = System.currentTimeMillis();
            method = BaseMapper.class.getMethod("select", Object.class);
            k2 = System.currentTimeMillis();
            System.out.println("GETMethod " + (k2-k1));

            n1 = System.currentTimeMillis();
            String at = ClassUtil.getValue(method);
            n2 = System.currentTimeMillis();
            System.out.println("TIME " + (n2-n1));



            now0 = System.currentTimeMillis();
            String a = ClassUtil.getValue(method);
            now1 = System.currentTimeMillis();
            System.out.println("Use noCache time: " + (now1 - now0));
            String b = ClassUtil.getValue(method);
            now2 = System.currentTimeMillis();
            System.out.println("Use Cache time: " + (now2 - now1));
        } catch (NoSuchMethodException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("Total time: " + (end-start) + "ms");
    }

    public static void main01(String[] args) {
        // 获得类上的注解
        Class aclass = BaseMapper.class;
        Annotation[] annotations = aclass.getAnnotations();
        Annotation annotation = aclass.getAnnotation(SQL.class);
        // for (Annotation annotation : annotations) {
        //     if (annotation instanceof SQL) {
        //         SQL sql = (SQL)annotation;
        //         System.out.println(sql.value());
        //     }
        // }

        // 获得方法上的注解
        Method[] methods = (BaseMapper.class).getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(SQL.class)) {    // need optimize?
                Annotation anno = method.getAnnotation(SQL.class);
                System.out.println(((SQL)anno).value());
            }
        }
    
        
       System.out.println("END!!!");
    }
}