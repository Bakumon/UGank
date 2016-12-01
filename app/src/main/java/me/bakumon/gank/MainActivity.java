package me.bakumon.gank;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.tiancaicc.springfloatingactionmenu.MenuItemView;
import com.tiancaicc.springfloatingactionmenu.OnMenuActionListener;
import com.tiancaicc.springfloatingactionmenu.SpringFloatingActionMenu;

import me.bakumon.gank.adapter.CommonViewPagerAdapter;
import me.bakumon.gank.fragment.MyFragment;
import me.bakumon.gank.utills.ToastUtil;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout mTl;
    private ViewPager mVp;

    private SpringFloatingActionMenu mSpringFloatingActionMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
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
