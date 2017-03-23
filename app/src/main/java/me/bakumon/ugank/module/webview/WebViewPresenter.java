package me.bakumon.ugank.module.webview;

import org.litepal.crud.DataSupport;

import java.util.List;

import me.bakumon.ugank.ThemeManage;
import me.bakumon.ugank.entity.Favorite;

/**
 * WebViewPresenter
 * Created by bakumon on 16-12-10.
 */

public class WebViewPresenter implements WebViewContract.Presenter {

    private WebViewContract.View mWebViewView;

    private String mGankUrl;
    private boolean mIsFavorite;
    private Favorite mFavoriteData;

    public WebViewPresenter(WebViewContract.View webViewView) {
        mWebViewView = webViewView;
    }

    @Override
    public void subscribe() {
        mWebViewView.setToolbarBackgroundColor(ThemeManage.INSTANCE.getColorPrimary());
        mWebViewView.setGankTitle(mWebViewView.getGankTitle());
        // 设置 FabButton 的背景色
        mWebViewView.setFabButtonColor(ThemeManage.INSTANCE.getColorPrimary());
        mFavoriteData = mWebViewView.getFavoriteData();
        findHasFavoriteGank();
        loadDate();
    }

    private void loadDate() {
        mGankUrl = mWebViewView.getLoadUrl();
        mWebViewView.loadGankURL(mGankUrl);
    }

    @Override
    public void unsubscribe() {
    }

    @Override
    public String getGankUrl() {
        return mGankUrl;
    }

    @Override
    public void favoriteGank() {
        if (mIsFavorite) { // 已经收藏
            unFavorite();
        } else { // 未收藏
            favorite();
        }
    }

    private void unFavorite() {
        int cows = DataSupport.deleteAll(Favorite.class, "gankID = ?", mFavoriteData.getGankID());
        // 不调用这句保存 在保存会失败，并且返回的是true
        // https://github.com/LitePalFramework/LitePal/issues/77
        mFavoriteData.clearSavedState();
        mIsFavorite = cows < 1;
        mWebViewView.setFavoriteState(mIsFavorite);
        if (mIsFavorite) {
            mWebViewView.showTip("取消收藏失败,请重试");
        }
    }

    private void favorite() {
        mFavoriteData.setCreatetime(System.currentTimeMillis());
        mIsFavorite = mFavoriteData.save();
        mWebViewView.setFavoriteState(mIsFavorite);
        if (!mIsFavorite) {
            mWebViewView.showTip("收藏失败,请重试");
        }
    }

    private void findHasFavoriteGank() {
        if (mFavoriteData == null) {
            // 隐藏收藏 fab
            mWebViewView.hideFavoriteFab();
            return;
        }
        List<Favorite> favorites = DataSupport.where("gankID = ?", mFavoriteData.getGankID()).find(Favorite.class);
        mIsFavorite = favorites.size() > 0;
        mWebViewView.setFavoriteState(mIsFavorite);
    }
}
