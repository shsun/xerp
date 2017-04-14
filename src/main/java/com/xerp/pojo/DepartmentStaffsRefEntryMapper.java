package com.xerp.pojo;

import com.xerp.pojo.DepartmentStaffsRefEntry;

public interface DepartmentStaffsRefEntryMapper {
    int deleteByPrimaryKey(Integer ID);

    int insert(DepartmentStaffsRefEntry record);

    int insertSelective(DepartmentStaffsRefEntry record);

    DepartmentStaffsRefEntry selectByPrimaryKey(Integer ID);

    int updateByPrimaryKeySelective(DepartmentStaffsRefEntry record);

    int updateByPrimaryKey(DepartmentStaffsRefEntry record);
}