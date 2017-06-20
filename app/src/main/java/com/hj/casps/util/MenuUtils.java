package com.hj.casps.util;

import com.hj.casps.common.Constant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/5/22.
 */

public class MenuUtils {
    public static void doBuildData(List<Bean.MenusEntity2> entity2List, List<MenusEntityExtra> extras) {

        List<XmlUtils.MenuBean> menuBean = Constant.MenuBean;
        for (Bean.MenusEntity2 item : entity2List) {
            String dircode = item.getDircode();
            if (dircode.length() == 8) { /*一级 */
                MenusEntityExtra entityExtra = new MenusEntityExtra(item);
                /*找一级的子类*/
                for (Bean.MenusEntity2 item2 : entity2List) {
                    String dircode1 = item2.getDircode(); /*所有的code 二级的长度都是12*/
                    String dircode2 = entityExtra.getEntity().getDircode(); /*一级的code*/
                    if (dircode1.length() == 12 && dircode1.startsWith(dircode2)) {
                        for (XmlUtils.MenuBean bean : menuBean) {
                            if (bean.getDircode().equals(dircode1)) {
                                entityExtra.addChild(item2);
                            }
                        }
                    }
                }
                extras.add(entityExtra);
            }
            /*判断添加 操作员管理*/
            if (dircode.equals("1021")) {
                extras.add(new MenusEntityExtra(item));
            }

        }
        /*删除其中一级分类必须要有二级分类的 一级*/
        List<String> parentIdMustHasChild = Bean.getParentIdMustHasChild(entity2List);
        for (Iterator<MenusEntityExtra> iterator = extras.iterator(); iterator.hasNext(); ) {
            MenusEntityExtra extra = iterator.next();

            if ((extra.getChidls() == null || extra.getChidls().size() == 0)
                    && parentIdMustHasChild.contains(extra.getEntity().dircode)) {
                iterator.remove();
            }
        }
    }

    public static class MenusEntityExtra {
        private Bean.MenusEntity2 entity;
        private List<Bean.MenusEntity2> chidls;

        public MenusEntityExtra(Bean.MenusEntity2 item) {
            this.entity = item;
        }

        public Bean.MenusEntity2 getEntity() {
            return entity;
        }

        public void setEntity(Bean.MenusEntity2 entity) {
            this.entity = entity;
        }

        public List<Bean.MenusEntity2> getChidls() {
            return chidls;
        }

        public void setChidls(List<Bean.MenusEntity2> chidls) {
            this.chidls = chidls;
        }

        public void addChild(Bean.MenusEntity2 item2) {
            if (chidls == null) {
                chidls = new ArrayList<>();
            }
            chidls.add(item2);
        }
    }

    public static class Bean {
        public static List<String> getParentIdMustHasChild(List<Bean.MenusEntity2> menuList) {
            List<String> lists = new ArrayList<>();
           /* lists.add("10110021");
            lists.add("10110035");
            lists.add("10110041");
            lists.add("10110051");
            lists.add("10110061");
            lists.add("10110081");
            lists.add("10110031");
            //统计报表
            lists.add("10110091");
            //担保库管理
            lists.add("10410011");
            //  融资管理
            lists.add("10410021");*/

            List<XmlUtils.MenuBean> menuBean = Constant.MenuBean;
            for (int i = 0; i < menuBean.size(); i++) {
                for (int j = 0; j < menuList.size(); j++) {
                    String dircode = menuList.get(j).getDircode();
                    String beanDirCode = menuBean.get(i).getDircode();
                    if (dircode.length() == 8 && beanDirCode.length() == 8) {
                        if (!dircode.equals("10110028"))
                            if (!(dircode.equals(beanDirCode))) {
                                lists.add(dircode);
                                continue;
                            }
                    }
                }
            }
            return lists;
        }

        private MenusEntity menus;
        /**
         * menus : {"menus":[{"dircode":"1011","dirname":"业务平台","dirnote":"各类业务活动的工作平台"},{"dircode":"10110061","dirname":"收付款管理","dirnote":"订单付款、收款、退款、收退款操作"},{"dircode":"101100610001","dirname":"银行账号管理","dirnote":"收付款所用银行账号配置"},{"dircode":"101100610002","dirname":"付款","dirnote":"根据采购订单向卖方支付货款执行的操作"},{"dircode":"101100610003","dirname":"收款","dirnote":"收到销售货款时执行的签收操作"},{"dircode":"101100610004","dirname":"退款","dirnote":"退回货款时执行的操作"},{"dircode":"101100610005","dirname":"收退款","dirnote":"收到退款时执行的签收操作"},{"dircode":"10110081","dirname":"结款单管理","dirnote":"结款单为买卖双方签署的一种合同，用法律文件形式确认一笔卖方的应收账款"},{"dircode":"101100810001","dirname":"新创建的结款单","dirnote":"等待对方确认的结款单"},{"dircode":"101100810002","dirname":"待审批结款单","dirnote":"对方创建的结款单，审查内容，如果确认即签定，也可修改内容由对方审批确认"},{"dircode":"101100810003","dirname":"执行中结款单","dirnote":"双方对缔结条款内容都确认后的结款单"},{"dircode":"101100810004","dirname":"结款单签章","dirnote":"结款双方进行线上数字签名的操作"},{"dircode":"101100810005","dirname":"登记担保资源","dirnote":"按照规定提交结款单有关资料，由平台审核登记为担保资源后以在融资中使用"}]}
         * return_code : 0
         * return_message : success
         */

        private int return_code;
        private String return_message;

        public MenusEntity getMenus() {
            return menus;
        }

        public void setMenus(MenusEntity menus) {
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

        public static class MenusEntity2 {

            /**
             * dircode : 101100810005
             * dirname : 登记担保资源
             * dirnote : 按照规定提交结款单有关资料，由平台审核登记为担保资源后以在融资中使用
             */

            private String dircode;
            private String dirname;
            private String dirnote;
            private int icon;

            public int getIcon() {
                return icon;
            }

            public void setIcon(int icon) {
                this.icon = icon;
            }

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

        public static class MenusEntity {
            /**
             * dircode : 1011
             * dirname : 业务平台
             * dirnote : 各类业务活动的工作平台
             */

            private List<MenusEntity2> menus;

            public List<MenusEntity2> getMenus() {
                return menus;
            }

            public void setMenus(List<MenusEntity2> menus) {
                this.menus = menus;
            }
        }
    }
}
