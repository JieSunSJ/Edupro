package cn.hytc.edupro.controller;

import cn.hytc.edupro.service.OperateLogService;
import cn.hytc.edupro.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/logs")
public class LogController {

    @Autowired
    private OperateLogService operateLogService;

    @GetMapping
    public Result list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize) {
        Map<String, Object> data = operateLogService.list(page, pageSize);
        return Result.success(data);
    }
}