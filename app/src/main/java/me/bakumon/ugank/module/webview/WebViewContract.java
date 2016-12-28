package me.bakumon.ugank.module.webview;

import android.app.Activity;

import me.bakumon.ugank.base.BasePresenter;
import me.bakumon.ugank.base.BaseView;

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

        void setToolbarBackgroundColor(int color);

        void setViewColorAccent(int color);

    }

    interface Presenter extends BasePresenter {
        String getGankUrl();
    }
}
