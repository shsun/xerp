package com.xerp.dao.mapper.module;

import java.util.List;

import com.xerp.controller.userreport.UserReportBean;
import com.xerp.controller.userreport.UserReportForm;

public interface UserReportMapper {

	public int selectUserReportBeanCount(UserReportForm form);

	public List<UserReportBean> selectUserReportBeanByLimit(UserReportForm form);

}