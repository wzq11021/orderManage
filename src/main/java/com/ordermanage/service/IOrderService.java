package com.ordermanage.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ordermanage.bean.Order;
import com.ordermanage.constant.PageRecord;

public interface IOrderService {
	/**
	 * 导出Excel
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	HSSFWorkbook exportExcel() throws Exception ;
	/**
	 * 解析数据转换为json格式
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	List<Order> resolveData() throws Exception ;
	/**
	 * 
	 * @param queryDate
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageRecord<Order> queryOrderByCondition(String queryDate, String pageNum, String pageSize) throws Exception ;

}
