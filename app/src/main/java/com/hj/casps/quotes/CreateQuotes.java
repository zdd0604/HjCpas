package com.hj.casps.quotes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hj.casps.R;
import com.hj.casps.adapter.TestArrayAdapter;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.commodity.ActivityManageGoods;
import com.hj.casps.commodity.ImageData;
import com.hj.casps.commodity.SelectPicture_new;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.appQuote.CreateBean;
import com.hj.casps.entity.appQuote.CreateModel;
import com.hj.casps.entity.appQuote.EditQuoteEntity;
import com.hj.casps.entity.appQuote.ReturnBean;
import com.hj.casps.util.LogoutUtils;
import com.hj.casps.util.StringUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Response;

//新建或者编辑报价管理
public class CreateQuotes extends ActivityBaseHeader2 implements View.OnClickListener {
    private TextView quote_create_info;
    private EditText product_name;
    private Spinner product_type;
    private ImageView chooose_product_pic;
    private EditText product_number;
    private EditText product_price_from;
    private EditText product_price_to;
    private EditText product_time_from;
    private EditText product_time_to;
    private EditText product_more;
    private Button create_quotes_commit;
    private String[] quotesTypeItems;
    private Calendar c = Calendar.getInstance();
    private TestArrayAdapter stringArrayAdapter;
    private boolean getData;
    private String id = "";//报价id
    private String imagePath;
    private String imageId;
    private String url;
    private boolean newOne;
    private String goodsId;
    private String goodsName;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    product_name.setText(goodsName);
                    break;

            }
        }
    };

    //接收选择的产品名称和id
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == RESULT_OK) {
            goodsName = Constant.GOODS_NAME;
            goodsId = Constant.GOODS_ID;
            handler.sendEmptyMessage(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quotes);
        EventBus.getDefault().register(this);
        initView();
        initData();
    }

    //判断是否是新建报价，如果不是，则从网络获取编辑报价需要的参数
    private void initData() {
        id = getIntent().getStringExtra("data");
        if (id == null || id.isEmpty()) {
            newOne = true;
            product_name.setOnClickListener(this);
            setTitle(getString(R.string.quotes_create));
        } else {
            newOne = false;
            setTitle(getString(R.string.quotes_edit_t));
            EditQuoteEntity editQuoteEntity = new EditQuoteEntity(
                    publicArg.getSys_token(),
                    Constant.getUUID(),
                    Constant.SYS_FUNC,
                    publicArg.getSys_user(),
                    publicArg.getSys_member(),
                    id
            );
            OkGo.post(Constant.EditQuoteUrl)
                    .params("param", mGson.toJson(editQuoteEntity)).
                    execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            initGson(s);
                        }
                    });
        }

    }

    //对编辑报价进行解析并显示图片，数据保存
    private void initGson(String response) {
        Log.d("response", response);
//        CreateModel.MapBean quoteModels = new CreateModel.MapBean();
        Gson gson = new Gson();
        try {
            CreateModel.MapBean quotesBack = gson.fromJson(response, CreateModel.MapBean.class);
            newOne = false;
            if (quotesBack != null) {
                product_name.setText(quotesBack.getGoodsName());
                product_number.setText(String.valueOf(quotesBack.getNum()));
                product_price_from.setText(String.valueOf(quotesBack.getMinPrice()));
                product_price_to.setText(String.valueOf(quotesBack.getMaxPrice()));
                product_time_from.setText(Constant.stmpToDate(quotesBack.getStartTime()));
                if (quotesBack.getImgPath() != null) {
                    Glide.with(this).load(quotesBack.getImgPath().startsWith("/v2content") ?
                            Constant.SHORTHTTPURL + quotesBack.getImgPath() :
                            Constant.HTTPURLIMAGE + quotesBack.getImgPath()).into(chooose_product_pic);
                }
                goodsId = quotesBack.getGoodsId();
                if (!product_time_from.getText().toString().isEmpty()) {
                    product_time_from.setCompoundDrawables(null, null, null, null);
                }
                product_time_to.setText(Constant.stmpToDate(quotesBack.getStartEnd()));
                if (!product_time_to.getText().toString().isEmpty()) {
                    product_time_to.setCompoundDrawables(null, null, null, null);
                }
                product_more.setText(quotesBack.getExplan());

                switch (quotesBack.getType()) {
                    default:
                    case 0:
                        product_type.setSelection(0, true);
                        break;
                    case 1:
                        product_type.setSelection(1, true);
                        break;

                }
            }
        } catch (JsonSyntaxException j) {
            LogShow("类型转换异常");
        }
    }

    private void initView() {
        quotesTypeItems = new String[]{"采购", "销售"};
        titleRight.setVisibility(View.GONE);

        quote_create_info = (TextView) findViewById(R.id.quote_create_info);
        product_name = (EditText) findViewById(R.id.product_name);
        product_type = (Spinner) findViewById(R.id.product_type);
        chooose_product_pic = (ImageView) findViewById(R.id.chooose_product_pic);
        product_number = (EditText) findViewById(R.id.product_number);
        product_price_from = (EditText) findViewById(R.id.product_price_from);
        setPricePoint(product_price_from);
        product_price_to = (EditText) findViewById(R.id.product_price_to);
        setPricePoint(product_price_to);
        product_time_from = (EditText) findViewById(R.id.product_time_from);
        product_time_to = (EditText) findViewById(R.id.product_time_to);
        product_more = (EditText) findViewById(R.id.product_more);
        create_quotes_commit = (Button) findViewById(R.id.create_quotes_commit);

        quote_create_info.setOnClickListener(this);
        create_quotes_commit.setOnClickListener(this);
        chooose_product_pic.setOnClickListener(this);
        product_time_from.setOnClickListener(this);
        product_time_to.setOnClickListener(this);
        stringArrayAdapter = new TestArrayAdapter(this, quotesTypeItems);
        product_type.setAdapter(stringArrayAdapter);

    }

    //点击事件
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.quote_create_info:
                if (newOne) {
                    CreateDialog(Constant.DIALOG_CONTENT_11);
                } else {
                    CreateDialog(Constant.DIALOG_CONTENT_10);
                }
                break;
            case R.id.create_quotes_commit:
                submit();
                break;
            case R.id.product_name:
                intent = new Intent(this, ActivityManageGoods.class);
                intent.putExtra("fra", 1);
                startActivityForResult(intent, 11);
                break;
            case R.id.chooose_product_pic:
                intent = new Intent(this, SelectPicture_new.class);
                intent.putExtra("fra", 0);
                intent.putExtra("PicStyle", 1);
                startActivity(intent);
                break;
            case R.id.product_time_from:
                showCalendar(product_time_from);
                break;
            case R.id.product_time_to:
                showCalendar(product_time_to);
                break;
        }
    }

    //编辑完毕，提交数据
    private void submit() {
        // validate
        if (!StringUtils.isStrTrue(getEdVaule(product_name))) {
            toastSHORT("请先选择商品类型");
            return;
        }
        if (!StringUtils.isStrTrue(getEdVaule(product_number))) {
            toastSHORT("数量不能为空");
            return;
        }
        if (!StringUtils.isStrTrue(getEdVaule(product_price_from))) {
            toastSHORT("单价最小值不能为空");
            return;
        }
        if (!StringUtils.isStrTrue(getEdVaule(product_price_to))) {
            toastSHORT("单价最大值不能为空");
            return;
        }
        if (Double.valueOf(getEdVaule(product_price_from)) > Double.valueOf(getEdVaule(product_price_to))) {
            toastSHORT("请重新核对价格");
            return;
        }
        if (!StringUtils.isStrTrue(getEdVaule(product_time_from))) {
            toastSHORT("有效开始时间不能为空");
            return;
        }
        if (!StringUtils.isStrTrue(getEdVaule(product_time_to))) {
            toastSHORT("有效结束时间不能为空");
            return;
        }
        if (!Constant.judgeDate(getEdVaule(product_time_from), getEdVaule(product_time_to))) {
            toast("开始时间不能大于结束时间");
            return;
        }
        String more = product_more.getText().toString().trim();
        waitDialogRectangle.show();
        waitDialogRectangle.setText("正在提交");
        if (newOne) {
            CreateBean createBean = new CreateBean();
            createBean.goodsName = getEdVaule(product_name);
            createBean.num = getEdVaule(product_number);
            createBean.minPrice = getEdVaule(product_price_from);
            createBean.maxPrice = getEdVaule(product_price_to);
            createBean.startTime = getEdVaule(product_time_from);
            createBean.startEnd = getEdVaule(product_time_to);
            createBean.explan = more;
            createBean.type = String.valueOf(product_type.getSelectedItemPosition());
            createBean.sys_token = publicArg.getSys_token();
            createBean.sys_func = Constant.SYS_FUNC;
            createBean.sys_uuid = Constant.getUUID();
            createBean.sys_user = publicArg.getSys_user();
            createBean.sys_name = publicArg.getSys_username();
            createBean.sys_member = publicArg.getSys_member();
            createBean.goodsId = goodsId;
            createBean.titlePic = imageId;
            createBean.imgPath = imagePath;
//        createBean.goodsName1 = name;
            Gson gson = new Gson();
            String s1 = gson.toJson(createBean);
            Log.d("s1", s1);
//            url = HTTPURL + QUOTE_URL_C;
            OkGo.post(Constant.CreateQuoteUrl).params("param", s1).execute(new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, okhttp3.Response response) {
                    initGson2(s);
                }
            });
        } else {
            CreateBean createBean = new CreateBean();
//            createBean.goodsName2 = name;
            createBean.num = getEdVaule(product_number);
            createBean.minPrice = getEdVaule(product_price_from);
            createBean.maxPrice = getEdVaule(product_price_to);
            createBean.quoteId = id;
            createBean.startTime = getEdVaule(product_time_from);
            createBean.startEnd = getEdVaule(product_time_to);
            createBean.explan = more;
            createBean.type = String.valueOf(product_type.getSelectedItemPosition());
            createBean.sys_token = publicArg.getSys_token();
            createBean.sys_func = Constant.SYS_FUNC;
            createBean.sys_uuid = Constant.getUUID();
            createBean.sys_user = publicArg.getSys_user();
            createBean.sys_name = publicArg.getSys_username();
            createBean.sys_member = publicArg.getSys_member();
            createBean.goodsId = goodsId;
            createBean.titlePic = imageId;
            createBean.imgPath = imagePath;
            Gson gson = new Gson();
            String s1 = gson.toJson(createBean);
            Log.d("s1", s1);
//            url = HTTPURL + QUOTE_URL_E;
            OkGo.post(Constant.SubEditQuoteUrl)
                    .params("param", s1)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, okhttp3.Response response) {
                            initGson2(s);
                        }
                    });
        }

    }

    //提交后的解析，完成后页面销毁
    private void initGson2(String s) {
        Log.d("response", s);
        Gson gson = new Gson();
        ReturnBean returnBean = gson.fromJson(s, ReturnBean.class);
        if (waitDialogRectangle != null && waitDialogRectangle.isShowing()) {
            waitDialogRectangle.dismiss();
        }
        if (returnBean.getReturn_code() == 0) {
            toast(returnBean.getReturn_message());
            setResult(33);
            finish();
        } else if (returnBean.getReturn_code() == 1101 || returnBean.getReturn_code() == 1102) {
            toastSHORT("重复登录或令牌超时");
            LogoutUtils.exitUser(CreateQuotes.this);
        } else {
            toast(returnBean.getReturn_message());
        }
    }


    //选择图片后加载图片
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ImageData imageData) {
        imageId = imageData.getEntity().getImgId();
        imagePath = imageData.getEntity().getImagePath();
        Glide.with(this).load(Constant.SHORTHTTPURL + imageData.getEntity().getImagePath()).into(chooose_product_pic);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
