package designing;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import util.dynproxy.MapperInvoHander;

public class MyProxy {

    public static void main(String[] args) {
        InvocationHandler invocationHandler = new MapperInvoHander();
        BaseMapper baseMapper = (BaseMapper)Proxy.newProxyInstance(BaseMapper.class.getClassLoader(), new Class[]{ BaseMapper.class}, invocationHandler);
        Object e = new Object();
        // String r = 
        baseMapper.select(e);
    }
}