package cn.hytc.tlias.service.impl;

import cn.hytc.tlias.entity.Dept;
import cn.hytc.tlias.mapper.DeptMapper;
import cn.hytc.tlias.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Dept> list() {
        return deptMapper.list();
    }
}