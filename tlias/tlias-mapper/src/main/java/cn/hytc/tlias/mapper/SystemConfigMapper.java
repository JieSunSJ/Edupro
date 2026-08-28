package cn.hytc.tlias.mapper;

import cn.hytc.tlias.entity.SystemConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SystemConfigMapper {
    @Select("SELECT * FROM system_config WHERE config_key = #{key}")
    SystemConfig getByKey(@Param("key") String key);
}