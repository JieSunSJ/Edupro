package cn.hytc.edupro.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    private Integer id;
    private Integer courseId;
    private Integer dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer weekStart;
    private Integer weekEnd;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private String courseName;
    private String teacherName;
    private String room;
}