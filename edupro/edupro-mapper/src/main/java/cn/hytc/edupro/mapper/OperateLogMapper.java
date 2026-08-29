package cn.hytc.edupro.mapper;

import cn.hytc.edupro.entity.OperateLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OperateLogMapper {

    @Insert("INSERT INTO operate_log (operate_user_id, operate_user, operate_time, class_name, method_name, method_params, return_value, cost_time, action, detail) " +
            "VALUES (#{operateUserId}, #{operateUser}, #{operateTime}, #{className}, #{methodName}, #{methodParams}, #{returnValue}, #{costTime}, #{action}, #{detail})")
    void insert(OperateLog operateLog);

    @Select("SELECT * FROM operate_log ORDER BY operate_time DESC LIMIT #{offset}, #{limit}")
    List<OperateLog> list(@Param("offset") int offset, @Param("limit") int limit);

    @Select("SELECT COUNT(*) FROM operate_log")
    Long count();
}