package me.bakumon.ugank.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * SaveImgDialog
 * Created by bakumon on 2016/12/29.
 */

public class SaveImgDialog implements DialogInterface.OnClickListener {


    private OnItemClick mOnItemClick;

    // 使用 android.app.AlertDialog ，v7包下的显示有上下内边距，并且字太大了
    private AlertDialog mDialog;
    private AlertDialog.Builder mBuilder;

    public SaveImgDialog(Activity context) {
        mBuilder = new AlertDialog.Builder(context);
        String[] items = {"保存图片"};
        mBuilder.setItems(items, this);
        mDialog = mBuilder.create();
    }

    public void show() {
        mDialog.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case 0: // 第一个，保存图片 item
                if (mOnItemClick != null) {
                    mOnItemClick.onItemClick();
                }
                break;
        }
    }

    public interface OnItemClick {
        void onItemClick();
    }

    public void setItemClick(OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

}
