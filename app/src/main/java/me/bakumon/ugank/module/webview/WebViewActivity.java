package me.bakumon.ugank.module.webview;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
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
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import me.bakumon.ugank.R;
import me.bakumon.ugank.base.SwipeBackBaseActivity;
import me.bakumon.ugank.entity.Favorite;
import me.bakumon.ugank.module.favorite.FavoriteActivity;
import me.bakumon.ugank.utills.AndroidUtil;
import me.bakumon.ugank.utills.DisplayUtils;
import me.bakumon.ugank.utills.MDTintUtil;
import me.bakumon.ugank.widget.ObservableWebView;

public class WebViewActivity extends SwipeBackBaseActivity implements WebViewContract.View {

    public static final String GANK_URL = "me.bakumon.gank.module.webview.WebViewActivity.gank_url";
    public static final String GANK_TITLE = "me.bakumon.gank.module.webview.WebViewActivity.gank_title";
    public static final String FAVORITE_DATA = "me.bakumon.gank.module.webview.WebViewActivity.favorite_data";
    public static final String FAVORITE_POSITION = "me.bakumon.gank.module.webview.WebViewActivity.favorite_position";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.web_view)
    ObservableWebView mWebView;
    @BindView(R.id.progressbar_webview)
    ProgressBar mProgressbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.fab_web_favorite)
    FloatingActionButton mFloatingActionButton;

    private WebViewContract.Presenter mWebViewPresenter = new WebViewPresenter(this);
    private boolean isForResult; // 是否回传结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mAppbar.setPadding(
                    mAppbar.getPaddingLeft(),
                    mAppbar.getPaddingTop() + DisplayUtils.getStatusBarHeight(this),
                    mAppbar.getPaddingRight(),
                    mAppbar.getPaddingBottom());
        }
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
        initWebView();
        mWebViewPresenter.subscribe();
    }

    public void initWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);

        mWebView.setWebChromeClient(new MyWebChrome());
        mWebView.setWebViewClient(new MyWebClient());
        mWebView.setOnScrollChangedCallback(new ObservableWebView.OnScrollChangedCallback() {
            @Override
            public void onScroll(int dx, int dy) {
                if (dy > 0) {
                    mFloatingActionButton.hide();
                } else {
                    mFloatingActionButton.show();
                }
            }
        });
    }

    @Override
    public void setToolbarBackgroundColor(int color) {
        mAppbar.setBackgroundColor(color);
    }

    @Override
    public String getLoadUrl() {
        return getIntent().getStringExtra(WebViewActivity.GANK_URL);
    }

    @Override
    public String getGankTitle() {
        return getIntent().getStringExtra(WebViewActivity.GANK_TITLE);
    }

    @Override
    public Favorite getFavoriteData() {
        return (Favorite) getIntent().getSerializableExtra(WebViewActivity.FAVORITE_DATA);
    }

    @Override
    public void setFavoriteState(boolean isFavorite) {
        if (isFavorite) {
            mFloatingActionButton.setImageResource(R.drawable.ic_favorite);
        } else {
            mFloatingActionButton.setImageResource(R.drawable.ic_unfavorite);
        }
        isForResult = !isFavorite;
    }

    @Override
    public void finish() {
        if (isForResult) {
            Intent intent = new Intent();
            intent.putExtra(FavoriteActivity.FAVORITE_POSITION, getIntent().getIntExtra(WebViewActivity.FAVORITE_POSITION, -1));
            setResult(RESULT_OK, intent);
        }
        super.finish();
    }

    @Override
    public void hideFavoriteFab() {
        mFloatingActionButton.setVisibility(View.GONE);
        mWebView.setOnScrollChangedCallback(null);
    }

    @Override
    public void showTip(String tip) {
        Toasty.error(this, tip).show();
    }

    @Override
    public void setFabButtonColor(int color) {
        MDTintUtil.setTint(mFloatingActionButton, color);
    }

    private class MyWebChrome extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mProgressbar.setVisibility(View.VISIBLE);
            mProgressbar.setProgress(newProgress);
        }
    }

    private class MyWebClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            mProgressbar.setVisibility(View.GONE);
        }
    }

    @Override
    public void setGankTitle(String title) {
        mTvTitle.setText(title);
    }

    @Override
    public void loadGankURL(String url) {
        mWebView.loadUrl(url);
    }

    @OnClick(R.id.fab_web_favorite)
    public void favorite() {
        mWebViewPresenter.favoriteGank();
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
                AndroidUtil.share(this, mWebViewPresenter.getGankUrl());
                break;
            case R.id.menu_copy_link:
                if (AndroidUtil.copyText(this, mWebViewPresenter.getGankUrl())) {
                    Toasty.success(this, "链接复制成功").show();
                }
                break;
            case R.id.menu_open_with:
                AndroidUtil.openWithBrowser(this, mWebViewPresenter.getGankUrl());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
