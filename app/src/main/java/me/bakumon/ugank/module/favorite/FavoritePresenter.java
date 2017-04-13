package me.bakumon.ugank.module.favorite;

import org.litepal.crud.DataSupport;

import java.util.List;

import me.bakumon.ugank.GlobalConfig;
import me.bakumon.ugank.ThemeManage;
import me.bakumon.ugank.entity.Favorite;

/**
 * FavoritePresenter
 * Created by bakumon on 17-3-22.
 */

public class FavoritePresenter implements FavoriteContract.Presenter {

    private FavoriteContract.View mView;

    private int mPage = 0;

    public FavoritePresenter(FavoriteContract.View view) {
        mView = view;
    }

    @Override
    public void subscribe() {
        mView.setToolbarBackgroundColor(ThemeManage.INSTANCE.getColorPrimary());
        getFavoriteItems(true);
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getFavoriteItems(boolean isRefresh) {
        if (isRefresh) {
            mPage = 0;
        } else {
            mPage += 1;
        }
        List<Favorite> favoriteList = DataSupport
                .limit(GlobalConfig.PAGE_SIZE_FAVORITE)
                .offset(GlobalConfig.PAGE_SIZE_FAVORITE * mPage)
                .order("createTime desc")
                .find(Favorite.class);
        if (isRefresh) {
            mView.setFavoriteItems(favoriteList);
            mView.setLoading();
            if (favoriteList == null || favoriteList.size() < 1) {
                mView.setEmpty();
                return;
            }
        } else {
            mView.addFavoriteItems(favoriteList);
        }
        boolean isLastPage = favoriteList.size() < GlobalConfig.PAGE_SIZE_FAVORITE;
        if (isLastPage) {
            mView.setLoadMoreIsLastPage();
        }
    }


}
