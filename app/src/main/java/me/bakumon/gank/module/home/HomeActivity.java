package me.bakumon.gank.module.home;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
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
import me.bakumon.gank.module.android.AndroidFragment;
import me.bakumon.gank.module.other.OtherFragment;
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

        // Android
        AndroidFragment androidFragment = new AndroidFragment();

        OtherFragment otherFragment1 = new OtherFragment();
        OtherFragment otherFragment2 = new OtherFragment();
        OtherFragment otherFragment3 = new OtherFragment();
        OtherFragment otherFragment4 = new OtherFragment();
        OtherFragment otherFragment5 = new OtherFragment();

        infoPagerAdapter.addFragment(otherFragment1);
        infoPagerAdapter.addFragment(androidFragment);
        infoPagerAdapter.addFragment(otherFragment2);
        infoPagerAdapter.addFragment(otherFragment3);
        infoPagerAdapter.addFragment(otherFragment4);
        infoPagerAdapter.addFragment(otherFragment5);


        mVpCategory.setAdapter(infoPagerAdapter);
        mTlHomeCategory.setupWithViewPager(mVpCategory);
        mTlHomeCategory.setTabGravity(TabLayout.GRAVITY_FILL);

        mVpCategory.setCurrentItem(1);
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
