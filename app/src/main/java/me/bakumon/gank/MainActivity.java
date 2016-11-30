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

        mVp.setAdapter(infoPagerAdapter);
        mTl.setupWithViewPager(mVp);
        mTl.setTabGravity(TabLayout.GRAVITY_FILL);

        mVp.setCurrentItem(0);
    }

}
