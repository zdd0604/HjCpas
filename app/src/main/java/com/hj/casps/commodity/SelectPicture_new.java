package com.hj.casps.commodity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.alibaba.fastjson.JSONObject;
import com.hj.casps.R;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.base.FragmentBase;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.PublicArg;
import com.hj.casps.entity.picturemanager.ShowBaseEntity;
import com.hj.casps.entity.picturemanager.request.RequestShowBase;
import com.hj.casps.util.DensityUtil;
import com.hj.casps.util.GsonTools;
import com.hj.casps.util.LogoutUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 选择轮播目录
 * Created by YaoChen on 2017/4/13.
 */

public class SelectPicture_new extends ActivityBaseHeader2 {
    private int mIndex = 0;
    ViewPager viewPager;
    private int PageFlag;
    private MagicIndicator indicator;
    private RecyclerView rv;
    private List<ShowBaseEntity.DataListBean> showBaseList;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                // 请求资源库列表成功
                case 0:
                    break;
                //设置fragment数据
                case 1:
                    break;

            }
        }
    };
    private List<SelectPicture_new.DataTreeListEntity> showDivList;
    private JiaPuViewPageAdapter adapter;
    private String baseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_selectpic);
        PageFlag = getIntent().getIntExtra("PicStyle", 0);
        if (PageFlag == 1) {
            setTitle(getString(R.string.select_title_pic));
        } else if (PageFlag == 2) {
            setTitle(getString(R.string.select_banner_pic));
        }
        initView();

        initShowBaseData();

    }

    /**
     * 设置上侧导航条数据
     */
    private void setShowBaseData() {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return showBaseList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(showBaseList.get(index).getBaseName());
                simplePagerTitleView.setNormalColor(Color.BLACK);
                simplePagerTitleView.setTextSize(16);
                simplePagerTitleView.setSelectedColor(R.color.tv_blue);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mIndex = index;
                        initShowDivData(showBaseList.get(index).getBaseId());
                    }
                });
                return simplePagerTitleView;
            }


            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(Color.parseColor("#40c4ff"));
                return null;
            }
        });

        indicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerPadding(DensityUtil.dip2px(SelectPicture_new.this, 15));
        titleContainer.setDividerDrawable(getResources().getDrawable(R.drawable.simple_splitter));
        ViewPagerHelper.bind(indicator, viewPager);

    }


    private void initShowBaseData() {
        PublicArg p = Constant.publicArg;
        RequestShowBase r = new RequestShowBase(
                p.getSys_token(), Constant.getUUID(),
                Constant.SYS_FUNC,
                p.getSys_user(),
                p.getSys_member());
        String param = mGson.toJson(r);
        OkGo.post(Constant.ShowBaseUrl).params("param", param).execute(new StringCallback() {
            @Override
            public void onSuccess(String data, Call call, Response response) {
                ShowBaseEntity showBaseEntity = GsonTools.changeGsonToBean(data, ShowBaseEntity.class);
                if (showBaseEntity != null) {
                    if (showBaseEntity.getReturn_code() == 0) {
                        showBaseList = showBaseEntity.getDataList();
                        initShowDivData(showBaseList.get(mIndex).getBaseId());
                    } else if (showBaseEntity.getReturn_code() == 1101 || showBaseEntity.getReturn_code() == 1102) {
                        LogoutUtils.exitUser(SelectPicture_new.this);
                    } else {
                        toastSHORT(showBaseEntity.getReturn_message());
                    }
                }
            }
        });

    }


