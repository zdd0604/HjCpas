package com.hj.casps.commodity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.hj.casps.R;
import com.hj.casps.base.ActivityBaseHeader2;
import com.hj.casps.common.Constant;
import com.hj.casps.entity.PublicArg;
import com.hj.casps.entity.goodsmanager.Pub;
import com.hj.casps.entity.goodsmanager.SelectPicture02ListEntity;
import com.hj.casps.entity.picturemanager.request.ReqDelMal;
import com.hj.casps.entity.picturemanager.request.RequestShowPic;
import com.hj.casps.entity.picturemanager.response.ShowPicEntity;
import com.hj.casps.ui.MyToast;
import com.hj.casps.util.BitmapUtils;
import com.hj.casps.util.BitmapUtils2;
import com.hj.casps.util.DataCleanManager;
import com.hj.casps.util.GsonTools;
import com.hj.casps.util.LogoutUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.appsdream.nestrefresh.base.AbsRefreshLayout;
import cn.appsdream.nestrefresh.base.OnPullListener;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import mehdi.sakout.fancybuttons.FancyButton;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by YaoChen on 2017/4/13.
 */
public class PeoplePicture extends ActivityBaseHeader2 implements OnPullListener {

    private ShowPicEntity showPicEntity;
    private int mPosition = -1;
    private Map<Integer, Boolean> map = new HashMap<>();
    public int PageFlag;
    private LinearLayout barLayout;
    private FancyButton addPic;
    private RecyclerView recycleView;
    private RelativeLayout topRelayout;
    private List<Map<String, Object>> data_list;
    private TextView barLayoutCancel;
    private TextView barLayoutSubmit;
    private final int RequestCodeForEditImageRes = 1028;
    private String divId;
    private List<File> imagePathList = new ArrayList<>();

    private SelectPicture02Adapter adapter;
    private final int QUERY_PICTRUE_NAME = 111;
    //删除图片
    private FancyButton del;
    private List<ShowPicEntity.DataListBean> mList = new ArrayList<>();
    private ArrayList<SelectPicture02ListEntity> checkListEntity;
    public ProgressDialog prdialog;
    private int ImageSum = 50; //默认显示的图片张数

