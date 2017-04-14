package com.xerp.controller.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import com.xerp.dao.entity.StaffEty;
import com.xerp.dao.mapper.base.StaffMapper;

import base.utils.json.JsonResult;
import base.utils.json.JsonResultFactory;

/**
 * 员工管理
 */
@Controller
@RequestMapping("/admin/StaffCtrl/")
public class StaffCtrl {

	@Autowired
	private StaffMapper staffMapper;
	
	
	/**
	 * 查询
	 */
	@RequestMapping(value="search")
	public @ResponseBody JsonResult search(@RequestBody StaffEty staffEty) throws Exception {
		int count = staffMapper.selectLimitCount(staffEty);
		List<StaffEty> list = staffMapper.selectByLimit(staffEty);
		return JsonResultFactory.extgrid(list, count);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(value="save")
	public @ResponseBody JsonResult save(@RequestBody StaffEty staffEty) throws Exception {
		if(staffEty.getId() == null) {
			staffMapper.insert(staffEty);
		}
		else {
			staffMapper.updateById(staffEty);
		}
		return JsonResultFactory.success();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="delete")
	public @ResponseBody JsonResult delete(@RequestParam("id") int id) {
		staffMapper.deleteById(id);
		return JsonResultFactory.success();
	}
	
	/**
	 * 得到详细信息
	 */
	@RequestMapping(value="getDetailInfo")
	public @ResponseBody JsonResult getDetailInfo(@RequestParam("id") int id) throws Exception {
		StaffEty staffEty = staffMapper.selectById(id);
		return JsonResultFactory.success(staffEty);
	}
	
}