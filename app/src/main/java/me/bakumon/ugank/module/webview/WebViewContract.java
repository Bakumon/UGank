package me.bakumon.ugank.module.webview;

import me.bakumon.ugank.base.BasePresenter;
import me.bakumon.ugank.base.BaseView;
import me.bakumon.ugank.entity.Favorite;

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

        void setFabButtonColor(int color);

        Favorite getFavoriteData();

        void setFavoriteState(boolean isFavorite);

        void hideFavoriteFab();

        void showTip(String tip);

    }

    interface Presenter extends BasePresenter {

        String getGankUrl();

        void favoriteGank();
    }
}
