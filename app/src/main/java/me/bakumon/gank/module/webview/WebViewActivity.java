package me.bakumon.gank.module.webview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.bakumon.gank.R;
import me.bakumon.gank.utills.ToastUtil;

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.tb_webview)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_webview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                ToastUtil.showToastDefault(this, "刷新");
                break;
            case R.id.menu_share:
                ToastUtil.showToastDefault(this, "分享");
                break;
            case R.id.menu_copy_link:
                ToastUtil.showToastDefault(this, "复制链接");
                break;
            case R.id.menu_open_with:
                ToastUtil.showToastDefault(this, "用浏览器打开");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
