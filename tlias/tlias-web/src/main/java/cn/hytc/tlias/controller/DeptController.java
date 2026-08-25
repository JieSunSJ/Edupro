package cn.hytc.tlias.controller;

import cn.hytc.tlias.entity.Dept;
import cn.hytc.tlias.service.DeptService;
import cn.hytc.tlias.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dept")
public class DeptController {
    @Autowired
    private DeptService deptService;

    @GetMapping("/list")
    public Result list() {
        return new Result(200, "success", deptService.list());
    }
}