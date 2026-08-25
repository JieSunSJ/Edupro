package cn.hytc.tlias.mapper;

import cn.hytc.tlias.entity.EmpExpr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 员工工作经历Mapper
 */
@Mapper
public interface EmpExprMapper {
        /**
         * 批量插入员工工作经历信息
         */
        public void insertBatch(@Param("exprList") List<EmpExpr> exprList);

    /**
     * 根据员工的ID批量删除工作经历信息
     */
    void deleteByEmpIds(@Param("empIds") List<Integer> empIds);
}