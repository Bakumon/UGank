package me.bakumon.ugank.module.category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.bakumon.ugank.GlobalConfig;
import me.bakumon.ugank.R;
import me.bakumon.ugank.entity.CategoryResult;
import me.bakumon.ugank.widget.RecycleViewDivider;
import me.bakumon.ugank.widget.recyclerviewwithfooter.DefaultFootItem;
import me.bakumon.ugank.widget.recyclerviewwithfooter.OnLoadMoreListener;
import me.bakumon.ugank.widget.recyclerviewwithfooter.RecyclerViewWithFooter;

/**
 * CategoryFragment
 * Created by bakumon on 2016/12/8.
 */
public class CategoryFragment extends Fragment implements CategoryContract.View, SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.recycler_view)
    RecyclerViewWithFooter mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private CategoryListAdapter mAndroidListAdapter;
    private CategoryContract.Presenter mPresenter = new CategoryPresenter(this);

    private int mPage = 1;

    private String mCategoryName;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mCategoryName = bundle.getString("mCategoryName");
    }

    @Override
    public void onResume() {
        super.onResume();
        // 用于设置项改变后，刷新列表显示
        mAndroidListAdapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment, container, false);
        ButterKnife.bind(this, view);

        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.colorSwipeRefresh1,
                R.color.colorSwipeRefresh2,
                R.color.colorSwipeRefresh3,
                R.color.colorSwipeRefresh4,
                R.color.colorSwipeRefresh5,
                R.color.colorSwipeRefresh6);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mAndroidListAdapter = new CategoryListAdapter(getContext());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL));
        mRecyclerView.setAdapter(mAndroidListAdapter);
        mRecyclerView.setOnLoadMoreListener(this);
        mRecyclerView.setEmpty();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.subscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    public static CategoryFragment newInstance(String mCategoryName) {
        CategoryFragment categoryFragment = new CategoryFragment();

        Bundle bundle = new Bundle();
        bundle.putString("mCategoryName", mCategoryName);

        categoryFragment.setArguments(bundle);
        return categoryFragment;
    }

    @Override
    public String getCategoryName() {
        return this.mCategoryName;
    }

    @Override
    public void showSwipLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideSwipLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
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
    public void setLoading() {
        mRecyclerView.setLoading();
    }

    @Override
    public void getAndroidItemsFail(String failMessage, final int number, final int page, final boolean isRefresh) {
        if (getUserVisibleHint()) {
            Snackbar.make(mSwipeRefreshLayout, failMessage, Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.getAndroidItems(number, page, isRefresh);
                }
            }).show();
        }
    }

    @Override
    public void setAndroidItems(CategoryResult categoryResult) {
        mAndroidListAdapter.mData = categoryResult.results;
        mAndroidListAdapter.notifyDataSetChanged();
    }

    @Override
    public void addAndroidItems(CategoryResult categoryResult) {
        mAndroidListAdapter.mData.addAll(categoryResult.results);
        mAndroidListAdapter.notifyDataSetChanged();
    }

}
