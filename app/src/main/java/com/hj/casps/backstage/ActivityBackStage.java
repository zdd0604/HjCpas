package com.hj.casps.backstage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.hj.casps.R;
import com.hj.casps.bankmanage.BankBillsActivity;
import com.hj.casps.bankmanage.PaymentActivity;
import com.hj.casps.bankmanage.ReceiptActivity;
import com.hj.casps.bankmanage.ReceiveRefundMoenyActivity;
import com.hj.casps.bankmanage.RefundActivity;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.commodity.ActivityManageGoods;
import com.hj.casps.common.Constant;
import com.hj.casps.cooperate.CooperateContents;
import com.hj.casps.cooperate.CooperateDirectory;
import com.hj.casps.cooperate.CooperateRequest;
import com.hj.casps.cooperate.GroupManager;
import com.hj.casps.entity.PublicArg;
import com.hj.casps.expressmanager.HarvestExpress;
import com.hj.casps.expressmanager.QuitExpress;
import com.hj.casps.expressmanager.QuitHarvestExpress;
import com.hj.casps.expressmanager.SendExpress;
import com.hj.casps.operatormanager.OperatorListActivity;
import com.hj.casps.ordermanager.BuyCart;
import com.hj.casps.overdealmanager.CheckWaitBills;
import com.hj.casps.overdealmanager.CreateSectionBills;
import com.hj.casps.overdealmanager.ExecuteBills;
import com.hj.casps.overdealmanager.RegisterAssureBills;
import com.hj.casps.protocolmanager.RequestProtocol;
import com.hj.casps.quotes.QuoteQuery;
import com.hj.casps.util.GsonTools;
import com.hj.casps.util.LogToastUtils;
import com.hj.casps.util.LogoutUtils;
import com.hj.casps.util.MenuUtils;
import com.hj.casps.util.XmlUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by YaoChen on 2017/4/17.
 */

