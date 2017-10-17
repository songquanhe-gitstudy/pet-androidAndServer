package com.song.petLeague.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.song.petLeague.R;

import static android.content.ContentValues.TAG;

/**
 * Created by song on 2017/3/27.
 */

public class ShopDetailActivity extends Activity {

    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_webview);
        initView();
        initData();
        initListener();

    }

    private void initView() {
        webView = (WebView) findViewById(R.id.wv_ShoppingView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
    }

    private void initData() {
        String url = getIntent().getStringExtra("url");
        Log.e(TAG, "url: " + url);
        webView.loadUrl(url);
    }

    private void initListener() {
        webView.setWebViewClient(new WebViewClient(){

            @SuppressLint("SetJavaScriptEnabled") @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if( url.startsWith("http:") || url.startsWith("https:") ) {
                    return false;
                }
                if(url.startsWith("orpheus")) {
                    return true;
                }

//	             Otherwise allow the OS to handle things like tel, mailto, etc.
                //跳转到App应用
//	            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//	            startActivity( intent );
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }

}
