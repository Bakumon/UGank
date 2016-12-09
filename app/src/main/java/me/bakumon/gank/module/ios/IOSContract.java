package me.bakumon.gank.module.ios;

import me.bakumon.gank.base.BasePresenter;
import me.bakumon.gank.base.BaseView;
import me.bakumon.gank.entity.IOSResult;

/**
 * IOSContract
 * Created by bakumon on 16-12-9.
 */

public interface IOSContract {

    interface View extends BaseView {

        void setIOSItems(IOSResult iosResult);

        void addIOSItems(IOSResult iosResult);

        void getIOSItemsFail(String failMessage);
    }

    interface Presenter extends BasePresenter {

        void getIOSItems(int number, int page, boolean isRefresh);
    }
}
