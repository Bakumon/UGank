package me.bakumon.gank.module.webview;

import android.app.Activity;
import android.content.Intent;

import me.bakumon.gank.App;

/**
 * WebViewPresenter
 * Created by bakumon on 16-12-10.
 */

public class WebViewPresenter implements WebViewContract.Presenter {

    private WebViewContract.View mWebViewView;

    private Activity mContext;

    private String mGankUrl;

    public WebViewPresenter(WebViewContract.View webViewView) {
        mWebViewView = webViewView;
    }

    @Override
    public void subscribe() {
        mContext = mWebViewView.getWebViewContext();
        mWebViewView.setToolbarBackgroundColor(App.getInstance().getColorPrimary());
        loadDate();
    }

    private void loadDate() {
        Intent intent = mContext.getIntent();
        mWebViewView.setGankTitle(intent.getStringExtra(WebViewActivity.GANK_TITLE));
        mWebViewView.initWebView();
        mGankUrl = intent.getStringExtra(WebViewActivity.GANK_URL);
        mWebViewView.loadGankURL(mGankUrl);
    }

    @Override
    public void unsubscribe() {
    }

    @Override
    public String getGankUrl() {
        return mGankUrl;
    }
}
