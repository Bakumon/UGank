package me.bakumon.ugank;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * ConfigManage
 * Created by bakumon on 2016/12/22 15:44.
 */
public enum ConfigManage {
    INSTANCE;

    private final String spName = "app_config";
    private final String key_isListShowImg = "isListShowImg";
    private final String key_thumbnailQuality = "thumbnailQuality";
    private final String key_banner_url = "keyBannerUrl";
    private final String key_launcher_img_show = "keyLauncherImgShow";
    private final String key_launcher_img_probability_show = "keyLauncherImgProbabilityShow";

    private boolean isListShowImg;
    private int thumbnailQuality;
    private String bannerURL;
    private boolean isShowLauncherImg;
    private boolean isProbabilityShowLauncherImg;

    public void initConfig(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        // 列表是否显示图片
        isListShowImg = sharedPreferences.getBoolean(key_isListShowImg, false);
        // 缩略图质量 0：原图 1：默认 2：省流
        thumbnailQuality = sharedPreferences.getInt(key_thumbnailQuality, 1);
        // Banner URL 用于加载页显示
        bannerURL = sharedPreferences.getString(key_banner_url, "");
        // 启动页是否显示妹子图
        isShowLauncherImg = sharedPreferences.getBoolean(key_launcher_img_show, true);
        // 启动页是否概率出现
        isProbabilityShowLauncherImg = sharedPreferences.getBoolean(key_launcher_img_probability_show, true);
    }

    public boolean isListShowImg() {
        return isListShowImg;
    }

    public void setListShowImg(boolean listShowImg) {
        SharedPreferences sharedPreferences = App.getInstance().getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key_isListShowImg, listShowImg);
        if (editor.commit()) {
            isListShowImg = listShowImg;
        }
    }

    public int getThumbnailQuality() {
        return thumbnailQuality;
    }

    public void setThumbnailQuality(int thumbnailQuality) {
        SharedPreferences sharedPreferences = App.getInstance().getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key_thumbnailQuality, thumbnailQuality);
        if (editor.commit()) {
            this.thumbnailQuality = thumbnailQuality;
        }
    }

    public String getBannerURL() {
        return bannerURL;
    }

    public void setBannerURL(String bannerURL) {
        SharedPreferences sharedPreferences = App.getInstance().getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key_banner_url, bannerURL);
        if (editor.commit()) {
            this.bannerURL = bannerURL;
        }
    }

    public boolean isShowLauncherImg() {
        return isShowLauncherImg;
    }

    public void setShowLauncherImg(boolean showLauncherImg) {
        SharedPreferences sharedPreferences = App.getInstance().getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key_launcher_img_show, showLauncherImg);
        if (editor.commit()) {
            isShowLauncherImg = showLauncherImg;
        }
    }

    public boolean isProbabilityShowLauncherImg() {
        return isProbabilityShowLauncherImg;
    }

    public void setProbabilityShowLauncherImg(boolean probabilityShowLauncherImg) {
        SharedPreferences sharedPreferences = App.getInstance().getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key_launcher_img_probability_show, probabilityShowLauncherImg);
        if (editor.commit()) {
            isProbabilityShowLauncherImg = probabilityShowLauncherImg;
        }
    }
}
