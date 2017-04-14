package com.xerp.dao.mapper.module;

import com.xerp.controller.userreport.UserReportBean;
import com.xerp.controller.userreport.UserReportForm;
import java.util.List;

public interface UserReportMapper {

	public int selectUserReportBeanCount(UserReportForm form);

	public List<UserReportBean> selectUserReportBeanByLimit(UserReportForm form);

}