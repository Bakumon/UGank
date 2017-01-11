package me.bakumon.ugank.module.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.florent37.glidepalette.BitmapPalette;
import com.github.florent37.glidepalette.GlidePalette;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import me.bakumon.ugank.R;
import me.bakumon.ugank.base.adapter.CommonViewPagerAdapter;
import me.bakumon.ugank.module.category.CategoryFragment;
import me.bakumon.ugank.module.search.SearchActivity;
import me.bakumon.ugank.module.setting.SettingActivity;
import me.bakumon.ugank.utills.DisplayUtils;
import me.bakumon.ugank.utills.MDTintUtil;
import me.bakumon.ugank.widget.SaveImgDialog;

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
    @BindView(R.id.tl_home_category)
    TabLayout mTlHomeCategory;
    @BindView(R.id.vp_home_category)
    ViewPager mVpCategory;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;

    private HomeContract.Presenter mHomePresenter = new HomePresenter(this);

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

        setFabDynamicState();

        String[] titles = {"App", "Android", "iOS", "前端", "瞎推荐", "拓展资源"};
        CommonViewPagerAdapter infoPagerAdapter = new CommonViewPagerAdapter(getSupportFragmentManager(), titles);

        // App
        CategoryFragment appFragment = CategoryFragment.newInstance("App");
        // Android
        CategoryFragment androidFragment = CategoryFragment.newInstance("Android");
        // iOS
        CategoryFragment iOSFragment = CategoryFragment.newInstance("iOS");
        // 前端
        CategoryFragment frontFragment = CategoryFragment.newInstance("前端");
        // 瞎推荐
        CategoryFragment referenceFragment = CategoryFragment.newInstance("瞎推荐");
        // 拓展资源s
        CategoryFragment resFragment = CategoryFragment.newInstance("拓展资源");

        infoPagerAdapter.addFragment(appFragment);
        infoPagerAdapter.addFragment(androidFragment);
        infoPagerAdapter.addFragment(iOSFragment);
        infoPagerAdapter.addFragment(frontFragment);
        infoPagerAdapter.addFragment(referenceFragment);
        infoPagerAdapter.addFragment(resFragment);

        mVpCategory.setAdapter(infoPagerAdapter);
        mTlHomeCategory.setupWithViewPager(mVpCategory);
        mTlHomeCategory.setTabGravity(TabLayout.GRAVITY_FILL);
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
    public void showBannerFail(String failMessage, final boolean isRandom) {
        Snackbar.make(mVpCategory, failMessage, Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHomePresenter.getBanner(isRandom);
            }
        }).show();
    }

    @Override
    public void setBanner(String imgUrl) {
        Glide.with(this).load(imgUrl)
                .listener(GlidePalette.with(imgUrl)
                        .intoCallBack(new BitmapPalette.CallBack() {
                            @Override
                            public void onPaletteLoaded(@Nullable Palette palette) {
                                mHomePresenter.setThemeColor(palette);
                            }
                        }))
                .into(mIvHomeBanner);
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

    @Override
    public Activity getBigimgContext() {
        return this;
    }

    @Override
    public void showPermissionsTip() {
        Snackbar.make(mVpCategory, "需要权限才能保存妹子", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showMsgSaveSuccess() {
        Snackbar.make(mVpCategory, "图片保存成功", Snackbar.LENGTH_LONG).setAction("查看", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivity(i);
            }
        }).show();
    }

    @Override
    public void showMsgSaveFail() {
        Snackbar.make(mVpCategory, "图片保存失败", Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHomePresenter.saveImg(mIvHomeBanner.getDrawable());
            }
        }).show();
    }

    @Override
    public void showSavingMsgTip() {
        Snackbar.make(mVpCategory, "正在保存图片...", Snackbar.LENGTH_LONG).show();
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

    private boolean isBannerBig; // banner 是否是大图
    private boolean isBannerAniming; // banner 放大缩小的动画是否正在执行

    @OnClick(R.id.iv_home_banner)
    public void wantBig(View view) {
        if (isBannerAniming) {
            return;
        }
        startBannerAnim();
    }

    @OnLongClick(R.id.iv_home_banner)
    public boolean bannerLongClick() {
        if (!isBannerBig) {
            return false;
        }
        showSaveMeiziDialog();
        return true;
    }

    private void showSaveMeiziDialog() {
        SaveImgDialog saveImgDialog = new SaveImgDialog(this);
        saveImgDialog.setItemClick(new SaveImgDialog.OnItemClick() {
            @Override
            public void onItemClick() {
                mHomePresenter.saveImg(mIvHomeBanner.getDrawable());
            }
        });
        saveImgDialog.show();
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
        startActivity(new Intent(HomeActivity.this, SettingActivity.class));
    }
}
