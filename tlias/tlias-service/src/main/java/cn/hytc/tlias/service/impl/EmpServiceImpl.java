package cn.hytc.tlias.service.impl;


import cn.hytc.tlias.entity.Emp;
import cn.hytc.tlias.entity.EmpExpr;
import cn.hytc.tlias.entity.LoginInfo;
import cn.hytc.tlias.mapper.EmpExprMapper;
import cn.hytc.tlias.mapper.EmpMapper;
import cn.hytc.tlias.annotation.Log;
import cn.hytc.tlias.service.EmpService;
import cn.hytc.tlias.utils.JwtUtils;
import cn.hytc.tlias.vo.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工服务实现
 */
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private EmpExprMapper empExprMapper;

    public List<Emp> list(String name, Integer gender, Integer job, Integer deptId, BigDecimal salaryMin, BigDecimal salaryMax) {
        return empMapper.list(name, gender, job, deptId, salaryMin, salaryMax);
    }

    public PageResult<Emp> page(Integer page, Integer pageSize, String name, Integer gender, Integer job, Integer deptId, BigDecimal salaryMin, BigDecimal salaryMax) {
        PageHelper.startPage(page, pageSize);
        List<Emp> empList = empMapper.list(name, gender, job, deptId, salaryMin, salaryMax);
        Page<Emp> p = (Page<Emp>) empList;
        return new PageResult<>(p.getTotal(), p.getResult());
    }

    @Log(module = "员工管理", operation = "批量删除员工")
    @Transactional
    @Override
    public void deleteByIds(List<Integer> ids) {
        empMapper.deleteByIds(ids);
        empExprMapper.deleteByEmpIds(ids);
    }

    @Log(module = "员工管理", operation = "新增员工")
    @Transactional
    @Override
    public void save(Emp emp) {
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.insert(emp);

        Integer empId = emp.getId();
        List<EmpExpr> exprList = emp.getExprList();
        if (!CollectionUtils.isEmpty(exprList)) {
            exprList.forEach(empExpr -> empExpr.setEmpId(empId));
            empExprMapper.insertBatch(exprList);
        }
    }

    @Override
    public Emp getById(Integer id) {
        return empMapper.getById(id);
    }

    @Log(module = "员工管理", operation = "修改员工")
    @Transactional
    @Override
    public void updateById(Emp emp) {
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);

        empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));

        Integer empId = emp.getId();
        List<EmpExpr> exprList = emp.getExprList();
        if (!CollectionUtils.isEmpty(exprList)) {
            exprList.forEach(empExpr -> empExpr.setEmpId(empId));
            empExprMapper.insertBatch(exprList);
        }
    }
    @Override
    public LoginInfo login(Emp emp) {
        Emp empLogin = empMapper.getUsernameAndPassword(emp);
        if(empLogin != null){
            if(emp.getRole() != null && !emp.getRole().equals(empLogin.getRole())){
                return null;
            }
            Map<String,Object> dataMap = new HashMap<>();
            dataMap.put("id", empLogin.getId());
            dataMap.put("username", empLogin.getUsername());
            dataMap.put("role", empLogin.getRole());

            String jwt = JwtUtils.generateJwt(dataMap);
            LoginInfo loginInfo = new LoginInfo(empLogin.getId(), empLogin.getUsername(), empLogin.getName(), empLogin.getRole(), jwt, empLogin.getImage());
            return loginInfo;
        }
        return null;
    }
    @Override
    public void updatePassword(Integer id, String oldPassword, String newPassword) {
        int rows = empMapper.updatePassword(id, oldPassword, newPassword);
        if (rows == 0) {
            throw new RuntimeException("原密码错误");
        }
    }
}