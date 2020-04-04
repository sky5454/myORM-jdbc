package org.yu.myorm.example.mapper;

import java.io.Serializable;

public class Grade implements Serializable {
    int id;
    String name = "";
    int sMath;



    // "   id int  AUTO_INCREMENT," +
    // "   name char(20)," +
    // "   s_math int,"

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSMath() {
        return sMath;
    }

    public void setSMath(int s_math) {
        this.sMath = s_math;
    }

    @Override
    public String toString() {
        return "Grade [id=" + id + ", name=" + name + ", sMath=" + sMath + "]";
    }
    
    

    
}