package me.bakumon.gank.base.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;

/**
 * 带position的listener，回调函数里有position及convertView
 * Created by wy on 2015/11/2.
 */
public class ListenerWithPosition implements CompoundButton.OnCheckedChangeListener, CompoundButton.OnLongClickListener, View.OnClickListener, TextWatcher {

    private int mPosition;
    private Object mHolder;
    private OnCheckedChangeWithPositionListener mCheckChangeListener;
    private OnClickWithPositionListener mOnClickListener;
    private OnTextChangWithPosition mOnTextChange;
    private OnLongClickWithPositionListener mOnLongClickListener;

    public ListenerWithPosition(int position, Object holder) {
        this.mPosition = position;
        this.mHolder = holder;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (mCheckChangeListener != null)
            mCheckChangeListener.onCheckedChanged(buttonView, isChecked, mPosition, mHolder);
    }

    @Override
    public void onClick(View v) {
        if (mOnClickListener != null)
            mOnClickListener.onClick(v, mPosition, mHolder);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (mOnTextChange != null) {
            mOnTextChange.beforeTextChanged(s, start, count, after, mPosition, mHolder);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mOnTextChange != null) {
            mOnTextChange.onTextChanged(s, start, before, count, mPosition, mHolder);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mOnTextChange != null) {
            mOnTextChange.afterTextChanged(s, mPosition, mHolder);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mOnLongClickListener != null) {
            mOnLongClickListener.onLongClick(v, mPosition, mHolder);
        }
        return false;
    }

    public interface OnCheckedChangeWithPositionListener<T> {
        void onCheckedChanged(CompoundButton buttonView, boolean isChecked, int position, T holder);
    }

    public interface OnClickWithPositionListener<T> {
        void onClick(View v, int position, T holder);
    }

    public interface OnLongClickWithPositionListener<T> {
        void onLongClick(View v, int position, T holder);
    }

    public interface OnTextChangWithPosition<T> {
        void beforeTextChanged(CharSequence s, int start, int count, int after, int position, T holder);

        void onTextChanged(CharSequence s, int start, int before, int count, int position, T holder);

        void afterTextChanged(Editable s, int position, T holder);
    }

    public void setCheckChangeListener(OnCheckedChangeWithPositionListener mCheckChangeListener) {
        this.mCheckChangeListener = mCheckChangeListener;
    }

    public void setOnClickListener(OnClickWithPositionListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public void setOnLongClickListener(OnLongClickWithPositionListener mOnLongClickListener) {
        this.mOnLongClickListener = mOnLongClickListener;
    }

    public void addTextChangedListener(OnTextChangWithPosition mOnTextChange) {
        this.mOnTextChange = mOnTextChange;
    }
}
