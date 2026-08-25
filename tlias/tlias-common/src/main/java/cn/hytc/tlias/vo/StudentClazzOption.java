package cn.hytc.tlias.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 班级学生人数统计
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentClazzOption {
    private List<String> clazzList; // 班级名称列表
    private List<Long> countList;   // 对应学生人数列表
}