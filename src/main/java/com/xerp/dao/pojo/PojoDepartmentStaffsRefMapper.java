package com.xerp.dao.pojo;

import com.xerp.dao.pojo.PojoDepartmentStaffsRef;
import com.xerp.dao.pojo.PojoDepartmentStaffsRefExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PojoDepartmentStaffsRefMapper {
    int countByExample(PojoDepartmentStaffsRefExample example);

    int deleteByExample(PojoDepartmentStaffsRefExample example);

    int deleteByPrimaryKey(Integer ID);

    int insert(PojoDepartmentStaffsRef record);

    int insertSelective(PojoDepartmentStaffsRef record);

    List<PojoDepartmentStaffsRef> selectByExample(PojoDepartmentStaffsRefExample example);

    PojoDepartmentStaffsRef selectByPrimaryKey(Integer ID);

    int updateByExampleSelective(@Param("record") PojoDepartmentStaffsRef record, @Param("example") PojoDepartmentStaffsRefExample example);

    int updateByExample(@Param("record") PojoDepartmentStaffsRef record, @Param("example") PojoDepartmentStaffsRefExample example);

    int updateByPrimaryKeySelective(PojoDepartmentStaffsRef record);

    int updateByPrimaryKey(PojoDepartmentStaffsRef record);
}