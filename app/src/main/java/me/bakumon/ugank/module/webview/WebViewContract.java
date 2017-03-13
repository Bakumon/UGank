package me.bakumon.ugank.module.webview;

import me.bakumon.ugank.base.BasePresenter;
import me.bakumon.ugank.base.BaseView;

/**
 * WebViewContract
 * Created by bakumon on 16-12-10.
 */

public interface WebViewContract {

    interface View extends BaseView {

        void setGankTitle(String title);

        void loadGankURL(String url);

        void setToolbarBackgroundColor(int color);

        String getLoadUrl();

        String getGankTitle();

    }

    interface Presenter extends BasePresenter {
        String getGankUrl();
    }
}
