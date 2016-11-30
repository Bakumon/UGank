package me.bakumon.gank.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import java.util.ArrayList;

import me.bakumon.gank.R;
import me.bakumon.gank.adapter.MyListAdapter;


/**
 * 我的发布-卖公司
 * Created by mafei on 2016/10/17.
 */

public class MyFragment extends BaseFragment {

    private RecyclerView my_recycler_view;
    private MyListAdapter mMyListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment);
    }

    @Override
    protected void initView() {
        my_recycler_view = getViewById(R.id.my_recycler_view);

        ArrayList<Object> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(null);

        }


        mMyListAdapter = new MyListAdapter(mContext, list, R.layout.item);
        my_recycler_view.setLayoutManager(new LinearLayoutManager(mContext));
        my_recycler_view.setAdapter(mMyListAdapter);
    }


    @Override
    public void getHttpData() {

    }

}
