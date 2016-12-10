package me.bakumon.gank.module.category;

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
import me.bakumon.gank.entity.CategoryResult;
import me.bakumon.gank.widget.LoadMore;
import me.bakumon.gank.widget.RecycleViewDivider;

/**
 * CategoryFragment
 * Created by bakumon on 2016/12/8.
 */
public class CategoryFragment extends Fragment implements CategoryContract.View, SwipeRefreshLayout.OnRefreshListener, LoadMore.OnLoadMoreListener {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private CategoryListAdapter mAndroidListAdapter;
    private CategoryContract.Presenter mPresenter = new CategoryPresenter(this);

    private int mPage = 1;

    private String mCategoryName;

    public void setCategoryName(String categoryName) {
        this.mCategoryName = categoryName;
    }

    @Override
    public String getCategoryName() {
        return this.mCategoryName;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment, container, false);
        ButterKnife.bind(this, view);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        LoadMore loadMore = new LoadMore(mRecyclerView);
        loadMore.setOnLoadMoreListener(this);

        mAndroidListAdapter = new CategoryListAdapter(getContext());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL));
        mRecyclerView.setAdapter(mAndroidListAdapter);
        mAndroidListAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        mPresenter.getAndroidItems(GlobalConfig.PAGE_SIZE_CATEGORY, mPage, true);
    }

    @Override
    public void onLoadMore() {
        mPage += 1;
        mPresenter.getAndroidItems(GlobalConfig.PAGE_SIZE_CATEGORY, mPage, false);
    }

    @Override
    public void getAndroidItemsFail(String failMessage) {
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
    public void setAndroidItems(CategoryResult categoryResult) {
        mAndroidListAdapter.mData = categoryResult.results;
        mAndroidListAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void addAndroidItems(CategoryResult categoryResult) {
        mAndroidListAdapter.mData.addAll(categoryResult.results);
        mAndroidListAdapter.notifyDataSetChanged();
    }

}