    Handler mHadler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.HANDLERTYPE_0:
                    initData(pageNo);
                    break;
                case Constant.HANDLERTYPE_1:
                    setAdapter();
                    break;
                case Constant.HANDLERTYPE_2:
                    break;
                case Constant.HANDLERTYPE_3:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_selectpic);
        //img10102
        //FragementTemporaryPic传过来的divid
        divId = (String) getIntent().getExtras().get(Constant.INTENT_DIV_ID);
        initView();
        viewShow();

    }

    //当pageno=0的时候的设置数据
    private void setData() {
        if (adapter == null) {
            adapter = new SelectPicture02Adapter();
        }
        adapter.getData().clear();
        recycleView.setAdapter(adapter);
        if (mList != null)
            for (int i = 0; i < mList.size(); i++) {
                SelectPicture02ListEntity data1 = new SelectPicture02ListEntity();
                data1.setImageName(mList.get(i).getName());
                data1.setImagePath(mList.get(i).getPicPath());
                data1.setImgId(mList.get(i).getMaterialId());
                data1.setMaterialPath(mList.get(i).getMaterialPath());
                adapter.addData(data1);
            }
//        adapter.notifyDataSetChanged();
    }

    //设置加载更多的数据
    private void setLoadData() {
        if (adapter == null) {
            adapter = new SelectPicture02Adapter();
        }
        recycleView.setAdapter(adapter);
        if (mList != null)
            for (int i = 0; i < mList.size(); i++) {
                SelectPicture02ListEntity data1 = new SelectPicture02ListEntity();
                data1.setImageName(mList.get(i).getName());
                data1.setImagePath(mList.get(i).getPicPath());
                data1.setImgId(mList.get(i).getMaterialId());
                data1.setMaterialPath(mList.get(i).getMaterialPath());
                adapter.addData(data1);
            }
        int itemCount = recycleView.getAdapter().getItemCount();
        /*跳转到加载所在位置*/
        recycleView.smoothScrollToPosition(itemCount);
    }

    /**
     * 获取网络数据
     *
     * @param pageno
     */
    private void initData(final int pageno) {
        //TODO
        RequestShowPic r = new RequestShowPic(
                publicArg.getSys_token(),
                Constant.getUUID(),
                Constant.SYS_FUNC,
                publicArg.getSys_user(),
                publicArg.getSys_member(),
                divId, "",
                Constant.MaterialName,
                String.valueOf(pageno + 1),
                String.valueOf(pageSize + 2));
        LogShow("r PeoplePicture=" + mGson.toJson(r));
        OkGo.post(Constant.ShowPicUrl)
                .params("param", mGson.toJson(r))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        showPicEntity = GsonTools.changeGsonToBean(s, ShowPicEntity.class);
                        mList.clear();
                        if (showPicEntity.getReturn_code() == 0 && showPicEntity != null) {
                            total = showPicEntity.getTotalCount();
                            mList = showPicEntity.getDataList();
                            mHadler.sendEmptyMessage(Constant.HANDLERTYPE_1);
                        } else if (showPicEntity.getReturn_code() == 1101 || showPicEntity.getReturn_code() == 1102) {
                            LogoutUtils.exitUser(PeoplePicture.this);
                        } else {
                            toastSHORT(showPicEntity.getReturn_message());
                            return;
                        }
                    }
                });


    }

    /**
     * 请求完成后返回数据
     * 并刷新数据
     */
    private void setAdapter() {
        if (pageNo != 0) {
            if (pageNo <= ((total - 1) / (pageSize + 2))) {
                setLoadData();
            } else {
                mLoader.onLoadAll();
            }
        } else {
            setData();
        }
    }

    private void viewShow() {
        PageFlag = getIntent().getIntExtra("PicStyle", 0);
        if (PageFlag == 1) {
            barLayout.setVisibility(View.VISIBLE);
            setTitle(getString(R.string.select_title_pic));
        } else if (PageFlag == 2) {
            setTitle(getString(R.string.select_banner_pic));
            barLayout.setVisibility(View.VISIBLE);
        } else {
            setTitle("分类");
            topRelayout.setVisibility(View.VISIBLE);
            barLayout.setVisibility(View.VISIBLE);
        }

    }

    private void initView() {
        //设置有点查询按钮
        setTitleRight(null, ResourcesCompat.getDrawable(getResources(), R.mipmap.nav_ser, null));

        barLayout = (LinearLayout) findViewById(R.id.selectpic02_ll);
        barLayoutCancel = (TextView) findViewById(R.id.selectpic02_cancel);
        barLayoutSubmit = (TextView) findViewById(R.id.selectpic02_true);
        topRelayout = (RelativeLayout) findViewById(R.id.selectctpic02_select_ll);
        recycleView = (RecyclerView) findViewById(R.id.selectpic02_grid);
        del = (FancyButton) findViewById(R.id.del);
        recycleView.setLayoutManager(new GridLayoutManager(this, 3));
        mLoader = new NestRefreshLayout(recycleView);
        mLoader.setOnLoadingListener(this);
        mLoader.setPullLoadEnable(true);
        mLoader.setPullRefreshEnable(true);

        recycleView.addItemDecoration(new SelectPicture02.SelectPicture02ItemDecoration(this));
        recycleView.addOnItemTouchListener(new SelectPicture02ItemClick());
        addPic = (FancyButton) findViewById(R.id.add);
        addPic.setText("上传");
        SelectPicture02Click l = new SelectPicture02Click();
        addPic.setOnClickListener(l);
        del.setOnClickListener(l);
        barLayoutCancel.setOnClickListener(l);
        barLayoutSubmit.setOnClickListener(l);

        if (hasInternetConnected())
            mHadler.sendEmptyMessage(Constant.HANDLERTYPE_0);

    }

    @Override
    protected void onRightClick() {
        intentActivity(ActivityPictureSearch.class, QUERY_PICTRUE_NAME);
    }

    @Override
    public void onRefresh(AbsRefreshLayout listLoader) {
        pageNo = 0;
        mHadler.sendEmptyMessage(Constant.HANDLERTYPE_0);
        mLoader.onLoadFinished();
    }

    @Override
    public void onLoading(AbsRefreshLayout listLoader) {
        pageNo++;
        mHadler.sendEmptyMessage(Constant.HANDLERTYPE_0);
        mLoader.onLoadFinished();
    }


    private class SelectPicture02Click implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.add:
                    //打开相机 选择图片
                    openCamera();
                    break;
                case R.id.selectpic02_cancel:
                    back();
                    break;
                case R.id.selectpic02_true:
                     /*确定*/
                    onSubmit();
                    break;
                case R.id.del:
                    /*删除图片*/
                    delPic();
                    break;
            }


        }

        //打开相机 选择图片
        private void openCamera() {
              /*多选图片*/
            RxGalleryFinal.with(PeoplePicture.this)
                    .image()
                    .multiple()
                    .maxSize(ImageSum)
                    .imageLoader(ImageLoaderType.GLIDE)
                    .subscribe(new RxBusResultSubscriber<ImageMultipleResultEvent>() {
                        @Override
                        protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
//                            if (imageMultipleResultEvent.getResult().size() > ImageSum) {
//                                toastSHORT("同时最多上传8张图片");
//                                return;
//                            }
                            for (int i = 0; i < imageMultipleResultEvent.getResult().size(); i++) {
                                String imagePath = imageMultipleResultEvent.getResult().get(i).getOriginalPath();
                                LogShow(imagePath);
                                File e = new File(BitmapUtils2.getCompressFile(imagePath));
                                imagePathList.add(e);
                            }
                            onSelectImage();
                        }

                        @Override
                        public void onCompleted() {
                            super.onCompleted();
                            Toast.makeText(getBaseContext(), "OVER", Toast.LENGTH_SHORT).show();
                        }
                    }).openGallery();
        }
    }

    //删除图片
    private void delPic() {
        SelectPicture02Adapter adapter = (SelectPicture02Adapter) recycleView.getAdapter();
        List<SelectPicture02ListEntity> datas = adapter.getData();
        if (map.isEmpty()) {
            toast("您必须选择一张图片删除");
            return;
        }
        if (map.size() > 1) {
            toast("每次只能删除一张");
            return;
        }
        for (int i = 0; i < datas.size(); i++) {
            for (Integer key : map.keySet()) {
                if (key == i) {
                    SelectPicture02ListEntity selectPic = datas.get(i);
                    String imgId = selectPic.getImgId();
                    delPicForNet(imgId, datas, adapter, i);
                }
            }
        }
    }

    /**
     * @param imgId   图片id
     * @param datas   数据集合
     * @param adapter
     * @param index   选中的角标
     */
    private void delPicForNet(String imgId, final List<SelectPicture02ListEntity> datas,
                              final SelectPicture02Adapter adapter, final int index) {
        PublicArg p = Constant.publicArg;
        String timeUUID = Constant.getTimeUUID();
        if (timeUUID.equals("")) {
            toastSHORT(getString(R.string.time_out));
            return;
        }
        ReqDelMal r = new ReqDelMal(p.getSys_token(),
                timeUUID,
                Constant.SYS_FUNC,
                p.getSys_user(),
                p.getSys_member(),
                imgId);
        String param = mGson.toJson(r);
        waitDialogRectangle.show();
        OkGo.post(Constant.DelMalUrl)
                .params("param", param)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        waitDialogRectangle.dismiss();
                        Pub pub = GsonTools.changeGsonToBean(s, Pub.class);
                        if (pub != null) {
                            if (pub.getReturn_code() == 0) {
                                //删除列表对应角标的数据
                                new MyToast(PeoplePicture.this, "删除图片成功");
                                map.clear();
                                adapter.remove(index);
                            } else if (pub.getReturn_code() == 1101 || pub.getReturn_code() == 1102) {
                                LogoutUtils.exitUser(PeoplePicture.this);
                            } else {
                                toast(pub.getReturn_message());
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        waitDialogRectangle.dismiss();
                    }
                });


    }

    //收集选中图片传到所用界面
    private void onSubmit() {
        checkListEntity = new ArrayList<>();
        SelectPicture02Adapter adapter = (SelectPicture02Adapter) recycleView.getAdapter();
        List<SelectPicture02ListEntity> datas = adapter.getData();
        for (int i = 0; i < datas.size(); i++) {
            for (Integer key : map.keySet()) {
                if (key == i) {
                    checkListEntity.add(datas.get(i));
                }
            }
        }
        if (PeoplePicture.this.PageFlag == 2) {
            ImageData imageData = new ImageData();
            imageData.setList(checkListEntity);
            //通过eventBus传递数据  接受者只要注册 然后接收对应数据内容即可拿到数据   ACTIVITY_EDIT_GOODS    里面接收数据
            EventBus.getDefault().post(imageData);
        }
        if (PeoplePicture.this.PageFlag == 1) {
            if (checkListEntity.size() != 1) {
               /* for (int i = 0; i < datas.size(); i++) {
                    datas.get(i).setCheck(false);
                }*/
//                  map.clear();
                checkListEntity.clear();
            /**/
                adapter.notifyDataSetChanged();
                toastSHORT("标题图片只能选择一张");
                return;
            }
            ImageData imageData = new ImageData();
            imageData.setEntity(checkListEntity.get(0));
            //通过eventBus传递数据  接受者只要注册 然后接收对应数据内容即可拿到数据   ACTIVITY_EDIT_GOODS    里面接收数据
            EventBus.getDefault().post(imageData);
        }
        PeoplePicture.this.finish();
        Constant.isAddPic = true;
        PeoplePicture.this.setResult(SelectPicture02.RESULT_CODE);

    }

    private void onSelectImage() {
        Intent intent = new Intent(this, SelectPicture03.class);
        intent.putExtra(Constant.INTENT_DIV_ID, divId);
        startActivityForResult(intent, RequestCodeForEditImageRes);
    }

    private class SelectPicture02ItemClick extends SimpleClickListener {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            SelectPicture02ListEntity entity = (SelectPicture02ListEntity) adapter.getItem(position);
            /**
             * 当mPostion小于0时候，说明之前没有选中过isCheck就设置为true选中状态
             */
            // ==2表示轮播图  《多选》
           /* if (PeoplePicture.this.PageFlag == 2) {*/
            if (mPosition < 0) {
                entity.setCheck(!entity.isCheck());
                mPosition = position;
                //0   ture
                map.put(mPosition, entity.isCheck());
            }
            //当map集合有选中的postion时候，就取出来取反设置
            else if (mPosition >= 0) {
                if (map.containsKey(position)) {
                    Boolean flag = (Boolean) map.get(position);
                    entity.setCheck(!flag);
                    mPosition = position;
                    map.remove(mPosition);
                } else {
                    //当map集合里没有记录就全部设置为选中状态
                    entity.setCheck(true);
                    mPosition = position;
                    //1    ture
                    map.put(mPosition, true);
                }
            }
            // ==1表示轮播图  《单选》
           /* } else if (PeoplePicture.this.PageFlag == 1) {
                entity.setCheck(!entity.isCheck());
                map.put(position, entity.isCheck());
            }*/
            adapter.notifyItemChanged(position);
        }

        @Override
        public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        }

        @Override
        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        }

        @Override
        public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
        }
    }

    private class SelectPicture02Adapter extends BaseQuickAdapter<SelectPicture02ListEntity, BaseViewHolder> {

        public SelectPicture02Adapter() {
            super(R.layout.selectpic_griditem, null);
        }

        @Override
        protected void convert(BaseViewHolder helper, SelectPicture02ListEntity item) {
            int width = getResources().getDisplayMetrics().widthPixels;
            width = (width - 25) / 3;
            ViewGroup.LayoutParams layoutParams = helper.getConvertView().getLayoutParams();
            layoutParams.height = (int) (width * 1.1f);
            layoutParams.width = width;
            TextView textView = helper.getView(R.id.selectpic_tv);
            ImageView imageView = helper.getView(R.id.selectpic_pic_img);
            //假数据使用
//            if ((PageFlag == 1) || (PageFlag == 2)) {
//                Glide.with(SelectPicture02.this).load(item.getImagePath()).error(R.drawable.default_imgs).into(imageView);
//            } else {
//            }
//            Glide.with(PeoplePicture.this).load(Constant.SHORTHTTPURL + item.getImagePath()).error(R.mipmap.c_shop).into(imageView);
            BitmapUtils.LoadImage(item.getImagePath(), imageView, 400, 500);
            textView.setText(item.getImageName());

            helper.setVisible(R.id.selectpic_ischeck, item.isCheck());

        }

    }

    /**
     * Gridlayoutmanager 分隔线
     * http://blog.csdn.net/lmj623565791/article/details/45059587
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清空查询条件
        Constant.MaterialName = "";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            //上传图片的处理
            if (requestCode == RequestCodeForEditImageRes) {
                //图片路径
//                String image = data.getStringExtra(SelectPicture03.ExtraImagePath);
                String imageName = data.getStringExtra(SelectPicture03.ExtraImageName);
                uploadImage(divId, imageName);
            }
            //点击查询图片名字
            if (requestCode == QUERY_PICTRUE_NAME) {
                pageNo = 0;
                mHadler.sendEmptyMessage(Constant.HANDLERTYPE_0);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    //上传图片
    private void uploadImage(String divId, String imageName) {
        if (imagePathList == null)
            return;
        final String timeUUID = Constant.getTimeUUID();
        if (timeUUID.equals("")) {
            toastSHORT(getString(R.string.time_out));
            return;
        }

        prdialog = new ProgressDialog(this);
        prdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        prdialog.setMessage("正在上传图片");
        prdialog.setCanceledOnTouchOutside(false);
        prdialog.setMax(100);
        prdialog.show();

        final String paramStr = "?"
                + "sys_token=" + publicArg.getSys_token()
                + "&sys_uuid=" + publicArg.getSys_uuid()
                + "&sys_func=" + Constant.SYS_FUNC
                + "&sys_user=" + publicArg.getSys_user()
                + "&sys_member=" + publicArg.getSys_member()
                + "&divId=" + divId
                + "&imgName=" + imageName;

        LogShow(imagePathList.size() + "------" + paramStr);

        OkGo.post(Constant.ImageUploadUrl + paramStr)//
                .tag(this)//
                .isMultipart(true)
                .addFileParams("image", imagePathList)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Pub pub = GsonTools.changeGsonToBean(s, Pub.class);
                        if (pub != null && pub.getReturn_code() == 0) {
                            new MyToast(PeoplePicture.this, "上传图片成功");
                            //刷新图片列表
                            pageNo = 0;
                            mHadler.sendEmptyMessage(Constant.HANDLERTYPE_0);
                        } else if (pub.getReturn_code() == 1101 || pub.getReturn_code() == 1102) {
                            LogoutUtils.exitUser(PeoplePicture.this);
                        } else {
                            toast(pub.getReturn_message());
                        }
                        deleteDatas();
                    }

                    /**
                     * Post执行上传过程中的进度回调，get请求不回调，UI线程
                     * @param currentSize  当前上传的字节数
                     * @param totalSize    总共需要上传的字节数
                     * @param progress     当前上传的进度
                     * @param networkSpeed 当前上传的速度 字节/秒
                     */
                    @Override
                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        super.upProgress(currentSize, totalSize, progress, networkSpeed);
                        LogShow("currentSize = [" + currentSize + "], totalSize = [" + totalSize + "], " +
                                "progress = [" + progress + "], networkSpeed = [" + networkSpeed + "]");
                        LogShow("总大小:" + DataCleanManager.getFormatSize(totalSize));
                        LogShow("当前大小:" + DataCleanManager.getFormatSize(currentSize));
                        prdialog.setProgress((int) (progress * 100));
                        if (((int) (progress * 100)) == 100) {
//                            pageNo = 0;
//                            mHadler.sendEmptyMessage(Constant.HANDLERTYPE_0);
                            deleteDatas();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        toastSHORT("上传失败");
                        deleteDatas();
                    }
                });      // 这种方式为同一个key，上传多个文件
    }

    /**
     * 清空缓存
     */
    private void deleteDatas() {
        prdialog.dismiss();
        imagePathList.clear();
    }
}
