package cn.hytc.tlias.mapper;

import cn.hytc.tlias.entity.Emp;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 员工Mapper
 */
@Mapper
public interface EmpMapper {
    List<Emp> list(@Param("name") String name, @Param("gender") Integer gender, @Param("job") Integer job,
                   @Param("deptId") Integer deptId, @Param("salaryMin") BigDecimal salaryMin, @Param("salaryMax") BigDecimal salaryMax);
    /**
     * 新增员工数据
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time) " +
            "values (#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime})")
    void insert(Emp emp);
    /**
     * 批量删除员工信息
     */
    void deleteByIds(@Param("ids") List<Integer> ids);
    /**
     * 根据ID获取员工信息
     */
    Emp getById(@Param("id") Integer id);
    /**
     * 根据ID更新员工信息
     */
    void updateById(Emp emp);
    /**
     * 统计各个职位的员工人数
     */
    @MapKey("pos")
    List<Map<String,Object>> countEmpJobData();
    /**
     * 统计各个性别员工人数
     */
    @MapKey("gender")
    List<Map> countEmpGenderData();
    /**
     * 根据用户名和密码查询员工信息
     */
    @Select("select * from emp where username = #{username} and password = #{password}")
    Emp getUsernameAndPassword(Emp emp);
    /**
     * 修改密码
     */
    @Update("update emp set password = #{newPassword}, update_time = now() where id = #{id} and password = #{oldPassword}")
    int updatePassword(@Param("id") Integer id, @Param("oldPassword") String oldPassword, @Param("newPassword") String newPassword);
}