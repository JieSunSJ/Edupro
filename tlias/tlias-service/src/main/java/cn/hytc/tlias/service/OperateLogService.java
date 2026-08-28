package cn.hytc.tlias.service;

import cn.hytc.tlias.entity.OperateLog;
import cn.hytc.tlias.mapper.OperateLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OperateLogService {

    @Autowired
    private OperateLogMapper operateLogMapper;

    public void save(OperateLog operateLog) {
        operateLogMapper.insert(operateLog);
    }

    public Map<String, Object> list(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<OperateLog> rows = operateLogMapper.list(offset, pageSize);
        Long total = operateLogMapper.count();
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        result.put("total", total);
        return result;
    }
}