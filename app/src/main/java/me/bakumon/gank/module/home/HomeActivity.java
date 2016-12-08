package me.bakumon.gank.module.home;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import me.bakumon.gank.adapter.CommonViewPagerAdapter;
import me.bakumon.gank.module.android.AndroidFragment;
import me.bakumon.gank.utills.ToastUtil;


public class HomeActivity extends AppCompatActivity implements HomeContract.View {

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
    }

    /*
       关于 setPresenter 方法：
          由于 googleSimple 中的 Presenter 是在 Activity 中 new 的，在 view 接口，也就是对应的 fragment 中
          不需要在创建 presenter ，所以需要此方法设置拿到 presenter
     */
    @Override
    public void setPresenter(@NonNull HomeContract.Presenter presenter) {
//        mHomePresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mHomePresenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHomePresenter.unsubscribe();
    }

    private void initView() {

        String[] titles = {"每日", "Android", "iOS", "福利", "前端", "瞎推荐"};

        CommonViewPagerAdapter infoPagerAdapter = new CommonViewPagerAdapter(getSupportFragmentManager(), titles);

        AndroidFragment androidFragment = new AndroidFragment();
        AndroidFragment androidFragment1 = new AndroidFragment();
        AndroidFragment androidFragment2 = new AndroidFragment();
        AndroidFragment androidFragment3 = new AndroidFragment();
        AndroidFragment androidFragment4 = new AndroidFragment();
        AndroidFragment androidFragment5 = new AndroidFragment();

        infoPagerAdapter.addFragment(androidFragment);
        infoPagerAdapter.addFragment(androidFragment1);
        infoPagerAdapter.addFragment(androidFragment2);
        infoPagerAdapter.addFragment(androidFragment3);
        infoPagerAdapter.addFragment(androidFragment4);
        infoPagerAdapter.addFragment(androidFragment5);


        mVpCategory.setAdapter(infoPagerAdapter);
        mTlHomeCategory.setupWithViewPager(mVpCategory);
        mTlHomeCategory.setTabGravity(TabLayout.GRAVITY_FILL);

        mVpCategory.setCurrentItem(1);
    }

    @Override
    public void showBannerFail(String failMessage) {
        ToastUtil.showToastDefault(this, failMessage);
    }

    @Override
    public void setBanner(String imgUrl) {
        Glide.with(this).load(imgUrl).into(mIvHomeBanner);
    }

    @OnClick(R.id.fab_home_add)
    public void add(View view) {
//        Snackbar.make(view, "Submit Gank", Snackbar.LENGTH_LONG).show();
        mHomePresenter.getRandomBanner();
    }

    private long mTimeStamp;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mTimeStamp > 2000) {
            ToastUtil.showToastDefault(this, getString(R.string.exit_app_tip));
        } else {
            finish();
        }
        mTimeStamp = System.currentTimeMillis();
    }

}
