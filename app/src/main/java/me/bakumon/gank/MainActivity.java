package me.bakumon.gank;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.melnykov.fab.FloatingActionButton;
import com.tiancaicc.springfloatingactionmenu.MenuItemView;
import com.tiancaicc.springfloatingactionmenu.OnMenuActionListener;
import com.tiancaicc.springfloatingactionmenu.SpringFloatingActionMenu;

import java.util.List;

import me.bakumon.gank.Cache.Data;
import me.bakumon.gank.adapter.CommonViewPagerAdapter;
import me.bakumon.gank.fragment.MyFragment;
import me.bakumon.gank.model.GankBeautyResult;
import me.bakumon.gank.model.Item;
import me.bakumon.gank.network.NetWork;
import me.bakumon.gank.utills.ToastUtil;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout mTl;
    private ViewPager mVp;
    private ImageView mIv;
    private TextView mTv;

    private SpringFloatingActionMenu mSpringFloatingActionMenu;

    Observer<GankBeautyResult> observer = new Observer<GankBeautyResult>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(MainActivity.this, "banner 加载失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(GankBeautyResult gankBeautyResult) {
            Glide.with(MainActivity.this).load(gankBeautyResult.beauties.get(0).url).into(mIv);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        getBannerByCache();
    }

    private Subscription subscription;

    private void getBanner() {
        subscription = NetWork.getGankApi()
                .getBeauties(1, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private long startingTime;

    private void getBannerByCache() {
        unsubscribe();
        startingTime = System.currentTimeMillis();
        subscription = Data.getInstance()
                .subscribeData(new Observer<List<Item>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "banner 加载失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(List<Item> items) {
                        int loadingTime = (int) (System.currentTimeMillis() - startingTime);
                        mTv.setText(loadingTime + "ms" + Data.getInstance().getDataSourceText());
                        Glide.with(MainActivity.this).load(items.get(0).imageUrl).into(mIv);
                    }
                });
    }

    private void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    /**
     * 创建 tumblr 风格的 createTumblrStyleFab
     */
    private void createTumblrStyleFab() {
        // create your own FAB
        final FloatingActionButton fab = new FloatingActionButton(this);
        fab.setType(FloatingActionButton.TYPE_NORMAL);
        fab.setImageResource(R.mipmap.icon_add);
        fab.setColorPressedResId(R.color.colorFabText);
        fab.setColorNormalResId(R.color.colorFabNormal);
        fab.setColorRippleResId(R.color.colorFabRipple);
        fab.setShadow(true);

        mSpringFloatingActionMenu = new SpringFloatingActionMenu.Builder(this)
                .fab(fab)
                //add menu item via addMenuItem(bgColor,icon,label,label color,onClickListener)
                .addMenuItem(R.color.colorFabBackGroundAndroid, R.mipmap.icon_android, "Android", R.color.colorFabText, 60, this)
                .addMenuItem(R.color.colorFabBackGroundRes, R.mipmap.icon_res, "拓展资源", R.color.colorFabText, 60, this)
                .addMenuItem(R.color.colorFabBackGroundVideo, R.mipmap.icon_video, "视频", R.color.colorFabText, 60, this)
                .addMenuItem(R.color.colorFabBackGroundiOS, R.mipmap.icon_ios, "iOS", R.color.colorFabText, 60, this)
                .addMenuItem(R.color.colorFabBackGroundH5, R.mipmap.icon_h5, "前端", R.color.colorFabText, 60, this)
                .addMenuItem(R.color.colorFabBackGroundMeizi, R.mipmap.icon_bra, "妹子", R.color.colorFabText, 60, this)
                //you can choose menu layout animation
                .animationType(SpringFloatingActionMenu.ANIMATION_TYPE_TUMBLR)
                //setup reveal color while the menu opening
                .revealColor(R.color.colorPrimary)
                //set FAB location, only support bottom center and bottom right
                .gravity(Gravity.RIGHT | Gravity.BOTTOM)
                .onMenuActionListner(new OnMenuActionListener() {
                    @Override
                    public void onMenuOpen() {
                        fab.setImageResource(R.mipmap.icon_cancel);
                    }

                    @Override
                    public void onMenuClose() {
                        fab.setImageResource(R.mipmap.icon_add);
                    }
                })
                .build();
    }

    @Override
    public void onBackPressed() {
        if (mSpringFloatingActionMenu.isMenuOpen()) {
            mSpringFloatingActionMenu.hideMenu();
        } else {
            super.onBackPressed();
        }
    }

    private void initView() {
        mTl = (TabLayout) findViewById(R.id.tl);
        mVp = (ViewPager) findViewById(R.id.vp);
        mIv = (ImageView) findViewById(R.id.iv);
        mTv = (TextView) findViewById(R.id.tv_search);

        mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBannerByCache();
            }
        });

        createTumblrStyleFab();

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


        mVp.setAdapter(infoPagerAdapter);
        mTl.setupWithViewPager(mVp);
        mTl.setTabGravity(TabLayout.GRAVITY_FILL);

        mVp.setCurrentItem(1);
    }


    @Override
    public void onClick(View view) {
        MenuItemView menuItemView = (MenuItemView) view;
        ToastUtil.showToastDefault(this, menuItemView.getLabelTextView().getText());
    }
}
