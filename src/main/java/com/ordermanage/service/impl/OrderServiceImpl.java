package com.ordermanage.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
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
import com.google.common.collect.Maps;
import com.ordermanage.bean.Order;
import com.ordermanage.constant.PageRecord;
import com.ordermanage.constant.dbSql.InsertId;
import com.ordermanage.constant.dbSql.QueryId;
import com.ordermanage.constant.dbSql.UpdateId;
import com.ordermanage.service.IOrderService;
import com.ordermanage.util.ExcelUtil;
import com.ordermanage.util.JsonUtil;

@Service
public class OrderServiceImpl extends CommonServiceImpl implements IOrderService{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public HSSFWorkbook exportExcel() throws Exception {
		logger.info("-------->>>>>>>>>>>exportExcel<<<<<<<<-------------");
		
		String data = restTemplate.getForObject("http://127.0.0.1:8000/jf/", String.class);
		
		logger.info("---------->>>>>>>>data:"+data+"<<<<<<<---------------");
		
		if(!StringUtils.isNoneBlank(data)){
			throw new Exception("抓取失败");
		}
		
		//将json字符串转为List
		List<Map> list = JsonUtil.toList(data, Map.class);
		
		ExcelUtil excelUtil = new ExcelUtil();
		//表头
		String[] title={"序号", "合同号", "商品名称", "商品条码", "商品编码", "订单类型", "大类", "订货门店", "收货地", "销售价格", "件数", "零散数", "细数", "实收数", "单位", "场次", "订单状态", "有效截止时间", "创建时间", "审核人"};
		//内容
		String[][] values = new String[list.size()][title.length];
		
