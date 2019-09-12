package com.cn.maitian.dev.constant;

public enum ErrorCodeEnum {
    SUCCESS(1,"10000","SUCCESS","成功"),
    ERROR(2,"11001","server is error","服务器繁忙"),
    PARAMISERROR(2,"11002","param is error","参数错误"),
    APPIDISERROR(2,"11003","appid is error","APPID不正确"),
    ORDERISVAILID(2,"11004","order is vailid","订单已失效"),
    ORDERISPAIED(2,"11005","order is paied","订单已支付"),
    ORDERNOTEXTIS(2,"11006","order not exist","订单不存在"),
    ORDERALREADYPAID(2,"11007","order already paid","订单已支付"),
    NOTREQUESTMORE(2,"11008","not request more","不能访问多次"),
    NOTREQUESTONEMINUTE(2,"11008","not request one minute","请求不是1分钟以内的"),
    ACCESSTOKENERROR(2,"99910","token  error","token无效"),
    SIGNERROR(2,"11009","sign error","无效签名"),
    NOTDREDGE(2,"11010" ,"not dredge" ,"此功能未开通" ),
    ACTIVITYISOVER(2,"11011" ,"not dredge" ,"活动已结束" ),
    ISALREADLYLOTTERY(2,"11012" ,"not dredge" ,"已经抽奖" ),
    ALREADLYANSWER(2,"11013" ,"not dredge" ,"已答题" ),
    NOTLOTTERY(2,"11014" ,"not dredge" ,"没有资格" ),
    NOUSERINFO(2,"11015" ,"not dredge" ,"用户名密码错误" ),
    USERNOTEXIST(2,"11016" ,"not dredge" ,"员工不存在" ),
    ISNOTEXCELFILE(2,"11017" ,"not dredge" ,"不是excel文件" ),
    NONLOGIN(2, "11018" ,"not dredge" ,"没有登录" );
    private int id;
    private String code;
    private String enText;
    private String cnText;
    ErrorCodeEnum(int id, String code, String enText, String cnText){
        this.id = id;
        this.code = code;
        this.enText=enText;
        this.cnText=cnText;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCnText() {
        return cnText;
    }

    public void setCnText(String cnText) {
        this.cnText = cnText;
    }

    public String getEnText() {
        return enText;
    }

    public void setEnText(String enText) {
        this.enText = enText;
    }
}
