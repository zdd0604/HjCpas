package com.hj.casps.commodity;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
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
import com.hj.casps.entity.goodsmanager.SelectPicture02ListEntity;
import com.hj.casps.entity.picturemanager.request.RequestShowPic;
import com.hj.casps.entity.picturemanager.response.ShowPicEntity;
import com.hj.casps.util.BitmapUtils;
import com.hj.casps.util.GsonTools;
import com.hj.casps.util.LogoutUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

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
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import mehdi.sakout.fancybuttons.FancyButton;
import okhttp3.Call;
import okhttp3.Response;

/**平台和临时素材库的图片列表
 * Created by YaoChen on 2017/4/13.
 */
public class SelectPicture02 extends ActivityBaseHeader2 implements OnPullListener {
    private static ShowPicEntity showPicEntity;
    private int mPosition = -1;
    private Map<Integer, Boolean> map = new HashMap<>();
    public int PageFlag;
    private LinearLayout barLayout;
    private FancyButton uploadBt;
    private RecyclerView recycleView;
    private RelativeLayout topRelayout;
    private List<Map<String, Object>> data_list;
    private TextView barLayoutCancel;
    private TextView barLayoutSubmit;
    private final int RequestCodeForEditImageRes = 1028;
    private String divId;
    public static final int RESULT_CODE = 666;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                //数据请求成功
                case 0:
                    break;
                case 1:
                    break;
            }
        }
    };
    private SelectPicture02Adapter adapter;
    private final int QUERY_PICTRUE_NAME = 111;
    private NestRefreshLayout mLoader;
    private int pageno = 0;
    private int pagesize = 12;
    private List<ShowPicEntity.DataListBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectpic02);
        System.out.println("savedInstanceState  SelectPicture02= [" + savedInstanceState + "]");
        //img10102
        //FragementTemporaryPic传过来的divid
        divId = (String) getIntent().getExtras().get(Constant.INTENT_DIV_ID);

        initView();
        viewShow();
        initData(pageno);

    }

    private void setData() {
        if (adapter == null) {
            adapter = new SelectPicture02Adapter();
        }
        adapter.getData().clear();
//  BaseQuickAdapter  adapter = (BaseQuickAdapter) recycleView.getAdapter();
        recycleView.setAdapter(adapter);
        for (int i = 0; i < mList.size(); i++) {
            SelectPicture02ListEntity data1 = new SelectPicture02ListEntity();
            data1.setImageName(mList.get(i).getName());
            data1.setImagePath(mList.get(i).getPicPath());
            data1.setImgId(mList.get(i).getMaterialId());
            data1.setMaterialPath(mList.get(i).getMaterialPath());
            adapter.addData(data1);
        }

    }


    private void initData(final int pageno) {
        PublicArg p = Constant.publicArg;
        //TODO divId写死的
        RequestShowPic r = new RequestShowPic(p.getSys_token(),
                Constant.getUUID(), Constant.SYS_FUNC,
                p.getSys_user(), p.getSys_member(),
                divId, "",
                Constant.MaterialName,
                String.valueOf(pageno + 1),
                String.valueOf(pagesize));
        String param = mGson.toJson(r);
        waitDialogRectangle.show();
        OkGo.post(Constant.ShowPicUrl).params("param", param).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                waitDialogRectangle.dismiss();
                ShowPicEntity showPicEntity = GsonTools.changeGsonToBean(s, ShowPicEntity.class);
                if (showPicEntity != null && showPicEntity.getReturn_code() == 0) {
                    if (pageno != 0) {
                        if (pageno <= ((showPicEntity.getTotalCount() - 1) / pagesize)) {
                            SelectPicture02.this.mList = showPicEntity.getDataList();
                            setLoadData();
                        } else {
                            mLoader.onLoadAll();
                        }
                    } else {
                        SelectPicture02.this.mList = showPicEntity.getDataList();
                        setData();
                    }
                }else if(showPicEntity.getReturn_code()==1101||showPicEntity.getReturn_code()==1102){
                    LogoutUtils.exitUser(SelectPicture02.this);
                }
                else {
                    toast(showPicEntity.getReturn_message());
                    return;
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                waitDialogRectangle.dismiss();
            }
        });
    }

    private void setLoadData() {
        if (adapter == null) {
            adapter = new SelectPicture02Adapter();
        }
        recycleView.setAdapter(adapter);
        for (int i = 0; i < mList.size(); i++) {
            SelectPicture02ListEntity data1 = new SelectPicture02ListEntity();
            data1.setImageName(mList.get(i).getName());
            data1.setImagePath(mList.get(i).getPicPath());
            data1.setImgId(mList.get(i).getMaterialId());
            data1.setMaterialPath(mList.get(i).getMaterialPath());
            adapter.addData(data1);
        }
        int itemCount = recycleView.getAdapter().getItemCount();
        recycleView.smoothScrollToPosition(itemCount);
    }

    private void viewShow() {
        PageFlag = getIntent().getIntExtra("PicStyle", 0);
        if (PageFlag == 1) {
            setTitle(getString(R.string.select_title_pic));
            getData();
        } else if (PageFlag == 2) {
            setTitle(getString(R.string.select_banner_pic));
            barLayout.setVisibility(View.VISIBLE);
            getData();
        } else {
            setTitle("分类");
            topRelayout.setVisibility(View.VISIBLE);
            barLayout.setVisibility(View.VISIBLE);
        }

    }

    private void getData() {

    }

    private void initView() {
        //设置有点查询按钮
        setTitleRight(null, ResourcesCompat.getDrawable(getResources(), R.mipmap.nav_ser, null));

        barLayout = (LinearLayout) findViewById(R.id.selectpic02_ll);
        barLayoutCancel = (TextView) findViewById(R.id.selectpic02_cancel);
        barLayoutSubmit = (TextView) findViewById(R.id.selectpic02_true);
        topRelayout = (RelativeLayout) findViewById(R.id.selectctpic02_select_ll);
        recycleView = (RecyclerView) findViewById(R.id.selectpic02_grid);
        mLoader = new NestRefreshLayout(recycleView);
        mLoader.setOnLoadingListener(this);
        mLoader.setPullLoadEnable(true);
        mLoader.setPullRefreshEnable(true);
        recycleView.setLayoutManager(new GridLayoutManager(this, 3));
        recycleView.addItemDecoration(new SelectPicture02ItemDecoration(this));
        recycleView.addOnItemTouchListener(new SelectPicture02ItemClick());

        uploadBt = (FancyButton) findViewById(R.id.selectctpic02_select_image);
        SelectPicture02Click l = new SelectPicture02Click();
        uploadBt.setOnClickListener(l);
        barLayoutCancel.setOnClickListener(l);
        barLayoutSubmit.setOnClickListener(l);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == RequestCodeForEditImageRes) {
                String imagePath = data.getStringExtra(SelectPicture03.ExtraImagePath);
                String imageName = data.getStringExtra(SelectPicture03.ExtraImageName);
                BaseQuickAdapter adapter = (BaseQuickAdapter) recycleView.getAdapter();
                SelectPicture02ListEntity data1 = new SelectPicture02ListEntity();
                data1.setImageName(imageName);
                data1.setImagePath(imagePath);
                adapter.addData(data1);
            }
            //点击查询图片名字
            if (requestCode == QUERY_PICTRUE_NAME) {
                pageno = 0;
                initData(pageno);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onRightClick() {
        intentActivity(ActivityPictureSearch.class, QUERY_PICTRUE_NAME);
    }

    @Override
    public void onRefresh(AbsRefreshLayout listLoader) {
        pageno = 0;
        initData(pageno);
        mLoader.onLoadFinished();
    }

    @Override
    public void onLoading(AbsRefreshLayout listLoader) {
        pageno++;
        initData(pageno);
        mLoader.onLoadFinished();
    }

    //上传图片的点击事件
    private class SelectPicture02Click implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.selectctpic02_select_image) {
                /*选择图片，单选*/
                RxGalleryFinal.with(SelectPicture02.this)
                        .image()
                        .radio()
                        .imageLoader(ImageLoaderType.GLIDE)
                        .subscribe(new RxBusResultSubscriber<ImageRadioResultEvent>() {
                            @Override
                            protected void onEvent(ImageRadioResultEvent imageMultipleResultEvent) throws Exception {
                                log(imageMultipleResultEvent.getResult() + "张图片");
                                String imagePath = imageMultipleResultEvent.getResult().getOriginalPath();
                                onSelectImage(imagePath);
                            }
                            @Override
                            public void onCompleted() {
                                super.onCompleted();
                                Toast.makeText(getBaseContext(), "OVER", Toast.LENGTH_SHORT).show();
                            }
                        }).openGallery();
            } else if (id == R.id.selectpic02_cancel) {
                /*取消*/
                back();
            } else if (id == R.id.selectpic02_true) {
                /*确定*/
                onSubmit();
            }
        }
    }
    //收集传递图片内容到其他页面
    private void onSubmit() {
        ArrayList<SelectPicture02ListEntity> checkListEntity = new ArrayList<>();
        SelectPicture02Adapter adapter = (SelectPicture02Adapter) recycleView.getAdapter();
        List<SelectPicture02ListEntity> datas = adapter.getData();
        for (int i = 0; i < datas.size(); i++) {
            for (Integer key : map.keySet()) {
                if (key == i) {
                    checkListEntity.add(datas.get(i));
                }
            }
        }
        if (SelectPicture02.this.PageFlag == 2) {
            ImageData imageData = new ImageData();
            imageData.setList(checkListEntity);
            EventBus.getDefault().post(imageData);
        }
        if (SelectPicture02.this.PageFlag == 1) {
            ImageData imageData = new ImageData();
            imageData.setEntity(checkListEntity.get(0));
            EventBus.getDefault().post(imageData);
        }
        SelectPicture02.this.finish();
        Constant.isAddPic=true;
        SelectPicture02.this.setResult(RESULT_CODE);

    }
    //跳转到其他页面来输入上传图片的名称
    private void onSelectImage(String imagePath) {
        Intent intent = new Intent(this, SelectPicture03.class);
        intent.putExtra(SelectPicture03.ExtraImagePath, imagePath);
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
            if (SelectPicture02.this.PageFlag == 2) {
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
            } else if (SelectPicture02.this.PageFlag == 1) {
                entity.setCheck(!entity.isCheck());
                map.put(position, entity.isCheck());
                onSubmit();
            }
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
//            Glide.with(SelectPicture02.this).load(Constant.SHORTHTTPURL + item.getImagePath()).error(R.drawable.default_imgs).into(imageView);
            BitmapUtils.LoadImage(item.getImagePath(), imageView, 400, 500);
            textView.setText(item.getImageName());
            helper.setVisible(R.id.selectpic_ischeck, item.isCheck());

        }
    }

    /**
     * Gridlayoutmanager 分隔线
     * http://blog.csdn.net/lmj623565791/article/details/45059587
     */
    public static class SelectPicture02ItemDecoration extends RecyclerView.ItemDecoration {
        private final int[] ATTRS = new int[]{android.R.attr.listDivider};
        private Drawable mDivider;
        int intrinsicHeight;

        public SelectPicture02ItemDecoration(Context context) {
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            intrinsicHeight = mDivider.getIntrinsicHeight();
            a.recycle();
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

            drawHorizontalLine(c, parent, state);
            drawVerticalLine(c, parent, state);

        }

        private int getSpanCount(RecyclerView parent) {
            // 列数
            int spanCount = -1;
            RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
            }
            return spanCount;
        }

        public void drawHorizontalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);

                //获得child的布局信息
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
                //Log.d("wnw", left + " " + top + " "+right+"   "+bottom+" "+i);
            }
        }

        //画竖线
        public void drawVerticalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int top = parent.getPaddingTop();
            int bottom = parent.getHeight() - parent.getPaddingBottom();
            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);

                //获得child的布局信息
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int left = child.getRight() + params.rightMargin;
                final int right = left + mDivider.getIntrinsicWidth();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        /**
         * 横向是否需要画分隔线
         *
         * @param parent
         * @param pos
         * @return
         */
        private boolean isColumnDrawDiv(RecyclerView parent, int pos) {
            RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                GridLayoutManager gridLayoutParam = (GridLayoutManager) layoutManager;
                int spanCount = gridLayoutParam.getSpanCount();
                int spanIndex = gridLayoutParam.getSpanSizeLookup().getSpanIndex(pos, spanCount);
                if (spanIndex == 0 || spanIndex == spanCount - 1) {
                    return false;
                } else {
                    return true;
                }
            }
            return false;
        }

        /**
         * 水平方向是否要画分隔线
         *
         * @param parent
         * @param pos
         * @return
         */
        private boolean isRowDrawDiv(RecyclerView parent, int pos) {
            RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                int itemCount = parent.getAdapter().getItemCount();
                GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                int spanCount = gridLayoutManager.getSpanCount();
                int lastLineIndex = itemCount % spanCount == 0 ? (itemCount / spanCount) : (itemCount / spanCount + 1);
                if (pos >= spanCount * (lastLineIndex - 1)) {
                    return true;
                }
            }
            return true;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            int itemPosition = parent.getChildAdapterPosition(view);
            boolean isDrawRow = isRowDrawDiv(parent, itemPosition);
            boolean isDrawColumn = isColumnDrawDiv(parent, itemPosition);
            int rightPadding = 0, bottomPadding = 0;
            if (isDrawRow) {
                bottomPadding = intrinsicHeight;
            }
            if (isDrawColumn) {
                rightPadding = intrinsicHeight;
            }
            outRect.set(0, 0, rightPadding, bottomPadding);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清空查询条件
        Constant.MaterialName = "";
    }

    /*@Override
    protected void onBackClick() {
        SelectPicture02.this.finish();
    }*/
}
