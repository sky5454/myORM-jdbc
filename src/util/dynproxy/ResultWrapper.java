package util.dynproxy;

import java.io.InputStream;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import util.DBConnecter;
import util.handleErr;

/**
 * 
 * see:
 * https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/reflect/Type.html
 * see: https://www.cnblogs.com/linghu-java/p/8067886.html
 */
public class ResultWrapper {

    public static Object convert(ResultSet rs, Type returnType) { // handler
        try {
            if (returnType instanceof ParameterizedType) {
                // 参数化类型 such as Coolection<String>, List<T>
                return convert(rs, (ParameterizedType) returnType);
            } else if (returnType instanceof TypeVariable) {
                // 泛型类型 T, E
                return convert(rs, (TypeVariable<?>) returnType);
            } else if (returnType instanceof WildcardType) {
                // ? , (? super xx) etc.
                return convert(rs, (WildcardType) returnType);
            } else if (returnType instanceof GenericArrayType) {
                // 泛型类型数组 如List<?>[], ..Map<E>[] , 其组件同时包含了 ParameterizedType 和 TypeVariable.
                return convert(rs, (GenericArrayType) returnType);
            } else if (returnType instanceof Class) {
                // 基本类型、其他引用类型（包括数组） Object or Other Class, include List[], Map[]
                return convert(rs, (Class) returnType);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            handleErr.printErr(e, "parse Result to Instance failed!", true);
        } catch (Exception e1) {
            handleErr.printErr(e1, "Exception", true);
        }

        String name = returnType.getTypeName();
        System.out.println("[ERR]: " + name + "hasn't be handled...");
        return new Object();
    }

    private static Object convert(ResultSet rs, Class returnType) throws Exception { // 不属于其他四种Type类型
        Class sortClass = Object.class;
        try {
            sortClass = (Class) returnType.getField("TYPE").get(null);
        } catch (Exception e) {
            /* 若为基本类型的包装类则赋值；若不是则catch异常，但无需处理，用于后面判断是否为基本类型的包装类 */ }

        if (returnType == void.class || returnType == Void.class) {
            return returnType.newInstance();

        } else if (returnType.isPrimitive()) { // 基本类型
            return rs.getObject(1);

        } else if (sortClass.isPrimitive()) { // 基本类型的包装类
            return returnType.cast(rs.getObject(1));

        } else if (returnType == String.class) {

            return rs.getString(1);

        } else if (returnType == ResultSet.class) {
            return rs; // without testing

        } else if (returnType == java.math.BigDecimal.class) {

        } else if (returnType == byte[].class) {

        } else if (returnType == List[].class) {

        } else if (returnType == Map[].class) {

        } else if (returnType == java.sql.Date.class) {

        } else if (returnType == java.sql.Time.class) {

        } else if (returnType == java.sql.Timestamp.class) {

        } else if (returnType == InputStream.class) {

        } else if (returnType == java.sql.Blob.class) {

        } else if (returnType == java.sql.Clob.class) {

        } else if (returnType == java.util.Date.class) {

        } else if (returnType == java.math.BigInteger.class) {

        } else if (returnType == LocalDate.class) {

        } else if (returnType == LocalDateTime.class) {

        } else if (returnType == LocalTime.class) {

        } else if (returnType == java.sql.ResultSet.class) {

        } else {
            // 自定义Entity类
            return DBConnecter.convertEntity(rs, returnType);
        }

        return new Object();
    }

    private static Object convert(ResultSet rs, ParameterizedType returnType) throws Exception { // 参数化类型 such as
                                                                                                 // Coolection<String>,
                                                                                                 // List<T>

        if (returnType.getRawType() == List.class) {
            List dataList = new LinkedList();
            rs.beforeFirst();
            while (rs.next()) {
                dataList.add(DBConnecter.convertEntity(rs, (Class) returnType.getActualTypeArguments()[0]));
            }

            System.out.println("\n当前行： " + rs.getRow() + "\n数组大小: " + dataList.size() + "\n到达结尾：" + rs.isLast()
                    + "\n直接跳转到结尾：" + rs.last() + "\n结果集总行数: " + rs.getRow());
            return dataList;
        } else if (returnType instanceof Map<?, ?>) {
            int i = 0;
            Map dataMap = new HashMap<>();
            rs.beforeFirst();
            while (rs.next()) {
                // TODO: what is K
                dataMap.put(i++, DBConnecter.convertEntity(rs, (Class) returnType.getActualTypeArguments()[0]));
            }
            return dataMap;
        }
        return new Object();
    }

    private static Object convert(ResultSet rs, TypeVariable returnType) { // 类型变量 T, E
        return new Object();
    }

    private static Object convert(ResultSet rs, WildcardType returnType) { // ?, (? extends xx), (? super xx) etc.
        return new Object();
    }

    private static Object convert(ResultSet rs, GenericArrayType returnType) throws Exception { // 泛型类数组 如List<?>[],
                                                                                                // ..Map<E>[] ,
                                                                                                // 其组件同时包含了
                                                                                                // ParameterizedType
                                                                                                // 和
                                                                                                // TypeVariable，官方文档如是说
        if (returnType instanceof List<?>) {
            List dataList = new LinkedList();
            rs.beforeFirst();
            while (rs.next()) {
                dataList.add(DBConnecter.convertEntity(rs, returnType.getClass()));
            }
            return dataList;
        } else if (returnType instanceof Map<?, ?>) {

        }

        return new Object();
    }
}