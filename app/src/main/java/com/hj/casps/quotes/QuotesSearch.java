package com.hj.casps.quotes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.hj.casps.R;
import com.hj.casps.adapter.TestArrayAdapter;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;

import java.util.Calendar;
import java.util.List;

//报价管理的搜索
public class QuotesSearch extends ActivityBaseHeader2 implements View.OnClickListener {

    private EditText product_name;
    private Spinner product_type;
    private Spinner quotes_search_status;
    private EditText publish_time_from;
    private EditText publish_time_to;
    private EditText period_time_from;
    private EditText period_time_to;
    private Button search_quotes_btn;
    private Calendar c = Calendar.getInstance();
    private TestArrayAdapter stringArrayAdapter;
    private String[] typeItems;
    private String[] stateItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes_search);
        initData();
        initView();
    }

    //加载数据
    private void initData() {
        typeItems = new String[]{"采购", "销售"};
        stateItems = new String[]{"全部", "使用中", "已过期", "已停用"};
    }

    //获取页面
    private void initView() {
        setTitle(getString(R.string.quotes_search));
        titleRight.setVisibility(View.GONE);
        product_name = (EditText) findViewById(R.id.product_name);
        product_type = (Spinner) findViewById(R.id.product_type);
        quotes_search_status = (Spinner) findViewById(R.id.quotes_search_status);
        publish_time_from = (EditText) findViewById(R.id.publish_time_from);
        publish_time_from.setOnClickListener(this);
        publish_time_to = (EditText) findViewById(R.id.publish_time_to);
        publish_time_to.setOnClickListener(this);
        period_time_from = (EditText) findViewById(R.id.period_time_from);
        period_time_from.setOnClickListener(this);
        period_time_to = (EditText) findViewById(R.id.period_time_to);
        period_time_to.setOnClickListener(this);
        search_quotes_btn = (Button) findViewById(R.id.search_quotes_btn);
        stringArrayAdapter = new TestArrayAdapter(this, typeItems);
        product_type.setAdapter(stringArrayAdapter);
        stringArrayAdapter = new TestArrayAdapter(this, stateItems);
        quotes_search_status.setAdapter(stringArrayAdapter);
        search_quotes_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_quotes_btn:
                submit();
                break;
            case R.id.publish_time_from:
                showCalendar(publish_time_from);
                break;
            case R.id.publish_time_to:
                showCalendar(publish_time_to);
                break;
            case R.id.period_time_from:
                showCalendar(period_time_from);
                break;
            case R.id.period_time_to:
                showCalendar(period_time_to);
                break;
        }
    }

    //提交报价搜索
    private void submit() {
        String name = product_name.getText().toString().trim();
        String from = publish_time_from.getText().toString().trim();
        String to = publish_time_to.getText().toString().trim();
        String period_from = period_time_from.getText().toString().trim();
        String period_to = period_time_to.getText().toString().trim();

        if (!Constant.judgeDate(from, to) || !Constant.judgeDate(period_from, period_to)) {
            toast("开始时间不能大于结束时间");
            return;
        }
        // TODO validate success, do something
        ModeltoJson modeltoJson = new ModeltoJson(
                publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                publicArg.getSys_user(),
                publicArg.getSys_name(),
                publicArg.getSys_member(),
                name, period_from,
                period_to, from,
                to, "1", "10",
                String.valueOf((quotes_search_status.getSelectedItemPosition() - 1) == -1 ? null :
                        (quotes_search_status.getSelectedItemPosition() - 1)),
                String.valueOf(product_type.getSelectedItemPosition()));
        Bundle bundle = new Bundle();
        bundle.putInt("type", product_type.getSelectedItemPosition());
        bundle.putString("searchjson", mGson.toJson(modeltoJson));
        setResult(22, getIntent().putExtras(bundle));
        finish();

    }

    /**
     * 网络请求实体类，用来生成json串的请求参数
     */
    private class ModeltoJson {
        /**
         * sys_token	string	令牌号
         * sys_uuid	string	操作唯一编码（防重复提交）
         * sys_func	string	功能编码（用于授权检查）
         * sys_user	string	用户id
         * sys_name	string	用户名
         * sys_member	string	会员id
         * goodname	string	商品名称
         * startTime	string	有效期（开始）
         * startEnd	string	有效期（结束）
         * createTime1	string	发布时间（开始）
         * createTime2	string	发布时间（结束）
         * pageNo	string	页号
         * pageSize	string	页行数
         * status	string	状态0受用1禁用2过期
         * type	string	报价类型// 0采购// 1销售
         */
        private String sys_token;//令牌号
        private String sys_uuid;//操作唯一编码
        private String sys_func;//功能编码
        private String sys_user;//用户ID
        private String sys_name;//用户名
        private String sys_member;//会员id
        private String goodname;//商品名称
        private String startTime;//有效期（开始）
        private String startEnd;//有效期（结束）
        private String createTime1;//发布时间（开始）
        private String createTime2;//发布时间（结束）
        private String pageno;//页号
        private String pagesize;//页行数
        private String status;//状态0受用1禁用2过期
        private String type;//报价类型0采购1销售

        public ModeltoJson(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_name, String sys_member, String goodname, String startTime, String startEnd, String createTime1, String createTime2, String pageno, String pagesize, String status, String type) {
            this.sys_token = sys_token;
            this.sys_uuid = sys_uuid;
            this.sys_func = sys_func;
            this.sys_user = sys_user;
            this.sys_name = sys_name;
            this.sys_member = sys_member;
            this.goodname = goodname;
            this.startTime = startTime;
            this.startEnd = startEnd;
            this.createTime1 = createTime1;
            this.createTime2 = createTime2;
            this.pageno = pageno;
            this.pagesize = pagesize;
            this.status = status;
            this.type = type;
        }
    }

    /**
     * 网络返回实体类，用来生成json串的请求参数
     */
    private class ModelQuotesSearch {
        private int return_code;//结果号
        private String return_message;//结果提示文本
        private List<QuoteModel> qtList;//功能编码
        private String sys_user;//用户ID
        private String sys_name;//用户名
        private String sys_member;//会员id
        private String goodname;//商品名称
        private String actionId;//关注会员目录  买卖
        private String checkBoxId;//更多选项（选择的群组的字符串）
        private String pageNo;//页号
        private String pageSize;//页行数
        private String type;//报价类型0采购1销售

    }

}
