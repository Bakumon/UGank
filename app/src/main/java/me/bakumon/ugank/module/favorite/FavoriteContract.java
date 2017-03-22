package me.bakumon.ugank.module.favorite;

import me.bakumon.ugank.base.BasePresenter;
import me.bakumon.ugank.base.BaseView;

/**
 * FavoriteContract
 * Created by bakumon on 17-3-22.
 */

public class FavoriteContract {
    interface View extends BaseView {

        void setToolbarBackgroundColor(int color);

        void showSwipLoading();

    }

    interface Presenter extends BasePresenter {

        void getFavoriteItems(boolean isRefresh);
    }
}
