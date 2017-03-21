package me.bakumon.ugank.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * RecyclableImageView
 * 重写 onDetachedFromWindow 方法，它在从屏幕中消失时回调，去掉drawable引用，加快内存的回收。
 * Created by bakumon on 17-3-21.
 */

public class RecyclableImageView extends AppCompatImageView {

    public RecyclableImageView(Context context) {
        super(context);
    }

    public RecyclableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclableImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setImageDrawable(null);
    }
}
