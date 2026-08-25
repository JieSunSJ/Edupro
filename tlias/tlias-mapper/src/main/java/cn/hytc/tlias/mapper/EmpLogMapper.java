package cn.hytc.tlias.mapper;

import cn.hytc.tlias.entity.EmpLog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 操作日志Mapper
 */
@Mapper
public interface EmpLogMapper {

    @Insert("insert into emp_log (operate_time, info) values (#{operateTime}, #{info})")
    void insert(EmpLog empLog);

    @Select("select id, operate_time, info from emp_log order by operate_time desc")
    List<EmpLog> list();

    @Delete("delete from emp_log where id = #{id}")
    void deleteById(Integer id);
}