package me.bakumon.ugank.module.favorite;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.bakumon.ugank.R;
import me.bakumon.ugank.base.SwipeBackBaseActivity;
import me.bakumon.ugank.utills.DisplayUtils;
import me.bakumon.ugank.widget.RecycleViewDivider;
import me.bakumon.ugank.widget.recyclerviewwithfooter.OnLoadMoreListener;
import me.bakumon.ugank.widget.recyclerviewwithfooter.RecyclerViewWithFooter;

public class FavoriteActivity extends SwipeBackBaseActivity implements FavoriteContract.View, SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.toolbar_favorite)
    Toolbar mToolbarFavorite;
    @BindView(R.id.appbar_favorite)
    AppBarLayout mAppbarFavorite;
    @BindView(R.id.recycler_view_favorite)
    RecyclerViewWithFooter mRecyclerView;
    @BindView(R.id.swipe_refresh_layout_favorite)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private FavoriteContract.Presenter mPresenter = new FavoritePresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mAppbarFavorite.setPadding(
                    mAppbarFavorite.getPaddingLeft(),
                    mAppbarFavorite.getPaddingTop() + DisplayUtils.getStatusBarHeight(this),
                    mAppbarFavorite.getPaddingRight(),
                    mAppbarFavorite.getPaddingBottom());
        }
        setSupportActionBar(mToolbarFavorite);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initView();
        mPresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    private void initView() {
        mToolbarFavorite.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.colorSwipeRefresh1,
                R.color.colorSwipeRefresh2,
                R.color.colorSwipeRefresh3,
                R.color.colorSwipeRefresh4,
                R.color.colorSwipeRefresh5,
                R.color.colorSwipeRefresh6);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        FavoriteListAdapter adapter = new FavoriteListAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setOnLoadMoreListener(this);
        mRecyclerView.setEmpty();
    }

    @Override
    public void setToolbarBackgroundColor(int color) {
        mAppbarFavorite.setBackgroundColor(color);
    }

    @Override
    public void showSwipLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

}
