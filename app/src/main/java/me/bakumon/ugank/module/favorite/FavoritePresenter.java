package me.bakumon.ugank.module.favorite;

import me.bakumon.ugank.ThemeManage;

/**
 * FavoritePresenter
 * Created by bakumon on 17-3-22.
 */

public class FavoritePresenter implements FavoriteContract.Presenter {

    private FavoriteContract.View mView;

    private int mPage;

    public FavoritePresenter(FavoriteContract.View view) {
        mView = view;
    }

    @Override
    public void subscribe() {
        mView.setToolbarBackgroundColor(ThemeManage.INSTANCE.getColorPrimary());
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getFavoriteItems(boolean isRefresh) {
        if (isRefresh) {
            mView.showSwipLoading();
            mPage = 1;
        } else {
            mPage += 1;
        }
        // TODO: 17-3-23 查询收藏
    }


}
