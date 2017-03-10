package me.bakumon.ugank.module.bigimg;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.bakumon.ugank.R;
import me.bakumon.ugank.base.SwipeBackBaseActivity;
import me.bakumon.ugank.utills.DisplayUtils;
import me.bakumon.ugank.widget.PinchImageView;
import me.bakumon.ugank.widget.SquareLoading;

public class BigimgActivity extends SwipeBackBaseActivity implements BigimgContract.View {

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
    @BindView(R.id.sl_big_img_loading)
    SquareLoading mSquareLoading;

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
        Bundle bundle = getIntent().getExtras();
        mBigimgPresenter.loadMeiziImg(bundle);
        mBigimgPresenter.setMeiziTitle(bundle);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBigimgPresenter.unsubscribe();
    }

    @Override
    public void setMeiziTitle(String title) {
        tvTitleBigImg.setText(title);
    }

    @Override
    public void loadMeizuImg(String url) {
        Picasso.with(this)
                .load(url)
                .into(imgBig, new Callback() {
                    @Override
                    public void onSuccess() {
                        hideLoading();
                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    @Override
    public void setToolbarBackgroundColor(int color) {
        appbarBigImg.setBackgroundColor(color);
    }

    @Override
    public void setLoadingColor(int color) {
        mSquareLoading.setSquareColor(color);
    }

    @Override
    public void showLoading() {
        mSquareLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mSquareLoading.setVisibility(View.GONE);
    }
}
