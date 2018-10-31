package com.zejor.mvp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zejor.R;
import com.zejor.base.BaseActivity;
import com.zejor.component.ApplicationComponent;
import com.zejor.utils.SharedPerferenceUtil;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 用来加载网页的界面
 */
public class WebViewActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.webView)
    WebView webView;
    private String loadUrl;


    @Override
    public void onRetry() {

    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_web_view;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {

    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData() {
        tvTitle.setText(R.string.app_name);

        webView.setWebChromeClient(new MyWebViewChromClient());
        webView.setWebViewClient(new MyWebviewclient());

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true); //将图片调整到适合WebView的大小
        settings.setSupportZoom(true); //支持缩放
        settings.setBuiltInZoomControls(true); //支持手势缩放
        settings.setDisplayZoomControls(false); //是否显示缩放按钮
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true); //自适应屏幕
        settings.setDomStorageEnabled(true);//解决一些网页加载不全的这种情况
        settings.setBlockNetworkImage(false);//解决图片显示有问题的方法
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        loadUrl = getIntent().getStringExtra("url");
        webView.setDownloadListener(new MyDownloadStart());

        if (loadUrl.contains("platformapi/startApp") || loadUrl.contains("http://api.unpay.com")) {
            try {
                Intent intent;
                intent = Intent.parseUri(loadUrl,
                        Intent.URI_INTENT_SCHEME);
                // intent.addCategory("android.intent.category.BROWSABLE");
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setComponent(null);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                Toast.makeText(this, "未安装微信", Toast.LENGTH_SHORT).show();
                finish();
            }
        }else  if(loadUrl.contains("alipayqr://platformapi") || loadUrl.contains("alipay://platformapi")) {
            Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(loadUrl));
            startActivity(intent);
            finish();
        }
        else {
            webView.loadUrl(loadUrl);
        }
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }

    /**
     * 设置页面的二次跳转中需要解决的一些问题
     */
    class MyWebviewclient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (progressBar != null) {
                progressBar.setVisibility(View.GONE);
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            String finalUrl = (String) SharedPerferenceUtil.getData(WebViewActivity.this, "zhiMaUrl", "");
            if ((!TextUtils.isEmpty(finalUrl)) && url.contains(finalUrl)) {
                WebViewActivity.this.finish();
                return false;
            }
            if (loadUrl.contains("platformapi/startApp") || loadUrl.contains("http://api.unpay.com")) {
                try {
                    Intent intent;
                    intent = Intent.parseUri(loadUrl,
                            Intent.URI_INTENT_SCHEME);
                    // intent.addCategory("android.intent.category.BROWSABLE");
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setComponent(null);
                    startActivity(intent);
                    finish();
                    return false;
                } catch (Exception e) {
                    Toast.makeText(WebViewActivity.this, "未安装微信", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            if(url.contains("alipayqr://platformapi") || url.contains("alipay://platformapi")) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
                    startActivity(intent);
                    finish();
                    return false;
                } catch (Exception e) {
                    Toast.makeText(WebViewActivity.this, "未安装支付宝", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

            if (url.endsWith(".apk")) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                WebViewActivity.this.startActivity(intent);
            }
            webView.loadUrl(url);
            return false;
        }
    }

    /**
     * 获取网页的加载进度
     */
    class MyWebViewChromClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (progressBar != null) {
                if (100 != newProgress) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
                progressBar.setProgress(newProgress);
            }
        }
    }


    @Override
    // 设置回退
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * webview下载文件
     */
    class MyDownloadStart implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent,
                                    String contentDisposition, String mimetype, long contentLength) {
            //调用系统浏览器下载
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

    }

}
