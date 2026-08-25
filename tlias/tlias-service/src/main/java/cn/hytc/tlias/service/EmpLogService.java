package cn.hytc.tlias.service;


import cn.hytc.tlias.entity.EmpLog;
import cn.hytc.tlias.vo.PageResult;

import java.util.List;

/**
 * 操作日志服务接口
 */
public interface EmpLogService {

    void insertLog(EmpLog empLog);

    List<EmpLog> list();

    PageResult<EmpLog> page(Integer page, Integer pageSize);

    void deleteById(Integer id);
}