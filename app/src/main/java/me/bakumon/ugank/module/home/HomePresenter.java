package me.bakumon.ugank.module.home;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;

import me.bakumon.ugank.App;
import me.bakumon.ugank.ConfigManage;
import me.bakumon.ugank.R;
import me.bakumon.ugank.ThemeManage;
import me.bakumon.ugank.entity.CategoryResult;
import me.bakumon.ugank.network.NetWork;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * HomePresenter
 * Created by bakumon on 2016/12/6 11:07.
 */
public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mHomeView;

    @NonNull
    private CompositeSubscription mSubscriptions;

    public HomePresenter(HomeContract.View homeView) {
        mHomeView = homeView;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void subscribe() {
        getBanner(false);
        cacheRandomImg();
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }


    @Override
    public void getRandomBanner() {
        getBanner(true);
    }

    @Override
    public void setThemeColor(@Nullable Palette palette) {
        if (palette != null) {
            int colorPrimary = App.getInstance().getResources().getColor(R.color.colorPrimary);
            // 把从调色板上获取的主题色保存在内存中
            ThemeManage.INSTANCE.setColorPrimary(palette.getDarkVibrantColor(colorPrimary));
            // 设置 AppBarLayout 的背景色
            mHomeView.setAppBarLayoutColor(ThemeManage.INSTANCE.getColorPrimary());
            // 设置 FabButton 的背景色
            mHomeView.setFabButtonColor(ThemeManage.INSTANCE.getColorPrimary());
            // 停止 FabButton 加载中动画
            mHomeView.enableFabButton();
            mHomeView.stopBannerLoadingAnim();
        }
    }

    private void cacheRandomImg() {
        if (!ConfigManage.INSTANCE.isShowLauncherImg()) { // 不显示欢迎妹子，也就不需要预加载了
            return;
        }
        if (ConfigManage.INSTANCE.isProbabilityShowLauncherImg()) { // 概率出现欢迎妹子
            if (Math.random() < 0.75) {
                ConfigManage.INSTANCE.setBannerURL("");
                return;
            }
        }
        Observable<CategoryResult> observable;
        observable = NetWork.getGankApi().getRandomBeauties(1);
        Subscription subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(CategoryResult meiziResult) {
                        if (meiziResult != null && meiziResult.results != null && meiziResult.results.size() > 0 && meiziResult.results.get(0).url != null) {
                            mHomeView.cacheImg(meiziResult.results.get(0).url);
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void saveCacheImgUrl(String url) {
        ConfigManage.INSTANCE.setBannerURL(url);
    }

    /**
     * 或单张 Banner
     *
     * @param isRandom true：随机  false：获取最新
     */
    @Override
    public void getBanner(final boolean isRandom) {
        mHomeView.startBannerLoadingAnim();
        mHomeView.disEnableFabButton();
        Observable<CategoryResult> observable;
        if (isRandom) {
            observable = NetWork.getGankApi().getRandomBeauties(1);
        } else {
            observable = NetWork.getGankApi().getCategoryDate("福利", 1, 1);
        }
        Subscription subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mHomeView.showBannerFail("Banner 图加载失败。");
                        mHomeView.enableFabButton();
                        mHomeView.stopBannerLoadingAnim();
                    }

                    @Override
                    public void onNext(CategoryResult meiziResult) {
                        if (meiziResult != null && meiziResult.results != null && meiziResult.results.size() > 0 && meiziResult.results.get(0).url != null) {
                            mHomeView.setBanner(meiziResult.results.get(0).url);
                        } else {
                            mHomeView.showBannerFail("Banner 图加载失败。");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

}
