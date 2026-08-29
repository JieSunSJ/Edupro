package cn.hytc.edupro.mapper;

import cn.hytc.edupro.entity.Schedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ScheduleMapper {
    List<Schedule> findByStudentId(@Param("studentId") Integer studentId, @Param("keyword") String keyword);
}