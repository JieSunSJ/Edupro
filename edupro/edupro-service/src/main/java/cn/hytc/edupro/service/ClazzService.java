package cn.hytc.edupro.service;


import cn.hytc.edupro.entity.Clazz;
import cn.hytc.edupro.vo.PageResult;

import java.time.LocalDate;
import java.util.List;

/**
 * 班级服务接口
 */
public interface ClazzService {
    List<Clazz> list(String name, LocalDate begin, LocalDate end);
    PageResult<Clazz> page(String name, LocalDate begin, LocalDate end, Integer page, Integer pageSize);
    void deleteById(Integer id);
    void add(Clazz clazz);
    Clazz getById(Integer id);
    void update(Clazz clazz);
    List<Clazz> Alllist();
    PageResult<Clazz> allPage(Integer page, Integer pageSize);
}