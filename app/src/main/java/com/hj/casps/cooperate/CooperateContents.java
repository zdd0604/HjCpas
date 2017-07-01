package com.hj.casps.cooperate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hj.casps.R;
import com.hj.casps.adapter.WZYBaseAdapter;
import com.hj.casps.base.ActivityBaseHeader;
import com.hj.casps.common.Constant;
import com.hj.casps.ui.MyDialog;
import com.hj.casps.util.LogoutUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;
import okhttp3.Call;
import okhttp3.Response;

import static com.hj.casps.common.Constant.SYS_FUNC;
import static com.hj.casps.common.Constant.getUUID;
//合作会员列表
public class CooperateContents extends ActivityBaseHeader implements View.OnClickListener {

    private TextView cooperate_contents_info;
    private ListView contents_list;
    private CooperateContentsAdapter adapter;
    private List<CooperateContentsBean> cooperateModels;
//    private String concerns_url = Constant.HTTPURL + "appMemberRelationship/queryMMBConcerns.app";
    private int grade = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    initData(grade);
                    break;

            }
        }
    };

    /**
     * 保存数据库
     */
    private void saveDaoData() {
        CooperateDirUtils.getInstance(this).deleteAll();
        for (int i = 0; i < cooperateModels.size(); i++) {
            CooperateDirUtils.getInstance(this).insertInfo(cooperateModels.get(i));
        }
    }


    /**
     * 加载本地数据
     */
    private void addLocality() {
        List<CooperateContentsBean> usrList = CooperateDirUtils.getInstance(this).queryCooperateContentsBeanInfo();

        if (usrList.size() > 0) {
            cooperateModels = usrList;
        }
        adapter.updateRes(cooperateModels);

    }

    //搜索会员等级后返回结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == 22) {
            grade = data.getExtras().getInt("grade");
            handler.sendEmptyMessage(0);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperate_contents);
        initView();
        if (hasInternetConnected()) {
            initData(grade);
        } else {
            addLocality();
        }

    }

    //加载合作会员
    private void initData(int grage) {
        cooperateModels = new ArrayList<>();
        QueryMMBConcerns concerns = new QueryMMBConcerns(publicArg.getSys_token(),
                getUUID(), SYS_FUNC,
                publicArg.getSys_user(),
                publicArg.getSys_member(),
                grade == 0 ? "" : String.valueOf(grage),
                "1", "10");
        OkGo.post(Constant.QueryMMBConcernsUrl)
                .tag(this)
                .params("param", mGson.toJson(concerns))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        QueryMMBConcernsBack backDetail = mGson.fromJson(s, QueryMMBConcernsBack.class);
                        if (backDetail.getReturn_code() != 0) {
                            toast(backDetail.getReturn_message());
                        }else if(backDetail.getReturn_code()==1101||backDetail.getReturn_code()==1102){
                            toastSHORT("重复登录或令牌超时");
                            LogoutUtils.exitUser(CooperateContents.this);
                        }

                        else {
                            cooperateModels = backDetail.getList();
                            if (cooperateModels.isEmpty()) {
                                toast("没有合作会员");
                                adapter.removeAll();
                            }
                            adapter.updateRes(cooperateModels);
                            saveDaoData();

                        }
                    }
                });
    }

    //加载布局
    private void initView() {
        setTitle(getString(R.string.cooperate_contents));
        cooperate_contents_info = (TextView) findViewById(R.id.cooperate_contents_info);
        cooperate_contents_info.setOnClickListener(this);
        contents_list = (ListView) findViewById(R.id.contents_list);
        adapter = new CooperateContentsAdapter(cooperateModels, this, R.layout.item_cooperate_contents);
        contents_list.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cooperate_contents_info:
                CreateDialog(Constant.DIALOG_CONTENT_14);

                break;
        }
    }

    //点击搜索
    @Override
    protected void onNavSearchClick() {
        super.onNavSearchClick();
        intentActivity(CooperateSearch.class, 11);
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
        private String grade;
        private String relationship_id;
        private String pageno;
        private String pagesize;

        public QueryMMBConcerns(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String relationship_id) {
            this.sys_token = sys_token;
            this.sys_uuid = sys_uuid;
            this.sys_func = sys_func;
            this.sys_user = sys_user;
            this.sys_member = sys_member;
            this.relationship_id = relationship_id;
        }

        public QueryMMBConcerns(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String grade, String relationship_id) {
            this.sys_token = sys_token;
            this.sys_uuid = sys_uuid;
            this.sys_func = sys_func;
            this.sys_user = sys_user;
            this.sys_member = sys_member;
            this.grade = grade;
            this.relationship_id = relationship_id;
        }

        public QueryMMBConcerns(String sys_token, String sys_uuid, String sys_func, String sys_user, String sys_member, String grade, String pageno, String pagesize) {
            this.sys_token = sys_token;
            this.sys_uuid = sys_uuid;
            this.sys_func = sys_func;
            this.sys_user = sys_user;
            this.sys_member = sys_member;
            this.grade = grade;
            this.pageno = pageno;
            this.pagesize = pagesize;
        }
    }

    /**
     * 关注会员目录返回类
     */
    public static class QueryMMBConcernsBack {

        /**
         * list : [{"mark_member":"c1559f7f457c44d0a6a72b9e0137fe55","mmbaddress":"北京市海淀区中关村路155号","mmbfName":"APP测试学校","mmbhomepage":"","relagrade":2,"relationship_id":"fb40e46a6c0c4f559877b4d49570afaf"},{"mark_member":"46e7ffa51761499e905a10f1a33ec450","mmbaddress":"北京市朝阳区亚运村路18号","mmbfName":"APP测试用企业","mmbhomepage":"","relagrade":1,"relationship_id":"dee77a3624e2473c90e8a2e3d04ff701"},{"mark_member":"da4383de72494f5d98dc7836d25f526f","mmbaddress":"","mmbfName":"cyh","mmbhomepage":"http://members.nxdj.org.cn/cyh.html","relagrade":1,"relationship_id":"c46f2d0f3cbc40418552e8763c629660"},{"mark_member":"d5d1244c85d5478ca88a253b509f8bed","mmbaddress":"北京海淀","mmbfName":"北京交通大学","mmbhomepage":"","relagrade":1,"relationship_id":"085fc922c80848508570dd5aacc72fc1"},{"mark_member":"5c23a03df5dd4d4f95b3dff6ad057e9f","mmbaddress":"北京","mmbfName":"北京美日鲜农场","mmbhomepage":"http://members.nxdj.org.cn/美日鲜农场.html","relagrade":1,"relationship_id":"42b18f0954d3475383476196021d6720"},{"mark_member":"a9c1512052164391ab1fcc2ffa67b0ec","mmbaddress":"北京市朝阳区安立路25号","mmbfName":"北京试用的学校","mmbhomepage":"","relagrade":1,"relationship_id":"4c038dba7b144e07972ca46435c693c1"},{"mark_member":"459f3498633d4952a293dc360e2b7c97","mmbaddress":"北京","mmbfName":"天坛学院","mmbhomepage":"http://192.168.1.120:8081/v2content/mmbhtml/天坛学院.html","relagrade":1,"relationship_id":"10b3f4d9411f49fcafa974f429fb982f"},{"mark_member":"55da4721cd7049e186a17745a795a6cd","mmbaddress":"北京小树林","mmbfName":"天美贸易公司","mmbhomepage":"http://members.nxdj.org.cn/天美商行.html","relagrade":1,"relationship_id":"df050732fe9e48c7ac6d67b85a46627e"}]
         * pagecount : 8
         * return_code : 0
         * return_message : 成功!
         */

        private int pagecount;
        private int return_code;
        private String return_message;
        private List<CooperateContentsBean> list;

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

        public List<CooperateContentsBean> getList() {
            return list;
        }

        public void setList(List<CooperateContentsBean> list) {
            this.list = list;
        }

    }


    //关注会员目录的适配器
    public class CooperateContentsAdapter extends WZYBaseAdapter<CooperateContentsBean> {
        private Context context;
//        private String url = Constant.HTTPURL + "appMemberRelationship/changeConcernGrade.app";
//        private String url_cancel = Constant.HTTPURL + "appMemberRelationship/cancelConcern.app";

        public CooperateContentsAdapter(List<CooperateContentsBean> data, Context context, int layoutRes) {
            super(data, context, layoutRes);
            this.context = context;

        }

        @Override
        public void bindData(WZYBaseAdapter.ViewHolder holder, final CooperateContentsBean listBean, final int indexPos) {

            final int[] grade_num = {listBean.getRelagrade()};

            TextView name = (TextView) holder.getView(R.id.cooperate_name_contents);
            name.setText(listBean.getMmbfName());
            TextView address = (TextView) holder.getView(R.id.cooperate_address_contents);
            address.setText(listBean.getMmbaddress());
            final TextView grade = (TextView) holder.getView(R.id.cooperate_grade_tv);
            grade.setText(String.valueOf(grade_num[0]));

            Button reduce = (Button) holder.getView(R.id.cooperate_grade_reduce);
            reduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (grade_num[0] > 1) {
                        grade_num[0]--;
                        grade.setText(String.valueOf(grade_num[0]));
                        listBean.setRelagrade(grade_num[0]);

                        postGrade(grade_num[0], listBean.getRelationship_id());
                    } else {
                        Toast.makeText(context, "关注等级不能小于一", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            Button plus = (Button) holder.getView(R.id.cooperate_grade_plus);
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (grade_num[0] < 5) {
                        grade_num[0]++;
                        grade.setText(String.valueOf(grade_num[0]));
                        listBean.setRelagrade(grade_num[0]);
                        postGrade(grade_num[0], listBean.getRelationship_id());

                    } else {
                        Toast.makeText(context, "关注等级不能大于五", Toast.LENGTH_SHORT).show();

                    }
                }


            });
            FancyButton cancel_cooperate = (FancyButton) holder.getView(R.id.cancel_cooperate);
            cancel_cooperate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final MyDialog myDialog = new MyDialog(context);
                    myDialog.setMessage(context.getString(R.string.cooperate_cancel_dialog));
                    myDialog.setYesOnclickListener(context.getString(R.string.True), new MyDialog.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            myDialog.dismiss();
                            postGrade(listBean.getRelationship_id());
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
            });
            FancyButton upgrade_cooperate = (FancyButton) holder.getView(R.id.upgrade_cooperate);
            upgrade_cooperate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, CooperateDetail.class);
                    intent.putExtra("type", 1);
                    intent.putExtra("name", listBean.getMmbfName());
                    intent.putExtra("address", listBean.getMmbaddress());
                    intent.putExtra("mark_member", listBean.getMark_member());
                    context.startActivity(intent);
                }
            });

        }

        private void postGrade(String relationship_id) {
            CooperateContents.QueryMMBConcerns concerns = new CooperateContents.QueryMMBConcerns(Constant.publicArg.getSys_token(), getUUID(), Constant.SYS_FUNC, Constant.publicArg.getSys_user(), Constant.publicArg.getSys_member(), relationship_id);
            OkGo.post(Constant.CancelConcernUrl).params("param", mGson.toJson(concerns)).execute(new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    CooperateContents.QueryMMBConcernsBack backDetail = mGson.fromJson(s, CooperateContents.QueryMMBConcernsBack.class);
                    if (backDetail.getReturn_code() != 0) {
                        Toast.makeText(context, backDetail.getReturn_message(), Toast.LENGTH_SHORT).show();
                    } else if(backDetail.getReturn_code()==1101||backDetail.getReturn_code()==1102){
                        toastSHORT("重复登录或令牌超时");
                        LogoutUtils.exitUser(CooperateContents.this);
                    }
                    else {
                        initData(grade);

                    }
                }

                @Override
                public void onError(Call call, Response response, Exception e) {
                    super.onError(call, response, e);
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }

        private void postGrade(int i, String relationship_id) {
            CooperateContents.QueryMMBConcerns concerns = new CooperateContents.QueryMMBConcerns(Constant.publicArg.getSys_token(), getUUID(), Constant.SYS_FUNC, Constant.publicArg.getSys_user(), Constant.publicArg.getSys_member(), String.valueOf(i), relationship_id);
            OkGo.post(Constant.ChangeConcernGradeUrl).params("param", mGson.toJson(concerns)).execute(new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    CooperateContents.QueryMMBConcernsBack backDetail = mGson.fromJson(s, CooperateContents.QueryMMBConcernsBack.class);
                    if (backDetail.getReturn_code() != 0) {
                        Toast.makeText(context, backDetail.getReturn_message(), Toast.LENGTH_SHORT).show();
                    }else if(backDetail.getReturn_code()==1101||backDetail.getReturn_code()==1102){
                        toastSHORT("重复登录或令牌超时");
                        LogoutUtils.exitUser(CooperateContents.this);
                    }

                    else {
                    }
                }

                @Override
                public void onError(Call call, Response response, Exception e) {
                    super.onError(call, response, e);
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

}
