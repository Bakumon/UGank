package me.bakumon.ugank.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import me.bakumon.ugank.R;
import me.bakumon.ugank.base.BaseDialog;

/**
 * AboutDialog
 * Created by bakumon on 2016/12/29.
 */

public class AboutDialog extends BaseDialog {


    @BindView(R.id.tv_affirm)
    TextView mTvAffirm;
    @BindView(R.id.tv_author_github)
    TextView mTvAuthorGithub;
    @BindView(R.id.tv_author_weibo)
    TextView getmTvAuthorWeibo;
    @BindView(R.id.tv_open_address)
    TextView mTvOpenAddress;
    @BindView(R.id.tv_thanks)
    TextView mTvThanks;
    @BindView(R.id.tv_gankio)
    TextView mTvGankio;

    private int mThemeColor;

    private Activity mContext;

    public AboutDialog(Context context, int layoutId) {
        super(context, layoutId, R.style.MyDialog);
    }

    public AboutDialog(Activity context) {
        super(context, R.layout.dialog_about, R.style.MyDialog);
    }

    public AboutDialog(Activity context, int color) {
        this(context);
        mContext = context;
        mThemeColor = color;
        initView();
    }

    private void initView() {
        mTvAffirm.setTextColor(mThemeColor);
        mTvAuthorGithub.setTextColor(mThemeColor);
        getmTvAuthorWeibo.setTextColor(mThemeColor);
        mTvOpenAddress.setTextColor(mThemeColor);
        mTvThanks.setTextColor(mThemeColor);
        mTvGankio.setTextColor(mThemeColor);

        setUnderline(mTvOpenAddress);
        setUnderline(mTvGankio);
        setUnderline(mTvAuthorGithub);
        setUnderline(getmTvAuthorWeibo);
    }

    private void setUnderline(TextView textView) {
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        textView.getPaint().setAntiAlias(true);//抗锯齿
    }

    @OnClick(R.id.tv_affirm)
    public void affirm() {
        cancel();
    }

    @OnClick(R.id.tv_thanks)
    public void thanks() {
        viewIntent("http://weibo.com/daimajia");
    }

    @OnClick(R.id.tv_open_address)
    public void openAddress() {
        viewIntent("https://github.com/Bakumon/UGank");
    }

    @OnClick(R.id.tv_gankio)
    public void gankio() {
        viewIntent("http://gank.io/");
    }

    @OnClick(R.id.tv_author_github)
    public void github() {
        viewIntent("https://github.com/Bakumon/");
    }

    @OnClick(R.id.tv_author_weibo)
    public void weibo() {
        viewIntent("http://weibo.com/twimb/");
    }

    private void viewIntent(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        mContext.startActivity(intent);
    }

}
