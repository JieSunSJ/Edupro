package cn.hytc.tlias.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 职位统计数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOption {
    private List jobList;  // 职位名称列表
    private List dataList; // 对应人数列表
}