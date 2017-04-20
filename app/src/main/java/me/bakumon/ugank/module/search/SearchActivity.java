package me.bakumon.ugank.module.search;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayoutManager;
import com.luolc.emojirain.EmojiRainLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import me.bakumon.ugank.R;
import me.bakumon.ugank.base.SwipeBackBaseActivity;
import me.bakumon.ugank.entity.History;
import me.bakumon.ugank.entity.SearchResult;
import me.bakumon.ugank.utills.DisplayUtils;
import me.bakumon.ugank.utills.KeyboardUtils;
import me.bakumon.ugank.utills.MDTintUtil;
import me.bakumon.ugank.widget.RecycleViewDivider;
import me.bakumon.ugank.widget.recyclerviewwithfooter.OnLoadMoreListener;
import me.bakumon.ugank.widget.recyclerviewwithfooter.RecyclerViewWithFooter;

public class SearchActivity extends SwipeBackBaseActivity implements SearchContract.View, TextWatcher, TextView.OnEditorActionListener, OnLoadMoreListener, HistoryListAdapter.OnItemClickListener {

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
    @BindView(R.id.ll_search_history)
    LinearLayout mLlHistory;
    @BindView(R.id.recycler_search_history)
    RecyclerView mRecyclerViewHistory;
    @BindView(R.id.emoji_rainLayout)
    EmojiRainLayout mEmojiRainLayout;

    private SearchContract.Presenter mSearchPresenter = new SearchPresenter(this);

    private SearchListAdapter mSearchListAdapter;
    private HistoryListAdapter mHistoryListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initView();
        mSearchPresenter.subscribe();
        mSearchPresenter.queryHistory();
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

        mHistoryListAdapter = new HistoryListAdapter(this);

        mHistoryListAdapter.setOnItemClickListener(this);
        mHistoryListAdapter.mData = null;
        mRecyclerViewHistory.setLayoutManager(new FlexboxLayoutManager());
        mRecyclerViewHistory.setAdapter(mHistoryListAdapter);

        mEmojiRainLayout.addEmoji(R.mipmap.emoji1);
        mEmojiRainLayout.addEmoji(R.mipmap.emoji2);
        mEmojiRainLayout.addEmoji(R.mipmap.emoji3);
        mEmojiRainLayout.addEmoji(R.mipmap.emoji4);
        mEmojiRainLayout.addEmoji(R.mipmap.emoji5);
        mEmojiRainLayout.addEmoji(R.mipmap.emoji6);

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
        Toasty.error(this, failMsg).show();
    }

    @Override
    public void setSearchItems(SearchResult searchResult) {
        mSearchListAdapter.mData = searchResult.results;
        mSearchListAdapter.notifyDataSetChanged();
        mSwipeRefreshLayoutSearch.setRefreshing(false);
    }

    @Override
    public void addSearchItems(SearchResult searchResult) {
        int start = mSearchListAdapter.getItemCount();
        mSearchListAdapter.mData.addAll(searchResult.results);
        mSearchListAdapter.notifyItemRangeInserted(start, searchResult.results.size());
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
        Toasty.warning(this, msg).show();
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
    public void showSearchResult() {
        mLlHistory.setVisibility(View.GONE);
        mSwipeRefreshLayoutSearch.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSearchHistory() {
        mLlHistory.setVisibility(View.VISIBLE);
        mSwipeRefreshLayoutSearch.setVisibility(View.GONE);
    }

    @Override
    public void setHistory(List<History> history) {
        mHistoryListAdapter.mData = history;
        mHistoryListAdapter.notifyDataSetChanged();
    }

    @Override
    public void startEmojiRain() {
        mEmojiRainLayout.startDropping();
    }

    @Override
    public void stopEmojiRain() {
        mEmojiRainLayout.stopDropping();
    }

    @Override
    public void onLoadMore() {
        mSearchPresenter.search(mEdSearch.getText().toString().trim(), true);
    }

    @OnClick(R.id.iv_edit_clear)
    public void editClear() {
        mRecyclerViewSearch.setEmpty();
        mEdSearch.setText("");
        KeyboardUtils.showSoftInput(this, mEdSearch);
        hideSwipLoading();
        showSearchHistory();
        mSearchPresenter.unsubscribe();
        mSearchPresenter.queryHistory();
    }

    @OnClick(R.id.iv_search)
    public void search() {
        KeyboardUtils.hideSoftInput(this);
        mSearchPresenter.search(mEdSearch.getText().toString().trim(), false);
    }

    @OnClick(R.id.tv_search_clean)
    public void cleanHistory() {
        mSearchPresenter.deleteAllHistory();
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
            hideSwipLoading();
            mSearchPresenter.unsubscribe();
            mRecyclerViewSearch.setEmpty();
            mSearchListAdapter.mData = null;
            mSearchListAdapter.notifyDataSetChanged();
            showSearchHistory();
            mSearchPresenter.queryHistory();
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            search();
        }
        return false;
    }

    @Override
    public void OnItemClick(History history) {
        if (history == null || history.getContent() == null) {
            return;
        }
        KeyboardUtils.hideSoftInput(this);
        mEdSearch.setText(history.getContent());
        mEdSearch.setSelection(mEdSearch.getText().toString().length());
        mSearchPresenter.search(history.getContent(), false);
    }
}
