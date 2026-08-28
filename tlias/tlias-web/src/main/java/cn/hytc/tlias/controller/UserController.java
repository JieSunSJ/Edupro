package cn.hytc.tlias.controller;

import cn.hytc.tlias.entity.User;
import cn.hytc.tlias.service.UserService;
import cn.hytc.tlias.vo.Result;
import cn.hytc.tlias.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody Map<String, String> params) {
        try {
            String username = params.get("username");
            String password = params.get("password");
            Map<String, Object> result = userService.login(username, password);
            return Result.success(result);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/profile")
    public Result profile(@RequestHeader("token") String token) {
        try {
            Integer userId = getUserId(token);
            User user = userService.getById(userId);
            return Result.success(user);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/profile")
    public Result updateProfile(@RequestHeader("token") String token, @RequestBody User user) {
        try {
            Integer userId = getUserId(token);
            user.setId(userId);
            userService.updateProfile(user);
            return Result.success("修改成功");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/password")
    public Result updatePassword(@RequestHeader("token") String token, @RequestBody Map<String, String> params) {
        try {
            Integer userId = getUserId(token);
            String oldPassword = params.get("oldPassword");
            String newPassword = params.get("newPassword");
            userService.updatePassword(userId, oldPassword, newPassword);
            return Result.success("密码修改成功");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/image")
    public Result updateImage(@RequestHeader("token") String token, @RequestBody Map<String, String> params) {
        try {
            Integer userId = getUserId(token);
            userService.updateImage(userId, params.get("image"));
            return Result.success("头像更新成功");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/teachers")
    public Result getTeachers() {
        return Result.success(userService.getTeachers());
    }

    private Integer getUserId(String token) {
        Claims claims = JwtUtils.parseJWT(token);
        return claims.get("id", Integer.class);
    }
}