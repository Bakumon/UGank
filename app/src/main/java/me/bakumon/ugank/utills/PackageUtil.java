package me.bakumon.ugank.utills;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * PackageUtil
 * Created by bakumon on 2016/12/29 11:01.
 */
public class PackageUtil {
    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
}
