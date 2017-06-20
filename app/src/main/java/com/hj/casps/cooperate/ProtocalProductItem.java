package com.hj.casps.cooperate;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.hj.casps.R;
import com.hj.casps.adapter.WZYBaseAdapter;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.goodsmanager.goodsmanagerCallBack.GoodsCategoryCallBack;
import com.hj.casps.entity.goodsmanager.response.GoodsCategoryEntity;
import com.hj.casps.entity.goodsmanager.response.NoteEntity;
import com.hj.casps.entity.protocalproductentity.RequestProtocal;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.hj.casps.common.Constant.finalGoodLevelEntities;
import static com.hj.casps.common.Constant.oldGoodLevelEntities;

//创建协议的选择商品类型界面,用于选择商品的类型
public class ProtocalProductItem extends ActivityBaseHeader2 implements View.OnClickListener {

    private TextView cooperate_protocol_info;
    private GridView cooperate_protocol_gv;
    private RecyclerView cooperate_protocol_recycle;//商品类型加载及删除的列表
    private List<NoteEntity> categoryList;//商品集合
    private RequestProtocal requestProtocal;
    private static List<GoodLevelEntity> goodLevelEntities = new ArrayList<>();//已经选择的商品类型列表
    private static ProductsAdapter adapter;
    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocal_product_item);
        initData();
        initView();
    }

    //加载商品类型数据
    private void initData() {
        finalGoodLevelEntities = new ArrayList<>();
        goodLevelEntities.clear();
        finalGoodLevelEntities.clear();
//        goodLevelEntities.addAll(oldGoodLevelEntities);
//        finalGoodLevelEntities.addAll(oldGoodLevelEntities);
        String ctrId = getIntent().getStringExtra("ctrId");
        String sellMmbId = getIntent().getStringExtra("sellMmbId");

        if (getIntent().getBooleanExtra("goods", false)) {
            requestProtocal = new RequestProtocal(
                    publicArg.getSys_token(),
                    Constant.getUUID(),
                    Constant.SYS_FUNC10110035,
                    publicArg.getSys_user(),
                    publicArg.getSys_member(),
                    ctrId,
                    sellMmbId);
            url = Constant.getUserCategoryByCtrIdUrl;
        } else {
            requestProtocal = new RequestProtocal(
                    publicArg.getSys_token(),
                    Constant.getUUID(),
                    Constant.SYS_FUNC10110035,
                    publicArg.getSys_user(),
                    publicArg.getSys_member(),
                    sellMmbId);
            url = Constant.getMemberCategoryUrl;
        }
        LogShow(mGson.toJson(requestProtocal));
        OkGo.post(url)
                .params("param", mGson.toJson(requestProtocal))
                .execute(new GoodsCategoryCallBack<GoodsCategoryEntity<List<NoteEntity>>>() {
                    @Override
                    public void onSuccess(GoodsCategoryEntity<List<NoteEntity>> listGoodsCategoryEntity,
                                          Call call, Response response) {
                        super.onSuccess(listGoodsCategoryEntity, call, response);
                        if (listGoodsCategoryEntity != null) {
                            categoryList = listGoodsCategoryEntity.categoryList;
//                            ActivityManageGoods.this.categoryList.addAll(categoryList);
//                            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_0);

                            cooperate_protocol_recycle.setLayoutManager(
                                    new LinearLayoutManager(ProtocalProductItem.this));
                            //如果数据为空或者没有数据
                            if (categoryList == null || categoryList.size() == 0) {
                                return;
                            }
                            ArrayList<ProtocalProductItem.GoodLevelEntity> dataList = generateData1();
                            //如果数据为空，就跳到添加数据页面
                            cooperate_protocol_recycle.setAdapter(new GoodListAdapter(dataList));
                            cooperate_protocol_recycle.addItemDecoration(
                                    new GoodDividerItemDecoration(ProtocalProductItem.this));
                            cooperate_protocol_recycle.addOnItemTouchListener(new GoodSimpleItemClick());
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    /**
     * 左侧导航的适配器
     * 注意 hascode() equals() 方法，
     * 在点击item会在列表中删除，equal的法，会误删除，确保同一级中不要有相同的name
     */
    public static class GoodLevelEntity extends AbstractExpandableItem<GoodLevelEntity> implements MultiItemEntity {
        private String name;
        private GoodLevelEntity parent;
        private double i;
        private boolean choose;
        private String categoryId;

        public GoodLevelEntity(String name, String categoryId) {
            this.name = name;
            this.categoryId = categoryId;
            this.i = Math.random();
        }

        public boolean isChoose() {
            return choose;
        }

        public void setChoose(boolean choose) {
            this.choose = choose;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
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

    @Override
    protected void onRightClick() {
        super.onRightClick();
        oldGoodLevelEntities.clear();
        setResult(22);
        finish();
    }
    //三级菜单加载
    private ArrayList<ProtocalProductItem.GoodLevelEntity> generateData1() {
        ArrayList<ProtocalProductItem.GoodLevelEntity> res = new ArrayList<>();
        for (int i = 0; i < categoryList.size(); i++) {
            ProtocalProductItem.GoodLevelEntity lv0 = new ProtocalProductItem.GoodLevelEntity(categoryList.get(i).getCategoryName(), categoryList.get(i).getCategoryId());
            if (categoryList.get(i).getNodes() != null && categoryList.get(i).getNodes().size() > 0)
                for (int j = 0; j < categoryList.get(i).getNodes().size(); j++) {
                    if (categoryList.get(i).getNodes().get(j)!= null) {
                        ProtocalProductItem.GoodLevelEntity lv1 = new ProtocalProductItem.GoodLevelEntity(categoryList.get(i).getNodes().get(j).getCategoryName(), categoryList.get(i).getNodes().get(j).getCategoryId());
                        for (int x = 0; x < categoryList.get(i).getNodes().get(j).getNodes().size(); x++) {
                            if (categoryList.get(i).getNodes().get(j).getNodes().get(x).getNodes() != null && categoryList.get(i).getNodes().get(j).getNodes().get(x).getNodes().size() > 0) {
                                ProtocalProductItem.GoodLevelEntity lv2 = new ProtocalProductItem.GoodLevelEntity(categoryList.get(i).getNodes().get(j).getNodes().get(x).getCategoryName(), categoryList.get(i).getNodes().get(j).getNodes().get(x).getCategoryId());
                                lv1.addSubItem(lv2);
                            }
                        }
                        lv0.addSubItem(lv1);
                    }
                }
            res.add(lv0);
        }
        return res;
    }

    //三级菜单的适配器
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

    //已选择商品清单的适配器
    private static class ProductsAdapter extends WZYBaseAdapter<GoodLevelEntity> {
        private ProductsAdapter(List<GoodLevelEntity> data, Context context, int layoutRes) {
            super(data, context, layoutRes);
        }

        @Override
        public void bindData(ViewHolder holder, final GoodLevelEntity goodLevelEntity, final int indexPos) {
            TextView name = (TextView) holder.getView(R.id.goods_item_name);
            ImageView close = (ImageView) holder.getView(R.id.close_goods);
            name.setText(goodLevelEntity.getName());
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goodLevelEntity.setChoose(false);
                    refreshAdapter();
                }
            });
        }
    }

    //商品刷新适配器
    private static void refreshAdapter() {
//        toast(goodLevelEntities.get(0).getName());
        finalGoodLevelEntities.clear();
        finalGoodLevelEntities.addAll(oldGoodLevelEntities);
        for (int i = 0; i < goodLevelEntities.size(); i++) {
            if (goodLevelEntities.get(i).isChoose()) {
                finalGoodLevelEntities.add(goodLevelEntities.get(i));
            }
        }
//        toast(finalGoodLevelEntities.get(0).getName());
        adapter.notifyDataSetChanged();

    }

    //选择商品子条目
    private class GoodSimpleItemClick extends SimpleClickListener {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int pos) {
            log("onItemClick() called with: " + "adapter = [" + adapter + "], view = [" + view + "], position = [" + pos + "]");
            GoodLevelEntity entity = (GoodLevelEntity) adapter.getItem(pos);
            //如果没有子条目就跳转到选择图片的页面
            if (!entity.hasSubItem()) {
                boolean b = false;
                for (int i = 0; i < oldGoodLevelEntities.size(); i++) {
                    if (oldGoodLevelEntities.get(i).getCategoryId().equalsIgnoreCase(entity.getCategoryId())) {
                        b = true;
                    }
                }
                if (!b && !goodLevelEntities.contains(entity)) {
                    entity.setChoose(true);
                    goodLevelEntities.add(entity);
                } else {
                    if (!b) {
                        goodLevelEntities.get(goodLevelEntities.indexOf(entity)).setChoose(true);

                    }
                    toast("已经选择了该商品");
                }
                refreshAdapter();
////                intentActivity(ActivityManageGoods.this, ActivityGoodsClass.class);
//                Intent intent = new Intent(ActivityManageGoods.this, ActivityGoodsClass.class);
//                intent.putExtra(Constant.CATEGORY_ID, entity.getCategoryId());
//                startActivity(intent);
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


    //商品品类选择的布局界面
    private void initView() {
        setTitle(getString(R.string.cooperate_products_protocol_type));
        setTitleRight(getString(R.string.save), null);
        cooperate_protocol_info = (TextView) findViewById(R.id.cooperate_protocol_info);
        cooperate_protocol_gv = (GridView) findViewById(R.id.cooperate_protocol_gv);
        cooperate_protocol_recycle = (RecyclerView) findViewById(R.id.cooperate_protocol_recycle);

        cooperate_protocol_info.setOnClickListener(this);
        adapter = new ProductsAdapter(finalGoodLevelEntities, this, R.layout.goods_item);
        cooperate_protocol_gv.setAdapter(adapter);
        refreshAdapter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        oldGoodLevelEntities.clear();
    }

    //操作说明
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cooperate_protocol_info:
                CreateDialog(Constant.DIALOG_CONTENT_20);

                break;
        }
    }
}
