package com.ordermanage.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
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
import com.ordermanage.bean.Order;
import com.ordermanage.constant.PageRecord;
import com.ordermanage.service.IOrderService;
import com.ordermanage.util.JsonUtil;
import com.ordermanage.util.ResultUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/dataResolve")
@CrossOrigin
@Api(value = "抓取订单数据")
public class OrderController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IOrderService orderService;
	
	/**
	 * 解析抓取的数据格式，转为json格式
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws ParseException 
	 */
	@GetMapping("/resolveData")
	@ApiOperation(value = "解析抓取到的数据", httpMethod = "GET")
	public ResultUtil resolveData(HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException, ParseException{
		response.setHeader("Access-Control-Allow-Origin", "*");
		logger.info("----------->>>>>>>>>resolveData start<<<<<<<<<-------------");
		ResultUtil result = new ResultUtil();
		List<Order> list = new ArrayList<>();
		try{
			list = orderService.resolveData();
			
		}catch (Exception e){
			result.setCode(0);
			result.setMsg("数据抓取失败");
			result.setData(null);
			logger.info("------------->>>>>>>>抓取数据失败，解析数据失败<<<<<<<-------------");
			return result;
		}
		result.setCode(0);
		result.setMsg("ok");
		result.setCount(1000);
		result.setData(list);
		logger.info("----------->>>>>>>>>resolveData  end <<<<<<<<<-------------");	
		return result;
	}
	/**
	 * 导出Excel
	 * @param request
	 * @param response
	 */
	@GetMapping("/exportDataExcel")
	@ApiOperation(value = "导出抓取到的订单数据", httpMethod = "GET")
	public void exportDataExcel(HttpServletRequest request, HttpServletResponse response){
		logger.info("--------->>>>>>>>>exportDataExcel start<<<<<<<<<<-------------");
		try{
			String excelName = "数据信息表" +System.currentTimeMillis() +".xls";
			String fileName = new String(excelName.getBytes(),"ISO8859-1");
			
			response.reset();
			response.setHeader("Content-Dispostion", "attachment;filename="+fileName);
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			
			//获取Excel表
			HSSFWorkbook wb = orderService.exportExcel();
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
	/**
	 * 查询抓取数据分页显示
	 * @param queryDate
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("queryOrderByCondition")
	@ApiOperation(value = "分页查询抓取的数据", httpMethod = "GET")
	public PageRecord<Order> queryOrderByCondition(String queryDate, String pageNum, String pageSize) throws Exception{
		logger.info("---------->>>>>>>>>queryOrderByCondition start<<<<<<<<<-------------");
		PageRecord<Order> page = orderService.queryOrderByCondition(queryDate, pageNum, pageSize);
		logger.info("---------->>>>>>>>>queryOrderByCondition end<<<<<<<<<--------------");
		return page;
	}
}
