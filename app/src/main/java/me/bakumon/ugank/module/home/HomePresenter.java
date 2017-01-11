package me.bakumon.ugank.module.home;

import android.Manifest;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;

import com.tbruyelle.rxpermissions.RxPermissions;

import me.bakumon.ugank.App;
import me.bakumon.ugank.R;
import me.bakumon.ugank.ThemeManage;
import me.bakumon.ugank.entity.CategoryResult;
import me.bakumon.ugank.network.NetWork;
import me.bakumon.ugank.utills.ImageUtil;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * HomePresenter
 * Created by bakumon on 2016/12/6 11:07.
 */
public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mHomeView;
    private Activity mContext;

    @NonNull
    private CompositeSubscription mSubscriptions;

    HomePresenter(HomeContract.View homeView) {
        mHomeView = homeView;
        mContext = mHomeView.getBigimgContext();
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void subscribe() {
        getBanner(false);
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
        mContext = null;
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
                        mHomeView.showBannerFail("Banner 图加载失败，请重试。101", isRandom);
                        mHomeView.enableFabButton();
                        mHomeView.stopBannerLoadingAnim();
                    }

                    @Override
                    public void onNext(CategoryResult meiziResult) {
                        if (meiziResult != null && meiziResult.results != null && meiziResult.results.size() > 0 && meiziResult.results.get(0).url != null) {
                            mHomeView.setBanner(meiziResult.results.get(0).url);
                        } else {
                            mHomeView.showBannerFail("Banner 图加载失败，请重试。102", isRandom);
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void saveImg(final Drawable drawable) {
        if (drawable == null) {
            mHomeView.showMsgSaveFail();
            return;
        }
        mHomeView.showSavingMsgTip();
        RxPermissions rxPermissions = new RxPermissions(mContext);
        Subscription requestPermissionSubscription = rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            saveImageToGallery(ImageUtil.drawableToBitmap(drawable));
                        } else {
                            mHomeView.showPermissionsTip();
                        }
                    }
                });
        mSubscriptions.add(requestPermissionSubscription);
    }

    private void saveImageToGallery(final Bitmap bitmap) {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                boolean isSaveSuccess = ImageUtil.saveImageToGallery(mContext, bitmap);
                subscriber.onNext(isSaveSuccess);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean isSaveSuccess) {
                        if (isSaveSuccess) {
                            mHomeView.showMsgSaveSuccess();
                        } else {
                            mHomeView.showMsgSaveFail();
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

}
