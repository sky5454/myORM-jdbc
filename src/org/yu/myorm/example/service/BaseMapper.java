package org.yu.myorm.example.service;

import java.util.List;

import org.yu.myorm.core.dynproxy.SQL;
import org.yu.myorm.example.mapper.Grade;


// @Mapper
public interface BaseMapper<E> {

    @SQL("SELECT * FROM grade where id = ?")
    Grade select(int id);


    @SQL("SELECT * FROM grade where name = ? and s_math > ?")
    List<Grade> select(String name, int s_math);


    
    @SQL("SELECT * FROM grade where name = ?")
    Object select(String name);

    @SQL("INSERT INTO grade VALUES (?E)")
    int insert(Grade Entity);


    @SQL("SELECT ID FROM grade where name=? and s_math=?")
    int selectIdByData(String name, int s_math);

    @SQL("SELECT name FROM grade where id=?")
    String selectNameById(int id);
}


