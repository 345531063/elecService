package com.cn.maitian.dev.model;

public class CashFlowModel extends BaseModel {
    private int payType = -1;//支付方式 1 微信 2支付宝 3 余额
    private String  payOrderId;
    private String  orderId;
    private String  payExplain;
    private String  amount;
    private int    flowType  =-1;//流水类型 1 提现 2 充值 3 转账 4 咨询 5 诊费
    private String  createTime;
    private String  comment;
    private  int    status =-1;//支付状态 0 支付完成 1 支付中 2 支付失败
    private  String balance;//当前余额
    private  String targetId;//对方比那好
    private  String targetName;//对方名称
    private  int  clientType =-1;// 0 医生端, 1 患者端
    private  int  cashFlowId;//流水编号

    public int getCashFlowId() {
        return cashFlowId;
    }

    public void setCashFlowId(int cashFlowId) {
        this.cashFlowId = cashFlowId;
    }

    public int getClientType() {
        return clientType;
    }

    public void setClientType(int clientType) {
        this.clientType = clientType;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(String payOrderId) {
        this.payOrderId = payOrderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayExplain() {
        return payExplain;
    }

    public void setPayExplain(String payExplain) {
        this.payExplain = payExplain;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getFlowType() {
        return flowType;
    }

    public void setFlowType(int flowType) {
        this.flowType = flowType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

	@Override
	public String toString() {
		return "CashFlowModel [payType=" + payType + ", payOrderId=" + payOrderId + ", orderId=" + orderId
				+ ", payExplain=" + payExplain + ", amount=" + amount + ", flowType=" + flowType + ", createTime="
				+ createTime + ", comment=" + comment + ", status=" + status + ", balance=" + balance + ", targetId="
				+ targetId + ", targetName=" + targetName + ", clientType=" + clientType + ", cashFlowId=" + cashFlowId
				+ "]";
	}
}
