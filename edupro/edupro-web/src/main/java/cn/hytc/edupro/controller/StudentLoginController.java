package cn.hytc.edupro.controller;

import cn.hytc.edupro.entity.Student;
import cn.hytc.edupro.service.StudentService;
import cn.hytc.edupro.utils.JwtUtils;
import cn.hytc.edupro.vo.Result;
import cn.hytc.edupro.vo.StudentLoginInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/student")
public class StudentLoginController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/login")
    public Result login(@RequestBody Student student) {
        log.info("学生登录: no={}", student.getNo());
        try {
            Student s = studentService.login(student.getNo(), student.getPassword());
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", s.getId());
            claims.put("role", "student");
            String token = JwtUtils.generateJwt(claims);

            StudentLoginInfo info = new StudentLoginInfo();
            info.setId(s.getId());
            info.setName(s.getName());
            info.setNo(s.getNo());
            info.setToken(token);
            info.setImage(s.getImage());
            info.setClazzId(s.getClazzId());
            info.setClazzName(s.getClazzName());
            info.setClazzRoom(s.getClazzRoom());
            info.setClazzMaster(s.getClazzMaster());
            info.setClazzSubject(s.getClazzSubject() != null ? getSubjectName(s.getClazzSubject()) : null);
            info.setClazzBegin(s.getClazzBegin());
            info.setClazzEnd(s.getClazzEnd());
            return Result.success(info);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/profile")
    public Result profile(@RequestHeader("token") String token) {
        try {
            Map<String, Object> claims = JwtUtils.parseJWT(token);
            Integer id = (Integer) claims.get("id");
            Student student = studentService.getById(id);
            return Result.success(student);
        } catch (Exception e) {
            return Result.error("获取个人信息失败");
        }
    }

    @PutMapping("/profile")
    public Result updateProfile(@RequestHeader("token") String token, @RequestBody Student student) {
        try {
            Map<String, Object> claims = JwtUtils.parseJWT(token);
            Integer id = (Integer) claims.get("id");
            student.setId(id);
            studentService.updateProfile(student);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.error("修改失败");
        }
    }

    @PutMapping("/password")
    public Result changePassword(@RequestHeader("token") String token, @RequestBody Map<String, String> params) {
        try {
            Map<String, Object> claims = JwtUtils.parseJWT(token);
            Integer id = (Integer) claims.get("id");
            String oldPassword = params.get("oldPassword");
            String newPassword = params.get("newPassword");
            studentService.updatePassword(id, oldPassword, newPassword);
            return Result.success("密码修改成功");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("修改失败");
        }
    }

    private String getSubjectName(Integer subject) {
        switch (subject) {
            case 1: return "Java";
            case 2: return "前端";
            case 3: return "大数据";
            case 4: return "Python";
            case 5: return "测试";
            case 6: return "运维";
            default: return "未知";
        }
    }
}