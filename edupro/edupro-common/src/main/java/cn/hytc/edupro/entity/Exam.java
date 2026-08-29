package cn.hytc.edupro.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exam {
    private Integer id;
    private Integer courseId;
    private String examName;
    private LocalDate examDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String room;
    private Integer examType;
    private LocalDateTime createTime;

    private String courseName;
    private String teacherName;
}