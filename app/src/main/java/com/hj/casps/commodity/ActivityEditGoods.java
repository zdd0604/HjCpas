package com.hj.casps.commodity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hj.casps.R;
import com.hj.casps.adapter.TestArrayAdapter;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.appgoods.SelectAllunitName;
import com.hj.casps.entity.appgoods.SelectAllunitNameJson;
import com.hj.casps.entity.goodsmanager.Pub;
import com.hj.casps.entity.goodsmanager.SelectPicture02ListEntity;
import com.hj.casps.entity.goodsmanager.request.RequestCheckName;
import com.hj.casps.entity.goodsmanager.request.RequestCreateGood;
import com.hj.casps.entity.goodsmanager.request.RequestDetailGood;
import com.hj.casps.entity.goodsmanager.request.RequestToUpdateGood;
import com.hj.casps.entity.goodsmanager.response.ResCheckName;
import com.hj.casps.entity.goodsmanager.response.ResToUpdateEntity;
import com.hj.casps.ui.MyGridView;
import com.hj.casps.util.GsonTools;
import com.hj.casps.util.LogoutUtils;
import com.hj.casps.util.StringUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.fancybuttons.FancyButton;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by YaoChen on 2017/4/11.
 * 商品编辑页面
 */

public class ActivityEditGoods extends ActivityBaseHeader2 implements View.OnClickListener {
    public ResToUpdateEntity updateEntity;
    /**
     * 单选按钮
     */
    @BindView(R.id.editGoods_select_pic)
    ImageView selectPic;
    //生产日期
    @BindView(R.id.editGoods_time_Tv)
    TextView productDate;
    @BindView(R.id.goodsedit_gooddetail01_ll)
    LinearLayout title01;
    @BindView(R.id.goodsedit_gooddetail02_ll)
    LinearLayout title02;
    @BindView(R.id.goodsedit_gooddetail01)
    TextView title1;
    @BindView(R.id.goodsedit_gooddetail02)
    TextView title2;
    /**
     * 轮播图
     */
    @BindView(R.id.edit_grid)
    MyGridView gridView;
    @BindView(R.id.editGoods_do_desc_tv)
    TextView top_desc;
    /**
     * 选择分类
     */
    @BindView(R.id.editGoods_select_class_tv)
    TextView good_class;
    //商品名称
    @BindView(R.id.editGoods_name_Et)
    EditText et_goodname;
    //生产地址
    @BindView(R.id.editGoods_pro_addr_Et)
    EditText goodaddress;
    //生产厂家
    @BindView(R.id.editGoods_pro_pre_Et)
    EditText product_Et;
    //生产编号
    @BindView(R.id.editGoods_num_Et)
    EditText product_num;
    //品牌
    @BindView(R.id.editGoods_brand_Et)
    EditText brand_et;
    //库存
    @BindView(R.id.editGoods_stock_Et)
    EditText stock_Et;
    //保质期
    @BindView(R.id.editGoods_shelf_life_Et)
    EditText productTime_et;
    //规格
    @BindView(R.id.editGoods_Specifications_Et)
    EditText Specification_Et;
    //规格单位
    @BindView(R.id.sp_specification)
    Spinner sp_specification;
    //开始价格
    @BindView(R.id.editGoods_price01_Et)
    EditText price_from_Et;
    //结束价格
    @BindView(R.id.editGoods_price02_Et)
    EditText price_to_Et;
    //价格单位
    @BindView(R.id.sp_unitPrice)
    TextView sp_unitPrice;
    //商品描述
    @BindView(R.id.editGoods_descr)
    EditText desc_Et;
    //保存/编辑按钮
    @BindView(R.id.editGoods_true_Btn)
    FancyButton submit_Fcybtn;
    public static Bitmap bimap;
    //判断添加还是编辑页面   true  添加     false  编辑
    private Boolean flag = false;
    private String categoryName;
    private String categoryId;
    private Boolean isRepeat;
    private String imagePath;
    private String imageId;
    private List<SelectAllunitNameJson.AllunitListBean> bean;
    private String[] specificationArr;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                //数据请求成功
                case Constant.HANDLERTYPE_0:
                    break;
                case Constant.HANDLERTYPE_1:
                    if (isRepeat) {
                        toastSHORT("商品名重复");
                        return;
                    } else {
                        submitAddData();
                    }
                    break;
                case Constant.HANDLERTYPE_3:
                    toastSHORT("商品添加提交成功");
                    Constant.isFreshGood = true;
                    setResult(RESULT_OK);
                    ActivityEditGoods.this.finish();
                    break;
                case Constant.HANDLERTYPE_4:
                    toastSHORT("商品编辑提交成功");
                    Constant.isFreshGood = true;
                    setResult(RESULT_OK);
                    ActivityEditGoods.this.finish();
                    break;
                case Constant.HANDLERTYPE_5:
                    getSelectAllunitName();
                    break;
                case Constant.HANDLERTYPE_6:
                    setSpanrAdapter();
                    break;
            }
        }
    };

    private void setSpanrAdapter() {
        sp_specification.setAdapter(new TestArrayAdapter(this, specificationArr));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editgoods);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        initView();
    }


    /**
     * 初始化布局
     */
    private void initView() {
        goodsId = getIntent().getStringExtra(Constant.INTENT_GOODSID);
        flag = getIntent().getBooleanExtra(Constant.INTENTISADDGOODS, false);
        if (flag) {
            setTitle("商品添加");
            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_5);
        } else {
            setTitle(getString(R.string.editgood));
            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_5);
        }
        setTitleRight(null, null);
        selectPic.setOnClickListener(this);
        title1.setOnClickListener(this);
        title2.setOnClickListener(this);
        submit_Fcybtn.setOnClickListener(this);
        top_desc.setOnClickListener(this);
        price_from_Et.addTextChangedListener(new TextOnChangeListener());
        price_to_Et.addTextChangedListener(new TextOnChangeListener());
        findViewById(R.id.editGoods_select_class_Ll).setOnClickListener(this);
        productDate.setOnClickListener(this);
        initData(multCheckDatas);
        adapter = new GridAdapter(this);
        gridView.setAdapter(adapter);

