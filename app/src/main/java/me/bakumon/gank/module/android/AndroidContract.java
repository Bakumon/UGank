package me.bakumon.gank.module.android;

import me.bakumon.gank.base.BasePresenter;
import me.bakumon.gank.base.BaseView;
import me.bakumon.gank.entity.AndroidResult;

/**
 * Created by mafei on 2016/12/8 16:37.
 *
 * @author mafei
 * @version 1.0.0
 * @class AndroidContract
 * @describe
 */
public interface AndroidContract {

    interface View extends BaseView {

        void setAndroidItems(AndroidResult androidResult);
    }

    interface Presenter extends BasePresenter {

    }
}
