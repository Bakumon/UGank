package me.bakumon.ugank.utills;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * AndroidUtil
 * Created by bakumon on 2016/12/12 11:00.
 */
public class AndroidUtil {
    /**
     * 使用系统发送分享数据
     *
     * @param context 上下文
     * @param text    要分享的文本
     */
    public static void share(Context context, String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(sendIntent, "分享到"));
    }

    /**
     * 使用浏览器打开
     *
     * @param context 上下文
     * @param uriStr  链接地址
     */
    public static void openWithBrowser(Context context, String uriStr) {
        Uri uri = Uri.parse(uriStr);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    /**
     * 复制文字到剪切板
     * @param context 上下文
     * @param copyText 要复制的文本
     */
    public static boolean copyText(Context context, String copyText) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newRawUri("gankUrl", Uri.parse(copyText));
        cmb.setPrimaryClip(data);
        return true;
    }
}
