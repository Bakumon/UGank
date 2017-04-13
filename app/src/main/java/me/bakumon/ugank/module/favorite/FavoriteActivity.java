package me.bakumon.ugank.module.favorite;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import me.bakumon.ugank.R;
import me.bakumon.ugank.base.SwipeBackBaseActivity;
import me.bakumon.ugank.entity.Favorite;
import me.bakumon.ugank.utills.DisplayUtils;
import me.bakumon.ugank.widget.RecycleViewDivider;
import me.bakumon.ugank.widget.recyclerviewwithfooter.OnLoadMoreListener;
import me.bakumon.ugank.widget.recyclerviewwithfooter.RecyclerViewWithFooter;

public class FavoriteActivity extends SwipeBackBaseActivity implements FavoriteContract.View, OnLoadMoreListener {

    @BindView(R.id.toolbar_favorite)
    Toolbar mToolbarFavorite;
    @BindView(R.id.appbar_favorite)
    AppBarLayout mAppbarFavorite;
    @BindView(R.id.recycler_view_favorite)
    RecyclerViewWithFooter mRecyclerView;

    public static final int REQUEST_CODE_FAVORITE = 101;
    public static final String FAVORITE_POSITION = "me.bakumon.ugank.module.favorite.FavoriteActivity.favorite_position";

    private FavoriteContract.Presenter mPresenter = new FavoritePresenter(this);
    private FavoriteListAdapter mAdapter;

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_FAVORITE) {
            int position = data.getIntExtra(FAVORITE_POSITION, -1);
            if (position != -1) {
                mAdapter.notifyItemRemoved(position);
                mAdapter.mData.remove(position);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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


        mAdapter = new FavoriteListAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnLoadMoreListener(this);
        mRecyclerView.setEmpty();
    }

    @Override
    public void setToolbarBackgroundColor(int color) {
        mAppbarFavorite.setBackgroundColor(color);
    }

    @Override
    public void addFavoriteItems(List<Favorite> favorites) {
        int start = mAdapter.getItemCount();
        mAdapter.mData.addAll(favorites);
        mAdapter.notifyItemRangeInserted(start, favorites.size());
    }

    @Override
    public void setFavoriteItems(List<Favorite> favorites) {
        mAdapter.mData = favorites;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setLoading() {
        mRecyclerView.setLoading();
    }

    @Override
    public void setEmpty() {
        mRecyclerView.setEmpty();
        Toasty.info(this, "暂无收藏").show();
    }

    @Override
    public void setLoadMoreIsLastPage() {
        mRecyclerView.setEnd("没有更多数据了");
    }

    @Override
    public void onLoadMore() {
        mPresenter.getFavoriteItems(false);
    }

}
