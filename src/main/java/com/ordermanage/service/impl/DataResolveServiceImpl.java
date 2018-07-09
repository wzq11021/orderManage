package com.ordermanage.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ordermanage.bean.Message;
import com.ordermanage.service.IDataResolveService;
import com.ordermanage.util.ExcelUtil;
import com.ordermanage.util.JsonUtil;

@Service
public class DataResolveServiceImpl implements IDataResolveService{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public HSSFWorkbook dataResolve(String data) throws JsonParseException, JsonMappingException, IOException {
		logger.info("-------->>>>>>>>>>>dataResolve<<<<<<<<-------------");
		logger.info("---------->>>>>>>>data:"+data+"<<<<<<<---------------");
		
		if(!StringUtils.isNoneBlank(data)){
			return null;
		}
		
		//将json字符串转为List对象
		List<Message> list = JsonUtil.toList(data, Message.class);
		
		ExcelUtil excelUtil = new ExcelUtil();
		//表头
		String[] title={"序号", "合同号", "商品名称", "商品条码", "商品编码", "订单类型", "大类", "订货门店", "收货地", "销售价格", "件数", "零散数", "细数", "实收数", "单位", "场次", "订单状态", "有效截止时间", "审核人"};
		//内容
		String[][] values = new String[list.size()][title.length];
		for(int i=0; i<list.size(); i++){
			Message message = list.get(i);
			values[i][0] = String.valueOf(message.getNo());
			values[i][1] = message.getContractCode();
			values[i][2] = message.getGoodName();
			values[i][3] = message.getGoodBarCode();
			values[i][4] = message.getGoodCode();
			values[i][5] = message.getOrderType();
			values[i][6] = message.getGoodType();
			values[i][7] = message.getOrderShop();
			values[i][8] = message.getAddress();
			values[i][9] = String.valueOf(message.getSellPrice());
			values[i][10] = String.valueOf(message.getCount());
			values[i][11] = String.valueOf(message.getSimpleCount());
			values[i][12] = String.valueOf(message.getLittleCount());
			values[i][13] = String.valueOf(message.getRealCount());
			values[i][14] = message.getUnit();
			values[i][15] = message.getStageCount();
			values[i][16] = message.getOrderState();
			
			//Date转为字符串
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			values[i][17] = format.format(message.getEndTime());
			
			values[i][18] = message.getOperatorName();
		}
		
		return excelUtil.getExcel("订单信息", title, values);
	}

}
