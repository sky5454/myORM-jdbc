package designing;

import java.sql.ResultSet;
import java.util.List;

import tmp.Grade;
import util.dynproxy.SQL;


// @Mapper
public interface BaseMapper<E> {

    @SQL("SELECT ID FROM grade where 1=1 or ?=1")
    int select(E Entity);

    @SQL("SELECT * FROM grade where id = ?")
    Grade select(int id);


    @SQL("SELECT * FROM grade where name = ? and s_math > ?")
    List<Grade> select(String name, int s_math);
    // E>


    
    @SQL("SELECT * FROM grade where name = ?")
    Object select(String name);

    @SQL("INSERT ? INTO grade VALUES (?)")
    boolean insert(E Entity);


    @SQL("SELECT ID FROM grade where name=? and s_math=?")
    int selectIdByData(String name, int s_math);

    @SQL("SELECT name FROM grade where id=?")
    String selectNameById(int id);
}


