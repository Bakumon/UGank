package me.bakumon.ugank.module.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.github.florent37.picassopalette.PicassoPalette;
import com.kekstudio.dachshundtablayout.DachshundTabLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import me.bakumon.ugank.GlobalConfig;
import me.bakumon.ugank.R;
import me.bakumon.ugank.base.adapter.CommonViewPagerAdapter;
import me.bakumon.ugank.module.category.CategoryFragment;
import me.bakumon.ugank.module.favorite.FavoriteActivity;
import me.bakumon.ugank.module.search.SearchActivity;
import me.bakumon.ugank.module.setting.SettingActivity;
import me.bakumon.ugank.utills.DisplayUtils;
import me.bakumon.ugank.utills.MDTintUtil;

/**
 * HomeActivity
 * Created by bakumon on 2016/12/8 16:42.
 */
public class HomeActivity extends AppCompatActivity implements HomeContract.View {

    @BindView(R.id.fab_home_random)
    FloatingActionButton mFloatingActionButton;
    @BindView(R.id.appbar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.iv_home_banner)
    ImageView mIvHomeBanner;
    @BindView(R.id.tab_home_category)
    DachshundTabLayout mDachshundTabLayout;
    @BindView(R.id.vp_home_category)
    ViewPager mVpCategory;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.tl_home_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_home_setting)
    AppCompatImageView mIvSetting;

    private HomeContract.Presenter mHomePresenter = new HomePresenter(this);
    public final static int SETTING_REQUEST_CODE = 101;

    private CategoryFragment appFragment;
    private CategoryFragment androidFragment;
    private CategoryFragment iOSFragment;
    private CategoryFragment frontFragment;
    private CategoryFragment referenceFragment;
    private CategoryFragment resFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initView();
        mHomePresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHomePresenter.unsubscribe();
    }

    private void initView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 4.4 以上版本
            // 设置 Toolbar 高度为 80dp，适配状态栏
            ViewGroup.LayoutParams layoutParams = mToolbar.getLayoutParams();
            layoutParams.height = DisplayUtils.dp2px(80, this);
            mToolbar.setLayoutParams(layoutParams);
        } else { // 4.4 一下版本
            // 设置 设置图标距离顶部（状态栏最底）为
            mIvSetting.setPadding(mIvSetting.getPaddingLeft(),
                    DisplayUtils.dp2px(15, this),
                    mIvSetting.getPaddingRight(),
                    mIvSetting.getPaddingBottom());
        }

        setFabDynamicState();

        String[] titles = {
                GlobalConfig.CATEGORY_NAME_APP,
                GlobalConfig.CATEGORY_NAME_ANDROID,
                GlobalConfig.CATEGORY_NAME_IOS,
                GlobalConfig.CATEGORY_NAME_FRONT_END,
                GlobalConfig.CATEGORY_NAME_RECOMMEND,
                GlobalConfig.CATEGORY_NAME_RESOURCE};
        CommonViewPagerAdapter infoPagerAdapter = new CommonViewPagerAdapter(getSupportFragmentManager(), titles);

        // App
        appFragment = CategoryFragment.newInstance(titles[0]);
        // Android
        androidFragment = CategoryFragment.newInstance(titles[1]);
        // iOS
        iOSFragment = CategoryFragment.newInstance(titles[2]);
        // 前端
        frontFragment = CategoryFragment.newInstance(titles[3]);
        // 瞎推荐
        referenceFragment = CategoryFragment.newInstance(titles[4]);
        // 拓展资源s
        resFragment = CategoryFragment.newInstance(titles[5]);

        infoPagerAdapter.addFragment(appFragment);
        infoPagerAdapter.addFragment(androidFragment);
        infoPagerAdapter.addFragment(iOSFragment);
        infoPagerAdapter.addFragment(frontFragment);
        infoPagerAdapter.addFragment(referenceFragment);
        infoPagerAdapter.addFragment(resFragment);

        mVpCategory.setAdapter(infoPagerAdapter);
        mDachshundTabLayout.setupWithViewPager(mVpCategory);
        mVpCategory.setCurrentItem(1);
    }

    private CollapsingToolbarLayoutState state; // CollapsingToolbarLayout 折叠状态

    private enum CollapsingToolbarLayoutState {
        EXPANDED, // 完全展开
        COLLAPSED, // 折叠
        INTERNEDIATE // 中间状态
    }

    /**
     * 根据 CollapsingToolbarLayout 的折叠状态，设置 FloatingActionButton 的隐藏和显示
     */
    private void setFabDynamicState() {
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset == 0) {
                    if (state != CollapsingToolbarLayoutState.EXPANDED) {
                        state = CollapsingToolbarLayoutState.EXPANDED; // 修改状态标记为展开
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                        mFloatingActionButton.hide();
                        state = CollapsingToolbarLayoutState.COLLAPSED; // 修改状态标记为折叠
                        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
                        layoutParams.height = DisplayUtils.dp2px(240, HomeActivity.this);
                        mAppBarLayout.setLayoutParams(layoutParams);
                        isBannerBig = false;
                    }
                } else {
                    if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                        if (state == CollapsingToolbarLayoutState.COLLAPSED) {
                            mFloatingActionButton.show();
                        }
                        state = CollapsingToolbarLayoutState.INTERNEDIATE; // 修改状态标记为中间
                    }
                }
            }
        });
    }

    @OnClick(R.id.ll_home_search)
    public void search(View view) {
        startActivity(new Intent(HomeActivity.this, SearchActivity.class));
    }

    @Override
    public void showBannerFail(String failMessage) {
        Toasty.error(this, failMessage).show();
    }

    @Override
    public void setBanner(String imgUrl) {
        Picasso.with(this).load(imgUrl)
                .into(mIvHomeBanner,
                        PicassoPalette.with(imgUrl, mIvHomeBanner)
                                .intoCallBack(new PicassoPalette.CallBack() {
                                    @Override
                                    public void onPaletteLoaded(Palette palette) {
                                        mHomePresenter.setThemeColor(palette);
                                    }
                                }));
    }

    @Override
    public void cacheImg(final String imgUrl) {
        // 预加载 提前缓存好的欢迎图
        Picasso.with(this).load(imgUrl).fetch(new Callback() {
            @Override
            public void onSuccess() {
                mHomePresenter.saveCacheImgUrl(imgUrl);
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void setAppBarLayoutColor(int appBarLayoutColor) {
        mCollapsingToolbar.setContentScrimColor(appBarLayoutColor);
        mAppBarLayout.setBackgroundColor(appBarLayoutColor);
    }

    @Override
    public void setFabButtonColor(int color) {
        MDTintUtil.setTint(mFloatingActionButton, color);
    }

    private ObjectAnimator mAnimator;

    @Override
    public void startBannerLoadingAnim() {
        mFloatingActionButton.setImageResource(R.drawable.ic_loading);
        mAnimator = ObjectAnimator.ofFloat(mFloatingActionButton, "rotation", 0, 360);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setDuration(800);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.start();
    }

    @Override
    public void stopBannerLoadingAnim() {
        mFloatingActionButton.setImageResource(R.drawable.ic_beauty);
        mAnimator.cancel();
        mFloatingActionButton.setRotation(0);
    }

    @Override
    public void enableFabButton() {
        mFloatingActionButton.setEnabled(true);
    }

    @Override
    public void disEnableFabButton() {
        mFloatingActionButton.setEnabled(false);
    }

    @OnClick(R.id.fab_home_random)
    public void random(View view) {
        mHomePresenter.getRandomBanner();
    }

    @OnClick(R.id.iv_home_collection)
    public void collection() {
        startActivity(new Intent(HomeActivity.this, FavoriteActivity.class));
    }

    private boolean isBannerBig; // banner 是否是大图
    private boolean isBannerAniming; // banner 放大缩小的动画是否正在执行

    @OnClick(R.id.iv_home_banner)
    public void wantBig(View view) {
        if (isBannerAniming) {
            return;
        }
        startBannerAnim();
    }

    private void startBannerAnim() {
        final CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
        ValueAnimator animator;
        if (isBannerBig) {
            animator = ValueAnimator.ofInt(DisplayUtils.getScreenHeight(this), DisplayUtils.dp2px(240, this));
        } else {
            animator = ValueAnimator.ofInt(DisplayUtils.dp2px(240, this), DisplayUtils.getScreenHeight(this));
        }
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                layoutParams.height = (int) valueAnimator.getAnimatedValue();
                mAppBarLayout.setLayoutParams(layoutParams);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isBannerBig = !isBannerBig;
                isBannerAniming = false;
            }
        });
        animator.start();
        isBannerAniming = true;
    }

    @Override
    public void onBackPressed() {
        if (isBannerAniming) {
            return;
        }
        if (isBannerBig) {
            startBannerAnim();
        } else {
            super.onBackPressed();
        }
    }

    @OnClick(R.id.iv_home_setting)
    public void goSetting() {
        startActivityForResult(new Intent(HomeActivity.this, SettingActivity.class), SETTING_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (mVpCategory.getCurrentItem()) {
            case 0:
                appFragment.onActivityResult(requestCode, resultCode, data);
                break;
            case 1:
                androidFragment.onActivityResult(requestCode, resultCode, data);
                break;
            case 2:
                iOSFragment.onActivityResult(requestCode, resultCode, data);
                break;
            case 3:
                frontFragment.onActivityResult(requestCode, resultCode, data);
                break;
            case 4:
                referenceFragment.onActivityResult(requestCode, resultCode, data);
                break;
            case 5:
                resFragment.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
}
