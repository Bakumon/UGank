package me.bakumon.ugank.module.favorite;

import java.util.List;

import me.bakumon.ugank.base.BasePresenter;
import me.bakumon.ugank.base.BaseView;
import me.bakumon.ugank.entity.Favorite;

/**
 * FavoriteContract
 * Created by bakumon on 17-3-22.
 */

public class FavoriteContract {
    interface View extends BaseView {

        void setToolbarBackgroundColor(int color);

        void addFavoriteItems(List<Favorite> favorites);

        void setFavoriteItems(List<Favorite> favorites);

        void setLoading();

        void setEmpty();

        void setLoadMoreIsLastPage();
    }

    interface Presenter extends BasePresenter {

        void getFavoriteItems(boolean isRefresh);
    }
}
