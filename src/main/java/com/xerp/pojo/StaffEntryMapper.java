package com.xerp.pojo;

import com.xerp.pojo.StaffEntry;

public interface StaffEntryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StaffEntry record);

    int insertSelective(StaffEntry record);

    StaffEntry selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StaffEntry record);

    int updateByPrimaryKey(StaffEntry record);
}