package com.hj.casps.entity.appQuote;

/**
 * 查询合作会员
 * Created by Admin on 2017/6/12.
 */

public class ShowMmbEntity {
    private String sys_token;//string	令牌号
    private String sys_uuid;//string	操作唯一编码（防重复提交）
    private String sys_func;//string	功能编码（用于授权检查）
    private String sys_user;//string	用户id
    private String sys_member;//	string	会员id
    private String type;//int	报价类型// 0采购// 1销售
    private String pageno;//int	页号
    private String pagesize;//	int	每页行数
    private String groupName;//	string	分组名
    private String mmbId;//	string	会员id

    public ShowMmbEntity() {
    }

    /**
     * 查询合作会员
     * @param sys_token
     * @param sys_uuid
     * @param sys_func
     * @param sys_user
     * @param sys_member
     * @param type
     * @param pageno
     * @param pagesize
     */
    public ShowMmbEntity(String sys_token, String sys_uuid,
                         String sys_func, String sys_user, String sys_member,
                         String type, String pageno, String pagesize) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.type = type;
        this.pageno = pageno;
        this.pagesize = pagesize;
    }

    /**
     * 查询群组
     * @param sys_token
     * @param sys_uuid
     * @param sys_func
     * @param sys_user
     * @param sys_member
     * @param type
     * @param pageno
     * @param pagesize
     * @param groupName
     * @param mmbId
     */
    public ShowMmbEntity(String sys_token, String sys_uuid, String sys_func, String sys_user,
                         String sys_member, String type, String pageno, String pagesize,
                         String groupName, String mmbId) {
        this.sys_token = sys_token;
        this.sys_uuid = sys_uuid;
        this.sys_func = sys_func;
        this.sys_user = sys_user;
        this.sys_member = sys_member;
        this.type = type;
        this.pageno = pageno;
        this.pagesize = pagesize;
        this.groupName = groupName;
        this.mmbId = mmbId;
    }
}
