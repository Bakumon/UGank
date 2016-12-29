package me.bakumon.ugank.module.setting;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.bakumon.ugank.R;
import me.bakumon.ugank.utills.DisplayUtils;
import me.bakumon.ugank.utills.MDTintUtil;

public class SettingActivity extends AppCompatActivity implements SettingContract.View, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.toolbar_setting)
    Toolbar mToolbarSetting;
    @BindView(R.id.switch_setting)
    SwitchCompat mSwitchSetting;
    @BindView(R.id.appbar_setting)
    AppBarLayout mAppbarSetting;
    @BindView(R.id.tv_setting_cache_size)
    AppCompatTextView mTvSettingCacheSize;
    @BindView(R.id.tv_setting_version_name)
    AppCompatTextView mTvSettingVersionName;

    private SettingPresenter mSettingPresenter = new SettingPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mAppbarSetting.setPadding(
                    mAppbarSetting.getPaddingLeft(),
                    mAppbarSetting.getPaddingTop() + DisplayUtils.getStatusBarHeight(this),
                    mAppbarSetting.getPaddingRight(),
                    mAppbarSetting.getPaddingBottom());
        }
        setSupportActionBar(mToolbarSetting);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mToolbarSetting.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mSwitchSetting.setOnCheckedChangeListener(this);
        mSettingPresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSettingPresenter.unsubscribe();
    }

    @OnClick(R.id.ll_is_show_list_img)
    public void changSwitchState(View view) {
        mSwitchSetting.setChecked(!mSwitchSetting.isChecked());
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        mSettingPresenter.saveIsListShowImg(isChecked);
    }

    @Override
    public void setToolbarBackgroundColor(int color) {
        mAppbarSetting.setBackgroundColor(color);
    }

    @Override
    public void changeSwitchState(boolean isChecked) {
        mSwitchSetting.setChecked(isChecked);
    }

    @Override
    public void setSwitchCompatsColor(int color) {
        MDTintUtil.setTint(mSwitchSetting, color);
    }

    @Override
    public void setCacheSizeInTv(String size) {
        mTvSettingCacheSize.setText("图片等缓存文件 " + size);
    }

    @Override
    public void setAppVersionNameInTv(String versionName) {
        mTvSettingVersionName.setText("版本: " + versionName);
    }

    @OnClick(R.id.ll_setting_cache)
    public void deleteCache() {
        Snackbar.make(mToolbarSetting, "TODO:清理缓存", Snackbar.LENGTH_LONG).show();
    }

    @OnClick(R.id.ll_setting_about)
    public void about() {
        Snackbar.make(mToolbarSetting, "TODO:关于 Dialog", Snackbar.LENGTH_LONG).show();
    }

    @OnClick(R.id.ll_setting_thanks)
    public void thanks() {
        Snackbar.make(mToolbarSetting, "TODO:致谢 Dialog", Snackbar.LENGTH_LONG).show();
    }

    @OnClick(R.id.ll_setting_issues)
    public void issues() {
        Uri uri = Uri.parse("https://github.com/Bakumon/UGank/issues");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}

