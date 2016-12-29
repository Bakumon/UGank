package me.bakumon.ugank.widget;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import me.bakumon.ugank.R;
import me.bakumon.ugank.base.BaseDialog;

/**
 * SaveImgDialog
 * Created by bakumon on 2016/12/29.
 */

public class SaveImgDialog extends BaseDialog implements View.OnClickListener {

    @BindView(R.id.tv_save_img)
    TextView mTvSaveImg;

    private OnItemClick mOnItemClick;

    public SaveImgDialog(Context context, int layoutId) {
        super(context, layoutId, R.style.MyDialog);
    }

    public SaveImgDialog(Activity context) {
        super(context, R.layout.dialog_save_img, R.style.MyDialog);
        initView();
    }

    private void initView() {
        mTvSaveImg.setOnClickListener(this);
    }

    public interface OnItemClick {
        void onItemClick();
    }

    public void setItemClick(OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    @Override
    public void onClick(View view) {
        dismiss();
        if (mOnItemClick != null) {
            mOnItemClick.onItemClick();
        }
    }
}
