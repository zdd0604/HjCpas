package com.hj.casps.cooperate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.hj.casps.R;
import com.hj.casps.base.ActivityBaseHeader;
import com.hj.casps.common.Constant;
import com.hj.casps.util.LogoutUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.hj.casps.common.Constant.getUUID;
//会员合作目录。显示已经成为会员的列表
public class CooperateDirectory extends ActivityBaseHeader implements RadioGroup.OnCheckedChangeListener {
    private RadioGroup cooperate_choose;
    private ListView quotes_list;
    private List<CooperateModel> cooperateModelsBuy;
    private List<CooperateModel> cooperateModelsSell;
    private CooperateAdapter adapter1;
    private CooperateAdapter adapter2;
    private boolean buyMode;
//    private String url = HTTPURL + "appMemberRelationship/getRelaMmbs.app";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    initData();
                    break;

            }
        }
    };

    /**
     * 保存数据库
     */
    private void saveDaoData(int type) {
//        CooperateDirUtils.getInstance(this).deleteAll();
        if (type == 0) {
            for (int i = 0; i < cooperateModelsBuy.size(); i++) {
                cooperateModelsBuy.get(i).setType(0);
                CooperateDirUtils.getInstance(this).insertInfo(cooperateModelsBuy.get(i));
            }
        } else {
            for (int i = 0; i < cooperateModelsSell.size(); i++) {
                cooperateModelsSell.get(i).setType(1);
                CooperateDirUtils.getInstance(this).insertInfo(cooperateModelsSell.get(i));
            }
        }

    }


    /**
     * 加载本地数据
     */
    private void addLocality() {
        List<CooperateModel> usrList = CooperateDirUtils.getInstance(this).queryInfo();
        if (usrList.size() > 0) {
            cooperateModelsBuy = new ArrayList<>();
            cooperateModelsSell = new ArrayList<>();
            for (int i = 0; i < usrList.size(); i++) {
                if (usrList.get(i).getType() == 0) {
                    cooperateModelsBuy.add(usrList.get(i));
                } else {
                    cooperateModelsSell.add(usrList.get(i));
                }
            }

        }
        adapter1.updateRes(cooperateModelsBuy);
        adapter2.updateRes(cooperateModelsSell);
    }

    //会员搜索后的结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == 22) {
            handler.sendEmptyMessage(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperate_directory);
        initView();
        if (hasInternetConnected()) {
            initData();
        } else {
            addLocality();
        }

    }

    //会员目录的加载
    private void initData() {
        cooperateModelsBuy = new ArrayList<>();
        cooperateModelsSell = new ArrayList<>();
        CooperateLoading cooperateLoading = new CooperateLoading(
                publicArg.getSys_token(),
                getUUID(),
                Constant.SYS_FUNC,
                publicArg.getSys_user(),
                publicArg.getSys_name(),
                publicArg.getSys_member());
        log(mGson.toJson(cooperateLoading));
        OkGo.post(Constant.GetRelaMmbsUrl)
                .tag(this)
                .params("param", mGson.toJson(cooperateLoading))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        deleteData();
                        CooperateBack cooperateBack = mGson.fromJson(s, CooperateBack.class);
                         if(cooperateBack.getReturn_code()==1101||cooperateBack.getReturn_code()==1102){
                            toastSHORT("重复登录或令牌超时");
                            LogoutUtils.exitUser(CooperateDirectory.this);
                             return;
                        }
                        switch (cooperateBack.getList().size()) {
                            case 0:
                                toast("尚未创建合作会员");
                                break;
                            case 1:
                                if (cooperateBack.getList().get(0).get买() == null) {
                                    cooperateModelsSell = cooperateBack.getList().get(0).get卖();
                                } else {
                                    cooperateModelsBuy = cooperateBack.getList().get(0).get买();
                                }

                                break;
                            case 2:
                                cooperateModelsBuy = cooperateBack.getList().get(0).get买();
                                cooperateModelsSell = cooperateBack.getList().get(1).get卖();
                                break;
                        }
                        if (cooperateModelsBuy != null && cooperateModelsBuy.size() > 0) {
                            adapter1.updateRes(cooperateModelsBuy);
                            saveDaoData(0);

                        }else {
                            adapter1.removeAll();
                        }
                        if (cooperateModelsSell != null && cooperateModelsSell.size() > 0) {
                            adapter2.updateRes(cooperateModelsSell);
                            saveDaoData(1);

                        }else {
                            adapter2.removeAll();
                        }

                    }


                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                    }
                });


    }

    //删除会员
    private void deleteData() {
        CooperateDirUtils.getInstance(this).deleteAll();

    }

    //会员目录的布局
    private void initView() {
        setTitle(getString(R.string.cooperate_direct));
        setTitleRight(null, null);
        cooperate_choose = (RadioGroup) findViewById(R.id.cooperate_choose);
        quotes_list = (ListView) findViewById(R.id.quotes_list);
        cooperate_choose.setOnCheckedChangeListener(this);
        adapter1 = new CooperateAdapter(cooperateModelsBuy, this, R.layout.cooperate_item);
        adapter2 = new CooperateAdapter(cooperateModelsSell, this, R.layout.cooperate_item);
        quotes_list.setAdapter(adapter1);
        buyMode = true;
        quotes_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), CooperateDetail.class);
                if (buyMode) {
                    intent.putExtra("relation_member", cooperateModelsBuy.get(i).getMemberId());
                    intent.putExtra("buztype", "0");
                } else {
                    intent.putExtra("relation_member", cooperateModelsSell.get(i).getMemberId());
                    intent.putExtra("buztype", "1");
                }
                startActivityForResult(intent, 11);
            }
        });

    }

    //买卖关系的切换
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (i) {
            case R.id.main_radio_first:
                buyMode = true;
                quotes_list.setAdapter(adapter1);
                break;
            case R.id.main_radio_second:
                buyMode = false;
                quotes_list.setAdapter(adapter2);
                break;
        }
    }
}
