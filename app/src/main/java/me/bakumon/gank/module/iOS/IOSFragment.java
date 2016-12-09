package me.bakumon.gank.module.iOS;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.bakumon.gank.GlobalConfig;
import me.bakumon.gank.R;
import me.bakumon.gank.entity.IOSResult;
import me.bakumon.gank.widget.LoadMore;
import me.bakumon.gank.widget.RecycleViewDivider;

/**
 * IOSFragment
 * Created by bakumon on 2016/12/8.
 */
public class IOSFragment extends Fragment implements IOSContract.View, SwipeRefreshLayout.OnRefreshListener, LoadMore.OnLoadMoreListener {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private IOSListAdapter mIOSListAdapter;
    private IOSContract.Presenter mPresenter = new IOSPresenter(this);

    private int mPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment, container, false);
        ButterKnife.bind(this, view);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        LoadMore loadMore = new LoadMore(mRecyclerView);
        loadMore.setOnLoadMoreListener(this);

        mIOSListAdapter = new IOSListAdapter(getContext());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL));
        mRecyclerView.setAdapter(mIOSListAdapter);
        mIOSListAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        mPresenter.getIOSItems(GlobalConfig.PAGE_SIZE_ANDROID, mPage, true);
    }

    @Override
    public void onLoadMore() {
        mPage += 1;
        mPresenter.getIOSItems(GlobalConfig.PAGE_SIZE_ANDROID, mPage, false);
    }

    @Override
    public void getIOSItemsFail(String failMessage) {
        mSwipeRefreshLayout.setRefreshing(false);
        Snackbar.make(mSwipeRefreshLayout, failMessage, Snackbar.LENGTH_LONG).show();
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
    public void setIOSItems(IOSResult iosResult) {
        mIOSListAdapter.mData = iosResult.results;
        mIOSListAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void addIOSItems(IOSResult iosResult) {
        mIOSListAdapter.mData.addAll(iosResult.results);
        mIOSListAdapter.notifyDataSetChanged();
    }

}
