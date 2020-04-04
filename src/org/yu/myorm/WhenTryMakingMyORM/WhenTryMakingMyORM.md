## When I Try To Make A ORM
the code is my testing for finding out a way which could let developing a ORM.   
by doing those test, I found that Java reflect can do what I need.  

**I want a ORM which likes MyBatis for myself, even it's simple**

### @SQL(SELECT * FROM Grade)
1. And I set out to do it, such as read the **VALUE** where located in the Annotations `@SQL`  
2. Then Passed the **VALUE** into a method/func `` as a parameter.  
```java
Connection.prepareStatement(VALUE)
```
3. But it doesn't satisfied with me, I try to make it useful like `MyBatis/MyBatis-Plus`'s interface which named BaseMapper.

### Dynamic Proxy
1. for `implements InvocationHandler`, I can use it to replace the specific classes which I have to write. After that, no longer to write lots of classes for every JavaBeans.  
2. And a new problem occur, which I have to deal with the transformation between JDBC's ResultSet and the other Java Class(including `custom JavaBeans, Object, int, Integer, String` etc.)
3. Also there is a the ReturnType of the method which likes the `List<Grade>` in method `List<Grade> select(String name, int s_math);` 

### Finally
I do it!

Thanks: MyBatis MyBatis-Plus bring me a think!