package cn.hytc.tlias.service.impl;

import cn.hytc.tlias.entity.User;
import cn.hytc.tlias.mapper.UserMapper;
import cn.hytc.tlias.service.UserService;
import cn.hytc.tlias.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Map<String, Object> login(String username, String password) {
        User user = userMapper.getByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户名不存在");
        }
        if (!password.equals(user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("role", user.getRole() == 1 ? "admin" : "teacher");
        String token = JwtUtils.generateJwt(claims);

        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("id", user.getId());
        result.put("name", user.getName());
        result.put("role", user.getRole());
        result.put("image", user.getImage());
        return result;
    }

    @Override
    public User getById(Integer id) {
        return userMapper.getById(id);
    }

    @Override
    @Transactional
    public void updateProfile(User user) {
        userMapper.updateProfile(user);
    }

    @Override
    @Transactional
    public void updatePassword(Integer id, String oldPassword, String newPassword) {
        User user = userMapper.getById(id);
        if (!oldPassword.equals(user.getPassword())) {
            throw new RuntimeException("原密码错误");
        }
        userMapper.updatePassword(id, newPassword);
    }

    @Override
    @Transactional
    public void updateImage(Integer id, String imageUrl) {
        userMapper.updateImage(id, imageUrl);
    }

    @Override
    public List<User> getTeachers() {
        return userMapper.getTeachers();
    }
}