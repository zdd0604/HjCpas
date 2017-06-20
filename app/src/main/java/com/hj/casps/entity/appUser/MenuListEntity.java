package com.hj.casps.entity.appUser;

import java.util.List;

/**
 * Created by Administrator on 2017/5/22.
 */

public class MenuListEntity {


    /**
     * menus : {"menus":[{"dircode":"1011","dirname":"业务平台","dirnote":"各类业务活动的工作平台"},{"dircode":"10110061","dirname":"收付款管理","dirnote":"订单付款、收款、退款、收退款操作"},{"dircode":"101100610001","dirname":"银行账号管理","dirnote":"收付款所用银行账号配置"},{"dircode":"101100610002","dirname":"付款","dirnote":"根据采购订单向卖方支付货款执行的操作"},{"dircode":"101100610003","dirname":"收款","dirnote":"收到销售货款时执行的签收操作"},{"dircode":"101100610004","dirname":"退款","dirnote":"退回货款时执行的操作"},{"dircode":"101100610005","dirname":"收退款","dirnote":"收到退款时执行的签收操作"},{"dircode":"10110081","dirname":"结款单管理","dirnote":"结款单为买卖双方签署的一种合同，用法律文件形式确认一笔卖方的应收账款"},{"dircode":"101100810001","dirname":"新创建的结款单","dirnote":"等待对方确认的结款单"},{"dircode":"101100810002","dirname":"待审批结款单","dirnote":"对方创建的结款单，审查内容，如果确认即签定，也可修改内容由对方审批确认"},{"dircode":"101100810003","dirname":"执行中结款单","dirnote":"双方对缔结条款内容都确认后的结款单"},{"dircode":"101100810004","dirname":"结款单签章","dirnote":"结款双方进行线上数字签名的操作"},{"dircode":"101100810005","dirname":"登记担保资源","dirnote":"按照规定提交结款单有关资料，由平台审核登记为担保资源后以在融资中使用"}]}
     * return_code : 0
     * return_message : success
     */

    private MenusBeanX menus;
    private int return_code;
    private String return_message;

    public MenusBeanX getMenus() {
        return menus;
    }

    public void setMenus(MenusBeanX menus) {
        this.menus = menus;
    }

    public int getReturn_code() {
        return return_code;
    }

    public void setReturn_code(int return_code) {
        this.return_code = return_code;
    }

    public String getReturn_message() {
        return return_message;
    }

    public void setReturn_message(String return_message) {
        this.return_message = return_message;
    }

    public static class MenusBeanX {
        private List<MenusBean> menus;

        public List<MenusBean> getMenus() {
            return menus;
        }

        public void setMenus(List<MenusBean> menus) {
            this.menus = menus;
        }

        public static class MenusBean {
            /**
             * dircode : 1011
             * dirname : 业务平台
             * dirnote : 各类业务活动的工作平台
             */

            private String dircode;
            private String dirname;
            private String dirnote;

            public String getDircode() {
                return dircode;
            }

            public void setDircode(String dircode) {
                this.dircode = dircode;
            }

            public String getDirname() {
                return dirname;
            }

            public void setDirname(String dirname) {
                this.dirname = dirname;
            }

            public String getDirnote() {
                return dirnote;
            }

            public void setDirnote(String dirnote) {
                this.dirnote = dirnote;
            }
        }
    }
}
