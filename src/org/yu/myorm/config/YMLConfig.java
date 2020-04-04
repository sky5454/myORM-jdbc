package org.yu.myorm.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class YMLConfig {

    DBConfig DB;

    public static YMLConfig loadDataFromYML(String ymlPath) throws FileNotFoundException {
        Yaml yml = new Yaml(new Constructor(YMLConfig.class));
        //Microsoft JDK11LTS  TODO: buffer
        YMLConfig ymlConfig = yml.load(new FileInputStream(ymlPath));
        ymlConfig.DB.init();
        return ymlConfig;
    }


    // @SGetter
    public DBConfig getDB() {
        return DB;
    }

    public void setDB(DBConfig DB) {
        this.DB = DB;
    }
}