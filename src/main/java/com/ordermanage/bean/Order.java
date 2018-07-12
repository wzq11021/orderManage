package com.ordermanage.bean;

import java.math.BigDecimal;
import java.util.Date;

public class Order {
    private Integer id;

    private String orderId;

    private String no;

    private String contractCode;

    private String goodName;

    private String goodBarCode;

    private String goodCode;

    private String orderType;

    private String goodType;

    private String orderShop;

    private String address;

    private BigDecimal sellPrice;

    private Integer count;

    private Integer simpleCount;

    private Integer littleCount;

    private Integer realCount;

    private String unit;

    private String stageCount;

    private String orderState;

    private Date endTime;

    private Date createTime;

    private String operatorName;

    private String companyCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no == null ? null : no.trim();
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode == null ? null : contractCode.trim();
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName == null ? null : goodName.trim();
    }

    public String getGoodBarCode() {
        return goodBarCode;
    }

    public void setGoodBarCode(String goodBarCode) {
        this.goodBarCode = goodBarCode == null ? null : goodBarCode.trim();
    }

    public String getGoodCode() {
        return goodCode;
    }

    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode == null ? null : goodCode.trim();
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public String getGoodType() {
        return goodType;
    }

    public void setGoodType(String goodType) {
        this.goodType = goodType == null ? null : goodType.trim();
    }

    public String getOrderShop() {
        return orderShop;
    }

    public void setOrderShop(String orderShop) {
        this.orderShop = orderShop == null ? null : orderShop.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
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
        this.unit = unit == null ? null : unit.trim();
    }

    public String getStageCount() {
        return stageCount;
    }

    public void setStageCount(String stageCount) {
        this.stageCount = stageCount == null ? null : stageCount.trim();
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState == null ? null : orderState.trim();
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName == null ? null : operatorName.trim();
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode == null ? null : companyCode.trim();
    }
}