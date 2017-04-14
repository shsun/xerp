package com.xerp.pojo;

import com.xerp.pojo.DepartmentEntry;

public interface DepartmentEntryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DepartmentEntry record);

    int insertSelective(DepartmentEntry record);

    DepartmentEntry selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DepartmentEntry record);

    int updateByPrimaryKey(DepartmentEntry record);
}