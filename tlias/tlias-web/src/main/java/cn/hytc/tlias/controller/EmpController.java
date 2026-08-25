package cn.hytc.tlias.controller;

import cn.hytc.tlias.entity.Emp;
import cn.hytc.tlias.vo.PageResult;
import cn.hytc.tlias.vo.Result;
import cn.hytc.tlias.service.EmpService;
import cn.hytc.tlias.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 员工管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/emp")
public class EmpController {
    @Autowired
    private EmpService empService;
    @Autowired
    private ReportService reportService;


    /** 查询所有员工 */
    @RequestMapping("/list")
    public Result list(@RequestParam(required = false) String name,
                       @RequestParam(required = false) Integer gender,
                       @RequestParam(required = false) Integer job,
                       @RequestParam(required = false) Integer deptId,
                       @RequestParam(required = false) BigDecimal salaryMin,
                       @RequestParam(required = false) BigDecimal salaryMax) {
        return new Result(200, "success", empService.list(name, gender, job, deptId, salaryMin, salaryMax));
    }

    /** 分页查询员工 */
    @RequestMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       @RequestParam(required = false) String name,
                       @RequestParam(required = false) Integer gender,
                       @RequestParam(required = false) Integer job,
                       @RequestParam(required = false) Integer deptId,
                       @RequestParam(required = false) BigDecimal salaryMin,
                       @RequestParam(required = false) BigDecimal salaryMax) {
        PageResult pageResult = empService.page(page, pageSize, name, gender, job, deptId, salaryMin, salaryMax);
        return new Result(200, "success", pageResult);
    }

    /** 添加员工 */
    @PostMapping
    public Result save(@RequestBody Emp emp) {
        empService.save(emp);
        return Result.success(emp);
    }

    /** 批量删除员工 */
    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids) {
        log.info("批量删除员工: ids={} ", ids);
        empService.deleteByIds(ids);
        return Result.success("删除成功");
    }

    /** 根据ID查询员工 */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        log.info("根据ID查询员工: id={} ", id);
        return Result.success(empService.getById(id));
    }

    /** 根据ID修改员工 */
    @PutMapping
    public Result update(@RequestBody Emp emp) {
        log.info("根据ID修改员工: emp={} ", emp);
        empService.updateById(emp);
        return Result.success("修改成功");
    }

    /** 修改密码 */
    @PutMapping("/password")
    public Result updatePassword(@RequestBody Map<String, String> params) {
        Integer id = Integer.valueOf(params.get("id"));
        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");
        log.info("修改密码: id={}", id);
        empService.updatePassword(id, oldPassword, newPassword);
        return Result.success("密码修改成功");
    }

}