package me.bakumon.ugank.module.setting;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
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
    @BindView(R.id.tv_setting_image_quality_tip)
    TextView mTvImageQualityTip;
    @BindView(R.id.tv_setting_clean_cache)
    TextView mTvCleanCache;
    @BindView(R.id.switch_setting_show_launcher_img)
    SwitchCompat mSwitchSettingShowLauncherImg;
    @BindView(R.id.switch_setting_always_show_launcher_img)
    SwitchCompat mSwitchSettingAlwaysShowLauncherImg;
    @BindView(R.id.ll_is_always_show_launcher_img)
    LinearLayout mLlAlwaysShowLauncherImg;
    @BindView(R.id.tv_is_always_show_launcher_img_title)
    AppCompatTextView mTvAlwaysShowLauncherImgTitle;
    @BindView(R.id.tv_is_always_show_launcher_img_content)
    AppCompatTextView mTvAlwaysShowLauncherImgContent;
    @BindView(R.id.tv_is_show_launcher_img_content)
    AppCompatTextView mTvShowLauncherImgContent;

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
                onBackPressed();
            }
        });
        mSwitchSetting.setOnCheckedChangeListener(this);
        mSwitchSettingShowLauncherImg.setOnCheckedChangeListener(this);
        mSwitchSettingAlwaysShowLauncherImg.setOnCheckedChangeListener(this);
        mSettingPresenter.subscribe();
    }

    @Override
    public void onBackPressed() {
        if (mSettingPresenter.isThumbnailSettingChanged()) { // 显示缩略图设置项改变
            setResult(RESULT_OK);
        }
        super.onBackPressed();
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

    @OnClick(R.id.ll_is_show_launcher_img)
    public void isShowLauncherImg() {
        mSwitchSettingShowLauncherImg.setChecked(!mSwitchSettingShowLauncherImg.isChecked());
    }

    @OnClick(R.id.ll_is_always_show_launcher_img)
    public void isAlwaysShowLauncherImg() {
        mSwitchSettingAlwaysShowLauncherImg.setChecked(!mSwitchSettingAlwaysShowLauncherImg.isChecked());
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.switch_setting:
                mSettingPresenter.saveIsListShowImg(isChecked);
                break;
            case R.id.switch_setting_show_launcher_img:
                mSettingPresenter.saveIsLauncherShowImg(isChecked);
                break;
            case R.id.switch_setting_always_show_launcher_img:
                mSettingPresenter.saveIsLauncherAlwaysShowImg(isChecked);
                break;
        }

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
    public void changeIsShowLauncherImgSwitchState(boolean isChecked) {
        mSwitchSettingShowLauncherImg.setChecked(isChecked);
    }

    @Override
    public void changeIsAlwaysShowLauncherImgSwitchState(boolean isChecked) {
        mSwitchSettingAlwaysShowLauncherImg.setChecked(isChecked);
    }

    @Override
    public void setSwitchCompatsColor(int color) {
        MDTintUtil.setTint(mSwitchSetting, color);
        MDTintUtil.setTint(mSwitchSettingShowLauncherImg, color);
        MDTintUtil.setTint(mSwitchSettingAlwaysShowLauncherImg, color);
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
        mTvImageQualityTip.setTextColor(getResources().getColor(R.color.colorTextUnEnable));
    }

    @Override
    public void setImageQualityChooseEnable() {
        mLlImageQuality.setClickable(true);
        mTvImageQualityTitle.setTextColor(getResources().getColor(R.color.colorTextEnable));
        mTvImageQualityContent.setTextColor(getResources().getColor(R.color.colorTextEnableGary));
        mTvImageQualityTip.setTextColor(getResources().getColor(R.color.colorTextEnableGary));
    }

    @Override
    public void setLauncherImgProbabilityUnEnable() {
        mLlAlwaysShowLauncherImg.setClickable(false);
        mSwitchSettingAlwaysShowLauncherImg.setClickable(false);
        mTvAlwaysShowLauncherImgTitle.setTextColor(getResources().getColor(R.color.colorTextUnEnable));
        mTvAlwaysShowLauncherImgContent.setTextColor(getResources().getColor(R.color.colorTextUnEnable));
    }

    @Override
    public void setLauncherImgProbabilityEnable() {
        mLlAlwaysShowLauncherImg.setClickable(true);
        mSwitchSettingAlwaysShowLauncherImg.setClickable(true);
        mTvAlwaysShowLauncherImgTitle.setTextColor(getResources().getColor(R.color.colorTextEnable));
        mTvAlwaysShowLauncherImgContent.setTextColor(getResources().getColor(R.color.colorTextEnableGary));
    }

    @Override
    public void setThumbnailQualityInfo(int quality) {
        String thumbnailQuality = "";
        switch (quality) {
            case 0:
                thumbnailQuality = "原图";
                break;
            case 1:
                thumbnailQuality = "默认";
                break;
            case 2:
                thumbnailQuality = "省流";
                break;
        }
        mTvImageQualityContent.setText(thumbnailQuality);
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

    @Override
    public void setShowLauncherTip(String tip) {
        mTvShowLauncherImgContent.setText(tip);
    }

    @Override
    public void setAlwaysShowLauncherTip(String tip) {
        mTvAlwaysShowLauncherImgContent.setText(tip);
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
        mSettingPresenter.cleanCache();
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
            Toasty.info(this, "谢谢，您没有安装支付宝客户端").show();
        }
    }
}

