package me.bakumon.gank.module.android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.bakumon.gank.R;
import me.bakumon.gank.adapter.MyListAdapter;
import me.bakumon.gank.widget.RecycleViewDivider;

public class AndroidFragment extends Fragment {

    @BindView(R.id.rv_android)
    RecyclerView mRvAndroid;
    @BindView(R.id.srl_android)
    SwipeRefreshLayout mSrlAndroid;

    private MyListAdapter mMyListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_android, container, false);
        ButterKnife.bind(this, view);

        mMyListAdapter = new MyListAdapter(getContext());

        mRvAndroid.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvAndroid.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL));
        mRvAndroid.setAdapter(mMyListAdapter);

        List<Object> list = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            list.add("");
        }

        mMyListAdapter.mData = list;
        mMyListAdapter.notifyDataSetChanged();
        return view;
    }
}
