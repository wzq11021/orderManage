package com.ordermanage.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ordermanage.bean.Order;
import com.ordermanage.service.IDataResolveService;
import com.ordermanage.util.ExcelUtil;
import com.ordermanage.util.JsonUtil;

@Service
public class DataResolveServiceImpl implements IDataResolveService{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public HSSFWorkbook exportExcel() throws JsonParseException, JsonMappingException, IOException {
		logger.info("-------->>>>>>>>>>>exportExcel<<<<<<<<-------------");
		
		String data = restTemplate.getForObject("http://127.0.0.1:8000/jf/", String.class);
		
		logger.info("---------->>>>>>>>data:"+data+"<<<<<<<---------------");
		
		if(!StringUtils.isNoneBlank(data)){
			return null;
		}
		
		//将json字符串转为List
		List<Map> list = JsonUtil.toList(data, Map.class);
		
		ExcelUtil excelUtil = new ExcelUtil();
		//表头
		String[] title={"序号", "合同号", "商品名称", "商品条码", "商品编码", "订单类型", "大类", "订货门店", "收货地", "销售价格", "件数", "零散数", "细数", "实收数", "单位", "场次", "订单状态", "有效截止时间", "审核人"};
		//内容
		String[][] values = new String[list.size()][title.length];
		
		List<Map<String, String>> paramList = new ArrayList<>();
		
		for(int i=0; i<list.size(); i++){
			/*Order Order = list.get(i);
			values[i][0] = String.valueOf(Order.getNo());
			values[i][1] = String.valueOf(Order.getContractCode());
			values[i][2] = String.valueOf(Order.getGoodName());
			values[i][3] = String.valueOf(Order.getGoodBarCode());
			values[i][4] = String.valueOf(Order.getGoodCode());
			values[i][5] = String.valueOf(Order.getOrderType());
			values[i][6] = String.valueOf(Order.getGoodType());
			values[i][7] = String.valueOf(Order.getOrderShop());
			values[i][8] = String.valueOf(Order.getAddress());
			values[i][9] = String.valueOf(Order.getSellPrice());
			values[i][10] = String.valueOf(Order.getCount());
			values[i][11] = String.valueOf(Order.getSimpleCount());
			values[i][12] = String.valueOf(Order.getLittleCount());
			values[i][13] = String.valueOf(Order.getRealCount());
			values[i][14] = String.valueOf(Order.getUnit());
			values[i][15] = String.valueOf(Order.getStageCount());
			values[i][16] = String.valueOf(Order.getOrderState());
			values[i][17] = String.valueOf(Order.getEndTime());
			values[i][18] = String.valueOf(Order.getOperatorName());*/
			Map<String,String> map = list.get(i);
			if(map.get("序号") != null){
				String no = String.valueOf(map.get("序号"));
				values[i][0] = no;
			}
			if(map.get("合同号") != null){
				String contractCode = String.valueOf(map.get("合同号"));
				values[i][1] = contractCode;
			}
			if(map.get("商品名称") != null){
				String goodName = String.valueOf(map.get("商品名称"));
				values[i][2] = goodName;
			}
			if(map.get("商品条码") != null){
				String goodBarCode = String.valueOf(map.get("商品条码"));
				values[i][3] = goodBarCode;
			}
			if(map.get("商品编码") != null){
				String goodCode = String.valueOf(map.get("商品编码"));
				values[i][4] = goodCode;
			}
			if(map.get("订单类型") != null){
				String orderType = String.valueOf(map.get("订单类型"));
				values[i][5] = orderType;
			}
			if(map.get("大类") != null){
				String goodType = String.valueOf(map.get("大类"));
				values[i][6] = goodType;
			}
			if(map.get("订货门店") != null){
				String orderShop = String.valueOf(map.get("订货门店"));
				values[i][7] = orderShop;
			}
			if(map.get("收货地") != null){
				String address = String.valueOf(map.get("收货地"));
				values[i][8] = address;
			}
			if(map.get("销售价格") != null){
				String sellPrice = String.valueOf(map.get("销售价格"));
				values[i][9] = sellPrice;
			}
			if(map.get("件数") != null){
				String count = String.valueOf(map.get("件数"));
				values[i][10] = count;
			}
			if(map.get("零散数") != null){
				String simpleCount = String.valueOf(map.get("零散数"));
				values[i][11] = simpleCount;
			}
			if(map.get("细数") != null){
				String littleCount = String.valueOf(map.get("细数"));
				values[i][12] = littleCount;
			}
			if(map.get("实收数") != null){
				String realCount = String.valueOf(map.get("实收数"));
				values[i][13] = realCount;
			}
			if(map.get("单位") != null){
				String unit = String.valueOf(map.get("单位"));
				values[i][14] = unit;
			}
			if(map.get("场次") != null){
				String stageCount = String.valueOf(map.get("场次"));
				values[i][15] = stageCount;
			}
			if(map.get("订单状态") != null){
				String orderState = String.valueOf(map.get("订单状态"));
				values[i][16] = orderState;
			}
			if(map.get("有效截止日期") != null){
				String endTime = String.valueOf(map.get("有效截止日期"));
				values[i][17] = endTime;
			}
			if(map.get("审核人") != null){
				String operatorName = String.valueOf(map.get("审核人"));
				values[i][18] = operatorName;
			}
		}
		return excelUtil.getExcel("订单信息", title, values);
	}

