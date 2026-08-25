package cn.hytc.tlias.service.impl;


import cn.hytc.tlias.entity.EmpLog;
import cn.hytc.tlias.mapper.EmpLogMapper;
import cn.hytc.tlias.service.EmpLogService;
import cn.hytc.tlias.vo.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 操作日志服务实现
 */
@Service
public class EmpLogServiceImpl implements EmpLogService {

    @Autowired
    private EmpLogMapper empLogMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void insertLog(EmpLog empLog) {
        empLogMapper.insert(empLog);
    }



    @Override
    public List<EmpLog> list() {
        return empLogMapper.list();
    }

    @Override
    public PageResult<EmpLog> page(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<EmpLog> logList = empLogMapper.list();
        Page<EmpLog> p = (Page<EmpLog>) logList;
        return new PageResult<>(p.getTotal(), p.getResult());
    }

    @Override
    public void deleteById(Integer id) {
        empLogMapper.deleteById(id);
    }
}