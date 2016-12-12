package me.bakumon.gank.module.webview;

import android.app.Activity;

import me.bakumon.gank.base.BasePresenter;
import me.bakumon.gank.base.BaseView;

/**
 * WebViewContract
 * Created by bakumon on 16-12-10.
 */

public interface WebViewContract {

    interface View extends BaseView {

        Activity getWebViewContext();

        void setGankTitle(String title);

        void loadGankURL(String url);

        void initWebView();

    }

    interface Presenter extends BasePresenter {
        String getGankUrl();
    }
}