//解析json

    private void handleJson(String data) {

        Entity entity = JSONObject.parseObject(data, Entity.class);
        if (entity.getReturn_code() == 1101 || entity.getReturn_code() == 1102) {
            LogoutUtils.exitUser(SelectPicture_new.this);
        }
        List<DataListEntity> sourceList = entity.getDataList();
        List<DataTreeListEntity> treeList = getChild(sourceList, "0");
        for (int i = 0, size = treeList.size(); i < size; i++) {
            System.out.println(treeList.get(i));
        }

        String name = Thread.currentThread().getName();
        System.out.println("name = [" + name + "]");
        showDivList = treeList;
//        bigList.add(showDivList);

        if (mIndex == 0) {
            setShowBaseData();
        }
        setFragmentData();
    }

    /**
     * 设置fragment数据
     */
    private void setFragmentData() {
        ArrayList<FragmentBase> fragList = new ArrayList<>();
        for (int i = 0; i < showBaseList.size(); i++) {
            FragementTemporaryPic e = new FragementTemporaryPic(
                    PageFlag,
                    showDivList,
                    showBaseList.get(mIndex).getBaseId());
            fragList.add(e);
        }

        adapter = new JiaPuViewPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        adapter.addFragment(fragList);
        viewPager.setCurrentItem(mIndex);

    }


    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.selectpic01_vp);
        rv = (RecyclerView) findViewById(R.id.rv);
        indicator = (MagicIndicator) findViewById(R.id.mi_magicIndicator);

    }

    private void initShowDivData(String baseId) {
        if (baseId.equals("4") || baseId.equals("19")) {
            titleRight.setVisibility(View.GONE);

        } else {
            SelectPicture_new.this.baseId = baseId;
//            titleRight.setVisibility(View.VISIBLE);
//            titleRight.setText("添加");
//            titleRight.setBackgroundColor(Color.TRANSPARENT);
//            titleRight.setTextSize(R.dimen.font_size_16);
        }

        PublicArg p = Constant.publicArg;
        RequestShowBase r = new RequestShowBase(p.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                p.getSys_user(),
                p.getSys_member(),
                baseId);
        String param = mGson.toJson(r);
//        String url="http://192.168.1.120:8081/v2/appMaterial/showDiv.app?param={\"sys_member\":\"testshop001\",\"sys_user\":\"e6ae4ad55d5b44769d2a54a0fedbfff7\",\"sys_token\":\"x4jiwtk2eyq8bsg9\",\"sys_func\":\"\",\"baseId\":\"19\"}";
        waitDialogRectangle.show();
        OkGo.post(Constant.ShowDivUrl).params("param", param).execute(new StringCallback() {
            @Override
            public void onSuccess(String data, Call call, Response response) {
                waitDialogRectangle.dismiss();
                handleJson(data);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                waitDialogRectangle.dismiss();
            }
        });

    }


    private class JiaPuViewPageAdapter extends FragmentStatePagerAdapter {
        private List<FragmentBase> list = new ArrayList<>();

        public JiaPuViewPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        //            bundle.putParcelableArrayList(Constant.INTENT_SHOW_DIV, (ArrayList<SelectPicture_new.DataTreeListEntity>) bigList.get(position));
//            bundle.putString(Constant.INTENT_BASE_ID,showDivList);
        @Override
        public Fragment getItem(int position) {
           /* Bundle bundle = new Bundle();
            bundle.putInt("PicStyle", PageFlag);
            String baseId = showBaseList.get(mIndex).getBaseId();
            bundle.putString(Constant.INTENT_BASEID, baseId);
            bundle.putParcelableArrayList(Constant.INTENT_SHOW_DIV, (ArrayList<SelectPicture_new.DataTreeListEntity>) showDivList);
            list.get(position).setArguments(bundle);*/
            return list.get(position);
        }

        public void addFragment(List<FragmentBase> f) {
            list.clear();
            list.addAll(f);
            notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private class JiapuRgChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            if (checkedId == R.id.selectpic01_temporary_rb) {
                viewPager.setCurrentItem(0);
            } else if (checkedId == R.id.selectpic01_platform_rb) {
                viewPager.setCurrentItem(1);
            } else {
                viewPager.setCurrentItem(2);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("requestCode SelectPicture_new = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
        //这里是ActivityAddPicRes所传过来的值  判断是否添加目录分类成功的  ActivityAddPicRes是FragementTemporaryPic打开的
        if (resultCode == RESULT_OK && requestCode == FragementTemporaryPic.ADD_PIC_RES_REQUES_CODE) {
            boolean is_add = data.getBooleanExtra(ActivityAddPicRes.IS_ADD, false);
            String baseid = data.getStringExtra(ActivityAddPicRes.BASE_ID);
            System.out.println("requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
            if (is_add) {
                initShowDivData(baseid);
            }
        }
    }


    public static class Entity {

        /**
         * listEntities : [{"divId":"101","divName":"小图","parentdivId":"0"},{"divId":"102","divName":"大图","parentdivId":"0"},{"divId":"1013207","divName":"蔬菜","parentdivId":"101"},{"divId":"101535c","divName":"水果","parentdivId":"101"},{"divId":"10170a2","divName":"肉类","parentdivId":"101"},{"divId":"10163bf","divName":"水产","parentdivId":"101"},{"divId":"101c627","divName":"米类","parentdivId":"101"},{"divId":"1016edb","divName":"面类","parentdivId":"101"},{"divId":"101b590","divName":"杂粮","parentdivId":"101"},{"divId":"101b4bb","divName":"食用油","parentdivId":"101"},{"divId":"101d906","divName":"调料","parentdivId":"101"},{"divId":"101abc5","divName":"蔬菜大图","parentdivId":"101"},{"divId":"1028b83","divName":"水果大图","parentdivId":"102"},{"divId":"102e44b","divName":"肉类大图","parentdivId":"102"},{"divId":"102705a","divName":"水产大图","parentdivId":"102"},{"divId":"10297a6","divName":"米类大图","parentdivId":"102"},{"divId":"102c479","divName":"面类大图","parentdivId":"102"},{"divId":"1025979","divName":"杂粮大图","parentdivId":"102"},{"divId":"1029149","divName":"食用油大图","parentdivId":"102"},{"divId":"102ee80","divName":"调料大图","parentdivId":"102"},{"divId":"103","divName":"其他图片","parentdivId":"0"},{"divId":"1033fbb","divName":"背景图片","parentdivId":"103"},{"divId":"103d8ec","divName":"证书、奖章","parentdivId":"103"}]
         * return_code : 0
         * return_message : 成功
         */

        private int return_code;
        private String return_message;
        /**
         * divId : 101
         * divName : 小图
         * parentdivId : 0
         */

        private List<DataListEntity> dataList;

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

        public List<DataListEntity> getDataList() {
            return dataList;
        }

        public void setDataList(List<DataListEntity> dataList) {
            this.dataList = dataList;
        }


    }

    public static class DataTreeListEntity implements Parcelable {
        List<DataTreeListEntity> listEntities;
        DataListEntity entity;

        protected DataTreeListEntity(Parcel in) {
            listEntities = in.createTypedArrayList(DataTreeListEntity.CREATOR);
        }

        public static final Creator<DataTreeListEntity> CREATOR = new Creator<DataTreeListEntity>() {
            @Override
            public DataTreeListEntity createFromParcel(Parcel in) {
                return new DataTreeListEntity(in);
            }

            @Override
            public DataTreeListEntity[] newArray(int size) {
                return new DataTreeListEntity[size];
            }
        };

        @Override
        public String toString() {
            return "DataTreeListEntity{" +
                    "listEntities=" + listEntities +
                    ", entity=" + entity +
                    '}';
        }

        public DataTreeListEntity(DataListEntity entity) {
            this.entity = entity;
        }

        public DataListEntity getEntity() {
            return entity;
        }

        public List<DataTreeListEntity> getListEntities() {
            return listEntities;
        }

        public void setListEntities(List<DataTreeListEntity> dataList) {
            this.listEntities = dataList;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(listEntities);
        }
    }

    public static class DataListEntity {
        private String divId;
        private String divName;
        private String parentdivId;

        @Override
        public String toString() {
            return "DataListEntity{" +
                    "divId='" + divId + '\'' +
                    ", divName='" + divName + '\'' +
                    ", parentdivId='" + parentdivId + '\'' +
                    '}';
        }

        public String getDivId() {
            return divId;
        }

        public void setDivId(String divId) {
            this.divId = divId;
        }

        public String getDivName() {
            return divName;
        }

        public void setDivName(String divName) {
            this.divName = divName;
        }

        public String getParentdivId() {
            return parentdivId;
        }

        public void setParentdivId(String parentdivId) {
            this.parentdivId = parentdivId;
        }
    }


    public static List<DataTreeListEntity> getChild(List<DataListEntity> sourceList, String level) {
        List<DataTreeListEntity> treeList = new ArrayList<DataTreeListEntity>();
        for (DataListEntity item : sourceList) {
            if (level.equals(item.getParentdivId())) {
                DataTreeListEntity treeListEntity = new DataTreeListEntity(item);
                treeList.add(treeListEntity);
                String divId = treeListEntity.getEntity().getDivId();
                treeListEntity.setListEntities(getChild(sourceList, divId));

            }
        }
        return treeList;
    }

    @Override
    protected void onRightClick() {
        Intent intent = new Intent(this, ActivityAddPicRes.class);
        intent.putExtra(Constant.INTENT_TYPE, Constant.PIC_ADD);
        intent.putExtra(Constant.INTENT_BASEID, baseId);
        //添加一级目录的  parentId  传 0    到  ActivityAddPicRes判断的传0  就传空  传空就说明是一级目录了
        intent.putExtra(Constant.INTENT_PARENTID, "0");
        startActivityForResult(intent, FragementTemporaryPic.ADD_PIC_RES_REQUES_CODE);
    }

    protected void setDelCallBack(String baseId) {
        initShowDivData(baseId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constant.isAddPic = false;
        System.out.println("SelectPicture_new onDestroy ");
    }
}
