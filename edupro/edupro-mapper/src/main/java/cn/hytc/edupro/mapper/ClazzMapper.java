package cn.hytc.edupro.mapper;

import cn.hytc.edupro.entity.Clazz;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;
/**
 * 班级Mapper
 */
@Mapper
public interface ClazzMapper {
    List<Clazz> list(@Param("name") String name, @Param("beginDate") LocalDate beginDate, @Param("endDate") LocalDate endDate);
    @Delete("DELETE FROM clazz WHERE id = #{id}")
    void deleteById(@Param("id") Integer id);
    void insert(Clazz clazz);
    @Select("SELECT * FROM clazz WHERE id = #{id}")
    Clazz getById(@Param("id") Integer id);
    void update(Clazz clazz);
    @Select("SELECT * FROM clazz")
    List<Clazz> Alllist();
}