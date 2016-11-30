package me.bakumon.gank;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import me.bakumon.gank.adapter.CommonViewPagerAdapter;
import me.bakumon.gank.fragment.MyFragment;


public class MainActivity extends AppCompatActivity {

    private TabLayout mTl;
    private ViewPager mVp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mTl = (TabLayout) findViewById(R.id.tl);
        mVp = (ViewPager) findViewById(R.id.vp);

        String[] titles = {"每日", "Android", "iOS", "福利", "瞎推荐", "视频"};

        CommonViewPagerAdapter infoPagerAdapter = new CommonViewPagerAdapter(getSupportFragmentManager(), titles);

        MyFragment infoSaleCompanyFragment = new MyFragment(); // 卖公司
        MyFragment infoSaleCompanyFragment1 = new MyFragment(); // 卖公司
        MyFragment infoSaleCompanyFragment2 = new MyFragment(); // 卖公司
        MyFragment infoSaleCompanyFragment3 = new MyFragment(); // 卖公司
        MyFragment infoSaleCompanyFragment4 = new MyFragment(); // 卖公司
        MyFragment infoSaleCompanyFragment5 = new MyFragment(); // 卖公司

        infoPagerAdapter.addFragment(infoSaleCompanyFragment);
        infoPagerAdapter.addFragment(infoSaleCompanyFragment1);
        infoPagerAdapter.addFragment(infoSaleCompanyFragment2);
        infoPagerAdapter.addFragment(infoSaleCompanyFragment3);
        infoPagerAdapter.addFragment(infoSaleCompanyFragment4);
        infoPagerAdapter.addFragment(infoSaleCompanyFragment5);

        mVp.setAdapter(infoPagerAdapter);
        mTl.setupWithViewPager(mVp);
        mTl.setTabGravity(TabLayout.GRAVITY_FILL);

        mVp.setCurrentItem(0);
    }

}
