package com.hj.casps.quotes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hj.casps.R;
import com.hj.casps.adapter.TestArrayAdapter;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.commodity.ActivityManageGoods;
import com.hj.casps.commodity.ImageData;
import com.hj.casps.commodity.SelectPicture_new;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.appQuote.EditQuoteEntity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Response;

import static com.hj.casps.common.Constant.HTTPURLIMAGE;
import static com.hj.casps.common.Constant.SHORTHTTPURL;
import static com.hj.casps.common.Constant.getUUID;
import static com.hj.casps.common.Constant.publicArg;
import static com.hj.casps.common.Constant.stmpToDate;

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
    //    private static final String QUOTE_URL = "appQuote/editQuote.app";
//    private static final String QUOTE_URL_C = "appQuote/createQuote.app";
//    private static final String QUOTE_URL_E = "appQuote/subEditQuote.app";
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
//        super.onActivityResult(requestCode, resultCode, data);
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
                    Constant.SYS_FUNC10110028,
                    publicArg.getSys_user(),
                    publicArg.getSys_member(),
                    id
            );
            OkGo.post(Constant.EditQuoteUrl)
                    .params("param",mGson.toJson(editQuoteEntity)).
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
        CreateModel.MapBean quotesBack = gson.fromJson(response, CreateModel.MapBean.class);
        newOne = false;

        if (quotesBack != null) {
            product_name.setText(quotesBack.getGoodsName());
            product_number.setText(String.valueOf(quotesBack.getNum()));
            product_price_from.setText(String.valueOf(quotesBack.getMinPrice()));
            product_price_to.setText(String.valueOf(quotesBack.getMaxPrice()));
            product_time_from.setText(stmpToDate(quotesBack.getStartTime()));
            if (quotesBack.getImgPath() != null) {
                Glide.with(this).load(quotesBack.getImgPath().startsWith("/v2content") ? SHORTHTTPURL + quotesBack.getImgPath() : HTTPURLIMAGE + quotesBack.getImgPath()).into(chooose_product_pic);
            }
            goodsId = quotesBack.getGoodsId();
            if (!product_time_from.getText().toString().isEmpty()) {
                product_time_from.setCompoundDrawables(null, null, null, null);
            }
            product_time_to.setText(stmpToDate(quotesBack.getStartEnd()));
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

        } else {

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
        String name = product_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请先选择商品类型", Toast.LENGTH_SHORT).show();
            return;
        }

        String number = product_number.getText().toString().trim();
        if (TextUtils.isEmpty(number)) {
            Toast.makeText(this, "数量不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String from = product_price_from.getText().toString().trim();
        if (TextUtils.isEmpty(from)) {
            Toast.makeText(this, "单价最小值不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String to = product_price_to.getText().toString().trim();
        if (TextUtils.isEmpty(to)) {
            Toast.makeText(this, "单价最大值不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String product_from = product_time_from.getText().toString().trim();
        if (TextUtils.isEmpty(product_from)) {
            Toast.makeText(this, "有效时间最小值不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String product_to = product_time_to.getText().toString().trim();
        if (TextUtils.isEmpty(product_to)) {
            Toast.makeText(this, "有效时间最大值不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String more = product_more.getText().toString().trim();
        waitDialogRectangle.show();
        waitDialogRectangle.setText("正在提交");
        if (newOne) {


            CreateBean createBean = new CreateBean();
            createBean.goodsName = name;
            createBean.num = number;
            createBean.minPrice = from;
            createBean.maxPrice = to;
            createBean.startTime = product_from;
            createBean.startEnd = product_to;
            createBean.explan = more;
            createBean.type = String.valueOf(product_type.getSelectedItemPosition());
            createBean.sys_token = publicArg.getSys_token();
            createBean.sys_func = Constant.SYS_FUNC10110028;
            createBean.sys_uuid = getUUID();
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
            createBean.num = number;
            createBean.minPrice = from;
            createBean.maxPrice = to;
            createBean.quoteId = id;
            createBean.startTime = product_from;
            createBean.startEnd = product_to;
            createBean.explan = more;
            createBean.type = String.valueOf(product_type.getSelectedItemPosition());
            createBean.sys_token = publicArg.getSys_token();
            createBean.sys_func = Constant.SYS_FUNC10110028;
            createBean.sys_uuid = getUUID();
            createBean.sys_user = publicArg.getSys_user();
            createBean.sys_name = publicArg.getSys_username();
            createBean.sys_member = publicArg.getSys_member();
            createBean.goodsId = goodsId;
            createBean.titlePic = imageId;
            createBean.imgPath = imagePath;
//        createBean.goodsName1 = name;
//        createBean.goodsName1 = name;
//        createBean.goodsName1 = name;
//        createBean.goodsName1 = name;
//        createBean.goodsName1 = name;
            Gson gson = new Gson();
            String s1 = gson.toJson(createBean);
            Log.d("s1", s1);
//            url = HTTPURL + QUOTE_URL_E;
            OkGo.post(Constant.SubEditQuoteUrl).params("param", s1).execute(new StringCallback() {
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
        } else {
            toast(returnBean.getReturn_message());
        }
    }

    /**
     * 提交的基类
     */
    private static class CreateBean {
        private String sys_token;
        private String sys_uuid;
        private String sys_func;
        private String sys_user;
        private String sys_name;
        private String sys_member;
        private String startTime;
        private String startEnd;
        private String goodsId;
        private String explan;
        private String titlePic;
        private String imgPath;
        private String unitPrice;
        private String minPrice;
        private String maxPrice;
        private String num;
        private String type;
        private String categoryId;
        private String account;
        private String address;
        private String rangType;
        private String goodsName;
        private String quoteId;

    }


    /**
     * 返回值的基类
     */
    private static class CreateModel {

        /**
         * map : {"areaId":"1","categoryId":"1002004001","createName":"刘长城","createTime":1488297600000,"explan":"优惠价","goodsId":"b6aa48901e134341a63c24c00c7264e1","goodsName":"土鸡","id":"30038403","imgPath":"","maxPrice":22,"minPrice":20,"mmbId":"testshop001","mmbName":"长城商行","num":1000,"publishId":"a29d2326763546a4b0063c202cff08ff","publishName":"刘长城","rangType":1,"startEnd":1493568000000,"startTime":1488297600000,"status":0,"titlePic":"76c179ef3f2a4130824798041e8e7345","type":1,"unitPrice":"￥","userId":"a29d2326763546a4b0063c202cff08ff"}
         * return_code : 0
         * return_message : success
         */

        private MapBean map;
        private int return_code;
        private String return_message;

        public MapBean getMap() {
            return map;
        }

        public void setMap(MapBean map) {
            this.map = map;
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

        public static class MapBean {
            /**
             * areaId : 1
             * categoryId : 1002004001
             * createName : 刘长城
             * createTime : 1488297600000
             * explan : 优惠价
             * goodsId : b6aa48901e134341a63c24c00c7264e1
             * goodsName : 土鸡
             * id : 30038403
             * imgPath :
             * maxPrice : 22
             * minPrice : 20
             * mmbId : testshop001
             * mmbName : 长城商行
             * num : 1000
             * publishId : a29d2326763546a4b0063c202cff08ff
             * publishName : 刘长城
             * rangType : 1
             * startEnd : 1493568000000
             * startTime : 1488297600000
             * status : 0
             * titlePic : 76c179ef3f2a4130824798041e8e7345
             * type : 1
             * unitPrice : ￥
             * userId : a29d2326763546a4b0063c202cff08ff
             */

            private String areaId;
            private String categoryId;
            private String createName;
            private long createTime;
            private String explan;
            private String goodsId;
            private String goodsName;
            private String id;
            private String imgPath;
            private double maxPrice;
            private double minPrice;
            private String mmbId;
            private String mmbName;
            private int num;
            private String publishId;
            private String publishName;
            private int rangType;
            private long startEnd;
            private long startTime;
            private int status;
            private String titlePic;
            private int type;
            private String unitPrice;
            private String userId;

            public String getAreaId() {
                return areaId;
            }

            public void setAreaId(String areaId) {
                this.areaId = areaId;
            }

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
            }

            public String getCreateName() {
                return createName;
            }

            public void setCreateName(String createName) {
                this.createName = createName;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getExplan() {
                return explan;
            }

            public void setExplan(String explan) {
                this.explan = explan;
            }

            public String getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(String goodsId) {
                this.goodsId = goodsId;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImgPath() {
                return imgPath;
            }

            public void setImgPath(String imgPath) {
                this.imgPath = imgPath;
            }

            public double getMaxPrice() {
                return maxPrice;
            }

            public void setMaxPrice(double maxPrice) {
                this.maxPrice = maxPrice;
            }

            public double getMinPrice() {
                return minPrice;
            }

            public void setMinPrice(double minPrice) {
                this.minPrice = minPrice;
            }

            public String getMmbId() {
                return mmbId;
            }

            public void setMmbId(String mmbId) {
                this.mmbId = mmbId;
            }

            public String getMmbName() {
                return mmbName;
            }

            public void setMmbName(String mmbName) {
                this.mmbName = mmbName;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getPublishId() {
                return publishId;
            }

            public void setPublishId(String publishId) {
                this.publishId = publishId;
            }

            public String getPublishName() {
                return publishName;
            }

            public void setPublishName(String publishName) {
                this.publishName = publishName;
            }

            public int getRangType() {
                return rangType;
            }

            public void setRangType(int rangType) {
                this.rangType = rangType;
            }

            public long getStartEnd() {
                return startEnd;
            }

            public void setStartEnd(long startEnd) {
                this.startEnd = startEnd;
            }

            public long getStartTime() {
                return startTime;
            }

            public void setStartTime(long startTime) {
                this.startTime = startTime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getTitlePic() {
                return titlePic;
            }

            public void setTitlePic(String titlePic) {
                this.titlePic = titlePic;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getUnitPrice() {
                return unitPrice;
            }

            public void setUnitPrice(String unitPrice) {
                this.unitPrice = unitPrice;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }
        }
    }

    /**
     * 创建和编辑报价返回值
     */
    private static class ReturnBean {
        private int return_code;
        private String return_message;

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
    }

    //选择图片后加载图片
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ImageData imageData) {
        imageId = imageData.getEntity().getImgId();
        imagePath = imageData.getEntity().getImagePath();
        Glide.with(this).load(SHORTHTTPURL + imageData.getEntity().getImagePath()).into(chooose_product_pic);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