//        String[] unitPriceArr = {"￥", "￥"};

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == (pictrueDatas.size() - 1)) {
                    //原始的
                    Intent intent = new Intent(ActivityEditGoods.this, SelectPicture_new.class);
                    intent.putExtra("PicStyle", 2);
                    startActivity(intent);
                }
            }

        });
    }


    //刷新数据
    private void setDataAndRefrush() {
        List<ResToUpdateEntity.GoodsImagesBean> goodsImages = updateEntity.getGoodsImages();
        for (int i = 0; i < goodsImages.size(); i++) {
            SelectPicture02ListEntity s1 = new SelectPicture02ListEntity();
            //设置的是缩略图
            s1.setImagePath(goodsImages.get(i).getPicPath());
            s1.setImgId(goodsImages.get(i).getMaterialId());
            multCheckDatas.add(s1);
        }
        initData(multCheckDatas);
        adapter.notifyDataSetChanged();
        setGoodInfo();
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));


//        setSinglePicture();
    }

    //设置商品信息
    private void setGoodInfo() {
        ResToUpdateEntity.GoodsInfoBean g = updateEntity.getGoodsInfo();
        //分类id
        categoryId = g.getCategoryId();
        //分类名字
        categoryName = g.getCategoryName();
        imagePath = g.getImgPath();
        Glide.with(this).load(Constant.SHORTHTTPURL + g.getImgPath()).into(selectPic);
        System.out.println("ImagePath===" + g.getImgPath());
        good_class.setText(g.getCategoryName());
        et_goodname.setText(g.getName());
        goodaddress.setText(g.getCreateAddress());
        product_Et.setText(g.getFactory());
        productDate.setText(Constant.stmpToDate(g.getCreateTime()));
        product_num.setText(g.getProductNum());
        brand_et.setText(g.getBrand());
        stock_Et.setText(g.getStockNum() + "");
        productTime_et.setText(g.getProductTime());
        Specification_Et.setText(g.getSpecification());
        //规格

        int positon = Arrays.binarySearch(specificationArr, g.getUnitSpecification());
        int index = 0;
        for (int i = 0; i < specificationArr.length; i++) {
            if (g.getUnitSpecification().equals(specificationArr[i])){
                index = i;
            }
        }
        LogShow(index + "----" + g.getUnitSpecification());
        sp_specification.setSelection(index);

        price_from_Et.setText(g.getMinPrice() + "");
        price_to_Et.setText(g.getMaxPrice() + "");
        desc_Et.setText(g.getDescribed());
    }

    private GridAdapter adapter;
    private ArrayList<SelectPicture02ListEntity> pictrueDatas = new ArrayList<>();
    ArrayList<SelectPicture02ListEntity> multCheckDatas = new ArrayList<>();
    private String goodsId;

    //编辑时候请求的接口
    private void initData2() {
        String timeUUID = Constant.getTimeUUID();
        if (timeUUID.equals("")) {
            toastSHORT(getString(R.string.time_out));
            return;
        }
        RequestToUpdateGood requestToUpdateGood = new RequestToUpdateGood(
                publicArg.getSys_token(),
                timeUUID,
                Constant.SYS_FUNC,
                publicArg.getSys_user(),
                publicArg.getSys_member(),
                goodsId);
        waitDialogRectangle.show();
        OkGo.post(Constant.ToUpdateGoodUrl)
                .params("param", mGson.toJson(requestToUpdateGood))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String data, Call call, Response response) {
                        waitDialogRectangle.dismiss();
                        ResToUpdateEntity entity = GsonTools.changeGsonToBean(data, ResToUpdateEntity.class);
                        if (entity != null && entity.getReturn_code() == 0) {
                            ActivityEditGoods.this.updateEntity = entity;
                            setDataAndRefrush();
                        } else if (entity.getReturn_code() == 1101 || entity.getReturn_code() == 1102) {
                            LogoutUtils.exitUser(ActivityEditGoods.this);
                        } else {
                            toastSHORT(entity.getReturn_message());
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        toastSHORT(e.getMessage());
                        waitDialogRectangle.dismiss();
                    }
                });
    }

    private void initData(ArrayList<SelectPicture02ListEntity> multCheckDatas) {
        pictrueDatas.clear();
        if (multCheckDatas != null && multCheckDatas.size() > 0) {
            for (int i = 0; i < multCheckDatas.size(); i++) {
                pictrueDatas.add(multCheckDatas.get(i));
            }
            SelectPicture02ListEntity listEntity = new SelectPicture02ListEntity();
            pictrueDatas.add(listEntity);
        } else {
            SelectPicture02ListEntity listEntity = new SelectPicture02ListEntity();
            pictrueDatas.add(listEntity);
        }
    }


    /**
     * 日历控件的显示
     */
    private void calendarShow() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(ActivityEditGoods.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                productDate.setText(new StringBuilder().append(year).append("-")
                        .append((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1))
                        .append("-").append((dayOfMonth < 10) ? "0" + dayOfMonth : dayOfMonth));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    /**
     * 表单提交的本地判断
     *
     * @return
     */
    private boolean isCheck() {
        if (good_class.getText().length() == 0) {
            toast(getString(R.string.error_good_class));
            return false;
        }
        if (et_goodname.getText().length() == 0) {
            toast(getString(R.string.error_good_name));
            return false;
        }
        if (price_from_Et.getText().length() == 0) {
            toast(getString(R.string.error_good_price));
            return false;
        }
        if (price_to_Et.getText().length() == 0) {
            toast(getString(R.string.error_good_price));
            return false;
        }
        maxlengthToast(et_goodname);
        maxlengthToast(goodaddress);
        maxlengthToast(product_Et);
        maxlengthToast(product_num);
        maxlengthToast(brand_et);
        maxlengthToast(productTime_et);
        maxlengthToast(Specification_Et);
        maxlengthToast(price_from_Et);
        maxlengthToast(price_to_Et);
        if (desc_Et.getText().length() > 200) {
            toast(getString(R.string.error_lengtn_200));
            return false;
        }

        return true;
    }

    private boolean maxlengthToast(EditText Et) {
        if (Et.getText().length() > 50) {
            toast(getString(R.string.error_lengtn_50));
            return false;
        }
        return true;
    }

    /**
     * 更换textView的drawable
     *
     * @param id
     * @param tv
     */
    private void setDrawable(int id, TextView tv) {
        Drawable topDrawable = ContextCompat.getDrawable(this, id);
        topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
        tv.setCompoundDrawables(null, null, topDrawable, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editGoods_do_desc_tv:
                //操作说明
                CreateDialog(Constant.DIALOG_CONTENT_30);
                break;
            case R.id.editGoods_select_pic:
                //原始的
                Intent intent = new Intent(ActivityEditGoods.this, SelectPicture_new.class);
                intent.putExtra("PicStyle", 1);
                startActivity(intent);
                break;
            //商品分类选择项
            case R.id.editGoods_select_class_Ll:
                startActivityForResult(new Intent(ActivityEditGoods.this, SelectClass.class), 0);
                break;
            case R.id.editGoods_time_Tv:
                calendarShow();
                break;
            case R.id.goodsedit_gooddetail01:
                if (title01.getVisibility() == View.VISIBLE) {
                    title01.setVisibility(View.GONE);
                    setDrawable(R.mipmap.jt2, title1);
                } else {
                    title01.setVisibility(View.VISIBLE);
                    setDrawable(R.mipmap.jt3, title1);

                }
                break;
            case R.id.goodsedit_gooddetail02:
                if (title02.getVisibility() == View.VISIBLE) {
                    title02.setVisibility(View.GONE);
                    setDrawable(R.mipmap.jt2, title2);
                } else {
                    title02.setVisibility(View.VISIBLE);
                    setDrawable(R.mipmap.jt3, title2);
                }
                break;
            //保存按钮
            case R.id.editGoods_true_Btn:
                //编辑或者新增的判断
                submitCommodity();
                break;
        }
    }

    /**
     * 判断条件是否满足提交
     */
    private void submitCommodity() {
        if (categoryId == null || categoryId.equals("")) {
            toastSHORT("必须选择分类");
            return;
        }
        if (this.et_goodname.getText().toString().trim().equals("")) {
            toastSHORT("商品名不能为空");
            return;
        }
//        if (!StringUtils.isStrTrue(getTvVaule(productDate))) {
//            toastSHORT("生产日期不能为空");
//            return;
//        }
//        if (!StringUtils.isStrTrue(getEdVaule(stock_Et))) {
//            toastSHORT("库存不能为空");
//            return;
//        }
//        if (!StringUtils.isStrTrue(getTvVaule(productTime_et))) {
//            toastSHORT("保质期不能为空");
//            return;
//        }
        if (!StringUtils.isStrTrue(getEdVaule(price_from_Et))) {
            toastSHORT("最低单价不能为空");
            return;
        }
        if (!StringUtils.isStrTrue(getEdVaule(price_to_Et))) {
            toastSHORT("最高单价不能为空");
            return;
        }
//        if (imagePath == null || imagePath.equals("")) {
//            toastSHORT("请选择标题");
//            return;
//        }
        //如果是添加 需要验证  商品名称是否唯一
        if (flag) {
            checkNameGoNet();
        } else {
            submitDetailData();
        }
    }

    //添加商品提交接口
    private void submitAddData() {
        String timeUUID = Constant.getTimeUUID();
        if (timeUUID.equals("")) {
            toastSHORT(getString(R.string.time_out));
            return;
        }

        String createAddress = getEdVaule(goodaddress);
        String factory = getEdVaule(product_Et);
        String product_num = getEdVaule(this.product_num);
        String brand = getEdVaule(brand_et);
        String stockNum = getEdVaule(stock_Et);
        String productTime = getEdVaule(productTime_et);
        String specification = getEdVaule(Specification_Et);
        String minPrice = getEdVaule(price_from_Et);
        String maxPrice = getEdVaule(price_to_Et);
        String described = getEdVaule(desc_Et);
        String name = getEdVaule(et_goodname);
        String createTime = productDate.getText().toString().trim();
        String unitPrice = sp_unitPrice.getText().toString().trim();
        String unitSpecification = sp_specification.getSelectedItem().toString();
        String subImagePath = imagePath.substring(17);

        //轮播图ids
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pictrueDatas.size() - 1; i++) {
            SelectPicture02ListEntity entity = pictrueDatas.get(i);
            if (i != pictrueDatas.size() - 1 - 1) {
                sb.append(entity.getImgId() + ",");
            } else {
                sb.append(entity.getImgId());
            }
        }
        String imageIds = sb.toString();

        //TODO imageIds暂时没有传递值
        RequestCreateGood r = new RequestCreateGood(
                publicArg.getSys_token(),
                timeUUID, Constant.SYS_FUNC,
                publicArg.getSys_user(),
                publicArg.getSys_member(),
                categoryId,
                createTime,
                createAddress,
                factory,
                product_num,
                brand,
                stockNum,
                productTime,
                specification,
                unitSpecification,
                minPrice,
                maxPrice,
                unitPrice,
                described,
                imageId,
                subImagePath,
                imageIds, name);

        waitDialogRectangle.show();
        OkGo.post(Constant.CreateGoodUrl)
                .params("param", mGson.toJson(r))
                .execute(new AbsCallback<Pub>() {

                    @Override
                    public Pub convertSuccess(Response response) throws Exception {
                        String data = response.body().string();
                        Pub pub = GsonTools.changeGsonToBean(data, Pub.class);
                        int return_code = pub.getReturn_code();
                        String return_message = pub.getReturn_message();
                        waitDialogRectangle.dismiss();
                        if (return_code == 0) {
                            mHandler.sendEmptyMessage(3);
                        } else if (return_code == 1101 || return_code == 1102) {
                            LogoutUtils.exitUser(ActivityEditGoods.this);
                        } else {
                            waitDialogRectangle.dismiss();
                            toast(return_message);
                        }
                        return null;
                    }

                    @Override
                    public void onSuccess(Pub pub, Call call, Response response) {
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        waitDialogRectangle.dismiss();
                    }
                });
    }

    //提交详情
    private void submitDetailData() {
        if (!StringUtils.isStrTrue(getTvVaule(productDate))) {
            toastSHORT("生产日期不能为空");
            return;
        }
        if (categoryId == null || categoryId.equals("")) {
            toastSHORT("必须选择分类");
            return;
        } else if (this.et_goodname.getText().toString().trim().equals("")) {
            toastSHORT("商品名不能为空");
            return;
        } else if (getEdVaule(price_from_Et).equals("")) {
            toastSHORT("最低单价不能为空");
            return;
        } else if (getEdVaule(price_to_Et).equals("")) {
            toastSHORT("最高单价不能为空");
            return;
        } else if (imagePath == null || imagePath.equals("")) {
            toastSHORT("请选择标题");
            return;
        }
        String timeUUID = Constant.getTimeUUID();
        if (timeUUID.equals("")) {
            toastSHORT(getString(R.string.time_out));
            return;
        }
        String createAddress = getEdVaule(goodaddress);
        String factory = getEdVaule(product_Et);
        String product_num = getEdVaule(this.product_num);
        String brand = getEdVaule(brand_et);
        String stockNum = getEdVaule(stock_Et);
        String productTime = getEdVaule(productTime_et);
        String minPrice = getEdVaule(price_from_Et);
        String maxPrice = getEdVaule(price_to_Et);
        String described = getEdVaule(desc_Et);
        String name = getEdVaule(et_goodname);
        String specification = getEdVaule(Specification_Et);

        String createTime = productDate.getText().toString().trim();
        //截取字符串数据
        String subImagePath = imagePath.substring(17);
        //轮播图ids
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pictrueDatas.size() - 1; i++) {
            SelectPicture02ListEntity entity = pictrueDatas.get(i);
            //第一个1代表后面的加号 第二个代表size-1
            if (i != pictrueDatas.size() - 1 - 1) {
                sb.append(entity.getImgId() + ",");
            } else {
                sb.append(entity.getImgId());
            }
        }
        String imageIds = sb.toString();

        //规格单位

        //价格单位
        String unitPrice = sp_unitPrice.getText().toString().trim();
        String unitSpecification = sp_specification.getSelectedItem().toString();
        RequestDetailGood r = new RequestDetailGood(
                publicArg.getSys_token(),
                timeUUID,
                Constant.SYS_FUNC,
                publicArg.getSys_user(),
                publicArg.getSys_member(),
                goodsId, categoryId,
                createAddress,
                factory,
                product_num,
                brand,
                stockNum,
                productTime,
                specification,
                unitSpecification,
                minPrice,
                maxPrice,
                unitPrice,
                described,
                imageId,
                subImagePath,
                imageIds,
                name,
                createTime);
        waitDialogRectangle.show();
        OkGo.post(Constant.UpdateGoodDetailUrl)
                .params("param", mGson.toJson(r))
                .execute(new AbsCallback<Pub>() {
                    @Override
                    public Pub convertSuccess(Response response) throws Exception {
                        String data = response.body().string();
                        Pub pub = GsonTools.changeGsonToBean(data, Pub.class);
                        int return_code = pub.getReturn_code();
                        String return_message = pub.getReturn_message();
                        waitDialogRectangle.dismiss();
                        if (return_code == 0) {
//                    toast("添加详情商品成功");
                            mHandler.sendEmptyMessage(4);
                        } else if (return_code == 1101 || return_code == 1102) {
                            LogoutUtils.exitUser(ActivityEditGoods.this);
                        } else {
                            toast(return_message);
                        }
                        return null;
                    }

                    @Override
                    public void onSuccess(Pub pub, Call call, Response response) {

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        waitDialogRectangle.dismiss();
                    }
                });

    }

    /*  @Override
      protected void onPause() {
          super.onPause();
          PublicDatas02.getInstance().setStylePic(-1);
          PublicDatas02.getInstance().setMultCheckList(null);
      }*/
    private void checkNameGoNet() {
        //如果是详情的话，验证是不是原有的商品名称，如果一样就直接可以上传
        /*if (!flag) {
            String editGoodName = updateEntity.getGoodsInfo().getName();
            String goodname = this.et_goodname.getText().toString().trim();
            if (editGoodName.equals(goodname)) {
                isRepeat = false;
                submitDetailData();
                return;
            }
        }*/
        String goodname = et_goodname.getText().toString().trim();

        RequestCheckName requestCheckName = new RequestCheckName(
                publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                publicArg.getSys_user(),
                publicArg.getSys_member(),
                categoryId + "",
                goodname);
        OkGo.post(Constant.CheckGoodName).
                params("param", mGson.toJson(requestCheckName)).
                execute(new StringCallback() {
                    @Override
                    public void onSuccess(String data, Call call, Response response) {
                        ResCheckName checkName = GsonTools.changeGsonToBean(data, ResCheckName.class);
                        if (checkName != null && checkName.getReturn_code() == 0) {
                            ActivityEditGoods.this.isRepeat = checkName.isRepeat();
                            mHandler.sendEmptyMessage(1);
                        } else if (checkName.getReturn_code() == 1101 || checkName.getReturn_code() == 1102) {
                            LogoutUtils.exitUser(ActivityEditGoods.this);
                        } else {
                            toastSHORT(checkName.getReturn_message());
                        }
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            categoryName = (String) data.getExtras().get(Constant.INTENT_GOODSNAME);
            categoryId = (String) data.getExtras().get(Constant.INTENT_GOODCATEGORYID);
            good_class.setText(categoryName);
        }
    }

    /**
     * 接口url：appGoods/selectAllunitName
     * 功能描述：查询所有单位列表list
     */
    public void getSelectAllunitName() {
        SelectAllunitName seName = new SelectAllunitName(
                publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                publicArg.getSys_user(),
                publicArg.getSys_name(),
                publicArg.getSys_member()
        );

        LogShow(mGson.toJson(seName));
        OkGo.post(Constant.SelectAllunitNameUrl)
                .tag(this)
                .params("param", mGson.toJson(seName))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String data, Call call, Response response) {
                        LogShow(data);

                        SelectAllunitNameJson entity = mGson.fromJson(data, SelectAllunitNameJson.class);
                        if (entity.getReturn_code() != 0) {
                            toastSHORT(entity.getReturn_message());
                            return;
                        }
                        bean = entity.getAllunitList();
                        specificationArr = new String[bean.size()];
                        for (int i = 0; i < bean.size(); i++) {
                            specificationArr[i] = bean.get(i).getUnitName();
                        }
                        if (specificationArr.length > 0)
                            mHandler.sendEmptyMessage(Constant.HANDLERTYPE_6);
                        if (!flag) {
                            initData2();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (!flag) {
                            initData2();
                        }
                    }
                });
    }

    /*//验证表单
    private Boolean isCheckForm() {
        String goodclass = good_class.getText().toString().trim();
        String goodnameStr = et_goodname.getText().toString().trim();
        String price_from = price_from_Et.getText().toString().trim();
        String price_to = price_to_Et.getText().toString().trim();
        if (goodclass == null || goodclass.equals("")) {
            toast("商品分类不能为空");
            return false;
        } else if (goodnameStr == null || goodnameStr.equals("")) {
            toast("商品名称不能为空");
            return false;
        } else if (price_from == null || price_from.equals("")) {
            toast("开始价格不能为空");
            return false;
        } else if (price_to == null || price_from.equals("")) {
            toast("结束价格不能为空");
            return false;
        }

        return true;
    }
*/

    /**
     * 选择轮播图的GridView
     */
    public class GridAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return pictrueDatas.size();
        }

        public SelectPicture02ListEntity getItem(int arg0) {
            return pictrueDatas.get(arg0);
        }

        public long getItemId(int arg0) {
            return arg0;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.editgoods_grid_item, null);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.editGoods_grid_select_pic);
                holder.imageDelete = (ImageView) convertView
                        .findViewById(R.id.editGoods_grid_select_pic_delete);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            SelectPicture02ListEntity item = getItem(position);
