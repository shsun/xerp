package com.xerp.dao.tables;

import com.xerp.dao.tables.PojoDepartment;
import com.xerp.dao.tables.PojoDepartmentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PojoDepartmentMapper {
    int countByExample(PojoDepartmentExample example);

    int deleteByExample(PojoDepartmentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PojoDepartment record);

    int insertSelective(PojoDepartment record);

    List<PojoDepartment> selectByExample(PojoDepartmentExample example);

    PojoDepartment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PojoDepartment record, @Param("example") PojoDepartmentExample example);

    int updateByExample(@Param("record") PojoDepartment record, @Param("example") PojoDepartmentExample example);

    int updateByPrimaryKeySelective(PojoDepartment record);

    int updateByPrimaryKey(PojoDepartment record);
}