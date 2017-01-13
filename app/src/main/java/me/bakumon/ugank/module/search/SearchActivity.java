package me.bakumon.ugank.module.search;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.bakumon.ugank.R;
import me.bakumon.ugank.entity.SearchResult;
import me.bakumon.ugank.utills.DisplayUtils;
import me.bakumon.ugank.utills.KeyboardUtils;
import me.bakumon.ugank.utills.MDTintUtil;
import me.bakumon.ugank.widget.RecycleViewDivider;
import me.bakumon.ugank.widget.recyclerviewwithfooter.OnLoadMoreListener;
import me.bakumon.ugank.widget.recyclerviewwithfooter.RecyclerViewWithFooter;

public class SearchActivity extends AppCompatActivity implements SearchContract.View, TextWatcher, TextView.OnEditorActionListener, OnLoadMoreListener {

    @BindView(R.id.toolbar_search)
    Toolbar mToolbarSearch;
    @BindView(R.id.ed_search)
    AppCompatEditText mEdSearch;
    @BindView(R.id.iv_edit_clear)
    AppCompatImageView mIvEditClear;
    @BindView(R.id.iv_search)
    AppCompatImageView mIvSearch;
    @BindView(R.id.appbar_search)
    AppBarLayout mAppbarSearch;
    @BindView(R.id.recycler_view_search)
    RecyclerViewWithFooter mRecyclerViewSearch;
    @BindView(R.id.swipe_refresh_layout_search)
    SwipeRefreshLayout mSwipeRefreshLayoutSearch;

    private SearchContract.Presenter mSearchPresenter = new SearchPresenter(this);

    private int mPage = 1;
    private SearchListAdapter mSearchListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initView();
        mSearchPresenter.subscribe();
    }

    private void initView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mAppbarSearch.setPadding(
                    mAppbarSearch.getPaddingLeft(),
                    mAppbarSearch.getPaddingTop() + DisplayUtils.getStatusBarHeight(this),
                    mAppbarSearch.getPaddingRight(),
                    mAppbarSearch.getPaddingBottom());
        }

        setSupportActionBar(mToolbarSearch);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mToolbarSearch.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mEdSearch.addTextChangedListener(this);
        mEdSearch.setOnEditorActionListener(this);

        mSwipeRefreshLayoutSearch.setColorSchemeResources(
                R.color.colorSwipeRefresh1,
                R.color.colorSwipeRefresh2,
                R.color.colorSwipeRefresh3,
                R.color.colorSwipeRefresh4,
                R.color.colorSwipeRefresh5,
                R.color.colorSwipeRefresh6);
        mSwipeRefreshLayoutSearch.setRefreshing(false);
        mSwipeRefreshLayoutSearch.setEnabled(false);

        mSearchListAdapter = new SearchListAdapter(this);
        mRecyclerViewSearch.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewSearch.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        mRecyclerViewSearch.setAdapter(mSearchListAdapter);
        mRecyclerViewSearch.setOnLoadMoreListener(this);
        mRecyclerViewSearch.setEmpty();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchPresenter.unsubscribe();
    }

    @Override
    public void setToolbarBackgroundColor(int color) {
        mAppbarSearch.setBackgroundColor(color);
    }

    @Override
    public void setEditTextCursorColor(int cursorColor) {
        MDTintUtil.setCursorTint(mEdSearch, cursorColor);
    }

    @Override
    public void showEditClear() {
        mIvEditClear.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEditClear() {
        mIvEditClear.setVisibility(View.GONE);
    }

    @Override
    public void showSearchFail(String failMsg, final String searchText, final int page, final boolean isLoadMore) {
        Snackbar.make(mSwipeRefreshLayoutSearch, failMsg, Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchPresenter.search(searchText, page, isLoadMore);
            }
        }).show();
    }

    @Override
    public void setSearchItems(SearchResult searchResult) {
        mSearchListAdapter.mData = searchResult.results;
        mSearchListAdapter.notifyDataSetChanged();
        mSwipeRefreshLayoutSearch.setRefreshing(false);
    }

    @Override
    public void addSearchItems(SearchResult searchResult) {
        mSearchListAdapter.mData.addAll(searchResult.results);
        mSearchListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showSwipLoading() {
        mSwipeRefreshLayoutSearch.setRefreshing(true);
    }

    @Override
    public void hideSwipLoading() {
        mSwipeRefreshLayoutSearch.setRefreshing(false);
    }

    @Override
    public void showTip(String msg) {
        Snackbar.make(mRecyclerViewSearch, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setLoadMoreIsLastPage() {
        mRecyclerViewSearch.setEnd("没有更多数据了");
    }

    @Override
    public void setEmpty() {
        mRecyclerViewSearch.setEmpty();
    }

    @Override
    public void setLoading() {
        mRecyclerViewSearch.setLoading();
    }

    @Override
    public void onLoadMore() {
        mPage += 1;
        mSearchPresenter.search(mEdSearch.getText().toString().trim(), mPage, true);
    }

    @OnClick(R.id.iv_edit_clear)
    public void editClear() {
        mRecyclerViewSearch.setEmpty();
        mEdSearch.setText("");
        KeyboardUtils.showSoftInput(this, mEdSearch);
        hideSwipLoading();
        mSearchPresenter.unsubscribe();
    }

    @OnClick(R.id.iv_search)
    public void search() {
        KeyboardUtils.hideSoftInput(this);
        mPage = 1;
        mSearchPresenter.search(mEdSearch.getText().toString().trim(), mPage, false);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length() > 0) {
            showEditClear();
        } else {
            hideEditClear();
            mRecyclerViewSearch.setEmpty();
            hideSwipLoading();
            mSearchPresenter.unsubscribe();
        }
        mSearchListAdapter.mData = null;
        mSearchListAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            mPage = 1;
            mSearchPresenter.search(mEdSearch.getText().toString().trim(), mPage, false);
            KeyboardUtils.hideSoftInput(this);
        }
        return false;
    }
}
