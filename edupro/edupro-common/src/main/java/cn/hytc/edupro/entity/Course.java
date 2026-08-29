package cn.hytc.edupro.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private Integer id;
    private String name;
    private String teacherName;
    private String room;
    private Integer capacity;
    private Integer subject;
    private String description;
    private Integer status;
    private LocalDate beginDate;
    private LocalDate endDate;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private Integer enrolledCount;
}