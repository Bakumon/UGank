package me.bakumon.gank.module.webview;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.bakumon.gank.R;
import me.bakumon.gank.utills.AndroidUtil;

public class WebViewActivity extends AppCompatActivity implements WebViewContract.View {

    public static final String GANK_URL = "me.bakumon.gank.module.webview.WebViewActivity.gank_url";
    public static final String GANK_TITLE = "me.bakumon.gank.module.webview.WebViewActivity.gank_title";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.progressbar_webview)
    ProgressBar mProgressbar;

    private WebViewContract.Presenter mHomePresenter = new WebViewPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mHomePresenter.subscribe();
    }

    @Override
    public void initWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);

        mWebView.setWebChromeClient(new MyWebChrome());
        mWebView.setWebViewClient(new MyWebClient());
    }

    class MyWebChrome extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mProgressbar.setVisibility(View.VISIBLE);
            mProgressbar.setProgress(newProgress);
        }
    }

    class MyWebClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            mProgressbar.setVisibility(View.GONE);
        }
    }

    @Override
    public Activity getWebViewContext() {
        return this;
    }

    @Override
    public void setGankTitle(String title) {
        mTvTitle.setText(title);
    }

    @Override
    public void loadGankURL(String url) {
        mWebView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_webview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:
                AndroidUtil.share(this, mHomePresenter.getGankUrl());
                break;
            case R.id.menu_copy_link:
                AndroidUtil.copyText(this, mHomePresenter.getGankUrl());
                break;
            case R.id.menu_open_with:
                AndroidUtil.openWithBrowser(this, mHomePresenter.getGankUrl());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
