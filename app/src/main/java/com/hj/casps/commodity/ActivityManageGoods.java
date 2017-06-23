package com.hj.casps.commodity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.hj.casps.R;
import com.hj.casps.base.ActivityBaseHeader;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.PublicArg;
import com.hj.casps.entity.goodsmanager.goodsmanagerCallBack.GoodsCategoryCallBack;
import com.hj.casps.entity.goodsmanager.request.RequestPub;
import com.hj.casps.entity.goodsmanager.response.GoodsCategoryEntity;
import com.hj.casps.entity.goodsmanager.response.NoteEntity;
import com.hj.casps.util.LogoutUtils;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.fancybuttons.FancyButton;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 商品管理模块
 * Created by YaoChen on 2017/4/12.
 */

public class ActivityManageGoods extends ActivityBaseHeader implements View.OnClickListener {
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.head_title)
    LinearLayout head_title;
    @BindView(R.id.layout_head_right_tv)
    TextView layout_head_right_tv;
    private int fra_type;
    //多选添加按钮
    private ImageView et_add;
    private List<NoteEntity> categoryList = new ArrayList<>();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.HANDLERTYPE_0:
                    //获取当前用户会员的所有使用的商品分类（出这个会员的商品目录结构）
                    refrushUi();
                    break;
            }
        }
    };

    //更新UI
    private void refrushUi() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (categoryList == null || categoryList.size() <= 0) {
            toastSHORT("商品分类初始化失败，可能网络或用户没有该分类");
            return;
        }
        ArrayList<GoodLevelEntity> dataList = generateData1();
        //如果数据为空，就跳到添加数据页面
        recyclerView.setAdapter(new GoodListAdapter(dataList));
        //添加分割线
        recyclerView.addItemDecoration(new GoodDividerItemDecoration(ActivityManageGoods.this));
        recyclerView.addOnItemTouchListener(new GoodSimpleItemClick());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectclass);
        findViewById(R.id.head_title).setVisibility(View.VISIBLE);

        FancyButton addGoods = (FancyButton) findViewById(R.id.layout_head_left_btn);
        addGoods.setBackgroundColor(getResources().getColor(R.color.white));
        addGoods.setTextColor(getResources().getColor(R.color.title_bg));
        addGoods.setOnClickListener(this);
        setTitle(getString(R.string.goodmanage));
//        titleLeft.setBackgroundResource(R.mipmap.nav_menu);

        /*Drawable drawable = getResources().getDrawable(R.mipmap.nav_menu);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
        titleLeft.setCompoundDrawables(null, null, drawable, null);//画在右边*/
        setTitleRight(null, null);
        ButterKnife.bind(this);
        layout_head_right_tv.setOnClickListener(this);
        initView();
        //判断是否有网络
        if (hasInternetConnected()) {
            initData();
        } else {
            //公共的缓存商品分类
            categoryList = Constant.goodCategoryList;
            refrushUi();
        }
