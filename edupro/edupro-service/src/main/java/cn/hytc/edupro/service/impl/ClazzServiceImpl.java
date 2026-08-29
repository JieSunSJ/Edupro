package cn.hytc.edupro.service.impl;

import cn.hytc.edupro.entity.Clazz;
import cn.hytc.edupro.mapper.ClazzMapper;
import cn.hytc.edupro.service.ClazzService;
import cn.hytc.edupro.vo.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * 班级服务实现
 */
@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzMapper clazzMapper;

    @Override
    public List<Clazz> list(String name, LocalDate begin, LocalDate end) {
        return clazzMapper.list(name, begin, end);
    }

    @Override
    public PageResult<Clazz> page(String name, LocalDate begin, LocalDate end, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Clazz> list = clazzMapper.list(name, begin, end);
        PageInfo<Clazz> pageInfo = new PageInfo<>(list);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public void deleteById(Integer id) {
        clazzMapper.deleteById(id);
    }

    @Override
    public void add(Clazz clazz) {
        clazzMapper.insert(clazz);
    }

    @Override
    public Clazz getById(Integer id) {
        return clazzMapper.getById(id);
    }

    @Override
    public void update(Clazz clazz) {
        clazzMapper.update(clazz);
    }

    @Override
    public List<Clazz> Alllist() {
        return clazzMapper.Alllist();
    }

    @Override
    public PageResult<Clazz> allPage(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Clazz> list = clazzMapper.Alllist();
        PageInfo<Clazz> pageInfo = new PageInfo<>(list);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

}