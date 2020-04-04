# myOrm-jdbc
 a ORM wrote by JDBC(MySQL) named myOrm-jdbc...

- for boring life
- and for study how to make a ORM.


## Environment
 - MySQL8.0
 - OpenJDK 20.1-(Zulu-11.37+17-win_x64)-Microsoft-Azure-restricted (build 11.0.6+10-LTS)
 - VSCode / eclipse / IDEA
 
And it also work in the JDK8

### About Developing
see [WhenTryMakingMyORM.md](src/org/yu/myorm/WhenTryMakingMyORM/WhenTryMakingMyORM.md)


### Dependency 

#### mysql-connecter-8.0.17
MySQL 的 JDBC 驱动，官方文档说向下兼容


#### snakeyaml
虽然 Java 有原生的 Java.org.yu.myorm.core.Properties 可以读取配置文件，但由于其可读性较低，不便于用户修改配置，
因此采用 yaml 格式的配置文件，通过第三方库snakeyaml 来读取配置文件并存入一个 JavaBeans (即 DBConfig 类)
