package me.bakumon.gank.module.bigimg;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.bakumon.gank.R;
import me.bakumon.gank.utills.DisplayUtils;
import me.bakumon.gank.utills.MDTintUtil;
import me.bakumon.gank.widget.PinchImageView;

public class BigimgActivity extends AppCompatActivity implements BigimgContract.View {

    public static final String MEIZI_URL = "me.bakumon.gank.module.img.BigimgActivity.meizi_url";
    public static final String MEIZI_TITLE = "me.bakumon.gank.module.img.BigimgActivity.meizi_title";

    @BindView(R.id.tv_title_big_img)
    TextView tvTitleBigImg;
    @BindView(R.id.toolbar_big_img)
    Toolbar toolbarBigImg;
    @BindView(R.id.appbar_big_img)
    AppBarLayout appbarBigImg;
    @BindView(R.id.img_big)
    PinchImageView imgBig;
    @BindView(R.id.fab_meizi_save)
    FloatingActionButton mFabMeiziSave;

    private BigimgContract.Presenter mBigimgPresenter = new BigimgPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_bigimg);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            appbarBigImg.setPadding(
                    appbarBigImg.getPaddingLeft(),
                    appbarBigImg.getPaddingTop() + DisplayUtils.getStatusBarHeight(this),
                    appbarBigImg.getPaddingRight(),
                    appbarBigImg.getPaddingBottom());
        }
        setSupportActionBar(toolbarBigImg);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbarBigImg.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mBigimgPresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBigimgPresenter.unsubscribe();
    }

    @OnClick(R.id.fab_meizi_save)
    public void save() {
        mBigimgPresenter.saveImg();
    }

    @Override
    public Activity getBigimgContext() {
        return this;
    }

    @Override
    public void setMeiziTitle(String title) {
        tvTitleBigImg.setText(title);
    }

    @Override
    public void loadMeizuImg(String url) {
        Glide.with(this)
                .load(url)
                .listener(((BigimgPresenter) mBigimgPresenter).new BigimgLoadCompleteistener())
                .into(imgBig);
    }

    @Override
    public void setToolbarBackgroundColor(int color) {
        appbarBigImg.setBackgroundColor(color);
    }

    @Override
    public void setViewColorAccent(int color) {
        MDTintUtil.setTint(mFabMeiziSave, color);
    }

    @Override
    public void showSaveFab() {
        mFabMeiziSave.show();
    }

    @Override
    public void showMsgSaveSuccess(String msg) {
        Snackbar.make(mFabMeiziSave, msg, Snackbar.LENGTH_LONG).setAction("查看", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivity(i);
            }
        }).show();
    }

    @Override
    public void showMsgSaveFail(String msg) {
        Snackbar.make(mFabMeiziSave, msg, Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBigimgPresenter.saveImg();
            }
        }).show();
    }

    @Override
    public void showPermissionsTip(String msg) {
        Snackbar.make(mFabMeiziSave, msg, Snackbar.LENGTH_LONG).show();
    }

    private ObjectAnimator mAnimator;

    @Override
    public void startFabSavingAnim() {
        mFabMeiziSave.setImageResource(R.drawable.ic_loading);
        mAnimator = ObjectAnimator.ofFloat(mFabMeiziSave, "rotation", 0, 360);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setDuration(800);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.start();
    }

    @Override
    public void stopFabSavingAnim() {
        mFabMeiziSave.setImageResource(R.drawable.ic_meizi_save);
        mAnimator.cancel();
        mFabMeiziSave.setRotation(0);
    }

    @Override
    public void setFabEnable(boolean isEnable) {
        mFabMeiziSave.setEnabled(isEnable);
    }

}
