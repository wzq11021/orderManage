package com.ordermanage.controller;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ordermanage.service.IDataResolveService;

@RestController
@RequestMapping("/dataResolve")
public class DataResolveController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IDataResolveService dataResolverService;
	
	@PostMapping("/exportDataExcel")
	public void exportDataExcel(String data, HttpServletRequest request, HttpServletResponse response){
		logger.info("--------->>>>>>>>>exportDataExcel start<<<<<<<<<<-------------");
		try{
			String excelName = "数据信息表" +System.currentTimeMillis() +".xls";
			String fileName = new String(excelName.getBytes(),"ISO8859-1");
			
			response.reset();
			response.setHeader("Content-Dispostion", "attachment;filename="+fileName);
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			
			//获取Excel表
			HSSFWorkbook wb = dataResolverService.dataResolve(data);
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
