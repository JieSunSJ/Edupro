package cn.hytc.edupro.mapper;

import cn.hytc.edupro.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    User getById(Integer id);

    User getByUsername(String username);

    List<User> getTeachers();

    void updateProfile(User user);

    void updatePassword(@Param("id") Integer id, @Param("password") String password);

    void updateImage(@Param("id") Integer id, @Param("image") String image);
}