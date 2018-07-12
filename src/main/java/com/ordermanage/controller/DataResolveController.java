package com.ordermanage.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ordermanage.service.IDataResolveService;
import com.ordermanage.util.JsonUtil;
import com.ordermanage.util.ResultUtil;

@RestController
@RequestMapping("/dataResolve")
@CrossOrigin
public class DataResolveController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IDataResolveService dataResolveService;
	
	/**
	 * 解析抓取的数据格式，转为json格式
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@GetMapping("/resolveData")
	public ResultUtil resolveData(HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException{
		response.setHeader("Access-Control-Allow-Origin", "*");
		logger.info("----------->>>>>>>>>resolveData start<<<<<<<<<-------------");
		List<Map<String,String>> list = dataResolveService.resolveData();
		logger.info("----------->>>>>>>>>resolveData  end <<<<<<<<<-------------");
		ResultUtil result = new ResultUtil();
		result.setCode(0);
		result.setMsg("ok");
		result.setCount(1000);
		result.setData(list);
		return result;
	}
	/**
	 * 导出Excel
	 * @param request
	 * @param response
	 */
	@GetMapping("/exportDataExcel")
	public void exportDataExcel(HttpServletRequest request, HttpServletResponse response){
		logger.info("--------->>>>>>>>>exportDataExcel start<<<<<<<<<<-------------");
		try{
			String excelName = "数据信息表" +System.currentTimeMillis() +".xls";
			String fileName = new String(excelName.getBytes(),"ISO8859-1");
			
			response.reset();
			response.setHeader("Content-Dispostion", "attachment;filename="+fileName);
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			
			//获取Excel表
			HSSFWorkbook wb = dataResolveService.exportExcel();
			if(wb==null){
				throw new Exception("抓取数据为空");
			}
			
			//输出
			OutputStream output = response.getOutputStream();
			wb.write(output);
			output.flush();
			output.close();
		}catch(Exception e){
			logger.info("----------->>>>>>>>>>>导出Excel表，响应客户端失败<<<<<<<<<<------------");
			e.printStackTrace();
		}
		logger.info("--------->>>>>>>>>exportDataExcel end<<<<<<<<<<<<-------------");
	}
}
