package me.bakumon.gank.module.home;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.bakumon.gank.R;
import me.bakumon.gank.base.adapter.CommonViewPagerAdapter;
import me.bakumon.gank.module.category.CategoryFragment;
import me.bakumon.gank.module.other.OtherFragment;
import me.bakumon.gank.utills.ToastUtil;

/**
 * HomeActivity
 * Created by bakumon on 2016/12/8 16:42.
 */
public class HomeActivity extends AppCompatActivity implements HomeContract.View {

    @BindView(R.id.fab_home_add)
    FloatingActionButton mFloatingActionButton;
    @BindView(R.id.appbar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.iv_home_banner)
    ImageView mIvHomeBanner;
    @BindView(R.id.tl_home_category)
    TabLayout mTlHomeCategory;
    @BindView(R.id.vp_home_category)
    ViewPager mVpCategory;

    private HomeContract.Presenter mHomePresenter = new HomePresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        mHomePresenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHomePresenter.unsubscribe();
    }

    private void initView() {

        setFabShowOrHide();

        String[] titles = {"今日", "Android", "iOS", "福利", "App", "前端", "瞎推荐"};

        CommonViewPagerAdapter infoPagerAdapter = new CommonViewPagerAdapter(getSupportFragmentManager(), titles);

        // 今日
        OtherFragment todayFragment = new OtherFragment();

        // Android
        CategoryFragment androidFragment = new CategoryFragment();
        androidFragment.setCategoryName("Android");

        // iOS
        CategoryFragment iOSFragment = new CategoryFragment();
        iOSFragment.setCategoryName("iOS");

        // 福利
        OtherFragment meiziFragment = new OtherFragment();

        // App
        CategoryFragment appFragment = new CategoryFragment();
        appFragment.setCategoryName("App");

        // 前端
        CategoryFragment frontFragment = new CategoryFragment();
        frontFragment.setCategoryName("前端");

        // 瞎推荐
        CategoryFragment referenceFragment = new CategoryFragment();
        referenceFragment.setCategoryName("瞎推荐");


        infoPagerAdapter.addFragment(todayFragment);
        infoPagerAdapter.addFragment(androidFragment);
        infoPagerAdapter.addFragment(iOSFragment);
        infoPagerAdapter.addFragment(meiziFragment);
        infoPagerAdapter.addFragment(appFragment);
        infoPagerAdapter.addFragment(frontFragment);
        infoPagerAdapter.addFragment(referenceFragment);


        mVpCategory.setAdapter(infoPagerAdapter);
        mTlHomeCategory.setupWithViewPager(mVpCategory);
        mTlHomeCategory.setTabGravity(TabLayout.GRAVITY_FILL);

        mVpCategory.setCurrentItem(1);
    }

    private CollapsingToolbarLayoutState state;

    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }

    /**
     * 根据 CollapsingToolbarLayout 的折叠状态，设置 FloatingActionButton 的隐藏和显示
     */
    private void setFabShowOrHide() {
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
    public void search() {
        ToastUtil.showToastDefault(this, "搜索");
    }

    @Override
    public void showBannerFail(String failMessage) {
        Snackbar.make(mVpCategory, failMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setBanner(String imgUrl) {
        Glide.with(this).load(imgUrl).into(mIvHomeBanner);
    }

    @OnClick(R.id.fab_home_add)
    public void add(View view) {
        mHomePresenter.getRandomBanner();
    }

}
