package me.bakumon.gank;

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

    private boolean isListShowImg;

    public void initConfig(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        // 列表是否显示图片
        isListShowImg = sharedPreferences.getBoolean(key_isListShowImg, false);
    }

    public boolean isListShowImg() {
        return isListShowImg;
    }

    public void setListShowImg(boolean listShowImg) {
        SharedPreferences sharedPreferences = App.getInstance().getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key_isListShowImg, isListShowImg);
        if (editor.commit()) {
            isListShowImg = listShowImg;
        }
    }
}
