package com.hj.casps.base;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.hj.casps.R;
import com.hj.casps.commodity.ActivityEditGoods;
import com.hj.casps.commodity.ActivityManageGoods;
import com.hj.casps.commodity.ActivityPictureSearch;
import com.hj.casps.commodity.SelectClass;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.PublicArg;
import com.hj.casps.entity.goodsmanager.goodsmanagerCallBack.GoodsCategoryCallBack;
import com.hj.casps.entity.goodsmanager.request.RequestPub;
import com.hj.casps.entity.goodsmanager.response.GoodsCategoryEntity;
import com.hj.casps.entity.goodsmanager.response.NoteEntity;
import com.hj.casps.quotes.CreateQuotes;
import com.hj.casps.reception.ActivityPriceSearch;
import com.hj.casps.util.GsonTools;
import com.hj.casps.util.LogToastUtils;
import com.hj.casps.util.LogoutUtils;
import com.hj.casps.util.MenuUtils;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import mehdi.sakout.fancybuttons.FancyButton;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by YaoChen Administrator on 2017/4/14.
 * 报价检索对应的侧边栏
 */

public class QuotePriceNavLeftFragment extends FragmentBase {

    private Unbinder butterKnife;
    @BindView(R.id.base_header_left_rv)
    RecyclerView recyclerView;
    @BindView(R.id.base_header_left_cart_num)
    TextView cartNumTv;
    @BindView(R.id.base_header_left_user_name_tv)
    TextView userNameTv;
    @BindView(R.id.base_header_left_user_iv)
    ImageView userAvatorIv;
    @BindView(R.id.base_nav_menu_bt1)
    FancyButton bt1;
    @BindView(R.id.base_nav_menu_bt2)
    FancyButton bt2;
    @BindView(R.id.base_nav_menu_bt3)
    FancyButton bt3;
    BaseLeftListener listener;
    private List<NoteEntity> categoryList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_basehead_left_fragment_layout, container, false);
        butterKnife = ButterKnife.bind(this, rootView);
        userNameTv.setText(Constant.publicArg.getSys_username());
        initData();
        return rootView;
    }

    //初始化数据
    private void initData() {
        PublicArg p = Constant.publicArg;
        RequestPub r = new RequestPub(p.getSys_token(), Constant.getUUID(), Constant.SYS_FUNC, p.getSys_user(), p.getSys_member());
        String param = GsonTools.createGsonString(r);
        OkGo.post(Constant.GetAllCategoryUrl)
                .params("param", param)
                .execute(new GoodsCategoryCallBack<GoodsCategoryEntity<List<NoteEntity>>>() {
                    @Override
                    public void onSuccess(GoodsCategoryEntity<List<NoteEntity>> entity, Call call, Response response) {
                        super.onSuccess(entity, call, response);
                        if (entity != null && entity.return_code == 0&&entity.categoryList!=null) {
                            categoryList = entity.categoryList;
                            setData();
                        } else {
                            toast(entity.return_message);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        toastSHORT(e.getMessage());
                        if (Constant.public_code){
                            LogoutUtils.exitUser(QuotePriceNavLeftFragment.this);
                        }
                    }
                });


    }

    @OnClick({R.id.base_header_left_cart_layout, R.id.base_header_left_exit, R.id.base_header_back,
            R.id.base_header_left_user_iv, R.id.base_header_left_user_name_tv})
    void onSimpleClick(View v) {
        if (listener != null) {
            final int vId = v.getId();
            if (vId == R.id.base_header_left_cart_layout) {
                listener.onCartClick(v);
            } else if (vId == R.id.base_header_left_exit) {
                listener.onExitClick(v);
            } else if (vId == R.id.base_header_back) {
                listener.onBackSysClick(v);
            } else if (vId == R.id.base_header_left_user_iv ||
                    vId == R.id.base_header_left_user_name_tv) {
                listener.onUserClick(v);
            }

        }
    }

    @OnClick({R.id.base_nav_menu_bt1, R.id.base_nav_menu_bt2, R.id.base_nav_menu_bt3})
    void onBtClick(View v) {
        if (listener != null) {
            final int vId = v.getId();
            int index = 0;
            if (vId == R.id.base_nav_menu_bt1) {
                index = 0;
            } else if (vId == R.id.base_nav_menu_bt2) {
                index = 1;
            } else if (vId == R.id.base_nav_menu_bt3) {
                index = 2;
            }
            listener.onBtClick(v, index);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        butterKnife.unbind();
    }

    private ArrayList<GoodLevelEntity> generateData() {
        ArrayList<GoodLevelEntity> res = new ArrayList<>();
//        if(entityList!=null&&entityList.size()>0)
        if(categoryList==null||!(categoryList.size()>0)){
            toastSHORT("获取平台分类数据错误");
            new Exception("获取平台分类数据错误");
        }
        for (int i = 0; i < categoryList.size(); i++) {
            GoodLevelEntity lv0 = new GoodLevelEntity(categoryList.get(i).getCategoryName(), categoryList.get(i).getCategoryId());
            for (int j = 0; j < categoryList.get(i).getNodes().size(); j++) {
                GoodLevelEntity lv1 = new GoodLevelEntity(categoryList.get(i).getNodes().get(j).getCategoryName(), categoryList.get(i).getNodes().get(j).getCategoryId());
                for (int x = 0; x < categoryList.get(i).getNodes().get(j).getNodes().size(); x++) {
                    GoodLevelEntity lv2 = new GoodLevelEntity(categoryList.get(i).getNodes().get(j).getNodes().get(x).getCategoryName(), categoryList.get(i).getNodes().get(j).getNodes().get(x).getCategoryId());
                    lv1.addSubItem(lv2);
                }
                lv0.addSubItem(lv1);
            }
            res.add(lv0);
        }
        return res;
    }



    //初始化菜单数据
    private ArrayList<GoodLevelEntity> generateData2() {
        ArrayList<GoodLevelEntity> res = new ArrayList<>();
        for (int i = 0; i < categoryList.size(); i++) {
            NoteEntity noteEntity = categoryList.get(i);
            GoodLevelEntity lv0 = new GoodLevelEntity(
                    noteEntity.getCategoryName(), noteEntity.getCategoryId());
            res.add(lv0);
            resolveData(lv0, noteEntity);
        }
        return res;
    }

    /**
     * noteEntity的子类添加到lv0的结构中
     *
     * @param lv0
     * @param noteEntity
     * @return
     */
    private void resolveData(GoodLevelEntity lv0, NoteEntity noteEntity) {
        if (noteEntity.getNodes() != null && noteEntity.getNodes().size() > 0) {
            for (int i = 0; i < noteEntity.getNodes().size(); i++) {
                NoteEntity itemNoteEntity=noteEntity.getNodes().get(i);
                GoodLevelEntity goodEntity = new GoodLevelEntity(
                        itemNoteEntity.getCategoryName(), itemNoteEntity.getCategoryId());
                lv0.addSubItem(goodEntity);
                resolveData(goodEntity,itemNoteEntity);
            }
        }
    }
 void setData(BaseLeftViewData data, List<GoodLevelEntity> listEntity) {

    }

   void setData(){
       recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
       recyclerView.setAdapter(new GoodListAdapter(generateData2()));
       //如果数据为空，就跳到添加数据页面
       recyclerView.addItemDecoration(new GoodDividerItemDecoration((ActivityBaseHeader3)(getActivity())));
       recyclerView.addOnItemTouchListener(new GoodSimpleItemClick());
   }
    void setCartNun(int num) {
        cartNumTv.setText(num + "");
    }

    public class BaseLeftViewData {
        private String useravator;
        private String userName;
        private String bt1Str;
        private String bt2Str;
        private String bt3Str;

        public void setUseravator(String useravator) {
            this.useravator = useravator;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public void setBt1Str(String bt1Str) {
            this.bt1Str = bt1Str;
        }

        public void setBt2Str(String bt2Str) {
            this.bt2Str = bt2Str;
        }

        public void setBt3Str(String bt3Str) {
            this.bt3Str = bt3Str;
        }
    }

    void setListener(BaseLeftListener listener) {
        this.listener = listener;
    }

    interface BaseLeftListener {
        /*购物车*/
        void onCartClick(View v);

        /*退出*/
        void onExitClick(View v);

        /*返回系统*/
        void onBackSysClick(View v);

        /*用户头像姓名点击事件*/
        void onUserClick(View v);

        /*列表中点击事件*/
        void onListItemClick(GoodLevelEntity levelEntity);

        /*采购 等事件*/
        void onBtClick(View v, int index);
    }

    private class GoodListAdapter extends BaseMultiItemQuickAdapter<GoodLevelEntity, BaseViewHolder> {
        public static final int TYPE_LEVEL_PARENT = 0; /*有子类*/
        public static final int TYPE_LEVEL_CHILD = 1;/*无子类*/
        float paddingLeftUnit;

        public float getPaddingLeftUnit() {
            return paddingLeftUnit;
        }

        public void setPaddingLeftUnit(float paddingLeftUnit) {
            this.paddingLeftUnit = paddingLeftUnit;
        }

        public GoodListAdapter(List<GoodLevelEntity> data) {
            super(data);
            paddingLeftUnit = getResources().getDisplayMetrics().density * 20;
            addItemType(TYPE_LEVEL_PARENT, R.layout.activity_manager_good_item);
            addItemType(TYPE_LEVEL_CHILD, R.layout.activity_manager_good_item);
        }

        @Override
        protected void convert(BaseViewHolder holder, GoodLevelEntity item) {
            switch (holder.getItemViewType()) {
                case TYPE_LEVEL_PARENT:
                    convertLevelParent(holder, item);
                    break;
                case TYPE_LEVEL_CHILD:
                    convertLevelChild(holder, item);
                    break;
            }
        }

        /*有子列的*/
        private void convertLevelParent(final BaseViewHolder holder, final GoodLevelEntity entity) {
            holder.setText(R.id.item_name, entity.getName());
            holder.getView(R.id.item_name).setPadding((int) (entity.getLevel() * paddingLeftUnit), 0, 0, 0);
            holder.getView(R.id.item_iv).setVisibility(View.VISIBLE);
            holder.setImageResource(R.id.item_iv, entity.isExpanded() ? R.mipmap.jt3 : R.mipmap.jt1);
        }


        /*无子列的*/
        private void convertLevelChild(final BaseViewHolder holder, final GoodLevelEntity entity) {
            holder.setText(R.id.item_name, entity.getName());
            holder.getView(R.id.item_iv).setVisibility(View.GONE);
            holder.getView(R.id.item_name).setPadding((int) (entity.getLevel() * paddingLeftUnit), 0, 0, 0);
        }

    }

    /**
     * 左侧导航的适配器
     */
    public class GoodLevelEntity extends AbstractExpandableItem<GoodLevelEntity> implements MultiItemEntity {
        private String name;
        private GoodLevelEntity parent;
        private double i = Math.random();
        private String categoryId;

        public GoodLevelEntity(String name, String categoryId) {
            this.name = name;
            this.categoryId = categoryId;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public double getI() {
            return i;
        }

        public void setI(int i) {
            this.i = Math.random();
        }

        /**
         * @param string
         */
        public GoodLevelEntity(String string) {
            this.name = string;
        }


        public GoodLevelEntity getParent() {
            return parent;
        }

        @Override
        public void addSubItem(int position, GoodLevelEntity subItem) {
            super.addSubItem(position, subItem);
            subItem.parent = this;
        }

        @Override
        public void addSubItem(GoodLevelEntity subItem) {
            super.addSubItem(subItem);
            subItem.parent = this;
        }

        @Override
        public void setExpanded(boolean expanded) {
            super.setExpanded(expanded);
            if (hasSubItem() && !expanded) {
                for (int j = 0, size = getSubItems().size(); j < size; j++) {
                    GoodLevelEntity levelChild = (GoodLevelEntity) getSubItems().get(j);
                    levelChild.setExpanded(false);
                }
            }
        }

        public String getName() {
            return name;
        }

        /**
         * 0为第一级 以次后排
         *
         * @return
         */
        @Override
        public int getLevel() {
            return getLevel2(this);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GoodLevelEntity)) return false;

            GoodLevelEntity that = (GoodLevelEntity) o;

            if (Double.compare(that.i, i) != 0) return false;
            return name.equals(that.name);

        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = name.hashCode();
            temp = Double.doubleToLongBits(i);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }

        private int getLevel2(GoodLevelEntity entity) {
            int level = 1;
            if (entity.getParent() != null) {
                level += getLevel2(entity.getParent());
            }
            return level;
        }

        @Override
        public int getItemType() {
            return hasSubItem() ? GoodListAdapter.TYPE_LEVEL_PARENT : GoodListAdapter.TYPE_LEVEL_CHILD;
        }
    }

    private class GoodSimpleItemClick extends SimpleClickListener {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int pos) {
            log("onItemClick() called with: " + "adapter = [" + adapter + "], view = [" + view + "], position = [" + pos + "]");
            GoodLevelEntity entity = (GoodLevelEntity) adapter.getItem(pos);
            //如果没有子条目就跳转到选择图片的页面
            if (!entity.hasSubItem()) {
                String categoryId = entity.getCategoryId();
                ActivityBaseHeader3 activity = (ActivityBaseHeader3) getActivity();
                ActivityPriceSearch activityPriceSearch = (ActivityPriceSearch) (activity);
                activityPriceSearch.setCategoryId(categoryId);
                activity.onNavMenuClick();
                return;

            }
            if (entity.isExpanded()) {
                collapse(adapter, entity);
            } else {
                expand(pos, adapter, entity);
            }
        }


        /**
         * 收缩 收缩当前item 及其子item
         *
         * @param adapter
         * @param entity
         */
        private void collapse(BaseQuickAdapter adapter, GoodLevelEntity entity) {
            List<GoodLevelEntity> needDelete = new ArrayList<>();
            collapseNeedDelete(needDelete, entity.getSubItems());
            entity.setExpanded(false);
            adapter.getData().removeAll(needDelete);
            adapter.notifyDataSetChanged();
        }

        /**
         * @param result
         * @param subItems 全部删除 包括子列
         */
        private void collapseNeedDelete(List<GoodLevelEntity> result, List<GoodLevelEntity> subItems) {
            for (GoodLevelEntity item : subItems) {
                result.add(item);
                boolean hasSubItem = item.hasSubItem();
                if (hasSubItem && item.isExpanded()) {
                    item.setExpanded(false);
                    collapseNeedDelete(result, item.getSubItems());
                }
            }
        }

        /**
         * 伸开 收缩与其同级的及其同级的子目录
         *
         * @param pos
         * @param adapter
         * @param entity
         */
        private void expand(int pos, BaseQuickAdapter adapter, GoodLevelEntity entity) {
            adapter.getData().addAll(pos + 1, entity.getSubItems()); /*要先添加 要不数据会乱掉*/
            List<GoodLevelEntity> needDelete = new ArrayList<>();
            if (entity.getParent() == null) {
                List<GoodLevelEntity> level0List = getLevel0List(adapter.getData());
                expandNeedDelete(needDelete, level0List);
            } else {
                expandNeedDelete(needDelete, entity.getParent().getSubItems());
            }
            adapter.getData().removeAll(needDelete);
            entity.setExpanded(true);/*不能在expandNeedDelete否则把将当前也加到删除列表了*/
            adapter.notifyDataSetChanged();
        }

        /**
         * 获得顶级列表
         *
         * @param data
         * @return
         */
        private List<GoodLevelEntity> getLevel0List(List<GoodLevelEntity> data) {
            List<GoodLevelEntity> result = new ArrayList<>();
            for (GoodLevelEntity item : data) {
                if (item.getParent() == null) {
                    result.add(item);
                }
            }
            return result;
        }

        /**
         * 获得待从列表中删除的item
         *
         * @param result
         * @param subItems
         */
        private void expandNeedDelete(List<GoodLevelEntity> result, List<GoodLevelEntity> subItems) {
            for (GoodLevelEntity item : subItems) {
                boolean hasSubItem = item.hasSubItem();
                if (item.isExpanded() && hasSubItem) { /*有子类且展开的*/
                    result.addAll(item.getSubItems());
                    expandNeedDelete(result, item.getSubItems());
                    item.setExpanded(false);  /*要加expandNeedDelete之后 注意顺序否则会册不掉，因为会将子列也没为false*/
                }
            }
        }

        @Override
        public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
            log("onItemLongClick() called with: " + "adapter = [" + adapter + "], view = [" + view + "], position = [" + position + "]");
        }

        @Override
        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            log("onItemChildClick() called with: " + "adapter = [" + adapter + "], view = [" + view + "], position = [" + position + "]");
        }

        @Override
        public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
            log("onItemChildLongClick() called with: " + "adapter = [" + adapter + "], view = [" + view + "], position = [" + position + "]");
        }
    }

    private class GoodDividerItemDecoration extends RecyclerView.ItemDecoration {
        private final int[] ATTRS = new int[]{android.R.attr.listDivider};
        private Drawable mDivider;

        public GoodDividerItemDecoration(Activity activity) {
            final TypedArray a = activity.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            log("getItemOffsets() called with: " + "outRect = [" + outRect + "], view = [" + view + "], parent = [" + parent + "], state = [" + state + "]");
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        }


        @Override
        public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
            log("onDraw() called with: " + "canvas = [" + canvas + "], parent = [" + parent + "], state = [" + state + "]");
            if (mDivider == null) {
                return;
            }
            final int left = parent.getPaddingLeft();
            final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
            GoodListAdapter goodListAdapter = (GoodListAdapter) parent.getAdapter();
            float paddingLeftUnit = goodListAdapter.getPaddingLeftUnit();
            final int childSize = parent.getChildCount();
            for (int i = 0; i < childSize; i++) {
                final View child = parent.getChildAt(i);
                int adapterPosition = parent.getChildAdapterPosition(child);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int top = child.getBottom() + layoutParams.bottomMargin;
                final int bottom = top + mDivider.getIntrinsicHeight();
                int drawableLeft = (int) (left + paddingLeftUnit * goodListAdapter.getItem(adapterPosition).getLevel());
                mDivider.setBounds(drawableLeft - 10, top, (int) (right - paddingLeftUnit * 2), bottom);
                mDivider.draw(canvas);
            }
        }

        /**
         * @param view
         * @param parent
         * @return true 应该绘制分隔线 一级菜单未展开的 二级菜单展开的最后一个
         */
        private boolean checkLevelOne(View view, RecyclerView parent) {
            int adapterPosition = parent.getChildAdapterPosition(view);
            GoodListAdapter adapter = (GoodListAdapter) parent.getAdapter();
            GoodLevelEntity levelEntity = adapter.getItem(adapterPosition);
            if (levelEntity != null) {
                /*一级元素 且收缩的*/
                if (levelEntity.hasSubItem() && !levelEntity.isExpanded()) {
                    return true;
                }
                /*二级元素 且最后一个元素时*/
                if (!levelEntity.hasSubItem() && levelEntity.getParent() != null) {
                    GoodLevelEntity levelEntityParent = levelEntity.getParent();
                    List subItems = levelEntityParent.getSubItems();
                    Object lastEntity = levelEntityParent.getSubItem(subItems.size() - 1);
                    if (levelEntity == lastEntity) {
                        return true;
                    }
                }

            }
            return false;
        }


    }
}
