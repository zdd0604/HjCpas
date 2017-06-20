package com.hj.casps.quotes.wyt;

/**
 * Created by Administrator on 2017/5/17.
 */

public class RequestSearchQuote {
    private String sys_token;//      string	令牌号
    private String sys_uuid;//         string	操作唯一编码（防重复提交）
    private String sys_func;//   string	功能编码（用于授权检查）
    private String sys_user;//  string	用户id
    private String sys_name;//               string	用户名
    private String sys_member;//          string	会员id
    private String goodname;//        	string	商品名      (传空)
    private String actionId;//          string	关注会员主页  买卖  （传空）
    private String checkBoxId;//            string	更多选项（选择的群组的字符串）（3,2,1）
    private String pageno;//        string	页号1
    private String pagesize;//          	string	页行数12
    private String type;//
    //             0采购       1销售
    private String categoryId;//                      string	商品品类id    传空

        //请求列表参数
    public RequestSearchQuote(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_name, String sys_member, String checkBoxId, String pageno, String pagesize, String type) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_name = sys_name;
        this.sys_member = sys_member;
        this.checkBoxId = checkBoxId;
        this.pageno = pageno;
        this.pagesize = pagesize;
        this.type = type;

    }


    public RequestSearchQuote(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_name, String sys_member, String checkBoxId, String pageno, String pagesize, String type,String goodname,String categoryId,String actionId) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_name = sys_name;
        this.sys_member = sys_member;
        this.checkBoxId = checkBoxId;
        this.pageno = pageno;
        this.pagesize = pagesize;
        this.type = type;
        this.goodname=goodname;
        this.categoryId=categoryId;
        this.actionId=actionId;
    }


    @Override
    public String toString() {
        return "RequestSearchQuote{" +
                "sys_token='" + sys_token + '\'' +
                ", sys_uuid='" + sys_uuid + '\'' +
                ", sys_func='" + sys_func + '\'' +
                ", sys_user='" + sys_user + '\'' +
                ", sys_name='" + sys_name + '\'' +
                ", sys_member='" + sys_member + '\'' +
                ", goodname='" + goodname + '\'' +
                ", actionId='" + actionId + '\'' +
                ", checkBoxId='" + checkBoxId + '\'' +
                ", pageno='" + pageno + '\'' +
                ", pagesize='" + pagesize + '\'' +
                ", type='" + type + '\'' +
                ", categoryId='" + categoryId + '\'' +
                '}';
    }
}
