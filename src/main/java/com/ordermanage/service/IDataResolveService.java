package com.ordermanage.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface IDataResolveService {
	/**
	 * 导出Excel
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	HSSFWorkbook exportExcel()throws JsonParseException, JsonMappingException, IOException ;
	/**
	 * 解析数据转换为json格式
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	List<Map<String, String>> resolveData() throws JsonParseException, JsonMappingException, IOException ;

}
