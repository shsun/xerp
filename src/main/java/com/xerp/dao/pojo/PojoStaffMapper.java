package com.xerp.dao.pojo;

import com.xerp.dao.pojo.PojoStaff;
import com.xerp.dao.pojo.PojoStaffExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PojoStaffMapper {
    int countByExample(PojoStaffExample example);

    int deleteByExample(PojoStaffExample example);

    int deleteByPrimaryKey(Integer id);

    List<PojoStaff> selectByExample(PojoStaffExample example);

    PojoStaff selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PojoStaff record, @Param("example") PojoStaffExample example);

    int updateByExample(@Param("record") PojoStaff record, @Param("example") PojoStaffExample example);

    int updateByPrimaryKeySelective(PojoStaff record);

    int updateByPrimaryKey(PojoStaff record);
}