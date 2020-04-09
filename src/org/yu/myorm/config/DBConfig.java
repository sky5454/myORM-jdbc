package org.yu.myorm.config;

enum DBDriverEnum {
    MYSQL(DBConfig.MYSQL), 
    SQLSERVER(DBConfig.SQLSERVER);
    // , SQLITE(DBConfig.SQLITE);

    private String type;

    public static DBDriverEnum formString(String type) {
        if (null == type)
            return null;
        for (DBDriverEnum e : DBDriverEnum.values()) {
            if (type.equalsIgnoreCase(e.name()))
                return e;
        }
        return null;
    }

    DBDriverEnum(String type) {
        setType(type);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}


// TODO: javaBeans need syn?
public class DBConfig {
    // MYSQL_PORT 3306 SQLSERVE_ENT_PORT 1433
    public static final String TIME_ZONE = "Asia/Shanghai";
    public static final String MYSQL = "com.mysql.cj.jdbc.Driver";
    public static final String SQLSERVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    // public static final String SQLSERVER2000 = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
    // public static final String SQLITE = "org.sqlite.JDBC";

    // private HashMap DBDriver = new HashMap<String, String>() {
    // // Anonymous Class
    // {
    // // // init block
    // DBDriver.put("MYSQL", MYSQL);
    // }
    // };

    private String url;
    private String driver;

    private String type;
    // private String version;
    private String address;
    private String port;
    private String database;
    private String user;
    private String passwd;


    /**
     * DBConfig init: url and driver
     * TODO: version
     */
    public void init() {
        // invoke this after this bean is ok;
        if (address == null || port == null || database == null || type == null)
            return; 

        // assign it when null
        if (null == driver) {
            this.driver = DBDriverEnum.formString(this.type).getType();
        }


        // assign it when null
        if (null == this.url) {
        switch (this.type.toLowerCase()) {
            case "mysql":
                this.url = "jdbc:mysql://" + address + ":" + port + "/" + database + "?"
                        + "useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=" + TIME_ZONE;
                break;
            case "sqlserver":
                this.url = "jdbc:sqlserver://" + address + ":" + port + ";DatabaseName=" + database;
                break;
            // case "sqlite": 
            default:
                this.url = "DB type unknown, url set err";
        }
        }
    }

    // @ Setter @Getter
    public void setUrl(String url) {
        this.url = url;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public String getDriver() {
        return driver;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }


    //@toString
    @Override
    public String toString() {
        return "DBConfig [address=" + address + ", driver=" + driver + ", passwd=" + passwd + ", port=" + port
                + ", database=" + database + ", type=" + type + ", url=" + url + ", user=" + user + "]";
    }
}