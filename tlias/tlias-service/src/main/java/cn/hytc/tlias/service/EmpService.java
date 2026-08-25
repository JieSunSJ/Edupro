package cn.hytc.tlias.service;


import cn.hytc.tlias.entity.Emp;
import cn.hytc.tlias.entity.LoginInfo;
import cn.hytc.tlias.vo.PageResult;

import java.math.BigDecimal;
import java.util.List;

/**
 * 员工服务接口
 */
public interface EmpService {
    List<Emp> list(String name, Integer gender, Integer job, Integer deptId, BigDecimal salaryMin, BigDecimal salaryMax);
    PageResult<Emp> page(Integer page, Integer pageSize, String name, Integer gender, Integer job, Integer deptId, BigDecimal salaryMin, BigDecimal salaryMax);
    /**
     * 添加员工
     * @param emp
     */
    void save(Emp emp);

    void deleteByIds(List<Integer> ids);
    Emp getById(Integer id);
    void updateById(Emp emp);
    /**
     * 登录
     */
    LoginInfo login(Emp emp);
    /**
     * 修改密码
     */
    void updatePassword(Integer id, String oldPassword, String newPassword);
}