package me.bakumon.ugank.module.webview;

import me.bakumon.ugank.ThemeManage;

/**
 * WebViewPresenter
 * Created by bakumon on 16-12-10.
 */

public class WebViewPresenter implements WebViewContract.Presenter {

    private WebViewContract.View mWebViewView;

    private String mGankUrl;

    public WebViewPresenter(WebViewContract.View webViewView) {
        mWebViewView = webViewView;
    }

    @Override
    public void subscribe() {
        mWebViewView.setToolbarBackgroundColor(ThemeManage.INSTANCE.getColorPrimary());
        mWebViewView.setGankTitle(mWebViewView.getGankTitle());
        // 设置 FabButton 的背景色
        mWebViewView.setFabButtonColor(ThemeManage.INSTANCE.getColorPrimary());
        loadDate();
    }

    private void loadDate() {
        mGankUrl = mWebViewView.getLoadUrl();
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
