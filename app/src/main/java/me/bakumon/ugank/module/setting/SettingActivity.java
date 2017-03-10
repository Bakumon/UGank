package me.bakumon.ugank.module.setting;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import me.bakumon.ugank.R;
import me.bakumon.ugank.base.SwipeBackBaseActivity;
import me.bakumon.ugank.utills.AlipayZeroSdk;
import me.bakumon.ugank.utills.DisplayUtils;
import me.bakumon.ugank.utills.MDTintUtil;
import me.bakumon.ugank.widget.AboutDialog;

public class SettingActivity extends SwipeBackBaseActivity implements SettingContract.View, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.toolbar_setting)
    Toolbar mToolbarSetting;
    @BindView(R.id.switch_setting)
    SwitchCompat mSwitchSetting;
    @BindView(R.id.appbar_setting)
    AppBarLayout mAppbarSetting;
    @BindView(R.id.tv_setting_version_name)
    AppCompatTextView mTvSettingVersionName;
    @BindView(R.id.ll_setting_image_quality)
    LinearLayout mLlImageQuality;
    @BindView(R.id.tv_setting_image_quality_title)
    TextView mTvImageQualityTitle;
    @BindView(R.id.tv_setting_image_quality_content)
    TextView mTvImageQualityContent;
    @BindView(R.id.tv_setting_clean_cache)
    TextView mTvCleanCache;

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
    public void setAppVersionNameInTv(String versionName) {
        mTvSettingVersionName.setText("版本: " + versionName);
    }

    @Override
    public void setImageQualityChooseUnEnable() {
        mLlImageQuality.setClickable(false);
        mTvImageQualityTitle.setTextColor(getResources().getColor(R.color.colorTextUnEnable));
        mTvImageQualityContent.setTextColor(getResources().getColor(R.color.colorTextUnEnable));
    }

    @Override
    public void setImageQualityChooseEnable() {
        mLlImageQuality.setClickable(true);
        mTvImageQualityTitle.setTextColor(getResources().getColor(R.color.colorTextEnable));
        mTvImageQualityContent.setTextColor(getResources().getColor(R.color.colorTextEnableGary));
    }

    @Override
    public void setThumbnailQualityInfo(String quality) {
        mTvImageQualityContent.setText(quality);
    }

    @Override
    public void showCacheSize(String cache) {
        mTvCleanCache.setText(cache);
    }

    @Override
    public void showSuccessTip(String msg) {
        Toasty.success(this, msg).show();
    }

    @Override
    public void showFailTip(String msg) {
        Toasty.error(this, msg).show();
    }

    @OnClick(R.id.ll_setting_image_quality)
    public void chooseThumbnailQuality() {
        new MaterialDialog.Builder(this)
                .title("缩略图质量")
                .items("原图", "默认", "省流")
                .widgetColor(mSettingPresenter.getColorPrimary())
                .alwaysCallSingleChoiceCallback()
                .itemsCallbackSingleChoice(mSettingPresenter.getThumbnailQuality(), new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        mSettingPresenter.setThumbnailQuality(which);
                        dialog.dismiss();
                        return true;
                    }
                })
                .positiveText("取消")
                .positiveColor(mSettingPresenter.getColorPrimary())
                .show();
    }

    @OnClick(R.id.ll_setting_about)
    public void about() {
        new AboutDialog(this, mSettingPresenter.getColorPrimary()).show();
    }

    @OnClick(R.id.ll_setting_clean_cache)
    public void cleanCache() {
        mSettingPresenter.cleanCache(this);
    }

    @OnClick(R.id.ll_setting_issues)
    public void issues() {
        Uri uri = Uri.parse("https://github.com/Bakumon/UGank/issues");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @OnClick(R.id.ll_setting_pay)
    public void pay() {
        // https://fama.alipay.com/qrcode/qrcodelist.htm?qrCodeType=P  二维码地址
        // http://cli.im/deqr/ 解析二维码
        // aex01251c8foqaprudcp503
        if (AlipayZeroSdk.hasInstalledAlipayClient(this)) {
            AlipayZeroSdk.startAlipayClient(this, "aex01251c8foqaprudcp503");
        } else {
            Snackbar.make(mToolbarSetting, "谢谢，您没有安装支付宝客户端", Snackbar.LENGTH_LONG).show();
        }
    }
}

