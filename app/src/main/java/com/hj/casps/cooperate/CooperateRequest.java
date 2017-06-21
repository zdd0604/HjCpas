package com.hj.casps.cooperate;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.hj.casps.R;
import com.hj.casps.adapter.WZYBaseAdapter;
import com.hj.casps.base.ActivityBaseHeader;
import com.hj.casps.common.Constant;
import com.hj.casps.ui.MyDialog;
import com.hj.casps.ui.MyToast;
import com.hj.casps.util.LogoutUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;
import okhttp3.Call;
import okhttp3.Response;

import static com.hj.casps.common.Constant.SYS_FUNC101100310003;

//关系管理—待审批申请
public class CooperateRequest extends ActivityBaseHeader implements View.OnClickListener {

    private TextView cooperate_request_info;
    private ListView request_list;
    private List<CoopBackResult.ListBean> cooperateRequstModels;
    private CooperateRequestAdapter adapter;
//    private String concerns_url = HTTPURL + "appMemberRelationship/queryMmbRelationships.app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperate_request);
        initData();
        initView();
    }

    //加载待审批申请的目录
    private void initData() {
        cooperateRequstModels = new ArrayList<>();
        QueryMMBConcerns queryMMBConcerns = new QueryMMBConcerns(publicArg.getSys_token(), "", SYS_FUNC101100310003, publicArg.getSys_user(), publicArg.getSys_member(), "1", "10");
        OkGo.post(Constant.QueryMmbRelationshipsUrl)
                .tag(this)
                .params("param", mGson.toJson(queryMMBConcerns))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        CoopBackResult backDetail = mGson.fromJson(s, CoopBackResult.class);
                        if (backDetail.getReturn_code() != 0) {
                            toast(backDetail.getReturn_message());
                        }else if(backDetail.getReturn_code()==1101||backDetail.getReturn_code()==1102){
                            toastSHORT("重复登录或令牌超时");
                            LogoutUtils.exitUser(CooperateRequest.this);
                        }


                        else {
                            cooperateRequstModels = backDetail.getList();
                            if (cooperateRequstModels.isEmpty()) {
                                toast("没有待审批申请");
                                adapter.removeAll();
                            }
                            for (int i = 0; i < cooperateRequstModels.size(); i++) {
                                cooperateRequstModels.get(i).setNum(i + 1);
                            }
                            adapter.updateRes(cooperateRequstModels);

                        }
                    }
                });


    }

    //加载带审批申请的界面
    private void initView() {
        setTitle(getString(R.string.cooperate_request));
        setTitleRight(null, null);
        cooperate_request_info = (TextView) findViewById(R.id.cooperate_request_info);
        request_list = (ListView) findViewById(R.id.request_list);

        cooperate_request_info.setOnClickListener(this);
        adapter = new CooperateRequestAdapter(cooperateRequstModels, this, R.layout.cooperate_request_item);
        request_list.setAdapter(adapter);
    }

    //操作说明
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cooperate_request_info:
                CreateDialog(Constant.DIALOG_CONTENT_16);

                break;
        }
    }

    /**
     * 关注会员目录提交类
     */
    public static class QueryMMBConcerns {
        private String sys_token;
        private String sys_uuid;
        private String sys_func;
        private String sys_user;
        private String sys_member;
        private String pageno;
        private String pagesize;

        public QueryMMBConcerns(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String pageno, String pagesize) {
            this.sys_token = sys_token;
            this.sys_uuid = sys_uuid;
            this.sys_func = sys_func;
            this.sys_user = sys_user;
            this.sys_member = sys_member;
            this.pageno = pageno;
            this.pagesize = pagesize;
        }
    }

    public static class CoopBackResult {

        /**
         * list : [{"biz_type":"向我采购","fname":"长城商行","relationship_id":"8cbe5688b8ee47229744e88ce385a0db"},{"biz_type":"向我销售","fname":"长城商行","relationship_id":"d3d15091274c475d935b1847cb95d9e5"}]
         * pagecount : 5
         * return_code : 0
         * return_message : 成功!
         */

        private int pagecount;
        private int return_code;
        private String return_message;
        private List<ListBean> list;

        public int getPagecount() {
            return pagecount;
        }

        public void setPagecount(int pagecount) {
            this.pagecount = pagecount;
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

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * biz_type : 向我采购
             * fname : 长城商行
             * relationship_id : 8cbe5688b8ee47229744e88ce385a0db
             */

            private String biz_type;
            private String fname;
            private String relationship_id;
            private int num;

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getBiz_type() {
                return biz_type;
            }

            public void setBiz_type(String biz_type) {
                this.biz_type = biz_type;
            }

            public String getFname() {
                return fname;
            }

            public void setFname(String fname) {
                this.fname = fname;
            }

            public String getRelationship_id() {
                return relationship_id;
            }

            public void setRelationship_id(String relationship_id) {
                this.relationship_id = relationship_id;
            }
        }
    }

    /**
     * 主适配器
     */
    private class CooperateRequestAdapter extends WZYBaseAdapter<CooperateRequest.CoopBackResult.ListBean> {
        private Context context;
        private MyDialog myDialog;

        public CooperateRequestAdapter(List<CooperateRequest.CoopBackResult.ListBean> data, Context context, int layoutRes) {
            super(data, context, layoutRes);
            this.context = context;
        }

        @Override
        public void bindData(WZYBaseAdapter.ViewHolder holder, final CooperateRequest.CoopBackResult.ListBean cooperateRequstModel, final int indexPos) {
            TextView name = (TextView) holder.getView(R.id.cooperate_name_request);
            name.setText(cooperateRequstModel.getFname());
            TextView relation = (TextView) holder.getView(R.id.cooperate_relation_request);
            relation.setText(cooperateRequstModel.getBiz_type());
            TextView request_num = (TextView) holder.getView(R.id.cooperate_request_num);
            request_num.setText(String.valueOf(cooperateRequstModel.getNum()));
            FancyButton agree = (FancyButton) holder.getView(R.id.cooperate_request_agree);
            agree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialog(true, cooperateRequstModel.getRelationship_id());
                }
            });
            FancyButton deny = (FancyButton) holder.getView(R.id.cooperate_request_deny);
            deny.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialog(false, cooperateRequstModel.getRelationship_id());
                }
            });

        }

        private void showDialog(final boolean agree, final String relationship_id) {
            myDialog = new MyDialog(context);
            if (agree) {
                myDialog.setMessage(context.getString(R.string.cooperate_dialog_agree));

            } else {
                myDialog.setMessage(context.getString(R.string.cooperate_dialog_deny));

            }
            myDialog.setYesOnclickListener(context.getString(R.string.True), new MyDialog.onYesOnclickListener() {
                @Override
                public void onYesClick() {
                    myDialog.dismiss();
                    postVerify(agree, relationship_id);
                }
            });
            myDialog.setNoOnclickListener(context.getString(R.string.cancel), new MyDialog.onNoOnclickListener() {
                @Override
                public void onNoClick() {
                    myDialog.dismiss();
                }
            });
            myDialog.show();
        }

        //提交审核结果
        private void postVerify(boolean agree, String relationship_id) {
//            String url = HTTPURL + "appMemberRelationship/verifyMmbRelationship.app";
            QueryMMBConcerns queryMMBConcerns = new QueryMMBConcerns(
                    publicArg.getSys_token(),
                    Constant.getUUID(),
                    SYS_FUNC101100310003,
                    publicArg.getSys_user(),
                    publicArg.getSys_member(),
                    relationship_id, agree ? "0" : "1");
            String s = mGson.toJson(queryMMBConcerns);
            s = s.replace("pageno", "relationship_id");
            s = s.replace("pagesize", "type");
            OkGo.post(Constant.VerifyMmbRelationshipUrl)
                    .tag(this)
                    .params("param", s)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            CoopBackResult backDetail = mGson.fromJson(s, CoopBackResult.class);
                            if (backDetail.getReturn_code() != 0) {
                                toast(backDetail.getReturn_message());
                            }else if(backDetail.getReturn_code()==1101||backDetail.getReturn_code()==1102){
                                toastSHORT("重复登录或令牌超时");
                                LogoutUtils.exitUser(CooperateRequest.this);
                            }


                            else {
                                new MyToast(context, context.getString(R.string.cooperate_dialog_ok));
                                initData();
                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            toast(e.getMessage());
                        }
                    });
        }
    }
}


