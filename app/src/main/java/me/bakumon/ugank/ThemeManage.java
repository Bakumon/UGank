package me.bakumon.ugank;

/**
 * ThemeManage
 * 主题色管理类
 * Created by bakumon on 2016/12/22 15:03.
 */
public enum ThemeManage {
    INSTANCE;

    private int colorPrimary;

    public void initColorPrimary(int colorPrimary) {
        setColorPrimary(colorPrimary);
    }

    public int getColorPrimary() {
        return colorPrimary;
    }

    public void setColorPrimary(int colorPrimary) {
        this.colorPrimary = colorPrimary;
    }


}
