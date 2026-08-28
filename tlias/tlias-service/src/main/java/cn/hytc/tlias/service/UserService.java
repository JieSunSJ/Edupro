package cn.hytc.tlias.service;

import cn.hytc.tlias.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    Map<String, Object> login(String username, String password);

    User getById(Integer id);

    void updateProfile(User user);

    void updatePassword(Integer id, String oldPassword, String newPassword);

    void updateImage(Integer id, String imageUrl);

    List<User> getTeachers();
}