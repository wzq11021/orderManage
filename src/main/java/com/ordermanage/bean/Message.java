package com.ordermanage.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 抓取数据
 * @author Tarro
 *
 */
public class Message implements Serializable{

	private static final long serialVersionUID = 746539674864690019L;
	
	private String id;
	private Integer no;//序号
	private String contractCode;//合同号
	private String goodName;//商品名称
	private String goodBarCode;//商品条码
	private String goodCode;//商品编码
	private String orderType;//订单类型
	private String goodType;//大类
	private String orderShop;//订货门店
	private String address;//收货地
	private BigDecimal sellPrice;//销售价格
	private Integer count;//件数
	private Integer simpleCount;//零散数
	private Integer littleCount;//细数
	private Integer realCount;//实收数
	private String unit;//单位
	private String stageCount;//场次
	private String orderState;//订单状态
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endTime;//有效截止时间
	private String operatorName;//审核人
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public String getGoodBarCode() {
		return goodBarCode;
	}
	public void setGoodBarCode(String goodBarCode) {
		this.goodBarCode = goodBarCode;
	}
	public String getGoodCode() {
		return goodCode;
	}
	public void setGoodCode(String goodCode) {
		this.goodCode = goodCode;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getGoodType() {
		return goodType;
	}
	public void setGoodType(String goodType) {
		this.goodType = goodType;
	}
	public String getOrderShop() {
		return orderShop;
	}
	public void setOrderShop(String orderShop) {
		this.orderShop = orderShop;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public BigDecimal getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(BigDecimal sellPrice) {
		this.sellPrice = sellPrice;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getSimpleCount() {
		return simpleCount;
	}
	public void setSimpleCount(Integer simpleCount) {
		this.simpleCount = simpleCount;
	}
	public Integer getLittleCount() {
		return littleCount;
	}
	public void setLittleCount(Integer littleCount) {
		this.littleCount = littleCount;
	}
	public Integer getRealCount() {
		return realCount;
	}
	public void setRealCount(Integer realCount) {
		this.realCount = realCount;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getStageCount() {
		return stageCount;
	}
	public void setStageCount(String stageCount) {
		this.stageCount = stageCount;
	}
	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	
}
