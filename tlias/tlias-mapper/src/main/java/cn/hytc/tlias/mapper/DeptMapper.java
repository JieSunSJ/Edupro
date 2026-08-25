package cn.hytc.tlias.mapper;

import cn.hytc.tlias.entity.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface DeptMapper {
    @Select("select * from dept order by id")
    List<Dept> list();
}