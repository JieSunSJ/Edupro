package cn.hytc.tlias.service.impl;


import cn.hytc.tlias.vo.JobOption;
import cn.hytc.tlias.mapper.EmpMapper;
import cn.hytc.tlias.mapper.StudentMapper;
import cn.hytc.tlias.service.ReportService;
import cn.hytc.tlias.vo.StudentClazzOption;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 报表服务实现
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public JobOption getEmpJobData() {
        List<Map<String,Object>> list = empMapper.countEmpJobData();
        List<Object> jobList = list.stream().map(dataMap -> dataMap.get("pos")).toList();
        List<Object> dataList = list.stream().map(dataMap -> dataMap.get("total")).toList();
        return new JobOption(jobList, dataList);
    }
    @Override
    public List<Map> getStudentGenderData() {
        return studentMapper.countStudentGenderData();
    }
    @Override
    public List<Map> getEmpGenderData() {
        return empMapper.countEmpGenderData();
    }
    @Override
    public List<Map> getStudentCollegeData() {
        return studentMapper.countStudentCollegeData();
    }
    @Override
    public StudentClazzOption getStudentCountData() {
        // 统计各班级学生人数，对List<Map>进行拆分
        List<Map<String, Object>> list = studentMapper.countStudentCountData();
        List<String> clazzList = new ArrayList<>(list.size());
        List<Long> dataList = new ArrayList<>(list.size());

        for (Map<String, Object> dataMap : list) {
            clazzList.add(String.valueOf(dataMap.get("name")));
            dataList.add((Long) dataMap.get("value"));
        }

        return new StudentClazzOption(clazzList, dataList);
    }
}