		List<Order> orderList = new ArrayList<>();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
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
			Order order = new Order();
			if(map.get("序号") != null){
				String no = String.valueOf(map.get("序号"));
				values[i][0] = no;
				order.setNo(no);
			}
			if(map.get("合同号") != null){
				String contractCode = String.valueOf(map.get("合同号"));
				values[i][1] = contractCode;
				order.setContractCode(contractCode);
			}
			if(map.get("商品名称") != null){
				String goodName = String.valueOf(map.get("商品名称"));
				values[i][2] = goodName;
				order.setGoodName(goodName);
			}
			if(map.get("商品条码") != null){
				String goodBarCode = String.valueOf(map.get("商品条码"));
				values[i][3] = goodBarCode;
				order.setGoodBarCode(goodBarCode);
			}
			if(map.get("商品编码") != null){
				String goodCode = String.valueOf(map.get("商品编码"));
				values[i][4] = goodCode;
				order.setGoodCode(goodCode);
			}
			if(map.get("订单类型") != null){
				String orderType = String.valueOf(map.get("订单类型"));
				values[i][5] = orderType;
				order.setOrderType(orderType);
			}
			if(map.get("大类") != null){
				String goodType = String.valueOf(map.get("大类"));
				values[i][6] = goodType;
				order.setGoodType(goodType);
			}
			if(map.get("订货门店") != null){
				String orderShop = String.valueOf(map.get("订货门店"));
				values[i][7] = orderShop;
				order.setOrderShop(orderShop);
			}
			if(map.get("收货地") != null){
				String address = String.valueOf(map.get("收货地"));
				values[i][8] = address;
				order.setAddress(address);
			}
			if(map.get("销售价格") != null){
				String sellPrice = String.valueOf(map.get("销售价格"));
				values[i][9] = sellPrice;
				order.setSellPrice(new BigDecimal(sellPrice));
			}
			if(map.get("件数") != null){
				String count = String.valueOf(map.get("件数"));
				values[i][10] = count;
				order.setCount(Integer.valueOf(count));
			}
			if(map.get("零散数") != null){
				String simpleCount = String.valueOf(map.get("零散数"));
				values[i][11] = simpleCount;
				order.setSimpleCount(Integer.valueOf(simpleCount));
			}
			if(map.get("细数") != null){
				String littleCount = String.valueOf(map.get("细数"));
				values[i][12] = littleCount;
				order.setLittleCount(Integer.valueOf(littleCount));
			}
			if(map.get("实收数") != null){
				String realCount = String.valueOf(map.get("实收数"));
				values[i][13] = realCount;
				order.setRealCount(Integer.valueOf(realCount));
			}
			if(map.get("单位") != null){
				String unit = String.valueOf(map.get("单位"));
				values[i][14] = unit;
				order.setUnit(unit);
			}
			if(map.get("场次") != null){
				String stageCount = String.valueOf(map.get("场次"));
				values[i][15] = stageCount;
				order.setStageCount(stageCount);
			}
			if(map.get("订单状态") != null){
				String orderState = String.valueOf(map.get("订单状态"));
				values[i][16] = orderState;
				order.setOrderState(orderState);
			}
			if(map.get("有效截止日期") != null){
				String endTime = String.valueOf(map.get("有效截止日期"));
				values[i][17] = endTime;
				order.setEndTime(format.parse(endTime));
			}
			if(map.get("日期") != null){
				String createTime = String.valueOf(map.get("日期"));
				values[i][18] = createTime;
				order.setCreateTime(format.parse(createTime));
			}
			if(map.get("审核人") != null){
				String operatorName = String.valueOf(map.get("审核人"));
				values[i][19] = operatorName;
				order.setOperatorName(operatorName);
			}
			if(queryObjectByParameter(QueryId.QUERY_ORDER_BY_ID, order) == null){
				insert(InsertId.INSERT_NEW_ORDER_MESSAGE, order);
			}else{
				update(UpdateId.UPDATE_ORDER_MESSAGE, order);
			}
		}
		return excelUtil.getExcel("订单信息", title, values);
	}

	@Override
	public List<Order> resolveData() throws Exception {
		logger.info("-------->>>>>>>>>>>resolveData<<<<<<<<-------------");
		
		String data = restTemplate.getForObject("http://127.0.0.1:8000/jf/", String.class);
		
		logger.info("---------->>>>>>>>data:"+data+"<<<<<<<---------------");
		
		if(!StringUtils.isNoneBlank(data)){
			throw new Exception("抓取数据失败");
		}
		
		//将json字符串转为List
		List<Map> list = JsonUtil.toList(data, Map.class);
		
		logger.info("------->>>data: " + JsonUtil.toJson(list));
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Order> orderList = new ArrayList<>();
		for(int i=0; i<list.size(); i++){
			Map<String,String> map = list.get(i);
			Order order = new Order();
			if(map.get("订单编号") != null){
				String orderId = String.valueOf(map.get("订单编号"));
				order.setOrderId(orderId);
			}
			if(map.get("序号") != null){
				String no = String.valueOf(map.get("序号"));
				order.setNo(no);
			}
			if(map.get("合同号") != null){
				String contractCode = String.valueOf(map.get("合同号"));
				order.setContractCode(contractCode);
			}
			if(map.get("商品名称") != null){
				String goodName = String.valueOf(map.get("商品名称"));
				order.setGoodName(goodName);
			}
			if(map.get("商品条码") != null){
				String goodBarCode = String.valueOf(map.get("商品条码"));
				order.setGoodBarCode(goodBarCode);
			}
			if(map.get("商品编码") != null){
				String goodCode = String.valueOf(map.get("商品编码"));
				order.setGoodCode(goodCode);
			}
			if(map.get("订单类型") != null){
				String orderType = String.valueOf(map.get("订单类型"));
				order.setOrderType(orderType);
			}
			if(map.get("大类") != null){
				String goodType = String.valueOf(map.get("大类"));
				order.setGoodType(goodType);
			}
			if(map.get("订货门店") != null){
				String orderShop = String.valueOf(map.get("订货门店"));
				order.setOrderShop(orderShop);
			}
			if(map.get("收货地") != null){
				String address = String.valueOf(map.get("收货地"));
				order.setAddress(address);
			}
			if(map.get("销售价格") != null){
				String sellPrice = String.valueOf(map.get("销售价格"));
				order.setSellPrice(new BigDecimal(sellPrice));
			}
			if(map.get("件数") != null){
				String count = String.valueOf(map.get("件数"));
				order.setCount(Integer.valueOf(count));
			}
			if(map.get("零散数") != null){
				String simpleCount = String.valueOf(map.get("零散数"));
				order.setSimpleCount(Integer.valueOf(simpleCount));
			}
			if(map.get("细数") != null){
				String littleCount = String.valueOf(map.get("细数"));
				order.setLittleCount(Integer.valueOf(littleCount));
			}
			if(map.get("实收数") != null){
				String realCount = String.valueOf(map.get("实收数"));
				order.setRealCount(Integer.valueOf(realCount));
			}
			if(map.get("单位") != null){
				String unit = String.valueOf(map.get("单位"));
				order.setUnit(unit);
			}
			if(map.get("场次") != null){
				String stageCount = String.valueOf(map.get("场次"));
				order.setStageCount(stageCount);
			}
			if(map.get("订单状态") != null){
				String orderState = String.valueOf(map.get("订单状态"));
				order.setOrderState(orderState);
			}
			if(map.get("有效截止日期") != null){
				String endTime = String.valueOf(map.get("有效截止日期"));
				
				order.setEndTime(format.parse(endTime));
			}
			if(map.get("日期") != null){
				String createTime = String.valueOf(map.get("日期"));
				order.setCreateTime(format.parse(createTime));
			}
			if(map.get("审核人") != null){
				String operatorName = String.valueOf(map.get("审核人"));
				order.setOperatorName(operatorName);
			}
			orderList.add(order);
			
			//查询为空
			if(queryObjectByParameter(QueryId.QUERY_ORDER_BY_ID, order)==null){
				insert(InsertId.INSERT_NEW_ORDER_MESSAGE, order);
			}else{
				update(UpdateId.UPDATE_ORDER_MESSAGE, order);
			}
			
		}
		return orderList;
	}
	
	@Override
	public PageRecord<Order> queryOrderByCondition(String queryDate, String pageNum, String pageSize) throws Exception {
		Map<String, Object> map = Maps.newHashMap();
		map.put("queryDate", queryDate);
		PageRecord<Order> page = queryPageByObject(QueryId.QUERY_COUNT_ORDER_BY_CONDITION, QueryId.QUERY_ORDER_BY_CONDITION, map, pageNum, pageSize);
		return page;
	}
}