//        refrushUi();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }

    //初始化数据
    private void initData() {
        PublicArg p = Constant.publicArg;
        RequestPub r = new RequestPub(p.getSys_token(), Constant.getUUID(), Constant.SYS_FUNC, p.getSys_user(), p.getSys_member());
        String param = mGson.toJson(r);
        OkGo.post(Constant.GetUserCategoryUrl)
                .params("param", param)
                .execute(new GoodsCategoryCallBack<GoodsCategoryEntity<List<NoteEntity>>>() {
                    @Override
                    public void onSuccess(GoodsCategoryEntity<List<NoteEntity>> listGoodsCategoryEntity, Call call, Response response) {
                        super.onSuccess(listGoodsCategoryEntity, call, response);
                        if (listGoodsCategoryEntity != null) {
                            List<NoteEntity> categoryList = listGoodsCategoryEntity.categoryList;
                            ActivityManageGoods.this.categoryList.addAll(categoryList);
                            Constant.goodCategoryList = categoryList;
                            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (Constant.public_code) {
                            LogoutUtils.exitUser(ActivityManageGoods.this);
                        }
                    }
                });


    }

    private void initView() {
        fra_type = getIntent().getIntExtra("fra", 0);
        if (fra_type == 1) {
            head_title.setVisibility(View.GONE);
        }

    }


    private ArrayList<GoodLevelEntity> generateTempData() {
        ArrayList<GoodLevelEntity> res = new ArrayList<>();
        for (int anInt = 0; anInt < 10; anInt++) {
            GoodLevelEntity lv0 = new GoodLevelEntity(anInt + "", anInt + "");
            for (int j = 0; j < 10; j++) {
                GoodLevelEntity lv1 = new GoodLevelEntity(j + "", j + "");
                for (int x = 0; x < 10; x++) {
                    GoodLevelEntity lv2 = new GoodLevelEntity(x + "", x + "");
                    lv1.addSubItem(lv2);
                }
                lv0.addSubItem(lv1);
            }
            res.add(lv0);
        }
        return res;
    }

    //初始化菜单数据
    private ArrayList<GoodLevelEntity> generateData1() {
        ArrayList<ActivityManageGoods.GoodLevelEntity> res = new ArrayList<>();
//        if(entityList!=null&&entityList.size()>0)
        for (int i = 0; i < categoryList.size(); i++) {
            ActivityManageGoods.GoodLevelEntity lv0 = new ActivityManageGoods.GoodLevelEntity(categoryList.get(i).getCategoryName(), categoryList.get(i).getCategoryId());
            for (int j = 0; j < categoryList.get(i).getNodes().size(); j++) {
                ActivityManageGoods.GoodLevelEntity lv1 = new ActivityManageGoods.GoodLevelEntity(categoryList.get(i).getNodes().get(j).getCategoryName(), categoryList.get(i).getNodes().get(j).getCategoryId());
                for (int x = 0; x < categoryList.get(i).getNodes().get(j).getNodes().size(); x++) {
                    ActivityManageGoods.GoodLevelEntity lv2 = new ActivityManageGoods.GoodLevelEntity(categoryList.get(i).getNodes().get(j).getNodes().get(x).getCategoryName(), categoryList.get(i).getNodes().get(j).getNodes().get(x).getCategoryId());
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
        ArrayList<ActivityManageGoods.GoodLevelEntity> res = new ArrayList<>();
        for (int i = 0; i < categoryList.size(); i++) {
            NoteEntity noteEntity = categoryList.get(i);
            ActivityManageGoods.GoodLevelEntity lv0 = new ActivityManageGoods.GoodLevelEntity(
                    noteEntity.getCategoryName(), noteEntity.getCategoryId());
            res.add(lv0);
            generateData2(lv0, noteEntity);
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
    private void generateData2(ActivityManageGoods.GoodLevelEntity lv0, NoteEntity noteEntity) {
        if (noteEntity.getNodes() != null && noteEntity.getNodes().size() > 0) {
            for (int i = 0; i < noteEntity.getNodes().size(); i++) {
                NoteEntity itemNoteEntity=noteEntity.getNodes().get(i);
                ActivityManageGoods.GoodLevelEntity goodEntity = new ActivityManageGoods.GoodLevelEntity(
                        itemNoteEntity.getCategoryName(), itemNoteEntity.getCategoryId());
                lv0.addSubItem(goodEntity);
                generateData2(goodEntity,itemNoteEntity);
            }
        }
    }

    private void onItemClick1(GoodLevelEntity lv1) {
        log("onItemClick() called with: " + "lv1 = [" + lv1 + "]");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //添加商品
            case R.id.layout_head_left_btn:
                Intent intent = new Intent(ActivityManageGoods.this, ActivityEditGoods.class);
                intent.putExtra(Constant.INTENTISADDGOODS, true);
                startActivity(intent);
                break;
            case R.id.layout_head_right_tv:
                //    操作说明
                CreateDialog(Constant.DIALOG_CONTENT_28);
                break;
        }
    }

    private class GoodListAdapter extends BaseMultiItemQuickAdapter<GoodLevelEntity, BaseViewHolder> {
        public static final int TYPE_LEVEL_PARENT = 0; /*有子类*/
        public static final int TYPE_LEVEL_CHILD = 1;/*无子类*/
        float paddingLeftUnit;

        public GoodListAdapter(List<GoodLevelEntity> data) {
            super(data);
            paddingLeftUnit = getResources().getDisplayMetrics().density * 20;
            addItemType(TYPE_LEVEL_PARENT, R.layout.activity_manager_good_item);
            addItemType(TYPE_LEVEL_CHILD, R.layout.activity_manager_good_item);
        }

        public float getPaddingLeftUnit() {
            return paddingLeftUnit;
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
     * 注意 hascode() equals() 方法，
     * 在点击item会在列表中删除，equal的法，会误删除，确保同一级中不要有相同的name
     */
    public class GoodLevelEntity extends AbstractExpandableItem<GoodLevelEntity> implements MultiItemEntity {
        private String name;
        private GoodLevelEntity parent;
        private double i;
        private String categoryId;

        public GoodLevelEntity(String name, String categoryId) {
            this.name = name;
            this.categoryId = categoryId;
            this.i = Math.random();
        }

        /**
         * @param string
         */
        public GoodLevelEntity(String string) {
            this.name = string;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
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
         * 1为第一级 以次后排
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
            if (name != null ? !name.equals(that.name) : that.name != null) return false;
            if (parent != null ? !parent.equals(that.parent) : that.parent != null) return false;
            return categoryId != null ? categoryId.equals(that.categoryId) : that.categoryId == null;

        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = name != null ? name.hashCode() : 0;
            result = 31 * result + (parent != null ? parent.hashCode() : 0);
            temp = Double.doubleToLongBits(i);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
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

    /**
     * 二级目录不显示分隔线
     * 水平分隔线
     */
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

    }

    private class GoodSimpleItemClick extends SimpleClickListener {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int pos) {
            log("onItemClick() called with: " + "adapter = [" + adapter + "], view = [" + view + "], position = [" + pos + "]");
            GoodLevelEntity entity = (GoodLevelEntity) adapter.getItem(pos);
            //如果没有子条目就跳转到选择图片的页面
            if (!entity.hasSubItem()) {
                onItemClick1(entity);
//                intentActivity(ActivityManageGoods.this, ActivityGoodsClass.class);
                //跳转到商品列表页
                Intent intent = new Intent(ActivityManageGoods.this, ActivityGoodsClass.class);
                intent.putExtra(Constant.CATEGORY_ID, entity.getCategoryId());
                intent.putExtra("fra", fra_type);
                startActivityForResult(intent, 11);
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


}
