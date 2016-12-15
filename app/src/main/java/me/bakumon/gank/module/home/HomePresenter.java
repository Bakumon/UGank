package me.bakumon.gank.module.home;

import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;

import me.bakumon.gank.App;
import me.bakumon.gank.R;
import me.bakumon.gank.entity.CategoryResult;
import me.bakumon.gank.network.NetWork;
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

    HomePresenter(HomeContract.View homeView) {
        mHomeView = homeView;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void subscribe() {
        getBanner(false);
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
            int colorAccent = App.getInstance().getResources().getColor(R.color.colorAccent);
            // 把从调色板上获取的主题色保存在内存中
            App.colorPrimary = palette.getDarkVibrantColor(colorPrimary);
            App.colorAccent = palette.getLightVibrantColor(colorAccent);
            // 设置 AppBarLayout 的背景色
            mHomeView.setAppBarLayoutColor(App.colorPrimary);
            // 设置 FabButton 的背景色
            mHomeView.setFabButtonColor(createColorStateList(App.colorAccent));
            // 停止 FabButton 加载中动画
            mHomeView.enableFabButton();
            mHomeView.stopBannerLoadingAnim();
        }
    }

    /**
     * 用于创建 FabButton 的背景
     */
    private ColorStateList createColorStateList(int color) {
        int[] colors = new int[]{color, color, color, color, color, color};
        int[][] states = new int[6][];
        states[0] = new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled};
        states[1] = new int[]{android.R.attr.state_enabled, android.R.attr.state_focused};
        states[2] = new int[]{android.R.attr.state_enabled};
        states[3] = new int[]{android.R.attr.state_focused};
        states[4] = new int[]{android.R.attr.state_window_focused};
        states[5] = new int[]{};
        return new ColorStateList(states, colors);
    }

    /**
     * 或单张 Banner
     *
     * @param isRandom true：随机  false：获取最新
     */
    private void getBanner(boolean isRandom) {
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
                        mHomeView.showBannerFail("Banner 图加载失败，请重试。101");
                        mHomeView.enableFabButton();
                        mHomeView.stopBannerLoadingAnim();
                    }

                    @Override
                    public void onNext(CategoryResult meiziResult) {
                        if (meiziResult != null && meiziResult.results != null && meiziResult.results.size() > 0 && meiziResult.results.get(0).url != null) {
                            mHomeView.setBanner(meiziResult.results.get(0).url);
                        } else {
                            mHomeView.showBannerFail("Banner 图加载失败，请重试。102");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }
}
