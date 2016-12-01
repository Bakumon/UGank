package me.bakumon.gank.utills;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    private static Toast mToast;

    /**
     * 显示最新的Toast以覆盖旧的Toast
     * 解决由于点击过快，新Toast来不及显示，导致迟迟看不到最新的Toast
     *
     * @param context 上下文
     * @param msg     消息
     */
    public static void showToastDefault(Context context, CharSequence msg) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }
}
