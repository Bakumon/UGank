package me.bakumon.gank.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.bakumon.gank.R;
import me.bakumon.gank.adapter.CommonViewPagerAdapter;
import me.bakumon.gank.fragment.MyFragment;
import me.bakumon.gank.utills.ToastUtil;


public class HomeActivity extends AppCompatActivity implements HomeContract.View {

    @BindView(R.id.iv_home_banner)
    ImageView mIvHomeBanner;
    @BindView(R.id.tl_home_category)
    TabLayout mTlHomeCategory;
    @BindView(R.id.vp_home_category)
    ViewPager mVpCategory;

    private HomeContract.Presenter homePresenter = new HomePresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
//        homePresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        homePresenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        homePresenter.unsubscribe();
    }

    private void initView() {

        String[] titles = {"每日", "Android", "iOS", "福利", "前端", "瞎推荐"};

        CommonViewPagerAdapter infoPagerAdapter = new CommonViewPagerAdapter(getSupportFragmentManager(), titles);

        MyFragment myFragment = new MyFragment();
        MyFragment myFragment1 = new MyFragment();
        MyFragment myFragment2 = new MyFragment();
        MyFragment myFragment3 = new MyFragment();
        MyFragment myFragment4 = new MyFragment();
        MyFragment myFragment5 = new MyFragment();

        infoPagerAdapter.addFragment(myFragment);
        infoPagerAdapter.addFragment(myFragment1);
        infoPagerAdapter.addFragment(myFragment2);
        infoPagerAdapter.addFragment(myFragment3);
        infoPagerAdapter.addFragment(myFragment4);
        infoPagerAdapter.addFragment(myFragment5);


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

    private long mTimeStamp;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mTimeStamp > 2000) {
            ToastUtil.showToastDefault(this, "再按一次退出程序");
        } else {
            finish();
        }
        mTimeStamp = System.currentTimeMillis();
    }

}
