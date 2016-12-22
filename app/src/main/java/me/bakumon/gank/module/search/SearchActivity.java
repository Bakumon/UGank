package me.bakumon.gank.module.search;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import me.bakumon.gank.App;
import me.bakumon.gank.R;
import me.bakumon.gank.entity.SearchResult;
import me.bakumon.gank.utills.KeyboardUtils;
import me.bakumon.gank.utills.MDTintUtil;
import me.bakumon.gank.utills.ToastUtil;
import me.bakumon.gank.widget.LoadMore;
import me.bakumon.gank.widget.RecycleViewDivider;

public class SearchActivity extends AppCompatActivity implements SearchContract.View, TextWatcher, TextView.OnEditorActionListener, LoadMore.OnLoadMoreListener {

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
    RecyclerView mRecyclerViewSearch;
    @BindView(R.id.swipe_refresh_layout_search)
    SwipeRefreshLayout mSwipeRefreshLayoutSearch;

    private SearchContract.Presenter mSearchPresenter = new SearchPresenter(this);
    private LoadMore mLoadMore;

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

        mLoadMore = new LoadMore(mRecyclerViewSearch);
        mLoadMore.setOnLoadMoreListener(this);
        mLoadMore.setIsLastPage(true);

        mEdSearch.addTextChangedListener(this);
        mEdSearch.setOnEditorActionListener(this);

        mSwipeRefreshLayoutSearch.setRefreshing(false);
        mSwipeRefreshLayoutSearch.setEnabled(false);

        mSearchListAdapter = new SearchListAdapter(this);
        mRecyclerViewSearch.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewSearch.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        mRecyclerViewSearch.setAdapter(mSearchListAdapter);

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
    public void showSearchFail(String failMsg) {
        ToastUtil.showToastDefault(this, failMsg);
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
    public void setLoadMoreIsLastPage(boolean isLastPage) {
        mLoadMore.setIsLastPage(isLastPage);
    }

    @Override
    public void onLoadMore() {
        mPage += 1;
        mSearchPresenter.search(mEdSearch.getText().toString().trim(), mPage, true);
    }

    @OnClick(R.id.iv_edit_clear)
    public void editClear() {
        mEdSearch.setText("");
        KeyboardUtils.showSoftInput(this, mEdSearch);
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