	@Override
	public List<Map<String, String>> resolveData() throws JsonParseException, JsonMappingException, IOException {
		logger.info("-------->>>>>>>>>>>resolveData<<<<<<<<-------------");
		
		String data = restTemplate.getForObject("http://127.0.0.1:8000/jf/", String.class);
		
		logger.info("---------->>>>>>>>data:"+data+"<<<<<<<---------------");
		
		if(!StringUtils.isNoneBlank(data)){
			return null;
		}
		
		//将json字符串转为List
		//List<Map> list = JSONObject.parseArray(data, Map.class);
		List<Map> list = JsonUtil.toList(data, Map.class);
		
		logger.info("------->>>data: " + JsonUtil.toJson(list));
		
		List<Map<String, String>> paramList = new ArrayList<>();
		
		for(int i=0; i<list.size(); i++){
			Map<String,String> map = list.get(i);
			Map<String,String> param = new HashMap<>();
			if(map.get("序号") != null){
				String no = String.valueOf(map.get("序号"));
				param.put("no", no);
			}
			if(map.get("合同号") != null){
				String contractCode = String.valueOf(map.get("合同号"));
				param.put("contractCode", contractCode);
			}
			if(map.get("商品名称") != null){
				String goodName = String.valueOf(map.get("商品名称"));
				param.put("goodName", goodName);
			}
			if(map.get("商品条码") != null){
				String goodBarCode = String.valueOf(map.get("商品条码"));
				param.put("goodBarCode", goodBarCode);
			}
			if(map.get("商品编码") != null){
				String goodCode = String.valueOf(map.get("商品编码"));
				param.put("goodCode", goodCode);
			}
			if(map.get("订单类型") != null){
				String orderType = String.valueOf(map.get("订单类型"));
				param.put("orderType", orderType);
			}
			if(map.get("大类") != null){
				String goodType = String.valueOf(map.get("大类"));
				param.put("goodType", goodType);
			}
			if(map.get("订货门店") != null){
				String orderShop = String.valueOf(map.get("订货门店"));
				param.put("orderShop", orderShop);
			}
			if(map.get("收货地") != null){
				String address = String.valueOf(map.get("收货地"));
				param.put("address", address);
			}
			if(map.get("销售价格") != null){
				String sellPrice = String.valueOf(map.get("销售价格"));
				param.put("sellPrice", sellPrice);
			}
			if(map.get("件数") != null){
				String count = String.valueOf(map.get("件数"));
				param.put("count", count);
			}
			if(map.get("零散数") != null){
				String simpleCount = String.valueOf(map.get("零散数"));
				param.put("simpleCount", simpleCount);
			}
			if(map.get("细数") != null){
				String littleCount = String.valueOf(map.get("细数"));
				param.put("littleCount", littleCount);
			}
			if(map.get("实收数") != null){
				String realCount = String.valueOf(map.get("实收数"));
				param.put("realCount", realCount);
			}
			if(map.get("单位") != null){
				String unit = String.valueOf(map.get("单位"));
				param.put("unit", unit);
			}
			if(map.get("场次") != null){
				String stageCount = String.valueOf(map.get("场次"));
				param.put("stageCount", stageCount);
			}
			if(map.get("订单状态") != null){
				String orderState = String.valueOf(map.get("订单状态"));
				param.put("orderState", orderState);
			}
			if(map.get("有效截止日期") != null){
				String endTime = String.valueOf(map.get("有效截止日期"));
				param.put("endTime", endTime);
			}
			if(map.get("审核人") != null){
				String operatorName = String.valueOf(map.get("审核人"));
				param.put("operatorName", operatorName);
			}
			paramList.add(param);
		}
		return paramList;
	}
}
