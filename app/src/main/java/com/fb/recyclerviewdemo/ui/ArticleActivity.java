package com.fb.recyclerviewdemo.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.fb.recyclerviewdemo.BaseActivity;
import com.fb.recyclerviewdemo.R;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebConfig;
import com.just.agentweb.DefaultWebClient;

public class ArticleActivity extends BaseActivity {
    private static final String TAG = "ArticleActivity";

    private ActionBar actionBar;

    private RelativeLayout layout_article;

    private AgentWeb mAgentWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_article);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowCustomEnabled(true);
        }
    }

    @Override
    protected void initView() {
        layout_article = (RelativeLayout) findViewById(R.id.layout_article);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null){
            String link = intent.getStringExtra("link");
            String projectLink = intent.getStringExtra("projectLink");
            Log.e(TAG, "link: " + link );
            Log.e(TAG, "projectLink: " + projectLink );

            mAgentWeb = AgentWeb.with(this)
                    .setAgentWebParent(layout_article, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                    .useDefaultIndicator(-1, 3)
                    //.setAgentWebWebSettings(getSettings())
                    .setWebViewClient(mWebViewClient)
                    .setWebChromeClient(mWebChromeClient)
                    //.setPermissionInterceptor(mPermissionInterceptor)
                    .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                    //.setAgentWebUIController(new UIController(getActivity()))
                    .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                    //.useMiddlewareWebChrome(getMiddlewareWebChrome())
                    //.useMiddlewareWebClient(getMiddlewareWebClient())
                    .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)
                    .interceptUnkownUrl()
                    .createAgentWeb()
                    .ready()
                    .go(link);

            //String cookies = AgentWebConfig.getCookiesByUrl(link); //查看 Cookies
            //AgentWebConfig.syncCookie("http://www.jd.com","ID=XXXX"); //同步 Cookie
        }
    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
            Log.i("Info", "BaseWebActivity onPageStarted");
        }
    };
    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //do you work
//            Log.i("Info","onProgress:"+newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            setTitle(title);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if (!mAgentWeb.back()){
                    this.finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAgentWeb.getWebLifeCycle().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAgentWeb.getWebLifeCycle().onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
