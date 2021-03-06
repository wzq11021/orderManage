package com.ordermanage.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ordermanage.service.ICommonService;
import com.ordermanage.constant.CommonValue;
import com.ordermanage.constant.PageRecord;
import com.ordermanage.dao.ICommonDao;
import com.ordermanage.util.JsonUtil;
import com.google.common.collect.Maps;

/**
 * 公共服务层实现类
 * @author Alex
 *
 */
@Service("commonService")
public class CommonServiceImpl implements ICommonService {
	
	@Autowired
	private ICommonDao dao;

	@Override
	public <T> List<T> queryListByObject(String statement, Object parameter) {
		return dao.queryListByObject(statement, parameter);
	}

	@Override
	public <T> PageRecord<T> queryPageByObject(String countStatement, String listStatement, Object parameter,
			String pageNum, String pageSize) throws Exception {
		Map<String, Object> params = Maps.newHashMap();
		if(parameter != null) {
			params = JsonUtil.toMap(JsonUtil.toJson(parameter), Object.class);			
		}
		if(StringUtils.isBlank(pageNum) || "0".equals(pageNum)) {
			pageNum = CommonValue.PAGE;
		}
		if(StringUtils.isBlank(pageSize) || "0".equals(pageSize)) {
			pageSize = CommonValue.SIZE;
		}
		/**
		 * TODO
		 * 报空指针异常 需处理
		 */
		int num = (Integer.valueOf(pageNum) - 1) * Integer.valueOf(pageSize);
		int size = Integer.valueOf(pageSize);
		params.put("pageNum", num);
		params.put("pageSize", size);
		PageRecord<T> page = new PageRecord<>();
		page.setList(dao.queryListByObject(listStatement, params));
		page.setPageTotal(dao.queryCountByObject(countStatement, params));
		page.setPageNum(num);
		page.setPageSize(size);
		return page;
	}

	@Override
	public int queryCountByObject(String statement, Object parameter) {
		return dao.queryCountByObject(statement, parameter);
	}

	@Override
	public int insert(String statement, Object parameter) {
		return dao.insert(statement, parameter);
	}

	@Override
	public int update(String statement, Object parameter) {
		return dao.update(statement, parameter);
	}

	@Override
	public int delete(String statement, Object parameter) {
		return dao.delete(statement, parameter);
	}

	@Override
	public Object queryObjectByParameter(String statement, Object parameter) {
		return dao.queryObjectByParameter(statement, parameter);
	}

}
