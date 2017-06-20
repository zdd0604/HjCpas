package com.hj.casps.base;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hj.casps.common.Constant;
import com.hj.casps.ui.MyDialog;
import com.hj.casps.widget.WaitDialogRectangle;
import com.lzy.okgo.OkGo;

import cn.appsdream.nestrefresh.base.AbsRefreshLayout;

/**
 * fragment 懒加载
 * http://www.jianshu.com/p/104be7cd72b6
 */

public class FragmentBase extends Fragment {
    public WaitDialogRectangle waitDialogRectangle;
    public Gson mGson;
    //是否可见
    protected boolean isVisble;
    // 标志位，标志Fragment已经初始化完成。
    public boolean isPrepared = false;
    private boolean hasLoadData = false;
    public int pageNo = 0;//	int	开始行
    public int pageSize = 10;//	int	页条数
    public int total;
    public MyDialog myDialog;
    public Context context;
    public AbsRefreshLayout mLoader;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isPrepared = true;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 跳转页面
     *
     * @param to
     * @param bundle
     */
    public void intentActivity(Class to, Bundle bundle) {
        Intent intent = new Intent(getActivity(), to);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 长toast
     *
     * @param content
     */
    public void toastLONG(String content) {
        Toast.makeText(getActivity(), content, Toast.LENGTH_LONG).show();
    }

    /**
     * 短toast
     *
     * @param content
     */
    public void toastSHORT(String content) {
        Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短toast
     *
     * @param content
     */
    public void LogShow(String content) {
        Log.e("casps", content);
    }


    public void intentActivity(Context from, Class to) {
        Intent intent = new Intent(from, to);
        startActivity(intent);
    }





    public void intentActivity(Class toClass, int ac_type) {
        Intent intent = new Intent(getActivity(), toClass);
        startActivityForResult(intent, ac_type);
    }

    public void intentActivity(Class toClass, int ac_type, Bundle bundle) {
        Intent intent = new Intent(getActivity(), toClass);
        intent.putExtras(bundle);
        startActivityForResult(intent, ac_type);
    }

    /**
     * bundle
     *
     * @param from
     * @param to
     */
    public void intentActivity(Context from, Class to, Bundle bundle) {
        Intent intent = new Intent(from, to);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    /**
     * 实现Fragment数据的缓加载
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisble = true;
            onVisible();
        } else {
            isVisble = false;
            onInVisible();
        }
    }

    public boolean hasInternetConnected() {
        ConnectivityManager manager = (ConnectivityManager) getActivity()
                .getSystemService(getActivity().CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo network = manager.getActiveNetworkInfo();
            if (network != null && network.isConnectedOrConnecting()) {
                return true;
            }
        }
        return false;
    }

    protected void log(String s) {
        Log.d(this.getClass().getSimpleName(), s);
    }

    protected void toast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }

 /*   @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisble = true;
            onVisible();
        } else {
            isVisble = false;
            onInVisible();
        }
    }*/

    protected void onInVisible() {
    }

    protected void onVisible() {
        if (isPrepared && isVisble && !hasLoadData) {
            loadData();
        }
    }

    protected void loadData() {
        hasLoadData = true;
    }

    public void intentActivity(Class toClass) {
        Intent intent = new Intent(getActivity(), toClass);
        startActivity(intent);
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        //根据 Tag 取消请求
//        OkGo.getInstance().cancelTag(this);
//        //取消所有请求
//        OkGo.getInstance().cancelAll();
//        delete();
//    }


    private void delete() {
        total = 0;
        pageNo = 0;
        Constant.appOrderMoney_settleCode = "";//	string	结款单号
        Constant.appOrderMoney_oppositeName = "";//	string	结款对方
        Constant.appOrderMoney_settlestatus = "";//	string	状态（1全部、2执行中、3本方请求终止、4对方请求终止）
        Constant.appOrderMoney_executeStartTime = "";//datetime		开始时间
        Constant.appOrderMoney_executeEndTime = "";//datetime		结束时间
        Constant.appSettle_statusRegist = "";//string	状态（0全部、1未申请登记、2已申请登记）
    }
}
