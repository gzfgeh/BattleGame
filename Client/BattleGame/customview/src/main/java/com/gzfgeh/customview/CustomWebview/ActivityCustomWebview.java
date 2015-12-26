package com.gzfgeh.customview.CustomWebview;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.gzfgeh.customview.CustomSwipeBack.SwipeBackActivity;
import com.gzfgeh.customview.R;

/**
 * Created by guzehnfu on 2015/12/17.
 */
public class ActivityCustomWebView extends SwipeBackActivity {
    private WebView webView;
    private WebSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = (WebView) findViewById(R.id.web_view);
        settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());
        enableDatabaseCache();
        enableHTML5AppCache();
        webView.loadUrl("http://www.baidu.com");
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_web_view;
    }

    //改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode== KeyEvent.KEYCODE_BACK) {
            if(webView.canGoBack()) {
                webView.goBack();//返回上一页面
                return true;
            } else {
                System.exit(0);//退出程序
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void enableDatabaseCache(){
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDatabasePath("/data/data/"+ getPackageName() +"/db");
    }


    private void enableHTML5AppCache() {
        webView.getSettings().setDomStorageEnabled(true);
        // Set cache size to 8 mb by default. should be more than enough
        webView.getSettings().setAppCacheMaxSize(1024*1024*8);

        // This next one is crazy. It's the DEFAULT location for your app's cache
        // But it didn't work for me without this line
        webView.getSettings().setAppCachePath("/data/data/"+ getPackageName() +"/cache");
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);

        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReachedMaxAppCacheSize(long requiredStorage, long quota, WebStorage.QuotaUpdater quotaUpdater) {
                quotaUpdater.updateQuota(requiredStorage * 2);
            }

            @Override
            public void onExceededDatabaseQuota(String url, String databaseIdentifier, long quota, long estimatedDatabaseSize, long totalQuota, WebStorage.QuotaUpdater quotaUpdater) {
                quotaUpdater.updateQuota(estimatedDatabaseSize * 2);
            }
        });
    }

    final class MyWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d("WebView","onPageStarted");
            super.onPageStarted(view, url, favicon);
        }
        public void onPageFinished(WebView view, String url) {
            Log.d("WebView","onPageFinished ");
            view.loadUrl("javascript:window.local_obj.showSource('<head>'+" +
                    "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
            super.onPageFinished(view, url);
        }
    }

    final class InJavaScriptLocalObj {

        @JavascriptInterface
        public void showSource(String html) {
            Log.d("HTML+", html);
            Toast.makeText(ActivityCustomWebView.this, html, Toast.LENGTH_SHORT).show();
        }
    }
}
