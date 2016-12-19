package me.bakumon.gank.module.search;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
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
import me.bakumon.gank.R;
import me.bakumon.gank.utills.ToastUtil;

public class SearchActivity extends AppCompatActivity implements SearchContract.View, TextWatcher, TextView.OnEditorActionListener {

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

    private SearchContract.Presenter mSearchPresenter = new SearchPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
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
        mSearchPresenter.subscribe();
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

    @OnClick(R.id.iv_edit_clear)
    public void editClear() {
        mEdSearch.setText("");
    }

    @OnClick(R.id.iv_search)
    public void search() {
        mSearchPresenter.search(mEdSearch.getText().toString().trim());
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
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            mSearchPresenter.search(mEdSearch.getText().toString().trim());
        }
        return false;
    }
}
