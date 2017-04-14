package com.xerp.pojo;

import com.xerp.pojo.PojoStaff;
import com.xerp.pojo.PojoStaffExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PojoStaffMapper {
    int countByExample(PojoStaffExample example);

    int deleteByExample(PojoStaffExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PojoStaff record);

    int insertSelective(PojoStaff record);

    List<PojoStaff> selectByExample(PojoStaffExample example);

    PojoStaff selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PojoStaff record, @Param("example") PojoStaffExample example);

    int updateByExample(@Param("record") PojoStaff record, @Param("example") PojoStaffExample example);

    int updateByPrimaryKeySelective(PojoStaff record);

    int updateByPrimaryKey(PojoStaff record);
}