//            if (position == pictrueDatas.size()) {
            Glide.with(ActivityEditGoods.this).load(Constant.SHORTHTTPURL + item.getImagePath()).into(holder.image);
            holder.image.setBackgroundColor(Color.TRANSPARENT);
//                holder.image.setImageResource(R.mipmap.sign_logo);
            holder.imageDelete.setVisibility(View.VISIBLE);
            if (item.getImagePath() == null) {
                holder.image.setBackgroundResource(R.mipmap.up_sc);
                holder.imageDelete.setVisibility(View.GONE);
            }
            //如果是最后一条就显示加号图片
            holder.imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    pictrueDatas.remove(position);
                    adapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }


        public class ViewHolder {
            public ImageView image;
            public ImageView imageDelete;
        }

    }

    /**
     * 输入框只能输入最多两位小数的数字
     */
    private class TextOnChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String temp = s.toString();
            int posDot = temp.indexOf(".");
            if (posDot <= 0) return;
            if (temp.length() - posDot - 1 > 2) {
                s.delete(posDot + 3, posDot + 4);
            }
        }
    }


    //接收图片库传过来的图片数据    单选或者多选的时候
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ImageData imageData) {
        if (imageData.getList() != null && imageData.getList().size() > 0) {
            ArrayList<SelectPicture02ListEntity> list = (ArrayList<SelectPicture02ListEntity>) imageData.getList();
            ArrayList<SelectPicture02ListEntity> doList = new ArrayList<>();
            pictrueDatas.remove(pictrueDatas.size() - 1);
            if (pictrueDatas.size() == 0) {
                doList.addAll(list);
            } else {
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < pictrueDatas.size(); j++) {
                        if (!list.get(i).getImgId().equals(pictrueDatas.get(j).getImgId())) {
                            doList.add(list.get(i));
                            break;
                        }
                    }
                }
            }
            if (doList.size() > 0) {
                for (int i = 0; i < doList.size(); i++) {
                    pictrueDatas.add(doList.get(i));
                }
            }
            list.clear();
            list.addAll(pictrueDatas);
//            pictrueDatas.addAll(list);
            initData(list);
            adapter.notifyDataSetChanged();
        }
        if (imageData.getEntity() != null) {
            imageId = imageData.getEntity().getImgId();
            imagePath = imageData.getEntity().getImagePath();
            Glide.with(this).load(Constant.SHORTHTTPURL + imageData.getEntity().getImagePath()).into(selectPic);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
