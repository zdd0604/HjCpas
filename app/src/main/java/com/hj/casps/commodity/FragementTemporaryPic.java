package com.hj.casps.commodity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.hj.casps.R;
import com.hj.casps.base.FragmentBase;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.PublicArg;
import com.hj.casps.entity.goodsmanager.Pub;
import com.hj.casps.entity.picturemanager.request.ResAddDiv;
import com.hj.casps.entity.picturemanager.request.ResUpdateDiv;
import com.hj.casps.ui.MyDialog;
import com.hj.casps.ui.MyToast;
import com.hj.casps.util.GsonTools;
import com.hj.casps.util.LogoutUtils;
import com.hj.casps.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by YaoChen on 2017/4/13.`
 * 临时素材库和平台素材库
 */

public class FragementTemporaryPic extends FragmentBase implements View.OnClickListener {

    private PopupWindow sharepopupWindow;
    private View contentView;

    private RecyclerView recyclerView;
    private int pageFlag;
    private List<SelectPicture_new.DataTreeListEntity> divList;
    private String baseId;
    //是否是自己的素材库   平台素材库 和临时素材库属于系统的
    private boolean isMyMaterial = true;
    public static final int ADD_PIC_RES_REQUES_CODE = 909;
    public static final int REQUES_CODE = 904;
    private LinearLayout ll_content;
    private EditText et_name;

// 图片封装为一个数组


    public FragementTemporaryPic(int pageFlag, List<SelectPicture_new.DataTreeListEntity> divList, String baseId) {
        this.pageFlag = pageFlag;
        this.divList = divList;
        this.baseId = baseId;
        //4和19代表系统的素材库
        if (baseId.equals("4") || baseId.equals("19")) {
            this.isMyMaterial = false;
        }

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_selectclass, container, false);
        ll_content = (LinearLayout) view.findViewById(R.id.ll_content);
        FancyButton add = (FancyButton) view.findViewById(R.id.submit);
        et_name = (EditText) view.findViewById(R.id.fragment_mypic_empty_class_Et);
        add.setOnClickListener(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        initView();
        return view;
    }


    private void setData() {
        //如果个人素材库没有数据，就显示一个可以添加一级目录的界面
       if(divList!=null&&divList.size()<=0&&isMyMaterial){
           ll_content.setVisibility(View.VISIBLE);
           return;
       }
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        ArrayList<GoodLevelEntity> arrayList = generateData();
        recyclerView.setAdapter(new GoodListAdapter(arrayList));
        recyclerView.addItemDecoration(new GoodDividerItemDecoration(this.getActivity()));
        recyclerView.addOnItemTouchListener(new GoodSimpleItemClick());
    }


    private void initView() {
        setData();
    }

    private ArrayList<FragementTemporaryPic.GoodLevelEntity> generateData() {
        ArrayList<FragementTemporaryPic.GoodLevelEntity> res = new ArrayList<>();
        for (int i = 0; i < divList.size(); i++) {
            SelectPicture_new.DataListEntity e1 = divList.get(i).getEntity();
            FragementTemporaryPic.GoodLevelEntity lv0 = new FragementTemporaryPic.GoodLevelEntity(e1.getDivName(), e1.getDivId(), e1.getParentdivId(), baseId);
            for (int j = 0; j < divList.get(i).getListEntities().size(); j++) {
                SelectPicture_new.DataListEntity e2 = divList.get(i).getListEntities().get(j).getEntity();
                FragementTemporaryPic.GoodLevelEntity lv1 = new FragementTemporaryPic.GoodLevelEntity(e2.getDivName(), e2.getDivId(), e2.getParentdivId(), baseId);
                for (int x = 0; x < divList.get(i).getListEntities().get(j).getListEntities().size(); x++) {
                    SelectPicture_new.DataListEntity e3 = divList.get(i).getListEntities().get(j).getListEntities().get(x).getEntity();
                    FragementTemporaryPic.GoodLevelEntity lv2 = new FragementTemporaryPic.GoodLevelEntity(e3.getDivName(), e3.getDivId(), e3.getParentdivId(), baseId);
                    lv1.addSubItem(lv2);
                }
                lv0.addSubItem(lv1);
            }
            res.add(lv0);
        }


        return res;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                String name = et_name.getText().toString().trim();
                if(name.equals("")){
                    ToastUtils.showToast(FragementTemporaryPic.this.getActivity(),"目录不能为空");
                    return;
                }
                addDivForNet(name,"0",baseId);
                break;
        }
    }

    private class GoodListAdapter extends BaseMultiItemQuickAdapter<FragementTemporaryPic.GoodLevelEntity, BaseViewHolder> {
        public static final int TYPE_LEVEL_PARENT = 0; /*有子类*/
        public static final int TYPE_LEVEL_CHILD = 1;/*无子类*/
        private final int anInt = 20;
        float paddingLeftUnit;

        public GoodListAdapter(List<FragementTemporaryPic.GoodLevelEntity> data) {
            super(data);
            paddingLeftUnit = getResources().getDisplayMetrics().density * 20;
            addItemType(TYPE_LEVEL_PARENT, R.layout.activity_manager_good_item);
            addItemType(TYPE_LEVEL_CHILD, R.layout.activity_manager_good_item);
        }

        public float getPaddingLeftUnit() {
            return paddingLeftUnit;
        }

        @Override
        protected void convert(BaseViewHolder holder, FragementTemporaryPic.GoodLevelEntity item) {
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
        private void convertLevelParent(final BaseViewHolder holder, final FragementTemporaryPic.GoodLevelEntity entity) {
            holder.setText(R.id.item_name, entity.getDivName());

            holder.setText(R.id.item_name, entity.getDivName());
            holder.getView(R.id.item_name).setPadding((int) (entity.getLevel() * paddingLeftUnit), 0, 0, 0);
            holder.getView(R.id.item_iv).setVisibility(View.VISIBLE);
            holder.setImageResource(R.id.item_iv, entity.isExpanded() ? R.mipmap.jt3 : R.mipmap.jt1);
        }


        /*无子列的*/
        private void convertLevelChild(final BaseViewHolder holder, final FragementTemporaryPic.GoodLevelEntity entity) {
            holder.setText(R.id.item_name, entity.getDivName());
            holder.getView(R.id.item_iv).setVisibility(View.GONE);
            holder.getView(R.id.item_name).setPadding((int) (entity.getLevel() * paddingLeftUnit), 0, 0, 0);
        }

    }


    /**
     * 左侧导航的适配器
     * 注意 hascode() equals() 方法，
     * 在点击item会在列表中删除，equal的法，会误删除，确保同一级中不要有相同的name
     */
    public class GoodLevelEntity extends AbstractExpandableItem<FragementTemporaryPic.GoodLevelEntity> implements MultiItemEntity {
        private String divName;
        private FragementTemporaryPic.GoodLevelEntity parent;
        private double i = Math.random();
        private String divId;
        private String parentId;
        private String baseId;

        public String getBaseId() {
            return baseId;
        }

        public void setDivName(String divName) {
            this.divName = divName;
        }

        public void setDivId(String divId) {
            this.divId = divId;
        }

        public String getParentId() {
            return parentId;
        }

        public GoodLevelEntity() {
        }

        public GoodLevelEntity(String divName, String divId, String parentId, String baseId) {
            this.divName = divName;
            this.divId = divId;
            this.parentId = parentId;
            this.baseId = baseId;
        }

        public double getI() {
            return i;
        }

        public void setI(int i) {
            this.i = Math.random();
        }

        public String getDivId() {
            return divId;
        }

        public GoodLevelEntity(String divName) {
            this.divName = divName;
        }

        /**
         * @param
         */


        public FragementTemporaryPic.GoodLevelEntity getParent() {
            return parent;
        }

        @Override
        public void addSubItem(int position, FragementTemporaryPic.GoodLevelEntity subItem) {
            super.addSubItem(position, subItem);
            subItem.parent = this;
        }

        @Override
        public void addSubItem(FragementTemporaryPic.GoodLevelEntity subItem) {
            super.addSubItem(subItem);
            subItem.parent = this;
        }

        @Override
        public void setExpanded(boolean expanded) {
            super.setExpanded(expanded);
            if (hasSubItem() && !expanded) {
                for (int j = 0, size = getSubItems().size(); j < size; j++) {
                    FragementTemporaryPic.GoodLevelEntity levelChild = (FragementTemporaryPic.GoodLevelEntity) getSubItems().get(j);
                    levelChild.setExpanded(false);
                }
            }
        }

        public String getDivName() {
            return divName;
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
            return divName.equals(that.divName);

        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = divName.hashCode();
            temp = Double.doubleToLongBits(i);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }

        private int getLevel2(FragementTemporaryPic.GoodLevelEntity entity) {
            int level = 1;
            if (entity.getParent() != null) {
                level += getLevel2(entity.getParent());
            }
            return level;
        }

        @Override
        public int getItemType() {
            return hasSubItem() ? FragementTemporaryPic.GoodListAdapter.TYPE_LEVEL_PARENT : FragementTemporaryPic.GoodListAdapter.TYPE_LEVEL_CHILD;
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
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        }

        /**
         * @param view
         * @param parent
         * @return true 应该绘制分隔线 一级菜单未展开的 二级菜单展开的最后一个
         */
        private boolean checkLevelOne(View view, RecyclerView parent) {
            int adapterPosition = parent.getChildAdapterPosition(view);
            FragementTemporaryPic.GoodListAdapter adapter = (FragementTemporaryPic.GoodListAdapter) parent.getAdapter();
            FragementTemporaryPic.GoodLevelEntity levelEntity = adapter.getItem(adapterPosition);
            if (levelEntity != null) {
                /*一级元素 且收缩的*/
                if (levelEntity.hasSubItem() && !levelEntity.isExpanded()) {
                    return true;
                }
                /*二级元素 且最后一个元素时*/
                if (!levelEntity.hasSubItem() && levelEntity.getParent() != null) {
                    FragementTemporaryPic.GoodLevelEntity levelEntityParent = levelEntity.getParent();
                    List subItems = levelEntityParent.getSubItems();
                    Object lastEntity = levelEntityParent.getSubItem(subItems.size() - 1);
                    if (levelEntity == lastEntity) {
                        return true;
                    }
                }

            }
            return false;
        }

        @Override
        public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {

            log("onDraw() called with: " + "canvas = [" + canvas + "], parent = [" + parent + "], state = [" + state + "]");
            if (mDivider == null) {
                return;
            }
            final int left = parent.getPaddingLeft();
            final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
            FragementTemporaryPic.GoodListAdapter goodListAdapter = (FragementTemporaryPic.GoodListAdapter) parent.getAdapter();
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
            FragementTemporaryPic.GoodLevelEntity entity = (FragementTemporaryPic.GoodLevelEntity) adapter.getItem(pos);
            //如果没有子条目就跳转到选择图片的页面
            if (!entity.hasSubItem()) {
//                onItemClick1(entity);
                Intent intent = null;
                //是个人素材库
                if (isMyMaterial) {
                    intent = new Intent(FragementTemporaryPic.this.getContext(), PeoplePicture.class);
                } else {
                    intent = new Intent(FragementTemporaryPic.this.getContext(), SelectPicture02.class);
                }
                String divId = entity.getDivId();
                intent.putExtra("PicStyle", pageFlag);
                intent.putExtra(Constant.INTENT_DIV_ID, divId);
                FragementTemporaryPic.this.startActivityForResult(intent,REQUES_CODE);
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
        private void collapse(BaseQuickAdapter adapter, FragementTemporaryPic.GoodLevelEntity entity) {
            List<FragementTemporaryPic.GoodLevelEntity> needDelete = new ArrayList<>();
            collapseNeedDelete(needDelete, entity.getSubItems());
            entity.setExpanded(false);
            adapter.getData().removeAll(needDelete);
            adapter.notifyDataSetChanged();
        }

        /**
         * @param result
         * @param subItems 全部删除 包括子列
         */
        private void collapseNeedDelete(List<FragementTemporaryPic.GoodLevelEntity> result, List<FragementTemporaryPic.GoodLevelEntity> subItems) {
            for (FragementTemporaryPic.GoodLevelEntity item : subItems) {
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
        private void expand(int pos, BaseQuickAdapter adapter, FragementTemporaryPic.GoodLevelEntity entity) {
            adapter.getData().addAll(pos + 1, entity.getSubItems()); /*要先添加 要不数据会乱掉*/
            List<FragementTemporaryPic.GoodLevelEntity> needDelete = new ArrayList<>();
            if (entity.getParent() == null) {
                List<FragementTemporaryPic.GoodLevelEntity> level0List = getLevel0List(adapter.getData());
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
        private List<FragementTemporaryPic.GoodLevelEntity> getLevel0List(List<FragementTemporaryPic.GoodLevelEntity> data) {
            List<FragementTemporaryPic.GoodLevelEntity> result = new ArrayList<>();
            for (FragementTemporaryPic.GoodLevelEntity item : data) {
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
        private void expandNeedDelete(List<FragementTemporaryPic.GoodLevelEntity> result, List<FragementTemporaryPic.GoodLevelEntity> subItems) {
            for (FragementTemporaryPic.GoodLevelEntity item : subItems) {
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
            showSharePopupWindow(adapter, position);
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

    private void showSharePopupWindow(BaseQuickAdapter adapter, int position) {

        FragementTemporaryPic.GoodLevelEntity entity = (FragementTemporaryPic.GoodLevelEntity) adapter.getItem(position);
        //如果是临时素材库或者平台素材库就直接返回
        if (!isMyMaterial) {
            toast("该素材库没有权限编辑");
            return;
        }
        contentView = LayoutInflater.from(this.getContext()).inflate(
                R.layout.show_pictrue_popup, null);

        if (!entity.hasSubItem() && !entity.parentId.equals("0")) {
            contentView.findViewById(R.id.ll_add).setVisibility(View.GONE);
        }if(entity.parentId.equals("0")){
            contentView.findViewById(R.id.ll_add_same).setVisibility(View.VISIBLE);
        }
        ShowPopupClick click = new ShowPopupClick(entity);
        contentView.findViewById(R.id.add_same).setOnClickListener(click);
        contentView.findViewById(R.id.add).setOnClickListener(click);
        contentView.findViewById(R.id.edit).setOnClickListener(click);
        contentView.findViewById(R.id.del).setOnClickListener(click);
        contentView.findViewById(R.id.cancel).setOnClickListener(click);
        sharepopupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        sharepopupWindow.setAnimationStyle(R.style.take_photo_anim);
        sharepopupWindow.setTouchable(true);
        sharepopupWindow.setOutsideTouchable(true);
        sharepopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        sharepopupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);

    }

    public class ShowPopupClick implements View.OnClickListener {
        private GoodLevelEntity entity;

        public ShowPopupClick(GoodLevelEntity entity) {
            this.entity = entity;
        }

        @Override
        public void onClick(View v) {
            SelectPicture_new context = (SelectPicture_new) FragementTemporaryPic.this.getContext();
            Intent intent = new Intent(context, ActivityAddPicRes.class);
            intent.putExtra(Constant.INTENT_BASEID, entity.getBaseId());
            switch (v.getId()) {
              case R.id.add_same:
                    intent.putExtra(Constant.INTENT_TYPE, Constant.PIC_ADD);
                    intent.putExtra(Constant.INTENT_PARENTID, entity.getParentId());
                    context.startActivityForResult(intent, ADD_PIC_RES_REQUES_CODE);
                    sharepopupWindow.dismiss();
                    break;
                case R.id.add:
                    intent.putExtra(Constant.INTENT_TYPE, Constant.PIC_ADD);
                    intent.putExtra(Constant.INTENT_PARENTID, entity.getDivId());
                    context.startActivityForResult(intent, ADD_PIC_RES_REQUES_CODE);
                    sharepopupWindow.dismiss();
                    break;
                case R.id.edit:
                    String divName = entity.getDivName();
                    intent.putExtra(Constant.INTENT_TYPE, Constant.PIC_EDIT);
                    intent.putExtra(Constant.INTENT_DIV_NAME, divName);
                    intent.putExtra(Constant.INTENT_DIV_ID, entity.getDivId());
                    context.startActivityForResult(intent, ADD_PIC_RES_REQUES_CODE);
                    sharepopupWindow.dismiss();
                    break;
                case R.id.del:
                    if (entity.hasSubItem()) {
                        toast("有子节点，无法删除");
                        sharepopupWindow.dismiss();
                        return;
                    } else {
                      /*  intent.putExtra(Constant.INTENT_TYPE, Constant.PIC_DEL);
                        intent.putExtra(Constant.INTENT_DIV_ID, entity.getDivId());
                        context.startActivityForResult(intent, ADD_PIC_RES_REQUES_CODE);*/
                        handleDel(entity.getDivId(),entity.getBaseId());
                    }
                    sharepopupWindow.dismiss();
                    break;
                case R.id.cancel:
                    sharepopupWindow.dismiss();
                    break;
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("requestCode FragementTemporaryPic= [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
        if(requestCode==REQUES_CODE&&Constant.isAddPic){
            FragementTemporaryPic.this.getActivity().finish();
        }

    }
    /*删除个人素材库分类(只能删除没有资源，没有子节点的目录)。*/
    private void delDiv(String divId, final String baseId) {
        PublicArg p = Constant.publicArg;
        String timeUUID = Constant.getTimeUUID();
        if(timeUUID.equals("")){
            toastSHORT(getString(R.string.time_out));
            return;
        }
        ResUpdateDiv r = new ResUpdateDiv(p.getSys_token(), timeUUID,Constant.SYS_FUNC101100210001, p.getSys_user(), p.getSys_member(), divId);
        String param = GsonTools.createGsonString(r);
        OkGo.post(Constant.DelDivUrl).params("param", param).execute(new StringCallback() {
            @Override
            public void onSuccess(String data, Call call, Response response) {
                Pub pub = GsonTools.changeGsonToBean(data, Pub.class);
                if (pub.getReturn_code() == 0) {
                    new MyToast(getActivity(), "删除成功");
                    SelectPicture_new context = (SelectPicture_new) FragementTemporaryPic.this.getContext();
                    context.setDelCallBack(baseId);
                }else if(pub.getReturn_code()==1101||pub.getReturn_code()==1102){
                    LogoutUtils.exitUser(FragementTemporaryPic.this);
                }


                else {
                    toastSHORT(pub.getReturn_message());
                    return;
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
            }
        });


    }

    private void handleDel(final String divId,final  String baseId) {

        final MyDialog dialog = new MyDialog(getActivity());
        dialog.setMessage("确定删除分类吗？").setYesOnclickListener("确定", new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                dialog.dismiss();
                delDiv(divId,baseId);
            }
        }).setNoOnclickListener("取消", new MyDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                dialog.dismiss();
            }
        }).show();

    }


    //添加个人素材库分类
    private void addDivForNet(String divName, String parentId, final String baseId) {
        //如果增加顶级目录，parentId传空
        if (parentId.equals("0")) parentId = "";

        PublicArg p = Constant.publicArg;
        String timeUUID = Constant.getTimeUUID();
        if(timeUUID.equals("")){
            toastSHORT(getString(R.string.time_out));
            return;
        }
        ResAddDiv r = new ResAddDiv(p.getSys_token(), timeUUID,Constant.SYS_FUNC101100210001, p.getSys_user(), p.getSys_member(), divName, parentId, baseId);
        String param = mGson.toJson(r);
        waitDialogRectangle.show();
        OkGo.post(Constant.AddDivUrl).params("param", param).execute(new StringCallback() {
            @Override
            public void onSuccess(String data, Call call, Response response) {
                waitDialogRectangle.dismiss();
                Pub pub = GsonTools.changeGsonToBean(data, Pub.class);
                if (pub.getReturn_code() == 0) {
                    SelectPicture_new context = (SelectPicture_new) FragementTemporaryPic.this.getContext();
                    new MyToast(getActivity(), "添加图片目录成功");
                    context.setDelCallBack(baseId);
                   /* Intent intent = new Intent();
                    intent.putExtra(IS_ADD, true);
                    intent.putExtra(BASE_ID, baseId);
                    setResult(RESULT_OK, intent);
                    ActivityAddPicRes.this.finish();*/
                } else if(pub.getReturn_code()==1101||pub.getReturn_code()==1102){
                    LogoutUtils.exitUser(FragementTemporaryPic.this);
                }
                else {
                    toastSHORT(pub.getReturn_message());
                    return;
                }
            }
            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                waitDialogRectangle.dismiss();
            }
        });
        //添加个人素材库分类
    }



}