public class ActivityBackStage extends ActivityBaseHeader2 {
    RecyclerView recyclerView;
    private Bundle bundle;
    private MenuUtils.Bean.MenusEntity menus;
    private String[][] level_0 = {{"基础设置", "商品管理"},
            {"报价管理", "报价列表"},
            {"关系管理", "业务合作会员目录", "关系会员管理", "待审批申请", "群组管理"},
            {"协议管理", "已提交合作协议", "待审批合作协议", "已完成合作协议"},
            {"订单管理", "新建订单列表", "待审批订单列表", "执行中订单列表", "采购拣单车", "销售拣单车"},
            {"收发货管理", "地址管理", "收货", "发货", "退货", "退货签收"},
            {"收付款管理", "银行账号", "付款", "收款", "退款", "收退款"},
            {"结款单管理", "新建结款单", "待审批结款单", "执行中结款单", "登记担保资源"},
            {"操作员管理", "操作员列表", "操作员添加", "修改密码"}};
    private int img[] = {R.mipmap.icon_t1, R.mipmap.icon_t1,
            R.mipmap.icon_t2, R.mipmap.icon_t3,
            R.mipmap.icon_t4, R.mipmap.icon_t5,
            R.mipmap.icon_t6, R.mipmap.icon_t7,
            R.mipmap.icon_t8, R.mipmap.icon_t9};
    private List<MenuUtils.MenusEntityExtra> menuList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backstage);
        setTitle(getString(R.string.app_name_big));

        TextView tv_name = (TextView) findViewById(R.id.backstage_name);
        tv_name.setText(publicArg.getSys_username());

        //网络请求用户所对应的菜单数据
        if (hasInternetConnected()) {
            if (initMenuData()) return;
            initView();

        } else {
            if (initMenuData()) return;
            initView();
        }


    }

    private boolean initMenuData() {
        List<MenuUtils.MenusEntityExtra> menuList = Constant.MenuList;
        List<XmlUtils.MenuBean> menuBean = Constant.MenuBean;
        //去基础数据里面查询是否需要渲染的界面数据
        if (menuList == null || menuList.size() <= 0) {
            toastSHORT("获取系统菜单失败");
            return true;
        }
        handMenuData(menuList, menuBean);
//        handMenuData2(menuList, menuBean);
        this.menuList = menuList;
        return false;
    }

    //从基础数据表里面查询是否需要图片
    private void handMenuData(List<MenuUtils.MenusEntityExtra> menuList, List<XmlUtils.MenuBean> menuBean) {
        if (menuList == null || !(menuList.size() > 0)) {
            toastSHORT("获取初始化菜单失败");
            return;
        }
        for (int i = 0; i < menuBean.size(); i++) {
            for (int j = 0; j < menuList.size(); j++) {
                if (menuList.get(j).getEntity().getDircode().equals(menuBean.get(i).getDircode())) {
                    MenuUtils.Bean.MenusEntity2 entity = menuList.get(j).getEntity();
                    entity.setIcon(Integer.parseInt(menuBean.get(i).getIcon()));
                }
            }
        }
    }



    private void initView() {
        bundle = new Bundle();
        recyclerView = (RecyclerView) findViewById(R.id.backstage_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<LevelEntity> data = generateData();
        recyclerView.setAdapter(new BaseListNavAdapter(data));
    }

        //加载目录数据
    private ArrayList<LevelEntity> generateData() {
        ArrayList<LevelEntity> res = new ArrayList<>();

        for (int i = 0; i < menuList.size(); i++) {
            LevelEntity lv0 = new LevelEntity(BaseListNavAdapter.TYPE_LEVEL_0, menuList.get(i).getEntity().getDircode(), img[menuList.get(i).getEntity().getIcon()], menuList.get(i).getEntity().getDirname());
            if (menuList.get(i).getChidls() != null && menuList.get(i).getChidls().size() > 0) {
                for (int j = 0; j < menuList.get(i).getChidls().size(); j++) {
                    LevelEntity lv1 = new LevelEntity(BaseListNavAdapter.TYPE_LEVEL_1, menuList.get(i).getChidls().get(j).getDircode(), menuList.get(i).getChidls().get(j).getDirname());
                    lv0.addSubItem(lv1);
                }
            }
            res.add(lv0);
        }
        return res;
    }
 /*   private ArrayList<LevelEntity> generateData() {
        ArrayList<LevelEntity> res = new ArrayList<>();
        for (int i = 0; i < level_0.length; i++) {
            LevelEntity lv0 = new LevelEntity(BaseListNavAdapter.TYPE_LEVEL_0, level_0[i][0], img[i]);
            for (int j = 1; j < level_0[i].length; j++) {
                LevelEntity lv1 = new LevelEntity(BaseListNavAdapter.TYPE_LEVEL_0, level_0[i][j]);
                lv0.addSubItem(lv1);
            }
            res.add(lv0);
        }
        return res;
    }*/

    private class BaseListNavAdapter extends BaseMultiItemQuickAdapter<LevelEntity, BaseViewHolder> {
        public static final int TYPE_LEVEL_0 = 0;
        public static final int TYPE_LEVEL_1 = 1;

        public BaseListNavAdapter(List<LevelEntity> data) {
            super(data);
            addItemType(TYPE_LEVEL_0, R.layout.activity_baseheader_left_item1);
            addItemType(TYPE_LEVEL_1, R.layout.activity_baseheader_left_item2);
        }

        @Override
        protected void convert(BaseViewHolder holder, LevelEntity item) {
            switch (holder.getItemViewType()) {
                case TYPE_LEVEL_0:
                    convertParent(holder, item);
                    break;
                case TYPE_LEVEL_1:
                    convertChild(holder, item);
                    break;
            }
        }


        private void convertParent(final BaseViewHolder holder, final LevelEntity lv0) {

            holder.setText(R.id.item_name, lv0.getName());
            holder.setVisible(R.id.item_img, true);
            holder.setImageResource(R.id.item_img, lv0.getId());
//            holder.setTextColor(R.id.item_name, lv0.isExpanded() ?
//                    mContext.getResources().getColor(R.color.blue) :
//                    mContext.getResources().getColor(R.color.black));
            holder.setImageResource(R.id.item_iv, lv0.isExpanded() ? R.mipmap.jt3 : R.mipmap.jt1);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!lv0.hasSubItem()) {
                        //跳转
                        jumpActivity(lv0.getDircode(), ActivityBackStage.this);
                    } else {
                        int pos = holder.getAdapterPosition();
                        LogToastUtils.log(getClass().getSimpleName(), "convertParent:" + pos);
                        if (lv0.isExpanded()) {
                            collapse(pos);
                        } else {
                            //*要先伸开，否则此位置就不对应了*//*
                            expand(pos);
                            int lastExpand = -1;
                            for (int i = getHeaderLayoutCount(); i < getItemCount(); i++) {
                                LevelEntity entity = getItem(i - getHeaderLayoutCount());
                                if (entity.isExpanded() && entity != lv0) {
                                    lastExpand = i;
                                    break;
                                }
                            }
                            if (lastExpand > -1) {
                                collapse(lastExpand);
                            }
                        }
                    }
                }
            });


        }

        private void convertChild(final BaseViewHolder holder, final LevelEntity lv1) {
            holder.setText(R.id.item_name, lv1.getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getAdapterPosition();
                    jumpActivity(lv1.getDircode(), ActivityBackStage.this);
                }
            });
        }

    }

    /**
     * ActivityBaseHeader new ActivityBackStage().jumpActivity(levelEntity.getName(), this);
     * 跳转这么写
     * bundle.putInt(Constant.BUNDLE_TYPE, Constant.BANK_BILLS_ACTIVITY_TYPE);
     * intentActivity(BankBillsActivity.class, bundle);
     * bundle会为空的
     *
     * @param name
     * @param context
     */

    public static void jumpActivity(String name, Context context) {
        Bundle bundle = new Bundle();
        if (bundle == null) {
            bundle = new Bundle();
        }
        switch (name) {
//            case "商品管理":
            case "101100210001":
                Constant.SYS_FUNC="101100210001";
                context.startActivity(new Intent(context, ActivityManageGoods.class));
//                context.startActivity(new Intent(context, ActivityGoodsClass.class));
                break;
//            case "报价列表":
            case "10110028":
                Constant.SYS_FUNC="10110028";
                context.startActivity(new Intent(context, QuoteQuery.class));
                break;
//            case "新建结款单":
            case "101100810001":
                Constant.SYS_FUNC="101100810001";
                context.startActivity(new Intent(context, CreateSectionBills.class));
                break;
//            case "待审批结款单":
            case "101100810002":
                Constant.SYS_FUNC="101100810002";
                context.startActivity(new Intent(context, CheckWaitBills.class));
                break;
//            case "执行中结款单":
            case "101100810003":
                Constant.SYS_FUNC="101100810003";
                context.startActivity(new Intent(context, ExecuteBills.class));
                break;
//            case "登记担保资源":
            case "101100810005":
                Constant.SYS_FUNC="101100810005";
                context.startActivity(new Intent(context, RegisterAssureBills.class));
                break;
//            case "银行账号":
            case "101100610001":
                Constant.SYS_FUNC="101100610001";
                bundle.putInt(Constant.BUNDLE_TYPE, Constant.BANK_BILLS_ACTIVITY_TYPE);
                context.startActivity(initActivity(context, BankBillsActivity.class, bundle));
                break;
//            case "地址管理":
            case "101100510001":
                Constant.SYS_FUNC="101100510001";
                bundle.putInt(Constant.BUNDLE_TYPE, Constant.ECPRESS_ADDRESS_ACTIVITY_TYPE);
                context.startActivity(initActivity(context, BankBillsActivity.class, bundle));
                break;
//            case "收货":
            case "101100510002":
                Constant.SYS_FUNC="101100510002";
                context.startActivity(new Intent(context, HarvestExpress.class));
                break;
//            case "发货":
            case "101100510003":
                Constant.SYS_FUNC="101100510003";
                context.startActivity(new Intent(context, SendExpress.class));
                break;
//            case "退货":
            case "101100510004":
                Constant.SYS_FUNC="101100510004";
                context.startActivity(new Intent(context, QuitExpress.class));
                break;
//            case "退货签收":
            case "101100510005":
                Constant.SYS_FUNC="101100510005";
                context.startActivity(new Intent(context, QuitHarvestExpress.class));
                break;

//            case "付款":
            case "101100610002":
                Constant.SYS_FUNC="101100610002";
                context.startActivity(new Intent(context, PaymentActivity.class));
                break;
//            case "收款":
            case "101100610003":
                Constant.SYS_FUNC="101100610003";
                context.startActivity(new Intent(context, ReceiptActivity.class));
                break;
//            case "退款":
            case "101100610004":
                Constant.SYS_FUNC="101100610004";
                context.startActivity(new Intent(context, RefundActivity.class));
                break;
//            case "收退款":
            case "101100610005":
                Constant.SYS_FUNC="101100610005";
                context.startActivity(new Intent(context, ReceiveRefundMoenyActivity.class));
                break;

//            case "操作员列表":
            case "1021":
                Constant.SYS_FUNC="1021";
                context.startActivity(new Intent(context, OperatorListActivity.class));
                break;
//            case "业务合作会员目录":
            case "101100310001":
                Constant.SYS_FUNC="101100310001";
                context.startActivity(new Intent(context, CooperateDirectory.class));
                break;
//            case "关系会员 管理"(目录):
            case "101100310002":
                Constant.SYS_FUNC="101100310002";
                context.startActivity(new Intent(context, CooperateContents.class));
                break;
//            case "待审批申请":
            case "101100310003":
                Constant.SYS_FUNC="101100310003";
                bundle.putInt(Constant.PROTOCOL_TYPE, Constant.protocol_0);
                context.startActivity(new Intent(context, CooperateRequest.class).putExtras(bundle));
                break;
//            case "群组管理":
            case "101100310004":
                Constant.SYS_FUNC="101100310004";
                bundle.putInt(Constant.PROTOCOL_TYPE, Constant.protocol_0);
                context.startActivity(new Intent(context, GroupManager.class).putExtras(bundle));
                break;
//            case "搜索供应商":
            case "101100310011":
                Constant.SYS_FUNC="101100310011";
                bundle.putInt(Constant.PROTOCOL_TYPE, Constant.protocol_2);
                context.startActivity(new Intent(context, GroupManager.class).putExtras(bundle));
                break;
//            case "谁在关注我":
            case "101100310012":
                Constant.SYS_FUNC="101100310012";
                bundle.putInt(Constant.PROTOCOL_TYPE, Constant.protocol_1);
                context.startActivity(new Intent(context, GroupManager.class).putExtras(bundle));
                break;
//            case "已提交合作协议":
            case "101100350003":
                Constant.SYS_FUNC="101100350003";
                bundle.putInt(Constant.PROTOCOL_TYPE, Constant.protocol_0);
                bundle.putInt(Constant.FRA_TYPE, Constant.fra_1);
                context.startActivity(new Intent(context, RequestProtocol.class).putExtras(bundle));
                break;
//            case "待审批合作协议":
            case "101100350004":
                Constant.SYS_FUNC="101100350004";
                bundle.putInt(Constant.PROTOCOL_TYPE, Constant.protocol_1);
                bundle.putInt(Constant.FRA_TYPE, Constant.fra_1);
                context.startActivity(new Intent(context, RequestProtocol.class).putExtras(bundle));
                break;
//            case "执行中合作协议":
            case "101100350005":
                Constant.SYS_FUNC="101100350005";
                bundle.putInt(Constant.PROTOCOL_TYPE, Constant.protocol_2);
                bundle.putInt(Constant.FRA_TYPE, Constant.fra_1);
                context.startActivity(initActivity(context, RequestProtocol.class, bundle));
                break;
//            case "采购拣单车":
            case "101100410002":
                Constant.SYS_FUNC="101100410002";
                bundle.putInt(Constant.ORDER_TYPE, Constant.order_type_buy);
                context.startActivity(new Intent(context, BuyCart.class).putExtras(bundle));
                break;
//            case "销售拣单车":
            case "101100410003":
                Constant.SYS_FUNC="101100410003";
                bundle.putInt(Constant.ORDER_TYPE, Constant.order_type_sell);
                context.startActivity(initActivity(context, BuyCart.class, bundle));
                break;
//            case "新建订单列表":
            case "101100410004":
                Constant.SYS_FUNC="101100410004";
                bundle.putInt(Constant.PROTOCOL_TYPE, Constant.protocol_0);
                bundle.putInt(Constant.FRA_TYPE, Constant.fra_2);
                context.startActivity(initActivity(context, RequestProtocol.class, bundle));
                break;
//            case "待审批订单列表":
            case "101100410005":
                Constant.SYS_FUNC="101100410005";
                bundle.putInt(Constant.PROTOCOL_TYPE, Constant.protocol_1);
                bundle.putInt(Constant.FRA_TYPE, Constant.fra_2);
                context.startActivity(initActivity(context, RequestProtocol.class, bundle));
                break;
//            case "执行中订单列表":
            case "101100410006":
                Constant.SYS_FUNC="101100410006";
                bundle.putInt(Constant.PROTOCOL_TYPE, Constant.protocol_2);
                bundle.putInt(Constant.FRA_TYPE, Constant.fra_2);
                context.startActivity(initActivity(context, RequestProtocol.class, bundle));
                break;

        }
    }

    /**
     * 侧滑跳转至重复的页面
     *
     * @param acontext
     * @param c
     * @param bundle
     * @return
     */
    private static Intent initActivity(Context acontext, Class c, Bundle bundle) {
        Intent intent = new Intent(acontext, c);
        intent.putExtras(bundle);
        return intent;
    }

    private class LevelEntity extends AbstractExpandableItem implements MultiItemEntity {
        private String name;
        private int type;
        private String dircode;

        public String getDircode() {
            return dircode;
        }

        public void setDircode(String dircode) {
            this.dircode = dircode;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        private int id;

        /**
         * TYPE_LEVEL_PARENT = 1;
         * TYPE_LEVEL_CHILD = 2;
         *
         * @param type 只能为
         */
        public LevelEntity(int type) {
            this.type = type;
        }

        public LevelEntity(int type, String string) {
            this.type = type;
            this.name = string;
        }


        public LevelEntity(int type, String dircode, String name) {
            this.name = name;
            this.type = type;
            this.dircode = dircode;
        }

        public LevelEntity(int type, String string, int id) {
            this.type = type;
            this.name = string;
            this.id = id;

        }

        public LevelEntity(int type, String dircode, int id, String name) {
            this.type = type;
            this.dircode = dircode;
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof LevelEntity)) return false;

            LevelEntity that = (LevelEntity) o;

            if (id != that.id) return false;
            return name.equals(that.name);

        }

        @Override
        public int hashCode() {
            int result = name.hashCode();
            result = 31 * result + id;
            return result;
        }

        public String getName() {
            return name;
        }


        @Override
        public int getLevel() {
            return hasSubItem() ? BaseListNavAdapter.TYPE_LEVEL_0 : BaseListNavAdapter.TYPE_LEVEL_1;
        }

        @Override
        public int getItemType() {
            return this.type;
        }
    }

}
