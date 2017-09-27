package wqj.com.oakdesigner.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wqj.com.oakdesigner.R;

import static android.view.KeyEvent.KEYCODE_BACK;

public class DetailsWebViewActivity extends AppCompatActivity {

    @BindView(R.id.title_back_layout)
    LinearLayout mTitleBackLayout;
    @BindView(R.id.title_name_tv)
    TextView mTitleNameTv;
    @BindView(R.id.title_name_right)
    TextView mTitleNameRight;
    @BindView(R.id.title_layout_right)
    LinearLayout mTitleLayoutRight;
    @BindView(R.id.public_function_layout)
    LinearLayout mPublicFunctionLayout;
    @BindView(R.id.webView_details)
    WebView mWebViewDetails;
    @BindView(R.id.layout_root)
    LinearLayout mLayoutRoot;
    @BindView(R.id.layout_back)
    LinearLayout mLayoutBack;
    private String url = "";
    private String titlename = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_web_view);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        Log.w("onCreate: ", url);
        setWebView();
    }

    public void setWebView() {
        WebSettings webSettings = mWebViewDetails.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
//        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        mWebViewDetails.loadUrl(url);
        mWebViewDetails.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebViewDetails.setWebChromeClient(new WebChromeClient() {
            //获取网站标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                System.out.println("标题在这里");
                titlename = title;
            }


            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    String progress = newProgress + "%";
                    mTitleNameTv.setText(progress);
                } else if (newProgress == 100) {
                    String progress = newProgress + "%";
                    if (progress.equals("100%"))
                        mTitleNameTv.setText(titlename);
                }
            }


        });
    }

    @OnClick(R.id.title_back_layout)
    public void onViewClicked() {
        mLayoutRoot.removeView(mWebViewDetails);
        mWebViewDetails.destroy();
        this.finish();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && mWebViewDetails.canGoBack()) {
            mWebViewDetails.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
