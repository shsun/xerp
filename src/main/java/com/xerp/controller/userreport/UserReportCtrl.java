package com.xerp.controller.userreport;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import com.xerp.controller.userreport.UserReportBean;
import com.xerp.controller.userreport.UserReportForm;
import com.xerp.dao.mapper.module.UserReportMapper;

import base.utils.json.JsonResult;
import base.utils.json.JsonResultFactory;

/**
 * 员工报表
 */
@Controller
@RequestMapping("/userreport/UserReportCtrl/")
public class UserReportCtrl {

	@Autowired
	private UserReportMapper userReportMapper;
	
	
	/**
	 * 查询
	 */
	@RequestMapping(value="search")
	public @ResponseBody JsonResult search(@RequestBody UserReportForm userReportForm) throws Exception {
		int count = userReportMapper.selectUserReportBeanCount(userReportForm);
		List<UserReportBean> list = userReportMapper.selectUserReportBeanByLimit(userReportForm);
		return JsonResultFactory.extgrid(list, count);
	}
}