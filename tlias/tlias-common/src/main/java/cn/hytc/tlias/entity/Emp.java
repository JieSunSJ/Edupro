package cn.hytc.tlias.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 员工实体
 */
@Data
//无参构造方法
@NoArgsConstructor
//有参构造方法
@AllArgsConstructor
public class Emp {
    private Integer id; //ID,主键
    private String username; //用户名
    private String password; //密码
    private Integer role; //角色, 1:管理员, 2:普通用户
    private String name; //姓名
    private Integer gender; //性别, 1:男, 2:女
    private String phone; //手机号
    private Integer job; //职位, 1:班主任,2:讲师,3:学工主管,4:教研主管,5:咨询师
    private BigDecimal salary; //薪资（支持小数后两位）
    private String image; //头像
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate entryDate; //入职日期
    private Integer deptId; //关联的部门ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime; //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime; //修改时间
    //关联的部门名称
    private String deptName; //部门名称
    private List<EmpExpr> exprList;
}