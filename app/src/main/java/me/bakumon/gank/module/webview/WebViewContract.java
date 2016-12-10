package me.bakumon.gank.module.webview;

import me.bakumon.gank.base.BasePresenter;
import me.bakumon.gank.base.BaseView;

/**
 * WebViewContract
 * Created by bakumon on 16-12-10.
 */

public interface WebViewContract {
    interface View extends BaseView {

        void showLoadFail(String failMessage);

    }

    interface Presenter extends BasePresenter {

    }
}
