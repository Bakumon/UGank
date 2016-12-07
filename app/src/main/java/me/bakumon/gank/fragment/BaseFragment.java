package me.bakumon.gank.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Fragment的基类
 * Created by DuanLu on 2016/7/29.
 */
public abstract class BaseFragment extends Fragment {

    protected View mView;//当前的View
    protected Context mContext;//Activity的对象
    int ResId;//RES   ID
    View view;// View

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    /**
     * 获取视图id
     *
     * @param id
     */
    protected void setContentView(int id) {
        this.ResId = id;
    }

    /**
     * 获取视图
     *
     * @param view
     */
    protected void setContentView(View view) {
        this.view = view;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (ResId != 0) {
            mView = inflater.inflate(ResId, container, false);
            return mView;
        } else if (view != null) {
            mView = view;
            return view;
        } else {
            TextView textView = new TextView(mContext);
            mView = textView;
            textView.setText("未添加视图");
            textView.setTextSize(30);
            textView.setGravity(Gravity.CENTER);
            return textView;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        ButterKnife.bind(this, mView);
        initView();
        getHttpData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //ButterKnife.unbind(this);
    }

    /**
     * 在此方法中初始化
     */
    protected abstract void initView();

    /**
     * 在此方法中获取http数据
     */
    public abstract void getHttpData();

    /**
     * 查找View,不用强制转型
     *
     * @param id   控件的id
     * @param <VT> View类型
     * @return 对应的View
     */
    protected <VT extends View> VT getViewById(@IdRes int id) {
        return (VT) mView.findViewById(id);
    }

    /**
     * 绑定点击事件
     */
    protected void setOnClickListeners(View.OnClickListener listener, View... views) {
        for (View view : views) {
            view.setOnClickListener(listener);
        }
    }

}
