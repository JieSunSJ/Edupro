package cn.hytc.edupro.mapper;

import cn.hytc.edupro.entity.SystemConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SystemConfigMapper {
    @Select("SELECT * FROM system_config WHERE config_key = #{key}")
    SystemConfig getByKey(@Param("key") String key);
}