package cn.hytc.edupro.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourse {
    private Integer id;
    private Integer studentId;
    private Integer courseId;
    private String semester;
    private LocalDateTime createTime;

    private String courseName;
    private String teacherName;
    private String room;
    private Integer subject;
    private String enrollTime;
}