package wqj.com.oakdesigner.activity.main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.refactor.lib.colordialog.ColorDialog;
import cn.refactor.lib.colordialog.PromptDialog;
import wqj.com.oakdesigner.R;
import wqj.com.oakdesigner.activity.adapter.Adapter_blue_process;
import wqj.com.oakdesigner.activity.adapter.Adapter_decorate_design;
import wqj.com.oakdesigner.activity.adapter.Adapter_info_center;
import wqj.com.oakdesigner.activity.bean.BeanBlueProcess;
import wqj.com.oakdesigner.activity.bean.BeanDecorateDesign;
import wqj.com.oakdesigner.activity.bean.BeanInfoCenter;
import wqj.com.oakdesigner.utils.Constant;
import wqj.com.oakdesigner.utils.FrescoImageLoader;
import wqj.com.oakdesigner.utils.RequestCode;
import wqj.com.oakdesigner.utils.StatusBarCompat;
import wqj.com.oakdesigner.utils.XPermissionUtils;

/**
 * Created by WQJ on 2017/7/20
 * Descripyion:主界面
 * Version: 1
 * Modify Person :
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.title_back_layout)
    LinearLayout titleBackLayout;
    @BindView(R.id.title_name_tv)
    TextView titleNameTv;
    @BindView(R.id.title_name_right)
    TextView titleNameRight;
    @BindView(R.id.title_layout_right)
    LinearLayout titleLayoutRight;
    @BindView(R.id.public_function_layout)
    LinearLayout publicFunctionLayout;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.img_ad)
    SimpleDraweeView imgAd;
    List images = new ArrayList();
    List titles = new ArrayList();
    List hrefs = new ArrayList();
    @BindView(R.id.recycler_design)
    RecyclerView mRecyclerDesign;
    @BindView(R.id.tv_more)
    TextView mTvMore;
    @BindView(R.id.img_more)
    ImageView mImgMore;
    @BindView(R.id.tv_more_blue)
    TextView mTvMoreBlue;
    @BindView(R.id.img_more_blue)
    ImageView mImgMoreBlue;
    @BindView(R.id.recycler_blue)
    RecyclerView mRecyclerBlue;
    @BindView(R.id.tv_more_info)
    TextView mTvMoreInfo;
    @BindView(R.id.img_more_info)
    ImageView mImgMoreInfo;
    @BindView(R.id.recycler_info)
    RecyclerView mRecyclerInfo;
    @BindView(R.id.layout_back)
    LinearLayout mLayoutBack;
    @BindView(R.id.img_delete)
    ImageView mImgDelete;
    @BindView(R.id.frame)
    FrameLayout mFrame;
    @BindView(R.id.img_book)
    ImageView mImgBook;
    private int isFirst = 0;
    private SharedPreferences sharedPreferences;
    private ImageView imgBook, imgDelete;
    private FrameLayout mFrameLayout;
    private List<BeanDecorateDesign> data = new ArrayList<>();
    private List<BeanBlueProcess> data1 = new ArrayList<>();
    private List<BeanInfoCenter> data2 = new ArrayList<>();
    private BeanDecorateDesign mBeanDecorateDesign;
    private BeanBlueProcess mBeanBlueProcess;
    private BeanInfoCenter mBeanInfoCenter;
    private Adapter_decorate_design mAdapterDecorateDesign;
    private Adapter_blue_process mAdapterBlueProcess;
    private Adapter_info_center mAdapterInfoCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerDesign.setLayoutManager(mLayoutManager);
        mRecyclerBlue.setLayoutManager(mLayoutManager1);
        mRecyclerInfo.setLayoutManager(mLayoutManager2);
        getBanner();
        initView();
        getPermissions();
        getDesigndecorate();
        getBlueProcess();
        getInfoCenter();
    }

    public void getPermissions() {
        imgBook = (ImageView) findViewById(R.id.img_book);
        imgDelete = (ImageView) findViewById(R.id.img_delete);
        mFrameLayout = (FrameLayout) findViewById(R.id.frame);
        sharedPreferences = getSharedPreferences("isFirst", Activity.MODE_PRIVATE);
        isFirst = sharedPreferences.getInt("isFirst", isFirst);
        if (Build.MANUFACTURER.equals("HUAWEI")) {//判断首次登陆并且是华为手机，进行权限授予
            if (isFirst == 0) {
                showDialog();
            } else {
            }
        } else {
            getPermission();
        }
        imgBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SubscribeDesignActivity.class);
                startActivity(intent);
            }
        });
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFrameLayout.setVisibility(View.GONE);
            }
        });
    }

    private void bannerOnclick() {
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Uri uri = Uri.parse(hrefs.get(position).toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    public void initView() {
        StatusBarCompat.compat(this, R.color.grayctitlecolor);
        titleNameTv.setText(R.string.app_name);
        titleNameRight.setBackgroundResource(R.drawable.ic_call_black_24dp);
        mLayoutBack.setVisibility(View.INVISIBLE);
        //add call state listener
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(MainActivity.TELEPHONY_SERVICE);
        PhoneCallListener phoneCallListener = new PhoneCallListener();
        telephonyManager.listen(phoneCallListener, PhoneStateListener.LISTEN_CALL_STATE);
        imgAd.setImageURI(Uri.parse("http://www.sz-oak.com/uploads/banner/marriage.gif"));
    }

    private void setBanner() {
//        设置图片加载框架
        banner.setImageLoader(new FrescoImageLoader());
//        设置轮播图格式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
//        设置图片合集
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.ZoomOutSlide);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }

    @OnClick({R.id.title_back_layout, R.id.img_ad, R.id.title_name_right, R.id.tv_more, R.id.img_more, R.id.tv_more_blue, R.id.img_more_blue, R.id.tv_more_info, R.id.img_more_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back_layout:
                break;
            case R.id.img_ad:
//                Intent intent = new Intent(MainActivity.this, TestActivity.class);
//                startActivity(intent);
//                overridePendingTransition(Animation.abc_slide_in_top, Animation.abc_slide_in_top);
                break;
            case R.id.title_name_right:
                ColorDialog colorDialog = new ColorDialog(this);
                colorDialog.setContentText(getString(R.string.callPhone));
                colorDialog.setTitle(getString(R.string.reminder));
                colorDialog.setPositiveListener(getString(R.string.confirm), new ColorDialog.OnPositiveListener() {
                    @Override
                    public void onClick(ColorDialog colorDialog) {
                        colorDialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:18038098825"));
                        checkPermission(Manifest.permission.CALL_PHONE, 1, 1);
                        startActivity(intent);
                    }
                });
                colorDialog.setNegativeListener(getString(R.string.cancle), new ColorDialog.OnNegativeListener() {
                    @Override
                    public void onClick(ColorDialog colorDialog) {
                        colorDialog.dismiss();
                    }
                });
                colorDialog.show();
                break;
            case R.id.tv_more:
                Toast.makeText(MainActivity.this, "点击了装饰设计的更多", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_more:
                Toast.makeText(MainActivity.this, "点击了装饰设计的更多", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_more_blue:
                Toast.makeText(MainActivity.this, "点击了蓝钻工艺的更多", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_more_blue:
                Toast.makeText(MainActivity.this, "点击了蓝钻工艺的更多", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_more_info:
                Toast.makeText(MainActivity.this, "点击了资讯中心的更多", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_more_info:
                Toast.makeText(MainActivity.this, "点击了资讯中心的更多", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void showDialog() {
        new PromptDialog(this)
                .setDialogType(PromptDialog.DIALOG_TYPE_INFO)
                .setAnimationEnable(true)
                .setTitleText(getString(R.string.info))
                .setContentText(getString(R.string.getpermission))
                .setPositiveListener(getString(R.string.ok), new PromptDialog.OnPositiveListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                        getPermission();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("isFirst", ++isFirst);
                        editor.commit();
                    }
                }).show();
    }

    public void getPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            XPermissionUtils.requestPermissions(this, RequestCode.EXTERNAL, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_PHONE_STATE}, new XPermissionUtils.OnPermissionListener() {
                @Override
                public void onPermissionGranted() {
                }

                @Override
                public void onPermissionDenied() {
                }

            });
        }

    }

    public void getBanner() {
        OkGo.<String>post(Constant.BaseUrl + "getBanner")
                .tag(this)
                .retryCount(3)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject js = new JSONObject(response.body());
                            String message = response.message();
                            String status = js.getString("status");
                            if (message.equals("OK") && status.equals("1")) {
                                JSONArray jsa = js.getJSONArray("data");
                                int legth = jsa.length();
                                if (legth > 0) {
                                    for (int i = 0; i < legth; i++) {
                                        String url = jsa.getJSONObject(i).getString("url");
                                        String title = jsa.getJSONObject(i).getString("title");
                                        String href = jsa.getJSONObject(i).getString("href");
                                        images.add(url);
                                        titles.add(title);
                                        hrefs.add(href);
                                    }
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        setBanner();
                        bannerOnclick();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    public void getDesigndecorate() {
        OkGo.<String>post(Constant.BaseUrl + "getDesigndecorate")
                .tag(this)
                .retryCount(3)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject js = new JSONObject(response.body());
                            String message = response.message();
                            String status = js.getString("status");
                            if (message.equals("OK") && status.equals("1")) {
                                JSONArray jsa = js.getJSONArray("data");
                                int legth = jsa.length();
                                if (legth > 0) {
                                    for (int i = 0; i < legth; i++) {
                                        String url = jsa.getJSONObject(i).getString("url");
                                        String title = jsa.getJSONObject(i).getString("title");
                                        String href = jsa.getJSONObject(i).getString("img");
                                        mBeanDecorateDesign = new BeanDecorateDesign(href, title, url);
                                        data.add(mBeanDecorateDesign);
                                    }
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Constant.mContext = MainActivity.this;
                        mAdapterDecorateDesign = new Adapter_decorate_design(R.layout.item_design_decorate, data);
                        mRecyclerDesign.setAdapter(mAdapterDecorateDesign);
                        mAdapterDecorateDesign.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                String url1 = data.get(position).getUrl();
                                Intent intent = new Intent(MainActivity.this, DetailsWebViewActivity.class);
                                intent.putExtra("url", url1);
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    public void getBlueProcess() {
        OkGo.<String>post(Constant.BaseUrl + "getBlueProcess")
                .tag(this)
                .retryCount(3)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject js = new JSONObject(response.body());
                            String message = response.message();
                            String status = js.getString("status");
                            if (message.equals("OK") && status.equals("1")) {
                                JSONArray jsa = js.getJSONArray("data");
                                int legth = jsa.length();
                                if (legth > 0) {
                                    for (int i = 0; i < legth; i++) {
                                        String url = jsa.getJSONObject(i).getString("url");
                                        String title = jsa.getJSONObject(i).getString("title");
                                        String href = jsa.getJSONObject(i).getString("img");
                                        mBeanBlueProcess = new BeanBlueProcess(href, title, url);
                                        data1.add(mBeanBlueProcess);
                                    }
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Constant.mContext = MainActivity.this;
                        mAdapterBlueProcess = new Adapter_blue_process(R.layout.item_blue_process, data1);
                        mRecyclerBlue.setAdapter(mAdapterBlueProcess);
                        mAdapterBlueProcess.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                String url1 = data1.get(position).getUrl();
                                Intent intent = new Intent(MainActivity.this, DetailsWebViewActivity.class);
                                intent.putExtra("url", url1);
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    public void getInfoCenter() {
        OkGo.<String>post(Constant.BaseUrl + "getInfoCenter")
                .tag(this)
                .retryCount(3)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject js = new JSONObject(response.body());
                            String message = response.message();
                            String status = js.getString("status");
                            if (message.equals("OK") && status.equals("1")) {
                                JSONArray jsa = js.getJSONArray("data");
                                int legth = jsa.length();
                                if (legth > 0) {
                                    for (int i = 0; i < legth; i++) {
                                        String url = jsa.getJSONObject(i).getString("url");
                                        String title = jsa.getJSONObject(i).getString("title");
                                        String href = jsa.getJSONObject(i).getString("img");
                                        mBeanInfoCenter = new BeanInfoCenter(href, title, url);
                                        data2.add(mBeanInfoCenter);
                                    }
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Constant.mContext = MainActivity.this;
                        mAdapterInfoCenter = new Adapter_info_center(R.layout.item_info_center, data2);
                        mRecyclerInfo.setAdapter(mAdapterInfoCenter);
                        mAdapterInfoCenter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                String url1 = data2.get(position).getUrl();
                                Intent intent = new Intent(MainActivity.this, DetailsWebViewActivity.class);
                                intent.putExtra("url", url1);
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    /**
     * 监听状态, 重启app
     *
     * @author lance
     */
    private class PhoneCallListener extends PhoneStateListener {
        private final String LOG_TAG = "PhoneCallListener";
        private boolean isPhoneCalling = false;

        public void onCallStateChanged(int state, String incomingNumber) {
            if (TelephonyManager.CALL_STATE_RINGING == state) {
                Log.i(LOG_TAG, "正在呼叫: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                Log.i(LOG_TAG, "OFFHOOK");
                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                Log.i(LOG_TAG, "Idle");

                if (isPhoneCalling) {
                    Log.i(LOG_TAG, "restart app");
                    Intent intent = getBaseContext()
                            .getPackageManager()
                            .getLaunchIntentForPackage(getBaseContext().getPackageName())
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    isPhoneCalling = false;
                }
            }
        }

    }
}
