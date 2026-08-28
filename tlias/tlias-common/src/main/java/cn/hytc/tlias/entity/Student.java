package cn.hytc.tlias.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 学生实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private Integer id; //ID
    private String name; //姓名
    private String no; //序号
    private Integer gender; //性别 , 1: 男 , 2 : 女
    private String phone; //手机号
    private String address; //联系地址
    private Integer clazzId; //班级ID
    private String password; //登录密码
    private String image; //头像URL
    private Integer unenrollCount; //每学期退课次数
    private Short violationCount; //违纪次数
    private Short violationScore; //违纪扣分
    private LocalDateTime createTime; //创建时间
    private LocalDateTime updateTime; //修改时间

    private String clazzName;//班级名称
    private String clazzRoom;//班级教室
    private Integer clazzMasterId;//班主任ID
    private String clazzMaster;//班主任姓名
    private Integer clazzSubject;//班级学科
    private String clazzBegin;//开课日期
    private String clazzEnd;//结课日期
}