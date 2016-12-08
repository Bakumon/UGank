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

import butterknife.BindView;
import butterknife.ButterKnife;
import me.bakumon.gank.R;
import me.bakumon.gank.entity.AndroidResult;
import me.bakumon.gank.widget.RecycleViewDivider;

public class AndroidFragment extends Fragment implements AndroidContract.View {

    @BindView(R.id.rv_android)
    RecyclerView mRvAndroid;
    @BindView(R.id.srl_android)
    SwipeRefreshLayout mSrlAndroid;

    private AndroidListAdapter mAndroidListAdapter;
    private AndroidContract.Presenter mPresenter = new AndroidPresenter(this);


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_android, container, false);
        ButterKnife.bind(this, view);

        mAndroidListAdapter = new AndroidListAdapter(getContext());

        mRvAndroid.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvAndroid.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL));
        mRvAndroid.setAdapter(mAndroidListAdapter);
        mAndroidListAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void setAndroidItems(AndroidResult androidResult) {
        mAndroidListAdapter.mData = androidResult.results;
        mAndroidListAdapter.notifyDataSetChanged();
    }
}
